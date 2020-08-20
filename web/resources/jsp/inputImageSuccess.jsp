<%--
  Created by IntelliJ IDEA.
  User: simi7
  Date: 2020-06-02
  Time: 오전 2:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Image Input Success</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/index.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/inputImageSuccess.css">
</head>
<body>
    <c:if test="${empty member}">
        <button class="signIn" onclick="location.href='/member/loginForm'">로그인</button>
        <button class="signUp" onclick="location.href='/member/joinForm'">회원가입</button>
    </c:if>
    <c:if test="${!empty member}">
        <button class="modify" onclick="location.href='/member/modifyForm'">정보 수정</button>
        <button class="logout" onclick="location.href='/member/logout'">로그 아웃</button>
        <button class="remove" onclick="location.href='/member/removeForm'">회원 탈퇴</button>
    </c:if>
    <div class="container">
        <div class="infoBox">
            <div class="infoImgArea">
                <img class="infoImg" src="/img/${getOriginalFilename}" alt="img"/>
            </div>
            <div class="infoTextArea">
                OCR: <input class="infoText" value="${ocrResult}">
                검색결과: <input class="infoText" id="title"><br><br>
<%--                <p>메뉴 : </p>--%>
<%--                <ul class="infoText" style="padding-left: 25px">--%>
<%--                    <li>짬뽕 5000원</li>--%>
<%--                    <li>짜장면 5000원</li>--%>
<%--                    <li>간짜장 5000원</li>--%>
<%--                    <li>울면 5000원</li>--%>
<%--                </ul> <br>--%>
<%--                <p class="infoText" >영업 시간 : 09:00 ~ 21:00</p><br>--%>
                <p id="category"></p> <br>
                <p id="roadAddress"></p> <br>
                <p id="link"></p>
            </div>
            <form action="storeData" method="post">
                <input style="display: none" type="text" name="imageFileName" value="${getOriginalFilename}">
                <input style="display: none" type="text" name="storeName" value="${ocrResult}">
                <input style="display: none" type="text" name="storeMenu" value="menu test data">
                <input style="display: none" type="text" name="storePhone" value="02-1234-5678">
                <input type="text" id="aLineReview" name="aLineReview" placeholder="한줄평을 써주세요.">
                <button type="submit" class="reInputButton">메인화면(기록 저장)</button>
            </form>
            <script>
                let searchResult = ${searchResult};
                document.getElementById("category").textContent = searchResult['items'][0]['category'];
                document.getElementById("roadAddress").textContent = searchResult['items'][0]['roadAddress'];
                document.getElementById("link").textContent = searchResult['items'][0]['link'];
                document.getElementById("title").value = searchResult['items'][0]['title'];
            </script>
        </div>
    </div>
    <script>
        let jsonF = ${searchResult};
        let roadAddress = jsonF[0]['items']['roadAddress'];

        document.getElementById("testContent").innerText = "tt";
    </script>
</body>
</html>