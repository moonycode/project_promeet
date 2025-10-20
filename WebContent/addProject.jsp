<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%request.setAttribute("projectActive", "active");%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<title>새 프로젝트 만들기 - ProMeet</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/common.css" />
</head>
<body>
    <div class="container">
        
        <%@ include file="Jspf/sidebar.jspf" %>
        
        <div class="main">
            <div class="panel">
                <a class="close-x" href="controller?cmd=projectUI">X</a>
                <h1 class="form-title">새 프로젝트 만들기</h1>
                
                <form action="controller" method="POST">
                    <input type="hidden" name="cmd" value="addProject" />
					<c:if test="${not empty user}">
					<input type="hidden" name="creatorId" value="${user.employeeId}" /> 
					</c:if>
                    
                    <c:if test="${not empty msg}">
                        <p style="color: red;">
                            <c:out value="${msg}" />
                        </p>
                    </c:if>

                    <div class="form-row">
                        <div class="form-col">
                            <input class="input-lg" type="text" name="projectName" required placeholder="제목을 입력하세요." />
                        </div>
                    </div>
                    
                    <div class="form-row">
                        <div class="form-col">
                            <input class="input-lg" type="text" name="client" placeholder="발주 기업/기관명(옵션)" />
                        </div>
                    </div>

                    <div class="form-row"> 
                        <label>프로젝트 시작일</label>
                        <input class="date-s" type="date" name="startDate" />
                        <label style="margin-left:10px;">프로젝트 목표일</label>
                        <input class="date-s" type="date" name="endDate" />
                    </div>

                    <div class="form-row">
                        <div class="form-col">
                            <textarea class="textarea-lg" name="description" placeholder="프로젝트 설명(옵션)"></textarea>
                        </div>
                    </div>

                    <div class="actions-right">
                        <button type="submit" class="btn-primary">등록</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>