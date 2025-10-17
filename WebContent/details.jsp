<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	request.setAttribute("projectActive", "active");
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>업무 상세 - 보기</title>
<link rel="stylesheet" href="CSS/common.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>

<body>
	<div class="container">
	 <%@ include file="Jspf/sidebar.jspf" %>
		<div class="main">
			<div class="detail-top">
				<div class="detail-titlebar">
					<div class="icon-back"  onclick="history.back()">←</div>
					<div class="detail-title"></div>
				</div>
				<div class="detail-dates">  <span class="start">2025-09-15</span>
  <span class="endline"><span class="til">~</span> <span class="end">2025-09-17</span></span></div>
				<div class="member-pill">
					<div class="avatar-s">
						<img
							src="data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='120' height='120'><defs><linearGradient id='g' x1='0' x2='1'><stop offset='0' stop-color='%23ffd1dc'/><stop offset='1' stop-color='%23c7b9ff'/></linearGradient></defs><rect width='100%' height='100%' fill='url(%23g)'/><circle cx='60' cy='50' r='22' fill='white'/><rect x='30' y='78' width='60' height='26' rx='13' fill='white'/></svg>"
							alt="멤버" />
					</div>
					<div class="plus">+2</div>
				</div>
			</div>
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
	<script>
		window.employeeId = "${user.employeeId}";
		window.taskNo = "${taskNo}";
	</script>
	<script src="JS/details.js"></script>
</body>
</html>
