<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/layout/header.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<main role="main">
    <%@ include file="/WEB-INF/views/layout/menubar.jsp" %>
    <div class="container mt-5">
        <div class="row mt-4">
            <div class="col-md-4">
                <img src="${pageContext.request.contextPath}${book.imagePath}" class="img-thumbnail" alt="썸네일">
            </div>
            <div class="col-md-8">
                <p style="font-size: 25px;"><strong>${book.title}</strong></p>
                <p>${book.author} 저 | ${book.publisher}</p>
                <p><fmt:formatDate value="${book.pubDate}" pattern="yyyy-MM-dd"/></p>
                <hr>
                <p>${book.description}</p>
            </div>
        </div>
    </div>

    <!-- 도서 정보 끝나고 -->
    <hr>

    <div class="container mt-4">
        <h5 class="mb-3">리뷰</h5>

        <!-- 리뷰 리스트 출력 -->
        <%--<c:forEach var="review" items="${reviewList}">--%>
            <div class="mb-4 pb-3 border-bottom">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <strong>닉네임</strong>
                        <span class="text-muted small"> | <fmt:formatDate value="${review.createdDate}" pattern="yyyy.MM.dd"/></span>
                    </div>
                    <div>
                        <span class="badge bg-light text-dark">⭐ 별점</span>
                    </div>
                </div>
                <p class="mt-2 mb-1">코멘트</p>
            </div>
        <%--</c:forEach>
--%>
        <!-- 더보기 버튼 -->
        <div class="text-center mt-4">
            <button class="btn btn-outline-secondary">+ 더보기</button>
        </div>
    </div>



</main>
