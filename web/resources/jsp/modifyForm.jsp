<%--
  Created by IntelliJ IDEA.
  User: simi7
  Date: 2020-06-07
  Time: 오전 4:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Modify</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/index.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/memberService.css">
</head>
<body>
    <div class="container">
        <div class="infoBox">
            <form action="modifyResult" method="post">
                <%--                readonly는 폼 전송시 객체 전송이 되지만 disabled는 해당 객체는 폼으로 전송되지 않는다.--%>
                <table>
                    <tr>
                        <td>Id :</td>
                        <td><input type="text" name="memberId" readonly value=${member.memberId}></td>
                    </tr>
                    <tr>
                        <td>Pw :</td>
                        <td><input type="password" name="memberPw" required maxlength="10" placeholder=" 최대10자"></td>
                    </tr>
                    <tr>
                        <td>Email :</td>
                        <td><input type="email" name="memberEmail" required maxlength="20" value=${member.memberEmail}></td>
                    </tr>
                </table>
                <button type="submit">정보수정</button>
                <button type="reset"  onclick="location.href='/'">취소</button>
            </form>
        </div>
    </div>
</body>
</html>
