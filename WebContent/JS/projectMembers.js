(function(){
  var CP   = (window.ProjectMembers && window.ProjectMembers.contextPath) || '';
  var BASE = (CP ? CP : '') + '/controller';
  var PNO  = (window.ProjectMembers && window.ProjectMembers.projectNo) || 0;

  var left = document.getElementById('leftList');
  var right= document.getElementById('rightList');
  if(!left || !right) return;

  function rebuildLeftPreview(){
    var checked = right.querySelectorAll('.pmgr-check:checked');
    var arr = Array.prototype.map.call(checked, function(cb){
      var card = cb.closest('.member-card');
      var clone = card.cloneNode(true);
      clone.classList.add('no-check');
      var inp = clone.querySelector('input[type="checkbox"]');
      if(inp) inp.remove();
      return clone;
    });
    left.innerHTML = '';
    arr.forEach(function(node){ left.appendChild(node); });
  }

  rebuildLeftPreview();

  right.addEventListener('change', function(e){
    var t = e.target;
    if(t.classList.contains('pmgr-check')){
      if(t.disabled) return;
      rebuildLeftPreview();
    }
  }, false);

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
        'cmd=updateProjectMembers'
        + '&projectNo=' + encodeURIComponent(PNO)
        + '&employeeIds=' + encodeURIComponent(ids.join(','));

      fetch(BASE, {
        method:'POST',
        headers:{'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'},
        credentials:'same-origin',
        body: body
      }).then(function(){ location.href = BASE + '?cmd=tasksUI&projectNo=' + encodeURIComponent(PNO); })
        .catch(function(){ alert('저장에 실패했습니다.'); });
    }, false);
  }
})();