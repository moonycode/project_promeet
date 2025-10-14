(function () {
  const tbody = document.getElementById('taskTbody');
  const filterStatus = document.getElementById('filterStatus');
  const filterPriority = document.getElementById('filterPriority');
  const sortButtons = document.querySelectorAll('.btn-sort');
  const btnAddRow = document.getElementById('btnAddRow');

  // ★ JSP에서 ${project.projectNo}를 data-attr로 심어주세요 (아래 JSP 예시 있음)
  const projectNo = document.body.getAttribute('data-projectno');

  let editingRow = null;
  let draftRow = null;
  let isInsertMode = false;

  function cancelEditSilent() {
    if (!draftRow) return;
    if (isInsertMode) draftRow.remove();
    else if (editingRow) editingRow.style.display = '';
    editingRow = null; draftRow = null; isInsertMode = false;
  }

  function applyFilters() {
    const sVal = filterStatus ? filterStatus.value : '전체';
    const pVal = filterPriority ? filterPriority.value : '전체';
    Array.from(tbody.querySelectorAll('tr')).forEach(tr => {
      if (tr === draftRow) return;
      const status = tr.getAttribute('data-status') || '';
      const priority = tr.getAttribute('data-priority') || '';
      const okStatus = (sVal === '전체') || (status === sVal);
      const okPriority = (pVal === '전체') || (priority === pVal);
      tr.style.display = (okStatus && okPriority) ? '' : 'none';
    });
  }

  function dateValue(s){ return s ? s.replaceAll('-', '') : ''; }

  function applySort(field, dir) {
    cancelEditSilent();
    const rows = Array.from(tbody.querySelectorAll('tr')).filter(tr => tr !== draftRow);
    rows.sort((a,b)=>{
      let av='', bv='';
      if(field==='start'){ av=dateValue(a.getAttribute('data-start')); bv=dateValue(b.getAttribute('data-start')); }
      if(field==='end'){   av=dateValue(a.getAttribute('data-end'));   bv=dateValue(b.getAttribute('data-end')); }
      if(av===bv) return 0;
      return (dir==='asc') ? (av>bv?1:-1) : (av<bv?1:-1);
    });
    rows.forEach(r=>tbody.appendChild(r));
  }

  function priorityOptions(selected){
    const arr=['','낮음','보통','높음','긴급','없음'];
    return arr.map(v=>`<option value="${v}" ${v===selected?'selected':''}>${v===''?'없음':v}</option>`).join('');
  }
  function statusOptions(selected){
    const arr=['대기','진행','완료','보류','기타'];
    return arr.map(v=>`<option value="${v}" ${v===selected?'selected':''}>${v}</option>`).join('');
  }

  function makeEditRow(fromTr, insertMode){
    const tds = fromTr ? fromTr.querySelectorAll('td') : null;
    const name = fromTr ? tds[0].textContent.trim() : '';
    const status = fromTr ? (fromTr.getAttribute('data-status')||'') : '대기';
    const start  = fromTr ? (fromTr.getAttribute('data-start') ||'') : '';
    const end    = fromTr ? (fromTr.getAttribute('data-end')   ||'') : '';
    const prio   = fromTr ? (fromTr.getAttribute('data-priority')||'') : '';

    const tr=document.createElement('tr'); tr.className='row-edit';
    tr.innerHTML = `
      <td><input class="input-s" type="text" id="editName" value="${insertMode?'':name}" placeholder="업무명(필수)"/></td>
      <td><span class="chip">담당자 추가<span class="count"></span></span></td>
      <td><select class="select-s w-120" id="editStatus">${statusOptions(insertMode?'대기':status)}</select></td>
      <td><input class="input-s w-90" type="date" id="editStart" value="${insertMode?'':start}"/></td>
      <td><input class="input-s w-90" type="date" id="editEnd" value="${insertMode?'':end}"/></td>
      <td><select class="select-s w-90" id="editPriority">${priorityOptions(insertMode?'':prio)}</select></td>
      <td>-</td>
      <td>
        <button type="button" class="btn-xs" id="btnOk">확인</button>
        <button type="button" class="btn-xs" id="btnCancel">취소</button>
      </td>`;
    return tr;
  }

  function startEdit(tr){
    cancelEditSilent(); editingRow=tr; isInsertMode=false;
    draftRow = makeEditRow(tr,false); tr.after(draftRow); tr.style.display='none'; wireEditButtons(tr);
  }
  function startInsert(){
    cancelEditSilent(); isInsertMode=true;
    draftRow = makeEditRow(tbody.querySelector('tr')||document.createElement('tr'), true);
    tbody.appendChild(draftRow); wireEditButtons(null);
  }

  async function ajax(url, payload){
    const res = await fetch(url,{
      method:'POST',
      headers:{'Content-Type':'application/json;charset=UTF-8'},
      body: JSON.stringify(payload)
    });
    if(!res.ok) throw new Error('서버 오류');
    return await res.json();
  }

  function wireEditButtons(originalTr){
    draftRow.querySelector('#btnCancel').addEventListener('click', cancelEditSilent);

    draftRow.querySelector('#btnOk').addEventListener('click', async ()=>{
      const name  = draftRow.querySelector('#editName').value.trim();
      const status= draftRow.querySelector('#editStatus').value || null;
      const start = draftRow.querySelector('#editStart').value || null;
      const end   = draftRow.querySelector('#editEnd').value || null;
      const prio  = draftRow.querySelector('#editPriority').value || null;

      if(!name){ alert('업무명은 필수입니다.'); return; }

      try{
        if(isInsertMode){
          // INSERT
          const out = await ajax('controller?cmd=taskAddJson', {
            projectNo: Number(projectNo), taskName: name,
            taskStatus: status, startDate: start, endDate: end, priority: prio
          });
          if(!out.ok) throw new Error(out.message||'등록 실패');

          const t = out.task; // 서버가 돌려준 신규 task
          const newTr = document.createElement('tr');
          newTr.setAttribute('data-taskno', t.taskNo);
          newTr.setAttribute('data-status', t.taskStatus || '');
          newTr.setAttribute('data-priority', t.priority || '');
          newTr.setAttribute('data-start', t.startDate || '');
          newTr.setAttribute('data-end', t.endDate || '');
          newTr.innerHTML = `
            <td>${t.taskName}</td>
            <td><span class="chip">${t.assigneesText || '담당자 배정'}</span></td>
            <td>${t.taskStatus || ''}</td>
            <td>${t.startDate || ''}</td>
            <td>${t.endDate || ''}</td>
            <td>${t.priority || ''}</td>
            <td class="progress-cell">${t.progress != null ? t.progress : 0}%</td>
            <td>
              <button type="button" class="btn-xs btn-edit">수정</button>
              <button type="button" class="btn-xs btn-del">삭제</button>
            </td>`;
          tbody.replaceChild(newTr, draftRow);
          draftRow=null; isInsertMode=false; bindRowButtons(newTr); applyFilters();
        }else{
          // UPDATE
          const taskNo = Number(originalTr.getAttribute('data-taskno'));
          const out = await ajax('controller?cmd=taskUpdateJson', {
            taskNo, taskName: name, taskStatus: status, startDate: start, endDate: end, priority: prio
          });
          if(!out.ok) throw new Error(out.message||'수정 실패');

          originalTr.setAttribute('data-status', status||'');
          originalTr.setAttribute('data-priority', prio||'');
          originalTr.setAttribute('data-start', start||'');
          originalTr.setAttribute('data-end', end||'');
          const tds = originalTr.querySelectorAll('td');
          tds[0].textContent = name;
          tds[2].textContent = status||'';
          tds[3].textContent = start||'';
          tds[4].textContent = end||'';
          tds[5].textContent = prio||'';
          cancelEditSilent(); applyFilters();
        }
      }catch(err){
        alert(err.message);
      }
    });
  }

  function bindRowButtons(tr){
    tr.querySelector('.btn-edit')?.addEventListener('click', e=>{ e.stopPropagation(); startEdit(tr); });
    tr.querySelector('.btn-del')?.addEventListener('click', async e=>{
      e.stopPropagation();
      if(!confirm('선택한 업무를 삭제하시겠습니까?')) return;
      const taskNo = Number(tr.getAttribute('data-taskno'));
      try{
        const out = await ajax('controller?cmd=taskDeleteJson', { taskNo });
        if(!out.ok) throw new Error(out.message||'삭제 실패');
        tr.remove();
      }catch(err){ alert(err.message); }
    });
  }

  function init(){
    Array.from(tbody.querySelectorAll('tr')).forEach(bindRowButtons);
    filterStatus?.addEventListener('change', ()=>{ cancelEditSilent(); applyFilters(); });
    filterPriority?.addEventListener('change', ()=>{ cancelEditSilent(); applyFilters(); });
    sortButtons.forEach(btn=>{
      btn.addEventListener('click', ()=>{
        const field=btn.getAttribute('data-sort'); const dir=btn.getAttribute('data-dir');
        applySort(field, dir);
      });
    });
    btnAddRow?.addEventListener('click', startInsert);
    document.querySelectorAll('.th-label,.th-text,.btn-outline').forEach(el=>{
      el.addEventListener('click', ()=> cancelEditSilent());
    });
    applyFilters();
  }
  document.addEventListener('DOMContentLoaded', init);
})();
