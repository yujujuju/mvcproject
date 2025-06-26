<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/layout/header.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>
    $(function() {

        $('#reviewForm').on('submit', function(e) {
            const content = $('textarea[name="content"]').val().trim();
            const rating = parseFloat($('#rating').val());

            $('#content').on('input', function () {
                const length = $(this).val().length;
                $('#charCount').text(length + "/3000");
            });

            if (content.length < 10) {
                alert("리뷰 내용을 10자 이상 입력해주세요.");
                $('#content').focus();
                e.preventDefault();
                return false;
            }

            if (content === '') {
                alert('리뷰 내용을 입력해주세요.');
                e.preventDefault();
                return;
            }

            if (isNaN(rating) || rating < 0.5 || rating > 5) {
                alert('별점은 0.5점 이상 5점 이하로 선택해야 합니다.');
                e.preventDefault();
                return false;
            }
        });

        $('#star-rating i').on('mousemove click', function (e) {
            const index = parseInt($(this).data('index'));
            const offset = $(this).offset();
            const half = (e.pageX - offset.left) < ($(this).width() / 2);
            let rating = index - (half ? 0.5 : 0);
            $('#rating').val(rating);

            $('#star-rating i').each(function (i) {
                $(this).removeClass('fa-solid fa-star fa-star-half-stroke active half');
                const current = i + 1;
                if (current <= rating) {
                    $(this).addClass('fa-solid fa-star active');
                } else if (current - 0.5 === rating) {
                    $(this).addClass('fa-solid fa-star-half-stroke active');
                } else {
                    $(this).addClass('fa-regular fa-star');
                }
            });
        });

        $('#star-rating').on('mouseleave', function () {
            const rating = parseFloat($('#rating').val(rating.toFixed(1)));
            $('#star-rating i').each(function (i) {
                $(this).removeClass('fa-solid fa-star fa-star-half-stroke active half');
                const current = i + 1;
                if (current <= rating) {
                    $(this).addClass('fa-solid fa-star active');
                } else if (current - 0.5 === rating) {
                    $(this).addClass('fa-solid fa-star-half-stroke active');
                } else {
                    $(this).addClass('fa-regular fa-star');
                }
            });
        });
    });
</script>

<main role="main">
    <%@ include file="/WEB-INF/views/layout/menubar.jsp" %>

    <!-- 리뷰 성공/실패 메세지 -->
    <c:if test="${not empty success}">
        <div class="alert alert-success alert-dismissible fade show mt-3" role="alert">
                ${success}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show mt-3" role="alert">
                ${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <!-- 도서 정보 -->
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

    <hr>

    <!-- 리뷰 -->
    <div class="container mt-4">

        <!-- 리뷰 등록 폼 -->
        <form id="reviewForm" action="${pageContext.request.contextPath}/review/write" method="post" class="mt-4">
            <input type="hidden" name="bookId" value="${book.bookId}">
            <div class="mb-2">
                <label for="content">리뷰 작성</label>
                <textarea id="content" name="content" class="form-control" rows="4" maxlength="3000"
                          placeholder="내용을 10자 이상 입력해주세요. 주제와 무관한 댓글, 악플등의 글은 삭제될 수 있습니다."></textarea>
            </div>
            <div class="mb-3">
                <label class="form-label">별점</label>
                <div class="star-rating" id="star-rating">
                    <input type="hidden" name="rating" id="rating" value="1.0" />
                    <i class="fa-regular fa-star" data-index="1"></i>
                    <i class="fa-regular fa-star" data-index="2"></i>
                    <i class="fa-regular fa-star" data-index="3"></i>
                    <i class="fa-regular fa-star" data-index="4"></i>
                    <i class="fa-regular fa-star" data-index="5"></i>
                </div>
            </div>
            <div class="text-end">
                <button type="submit" class="btn btn-primary">리뷰 등록</button>
            </div>
        </form>


        <!-- 리뷰 목록 -->
        <c:choose>
            <c:when test="${empty reviewList}">
                <p class="text-muted">등록된 리뷰가 없습니다.</p>
            </c:when>
            <c:otherwise>
                <c:forEach var="review" items="${reviewList}">
                    <div class="border-bottom pb-3 mb-3">
                        <strong>${review.nickname}</strong>
                        <span class="text-muted small"> |
                        <fmt:formatDate value="${review.createdDate}" pattern="yyyy.MM.dd"/>
                    </span>
                        <p class="mt-2">${review.content}</p>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>

        <!-- 더보기 버튼 -->
        <div class="text-center mt-4">
            <button class="btn btn-outline-secondary">+ 더보기</button>
        </div>
    </div>



</main>
