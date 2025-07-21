<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/layout/header.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


        <main role="main">
            <%@ include file="/WEB-INF/views/layout/menubar.jsp" %>
            <div class="container">

                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h2 class="section-title mb-0"><a href="${pageContext.request.contextPath}/book/bookList"> 도서 목록</a></h2>

                    <div class="search-wrapper d-flex align-items-center">
                        <form action="${pageContext.request.contextPath}/book/bookList" method="get" class="d-flex">
                            <input type="text" class="form-control search-input" name="keyword" value="${keyword}" placeholder="도서 검색">
                            <button type="submit" class="btn search-icon ms-2">
                                <i class="fa fa-search"></i>
                            </button>
                        </form>
                    </div>
                </div>

                <div class="row g-4 justify-content-center">
                <c:forEach var="book" items="${bookList}">
                    <div class="col-md-2 col-6 book-card">
                        <a href="${pageContext.request.contextPath}/book/detail/${book.bookId}">
                        <img class="book-cover"
                             src="${book.imagePath}"
                             alt="${book.title}"
                             width="140" height="140">
                        </a>
                        <h5 class="mt-2"> <a href="${pageContext.request.contextPath}/book/detail/${book.bookId}">
                        ${book.title}</a></h5>
                        <p class="text-muted">${book.author}</p>
                    </div>
                </c:forEach>

                <c:if test="${empty bookList}">
                    <div class="col-12 text-center text-muted py-5">
                        <i class="bi bi-emoji-frown" style="font-size: 2rem;"></i><br/>
                        <strong>검색 결과가 없습니다.</strong>
                    </div>
                </c:if>
            </div>
            </div>

            <!-- 페이징 처리 -->
            <div class="row">
                <div class="col d-flex justify-content-center mt-3 mb-0 pagination-wrapper">
                    <ul class="pagination">

                        <!-- 이전 버튼 -->
                        <c:if test="${paging.page > 1}">
                            <li class="page-item">
                                <a class="page-link" href="?page=${paging.page - 1}" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                        </c:if>

                        <!-- 페이지 번호 -->
                        <c:forEach begin="1" end="${paging.totalPage}" var="i">
                            <li class="page-item ${i == paging.page ? 'active' : ''}">
                                <a class="page-link" href="?page=${i}">${i}</a>
                            </li>
                        </c:forEach>

                        <!-- 다음 버튼 -->
                        <c:if test="${paging.page < paging.totalPage}">
                            <li class="page-item">
                                <a class="page-link" href="?page=${paging.page + 1}" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                        </c:if>

                    </ul>
                </div>
            </div>
        </main>
