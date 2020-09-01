<%--
  Created by IntelliJ IDEA.
  User: simi7
  Date: 2020-09-01
  Time: 오전 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/index.css">
    <style>
        img {
            width: 20vw;
        }
    </style>
</head>
<body>
    <c:if test="${pageContext.request.userPrincipal.name == null}">
        <button class="signIn" onclick="location.href='/member/login'">로그인</button>
        <button class="signUp" onclick="location.href='/member/join'">회원가입</button>
    </c:if>
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <button class="modify" onclick="location.href='/member/modify'">정보 수정</button>
        <button class="logout" onclick="location.href='/member/logout'">로그 아웃</button>
        <button class="remove" onclick="location.href='/member/remove'">회원 탈퇴</button>
    </c:if>
    <div class="container">
        <div class="infoBox">
            <p>사진을 선택해 주세요.</p>
            <div style="border-bottom: black 1px solid; margin-bottom: 5px"></div>
            <img src="/resources/img/example/은진식당%20gps.jpg" onclick=imgIn1()>
            <p>은진식당</p>
            <p>37.2771652, 127.0372538</p>
            <div style="border-bottom: black 1px solid; margin-bottom: 5px"></div>

            <img src="/resources/img/example/스타벅스%20북촌로점%20gps.jpg" onclick=imgIn2()>
            <p>스타벅스 북촌로점</p>
            <p>37.5794488, 126.9864673</p>
            <div style="border-bottom: black 1px solid; margin-bottom: 5px"></div>
            <button onclick="location.href='/'">메인으로</button>

            <form id="inputForm" action="cropExample" method="post">
                <input id="imageIn" name="exampleImg" style="display: none">
                <button style="display: none" type="submit"></button>
            </form>
            <script>
                function imgIn1() {
                    document.getElementById("imageIn").value = "1";
                    document.getElementById("inputForm").submit();
                }
                function imgIn2() {
                    document.getElementById("imageIn").value = "2";
                    document.getElementById("inputForm").submit();
                }
            </script>
        </div>
    </div>
</body>
</html>
