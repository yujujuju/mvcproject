<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<!doctype html>
<html lang="ko">
    <head>
        <meta charset="utf-8">
        <title>로그인</title>

        <script>
            $(document).ready(function () {

                $('.form-signin').on('submit', function () {
                    const userId = $('#userId').val();
                    const password = $('#password').val();
                    console.log("서버에 전송할 ID:", userId);
                    console.log("서버에 전송할 PW:", password);

                });
            });
        </script>
    </head>


    <body class="text-center">
        <form class="form-signin" action="${pageContext.request.contextPath}/user/login" method="post">
            <img class="mb-4" src="${pageContext.request.contextPath}/images/logo.png" alt="">

            <c:if test="${not empty msg}">
                <div class="alert alert-danger" role="alert">
                        ${msg}
                </div>
            </c:if>

            <label for="userId" class="sr-only">아이디</label>
            <input type="text" id="userId" name="userId" class="form-control" placeholder="아이디" required autofocus>

            <label for="password" class="sr-only">비밀번호</label>
            <input type="password" id="password" name="password" class="form-control" placeholder="비밀번호" required>

            <button class="btn btn-lg btn-primary btn-block" type="submit">로그인</button>
            <p class="mt-5 mb-3 text-muted">&copy; 2025</p>
        </form>
    </body>
</html>
