(function () {
  var CP   = (window.TaskPage && window.TaskPage.contextPath) || "";
  var BASE = (CP ? CP : "") + "/controller";

  function buildQS(params) {
    var arr = [];
    for (var k in params) {
      if (!params.hasOwnProperty(k)) continue;
      var v = params[k];
      if (v === undefined || v === null || v === "") continue;
      arr.push(encodeURIComponent(k) + "=" + encodeURIComponent(String(v)));
    }
    return arr.join("&");
  }
  function toStringArray(a) {
    if (!a) return [];
    if (Array.isArray(a)) return a.map(function (x) { return String(x); });
    return String(a).split(",").map(function (x) { return x.trim(); }).filter(Boolean);
  }
  function normalizeMode(m){
    m = String(m||'view').toLowerCase();
    return (m === 'edittask' || m === 'edit') ? 'edit' : 'view';
  }
  function setModalTitleFromBody(){
    var wrap = document.getElementById('task-members-modal');
    if(!wrap) return;
    var suf = wrap.querySelector('#tm-title-suffix');
    if(!suf) return;
    var nameEl = document.querySelector('#task-members-body #tm-taskName');
    var taskName = nameEl ? (nameEl.textContent || '').trim() : '';
    suf.textContent = taskName ? (' — ' + taskName) : '';
  }

  function setupEditDualList(body){
    var left  = body.querySelector('#tm-left');
    var right = body.querySelector('#tm-right');
    if (!left || !right) return;
    function renderLeft(){
      left.innerHTML = '';
      var checked = right.querySelectorAll('input.tm-check:checked');
      if (!checked.length){
        var empty = document.createElement('div');
        empty.className = 'small tm-muted';
        empty.textContent = '선택된 담당자가 없습니다.';
        left.appendChild(empty);
        return;
      }
      var seen = Object.create(null);
      Array.prototype.forEach.call(checked, function(cb){
        var id = String(cb.value);
        if (seen[id]) return; seen[id] = 1;
        var item = cb.closest('.tm-item') || cb.closest('.tm-card') || cb.closest('.member-card');
        if (!item) return;
        var clone = item.cloneNode(true);
        var cbs = clone.querySelectorAll('input[type="checkbox"]');
        for (var i=0;i<cbs.length;i++) cbs[i].remove();
        left.appendChild(clone);
      });
    }
    renderLeft();
    right.addEventListener('change', function(e){
      if (e.target && e.target.classList.contains('tm-check')) renderLeft();
    });
  }

  var Modal = {
    elModal:null, elBody:null, elApply:null, elClose:null, elBackdrop:null,

    ensure: function () {
      if (this.elModal) return;
      this.elModal   = document.getElementById("task-members-modal");
      if (!this.elModal) return;
      this.elBody     = document.getElementById("task-members-body");
      this.elApply    = document.getElementById("task-members-apply");
      this.elClose    = document.getElementById("task-members-close");
      this.elBackdrop = this.elModal.querySelector("[data-close]");

      var close = this.close.bind(this);
      if (this.elClose)    this.elClose.addEventListener("click", close, false);
      if (this.elBackdrop) this.elBackdrop.addEventListener("click", close, false);
      document.addEventListener("keydown", function (e) {
        if (e.key === "Escape") close();
      }, false);
    },

    open: function (modeRaw, projectNo, taskNo, presetIds, onApply) {
      this.ensure();
      if (!this.elModal || !this.elBody) return;

      var mode = normalizeMode(modeRaw);
      var pno  = String(projectNo || (window.TaskPage && window.TaskPage.projectNo) || "");
      var tno  = (taskNo!=null && taskNo!=="" && taskNo!=="undefined") ? String(taskNo) : null;

      if (!pno){ alert("유효한 프로젝트가 아닙니다."); return; }
      if ((mode === "edit" || mode === "view") && !tno){
        if (mode === 'view') { alert("유효하지 않은 업무입니다."); return; }
      }

      if (this.elApply){
        var edit = (mode === 'edit');
        this.elApply.classList.toggle('hidden', !edit);
        this.elApply.disabled = !edit;
        if (!edit) this.elApply.onclick = null;
      }

      this.elBody.innerHTML = '<div class="small" style="color:#777">불러오는 중...</div>';
      this.elModal.classList.remove("hidden");

      var qs = buildQS({ cmd:"taskMemberUI", mode:mode, projectNo:pno, taskNo:tno });

      var self = this;
      fetch(BASE + "?" + qs, {
        headers: { "X-Requested-With": "XMLHttpRequest" },
        credentials: "same-origin",
        cache: "no-store"
      })
      .then(function (r) { return r.text(); })
      .then(function (html) {
        self.elBody.innerHTML = html;

        setModalTitleFromBody();

        if (mode === "edit") {
          var serverPreset = (self.elBody.querySelector('#tm-preset')||{}).value || '';
          var merged = {};
          toStringArray(serverPreset).forEach(function(x){ merged[String(x)] = true; });
          toStringArray(presetIds).forEach(function(x){ merged[String(x)] = true; });
          var mergedArr = Object.keys(merged);

          if (mergedArr.length) {
            Array.prototype.forEach.call(
              self.elBody.querySelectorAll(".tm-check"),
              function (cb) { if (merged[String(cb.value)]) cb.checked = true; }
            );
          }
          setupEditDualList(self.elBody);

          if (self.elApply){
            self.elApply.onclick = function () {
              var ids = [], names = [];
              Array.prototype.forEach.call(
                self.elBody.querySelectorAll(".tm-check:checked"),
                function (cb) {
                  ids.push(String(cb.value)); // project_join_no
                  var row = cb.closest(".tm-item") || cb.closest(".tm-card") || cb.closest(".member-card");
                  var nmEl = row && row.querySelector(".name");
                  var nm = nmEl ? nmEl.textContent.trim() : "";
                  if (nm) names.push(nm);
                }
              );
              var text = "-";
              if (names.length) {
                text = names[0];
                if (names.length > 1) text += "+" + (names.length - 1);
              }
              if (typeof onApply === "function") onApply(ids, text);
              self.close();
            };
          }
        }
      })
      .catch(function () {
        self.elBody.innerHTML = '<div style="color:#c00">불러오기 실패</div>';
      });
    },

    close: function () {
      if (!this.elModal) return;
      this.elModal.classList.add("hidden");
    }
  };

  window.TaskMembersModal = {
    open: function (mode, projectNo, taskNo, presetIds, onApply) {
      Modal.open(mode, projectNo, taskNo, presetIds, onApply);
    },
    close: function () { Modal.close(); }
  };
})();