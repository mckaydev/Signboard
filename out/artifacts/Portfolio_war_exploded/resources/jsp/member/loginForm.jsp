<%--
  Created by IntelliJ IDEA.
  User: simi7
  Date: 2020-06-07
  Time: 오전 3:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>LogIn</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/index.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/memberService.css">
</head>
<body>
    <div class="container">
        <div class="infoBox">
            <form action="/member/loginProcess" method="post">
                <table style="margin: auto">
                    <tr>
                        <td>Id :</td>
                        <td><input type="text" name="username" required maxlength="10"></td>
                    </tr>
                    <tr>
                        <td>Pw :</td>
                        <td><input type="password" name="password" required maxlength="10"></td>
                    </tr>
                </table>
                <button type="submit">로그인</button>
                <button type="reset"  onclick="location.href='../../..'">취소</button>
            </form>
        </div>
    </div>
</body>
</html>
