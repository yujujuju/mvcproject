<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/layout/header.jsp" %>

<script>
    function scrollSlider(sliderId, direction) {
        const slider = document.getElementById(sliderId);
        const itemWidth = 200; // 카드 폭 + 여백
        const scrollAmount = direction * itemWidth;

        // 현재 위치 + 이동 거리 계산
        const targetScrollLeft = slider.scrollLeft + scrollAmount;

        // 전체 길이, 보여지는 길이
        const maxScroll = slider.scrollWidth - slider.clientWidth;

        // 왼쪽 끝 도달 → 마지막으로 이동
        if (targetScrollLeft < 0) {
            slider.scrollTo({ left: maxScroll, behavior: 'smooth' });
        }
        // 오른쪽 끝 도달 → 처음으로 이동
        else if (targetScrollLeft > maxScroll) {
            slider.scrollTo({ left: 0, behavior: 'smooth' });
        }
        // 그 외 일반 이동
        else {
            slider.scrollBy({ left: scrollAmount, behavior: 'smooth' });
        }
    }
</script>

<main role="main">
    <section class="bg-light" style="min-height: 100vh;">
        <%@ include file="/WEB-INF/views/layout/menubar.jsp" %>

        <div class="container-fluid px-0">
            <div class="row no-gutters">
                <div class="col-12 d-flex justify-content-center">
                    <div class="content-wrapper px-4 py-4" style="max-width: 1200px; width: 100%;">

                        <!-- ✅ 최신 등록 도서 -->
                        <h4 class="font-weight-bold mb-3 d-flex justify-content-between align-items-center">
                            <span>최신 등록 도서</span>
                            <a href="${ctx}/book/bookList" class="text-secondary small">더보기 +</a>
                        </h4>

                        <div class="slider-container px-3 mb-5 position-relative">
                            <button class="scroll-btn left" onclick="scrollSlider('recentSlider', -1)">‹</button>

                            <div class="book-slider d-flex flex-nowrap" id="recentSlider">
                                <c:forEach var="book" items="${recentBooks}">
                                    <div class="slider-item text-center">
                                        <a href="${ctx}/book/detail/${book.bookId}">
                                            <img src="${ctx}${book.imagePath}" alt="${book.title}">
                                        </a>
                                        <p class="mt-2 small text-truncate" title="${book.title}">${book.title}</p>
                                    </div>
                                </c:forEach>
                            </div>

                            <button class="scroll-btn right" onclick="scrollSlider('recentSlider', 1)">›</button>
                        </div>

                        <!-- ✅ 베스트 리뷰 도서 -->
                        <h4 class="font-weight-bold mb-3 d-flex justify-content-between align-items-center">
                            <span>베스트 리뷰 도서</span>
                        </h4>

                        <div class="slider-container px-3 position-relative">
                            <button class="scroll-btn left" onclick="scrollSlider('bestSlider', -1)">‹</button>

                            <div class="book-slider d-flex flex-nowrap" id="bestSlider">
                                <c:forEach var="book" items="${topReviewBooks}" varStatus="status">
                                    <div class="slider-item text-center">
                                        <span class="badge bg-review-best mb-1">Top ${status.index + 1}</span>
                                        <a href="${ctx}/book/detail/${book.bookId}">
                                            <img src="${ctx}${book.imagePath}" alt="${book.title}">
                                        </a>
                                        <p class="mt-2 small text-truncate" title="${book.title}">${book.title}</p>
                                        <div class="text-muted small mt-1">⭐ ${book.avgRating}점</div>
                                    </div>
                                </c:forEach>
                            </div>

                            <button class="scroll-btn right" onclick="scrollSlider('bestSlider', 1)">›</button>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </section>
</main>
