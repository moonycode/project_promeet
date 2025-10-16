<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>업무 상세 - 보기</title>
<link rel="stylesheet" href="CSS/common.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>

<body>
	<div class="container">
	    <div class="sidebar">
      <div class="logo-wrap">
        </div>
      <div class="avatar">
        </div>
      <div class="user-name-position">
        <c:if test="${not empty user}">
            <c:out value="${user.name}" /> 
            <c:out value="${user.position}" />
        </c:if>
        <c:if test="${empty user}">
             로그인 필요
        </c:if>
    </div>

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
        <a class="active" href="controller?cmd=projectUI">프로젝트</a> 
        <a href="#">파일함</a>
        <a href="#">일정관리</a>
        <a href="#">마이페이지</a>
      </nav>
        
      <button class="logout">로그아웃</button>
    </div>
		<div class="main">
			
			<div class="detail-body">
				<div class="todo-card view">
					<div class="todo-list-wrap">
						<div class="todo-list" id="todoList"></div>
					</div>
					<button id="btnAddLine" class="btn-primary" style="display: none;">추가</button>
					<div class="footer-actions">
						<button id="btnToggleEdit" class="btn-primary">수정</button>
						<button id="btnCancel" class="btn-primary" style="display: none;">취소</button>
					</div>
				</div>

				<section class="cmt-panel">
					<div class="cmt-list" id="commentList">
						<c:forEach var="comment" items="${commentList}">
							<article class="cmt4">
								<div class="line1">
									<div class="name">${comment.commentWriter}</div>
								</div>

								<div class="line2">${comment.commentContents}</div>

								<div class="line3">
									<div class="meta-row">
										<time>${comment.commentIndate}</time>

										<c:if test="${!comment.commentDeldate}">
											<div class="meta-actions">
												<a href="#" class="btnEdit" data-id="${comment.commentNo}" data-type="comment">수정</a>
												<a href="#" class="btnDel" data-id="${comment.commentNo}" data-type="comment">삭제</a>
											</div>
										</c:if>
									</div>
								</div>

								<div class="line4">
									<c:if test="${!comment.commentDeldate}">
										<button class="btn-reply" data-id="${comment.commentNo}">답글</button>
									</c:if>
								</div>

								<div class="reply-stack">
									<c:forEach var="reply" items="${comment.replies}">
										<div class="r3">
											<div class="rline1">
												<span class="arrow">ㄴ</span>
												<span class="name">${reply.replyWriter}</span>
											</div>
											<div class="rline2">
												<c:choose>
													<c:when test="${reply.repliesDeldate}">
														삭제된 답글입니다.
													</c:when>
													<c:otherwise>
														${reply.repliesContents}
													</c:otherwise>
												</c:choose>
											</div>
											<div class="rline3">
												<div class="meta-row">
													<time>${reply.repliesIndate}</time>

													<c:if test="${!reply.repliesDeldate}">
														<div class="meta-actions">
															<a href="#" class="btnEdit" data-id="${reply.replyNo}" data-type="reply">수정</a>
															<a href="#" class="btnDel" data-id="${reply.replyNo}" data-type="reply">삭제</a>
														</div>
													</c:if>
												</div>
											</div>
										</div>
									</c:forEach>
								</div>
							</article>
						</c:forEach>
					</div>
					<div class="comment-input">
						<input id="newComment" type="text" placeholder="댓글 내용을 입력하세요" />
						<button id="btnAddFile" class="btn-small btn-gray">첨부</button>
						<button id="btnAddComment" class="btn-small btn-pink">등록</button>
					</div>
					<p id="attachedFileName" class="attached-file"></p>
				</section>
			</div>
		</div>
	</div>
		<dialog id="fileDialog">
  <form method="dialog">
    <h4>파일 이름을 입력하세요</h4>
    <input type="text" id="fileNameInput" placeholder="">
    <menu>
      <button value="cancel">취소</button>
      <button value="confirm">확인</button>
    </menu>
  </form>
</dialog>

	<script src="JS/details.js"></script>
</body>
</html>
