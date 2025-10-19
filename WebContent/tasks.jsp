<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>업무 목록</title>
  <!-- 공통 CSS -->
  <link rel="stylesheet" href="<c:url value='/CSS/common.css'/>" />
</head>
<body class="pm-tasks">
  <div class="container">
    <%@ include file="Jspf/sidebar.jspf" %>

    <div class="main">
      <!-- 완료/삭제된 프로젝트면 읽기 전용 -->
      <c:set var="readonly" value="${not empty project.completeDate or not empty project.delDate}" />

      <!-- 헤더 -->
      <div class="page-header">
        <div class="page-title">
          <span><c:out value="${project.projectName}" /></span>
          <span class="date-range">
            <fmt:formatDate value="${project.startDate}" pattern="yyyy-MM-dd" /> ~
            <fmt:formatDate value="${project.endDate}"   pattern="yyyy-MM-dd" />
          </span>
        </div>

        <!-- 멤버 아이콘 / 비율 -->
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

      <!-- 설명 + 액션 -->
      <div class="desc-wrap">
        <div class="desc-box">
          <c:out value="${project.description}" default="" />
        </div>

        <c:choose>
          <c:when test="${readonly}">
            <span class="btn-outline" id="btn-restore" data-projectno="${project.projectNo}">프로젝트 복원</span>
          </c:when>
          <c:otherwise>
            <a class="btn-outline" href="<c:url value='/controller'/>?cmd=updateProjectUI&amp;projectNo=${project.projectNo}">프로젝트 수정</a>
          </c:otherwise>
        </c:choose>
      </div>

      <!-- 업무 테이블 -->
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

              <!-- 진행상태 필터 -->
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

              <!-- 시작일 정렬 -->
              <th class="th-sort">
                <span class="th-text">시작일</span>
                <span class="sort-inline">
                  <span class="btn-sort" id="s-start-asc"  role="button" aria-pressed="false" tabindex="0">▲</span>
                  <span class="btn-sort" id="s-start-desc" role="button" aria-pressed="false" tabindex="0">▼</span>
                </span>
              </th>

              <!-- 마감일 정렬 -->
              <th class="th-sort">
                <span class="th-text">마감일</span>
                <span class="sort-inline">
                  <span class="btn-sort" id="s-end-asc"  role="button" aria-pressed="false" tabindex="0">▲</span>
                  <span class="btn-sort" id="s-end-desc" role="button" aria-pressed="false" tabindex="0">▼</span>
                </span>
              </th>

              <!-- 우선순위 필터 -->
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
            <!-- 비어있을 때 -->
            <c:if test="${empty tasks}">
              <tr class="empty"><td colspan="8">등록된 업무가 없습니다.</td></tr>
            </c:if>

            <!-- 리스트 -->
            <c:forEach var="t" items="${tasks}">
              <tr data-taskno="${t.taskNo}">
                <td>
                  <a href="<c:url value='/controller' />?cmd=detailTaskUI&amp;taskNo=${t.taskNo}&amp;projectNo=${project.projectNo}"
                     class="task-title-link">
                    <c:out value="${t.taskName}" />
                  </a>
                </td>

                <td>
                  <c:choose>
                    <c:when test="${empty t.membersText}">
                      <span>-</span>
                    </c:when>
                    <c:otherwise>
                      <!-- 읽기 화면에서 클릭 → 보기 모달 -->
                      <span class="chip member-chip-view" data-taskno="${t.taskNo}">
                        <c:out value="${t.membersText}" />
                      </span>
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

      <!-- 하단 ‘업무추가’ 버튼 -->
      <c:if test="${not readonly}">
        <div style="margin-top:10px;">
          <span class="btn-outline" id="btn-add">+ 업무추가</span>
        </div>
      </c:if>
    </div>
  </div>

  <!-- 전역값 주입 -->
  <script>
    window.TaskPage = {
      contextPath: '<c:out value="${pageContext.request.contextPath}" default=""/>',
      projectNo:   <c:out value="${project.projectNo}" default="0" />,
      readonly:    <c:out value="${readonly ? 'true' : 'false'}" default="false" />
    };
  </script>

  <!-- 모달 마크업 -->
  <%@ include file="Jspf/taskMembersModal.jspf" %>

  <!-- JS -->
  <script src="<c:url value='/JS/taskMembersModal.js'/>" type="text/javascript" defer></script>
  <script src="<c:url value='/JS/tasks.js'/>" type="text/javascript" defer></script>
</body>
</html>
