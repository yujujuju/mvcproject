<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/layout/header.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script>
    $(document).ready(function () {
        $('#deleteBtn').click(function () {
            if (confirm('정말 삭제하시겠습니까?')) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/admin/book/delete/${book.bookId}',
                    type: 'POST',
                    success: function (response) {
                        alert('삭제되었습니다.');
                        window.location.href = '${pageContext.request.contextPath}/admin/book/list';
                    },
                    error: function () {
                        alert('삭제 중 오류가 발생했습니다.');
                    }
                });
            }
        });
    });
</script>

<div class="container-fluid">
    <div class="row row-full">
        <%@ include file="/WEB-INF/views/layout/sidebar.jsp" %>

        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-4 px-4 pb-5">
            <h2 class="mb-4">도서 목록</h2>

            <div class="container mt-5">
                <h2>${book.title}</h2>
                <div class="row mt-4">
                    <div class="col-md-4">
                        <img src="${pageContext.request.contextPath}${book.imagePath}" class="img-thumbnail" alt="썸네일">
                    </div>
                    <div class="col-md-8">
                        <p><strong>저자:</strong> ${book.author}</p>
                        <p><strong>출판사:</strong> ${book.publisher}</p>
                        <p><strong>출판일:</strong> ${book.pubDate}</p>
                        <p><strong>줄거리:</strong></p>
                        <p>${book.description}</p>
                    </div>
                </div>
                <div class="mt-4 mb-5">
                    <a href="${pageContext.request.contextPath}/admin/book/list" class="btn btn-secondary">목록으로</a>

                    <!-- 수정 버튼 -->
                    <a href="${pageContext.request.contextPath}/admin/book/edit/${book.bookId}" class="btn btn-primary ml-2">수정</a>

                    <!-- 삭제 버튼 (form으로 처리) -->
                    <form action="${pageContext.request.contextPath}/admin/book/delete/${book.bookId}" method="post" style="display:inline;">
                        <button type="button" id="deleteBtn" class="btn btn-danger ml-2">
                            삭제
                        </button>
                    </form>
                </div>

            </div>

        </main>
    </div>
</div>
