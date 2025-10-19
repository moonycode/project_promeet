(function(){
  // ---------- helpers ----------
  function qs(s, r){ return (r||document).querySelector(s); }
  function qsa(s, r){ return Array.prototype.slice.call((r||document).querySelectorAll(s)); }
  function getParam(name){
    var m = location.search.match(new RegExp('[?&]'+name+'=([^&]*)'));
    return m ? decodeURIComponent(m[1]) : '';
  }
  function encode(v){ return encodeURIComponent(v==null?'':v); }
  function openViewModal(taskNo){
	  if (!window.TaskMembersModal || typeof window.TaskMembersModal.open !== 'function') return;
	  window.TaskMembersModal.open({
	    mode: 'viewTask',                 // 보기 전용
	    projectNo: window.TaskPage.projectNo,
	    taskNo: taskNo
	    // onApply 없음
	  });
	}

  // ---------- globals ----------
  var CP   = (window.TaskPage && window.TaskPage.contextPath) || '';
  var BASE = (CP ? CP : '') + '/controller';

  // 임시 편집 버퍼(담당자 포함): taskNo -> { memberIds:[], membersText:'' }
  var EDIT_BUFFER = {};
  // 현재 편집중인 행(있으면 단 하나) 관리
  var editing = { tr: null, taskNo: null };

  // ---------- state / url ----------
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
    if (st.orderBy)                                  q.push('orderBy='+encode(st.orderBy));
    return base + (q.length ? '&' + q.join('&') : '');
  }

  // 주소창을 더럽히지 않도록 pushState 사용 안 함
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

  // ---------- fetch & swap ----------
  function replaceTbodyFromHTML(html){
    var parser = new DOMParser();
    var doc    = parser.parseFromString(html, 'text/html');
    var newTb  = doc.querySelector('#taskBody') || doc.body.querySelector('#taskBody');
    var oldTb  = document.getElementById('taskBody');
    if(newTb && oldTb){
      oldTb.parentNode.replaceChild(newTb, oldTb);
      // tbody 교체 후 동적 핸들러 재바인딩
      mountTbodyDelegates();
    }
  }

  function fetchList(){
    // 편집중이면 취소 안내
    if (editing.tr && !confirm('편집 중인 내용이 취소됩니다. 계속하시겠습니까?')) {
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
        syncURL(); // 주소창은 현재 상태 유지(푸시X)
      })
      .catch(function(err){
        console.error(err);
        alert('목록을 불러오지 못했습니다.');
      });
  }

  // ---------- UI reflect ----------
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

  // --- Dropdown Portal (table 밖으로 띄우기) ---
  (function () {
    const OPEN_CLASS = 'portal-open';
    let current = null;

    function openPortal(labelEl, panelEl) {
      if (current) closePortal();

      const parent = panelEl.parentNode;
      const next   = panelEl.nextSibling;
      const restore = () => {
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

      panelEl.style.top  = `${top}px`;
      panelEl.style.left = `${left}px`;

      const overX = left + panelEl.offsetWidth - window.innerWidth + 12;
      if (overX > 0) panelEl.style.left = `${left - overX}px`;

      const overY = top + panelEl.offsetHeight - window.innerHeight + 12;
      if (overY > 0) panelEl.style.top = `${Math.max(12, r.top - panelEl.offsetHeight - 6)}px`;

      const outsideClickHandler = (e) => {
        if (panelEl.contains(e.target) || labelEl.contains(e.target)) return;
        closePortal();
      };
      const keyHandler = (e) => { if (e.key === 'Escape') closePortal(); };

      setTimeout(() => {
        document.addEventListener('mousedown', outsideClickHandler);
        document.addEventListener('keydown', keyHandler);
      }, 0);

      current = { labelEl, panelEl, restore, outsideClickHandler, keyHandler };
    }

    function closePortal() {
      if (!current) return;
      document.removeEventListener('mousedown', current.outsideClickHandler);
      document.removeEventListener('keydown', current.keyHandler);
      current.restore();
      current = null;
    }

    function bindHeadDropdown(thWrap) {
      const label = thWrap.querySelector('.th-label');
      const panel = thWrap.querySelector('.dd-panel');
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

    document.querySelectorAll('.th-filter .th-wrap').forEach(bindHeadDropdown);

    function recalc(){
      if (!current) return;
      const { labelEl, panelEl } = current;
      const r = labelEl.getBoundingClientRect();
      let top  = Math.round(r.bottom + 6);
      let left = Math.round(r.left);
      panelEl.style.top  = `${top}px`;
      panelEl.style.left = `${left}px`;

      const overX = left + panelEl.offsetWidth - window.innerWidth + 12;
      if (overX > 0) panelEl.style.left = `${left - overX}px`;
      const overY = top + panelEl.offsetHeight - window.innerHeight + 12;
      if (overY > 0) panelEl.style.top = `${Math.max(12, r.top - panelEl.offsetHeight - 6)}px`;
    }
    window.addEventListener('resize', recalc, { passive:true });
    window.addEventListener('scroll', recalc,  { passive:true });
  })();

  // ---------- filters ----------
  (function mountFilters(){
    var fs=qs('#f-status'); var fp=qs('#f-priority');
    if(fs) fs.addEventListener('change', function(){
      state.taskStatus = this.value || 'ALL';
      fetchList().then(reflectUI);
    });
    if(fp) fp.addEventListener('change', function(){
      state.priority = this.value || 'ALL';
      fetchList().then(reflectUI);
    });
  })();

  // ---------- sorting ----------
  (function mountSorters(){
    function press(key){
      if (editing.tr && !confirm('편집 중인 내용이 취소됩니다. 계속하시겠습니까?')) return;
      editing.tr = null; editing.taskNo = null;
      state.orderBy = (state.orderBy === key) ? '' : key; // 토글
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

  // ---------- 편집 보조 ----------
  function toEditRow(tr){
    var tds = tr.getElementsByTagName('td');
    // 컬럼 인덱스(현재 테이블 구조 기준)
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

    // 담당자 셀
    var taskNo = tr.getAttribute('data-taskno');
    var buf = EDIT_BUFFER[taskNo];
    var tempText = buf && buf.membersText ? buf.membersText : (membersText || '-');
    tds[COL.MEMBERS].innerHTML =
      '<span class="chip" id="e-members">'+ tempText +'</span> '+
      '<button type="button" class="btn-outline s" id="e-members-pick">담당자 선택</button>';

    // 액션
    tds[COL.ACT].innerHTML = '<span class="btn-xs btn-ok">확인</span> <span class="btn-xs btn-cancel">취소</span>';

    tr.classList.add('row-edit');
  }

  function cancelEdit(){
    editing.tr = null; editing.taskNo = null;
    fetchList();
  }

  // ---------- tbody delegates (수정/삭제/담당자 모달/추가) ----------
  function mountTbodyDelegates(){
    var tbody = qs('#taskBody');
    if(!tbody) return;

    tbody.addEventListener('click', function(e){
      var t = e.target;
      var cls = (t.className || '');

      // 수정
      if (cls.indexOf('btn-edit') > -1){
        if (editing.tr && editing.tr !== t.closest('tr')){
          if(!confirm('다른 행 편집을 취소하고 이동할까요?')) return;
          cancelEdit();
        }
        var tr = t.closest('tr'); if(!tr) return;
        editing.tr = tr; editing.taskNo = tr.getAttribute('data-taskno');
        toEditRow(tr);
        e.preventDefault();
        return;
      }

      // 편집 확인
      if (cls.indexOf('btn-ok') > -1){
        var tr = t.closest('tr'); if(!tr) return;
        var taskNo = tr.getAttribute('data-taskno');

        var name = qs('#e-name', tr).value.trim();
        var sd   = qs('#e-sd', tr).value;  // '' 허용 → 서버에서 NULL 처리
        var ed   = qs('#e-ed', tr).value;
        var stKr = qs('#e-status', tr).value; // 한글 그대로
        var prKr = qs('#e-prio', tr).value;   // 한글 그대로

        if(!name){ alert('업무명은 필수입니다.'); return; }
        if(sd && ed && sd > ed){ alert('마감일이 시작일보다 빠릅니다.'); return; }

        var buf = EDIT_BUFFER[taskNo] || { memberIds:[], membersText:'' };
        var form =
          'cmd=updateTaskAction'
          + '&taskNo='    + encode(taskNo)
          + '&taskName='  + encode(name)
          + '&taskStatus='+ encode(stKr)   // 한글
          + '&startDate=' + encode(sd)     // '' → NULL
          + '&endDate='   + encode(ed)
          + '&priority='  + encode(prKr)   // 한글
          + '&memberIds=' + encode((buf.memberIds||[]).join(',')); // 서버가 무시해도 무해

        fetch(BASE, {
          method:'POST',
          headers:{'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'},
          credentials: 'same-origin',
          body: form
        }).then(function(){
          delete EDIT_BUFFER[taskNo];
          editing.tr = null; editing.taskNo = null;
          return fetchList();
        });
        e.preventDefault();
        return;
      }

      // 편집 취소
      if (cls.indexOf('btn-cancel') > -1){
        cancelEdit();
        e.preventDefault();
        return;
      }

      // 삭제
      if (cls.indexOf('btn-del') > -1){
        if (editing.tr){ if(!confirm('편집을 취소하고 삭제할까요?')) return; }
        var taskNo = t.getAttribute('data-taskno') || (t.closest('tr') && t.closest('tr').getAttribute('data-taskno'));
        if(!taskNo) return;
        if(!confirm('해당 업무를 삭제하시겠습니까?')) return;
        fetch(BASE, {
          method:'POST',
          headers:{'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'},
          credentials: 'same-origin',
          body:'cmd=deleteTaskAction&taskNo='+encode(taskNo)
        }).then(function(){ return fetchList(); });
        e.preventDefault();
        return;
      }
   // 읽기 모드: 담당자 텍스트 클릭 → 보기 모달
      if (cls.indexOf('member-chip-view') > -1){
        var tr = t.closest('tr'); if(!tr) return;
        var taskNo = tr.getAttribute('data-taskno');
        if (taskNo) openViewModal(taskNo);
        e.preventDefault();
        return;
      }

      // 담당자 모달(편집 중)
      if (cls.indexOf('member-chip-btn') > -1 || t.id === 'e-members-pick'){
        var tr = t.closest('tr'); if(!tr) return;
        var taskNo = tr.getAttribute('data-taskno');

        // 현재 버퍼(임시) 전달, 적용 콜백에서 행에 텍스트 반영
        var buf = EDIT_BUFFER[taskNo] || { memberIds:[], membersText:(qs('#e-members', tr) ? qs('#e-members', tr).textContent.trim() : '') };
        if (window.TaskMembersModal && typeof window.TaskMembersModal.open === 'function'){
          window.TaskMembersModal.open({
            mode:'editTask',
            projectNo: window.TaskPage.projectNo,
            taskNo: taskNo,
            presetIds: buf.memberIds,
            onApply: function(nextIds, nextText){
              EDIT_BUFFER[taskNo] = { memberIds: nextIds || [], membersText: nextText || '' };
              var chip = qs('#e-members', tr);
              if(chip) chip.textContent = (nextText || '-');
            }
          });
        }
        e.preventDefault();
        return;
      }
    }, false);

    // 상단 복원 버튼
    var btnRestore = qs('#btn-restore');
    if(btnRestore && !btnRestore.dataset.bound){
      btnRestore.dataset.bound = '1';
      btnRestore.addEventListener('click', function(){
        var pno = btnRestore.getAttribute('data-projectno');
        if(!pno) return;
        if(!confirm('프로젝트를 복원하시겠습니까?')) return;
        fetch(BASE, {
          method:'POST',
          headers:{'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'},
          credentials: 'same-origin',
          body:'cmd=restoreProjectAction&projectNo='+encode(pno)
        }).then(function(){ location.reload(); });
      }, false);
    }
  }

  // ---------- init ----------
  (function init(){
    mountTbodyDelegates();
    reflectUI();
    syncURL(); // 첫 진입시 URL 정리

    // ▼ btn-add는 페이지 하나에 한 번만 바인딩
    var btnAdd = document.getElementById('btn-add');
    if (btnAdd && !window.TaskPage.readonly && !btnAdd.dataset.bound) {
      btnAdd.dataset.bound = '1';
      btnAdd.addEventListener('click', function () {
        // 이미 임시행이 있으면 또 만들지 않기
        if (document.querySelector('#taskBody tr.is-new')) return;

        if (editing.tr && !confirm('편집 중인 내용이 취소됩니다. 계속하시겠습니까?')) return;
        editing.tr = null; editing.taskNo = null;

        var tbody = document.getElementById('taskBody');
        if (!tbody) return;

        var tr = document.createElement('tr');
        tr.className = 'row-edit is-new';
        tr.innerHTML =
          '<td><input class="input-s" id="n-name" type="text" placeholder="업무명(필수)"></td>' +
          '<td><span class="chip" id="n-members">-</span></td>' +
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

        // 확인
        tr.querySelector('.btn-ok-new').addEventListener('click', function () {
          var name = (tr.querySelector('#n-name')||{}).value.trim();
          if (!name) { alert('업무명은 필수입니다.'); return; }
          var sd = (tr.querySelector('#n-sd')||{}).value || '';
          var ed = (tr.querySelector('#n-ed')||{}).value || '';
          if (sd && ed && sd > ed) { alert('마감일이 시작일보다 빠릅니다.'); return; }

          var stKr = (tr.querySelector('#n-status')||{}).value || '대기';
          var prKr = (tr.querySelector('#n-prio')||{}).value   || '없음';

          var form =
            'cmd=addTaskAction'
            + '&projectNo=' + encode(window.TaskPage.projectNo || 0)
            + '&taskName='  + encode(name)
            + '&taskStatus='+ encode(stKr)   // 한글
            + '&startDate=' + encode(sd)     // '' → NULL
            + '&endDate='   + encode(ed)
            + '&priority='  + encode(prKr);  // 한글

          fetch(BASE, {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
            credentials: 'same-origin',
            body: form
          }).then(function () { return fetchList(); });
        });

        // 취소
        tr.querySelector('.btn-cancel-new').addEventListener('click', function () {
          tr.remove();
        });
      }, false);
    }
  })();
})();
