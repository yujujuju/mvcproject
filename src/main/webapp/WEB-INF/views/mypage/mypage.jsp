<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/layout/header.jsp" %>

<!doctype html>
<html lang="ko">

<body>
<div class="container-fluid">
    <div class="row" style="height: 100%">

        <%@ include file="/WEB-INF/views/layout/sidebar.jsp" %>

        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-4 px-4">
            <h2 class="mb-4">마이페이지</h2>

            <div class="card shadow-sm" style="max-width: 600px;">
                <div class="card-header bg-primary text-white">
                    <strong>회원 정보</strong>
                </div>
                <div class="card-body">
                    <p class="mb-2"><strong>아이디:</strong> ${sessionScope.loginUser.userId}</p>
                    <p class="mb-2"><strong>닉네임:</strong> ${sessionScope.loginUser.nickname}</p>
                </div>
            </div>
        </main>

    </div>
</div>

<!-- JS -->
<script>feather.replace()</script>
</body>
</html>