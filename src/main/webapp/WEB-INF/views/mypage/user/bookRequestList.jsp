<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/views/layout/header.jsp" %>

<!doctype html>
<html lang="ko">

<body>
<div class="container-fluid">
    <div class="row">

        <%@ include file="/WEB-INF/views/layout/sidebar.jsp" %>

        <main class="col-md-10 px-4 pt-4" style="min-height: 100vh;">
            <h2 class="mb-4">도서 요청 현황</h2>

            <table class="table table-hover table-bordered align-middle text-center">
                <thead class="table-light">
                <tr>
                    <th>번호</th>
                    <th>제목</th>
                    <th>저자</th>
                    <th>출판사</th>
                    <th>요청일</th>
                    <th>상태</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty requestList}">
                        <c:forEach var="book" items="${requestList}" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${book.title}</td>
                                <td>${book.author}</td>
                                <td>${book.publisher}</td>
                                <td>
                                    <fmt:formatDate value="${book.requestDate}" pattern="yyyy-MM-dd" />
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${book.status == '요청'}">
                                            <span class="badge bg-info text-dark">요청</span>
                                        </c:when>
                                        <c:when test="${book.status == '승인'}">
                                            <span class="badge bg-success">승인</span>
                                        </c:when>
                                        <c:when test="${book.status == '거절'}">
                                            <span class="badge bg-danger">거절</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-light text-dark">${book.status}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="6" class="text-center text-muted">요청한 도서가 없습니다.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>


        </main>

    </div>
</div>

<!-- JS -->
<script>feather.replace()</script>
</body>
</html>