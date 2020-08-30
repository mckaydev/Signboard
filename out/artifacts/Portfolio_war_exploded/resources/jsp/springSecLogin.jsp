<%--
  Created by IntelliJ IDEA.
  User: simi7
  Date: 2020-08-29
  Time: 오전 10:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="SecLoginResult" method="post">
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
        <button type="reset"  onclick="location.href='/'">취소</button>
    </form>
</body>
</html>
