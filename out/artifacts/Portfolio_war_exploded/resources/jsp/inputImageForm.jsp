<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: simi7
  Date: 2020-06-02
  Time: 오전 2:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>간판 검색기</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/index.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/fileBox.css">
    <script src="/resources/js/jquery-3.5.1.js"></script>
    <script src="/resources/js/imageUploadButton.js"></script>
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
            <p>
                간판의 사진을 업로드 해주세요.
            </p> <br>
            <div class="fileBox upload">
                <form action="cropImage" method="post" enctype="multipart/form-data">
                    <input class="upload-name select" value="업로드 버튼으로 사진을 선택하세요." disabled="disabled">
                    <label for="ex_filename">업로드</label>
                    <input type="file" id="ex_filename" class="upload-hidden" name="imageFile" required>
                    <button type="submit" class="upload-hidden">제출</button>
                </form> <br>
<%--                <button class="upload-name" onclick="location.href='/find'">내 주변 맛집 찾기</button> &nbsp--%>
                <button class="upload-name" id="priorSearch" onclick="location.href='/priorSearch'">이전 검색 기록</button> &nbsp
                <button class="upload-name" id="bookmarkedSearch" onclick="location.href='/bookmarkedSearch'">즐겨찾기</button>
                <c:if test="${pageContext.request.userPrincipal.name == null}">
                    <script>
                        document.getElementById("priorSearch").onclick = function () {
                            alert("로그인이 필요합니다.");
                        }
                        document.getElementById("bookmarkedSearch").onclick = function () {
                            alert("로그인이 필요합니다.");
                        }
                    </script>
                </c:if>
            </div>
        </div>
    </div>
</body>
</html>
