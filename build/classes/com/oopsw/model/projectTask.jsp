<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>프로젝트 업무 목록 - ProMeet</title>
  <link rel="stylesheet" href="./CSS/common.css" />
</head>
<body data-projectno="${project.projectNo}">
  <div class="container">

    <!-- 사이드바 -->
    <div class="sidebar">
      <div class="logo-wrap">
        <img src="data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='240' height='60'><rect width='100%' height='100%' rx='12' ry='12' fill='%23b14dd6'/><text x='50%' y='55%' dominant-baseline='middle' text-anchor='middle' font-family='Arial' font-size='28' fill='white'>ProMeet</text></svg>" alt="ProMeet 로고" />
      </div>
      <div class="avatar">
        <img src="data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='120' height='120'><defs><linearGradient id='g' x1='0' x2='1'><stop offset='0' stop-color='%23ffd1dc'/><stop offset='1' stop-color='%23c7b9ff'/></linearGradient></defs><rect width='100%' height='100%' fill='url(%23g)'/><circle cx='60' cy='50' r='22' fill='white'/><rect x='30' y='78' width='60' height='26' rx='13' fill='white'/></svg>" alt="프로필 이미지(가상)" />
      </div>
      <div class="username"><c:out value="${loginName != null ? loginName : '홍길동 팀장'}"/></div>

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
        <a class="active" href="#">프로젝트</a>
        <a href="#">파일함</a>
        <a href="#">일정관리</a>
        <a href="#">마이페이지</a>
      </nav>

      <button class="logout">로그아웃</button>
    </div>
    <!-- // 사이드바 -->

    <!-- 메인 -->
    <div class="main">
      <div class="page-header">
        <div class="page-title">
          <span><c:out value="${project.projectName != null ? project.projectName : '304기 2차 프로젝트'}"/></span>
          <span class="date-range">
            <c:out value="${project.startDate != null ? project.startDate : '2025-09-15'}"/> ~
            <c:out value="${project.endDate   != null ? project.endDate   : '2025-10-02'}"/>
          </span>
        </div>

        <!-- 1) 멤버칩: 클릭 시 taskMembers.html 이동 -->
        <div class="member-chip" id="memberChip" role="button" tabindex="0" style="cursor:pointer">
          <div class="avatar-s">
            <img src="data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='120' height='120'><defs><linearGradient id='g' x1='0' x2='1'><stop offset='0' stop-color='%23ffd1dc'/><stop offset='1' stop-color='%23c7b9ff'/></linearGradient></defs><rect width='100%' height='100%' fill='url(%23g)'/><circle cx='60' cy='50' r='22' fill='white'/><rect x='30' y='78' width='60' height='26' rx='13' fill='white'/></svg>" alt="멤버 아이콘" />
          </div>
          <span class="member-ratio">
            <c:out value="${project.joinedCount != null ? project.joinedCount : 3}"/>/<c:out value="${project.memberTotal != null ? project.memberTotal : 5}"/>
          </span>
        </div>
      </div>

      <!-- 설명 + 수정 버튼 -->
      <div class="desc-wrap">
        <div class="desc-box">
          <c:out value="${project.description != null ? project.description : '<업무관리 플랫폼> 기획중입니다.\n1. UI 명세서'}"/>
        </div>
        <!-- 3) 프로젝트 수정: 클릭 시 projectUpdate.html 이동 -->
        <a class="btn-outline" id="btnProjectUpdate" href="projectUpdate.html">프로젝트 수정</a>
      </div>

      <!-- 필터/정렬 테이블 -->
      <div class="task-wrap">
        <table class="task-table" id="taskTable">
          <thead>
            <tr>
              <th>업무명</th>
              <th>담당자</th>

              <!-- 2) 진행상태 필터 -->
              <th class="th-filter">
                <div class="th-wrap">
                  <span class="th-label">진행상태 ▾</span>
                  <div class="dd-panel">
                    <div class="row">
                      <span class="lbl">선택</span>
                      <select class="select-s" id="filterStatus">
                        <option value="전체" selected>전체</option>
                        <option value="완료">완료</option>
                        <option value="진행">진행</option>
                        <option value="대기">대기</option>
                        <option value="보류">보류</option>
                        <option value="기타">기타</option>
                      </select>
                    </div>
                  </div>
                </div>
              </th>

              <!-- 2) 시작일 정렬 -->
              <th class="th-sort">
                <span class="th-text">시작일</span>
                <span class="sort-inline">
                  <button type="button" class="btn-sort" data-sort="start" data-dir="asc"  title="오름차">▲</button>
                  <button type="button" class="btn-sort" data-sort="start" data-dir="desc" title="내림차">▼</button>
                </span>
              </th>

              <!-- 2) 마감일 정렬 -->
              <th class="th-sort">
                <span class="th-text">마감일</span>
                <span class="sort-inline">
                  <button type="button" class="btn-sort" data-sort="end" data-dir="asc"  title="오름차">▲</button>
                  <button type="button" class="btn-sort" data-sort="end" data-dir="desc" title="내림차">▼</button>
                </span>
              </th>

              <!-- 2) 우선순위 필터 -->
              <th class="th-filter">
                <div class="th-wrap">
                  <span class="th-label">우선순위 ▾</span>
                  <div class="dd-panel">
                    <div class="row">
                      <span class="lbl">선택</span>
                      <select class="select-s" id="filterPriority">
                        <option value="전체" selected>전체</option>
                        <option value="긴급">긴급</option>
                        <option value="높음">높음</option>
                        <option value="보통">보통</option>
                        <option value="낮음">낮음</option>
                        <option value="">없음</option>
                      </select>
                    </div>
                  </div>
                </div>
              </th>

              <th>진척도</th>
              <th>&nbsp;</th>
            </tr>
          </thead>

          <tbody id="taskTbody">
            <!-- 서버에서 전달된 tasks 리스트를 렌더링 -->
            <!-- tasks: List<TaskDTO> 가정 (taskNo, taskName, assigneesText, status, startDate, endDate, priority, progress) -->
            <c:forEach var="t" items="${tasks}">
              <tr
                data-taskno="${t.taskNo}"
                data-status="${t.status}"
                data-priority="${t.priority}"
                data-start="${t.startDate}"
                data-end="${t.endDate}"
              >
                <td><c:out value="${t.taskName}"/></td>
                <td>
                  <span class="chip">
                    <c:out value="${t.assigneesText}"/>
                  </span>
                </td>
                <td><c:out value="${t.status}"/></td>
                <td><c:out value="${t.startDate}"/></td>
                <td><c:out value="${t.endDate}"/></td>
                <td><c:out value="${t.priority}"/></td>
                <td class="progress-cell"><c:out value="${t.progress}"/>%</td>
                <td>
                  <!-- 4) 수정/삭제 버튼 -->
                  <button type="button" class="btn-xs btn-edit">수정</button>
                  <button type="button" class="btn-xs btn-del">삭제</button>
                </td>
              </tr>
            </c:forEach>

            <!-- 서버데이터가 없을 때 샘플(원본 HTML과 동일) -->
            <c:if test="${empty tasks}">
              <tr data-status="완료" data-priority="긴급" data-start="2025-09-15" data-end="2025-09-17">
                <td>UI 명세서</td>
                <td><span class="chip">홍길동<span class="count">+1</span></span></td>
                <td>완료</td>
                <td>2025-09-15</td>
                <td>2025-09-17</td>
                <td>긴급</td>
                <td class="progress-cell">100%</td>
                <td><button type="button" class="btn-xs btn-edit">수정</button> <button type="button" class="btn-xs btn-del">삭제</button></td>
              </tr>
              <tr data-status="진행" data-priority="긴급" data-start="2025-09-17" data-end="2025-09-22">
                <td>Figma</td>
                <td><span class="chip">김영희</span></td>
                <td>진행</td>
                <td>2025-09-17</td>
                <td>2025-09-22</td>
                <td>긴급</td>
                <td class="progress-cell">70%</td>
                <td><button type="button" class="btn-xs btn-edit">수정</button> <button type="button" class="btn-xs btn-del">삭제</button></td>
              </tr>
              <tr data-status="대기" data-priority="낮음" data-start="2025-08-15" data-end="2025-09-15">
                <td>논리모델링</td>
                <td><span class="chip">김철수<span class="count">+2</span></span></td>
                <td>대기</td>
                <td>2025-08-15</td>
                <td>2025-09-15</td>
                <td>낮음</td>
                <td class="progress-cell">0%</td>
                <td><button type="button" class="btn-xs btn-edit">수정</button> <button type="button" class="btn-xs btn-del">삭제</button></td>
              </tr>
              <tr data-status="보류" data-priority="" data-start="2025-10-10" data-end="2025-11-21">
                <td>구현</td>
                <td><span class="chip">김미애<span class="count">+3</span></span></td>
                <td>보류</td>
                <td>2025-10-10</td>
                <td>2025-11-21</td>
                <td></td>
                <td class="progress-cell">0%</td>
                <td><button type="button" class="btn-xs btn-edit">수정</button> <button type="button" class="btn-xs btn-del">삭제</button></td>
              </tr>
            </c:if>
          </tbody>
        </table>
      </div>

      <!-- 6) 업무추가 -->
      <div style="margin-top:10px;">
        <button type="button" class="btn-outline" id="btnAddRow">+ 업무추가</button>
      </div>
    </div>
    <!-- // 메인 -->

  </div>

  <!-- 분리된 JS 로딩 -->
  <script src="./JS/tasks.js"></script>
  <script>
    // 링크 연결 (접근성: 키보드 엔터도 이동)
    const memberChip = document.getElementById('memberChip');
    memberChip?.addEventListener('click', () => location.href = 'taskMembers.html');
    memberChip?.addEventListener('keydown', (e) => { if (e.key === 'Enter') location.href = 'taskMembers.html'; });
  </script>
</body>
</html>
