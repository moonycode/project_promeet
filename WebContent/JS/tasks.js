(function(){
  // ===== 유틸 =====
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

  // 공용 POST
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

  // x-www-form-urlencoded 본문 생성 (배열 필드는 같은 이름 반복)
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

  function hasNewRow(){
    var tb = document.getElementById('taskBody');
    return !!(tb && tb.querySelector('tr.is-new'));
  }

  // 새/편집 상태 이탈 시 confirm 1회만 + 새행은 조용히 제거
  function setAddButtonDisabled(disabled){
    var addBtn = document.getElementById('btn-add');
    if (!addBtn) return;
    if (disabled) {
      addBtn.classList.add('is-disabled');
      addBtn.setAttribute('aria-disabled','true');
    } else {
      addBtn.classList.remove('is-disabled');
      addBtn.removeAttribute('aria-disabled');
    }
  }

  function confirmLoseEditingIfNeeded(opts) {
    var opt = opts || {};
    // 새행은 경고 없이 정리 (중복 confirm 방지)
    if (hasNewRow()) {
      var tb = document.getElementById('taskBody');
      var nr = tb && tb.querySelector('tr.is-new');
      if (nr) nr.remove();
      delete EDIT_BUFFER[NEW_KEY];
      setAddButtonDisabled(false);
    }
    // 편집행만 1회 확인
    if (editing.tr) {
      if (!opt.silentForEdit){
        if (!confirm(CONFIRM_MSG)) return false;
      }
      editing.tr = null;
      editing.taskNo = null;
    }
    return true;
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

    // 혹시 남아 있던 새행 강제 제거
    if (hasNewRow()){
      var tb = document.getElementById('taskBody');
      var nr = tb && tb.querySelector('tr.is-new');
      if (nr) nr.remove();
      delete EDIT_BUFFER[NEW_KEY];
      setAddButtonDisabled(false);
    }

    // 편집행 초기화
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
        reflectUI();
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
      if (!confirmLoseEditingIfNeeded({ silentForEdit:false })) return;
      state.taskStatus = this.value || 'ALL';
      fetchList().then(reflectUI);
    });
    if(fp) fp.addEventListener('change', function(){
      if (!confirmLoseEditingIfNeeded({ silentForEdit:false })) return;
      state.priority = this.value || 'ALL';
      fetchList().then(reflectUI);
    });
  })();

  // === 정렬 ===
  (function mountSorters(){
    function press(key){
      if (!confirmLoseEditingIfNeeded({ silentForEdit:false })) return;
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
    var buf = EDIT_BUFFER[taskNo] || { pjoinNos:[], membersText:(membersText||'') };
    var tempText = (buf.membersText || membersText || '').trim();
    if (!tempText) tempText = '담당자 추가'; // ✨ 의도적 변경: 빈 경우 기본 문구

    tds[COL.MEMBERS].innerHTML =
      '<button type="button" class="chip member-chip-btn" id="e-members-pick">'
      + tempText + '</button>';

    tds[COL.ACT].innerHTML = '<span class="btn-xs btn-ok">확인</span> <span class="btn-xs btn-cancel">취소</span>';

    tr.classList.add('row-edit');
  }

  function cancelEdit(){
    editing.tr = null; editing.taskNo = null;
    fetchList();
  }
  function ensureEditProjectButton() {
	  var pno = String(window.TaskPage && window.TaskPage.projectNo || '');
	  var btn = document.getElementById('btn-edit-project');
	  if (btn) {
	    // 보이기 + 잠재적 숨김 속성 해제
	    btn.style.display = '';
	    btn.removeAttribute('hidden');
	    btn.classList.remove('ghost');
	  } else {
	    // 꽂을 자리 찾기(복원/재개 버튼 근처 → 헤더 액션 영역 → 헤더)
	    var container =
	      (document.getElementById('btn-restore') && document.getElementById('btn-restore').parentNode) ||
	      (document.getElementById('btn-reopen')  && document.getElementById('btn-reopen').parentNode)  ||
	      document.querySelector('.header .actions-right') ||
	      document.querySelector('.header') || document.body;

	    btn = document.createElement('a');
	    btn.id = 'btn-edit-project';
	    btn.className = 'btn';
	    btn.setAttribute('data-projectno', pno);
	    btn.href = (CP || '') + '/controller?cmd=updateProjectUI&projectNo=' + encodeURIComponent(pno);
	    btn.textContent = '프로젝트 수정';
	    container.insertBefore(btn, container.firstChild);
	  }

	  // 클릭 바인딩 보장
	  if (!btn.dataset.bound) {
	    btn.dataset.bound = '1';
	    btn.addEventListener('click', function(e){
	      // a 링크면 기본 이동도 OK, 하지만 readonly 방지 체크 유지
	      var pno = this.getAttribute('data-projectno');
	      if (!pno) { e.preventDefault(); return; }
	      if (window.TaskPage && window.TaskPage.readonly) { e.preventDefault(); return; }
	      // a의 href가 있으므로 추가 작업 불필요
	    }, false);
	  }
	  return btn;
	}

  function activateButtonsInPlace() {
    // 복원/재개 버튼 숨김, 프로젝트 수정 보이기
    var r = document.getElementById('btn-restore'); if (r) r.style.display = 'none';
    var o = document.getElementById('btn-reopen');  if (o) o.style.display = 'none';
    ensureEditProjectButton();


    // 읽기 전용 해제
    window.TaskPage.readonly = false;

    // 목록행의 액션 버튼들 교체(이미 있음)
    var rows = document.querySelectorAll('#taskBody tr[data-taskno]');
    rows.forEach(function(tr){
      var taskNo = tr.getAttribute('data-taskno');
      var act = tr.querySelector('.actions-cell');
      if (!act) return;
      if (act.querySelector('.ghost')) {
        act.innerHTML =
          '<span class="btn-xs btn-edit">수정</span> ' +
          '<span class="btn-xs btn-del" data-taskno="'+ (taskNo || '') +'">삭제</span>';
      }
    });

    // 업무추가 영역 보이기 + 바인딩 보장
    var addWrap = document.getElementById('add-btn-wrap');
    if (addWrap) addWrap.style.display = '';

    var addBtn = document.getElementById('btn-add');
    if (addBtn) {
      addBtn.classList.remove('is-disabled');
      addBtn.removeAttribute('aria-disabled');
      if (!addBtn.dataset.bound) {
        addBtn.dataset.bound = '1';
        // init에서 쓰던 동일 로직을 그대로 호출
        addBtn.addEventListener('click', function () {
          if (hasNewRow()) return;
          setAddButtonDisabled(true);
          var tbody = document.getElementById('taskBody');
          if (!tbody) return;

          var tr = document.createElement('tr');
          tr.className = 'row-edit is-new';
          tr.innerHTML =
            '<td><input class="input-s" id="n-name" type="text" placeholder="업무명(필수)"></td>' +
            '<td><button type="button" class="chip member-chip-btn" id="n-members-pick">담당자 추가</button></td>' +
            '<td><select class="select-s" id="n-status"><option selected>대기</option><option>진행</option><option>완료</option><option>보류</option><option>기타</option></select></td>' +
            '<td><input class="input-s w-120" id="n-sd" type="date"></td>' +
            '<td><input class="input-s w-120" id="n-ed" type="date"></td>' +
            '<td><select class="select-s" id="n-prio"><option selected>없음</option><option>낮음</option><option>보통</option><option>높음</option><option>긴급</option></select></td>' +
            '<td class="progress-cell">0%</td>' +
            '<td class="actions-cell"><span class="btn-xs btn-ok-new">확인</span> <span class="btn-xs btn-cancel-new">취소</span></td>';

          tbody.appendChild(tr);
          (tr.querySelector('#n-name') || tr).focus();

          var newBuf = EDIT_BUFFER[NEW_KEY] || { pjoinNos: [], membersText:'' };
          EDIT_BUFFER[NEW_KEY] = newBuf;

          var pickBtn = tr.querySelector('#n-members-pick');
          if (pickBtn) {
            pickBtn.addEventListener('click', function(e){
              e.preventDefault(); e.stopPropagation();
              if (window.TaskMembersModal && typeof window.TaskMembersModal.open === 'function'){
                var preset = newBuf.pjoinNos || [];
                var apply  = function(nextIds, nextText){
                  var text = (nextText && nextText.trim().length>0) ? nextText : formatMembersTextByIds(nextIds);
                  if (!text || !text.trim()) text = '담당자 추가';
                  newBuf.pjoinNos   = (nextIds || []);
                  newBuf.membersText = text;
                  EDIT_BUFFER[NEW_KEY] = newBuf;
                  var chip = tr.querySelector('#n-members-pick');
                  if (chip) chip.textContent = text;
                };
                window.TaskMembersModal.open('editTask', window.TaskPage.projectNo, null, preset, apply);
              }
            }, false);
          }

          // 신규 저장/취소 바인딩 (기존 init과 동일)
          tr.querySelector('.btn-ok-new').addEventListener('click', function () {
            var name = (tr.querySelector('#n-name')||{}).value.trim();
            if (!name) { alert('업무명은 필수입니다.'); return; }
            var sd = (tr.querySelector('#n-sd')||{}).value || '';
            var ed = (tr.querySelector('#n-ed')||{}).value || '';
            if (sd && ed && sd > ed) { alert('마감일이 시작일보다 빠릅니다.'); return; }
            var stKr = (tr.querySelector('#n-status')||{}).value || '대기';
            var prKr = (tr.querySelector('#n-prio')||{}).value   || '없음';
            var bufNow = EDIT_BUFFER[NEW_KEY] || newBuf;

            var form = buildForm({
              cmd:        'addTaskAction',
              projectNo:  (window.TaskPage.projectNo || 0),
              taskName:   name,
              taskStatus: stKr,
              startDate:  sd,
              endDate:    ed,
              priority:   prKr,
              pjoinNos:   (bufNow.pjoinNos || [])
            }, ['pjoinNos']);

            if (Array.isArray(bufNow.pjoinNos) && bufNow.pjoinNos.length > 0) {
              form += '&pjoinNos=' + encodeURIComponent(bufNow.pjoinNos.join(','));
            }

            postForm(form).then(function () {
              var tb = document.getElementById('taskBody');
              var nr = tb && tb.querySelector('tr.is-new');
              if (nr) nr.remove();
              delete EDIT_BUFFER[NEW_KEY];
              setAddButtonDisabled(false);
              return fetchList();
            }).catch(function(err){
              console.error(err);
              alert('업무 추가에 실패했습니다.\n\n' + err.message);
            });
          });

          tr.querySelector('.btn-cancel-new').addEventListener('click', function () {
            delete EDIT_BUFFER[NEW_KEY];
            tr.remove();
            setAddButtonDisabled(false);
          });
        }, false);
      }
    }
  }


  function formatMembersTextByIds(nextIds){
    if (!nextIds || nextIds.length === 0) return '';
    var first = String(nextIds[0]);
    var rest  = nextIds.length - 1;
    return rest > 0 ? (first + ' 외 ' + rest) : first;
  }

  // === tbody 델리게이트 ===
  function mountTbodyDelegates(){
    var tbody = qs('#taskBody');
    if(!tbody) return;

    tbody.addEventListener('click', function(e){
      var t = e.target;
      var cls = (t.className || '');

      // 편집
      if (cls.indexOf('btn-edit') > -1){
        if (!confirmLoseEditingIfNeeded({ silentForEdit:false })) { e.preventDefault(); return; }
        var tr = t.closest('tr'); if(!tr) return;
        editing.tr = tr; editing.taskNo = tr.getAttribute('data-taskno');
        toEditRow(tr);
        e.preventDefault();
        return;
      }

      // 저장
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

        var form = 'cmd=updateTaskAction'
            + '&taskNo='    + encode(taskNo)
            + '&projectNo=' + encode(window.TaskPage.projectNo || 0)
            + '&taskName='  + encode(name)
            + '&taskStatus='+ encode(stKr)
            + '&startDate=' + encode(sd)
            + '&endDate='   + encode(ed)
            + '&priority='  + encode(prKr);
   (buf.pjoinNos || []).forEach(function(no){
     form += '&pjoinNos=' + encode(no);
   });

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

      // 취소
      if (cls.indexOf('btn-cancel') > -1){
        cancelEdit();
        e.preventDefault();
        return;
      }

      // 삭제
      if (cls.indexOf('btn-del') > -1){
        if (!confirmLoseEditingIfNeeded({ silentForEdit:false })) return;
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

      // 담당자 보기
      if (cls.indexOf('member-chip-view') > -1){
        var tr = t.closest('tr'); if(!tr) return;
        var taskNo = tr.getAttribute('data-taskno');
        if (taskNo) openViewModal(taskNo);
        e.preventDefault();
        return;
      }

      // 담당자 선택(편집/신규 공용)
      if (cls.indexOf('member-chip-btn') > -1 || t.id === 'e-members-pick' || t.id === 'n-members-pick'){
        var tr = t.closest('tr'); if(!tr) return;
        var taskNo = tr.getAttribute('data-taskno');

        var bufKey = taskNo || NEW_KEY;
        var chipId = taskNo ? 'e-members-pick' : 'n-members-pick';
        var chipEl = qs('#'+chipId, tr);

        var currentText = chipEl ? chipEl.textContent.trim() : '';
        var buf = EDIT_BUFFER[bufKey] || { pjoinNos:[], membersText: currentText };

        if (window.TaskMembersModal && typeof window.TaskMembersModal.open === 'function'){
          var preset = buf.pjoinNos || [];
          var apply  = function(nextIds, nextText){
            var text = (nextText && nextText.trim().length>0) ? nextText : formatMembersTextByIds(nextIds);
            // 빈 문자열이면 기본 문구
            if (!text || !text.trim()) text = '담당자 추가';
            EDIT_BUFFER[bufKey] = { pjoinNos: (nextIds||[]), membersText: text };
            var chip = qs('#'+chipId, tr);
            if (chip) chip.textContent = text;
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

    // 복원 버튼
    var btnRestore = qs('#btn-restore');
    if (btnRestore && !btnRestore.dataset.bound) {
      btnRestore.dataset.bound = '1';
      btnRestore.addEventListener('click', function(){
        var pno = btnRestore.getAttribute('data-projectno');
        if(!pno) return;
        if(!confirm('프로젝트를 복원하시겠습니까?')) return;
        restore(pno);
      }, false);
    }

    // 재개 버튼
    var btnReopen = document.getElementById('btn-reopen');
    if (btnReopen && !btnReopen.dataset.bound){
      btnReopen.dataset.bound = '1';
      btnReopen.addEventListener('click', function(){
        var pno = btnReopen.getAttribute('data-projectno');
        if(!pno) return;
        if(!confirm('프로젝트 완료 상태를 해제하시겠습니까?')) return;
        restore(pno);
      });
    }
  }

  // 복원 호출 (화면 유지 모드)
  function restore(projectNo){
    return postForm('cmd=restoreProject&projectNo=' + encodeURIComponent(projectNo))
      .then(function(txt){
        try {
          var res = JSON.parse(txt);
          if (res.status !== 'ok') { alert(res.message || '복원 실패'); return; }
        } catch(e){ /* JSON 아니면 무시 */ }

        activateButtonsInPlace();
        fetchList();
      })
      .catch(function(){
        alert('복원 실패');
      });
  }

  // === 초기화 ===
  (function init(){
    mountTbodyDelegates();
    reflectUI();
    syncURL();

    // '프로젝트 수정' 버튼
    var btnEditProject = document.getElementById('btn-edit-project');
    if (btnEditProject && !btnEditProject.dataset.bound) {
      btnEditProject.dataset.bound = '1';
      btnEditProject.addEventListener('click', function(){
        var pno = this.getAttribute('data-projectno');
        if (!pno) return;
        if (window.TaskPage && window.TaskPage.readonly) return;
        location.href = CP + '/controller?cmd=updateProjectUI&projectNo=' + encodeURIComponent(pno);
      }, false);
    }

    // '업무 추가' 버튼
    var btnAdd = document.getElementById('btn-add');
    if (btnAdd && !window.TaskPage.readonly && !btnAdd.dataset.bound) {
      btnAdd.dataset.bound = '1';
      btnAdd.addEventListener('click', function () {
        if (hasNewRow()) return; // 이미 새 행 있으면 무시

        setAddButtonDisabled(true);

        var tbody = document.getElementById('taskBody');
        if (!tbody) return;

        var tr = document.createElement('tr');
        tr.className = 'row-edit is-new';
        tr.innerHTML =
          '<td><input class="input-s" id="n-name" type="text" placeholder="업무명(필수)"></td>' +
          '<td>' +
          // ✨ 의도적 변경: 기본 텍스트 표시
          '  <button type="button" class="chip member-chip-btn" id="n-members-pick">담당자 추가</button>' +
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

        var newBuf = EDIT_BUFFER[NEW_KEY] || { pjoinNos: [], membersText:'' };
        EDIT_BUFFER[NEW_KEY] = newBuf;

        var pickBtn = tr.querySelector('#n-members-pick');
        if (pickBtn) {
          pickBtn.addEventListener('click', function(e){
        	  e.preventDefault();
        	  e.stopPropagation();
            if (window.TaskMembersModal && typeof window.TaskMembersModal.open === 'function'){
              var preset = newBuf.pjoinNos || [];
              var apply  = function(nextIds, nextText){
                var text = (nextText && nextText.trim().length>0) ? nextText : formatMembersTextByIds(nextIds);
                if (!text || !text.trim()) text = '담당자 추가'; // ✨
                newBuf.pjoinNos   = (nextIds || []);
                newBuf.membersText = text;
                EDIT_BUFFER[NEW_KEY] = newBuf;
                var chip = tr.querySelector('#n-members-pick');
                if (chip) chip.textContent = text;
              };
              window.TaskMembersModal.open('editTask', window.TaskPage.projectNo, null, preset, apply);
            }
          }, false);
        }

        // 신규 저장
        tr.querySelector('.btn-ok-new').addEventListener('click', function () {
          var name = (tr.querySelector('#n-name')||{}).value.trim();
          if (!name) { alert('업무명은 필수입니다.'); return; }
          var sd = (tr.querySelector('#n-sd')||{}).value || '';
          var ed = (tr.querySelector('#n-ed')||{}).value || '';
          if (sd && ed && sd > ed) { alert('마감일이 시작일보다 빠릅니다.'); return; }

          var stKr = (tr.querySelector('#n-status')||{}).value || '대기';
          var prKr = (tr.querySelector('#n-prio')||{}).value   || '없음';
          var bufNow = EDIT_BUFFER[NEW_KEY] || newBuf;
          var form = 'cmd=addTaskAction'
        	       + '&projectNo=' + encode(window.TaskPage.projectNo || 0)
        	       + '&taskName='  + encode(name)
        	       + '&taskStatus='+ encode(stKr)
        	       + '&startDate=' + encode(sd)
        	       + '&endDate='   + encode(ed)
        	       + '&priority='  + encode(prKr);
        	    (bufNow.pjoinNos || []).forEach(function(no){
        	      form += '&pjoinNos=' + encode(no);
        	    });
          
          postForm(form).then(function () {
            var tb = document.getElementById('taskBody');
            var nr = tb && tb.querySelector('tr.is-new');
            if (nr) nr.remove();
            delete EDIT_BUFFER[NEW_KEY];
            setAddButtonDisabled(false);
            return fetchList();
          }).catch(function(err){
            console.error(err);
            alert('업무 추가에 실패했습니다.\n\n' + err.message);
          });
        });

        // 신규 취소
        tr.querySelector('.btn-cancel-new').addEventListener('click', function () {
          delete EDIT_BUFFER[NEW_KEY];
          tr.remove();
          setAddButtonDisabled(false);
        });
      }, false);
    }
  })();
})();
