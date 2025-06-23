<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/layout/header.jsp" %>

<div class="container-fluid">
  <div class="row">
    <%@ include file="/WEB-INF/views/layout/sidebar.jsp" %>
    <main class="col-md-10 px-4 pt-4">
      <h2 class="mb-4">도서 요청 상세</h2>

      <table class="table table-bordered">
        <tr><th>제목</th><td>${request.title}</td></tr>
        <tr><th>저자</th><td>${request.author}</td></tr>
        <tr><th>출판사</th><td>${request.publisher}</td></tr>
        <tr><th>요청자</th><td>${request.userId}</td></tr>
        <tr><th>요청일</th>
          <td><fmt:formatDate value="${request.requestDate}" pattern="yyyy-MM-dd" /></td>
        </tr>
        <tr><th>상태</th><td>${request.status}</td></tr>
        <c:if test="${not empty request.rejectReason}">
          <tr><th>거절 사유</th><td>${request.rejectReason}</td></tr>
        </c:if>
      </table>

      <c:if test="${request.status == '요청'}">
        <form method="post" action="${pageContext.request.contextPath}/admin/book/approve" class="d-inline">
          <input type="hidden" name="requestId" value="${request.requestId}">
          <button type="submit" class="btn btn-success">승인</button>
        </form>

        <form method="post" action="${pageContext.request.contextPath}/admin/book/reject" class="d-inline mt-2">
          <input type="hidden" name="requestId" value="${request.requestId}">
          <textarea name="rejectReason" placeholder="거절 사유 입력" required class="form-control mt-2"></textarea>
          <button type="submit" class="btn btn-danger mt-2">거절</button>
        </form>
      </c:if>

      <c:if test="${not empty msg}">
        <script>
          alert("${msg}");
        </script>
      </c:if>
    </main>
  </div>
</div>
