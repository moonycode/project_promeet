<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<title>ProMeet 휴지통</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/common.css" />
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
					<label for="status">상태</label> <select id="status" name="status">
						<option>출근</option>
						<option>자리비움</option>
						<option>외근</option>
						<option>퇴근</option>
					</select>
				</div>
			</div>
			
			<nav class="nav">
				<a href="controller?cmd=projectUI">프로젝트</a>	
				<a href="#">파일함</a>
				<a href="#">일정관리</a>
				<a href="#">마이페이지</a>
			</nav>
			
			<button class="logout">로그아웃</button>
		</div>
		
		<div class="main">
			<div class="header">
				<h1>휴지통</h1>
				<div>
					<a class="btn" href="controller?cmd=projectUI">메인으로</a>
				</div>
			</div>

			<section class="section">
				<div class="projects project-list-container">
					<c:choose>
						<c:when test="${not empty binProjects}">
							<c:forEach var="project" items="${binProjects}">
            					<a href="controller?cmd=taskUI&projectNo=${project.projectNo}" class="project-card-link">
								<article class="project-card">
									<div class="project-header">
										<div class="people"><span class="count">${project.memberCount}</span></div>
					                    <div class="project-title">
					                        <h3><c:out value="${project.projectName}" /></h3>
					                    </div>
									</div>
									
									<div class="card-body">
										<div class="client-info small">${project.client}</div>
										<div class="description small">
											<c:out value="${project.description}" />
										</div>
									</div>
									
									<div class="card-footer">
										<c:set var="progress" value="${project.totalCnt > 0 ? (project.doneCnt * 100.0) / project.totalCnt : 0}" />
										<div class="small">
											진행도	
											<fmt:formatNumber value="${progress}" pattern="#0.0" />%	
										</div>
										<div class="progress">
											<div class="progress-bar" style="width:<c:out value="${progress}" />%"></div>
										</div>
										
									</div>
								</article>
								</a>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<p class="empty-bin-message">휴지통이 비어 있습니다.</p>
						</c:otherwise>
					</c:choose>
				</div>
			</section>
		</div>
	</div>
</body>
</html>