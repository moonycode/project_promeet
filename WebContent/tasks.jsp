<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>업무 목록</title>
  <link rel="stylesheet" href="<c:url value='/CSS/common.css'/>" />
</head>
<body class="pm-tasks">
  <div class="container">
    <%@ include file="Jspf/sidebar.jspf" %>

    <div class="main">
      <c:set var="readonly" value="${not empty project.completeDate or not empty project.delDate}" />

      <div class="page-header">
        <div class="page-title">
          <span><c:out value="${project.projectName}" /></span>
          <span class="date-range">
            <fmt:formatDate value="${project.startDate}" pattern="yyyy-MM-dd" /> ~
            <fmt:formatDate value="${project.endDate}"   pattern="yyyy-MM-dd" />
          </span>
        </div>

        <div class="member-chip">
          <a href="<c:url value='/controller' />?cmd=projectMembersUI&amp;projectNo=${project.projectNo}"
             class="avatar-s" aria-label="프로젝트 멤버 보기">
            <img
              src="data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='120' height='120'><defs><linearGradient id='g' x1='0' x2='1'><stop offset='0' stop-color='%23ffd1dc'/><stop offset='1' stop-color='%23c7b9ff'/></linearGradient></defs><rect width='100%' height='100%' fill='url(%23g)'/><circle cx='60' cy='50' r='22' fill='white'/><rect x='30' y='78' width='60' height='26' rx='13' fill='white'/></svg>"
              alt="멤버 아이콘" />
          </a>
          <a href="<c:url value='/controller' />?cmd=projectMembersUI&amp;projectNo=${project.projectNo}" class="member-ratio">
            <c:out value="${project.workingCount}" />/<c:out value="${project.memberCount}" />
          </a>
        </div>
      </div>

      <div class="desc-wrap">
        <div class="desc-box">
          <c:out value="${project.description}" default="" />
        </div>
</div>
     <div class="header-actions">
 <c:choose>
  <c:when test="${isDeletedProject}">
    <button type="button" id="btn-restore" data-projectno="${project.projectNo}" class="btn">프로젝트 복원</button>
  </c:when>

  <c:when test="${isCompletedProject}">
    <button type="button" id="btn-reopen" data-projectno="${project.projectNo}" class="btn">프로젝트 재개</button>
  </c:when>

  <c:otherwise>
    <button type="button" id="btn-edit-project" data-projectno="${project.projectNo}" class="btn">프로젝트 수정</button>
    <!-- 그 외 완료/삭제 버튼 등 기존 버튼들 -->
  </c:otherwise>
</c:choose>
</div>


      <div class="task-wrap">
        <table class="task-table">
          <colgroup>
            <col class="col-name" />
            <col class="col-members" />
            <col class="col-status" />
            <col class="col-start" />
            <col class="col-end" />
            <col class="col-priority" />
            <col class="col-progress" />
            <col class="col-actions" />
          </colgroup>

          <thead>
            <tr>
              <th>업무명</th>
              <th>담당자</th>

              <th class="th-filter">
                <div class="th-wrap">
                  <span class="th-label" tabindex="0" aria-expanded="false">진행상태 ▾</span>
                  <div class="dd-panel">
                    <div class="row">
                      <span class="lbl">선택</span>
                      <select class="select-s" id="f-status" aria-label="상태 필터">
                        <option value="ALL">전체</option>
                        <option value="DONE">완료</option>
                        <option value="PROGRESS">진행</option>
                        <option value="WAIT">대기</option>
                        <option value="HOLD">보류</option>
                        <option value="ETC">기타</option>
                      </select>
                    </div>
                  </div>
                </div>
              </th>

              <th class="th-sort">
                <span class="th-text">시작일</span>
                <span class="sort-inline">
                  <span class="btn-sort" id="s-start-asc"  role="button" aria-pressed="false" tabindex="0">▲</span>
                  <span class="btn-sort" id="s-start-desc" role="button" aria-pressed="false" tabindex="0">▼</span>
                </span>
              </th>

              <th class="th-sort">
                <span class="th-text">마감일</span>
                <span class="sort-inline">
                  <span class="btn-sort" id="s-end-asc"  role="button" aria-pressed="false" tabindex="0">▲</span>
                  <span class="btn-sort" id="s-end-desc" role="button" aria-pressed="false" tabindex="0">▼</span>
                </span>
              </th>

              <th class="th-filter">
                <div class="th-wrap">
                  <span class="th-label" tabindex="0" aria-expanded="false">우선순위 ▾</span>
                  <div class="dd-panel">
                    <div class="row">
                      <span class="lbl">선택</span>
                      <select class="select-s" id="f-priority" aria-label="우선순위 필터">
                        <option value="ALL">전체</option>
                        <option value="EMERGENCY">긴급</option>
                        <option value="HIGH">높음</option>
                        <option value="NORMAL">보통</option>
                        <option value="LOW">낮음</option>
                        <option value="NONE">없음</option>
                      </select>
                    </div>
                  </div>
                </div>
              </th>

              <th>진척도</th>
              <th>&nbsp;</th>
            </tr>
          </thead>

          <tbody id="taskBody">
            <c:if test="${empty tasks}">
              <tr class="empty"><td colspan="8">등록된 업무가 없습니다.</td></tr>
            </c:if>

            <c:forEach var="t" items="${tasks}">
              <tr data-taskno="${t.taskNo}">
                <td>
                  <a href="<c:url value='/controller' />?cmd=detailTaskUI&amp;taskNo=${t.taskNo}&amp;projectNo=${project.projectNo}"
                     class="task-title-link">
                    <c:out value="${t.taskName}" />
                  </a>
                </td>

                <td class="td-members">
                  <c:choose>
                    <c:when test="${empty t.membersText}">
                      <span>-</span>
                    </c:when>
                    <c:otherwise>
                      <a href="#" class="chip member-chip-view"><c:out value="${t.membersText}"/></a>
                    </c:otherwise>
                  </c:choose>
                </td>

                <td><c:out value="${t.taskStatus}" default="" /></td>
                <td><fmt:formatDate value="${t.startDate}" pattern="yyyy-MM-dd" /></td>
                <td><fmt:formatDate value="${t.endDate}"   pattern="yyyy-MM-dd" /></td>
                <td><c:out value="${t.priority}" default="" /></td>

                <td class="progress-cell"><c:out value="${t.progress}" />%</td>

                <td class="actions-cell">
                  <c:choose>
                    <c:when test="${not readonly}">
                      <span class="btn-xs btn-edit">수정</span>
                      <span class="btn-xs btn-del" data-taskno="${t.taskNo}">삭제</span>
                    </c:when>
                    <c:otherwise>
                      <span class="btn-xs ghost">수정</span>
                      <span class="btn-xs ghost">삭제</span>
                    </c:otherwise>
                  </c:choose>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>

      <c:if test="${not readonly}">
        <div style="margin-top:10px;">
          <span class="btn-outline" id="btn-add">+ 업무추가</span>
        </div>
      </c:if>
    </div>
  </div>

  <script>
    window.TaskPage = {
      contextPath: '<c:out value="${pageContext.request.contextPath}" default=""/>',
      projectNo:   <c:out value="${project.projectNo}" default="0" />,
      readonly:    JSON.parse('<c:out value="${readonly}" default="false"/>')
    };
  </script>

  <%@ include file="Jspf/taskMembersModal.jspf" %>
  <script src="<c:url value='/JS/taskMembersModal.js'/>" type="text/javascript" defer></script>
  <script src="<c:url value='/JS/tasks.js'/>" type="text/javascript" defer></script>
</body>
</html>
