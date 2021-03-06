<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: simi7
  Date: 2020-06-06
  Time: 오후 8:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>joinForm</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/index.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/memberService.css">
</head>
<body>
    <div class="container">
        <div class="infoBox">
            <form action="/member/joinProcess" method="post">
                <table style="margin: auto">
                    <tr>
                        <td>Id :</td>
                        <td><input type="text" name="username" required maxlength="10" placeholder=" 최대10자"></td>
                    </tr>
                    <tr>
                        <td>Pw :</td>
                        <td><input type="password" name="password" required maxlength="10" placeholder=" 최대10자"></td>
                    </tr>
                    <tr>
                        <td>Email :</td>
                        <td><input type="email" name="email" required maxlength="20" placeholder=" 최대20자"></td>
                    </tr>
                </table>
                <button type="submit">회원가입</button>
                <button type="reset"  onclick="location.href='../../..'">취소</button>
            </form>
        </div>
    </div>
</body>
</html>
