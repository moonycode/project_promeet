<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
		<%@ include file="Jspf/sidebar.jspf"%>
		<div class="main">
			<div class="detail-top">
				<div class="detail-titlebar">
					<div class="icon-back" onclick="history.back()">←</div>
					<div class="detail-title"></div>
				</div>

				<div class="detail-dates">
					<span class="start"></span> <span class="til">~</span> <span
						class="end"></span>
				</div>

				<div class="member-pill">
					<span class="members"></span>
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
					<div class="cmt-list" id="commentList"></div>
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
		window.taskNo = "${task.taskNo}";
		window.taskName = "${task.taskName}";
		window.startDate = "<fmt:formatDate value='${task.startDate}' pattern='yyyy-MM-dd'/>";
		window.endDate = "<fmt:formatDate value='${task.endDate}' pattern='yyyy-MM-dd'/>";
		window.membersText = "${task.membersText}";
	</script>
	<script src="JS/details.js"></script>
</body>
</html>
