<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/layout/header.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="container-fluid">
    <div class="row row-full">
        <%@ include file="/WEB-INF/views/layout/sidebar.jsp" %>

        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-4 px-4">
            <h2 class="mb-4">도서 목록</h2>

            <div class="row">
                <c:forEach var="book" items="${bookList}">
                    <div class="col-lg-4 text-center mb-5">
                        <img class="rounded-circle mb-3"
                             src="${pageContext.request.contextPath}${book.imagePath}"
                             alt="${book.title}"
                             width="140" height="140">

                        <h4>${book.title}</h4>
                        <p class="text-muted description-truncate">
                                ${fn:substring(book.description, 0, 100)}...
                        </p>

                        <a href="${pageContext.request.contextPath}/admin/book/detail/${book.bookId}" class="btn btn-secondary">
                            상세보기 &raquo;
                        </a>
                    </div>
                </c:forEach>
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
    </div>
</div>
