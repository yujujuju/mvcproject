<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
    <meta charset="UTF-8">
    <title>책갈피</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">

    <!-- Optional Theme (선택) -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap-theme.min.css">

    <!-- 내 CSS -->
    <link rel="stylesheet" href="${ctx}/css/main.css">
</head>
<body>

<!-- 공통 헤더 (navbar) -->
<header>
    <div class="navbar navbar-dark bg-dark shadow-sm">
        <div class="container d-flex justify-content-between align-items-center">

            <!-- 로고 -->
            <a href="${ctx}/" class="navbar-brand">
                <img src="${ctx}/images/logo.png" alt="책갈피" class="logo-img" height="40">
            </a>

            <!-- 로그인 / 회원가입 버튼 -->
            <div>
                <a href="${ctx}/login.jsp" class="btn btn-outline-light mr-2">로그인</a>
                <a href="${ctx}/user/signup" class="btn btn-outline-light">회원가입</a>
            </div>
        </div>
    </div>
</header>
