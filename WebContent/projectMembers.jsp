<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>프로젝트 참여 인원 조회 / 수정</title>
  <link rel="stylesheet" href="<c:url value='/CSS/common.css'/>" />
</head>
<body class="pm-manager">
  <div class="container">
    <%@ include file="Jspf/sidebar.jspf" %>

    <div class="main">
      <h1 class="page-title">
        <span><c:out value="${project.projectName}"/></span>
      </h1>

      <div class="manager-wrap">
        <!-- 좌측: 현재 참여 인원(미리보기) -->
        <section class="panel">
          <h3 class="panel-title">프로젝트 참여 인원</h3>
          <div class="listbox" id="leftList">
            <c:forEach var="m" items="${leftList}">
              <div class="member-card no-check">
                <label class="card-body">
                  <span class="line1"><span class="name"><c:out value="${m.name}"/></span>
                    <c:set var="dotClass"
                           value="${m.workStatus eq '출근' ? 'green'
                                   : m.workStatus eq '자리비움' ? 'yellow'
                                   : m.workStatus eq '외근' ? 'purple'
                                   : 'gray'}"/>
                    <span class="s-dot ${dotClass}"></span>
                  </span>
                  <span class="line2 small"><c:out value="${m.department}"/></span>
                  <span class="line3 small"><c:out value="${m.phoneNumber}"/></span>
                  <span class="line3 small"><c:out value="${m.email}"/></span>
                </label>
              </div>
            </c:forEach>
          </div>
        </section>

        <!-- 우측: 직원 리스트(체크) -->
        <section class="panel">
          <h3 class="panel-title">직원 리스트</h3>
          <div class="listbox" id="rightList">
            <c:forEach var="r" items="${rightList}" varStatus="st">
              <div class="member-card">
                <input type="checkbox" id="r-${st.index}" class="pmgr-check"
                       data-empid="${r.employeeId}"
                       <c:if test="${r.joinFlag == 1}">checked</c:if>
                       <c:if test="${r.managerFlag == 1}">disabled</c:if> />
                <label class="card-body" for="r-${st.index}">
                  <span class="line1">
                    <span class="name"><c:out value="${r.name}"/></span>
                    <c:choose>
                      <c:when test="${r.workStatus eq '출근'}"><span class="s-dot green"></span></c:when>
                      <c:when test="${r.workStatus eq '자리비움'}"><span class="s-dot yellow"></span></c:when>
                      <c:when test="${r.workStatus eq '외근'}"><span class="s-dot purple"></span></c:when>
                      <c:otherwise><span class="s-dot gray"></span></c:otherwise>
                    </c:choose>
                    <c:if test="${r.managerFlag == 1}"><span class="small">&nbsp;(팀장)</span></c:if>
                  </span>
                  <span class="line2 small"><c:out value="${r.department}"/></span>
                  <span class="line3 small"><c:out value="${r.phoneNumber}"/></span>
                  <span class="line3 small"><c:out value="${r.email}"/></span>
                </label>
              </div>
            </c:forEach>
          </div>
        </section>
      </div>

      <div class="actions-right mgr-actions">
        <a class="btn btn-outline is-lg" href="<c:url value='/controller?cmd=tasksUI&amp;projectNo=${param.projectNo}'/>">취소</a>
        <button class="btn btn-primary is-lg" id="btnSave">저장</button>
      </div>
    </div>
  </div>

  <script>
    window.ProjectMembers = {
      contextPath: '<c:out value="${pageContext.request.contextPath}" />',
      projectNo:   <c:out value="${param.projectNo}" default="0"/>
    };
  </script>
  <script src="<c:url value='/JS/projectMembers.js'/>"></script>
</body>
</html>
