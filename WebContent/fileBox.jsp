<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%request.setAttribute("fileBoxActive", "active");%>
<head>
<title>파일함 - A프로젝트 폴더</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="CSS/common.css" />
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<body>
	<div class="container">
	
		<%@ include file="Jspf/sidebar.jspf" %>

		<!-- Main -->
		<div class="main">
			<h1 class="page-title" style="gap: 8px">
				<span style="font-size: 36px; font-weight: 900" class="fileBox">파일함</span>
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
