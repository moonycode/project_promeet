<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%request.setAttribute("myPageActive", "active");%>
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
        .msg-success { color: blue; }
        .msg-error { color: red; }
        .panel .field input:not([disabled]) { 
            border: 1px solid #ccc; 
            padding: 8px;
            border-radius: 4px;
        }
    </style>
</head>
<body class="pm-mypage">
    <div class="container">
        
        <%@ include file="Jspf/sidebar.jspf" %>

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