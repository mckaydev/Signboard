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
    <link rel="stylesheet" href="/resources/css/jcrop.css">
    <script src="/resources/js/jcrop.js"></script>
    <script type="text/javascript" src="/resources/js/exif.js"></script>
</head>
<body>
    <div class="container">
        <div class="infoBox" id="infoBox" style="width: 50vw; display: flex; flex-direction: row; flex-wrap: wrap">
            <img src="/img/${getOriginalFilename}" id="target" alt="[JcropExample]"
                 style="max-height: 80vh; width: 50vw"/>
            <form action="cropResult" id="coords" class="coords" method="post">
                <label style="display: none">x1: <input id="x1" name="x1" type="text" required></label>
                <label style="display: none">y1: <input id="y1" name="y1" type="text" required></label>
                <label style="display: none">x2: <input id="x2" name="x2" type="text" required></label>
                <label style="display: none">y2: <input id="y2" name="y2" type="text" required></label>
                <label style="display: none">w: <input id="w" name="w" type="text" required></label>
                <label style="display: none">h: <input id="h" name="h" type="text" required></label>
                <label style="display: none"><input name="originalFileName" value="${getOriginalFilename}"></label>
                <label style="display: none"><input id="oW" name="offsetWidth" ></label>
                <label style="display: none"><input id="oH" name="offsetHeight" ></label>
                <label style="display: none">GPS X: <input id="ddX" name="ddX" readonly></label>
                <label style="display: none">GPS Y: <input id="ddY" name="ddY" readonly></label>
                <label id="dongLabel">
                    <input style="font-size: 1.5rem; width: 50%; text-align: center" id="dong" name="dong" maxlength="10">
                </label><br>
<%--                <label style="font-size: 2rem"><input type="radio" name="whatLang" checked="checked" value="kor">한국어</label>--%>
<%--                <label style="font-size: 2rem"><input type="radio" name="whatLang" value="eng">영어</label>--%>
                <button style="width: 50vw; box-sizing: border-box" type="submit" onclick="offset()">제출</button>
            </form>
            <script>
                const jcrop = Jcrop.attach('target', {
                    shadeColor: 'light'
                });
                let x = 100, y = 100, w = 100, h = 100;

                const rect = Jcrop.Rect.create(x,y,w,h);
                const options = {};
                jcrop.newWidget(rect, options);

                jcrop.listen('crop.update', (widget,e) => {
                    let test = widget.pos;
                    document.getElementById("x1").value = test.x;
                    document.getElementById("y1").value = test.y;
                    document.getElementById("x2").value = test.x + test.w;
                    document.getElementById("y2").value = test.y + test.h;
                    document.getElementById("w").value = test.w;
                    document.getElementById("h").value = test.h;
                })

                window.onload = function () {
                    if ('${ocrResult}' === 'fail') {
                        alert("ocr 결과가 없어요. 영역을 다시 지정해 주세요.");
                    }
                    if ('${apiResult}' === 'fail') {
                        alert("검색 결과가 없어요. 영역을 다시 지정해 주세요.");
                    }
                    let img = document.getElementById("target");
                    // img.exifdata = null;
                    EXIF.getData(img, function () {
                        let x1 = String(EXIF.getTag(this, "GPSLongitude"));
                        let y1 = String(EXIF.getTag(this, "GPSLatitude"));
                        let x1Split = x1.split(',');
                        document.getElementById("ddX").value = parseFloat(x1Split[0]) + (parseFloat(x1Split[1]) / 60) + (parseFloat(x1Split[2]) / 3600);
                        let y1Split = y1.split(',');
                        document.getElementById("ddY").value = parseFloat(y1Split[0]) + (parseFloat(y1Split[1]) / 60) + (parseFloat(y1Split[2]) / 3600);
                        if(x1 === "undefined") {
                            document.getElementById("dong").placeholder = "GPS 정보가 없어요. 행정동을 입력해 주세요.";
                        } else {
                            document.getElementById("dongLabel").style = "display: none";
                        }
                    })
                }

                function offset() {
                    if (document.getElementById("x1").value === "") {
                        alert("간판의 영역을 지정 해주세요");
                    }
                    document.getElementById("oW").value = document.getElementById("target").offsetWidth;
                    document.getElementById("oH").value = document.getElementById("target").offsetHeight;
                }
            </script>
        </div>
    </div>
</body>
</html>
