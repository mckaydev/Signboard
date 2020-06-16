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
            <form action="joinResult" method="post">
                <table>
                    <tr>
                        <td>Id :</td>
                        <td><input type="text" name="memberId" required maxlength="10" placeholder=" 최대10자"></td>
                    </tr>
                    <tr>
                        <td>Pw :</td>
                        <td><input type="password" name="memberPw" required maxlength="10" placeholder=" 최대10자"></td>
                    </tr>
                    <tr>
                        <td>Email :</td>
                        <td><input type="email" name="memberEmail" required maxlength="20" placeholder=" 최대20자"></td>
                    </tr>
                </table>
                <button style="margin-top: 10px" type="submit">회원가입</button>
                <button style="margin-top: 10px" type="reset"  onclick="location.href='/'">취소</button>
            </form>
        </div>
    </div>
</body>
</html>
