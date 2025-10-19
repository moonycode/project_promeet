<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="small" style="font-weight:900;margin-bottom:8px">담당자 (읽기전용)</div>

<c:if test="${empty members}">
  <div class="small" style="color:#777">참여 중인 담당자가 없습니다.</div>
</c:if>

<c:forEach var="m" items="${members}">
  <div class="tm-card" data-empid="${m.employeeId}">
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
  </div>
</c:forEach>
