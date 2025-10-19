(function(){
  var CP   = (window.ProjectMembers && window.ProjectMembers.contextPath) || '';
  var BASE = (CP ? CP : '') + '/controller';
  var PNO  = (window.ProjectMembers && window.ProjectMembers.projectNo) || 0;

  var left = document.getElementById('leftList');
  var right= document.getElementById('rightList');
  if(!left || !right) return;

  function rebuildLeftPreview(){
    // 수집: 우측 체크된 항목들만 카드로 구성
    var checked = right.querySelectorAll('.pmgr-check:checked');
    var arr = Array.prototype.map.call(checked, function(cb){
      var card = cb.closest('.member-card');
      var clone = card.cloneNode(true);
      // 좌측은 체크박스 없는 스타일로
      clone.classList.add('no-check');
      // 체크박스 제거
      var inp = clone.querySelector('input[type="checkbox"]');
      if(inp) inp.remove();
      return clone;
    });

    left.innerHTML = '';
    arr.forEach(function(node){ left.appendChild(node); });
  }

  // 최초 미리보기 정렬
  rebuildLeftPreview();

  // 변화시마다 즉시 미리보기 반영
  right.addEventListener('change', function(e){
    var t = e.target;
    if(t.classList.contains('pmgr-check')){
      // 팀장은 disabled라 여기 안 들어오지만, 안전망
      if(t.disabled) return;
      rebuildLeftPreview();
    }
  }, false);

  // 저장
  var btn = document.getElementById('btnSave');
  if(btn){
    btn.addEventListener('click', function(){
      var cbs = right.querySelectorAll('.pmgr-check:checked');
      var ids = [];
      Array.prototype.forEach.call(cbs, function(cb){
        var empId = cb.getAttribute('data-empid');
        if(empId) ids.push(empId);
      });
      var body =
        'cmd=updateProjectMemberAction'
        + '&projectNo=' + encodeURIComponent(PNO)
        + '&employeeIds=' + encodeURIComponent(ids.join(','));

      fetch(BASE, {
        method:'POST',
        headers:{'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'},
        body: body
      }).then(function(r){ return r.text(); })
        .then(function(){ location.href = BASE + '?cmd=tasksUI&projectNo=' + encodeURIComponent(PNO); })
        .catch(function(){ alert('저장에 실패했습니다.'); });
    }, false);
  }
})();