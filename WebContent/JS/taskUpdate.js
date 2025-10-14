// taskEdit.js - ProMeet 업무 추가/수정 화면 스크립트 (ES5 호환 버전)
(function () {
  // ---- 안전한 값 가져오기 ----
  var TaskEdit = window.TaskEdit || {};
  var baseUrl   = TaskEdit.baseUrl || "controller";
  var projectNo = TaskEdit.projectNo || "";
  var isEdit    = !!TaskEdit.isEdit;
  var taskNo    = TaskEdit.taskNo || "";

  var form            = document.getElementById("taskEditForm");
  var btnConfirm      = document.getElementById("btn-confirm");
  var btnCancel       = document.getElementById("btn-cancel");
  var btnGoAdd        = document.getElementById("btn-go-add");
  var btnEditMembers  = document.getElementById("btn-edit-members");
  var assigneeChip    = document.getElementById("assigneeChip");

  var hiddenLeader = document.getElementById("leaderName");
  var hiddenCnt    = document.getElementById("memberCnt");
  var hiddenIds    = document.getElementById("memberIds");

  // ---- 유틸: 쿼리스트링 만들기 ----
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

  // ---- 목록으로 이동 ----
  function goTasksList(extra) {
    extra = extra || {};
    var params = {
      cmd: "tasksUI",
      projectNo: projectNo
    };
    for (var k in extra) {
      if (extra.hasOwnProperty(k)) params[k] = extra[k];
    }
    location.href = baseUrl + "?" + buildQS(params);
  }

  // ---- 담당자 편집 ----
  if (btnEditMembers) {
    btnEditMembers.onclick = function () {
      var params = {
        cmd: "taskMembersSelectUI",
        projectNo: projectNo
      };
      if (isEdit) params.taskNo = taskNo;
      if (hiddenIds && hiddenIds.value) params.selected = hiddenIds.value;
      location.href = baseUrl + "?" + buildQS(params);
    };
  }

  // ---- 확인(추가/수정) ----
  if (btnConfirm) {
    btnConfirm.onclick = function () {
      if (!form) return;
      // 간단 유효성 검사
      var taskNameInput = form.querySelector("input[name='taskName']");
      var taskName = taskNameInput ? taskNameInput.value : "";
      if (!taskName || !taskName.replace(/\s+/g, "")) {
        alert("업무명을 입력하세요.");
        return;
      }

      // 폼 값 수집 (ES5 방식)
      var inputs = form.querySelectorAll("input, select, textarea");
      var params = {};
      for (var i = 0; i < inputs.length; i++) {
        var el = inputs[i];
        if (!el.name) continue;
        // 체크박스/라디오 고려(여기선 사용 X)
        if ((el.type === "checkbox" || el.type === "radio") && !el.checked) continue;
        params[el.name] = el.value;
      }

      // 서버로 GET 전송 (Action에서 처리 후 리다이렉트)
      location.href = baseUrl + "?" + buildQS(params);
    };
  }

  // ---- 취소 ----
  if (btnCancel) {
    btnCancel.onclick = function () {
      if (confirm("변경사항이 저장되지 않습니다. 목록으로 돌아갈까요?")) {
        goTasksList();
      }
    };
  }

  // ---- + 업무추가 ----
  if (btnGoAdd) {
    btnGoAdd.onclick = function () {
      var params = { cmd: "taskEditUI", projectNo: projectNo };
      location.href = baseUrl + "?" + buildQS(params);
    };
  }

  // ---- 담당자 칩 동기화 ----
  function syncChip() {
    var name = hiddenLeader ? hiddenLeader.value : "-";
    var cnt  = hiddenCnt ? parseInt(hiddenCnt.value || "0", 10) : 0;

    if (!assigneeChip) return;

    // 안쪽 초기화
    while (assigneeChip.firstChild) assigneeChip.removeChild(assigneeChip.firstChild);

    assigneeChip.appendChild(document.createTextNode(name || "-"));
    if (cnt > 1) {
      var span = document.createElement("span");
      span.className = "count";
      span.appendChild(document.createTextNode("+" + (cnt - 1)));
      assigneeChip.appendChild(span);
    }
  }
  syncChip();
})();
