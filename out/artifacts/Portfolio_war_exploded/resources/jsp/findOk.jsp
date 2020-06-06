<%--
  Created by IntelliJ IDEA.
  User: simi7
  Date: 2020-06-02
  Time: 오후 5:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Find</title>
    <link rel="stylesheet" type="text/css" href="resources/css/index.css">
    <link rel="stylesheet" type="text/css" href="resources/css/inputImageSuccess.css">
    <link rel="stylesheet" type="text/css" href="resources/css/findOk.css">
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
        <div class="infoBox" id="infoBox1">
            <div class="infoImgArea">
    <%--            ${getOriginalFilename}--%>
                <img class="infoImg" src="/img/test.jpg" alt="img0"/>
            </div>
            <div class="infoTextArea">
                <p class="infoText">
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
        </div>
        <div class="infoBox" id="infoBox2">
            <div class="infoImgArea">
                <img class="infoImg" src="/img/test (1).gif" alt="img1"/>
            </div>
            <div class="infoTextArea">
                <p class="infoText">
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
        </div>
        <div class="infoBox" id="infoBox3">
            <div class="infoImgArea">
                <img class="infoImg" src="/img/test (2).jpg" alt="img2"/>
            </div>
            <div class="infoTextArea">
                <p class="infoText">
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
        </div>
        <div class="infoBox" id="infoBox4">
            <div class="infoImgArea">
                <img class="infoImg" src="/img/test (3).jpg" alt="img3"/>
            </div>
            <div class="infoTextArea">
                <p class="infoText">
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
        </div>
        <div class="infoBox" id="infoBox5">
            <div class="infoImgArea">
                <img class="infoImg" src="/img/test (4).jpg" alt="img0"/>
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
        </div>
        <div class="infoBox" id="infoBox6">
            <div class="infoImgArea">
                <img class="infoImg" src="/img/test (5).jpg" alt="img1"/>
            </div>
            <div class="infoTextArea">
                <p class="infoText">
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
        </div>
        <div class="infoBox" id="infoBox7">
            <div class="infoImgArea">
                <img class="infoImg" src="/img/test (6).jpg" alt="img2"/>
            </div>
            <div class="infoTextArea">
                <p class="infoText">
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
        </div>
        <div class="infoBox" id="infoBox8">
            <div class="infoImgArea">
                <img class="infoImg" src="/img/test (7).jpg" alt="img3"/>
            </div>
            <div class="infoTextArea">
                <p class="infoText">
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
        </div>
        <button class="reInputButton" onclick="location.href='/'">메인화면</button>
    </div>
</body>
</html>
