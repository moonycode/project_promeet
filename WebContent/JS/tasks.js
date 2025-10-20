(function(){
  function qs(s, r){ return (r||document).querySelector(s); }
  function qsa(s, r){ return Array.prototype.slice.call((r||document).querySelectorAll(s)); }
  function getParam(name){
    var m = location.search.match(new RegExp('[?&]'+name+'=([^&]*)'));
    return m ? decodeURIComponent(m[1]) : '';
  }
  function encode(v){ return encodeURIComponent(v==null?'':v); }

  function openViewModal(taskNo){
    if (!window.TaskMembersModal || typeof window.TaskMembersModal.open !== 'function') return;
    window.TaskMembersModal.open('viewTask', window.TaskPage.projectNo, taskNo, null, null);
  }

  var CONFIRM_MSG = '편집 중인 내용이 취소됩니다. 계속하시겠습니까?';

  var CP   = (window.TaskPage && window.TaskPage.contextPath) || '';
  var BASE = (CP ? CP : '') + '/controller';

  // --- 공용 POST (응답검증 + 에러메시지) ---
  function postForm(bodyStr){
    return fetch(BASE, {
      method:'POST',
      headers:{
        'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8',
        'X-Requested-With':'XMLHttpRequest'
      },
      credentials:'same-origin',
      body: bodyStr
    }).then(function(r){
      if(!r.ok){
        return r.text().then(function(t){ throw new Error(t || ('HTTP '+r.status)); });
      }
      return r.text();
    });
  }

  // --- x-www-form-urlencoded 본문 생성 (배열 필드는 같은 이름 반복) ---
  function buildForm(paramsObj, listFields){
    var body = new URLSearchParams();
    var lists = Array.isArray(listFields) ? listFields : [];
    Object.keys(paramsObj).forEach(function(k){
      var v = paramsObj[k];
      if (lists.indexOf(k) > -1 && Array.isArray(v)) {
        v.forEach(function(x){ if (x!=null) body.append(k, x); });
      } else if (v != null) {
        body.append(k, v);
      }
    });
    return body.toString();
  }

  var EDIT_BUFFER = {};
  var NEW_KEY = '__NEW__';
  var editing = { tr: null, taskNo: null };

  function isEditingAlive(){
    return editing.tr && editing.tr.isConnected && editing.tr.classList.contains('row-edit');
  }
  function ensureEditingClean(){
    if (!isEditingAlive()){
      editing.tr = null;
      editing.taskNo = null;
      return false;
    }
    return true;
  }

  // 신규행 감지/정리
  function hasNewRow(){
    var tb = document.getElementById('taskBody');
    return !!(tb && tb.querySelector('tr.is-new'));
  }
  function ensureCloseNewIfExists(next){
    if (!hasNewRow()){ next(); return; }
    if (confirm('작성 중인 새 업무가 취소됩니다. 계속하시겠습니까?')) {
      var tb = document.getElementById('taskBody');
      var nr = tb && tb.querySelector('tr.is-new');
      if (nr) nr.remove();
      delete EDIT_BUFFER[NEW_KEY];
      next();
    }
  }

  window.addEventListener('pageshow', function (e) {
    if (e.persisted) {
      location.reload();
    } else {
      try {
        var nav = performance.getEntriesByType && performance.getEntriesByType('navigation')[0];
        if (nav && (nav.type === 'back_forward')) location.reload();
      } catch (ignore){}
    }
  });

  var state = {
    taskStatus: getParam('taskStatus') || 'ALL',
    priority  : getParam('priority')   || 'ALL',
    orderBy   : getParam('orderBy')    || ''
  };

  function buildURL(st){
    var base = BASE + '?cmd=tasksUI&projectNo=' + encode(window.TaskPage.projectNo || 0);
    var q = [];
    if (st.taskStatus && st.taskStatus !== 'ALL') q.push('taskStatus='+encode(st.taskStatus));
    if (st.priority   && st.priority   !== 'ALL') q.push('priority='+encode(st.priority));
    if (st.orderBy)                                q.push('orderBy='+encode(st.orderBy));
    return base + (q.length ? '&' + q.join('&') : '');
  }

  function syncURL(){
    var url = buildURL(state);
    history.replaceState({__tasks__:true, state:state}, '', url);
  }

  window.addEventListener('popstate', function(ev){
    if(!ev.state || !ev.state.__tasks__) return;
    state = ev.state.state || state;
    reflectUI();
    fetchList();
  });

  function replaceTbodyFromHTML(html){
    var parser = new DOMParser();
    var doc    = parser.parseFromString(html, 'text/html');
    var newTb  = doc.querySelector('#taskBody') || doc.body.querySelector('#taskBody');
    var oldTb  = document.getElementById('taskBody');
    if(newTb && oldTb){
      oldTb.parentNode.replaceChild(newTb, oldTb);
      mountTbodyDelegates();
    }
  }

  function fetchList(){
    ensureEditingClean();

    // 신규행 가드
    if (hasNewRow()){
      if(!confirm('작성 중인 새 업무가 취소됩니다. 계속하시겠습니까?')) {
        return Promise.resolve();
      }
      var tb = document.getElementById('taskBody');
      var nr = tb && tb.querySelector('tr.is-new');
      if (nr) nr.remove();
      delete EDIT_BUFFER[NEW_KEY];
    }

    // 편집행 가드
    if (editing.tr && !confirm(CONFIRM_MSG)) {
      return Promise.resolve();
    }
    editing.tr = null; editing.taskNo = null;

    var url = buildURL(state);
    return fetch(url, {
        headers: { 'X-Requested-With':'XMLHttpRequest' },
        credentials: 'same-origin',
        cache: 'no-store'
      })
      .then(function(r){ return r.text(); })
      .then(function(html){
        replaceTbodyFromHTML(html);
        syncURL();
      })
      .catch(function(err){
        console.error(err);
        alert('목록을 불러오지 못했습니다.');
      });
  }

  function setActiveSortButtons(){
    var map = {
      'start_asc' :'s-start-asc',
      'start_desc':'s-start-desc',
      'end_asc'   :'s-end-asc',
      'end_desc'  :'s-end-desc'
    };
    qsa('.btn-sort').forEach(function(b){
      b.classList.remove('is-active');
      b.setAttribute('aria-pressed','false');
    });
    var id = map[state.orderBy];
    if (id){
      var btn = document.getElementById(id);
      if (btn){
        btn.classList.add('is-active');
        btn.setAttribute('aria-pressed','true');
      }
    }
  }
  function reflectUI(){
    var fs=qs('#f-status');   if(fs) fs.value = state.taskStatus;
    var fp=qs('#f-priority'); if(fp) fp.value = state.priority;
    setActiveSortButtons();
  }

  // === 테이블 헤더 드롭다운 포털 ===
  (function () {
    const OPEN_CLASS = 'portal-open';
    let current = null;

    function openPortal(labelEl, panelEl) {
      if (current) closePortal();

      const parent = panelEl.parentNode;
      const next   = panelEl.nextSibling;
      const restore = function () {
        if (next) parent.insertBefore(panelEl, next); else parent.appendChild(panelEl);
        panelEl.classList.remove(OPEN_CLASS);
        labelEl.setAttribute('aria-expanded','false');
      };

      document.body.appendChild(panelEl);
      panelEl.classList.add(OPEN_CLASS);
      labelEl.setAttribute('aria-expanded','true');

      const r = labelEl.getBoundingClientRect();
      let top  = Math.round(r.bottom + 6);
      let left = Math.round(r.left);

      panelEl.style.top  = top + 'px';
      panelEl.style.left = left + 'px';

      const overX = left + panelEl.offsetWidth - window.innerWidth + 12;
      if (overX > 0) panelEl.style.left = (left - overX) + 'px';

      const overY = top + panelEl.offsetHeight - window.innerHeight + 12;
      if (overY > 0) panelEl.style.top = Math.max(12, r.top - panelEl.offsetHeight - 6) + 'px';

      const outsideClickHandler = function(e){
        if (panelEl.contains(e.target) || labelEl.contains(e.target)) return;
        closePortal();
      };
      const keyHandler = function(e){ if (e.key === "Escape") closePortal(); };

      setTimeout(function(){
        document.addEventListener('mousedown', outsideClickHandler);
        document.addEventListener('keydown', keyHandler);
      }, 0);

      current = { labelEl:labelEl, panelEl:panelEl, restore:restore, outsideClickHandler:outsideClickHandler, keyHandler:keyHandler };
    }

    function closePortal() {
      if (!current) return;
      document.removeEventListener('mousedown', current.outsideClickHandler);
      document.removeEventListener('keydown', current.keyHandler);
      current.restore();
      current = null;
    }

    function bindHeadDropdown(thWrap) {
      var label = thWrap.querySelector('.th-label');
      var panel = thWrap.querySelector('.dd-panel');
      if (!label || !panel) return;
      function toggle(e){
        e.preventDefault(); e.stopPropagation();
        if (current && current.panelEl === panel) closePortal();
        else openPortal(label, panel);
      }
      label.addEventListener('click', toggle, false);
      label.addEventListener('keydown', function(e){
        if (e.key === 'Enter' || e.key === ' ') toggle(e);
      }, false);
    }

    var wraps = document.querySelectorAll('.th-filter .th-wrap');
    for (var i=0;i<wraps.length;i++) bindHeadDropdown(wraps[i]);

    function recalc(){
      if (!current) return;
      var labelEl = current.labelEl, panelEl = current.panelEl;
      var r = labelEl.getBoundingClientRect();
      var top  = Math.round(r.bottom + 6);
      var left = Math.round(r.left);
      panelEl.style.top  = top + 'px';
      panelEl.style.left = left + 'px';

      var overX = left + panelEl.offsetWidth - window.innerWidth + 12;
      if (overX > 0) panelEl.style.left = (left - overX) + 'px';
      var overY = top + panelEl.offsetHeight - window.innerHeight + 12;
      if (overY > 0) panelEl.style.top = Math.max(12, r.top - panelEl.offsetHeight - 6) + 'px';
    }
    window.addEventListener('resize', recalc, { passive:true });
    window.addEventListener('scroll', recalc,  { passive:true });

    window.__closeHeadPortal = closePortal;
  })();

  // === 필터 ===
  (function mountFilters(){
    var fs=qs('#f-status'); var fp=qs('#f-priority');
    if(fs) fs.addEventListener('change', function(){
      ensureEditingClean();
      if (editing.tr && !confirm(CONFIRM_MSG)) return;
      state.taskStatus = this.value || 'ALL';
      fetchList().then(reflectUI);
    });
    if(fp) fp.addEventListener('change', function(){
      ensureEditingClean();
      if (editing.tr && !confirm(CONFIRM_MSG)) return;
      state.priority = this.value || 'ALL';
      fetchList().then(reflectUI);
    });
  })();

  // === 정렬 ===
  (function mountSorters(){
    function press(key){
      ensureEditingClean();
      if (editing.tr && !confirm(CONFIRM_MSG)) return;
      state.orderBy = (state.orderBy === key) ? '' : key;
      fetchList().then(reflectUI);
    }
    function bind(btnId, key){
      var btn = qs('#'+btnId);
      if(!btn) return;
      var handler = function(){ press(key); };
      btn.addEventListener('click', handler, false);
      btn.addEventListener('keydown', function(e){
        if(e.key==='Enter' || e.key===' ') handler();
      }, false);
    }
    bind('s-start-asc',  'start_asc');
    bind('s-start-desc', 'start_desc');
    bind('s-end-asc',    'end_asc');
    bind('s-end-desc',   'end_desc');
  })();

  function toEditRow(tr){
    var tds = tr.getElementsByTagName('td');
    var COL = { NAME:0, MEMBERS:1, STATUS:2, START:3, END:4, PRIO:5, PROG:6, ACT:7 };

    var name   = (tds[COL.NAME].textContent||'').trim();
    var status = (tds[COL.STATUS].textContent||'').trim();
    var sd     = (tds[COL.START].textContent||'').trim();
    var ed     = (tds[COL.END].textContent||'').trim();
    var prio   = (tds[COL.PRIO].textContent||'').trim();
    var membersText = (tds[COL.MEMBERS].textContent||'').trim();

    tds[COL.NAME].innerHTML   = '<input class="input-s" id="e-name" type="text" value="'+name.replace(/"/g,'&quot;')+'">';
    tds[COL.STATUS].innerHTML = (
      '<select class="select-s" id="e-status">'
      + '<option'+(status==='완료'?' selected':'')+'>완료</option>'
      + '<option'+(status==='진행'?' selected':'')+'>진행</option>'
      + '<option'+(status==='대기'?' selected':'')+'>대기</option>'
      + '<option'+(status==='보류'?' selected':'')+'>보류</option>'
      + '<option'+(status==='기타'?' selected':'')+'>기타</option>'
      + '</select>'
    );
    tds[COL.START].innerHTML  = '<input class="input-s w-120" id="e-sd" type="date" value="'+sd+'">';
    tds[COL.END].innerHTML    = '<input class="input-s w-120" id="e-ed" type="date" value="'+ed+'">';
    tds[COL.PRIO].innerHTML   = (
      '<select class="select-s" id="e-prio">'
      + '<option'+(prio==='긴급'?' selected':'')+'>긴급</option>'
      + '<option'+(prio==='높음'?' selected':'')+'>높음</option>'
      + '<option'+(prio==='보통'?' selected':'')+'>보통</option>'
      + '<option'+(prio==='낮음'?' selected':'')+'>낮음</option>'
      + '<option'+(prio==='없음'?' selected':'')+'>없음</option>'
      + '</select>'
    );

    var taskNo = tr.getAttribute('data-taskno');
    var buf = EDIT_BUFFER[taskNo] || { pjoinNos:[], membersText:(membersText||'-') };
    var tempText = buf.membersText || (membersText || '-');
    tds[COL.MEMBERS].innerHTML =
      '<span class="chip" id="e-members">'+ tempText +'</span> '+
      '<button type="button" class="btn-outline s member-chip-btn" id="e-members-pick">담당자 선택</button>';

    tds[COL.ACT].innerHTML = '<span class="btn-xs btn-ok">확인</span> <span class="btn-xs btn-cancel">취소</span>';

    tr.classList.add('row-edit');
  }

  function cancelEdit(){
    editing.tr = null; editing.taskNo = null;
    fetchList();
  }

  function mountTbodyDelegates(){
    var tbody = qs('#taskBody');
    if(!tbody) return;

    tbody.addEventListener('click', function(e){
      var t = e.target;
      var cls = (t.className || '');

      if (cls.indexOf('btn-edit') > -1){
        ensureEditingClean();
        if (editing.tr && editing.tr !== t.closest('tr')){
          if(!confirm(CONFIRM_MSG)) return;
          cancelEdit();
        }
        if (hasNewRow()){
          ensureCloseNewIfExists(function(){});
          if (hasNewRow()) return; // 사용자가 취소
        }
        var tr = t.closest('tr'); if(!tr) return;
        editing.tr = tr; editing.taskNo = tr.getAttribute('data-taskno');
        toEditRow(tr);
        e.preventDefault();
        return;
      }

      if (cls.indexOf('btn-ok') > -1){
        var tr = t.closest('tr'); if(!tr) return;
        var taskNo = tr.getAttribute('data-taskno');

        var name = qs('#e-name', tr).value.trim();
        var sd   = qs('#e-sd', tr).value;
        var ed   = qs('#e-ed', tr).value;
        var stKr = qs('#e-status', tr).value;
        var prKr = qs('#e-prio', tr).value;

        if(!name){ alert('업무명을 입력하세요.'); return; }
        if(sd && ed && sd > ed){ alert('마감일이 시작일보다 빠릅니다.'); return; }

        var buf = EDIT_BUFFER[taskNo] || { pjoinNos:[], membersText:'' };

        var form = buildForm({
          cmd:        'updateTaskAction',
          taskNo:     taskNo,
          projectNo:  (window.TaskPage.projectNo || 0),
          taskName:   name,
          taskStatus: stKr,
          startDate:  sd,
          endDate:    ed,
          priority:   prKr,
          pjoinNos:   (buf.pjoinNos || [])
        }, ['pjoinNos']);

        postForm(form).then(function(){
          delete EDIT_BUFFER[taskNo];
          editing.tr = null; editing.taskNo = null;
          return fetchList();
        }).catch(function(err){
          console.error(err);
          alert('업무 저장에 실패했습니다.\n\n' + err.message);
        });
        e.preventDefault();
        return;
      }

      if (cls.indexOf('btn-cancel') > -1){
        cancelEdit();
        e.preventDefault();
        return;
      }

      if (cls.indexOf('btn-del') > -1){
        ensureEditingClean();
        if (editing.tr){ if(!confirm(CONFIRM_MSG)) return; }
        if (hasNewRow()){
          ensureCloseNewIfExists(function(){});
          if (hasNewRow()) return;
        }
        var taskNo = t.getAttribute('data-taskno') || (t.closest('tr') && t.closest('tr').getAttribute('data-taskno'));
        if(!taskNo) return;
        if(!confirm('해당 업무를 삭제하시겠습니까?')) return;

        var form = 'cmd=deleteTaskAction&taskNo='+encode(taskNo)+'&projectNo='+encode(window.TaskPage.projectNo||0);
        postForm(form).then(function(){ return fetchList(); })
        .catch(function(err){
          console.error(err);
          alert('업무 삭제에 실패했습니다.\n\n' + err.message);
        });
        e.preventDefault();
        return;
      }

      if (cls.indexOf('member-chip-view') > -1){
        var tr = t.closest('tr'); if(!tr) return;
        var taskNo = tr.getAttribute('data-taskno');
        if (taskNo) openViewModal(taskNo);
        e.preventDefault();
        return;
      }

      if (cls.indexOf('member-chip-btn') > -1 || t.id === 'e-members-pick' || t.id === 'n-members-pick'){
        var tr = t.closest('tr'); if(!tr) return;
        var taskNo = tr.getAttribute('data-taskno');

        var bufKey = taskNo || NEW_KEY;
        var chipId = taskNo ? 'e-members' : 'n-members';
        var chipEl = qs('#'+chipId, tr);
        var currentText = chipEl ? chipEl.textContent.trim() : '-';

        var buf = EDIT_BUFFER[bufKey] || { pjoinNos:[], membersText: currentText };

        if (window.TaskMembersModal && typeof window.TaskMembersModal.open === 'function'){
          var preset = buf.pjoinNos || [];
          var apply  = function(nextIds, nextText){
            EDIT_BUFFER[bufKey] = { pjoinNos: (nextIds||[]), membersText: (nextText||'-') };
            var chip = qs('#'+chipId, tr);
            if (chip) chip.textContent = (nextText || '-');
          };

          if (taskNo) {
            window.TaskMembersModal.open('editTask', window.TaskPage.projectNo, taskNo, preset, apply);
          } else {
            window.TaskMembersModal.open('editTask', window.TaskPage.projectNo, null, preset, apply);
          }
        }
        e.preventDefault();
        return;
      }
    }, false);

    var btnRestore = qs('#btn-restore');
    if(btnRestore && !btnRestore.dataset.bound){
      btnRestore.dataset.bound = '1';
      btnRestore.addEventListener('click', function(){
        var pno = btnRestore.getAttribute('data-projectno');
        if(!pno) return;
        if(!confirm('프로젝트를 복원하시겠습니까?')) return;
        // cmd 이름은 서버 매핑과 동일하게 유지(restoreProjectAction)
        postForm('cmd=restoreProjectAction&projectNo='+encode(pno))
          .then(function(){ location.reload(); })
          .catch(function(err){
            console.error(err);
            alert('프로젝트 복원에 실패했습니다.\n\n' + err.message);
          });
      }, false);
    }
  }

  (function init(){
    mountTbodyDelegates();
    reflectUI();
    syncURL();

    var btnAdd = document.getElementById('btn-add');
    if (btnAdd && !window.TaskPage.readonly && !btnAdd.dataset.bound) {
      btnAdd.dataset.bound = '1';
      btnAdd.addEventListener('click', function () {
        ensureEditingClean();
        if (editing.tr){
          if(!confirm(CONFIRM_MSG)) return;
          cancelEdit();
        }
        if (hasNewRow()){
          ensureCloseNewIfExists(function(){});
          if (hasNewRow()) return;
        }

        var tbody = document.getElementById('taskBody');
        if (!tbody) return;

        if (tbody.querySelector('tr.is-new')) return;

        var tr = document.createElement('tr');
        tr.className = 'row-edit is-new';
        tr.innerHTML =
          '<td><input class="input-s" id="n-name" type="text" placeholder="업무명(필수)"></td>' +
          '<td>' +
          '  <span class="chip" id="n-members">-</span> ' +
          '  <button type="button" class="btn-outline s member-chip-btn" id="n-members-pick">담당자 선택</button>' +
          '</td>' +
          '<td>' +
          '  <select class="select-s" id="n-status">' +
          '    <option selected>대기</option><option>진행</option><option>완료</option><option>보류</option><option>기타</option>' +
          '  </select>' +
          '</td>' +
          '<td><input class="input-s w-120" id="n-sd" type="date"></td>' +
          '<td><input class="input-s w-120" id="n-ed" type="date"></td>' +
          '<td>' +
          '  <select class="select-s" id="n-prio">' +
          '    <option selected>없음</option><option>낮음</option><option>보통</option><option>높음</option><option>긴급</option>' +
          '  </select>' +
          '</td>' +
          '<td class="progress-cell">0%</td>' +
          '<td class="actions-cell">' +
          '  <span class="btn-xs btn-ok-new">확인</span> <span class="btn-xs btn-cancel-new">취소</span>' +
          '</td>';

        tbody.appendChild(tr);
        (tr.querySelector('#n-name') || tr).focus();

        tr.querySelector('.btn-ok-new').addEventListener('click', function () {
          var name = (tr.querySelector('#n-name')||{}).value.trim();
          if (!name) { alert('업무명은 필수입니다.'); return; }
          var sd = (tr.querySelector('#n-sd')||{}).value || '';
          var ed = (tr.querySelector('#n-ed')||{}).value || '';
          if (sd && ed && sd > ed) { alert('마감일이 시작일보다 빠릅니다.'); return; }

          var stKr = (tr.querySelector('#n-status')||{}).value || '대기';
          var prKr = (tr.querySelector('#n-prio')||{}).value   || '없음';

          var newBuf = EDIT_BUFFER[NEW_KEY] || { pjoinNos: [] };
          var form = buildForm({
            cmd:        'addTaskAction',
            projectNo:  (window.TaskPage.projectNo || 0),
            taskName:   name,
            taskStatus: stKr,
            startDate:  sd,
            endDate:    ed,
            priority:   prKr,
            pjoinNos:   (newBuf.pjoinNos || [])
          }, ['pjoinNos']);

          postForm(form).then(function () {
            delete EDIT_BUFFER[NEW_KEY];
            return fetchList();
          }).catch(function(err){
            console.error(err);
            alert('업무 추가에 실패했습니다.\n\n' + err.message);
          });
        });

        tr.querySelector('.btn-cancel-new').addEventListener('click', function () {
          delete EDIT_BUFFER[NEW_KEY];
          tr.remove();
        });
      }, false);
    }
  })();
})();
