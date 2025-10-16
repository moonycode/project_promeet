<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="user" value="${sessionScope.user}" />
<c:set var="info" value="${requestScope.myPageInfo}" />

<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>마이페이지 - ProMeet</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/common.css" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        #message-area {
            text-align: center;
            margin-bottom: 15px;
            font-weight: bold;
            min-height: 20px;
        }
        .msg-success { color: #28a745; }
        .msg-error { color: #dc3545; }
        .panel .field input:not([disabled]) { 
            border: 1px solid #ccc; 
            padding: 8px;
            border-radius: 4px;
        }
    </style>
</head>
<body class="pm-mypage">
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
                        <option <c:if test="${user.workStatus eq '출근'}">selected</c:if>>출근</option>
                        <option <c:if test="${user.workStatus eq '자리비움'}">selected</c:if>>자리비움</option>
                        <option <c:if test="${user.workStatus eq '외근'}">selected</c:if>>외근</option>
                        <option <c:if test="${user.workStatus eq '퇴근'}">selected</c:if>>퇴근</option>
                    </select>
                </div>
            </div>
            
            <nav class="nav">
                <a href="controller?cmd=projectUI">프로젝트</a>
                <a href="#">파일함</a>
                <a href="#">일정관리</a>
                <a class="active" href="controller?cmd=myPageUI">마이페이지</a>
            </nav>
            
            <button class="logout">로그아웃</button>
        </div>

        <div class="main">
            <div class="main-inner">
                <h1 class="page-title">마이페이지</h1>
                
                <div id="message-area"></div>

                <div class="grid">
                    <div class="panel">
                        
                        <div class="field">
                            <label>사번</label>
                            <input class="readonly" type="text" value="${info.employeeId}" disabled>
                        </div>
                        
                        <div class="field">
                            <label>부서</label>
                            <input class="readonly" type="text" value="${info.position}" disabled> 
                        </div>
                        
                        <div class="field">
                            <label>전화번호</label>
                            <input class="readonly" type="tel" value="${info.phoneNumber}" disabled>
                        </div>
                        
                        <div class="field">
                            <label>이메일</label>
                            <input class="readonly" type="email" value="${info.email}" disabled>
                        </div>
                        
                        <div class="field">
                            <label>프로젝트 생성권한</label>
                            <input class="readonly" type="text" value="${info.projectCreatorFlag eq 1 ? 'Y' : 'N'}" disabled>
                        </div>
                    </div>
                
                    <div class="panel">
                        <div class="field">
                            <label for="current-password">기존 비밀번호</label>
                            <input id="current-password" type="password" placeholder="현재 비밀번호">
                        </div>
                        <div class="field">
                            <label for="new-password">새 비밀번호</label>
                            <input id="new-password" type="password" placeholder="새 비밀번호">
                        </div>
                        <div class="field">
                            <label for="confirm-password">새 비밀번호 확인</label>
                            <input id="confirm-password" type="password" placeholder="새 비밀번호 확인">
                        </div>
                        <div class="actions">
                            <button id="cancel-btn" class="btn btn-outline">취소</button>
                            <button id="change-password-btn" class="btn primary">수정</button>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
<script src="JS/mypage.js"></script>
</body>
</html>