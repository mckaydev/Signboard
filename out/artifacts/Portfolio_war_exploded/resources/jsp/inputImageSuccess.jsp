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
    <link rel="stylesheet" type="text/css" href="resources/css/index.css">
    <link rel="stylesheet" type="text/css" href="resources/css/inputImageSuccess.css">
</head>
<body>
    <div class="container">
        <div class="infoBox">
            <div class="infoImgArea">
                <img id="infoImg" src="/img/${getOriginalFilename}" alt="img"/>
            </div>
            <div class="infoTextArea">
                <p id="infoText">
                    ★4.7 주타비네 가게<br><br>
                    메뉴 : </p>
                <ul style="padding-left: 25px">
                    <li>짬뽕 5000원</li>
                    <li>짜장면 5000원</li>
                    <li>간짜장 5000원</li>
                    <li>울면 5000원</li>
                </ul> <br>
                <p>
                영업 시간 : 09:00 ~ 21:00
                </p>
            </div>
            <div class="ButtonArea">
                <button class="reInputButton"  onclick="location.href='/'">사진 재입력</button>
            </div>
        </div>
    </div>
</body>
</html>