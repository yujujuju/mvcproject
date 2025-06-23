<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${ctx}/css/main.css">

<nav class="col-md-2 d-none d-md-block bg-light sidebar"  style="height: 100vh;">
    <div class="sidebar-sticky" style="height: 100vh;">
        <ul class="nav flex-column">

            <!-- 관리자 메뉴 -->
            <c:if test="${sessionScope.loginUser.role == 'ADMIN'}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/book/register">
                        <span data-feather="book-open"></span>
                        도서 등록
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/book/requestList">
                        <span data-feather="book-open"></span>
                        도서 요청 현황
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/book/list">
                        <span data-feather="book-open"></span>
                        등록 도서 목록
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">
                        <span data-feather="user"></span>
                        회원 목록 조회
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/book-requests">
                        <span data-feather="clipboard"></span>
                        도서 요청 관리
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/review-manage">
                        <span data-feather="message-square"></span>
                        리뷰/댓글 관리
                    </a>
                </li>
            </c:if>

            <!-- 사용자 메뉴 -->
            <c:if test="${sessionScope.loginUser.role == 'USER'}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/user/requestBook">
                        <span data-feather="plus-square"></span>
                        도서 요청하기
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/user/requestList">
                        <span data-feather="book-open"></span>
                        도서 요청 현황
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/user/reviews">
                        <span data-feather="edit-3"></span>
                        내 리뷰 보기
                    </a>
                </li>
            </c:if>

        </ul>
    </div>
</nav>
