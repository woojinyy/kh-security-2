<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>
<script>
    const login = (event) => {
        alert("로그인 호출");
        document.getElementById("f_login").submit();
    }
</script>
</head>
<body>
<h1>로그인 페이지</h1>
<form id="f_login" action="/login" method="post"><!-- 개발자가 처리하는 부분 -->
    <input type="text" name="username" placeholder="Username" /><br>
    <input type="password" name="password" placeholder="Password" /><br>
    <button type="button" onclick="login()">로그인</button>
</form>
<a href="/auth/joinForm.jsp">회원가입</a>
</body>
</html>