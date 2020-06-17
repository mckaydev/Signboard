<%--
  Created by IntelliJ IDEA.
  User: simi7
  Date: 2020-06-15
  Time: 오후 4:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Crop Image</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/index.css">
<%--    <link rel="stylesheet" href="https://unpkg.com/jcrop/dist/jcrop.css">--%>
    <link rel="stylesheet" href="/resources/css/jcrop.css">
    <script src="https://unpkg.com/jcrop"></script>
</head>
<body>
    <div class="container">
        <div class="infoBox" id="infoBox" style="width: auto">
            <img src="/img/${getOriginalFilename}" id="target" alt="[JcropExample]"/>
            <script>
                const jcrop = Jcrop.attach('target', {
                    shadeColor: 'light'
                });
                let x = 100, y = 100, w = 100, h = 100;

                const rect = Jcrop.Rect.create(x,y,w,h);
                const options = {};
                jcrop.newWidget(rect, options);
            </script>
            <form action="cropResult" id="coords" class="coords" method="get">
                <label>x1: <input id="x1" name="x1" type="text"></label>
                <label>y1: <input id="y1" name="y1" type="text"></label><br>
                <label>x2: <input id="x2" name="x2" type="text"></label>
                <label>y2: <input id="y2" name="y2" type="text"></label><br>
                <label>w: <input id="w" name="w" type="text"></label>
                <label>h: <input id="h" name="h" type="text"></label> <br>
                <label><input name="originalFileName" value="${getOriginalFilename}" style="display: none"></label>
                <button type="submit">제출</button>
            </form>
            <script>
                jcrop.listen('crop.update', (widget,e) => {
                    let test = widget.pos;
                    document.getElementById("x1").value = test.x;
                    document.getElementById("y1").value = test.y;
                    document.getElementById("x2").value = test.x + test.w;
                    document.getElementById("y2").value = test.y + test.h;
                    document.getElementById("w").value = test.w;
                    document.getElementById("h").value = test.h;
                })
            </script>
        </div>
    </div>
</body>
</html>
