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
    <title>Prior Search</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/index.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/findOk.css">
    <script src="/resources/js/priorSearch.js"></script>
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
<div class="container" id="container">
<%--    <button style="position: fixed" onclick="createFunction()">Hit</button>--%>
<%--    <ul class="pageList" id="pageList">--%>
<%--    </ul>--%>
    <script>
        let jsonFile = ${shListJson};
        let contentPerPage = 1;
        let pagePerPages = 5;
        let currentPage = 0;
        createFunction(currentPage);
        pageButton()
    </script>
    <button class="mainButton" id="mainButton" onclick="location.href='/'">메인화면</button>
</div>
</body>
</html>
