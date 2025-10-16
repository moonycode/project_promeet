<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%request.setAttribute("scheduleActive", "active");%>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<head>
<title>일정관리 - ProMeet</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<!-- 공통 스타일 -->
<link rel="stylesheet" href="CSS/common.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />
</head>
<body class="pm-schedule">
	<div class="container">
		
		<%@ include file="Jspf/sidebar.jspf" %>

		<!-- 메인 -->
		<div class="main">
			<div class="page-header">
				<div class="page-title">일정관리</div>
			</div>

			<!-- 캘린더 + 우측 일정목록 -->
			<div class="sched-layout">
				<!-- 캘린더 -->
				<section class="panel">
					<div class="calendar-header">
						<button id="prevMonth" class="btn-outline s" aria-label="이전달">&lt;</button>
						<div class="year-month">
							<span class="year"></span>
							<h2 class="month"></h2>
						</div>
						<button id="nextMonth" class="btn-outline s" aria-label="다음달">&gt;</button>
					</div>
					<table class="calendar">
						<thead>
							<tr>
								<th>월</th>
								<th>화</th>
								<th>수</th>
								<th>목</th>
								<th>금</th>
								<th>토</th>
								<th>일</th>
							</tr>
						</thead>
						<tbody id="calendarTbody">
						</tbody>
					</table>

				</section>

				<!-- 일정목록 -->
				<aside class="panel sched-list">
					<h3 class="panel-title"></h3>
					<div class="sched-scroll-container">
						<ul class="sched-ul"></ul>
					</div>

					<!-- 일정 추가 폼 -->
					<div class="sched-add-form">
						<input type="text" class="input-s" placeholder="일정명 입력" />

						<!-- dot 선택 -->
						<div class="dot-picker">
							<label><input type="radio" name="color" value="red"><span
								class="dot red"></span></label> <label><input type="radio"
								name="color" value="orange"><span class="dot orange"></span></label>
							<label><input type="radio" name="color" value="yellow"><span
								class="dot yellow"></span></label> <label><input type="radio"
								name="color" value="green"><span class="dot green"></span></label>
							<label><input type="radio" name="color" value="blue"><span
								class="dot blue"></span></label> <label><input type="radio"
								name="color" value="navy"><span class="dot navy"></span></label>
							<label><input type="radio" name="color" value="purple"><span
								class="dot purple"></span></label> <label><input type="radio"
								name="color" value="black"><span class="dot black"></span></label>
							<label><input type="radio" name="color" value="white"><span
								class="dot white"></span></label>
						</div>


						<!-- 시간 선택 -->
						<div class="time-picker">
							<select id="startHour"></select> : <select id="startMin"></select>
							~ <select id="endHour"></select> : <select id="endMin"></select>
						</div>

						<div class="actions">
							<button class="save-xs">저장</button>
							<button class="cancel-xs">취소</button>
						</div>
					</div>
					<button class="btn secondary add-btn">+ 일정 추가</button>
				</aside>
			</div>
		</div>
		<div id="customAlert"
			style="display: none; position: fixed; top: 30%; left: 50%; transform: translateX(-50%); background: #fff; border: 1px solid #ccc; padding: 20px; z-index: 9999;">
			<p id="alertMessage"></p>
			<button onclick="$('#customAlert').hide()">확인</button>
		</div>		
		</div>
		<script src="JS/schedule.js"></script>
</body>