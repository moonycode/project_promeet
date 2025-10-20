<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="tm-subtitle">
  <span class="tm-subtitle-label">담당자 선택</span>
  <c:if test="${not empty taskName}">
    <span class="tm-subtitle-suffix"> — <c:out value="${taskName}"/></span>
  </c:if>
</div>

<div class="tm-wrap">
  <div class="tm-col">
    <div class="tm-col-head"><div class="tm-col-title">업무 참여 인원</div></div>
    <div class="tm-list" id="tm-left"></div>
  </div>

  <div class="tm-col">
    <div class="tm-col-head"><div class="tm-col-title">프로젝트 참여 인원</div></div>
    <div class="tm-list" id="tm-right">
      <c:forEach var="m" items="${projectMembers}">
        <label class="tm-item" data-pjoin="${m.projectJoinNo}">
          <input type="checkbox" class="tm-check" value="${m.projectJoinNo}"/>
          <div class="tm-avatar"><c:out value="${fn:substring(m.name,0,1)}"/></div>
          <div class="tm-item-meta">
            <div class="tm-line1">
              <span class="name"><c:out value="${m.name}"/></span>
              <c:set var="__ws" value="${m.workStatus}" />
              <c:if test="${empty __ws}"><c:set var="__ws" value=""/></c:if>
              <c:set var="dotClass" value="${__ws eq '출근' ? 'green' : __ws eq '자리비움' ? 'yellow' : __ws eq '외근' ? 'purple' : 'gray'}"/>
              <span class="tm-dot ${dotClass}" title="${__ws}"></span>
            </div>
            <div class="tm-line2 small"><c:out value="${m.department}"/> / <c:out value="${m.position}"/></div>
            <div class="tm-line2 small tm-muted"><c:out value="${m.email}"/></div>
            <div class="tm-line2 small tm-muted"><c:out value="${m.phoneNumber}"/></div>
          </div>
        </label>
      </c:forEach>
    </div>
  </div>
</div>

<input type="hidden" id="tm-preset" value="<c:out value='${taskMemberIds}' default=''/>"/>
