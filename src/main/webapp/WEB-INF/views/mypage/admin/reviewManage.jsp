<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/layout/header.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>
    $(document).on('click', '.delete-btn', function () {
        const reviewId = $(this).data('id');

        if (confirm("해당 리뷰를 삭제하시겠습니까?")) {
            $.ajax({
                type: "POST",
                url: "review-manage",
                data: { reviewId: reviewId },
                success: function (result) {
                    console.log("서버 응답:", result);
                    if (Number(result) === 1) {
                        alert("삭제 완료되었습니다.");
                        location.reload();
                    } else {
                        alert("삭제 실패했습니다.");
                    }
                },
                error: function (xhr, status, err) {
                    console.error("에러 응답:", xhr.responseText);
                    alert("오류가 발생했습니다.");
                }
            });
        }
        $(this).closest('tr').remove(); // 새로고침 없이 DOM 삭제
    });
</script>

<div class="container-fluid">
    <div class="row row-full">
        <%@ include file="/WEB-INF/views/layout/sidebar.jsp" %>

        <!-- 본문 -->
        <main class="col-md-10 px-4 pt-4" style="min-height: 100vh;">
            <h2 class="mb-4">유저 리뷰 관리</h2>

            <table class="table table-bordered text-center align-middle">
                <thead class="table-light">
                <tr>
                    <th>번호</th>
                    <th>도서명</th>
                    <th>작성자</th>
                    <th>별점</th>
                    <th>내용</th>
                    <th>작성일</th>
                    <th>좋아요</th>
                    <th>삭제</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty reviewList}">
                        <c:forEach var="review" items="${reviewList}" varStatus="loop">
                            <tr>
                                <td>${paging.totalRecord - (paging.page - 1) * paging.pageSize - loop.index}</td>
                                <td>${review.bookTitle}</td>
                                <td>${review.nickname}</td>
                                <td>${review.rating}</td>
                                <td class="text-start review-content">${review.content}</td>
                                <td><fmt:formatDate value="${review.createDate}" pattern="yyyy-MM-dd" /></td>
                                <td>${review.likeCount}</td>
                                <td>
                                    <button class="btn btn-sm btn-danger delete-btn" data-id="${review.reviewId}">삭제</button>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="8" class="text-center text-muted py-4">
                                <i class="bi bi-emoji-frown" style="font-size: 2rem;"></i><br />
                                <strong>등록된 리뷰가 없습니다.</strong><br />
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>

            <!-- 페이징 -->
            <nav class="mt-3">
                <ul class="pagination justify-content-center">
                    <c:if test="${paging.page > 1}">
                        <li class="page-item">
                            <a class="page-link" href="?page=${paging.page - 1}">이전</a>
                        </li>
                    </c:if>

                    <c:forEach var="i" begin="1" end="${paging.totalPage}">
                        <li class="page-item ${paging.page == i ? 'active' : ''}">
                            <a class="page-link" href="?page=${i}">${i}</a>
                        </li>
                    </c:forEach>

                    <c:if test="${paging.page < paging.totalPage}">
                        <li class="page-item">
                            <a class="page-link" href="?page=${paging.page + 1}">다음</a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </main>
    </div>
</div>

<script>feather.replace()</script>
