<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="ko">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Login - ProMeet</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/common.css" />
</head>
<body class="pm-login">
    <div class="header">
        <img src="${pageContext.request.contextPath}/img/logo.png" alt="ProMeet" width="40" height="40" />
        ProMeet
    </div>
    <div class="wrap">
        <div class="hero">
            <img src="${pageContext.request.contextPath}/img/mainimage.png" alt="Welcome" style="max-width:70%;height:auto;">
            <div class="hero-text">
                <h2>간편하고 직관적인 협업</h2>
                <p>ProMeet으로 우리 회사 안에서 빠르고 쉽게 연결하세요</p>
            </div>
        </div>
        <div class="card">
            <h2>로그인</h2>
            <form action="controller" method="POST">
                <input type="hidden" name="cmd" value="login" />
                
                <c:if test="${not empty msg}">
                    <p style="color: red; margin-bottom: 10px;"><c:out value="${msg}" /></p>
                </c:if>

                <div class="kv" style="margin-top:12px">
                    <label for="employeeId">사번</label>
                    <input type="text" id="employeeId" name="employeeId" placeholder="" required />
                    <label for="password">비밀번호</label>
                    <input type="password" id="password" name="password" placeholder="" required />
                    
                    <button type="submit" class="btn" style="margin-top:12px">로그인</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>