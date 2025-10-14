// JS/projectMembers.js
(function () {
  "use strict";

  // 컨텍스트 경로: JSP에서 window.CTX로 이미 주입됨
  var CTX = (typeof window.CTX === 'string') ? window.CTX : '';

  var form = document.getElementById('memberForm');
  var list = document.getElementById('employeeList');
  var btnCancel = document.getElementById('btnCancel');

  // 카드 클릭으로 체크 토글 (버블링 위임)
  if (list) {
    list.addEventListener('click', function (e) {
      var card = e.target.closest('.js-card');
      if (!card) return;

      // label 클릭은 브라우저 기본(label-for) 동작 사용
      if (e.target.tagName && e.target.tagName.toLowerCase() === 'label') {
        return;
      }
      // input 직접 클릭 시도는 그대로 둠
      if (e.target.tagName && e.target.tagName.toLowerCase() === 'input') {
        return;
      }

      var id = card.getAttribute('data-cb-id');
      if (!id) return;

      var cb = document.getElementById(id);
      if (cb && !cb.disabled) {
        cb.checked = !cb.checked;
      }
    });
  }

  // 취소 → 프로젝트 업무 화면으로 이동 (필요 시 cmd 이름 수정)
  if (btnCancel && form) {
    btnCancel.addEventListener('click', function () {
      var pno = form.getAttribute('data-project-no');
      if (!pno) {
        alert('projectNo가 없습니다.');
        return;
      }
      // 당신의 라우팅에 맞게 cmd 수정하세요. (예: tasksUI / projectDetailUI 등)
      var url = CTX + '/controller?cmd=tasksUI&projectNo=' + encodeURIComponent(pno);
      window.location.href = url;
    });
  }

  // 폼 전송 시 간단 검증(선택 사항)
  if (form) {
    form.addEventListener('submit', function () {
      // 특수 검증이 필요하면 여기에 추가
      // 예: managerId가 반드시 포함되는지 확인 등 (현재는 hidden 처리로 포함됨)
    });
  }
})();
