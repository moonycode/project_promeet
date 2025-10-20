<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%request.setAttribute("projectActive", "active");%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>ProMeet 프로젝트 관리</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/common.css" />
</head>
<body>
  <div class="container">
    
    <%@ include file="Jspf/sidebar.jspf" %>
    
    <div class="main">
      <div class="header">
        <h1>프로젝트</h1>
        <div>
          <a class="btn secondary" href="controller?cmd=binProjectUI">휴지통</a>
          <a class="btn" href="controller?cmd=addProjectUI">+ 프로젝트 등록</a> 
        </div>
      </div>

      <section class="section">
        <h2>진행중</h2>
        <div class="projects project-list-container">
          <c:forEach var="project" items="${ongoingProjects}">
            <a href="controller?cmd=tasksUI&projectNo=${project.projectNo}" class="project-card-link">
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
				    <div class="meta">
				    <c:choose><c:when test="${project.dday != null and project.dday < 0}">D+<c:out value="${project.dday * -1}" /></c:when><c:when test="${project.dday != null and project.dday >= 0}">D-<c:out value="${project.dday}" /></c:when><c:otherwise>D-0</c:otherwise></c:choose>
				    </div>
				</div>
              </article>
            </a>
          </c:forEach>
        </div>
      </section>

      <section class="section">
        <h2>완료</h2>
        <div class="projects project-list-container">
          <c:forEach var="project" items="${completedProjects}">
            <a href="controller?cmd=tasksUI&projectNo=${project.projectNo}" class="project-card-link">
              <article class="project-card completed-project-card">
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
              </article>
            </a>
          </c:forEach>
        </div>
      </section>
    </div>
  </div>
</body>
</html>