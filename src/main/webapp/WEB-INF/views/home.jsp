

<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="/WEB-INF/views/header.jsp" %>

<main role="main">
  <section class="py-5 bg-light">
    <div class="container">
      <div class="row">
        <%-- 도서 리스트 반복 카드 --%>
        <div class="col-md-4">
          <div class="card mb-4 shadow-sm">
            <img src="book-cover.jpg" class="bd-placeholder-img card-img-top" width="100%" height="225" alt="책 표지">
            <div class="card-body">
              <h5 class="card-title">도서 제목</h5>
              <p class="card-text">저자: 홍길동<br>★ 4.5</p>
              <div class="d-flex justify-content-between align-items-center">
                <a href="bookDetail.jsp?id=1" class="btn btn-sm btn-outline-secondary">자세히 보기</a>
              </div>
            </div>
          </div>
        </div>
        <%-- 여기서 도서 카드 반복 --%>
      </div>
    </div>
  </section>
</main>

<!-- Bootstrap JS + jQuery -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>

</body>
</html>
