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
    <link rel="stylesheet" type="text/css" href="resources/css/index.css">
    <link rel="stylesheet" type="text/css" href="resources/css/fileBox.css">
    <script src="resources/js/jquery-3.5.1.js"></script>
    <script src="resources/js/imageUploadButton.js"></script>
</head>
<body>
    <div class="container">
        <div class="infoBox">
            <p>
                간판의 사진을 업로드 해주세요.
            </p> <br>
            <div class="fileBox upload">
                <form action="inputImageSuccess" method="post" enctype="multipart/form-data">
                    <input class="upload-name select" value="파일선택" disabled="disabled">
                    <label for="ex_filename">업로드</label>
                    <input type="file" id="ex_filename" class="upload-hidden" name="imageFile">
                    <input type="submit" value="제출" class="upload-hidden">
                </form> <br>
                <button class="upload-name" onclick="location.href='/find'">내 주변 맛집 찾기</button> &nbsp
                <button class="upload-name" onclick="location.href='/priorSearch'">이전 검색 기록</button>
            </div>
        </div>
    </div>
</body>
</html>
