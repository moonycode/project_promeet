<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="projectNo" value="${empty requestScope.projectNo ? param.projectNo : requestScope.projectNo}" />
<c:set var="managerId" value="${requestScope.managerId}" />
<c:set var="employees" value="${requestScope.employees}" />

<!doctype html>
<html lang="ko">
<head>          
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>프로젝트 참여 인원 조회 / 수정</title>
  <!-- CSS 위치: /CSS/common.css -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/common.css" />
  <style>
    .mgr-actions .btn.is-lg { height:44px; padding:10px 16px; border-radius:12px; }
  </style>
</head>
<body class="pm-manager">
<div class="container">
<%@ include file="/WEB-INF/jsp/inc/sidebar.jspf" %>

  <div class="main">
    <h1 class="page-title">프로젝트 참여 인원 조회 / 수정</h1>

    <form method="post"
          action="${pageContext.request.contextPath}/controller?cmd=updateProjectMembers"
          id="memberForm"
          data-project-no="${projectNo}">
      <input type="hidden" name="projectNo" value="${projectNo}"/>

      <div class="manager-wrap">
        <!-- 좌측: 현재 참여 인원 -->
        <section class="panel">
          <h3 class="panel-title">프로젝트 참여 인원</h3>
          <div class="listbox">
            <c:choose>
              <c:when test="${not empty joinedMembers}">
                <c:forEach var="e" items="${joinedMembers}">
                  <c:if test="${e.joinFlag == 1}">
                    <div class="member-card no-check">
                      <label class="card-body">
                        <span class="line1">
                          <span class="name">${e.name} ${e.position}</span>
                          <span class="s-dot green"></span>
                        </span>
                        <span class="line2 small">${e.department}</span>
                        <span class="line3 small">${e.phoneNumber}</span>
                        <span class="line3 small">${e.email}</span>
                      </label>
                    </div>
                  </c:if>
                </c:forEach>
              </c:when>
              <c:otherwise>
                <div class="small">참여 인원 데이터가 없습니다.</div>
              </c:otherwise>
            </c:choose>
          </div>
        </section>

        <!-- 우측: 직원 리스트(체크 토글) -->
        <section class="panel">
          <h3 class="panel-title">직원 리스트</h3>
          <div class="listbox" id="employeeList">
            <c:choose>
              <c:when test="${not empty employees}">
                <c:forEach var="e" items="${employees}" varStatus="st">
                  <c:set var="id" value="emp-${st.index}" />
                  <c:set var="checked" value="${e.joinFlag == 1}" />
                  <c:set var="isManager" value="${not empty managerId and managerId == e.employeeId}" />

                  <!-- 근무 상태 점 색상 -->
                  <c:set var="dotClass" value="gray" />
                  <c:choose>
                    <c:when test="${e.workStatus == '출근'}"><c:set var="dotClass" value="green" /></c:when>
                    <c:when test="${e.workStatus == '외근'}"><c:set var="dotClass" value="purple" /></c:when>
                  </c:choose>

                  <div class="member-card js-card" data-cb-id="${id}">
                    <input type="checkbox"
                           id="${id}"
                           name="employeeId"
                           value="${e.employeeId}"
                           <c:if test="${checked}">checked</c:if>
                           <c:if test="${isManager}">disabled</c:if> />
                    <label class="card-body" for="${id}">
                      <span class="line1">
                        <span class="name">${e.name} ${e.position}</span>
                        <span class="s-dot ${dotClass}"></span>
                        <c:if test="${isManager}">
                          <span class="small" style="margin-left:6px;color:#a66;">(매니저)</span>
                        </c:if>
                      </span>
                      <span class="line2 small">${e.department}</span>
                      <span class="line3 small">${e.phoneNumber}</span>
                      <span class="line3 small">${e.email}</span>
                    </label>

                    <!-- disabled 체크박스는 전송 안 되므로, 매니저는 hidden으로 강제 포함 -->
                    <c:if test="${isManager}">
                      <input type="hidden" name="employeeId" value="${e.employeeId}" />
                    </c:if>
                  </div>
                </c:forEach>
              </c:when>
              <c:otherwise>
                <div class="small">직원 데이터가 없습니다.</div>
              </c:otherwise>
            </c:choose>
          </div>
        </section>
      </div>

      <div class="actions-right mgr-actions">
        <button type="button" class="btn btn-outline is-lg" id="btnCancel">취소</button>
        <button type="submit" class="btn btn-primary is-lg">저장</button>
      </div>
    </form>
  </div>
</div>

<!-- 컨텍스트 경로를 안전하게 전역 변수로 노출 -->
<script>
  window.CTX = '${pageContext.request.contextPath}';
</script>
<!-- JS 위치: /JS/projectMembers.js -->
<script src="${pageContext.request.contextPath}/JS/projectMembers.js"></script>
</body>
</html>
