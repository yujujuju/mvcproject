<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>로그인</title></head>
<body>
<h2>로그인 페이지</h2>
<form action="/user/login" method="post">
  <input type="text" name="userId" placeholder="아이디">
  <input type="password" name="password" placeholder="비밀번호">
  <button type="submit">로그인</button>
</form>
</body>
</html>
