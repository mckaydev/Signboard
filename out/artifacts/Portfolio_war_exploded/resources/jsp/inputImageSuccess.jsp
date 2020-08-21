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
    <script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=ai9wkb82y8"></script>
    <script src="/resources/js/naverMap.js"></script>
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
            <div class="infoMapArea">
                <div id="map" class="infoImg"></div>
            </div>
            <script>
            </script>
            <div class="infoTextArea">
                <table>
                    <tr>
                        <td>OCR :</td>
                        <td><input class="infoText" value="${ocrResult}"><br></td>
                    </tr>
                    <tr>
                        <td>상호명 :</td>
                        <td><input class="infoText" id="title"></td>
                    </tr>
                </table><br>
                <table>
                    <tr>
                        <td><p>추천 메뉴 : </p></td>
                        <td><p style="color: red">(테스트용)</p></td>
                    </tr>
                    <tr>
                        <td><p>스위티 자몽&라임 블렌디드</p></td>
                        <td><p>6,300원</p></td></tr>
                    <tr>
                        <td><p>스위트 멜론 블렌디드</p></td>
                        <td><p>6,300원</p></td></tr>
                    <tr>
                        <td><p>블론드 서머 라떼</p></td>
                        <td><p>5,900원</p></td></tr>
                    <tr>
                        <td><p>카페 아메리카노</p></td>
                        <td><p>4,100원</p></td></tr>
                </table><br>
                <table>
                    <tr><td><p id="category"></p></td></tr>
                    <tr><td><p id="roadAddress"></p></td></tr>
                    <tr><td><p id="link"></td></tr>
                </table><br><br>
                <form action="storeData" method="post">
                    <input style="display: none" type="text" name="imageFileName" value="${getOriginalFilename}">
                    <input style="display: none" type="text" id="storeName" name="storeName">
                    <input style="display: none" type="text" id="gpsAddress" name="gpsAddress">
                    <input style="display: none" type="text" name="storeMenu" value="menu test data">
                    <input style="display: none" type="text" name="storePhone" value="02-1234-5678">
                    <table>
                        <tr>
                            <td>별점: <label><input type="radio" name="rate" value="1">1점</label>
                                <label><input type="radio" name="rate" value="2">2점</label>
                                <label><input type="radio" name="rate" value="3">3점</label>
                                <label><input type="radio" name="rate" value="4">4점</label>
                                <label><input type="radio" name="rate" checked="checked" value="5">5점</label></td>
                        </tr>
                        <tr>
                            <td><input type="text" id="aLineReview" name="aLineReview" placeholder="한줄평을 써주세요."></td></tr>
                        <tr>
                            <td><button type="submit" class="reInputButton">메인화면(기록 저장)</button></td></tr>
                    </table>
                </form>
            </div>
            <script>
                let searchResult = ${searchResult};
                let geoLoc = ${geoLoc};

                document.getElementById("category").textContent = searchResult['items'][0]['category'];
                document.getElementById("roadAddress").textContent = searchResult['items'][0]['roadAddress'];
                document.getElementById("link").textContent = searchResult['items'][0]['link'];
                document.getElementById("title").value = searchResult['items'][0]['title'];
                document.getElementById("storeName").value = searchResult['items'][0]['title'];
                document.getElementById("gpsAddress").value = geoLoc['addresses'][0]['x'] + " " + geoLoc['addresses'][0]['y'];

                naverMapConf(geoLoc['addresses'][0]['x'], geoLoc['addresses'][0]['y']);
            </script>
        </div>
    </div>
</body>
</html>