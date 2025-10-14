<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <title>프로젝트 수정 - ProMeet</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/common.css" />
</head>
<body>
    <div class="container">
        
        <div class="sidebar">
            <div class="logo-wrap"></div>
            <div class="avatar"></div>
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
            <div class="panel">
                <a class="close-x" href="controller?cmd=taskUI&projectNo=<c:out value="${project.projectNo}" />">X</a>
                
                <h1 class="form-title">프로젝트 수정</h1>
                
                <form action="controller" method="POST">
                    <input type="hidden" name="cmd" value="updateProject" />
                    <input type="hidden" name="projectNo" value="<c:out value="${project.projectNo}" />" />
                    <input type="hidden" name="creatorId" value="<c:out value="${project.creatorId}" />" />
                    
                    <div class="form-row">
                        <div class="form-col">
                            <input class="input-lg" type="text" name="projectName" required 
                                value="<c:out value="${project.projectName}" />" placeholder="제목을 입력하세요." />
                        </div>
                    </div>
                    
                    <div class="form-row">
                        <div class="form-col">
                            <input class="input-lg" type="text" name="client" 
                                value="<c:out value="${project.client}" />" placeholder="발주 기업/기관명(옵션)" />
                        </div>
                    </div>

                    <div class="form-row" style="align-items: center; justify-content: space-between;">
                        <label>프로젝트 시작일</label>
                        <input class="date-s" type="date" name="startDate" value="<fmt:formatDate value="${project.startDate}" pattern="yyyy-MM-dd" />" />
                        
                        <label style="margin-left:10px;">프로젝트 목표일</label>
                        <input class="date-s" type="date" name="endDate" value="<fmt:formatDate value="${project.endDate}" pattern="yyyy-MM-dd" />" />
                        
                        <a href="controller?cmd=completeProject&projectNo=<c:out value="${project.projectNo}" />" 
                            class="btn secondary complete-btn" onclick="return confirm('프로젝트를 종료하시겠습니까?');" style="margin-left: auto;">프로젝트 종료</a> 
                    </div>

                    <div class="form-row">
                        <div class="form-col">
                            <textarea class="textarea-lg" name="description" placeholder="프로젝트 설명(옵션)"><c:out value="${project.description}" /></textarea>
                        </div>
                    </div>

                    <div class="actions-right">
                        <a href="controller?cmd=deleteProject&projectNo=<c:out value="${project.projectNo}" />" 
                            class="btn secondary delete-btn" onclick="return confirm('프로젝트를 삭제하시겠습니까?');">프로젝트 삭제</a>
                        
                        <button type="submit" class="btn-primary">저장</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>