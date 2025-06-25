
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="/WEB-INF/views/layout/header.jsp" %>

<script>
  function scrollSlider(direction) {
    const slider = document.getElementById("bookSlider");
    const itemWidth = 200; // 카드 180 + 여백 20
    slider.scrollBy({
      left: direction * itemWidth,
      behavior: "smooth"
    });
  }
</script>


<main role="main">
  <section class="bg-light" style="min-height: 100vh;">
        <%@ include file="/WEB-INF/views/layout/menubar.jsp" %>
            <div class="container-fluid px-0">
              <div class="row no-gutters">

        <!-- ✅ 사이드바 -->

        <!-- ✅ 본문 -->
        <div class="col-12 d-flex justify-content-center">
          <div class="content-wrapper px-4 py-4" style="max-width: 1200px; width: 100%;">
            <h4 class="font-weight-bold mb-3 d-flex justify-content-between align-items-center">
                <span>최신 등록 도서</span>
                <a href="${ctx}/admin/book/list" class="text-secondary small">더보기 +</a>
              </h4>

          <div class="slider-container px-3">
            <button class="scroll-btn left" onclick="scrollSlider(-1)">‹</button>

            <div class="book-slider d-flex" id="bookSlider">
              <c:forEach var="book" items="${recentBooks}">
                <div class="slider-item text-center">
                  <img src="${ctx}${book.imagePath}" alt="${book.title}">
                  <p class="mt-2 small text-truncate" title="${book.title}">${book.title}</p>
                </div>
              </c:forEach>
            </div>

            <button class="scroll-btn right" onclick="scrollSlider(1)">›</button>
          </div>
        </div>
      </div>
    </div>
  </section>
</main>