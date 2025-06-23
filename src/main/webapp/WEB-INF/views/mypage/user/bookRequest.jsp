<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/layout/header.jsp" %>

<!doctype html>
<html lang="ko">

<body>
<div class="container-fluid">
    <div class="row">

        <%@ include file="/WEB-INF/views/layout/sidebar.jsp" %>

        <main class="col-md-10 px-4 pt-4" style="min-height: 100vh;">
            <h2 class="mb-4">도서 요청</h2>

            <p class="text-muted" style="font-size: 0.95rem;">
                ※ 하루 도서 요청 가능 횟수는 <strong>3회</strong>입니다.
            </p>

            <c:if test="${not empty todayCount}">
                <p class="text-muted mb-4" style="font-size: 0.95rem;">
                    오늘 <strong>${todayCount}/3회</strong> 도서 요청하셨습니다.
                </p>
            </c:if>

            <c:if test="${not empty msg}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                        ${msg}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>

            <form:form modelAttribute="requestBook"
                       method="post"
                       action="${pageContext.request.contextPath}/user/requestBook"
                       class="bg-white p-4 shadow-sm border rounded"
                       style="max-width:600px; margin-bottom: 100px;">

                <div class="form-group">
                    <label for="title">도서 제목</label>
                    <form:input path="title" class="form-control" id="title" />
                </div>

                <div class="form-group">
                    <label for="author">작가</label>
                    <form:input path="author" class="form-control" id="author"/>
                </div>

                <div class="form-group">
                    <label for="publisher">출판사</label>
                    <form:input path="publisher" class="form-control" id="publisher" />
                </div>

                <button type="submit" class="btn btn-primary mt-3">도서 요청하기</button>
            </form:form>

        </main>

    </div>
</div>

<!-- JS -->
<script>feather.replace()</script>
</body>
</html>