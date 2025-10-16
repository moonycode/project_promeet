<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<title>파일함 - A프로젝트 폴더</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="CSS/common.css" />
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<body>
	<div class="container">
		<!-- Sidebar -->
		<div class="sidebar">
			<div class="logo-wrap">
				<img
					src="data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='240' height='60'><rect width='100%' height='100%' rx='12' ry='12' fill='%23b14dd6'/><text x='50%' y='55%' dominant-baseline='middle' text-anchor='middle' font-family='Arial' font-size='28' fill='white'>ProMeet</text></svg>"
					alt="ProMeet 로고" />
			</div>
			<div class="avatar">
				<img
					src="data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='120' height='120'><defs><linearGradient id='g' x1='0' x2='1'><stop offset='0' stop-color='%23ffd1dc'/><stop offset='1' stop-color='%23c7b9ff'/></linearGradient></defs><rect width='100%' height='100%' fill='url(%23g)'/><circle cx='60' cy='50' r='22' fill='white'/><rect x='30' y='78' width='60' height='26' rx='13' fill='white'/></svg>"
					alt="프로필 이미지(가상)" />
			</div>
			<div class="username">홍길동 팀장</div>

			<div class="status-board">
				<div class="row">
					<label for="status">상태</label> <select id="status" name="status">
						<option>출근</option>
						<option>자리비움</option>
						<option>외근</option>
						<option>퇴근</option>
					</select>
				</div>
			</div>

			<nav class="nav">
				<a href="#">프로젝트</a> <a class="active" href="#">파일함</a> <a href="#">일정관리</a>
				<a href="#">마이페이지</a>

			</nav>

			<button class="logout">로그아웃</button>
		</div>

		<!-- Main -->
		<div class="main">
			<h1 class="page-title" style="gap: 8px">
				<span style="font-size: 36px; font-weight: 900">파일함</span>
			</h1>
			<div class="searchbar">
				<input class="search-input" type="text" value=""
					placeholder="파일명 검색" />
				<button class="search-btn" aria-label="검색">🔍</button>
			</div>

			<section class="section">
				<h2 class="filebox-h2"></h2>

				<div class="file-grid" id="fileGrid"></div>
			</section>
			<section class="panel" style="padding: 0">
				<table class="file-table">
					<tbody>
					</tbody>
				</table>
			</section>


		</div>

	</div>
	<script src="JS/fileBox.js"></script>
</body>
