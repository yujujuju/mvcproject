<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/layout/header.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>
    const contextPath = '${pageContext.request.contextPath}';
    const bookId = '${book.bookId}';
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
        <div class="text-end mb-2">
            <a href="${pageContext.request.contextPath}/book/bookList" class="btn btn-sm text-white"
               style="background-color: #739db0; border-color: #759eb1; margin-top: 20px">
                목록으로
            </a>
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
                    <button type="submit" class="btn btn-primary" style="margin-left: 30px; background-color: #739db0; border-color: #759eb1">등록</button>
                </div>
            </div>
        </form>
        <hr>
        <hr>

        <!-- 리뷰 목록 -->
        <c:choose>
            <c:when test="${empty reviewList}">
                <p class="text-muted">등록된 리뷰가 없습니다.</p>
            </c:when>
            <c:otherwise>
                <c:forEach var="review" items="${reviewList}">
                    <div class="review-item border-bottom pb-3 mb-3"
                         id="review-${review.reviewId}"
                         data-rating="${review.rating}"
                         data-content="${fn:escapeXml(review.content)}">


                        <div class="d-flex justify-content-between align-items-center">
                            <strong>${review.nickname}</strong>

                            <div class="d-flex align-items-center gap-2">
                                <!-- 닉네임 + 수정/삭제 -->
                                <c:if test="${loginUser != null && loginUser.userId eq review.userId}">
                                    <div class="text-muted small">
                                        <a href="javascript:void(0);" onclick="editReview(${review.reviewId})">수정</a> |
                                        <a href="javascript:void(0);" class="text-danger" onclick="deleteReview(${review.reviewId})">삭제</a>
                                    </div>
                                </c:if>

                                <button class="like-btn btn btn-sm border-0"
                                        data-review-id="${review.reviewId}"
                                        <c:if test="${empty loginUser}">disabled</c:if>>
                                    <i class="fa-heart <c:out value='${review.likedByUser ? "fa-solid text-danger" : "fa-regular"}'/>"></i>
                                    <span class="like-count">${review.likeCount}</span>
                                </button>
                            </div>
                        </div>

                        <!-- 날짜 + 별점 -->
                        <div class="text-muted small d-flex align-items-center mt-1">
                    <span>
                        <fmt:formatDate value="${review.createDate}" pattern="yyyy.MM.dd" /> |
                    </span>
                            <span class="ms-2 text-warning" style="margin-left: 20px;">
                        <c:forEach var="i" begin="1" end="5">
                            <c:choose>
                                <c:when test="${i <= review.rating}">
                                    <i class="fa-solid fa-star"></i>
                                </c:when>
                                <c:when test="${i - review.rating <= 0.5}">
                                    <i class="fa-solid fa-star-half-stroke"></i>
                                </c:when>
                                <c:otherwise>
                                    <i class="fa-regular fa-star"></i>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </span>
                        </div>

                        <!-- 본문 내용 영역 (수정 시 이 영역만 바뀜) -->
                        <div class="review-body mt-2">
                            <p class="review-content">${review.content}</p>
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>

    </div>
</main>
<script src="${pageContext.request.contextPath}/js/review.js"></script>