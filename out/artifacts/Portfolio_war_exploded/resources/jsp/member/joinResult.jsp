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
    <title>joinResult</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/index.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/memberService.css">
</head>
<body>
    <div class="container">
        <div class="infoBox">
            <p>
                가입하신 정보입니다. <br>
                Id : ${member.memberId} <br>
                Pw : ${member.memberPw} <br>
                Email : ${member.memberEmail} <br>
                <button onclick="location.href='../../..'">메인화면</button>
            </p>
        </div>
    </div>
</body>
</html>
