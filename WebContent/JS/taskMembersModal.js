(function(){
  var CP   = (window.TaskPage && window.TaskPage.contextPath) || '';
  var BASE = (CP ? CP : '') + '/controller';

  var Modal = {
    elModal:null, elBody:null, elApply:null, elClose:null, elBackdrop:null,
    ensure: function(){
      if(this.elModal) return;
      this.elModal = document.getElementById('task-members-modal');
      if(!this.elModal) return;
      this.elBody  = document.getElementById('task-members-body');
      this.elApply = document.getElementById('task-members-apply');
      this.elClose = document.getElementById('task-members-close');
      this.elBackdrop = this.elModal.querySelector('[data-close]');
      var close = this.close.bind(this);
      if(this.elClose) this.elClose.addEventListener('click', close, false);
      if(this.elBackdrop) this.elBackdrop.addEventListener('click', close, false);
      document.addEventListener('keydown', function(e){
        if(e.key === 'Escape') close();
      }, false);
    },
    open: function(mode, projectNo, taskNo, presetIds, onApply){
      this.ensure();
      if(!this.elModal || !this.elBody) return;
      this.elBody.innerHTML = '<div class="small" style="color:#777">불러오는 중...</div>';
      this.elModal.classList.remove('hidden');

      var url = BASE + '?cmd=taskMemberUI&mode=' + encodeURIComponent(mode)
              + '&projectNo=' + encodeURIComponent(projectNo)
              + '&taskNo=' + encodeURIComponent(taskNo);

      fetch(url, { headers:{'X-Requested-With':'XMLHttpRequest'} })
        .then(function(r){ return r.text(); })
        .then(function(html){
          Modal.elBody.innerHTML = html;
          if(mode === 'edit'){
            var set = new Set((presetIds || []).map(String));
            Array.prototype.forEach.call(Modal.elBody.querySelectorAll('.tm-check'), function(cb){
              if(set.has(String(cb.value))) cb.checked = true;
            });
            if(Modal.elApply) Modal.elApply.onclick = function(){
              var ids=[]; var names=[];
              Array.prototype.forEach.call(Modal.elBody.querySelectorAll('.tm-check:checked'), function(cb){
                ids.push(cb.value);
                var card = cb.closest('.tm-card');
                var nm = card && card.querySelector('.name') ? card.querySelector('.name').textContent.trim() : '';
                if(nm) names.push(nm);
              });
              var text = '-';
              if(names.length > 0){
                text = names[0];
                if(names.length > 1) text += '+' + (names.length-1);
              }
              if(typeof onApply === 'function') onApply(ids, text);
              Modal.close();
            };
          } else {
            // view 모드: 적용 버튼 숨김
            if(Modal.elApply) Modal.elApply.onclick = null;
          }
        })
        .catch(function(){ Modal.elBody.innerHTML = '<div style="color:#c00">불러오기 실패</div>'; });
    },
    close: function(){
      if(!this.elModal) return;
      this.elModal.classList.add('hidden');
    }
  };

  window.TaskMembersModal = {
    open: Modal.open.bind(Modal),
    close: Modal.close.bind(Modal)
  };
})();