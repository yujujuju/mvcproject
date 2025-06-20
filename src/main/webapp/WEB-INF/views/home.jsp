
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="/WEB-INF/views/layout/header.jsp" %>

<main role="main">
  <section class="bg-light" style="min-height: 100vh;">
    <div class="container-fluid px-0">
      <div class="row no-gutters">

        <!-- 사이드바 -->
        <nav class="col-md-2 bg-white border-right" style="min-height: 100vh;">
          <div class="sidebar-sticky pt-4 pl-3">
            <ul class="nav flex-column">
              <li class="nav-item"><a class="nav-link active" href="#">🏠 메인</a></li>
              <li class="nav-item"><a class="nav-link" href="#">👤 마이페이지</a></li>
              <li class="nav-item"><a class="nav-link" href="#">📚 찜한 도서</a></li>
              <li class="nav-item"><a class="nav-link" href="#">📝 도서 요청</a></li>
            </ul>
          </div>
        </nav>

        <!-- 도서 카드 영역 -->
        <div class="col-md-10 px-4 py-4">
          <div class="row">
            <%--<c:forEach var="book" items="${bookList}">--%>
              <div class="col-md-4">
                <div class="card mb-4 shadow-sm">
                  <img src="${ctx}${book.imagePath}" class="card-img-top" height="225" alt="책 표지">
                  <div class="card-body">
                    <h5 class="card-title">${book.title}</h5>
                    <p class="card-text">저자: ${book.author}</p>
                    <a href="${ctx}/book/detail?id=${book.id}" class="btn btn-sm btn-outline-secondary">자세히 보기</a>
                  </div>
                </div>
              </div>
           <%-- </c:forEach>--%>
          </div>
        </div>

      </div>
    </div>
  </section>
</main>



<!-- Bootstrap JS + jQuery -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>

</body>
</html>
