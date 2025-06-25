<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<html>
<head>
    <meta charset="UTF-8">
    <title>책갈피</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">

    <!-- jQuery CDN -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://unpkg.com/feather-icons/dist/feather.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <!-- 내 CSS -->
    <link rel="stylesheet" href="${ctx}/css/main.css">
    <link rel="stylesheet" href="${ctx}/css/book.css">
</head>

<body>

<!-- 공통 헤더 (navbar) -->
<header>
    <div class="navbar navbar-dark bg-dark shadow-sm">
        <div class="container d-flex justify-content-between align-items-center">

            <!-- 로고 -->
            <header class="main-header d-flex align-items-center px-4 py-2">
                <a href="${ctx}/" class="d-inline-block">
                    <img src="${ctx}/images/logo.png" alt="로고" class="site-logo">
                </a>
            </header>

            <div>
                <!-- 로그인 상태 확인 -->
                <c:choose>
                    <c:when test="${not empty sessionScope.loginUser}">
                        <span class="text-white mr-3">
                            <strong>${sessionScope.loginUser.nickname}</strong> 님 환영합니다!
                        </span>
                        <a href="${ctx}/user/logout" class="btn btn-outline-light">로그아웃</a>
                    </c:when>

                    <c:otherwise>
                        <a href="${ctx}/user/login" class="btn btn-outline-light mr-2">로그인</a>
                        <a href="${ctx}/user/signup" class="btn btn-outline-light">회원가입</a>
                    </c:otherwise>
                </c:choose>

                <!-- 로그인 했을 시 마이페이지 노출 -->
                <c:if test="${not empty sessionScope.loginUser}">
                    <a href="${pageContext.request.contextPath}/mypage" class="btn btn-outline-light mr-2">마이페이지</a>
                </c:if>

            </div>

        </div>
    </div>
</header>
