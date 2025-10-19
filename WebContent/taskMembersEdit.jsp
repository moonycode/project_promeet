<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="small" style="font-weight:900;margin-bottom:8px">업무 담당자 선택</div>

<c:if test="${empty projectMembers}">
  <div class="small" style="color:#777">이 프로젝트에 참여 중인 직원이 없습니다.</div>
</c:if>

<c:forEach var="m" items="${projectMembers}">
  <label class="tm-card" data-empid="${m.employeeId}">
    <input type="checkbox" class="tm-check" value="${m.employeeId}" data-name="${m.name}" style="margin-right:8px" />
    <div class="tm-avatar"><c:out value="${fn:substring(m.name,0,1)}"/></div>
    <div style="display:flex;flex-direction:column;gap:2px">
      <div class="tm-line1">
        <span class="name"><c:out value="${m.name}"/></span>
        <c:set var="dotClass"
               value="${m.workStatus eq '출근' ? 'green'
                       : m.workStatus eq '자리비움' ? 'yellow'
                       : m.workStatus eq '외근' ? 'purple'
                       : 'gray'}"/>
        <span class="tm-dot ${dotClass}" title="${m.workStatus}"></span>
      </div>
      <div class="tm-line2 small"><c:out value="${m.department}"/> / <c:out value="${m.position}"/></div>
      <div class="tm-line2 small" style="color:#777"><c:out value="${m.email}"/></div>
    </div>
  </label>
</c:forEach>

<c:if test="${not empty taskMemberIds}">
  <script>
    // 서버가 내려준 현재 업무 담당자 id들을 data-attr로 두고, 모달 JS가 없을 경우 대비
    (function(){
      var set = new Set(String('${taskMemberIds}').split(',').filter(Boolean));
      document.querySelectorAll('#task-members-body .tm-check').forEach(function(cb){
        if (set.has(String(cb.value))) cb.checked = true;
      });
    })();
  </script>
</c:if>
