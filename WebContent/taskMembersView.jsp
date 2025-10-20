<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- 상단 소제목 라인 -->
<div class="tm-subtitle">
  <c:if test="${not empty taskName}">
    <span class="tm-subtitle-suffix"><c:out value="${taskName}"/></span>
  </c:if>
</div>

<c:if test="${empty members}">
  <div class="small" style="color:#777">참여 중인 담당자가 없습니다.</div>
</c:if>

<c:forEach var="m" items="${members}">
  <div class="tm-card" data-empid="${m.employeeId}">
    <div class="tm-avatar"><c:out value="${fn:substring(m.name,0,1)}"/></div>
    <div class="tm-card-meta">
      <div class="tm-line1">
        <span class="name"><c:out value="${m.name}"/></span>

        <c:set var="__ws" value="${empty m.workStatus ? '' : m.workStatus}"/>
        <c:set var="dotClass"
               value="${__ws eq '출근' ? 'green'
                       : __ws eq '자리비움' ? 'yellow'
                       : __ws eq '외근' ? 'purple'
                       : 'gray'}"/>
        <span class="tm-dot ${dotClass}" title="${__ws}"></span>
      </div>

      <div class="tm-line2 small">
        <c:out value="${m.department}"/> / <c:out value="${m.position}"/>
      </div>
      <div class="tm-line2 small tm-muted">
        <c:out value="${m.email}"/>
      </div>
    </div>
  </div>
</c:forEach>
