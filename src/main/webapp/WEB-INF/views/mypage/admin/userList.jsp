<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/layout/header.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container-fluid">
    <div class="row row-full">

        <%@ include file="/WEB-INF/views/layout/sidebar.jsp" %>
        <!-- 본문 -->
        <main class="col-md-10 px-4 pt-4" style="min-height: 100vh;">
            <h2 class="mb-4">
                회원 목록
            </h2>

            <form method="get" action="${pageContext.request.contextPath}/admin/user/list" class="mb-3">
                <select name="searchType" class="form-select d-inline w-auto">
                    <option value="nickname" ${searchType == 'nickname' ? 'selected' : ''}>닉네임</option>
                    <option value="userId" ${searchType == 'userId' ? 'selected' : ''}>아이디</option>
                </select>
                <input type="text" name="keyword" placeholder="검색어 입력" value="${searchName}" class="form-control d-inline w-auto ms-2"/>
                <button type="submit" class="btn btn-sm btn-primary ms-2">검색</button>
            </form>

            <table class="table table-bordered text-center">
                <thead class="table-light">
                <tr>
                    <th>아이디</th>
                    <th>닉네임</th>
                    <th>최근 접속일</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty userList}">
                        <c:forEach var="user" items="${userList}">
                            <tr>
                                <td>${user.userId}</td>
                                <td>${user.nickname}</td>
                                <td>
                                    <fmt:formatDate value="${user.lastLogin}" pattern="yyyy-MM-dd HH:mm" />
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="3" class="text-center text-muted py-4">
                                <i class="bi bi-emoji-frown" style="font-size: 2rem;"></i><br/>
                                <strong>검색 결과가 없습니다.</strong><br/>
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>

            </table>

            <nav class="mt-3">
                <ul class="pagination justify-content-center">
                    <c:forEach var="i" begin="1" end="${paging.totalPage}">
                        <li class="page-item ${paging.page == i ? 'active' : ''}">
                            <a class="page-link" href="?page=${i}&nickname=${searchName}">${i}</a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>


        </main>
    </div>
</div>

<script>feather.replace()</script>
