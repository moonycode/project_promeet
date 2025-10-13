<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
/*
  기대하는 request 속성/파라미터
  - requestScope.projectNo (필수)
  - requestScope.task (수정 모드일 때): taskNo, taskName, taskStatus, startDate, endDate, priority
  - param.leaderName, param.memberCnt, param.memberIds  ← 담당자 선택 페이지에서 돌아올 때 전달
*/
%>
<c:set var="projectNo" value="${empty requestScope.projectNo ? param.projectNo : requestScope.projectNo}" />
<c:set var="task" value="${requestScope.task}" />
<c:set var="isEdit" value="${not empty task}" />
<c:set var="leaderName" value="${empty param.leaderName ? (empty task.leaderName ? '-' : task.leaderName) : param.leaderName}" />
<c:set var="memberCnt"  value="${empty param.memberCnt  ? (empty task.memberCount ? 0 : task.memberCount) : param.memberCnt}" />
<c:set var="memberIds"  value="${empty param.memberIds  ? task.memberIds : param.memberIds}" />
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>프로젝트 업무 ${isEdit ? '수정' : '추가'} - ProMeet</title>
  <link rel="stylesheet" href="./CSS/common.css" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
</head>
<body>
  <div class="container">
    <!-- Sidebar -->
    <div class="sidebar">
      <div class="logo-wrap">
        <img src="data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='240' height='60'><rect width='100%' height='100%' rx='12' ry='12' fill='%23b14dd6'/><text x='50%' y='55%' dominant-baseline='middle' text-anchor='middle' font-family='Arial' font-size='28' fill='white'>ProMeet</text></svg>" alt="ProMeet 로고" />
      </div>
      <div class="avatar">
        <img src="data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='120' height='120'><defs><linearGradient id='g' x1='0' x2='1'><stop offset='0' stop-color='%23ffd1dc'/><stop offset='1' stop-color='%23c7b9ff'/></linearGradient></defs><rect width='100%' height='100%' fill='url(%23g)'/><circle cx='60' cy='50' r='22' fill='white'/><rect x='30' y='78' width='60' height='26' rx='13' fill='white'/></svg>" alt="프로필 이미지(가상)" />
      </div>
      <div class="username"><c:out value="${sessionScope.loginName != null ? sessionScope.loginName : '홍길동 팀장'}"/></div>

      <div class="status-board">
        <div class="row">
          <label for="status">상태</label>
          <select id="status" name="status">
            <option>출근</option>
            <option>자리비움</option>
            <option>외근</option>
            <option>퇴근</option>
          </select>
        </div>
      </div>

      <nav class="nav">
        <a class="active" href="controller?cmd=projectsUI">프로젝트</a>
        <a href="controller?cmd=filesUI">파일함</a>
        <a href="controller?cmd=schedulesUI">일정관리</a>
        <a href="controller?cmd=myPageUI">마이페이지</a>
      </nav>

      <a class="logout" href="controller?cmd=logoutAction">로그아웃</a>
    </div>

    <!-- Main -->
    <div class="main">
      <div class="page-header">
        <div class="page-title">
          <span><c:out value="${param.projectName != null ? param.projectName : (requestScope.project.projectName)}"/></span>
          <span class="date-range">
            <c:out value="${requestScope.project.startDate}"/> ~ <c:out value="${requestScope.project.endDate}"/>
          </span>
        </div>
        <div class="member-chip">
          <div class="avatar-s">
            <img src="data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='120' height='120'><defs><linearGradient id='g' x1='0' x2='1'><stop offset='0' stop-color='%23ffd1dc'/><stop offset='1' stop-color='%23c7b9ff'/></linearGradient></defs><rect width='100%' height='100%' fill='url(%23g)'/><circle cx='60' cy='50' r='22' fill='white'/><rect x='30' y='78' width='60' height='26' rx='13' fill='white'/></svg>" alt="멤버 아이콘" />
          </div>
          <span class="member-ratio">
            <c:out value="${requestScope.project.workingCnt}"/>/<c:out value="${requestScope.project.membersCnt}"/>
          </span>
        </div>
      </div>

      <div class="desc-wrap">
        <div class="desc-box"><c:out value="${requestScope.project.description}"/></div>
        <a class="btn-outline" href="controller?cmd=projectEditUI&projectNo=${projectNo}">프로젝트 수정</a>
      </div>

      <!-- 편집 폼 -->
      <form id="taskEditForm" class="task-wrap">
        <!-- 필수 전달 값 -->
        <input type="hidden" name="cmd" value="${isEdit ? 'updateTaskAction' : 'insertTaskAction'}"/>
        <input type="hidden" name="projectNo" value="${projectNo}"/>
        <c:if test="${isEdit}"><input type="hidden" name="taskNo" value="${task.taskNo}"/></c:if>

        <!-- 담당자 선택 결과(되돌아올 때 유지) -->
        <input type="hidden" id="memberIds"   name="memberIds"   value="${memberIds}"/>
        <input type="hidden" id="leaderName"  name="leaderName"  value="${leaderName}"/>
        <input type="hidden" id="memberCnt"   name="memberCnt"   value="${memberCnt}"/>

        <table class="task-table">
          <thead>
            <tr>
              <th>업무명</th>
              <th>담당자</th>
              <th>진행상태</th>
              <th>시작일</th>
              <th>마감일</th>
              <th>우선순위</th>
              <th>진척도</th>
              <th>&nbsp;</th>
            </tr>
          </thead>

          <tbody>
            <tr class="row-edit" data-taskno="${isEdit ? task.taskNo : ''}">
              <td>
                <input class="input-s" type="text" name="taskName"
                       value="${isEdit ? task.taskName : ''}" placeholder="업무명을 입력하세요." />
              </td>

              <!-- 담당자: 대표자 + (멤버수-1) 표기 + 편집 버튼 -->
              <td>
                <span id="assigneeChip" class="chip" title="담당자 편집">
                  <c:out value="${leaderName}"/><c:if test="${memberCnt > 1}">
                    <span class="count">+<c:out value="${memberCnt - 1}"/></span>
                  </c:if>
                </span>
                <button type="button" class="btn-xs" id="btn-edit-members">담당자 편집</button>
              </td>

              <td>
                <select class="select-s w-120" name="taskStatus">
                  <c:set var="st" value="${isEdit ? task.taskStatus : param.taskStatus}" />
                  <option ${st=='대기'  ? 'selected' : ''}>대기</option>
                  <option ${st=='진행'  ? 'selected' : ''}>진행</option>
                  <option ${st=='완료'  ? 'selected' : ''}>완료</option>
                  <option ${st=='보류'  ? 'selected' : ''}>보류</option>
                  <option ${st=='기타'  ? 'selected' : ''}>기타</option>
                </select>
              </td>

              <td><input class="input-s w-90" type="date" name="startDate" value="${isEdit ? task.startDate : ''}" /></td>
              <td><input class="input-s w-90" type="date" name="endDate"   value="${isEdit ? task.endDate   : ''}" /></td>

              <td>
                <select class="select-s w-90" name="priority">
                  <c:set var="pr" value="${isEdit ? task.priority : param.priority}" />
                  <option ${pr=='낮음' ? 'selected' : ''}>낮음</option>
                  <option ${pr=='보통' ? 'selected' : ''}>보통</option>
                  <option ${pr=='높음' ? 'selected' : ''}>높음</option>
                  <option ${pr=='긴급' ? 'selected' : ''}>긴급</option>
                  <option ${empty pr || pr=='없음' ? 'selected' : ''}>없음</option>
                </select>
              </td>

              <td>-</td>

              <td>
                <button type="button" class="btn-xs" id="btn-confirm">${isEdit ? '수정 완료' : '추가 완료'}</button>
                <button type="button" class="btn-xs" id="btn-cancel">취소</button>
              </td>
            </tr>
          </tbody>
        </table>
      </form>

      <div style="margin-top:10px;">
        <!-- 추가 모드에서도 한 번 더 신규 추가 진입 가능 -->
        <button type="button" class="btn-outline" id="btn-go-add">+ 업무추가</button>
      </div>
    </div>
  </div>

  <script>
    // 페이지에서 JS가 사용할 컨텍스트 값
    window.TaskEdit = {
      baseUrl: "controller",
      projectNo: "${projectNo}",
      isEdit: ${isEdit ? 'true' : 'false'},
      taskNo: "${isEdit ? task.taskNo : ''}"
    };
  </script>
  <script src="./JS/taskUpdate.js"></script>
</body>
</html>
