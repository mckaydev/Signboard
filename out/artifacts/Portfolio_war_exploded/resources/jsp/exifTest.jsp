<%--
  Created by IntelliJ IDEA.
  User: simi7
  Date: 2020-08-23
  Time: 오전 4:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>EXIF</title>
    <script type="text/javascript" src="/resources/js/exif.js"></script>
</head>
<body>
    <img id="img" src="../img/은진식당%20gps.jpg"><br>
    <p id="text1"></p>
    <p id="text2"></p>
    <p>---------------------------------------------------</p>
    <p id="dmsX"></p>
    <p id="dmsY"></p>
    <p>---------------------------------------------------</p>
    <p id="ddX"></p>
    <p id="ddY"></p>
    <script>
        let img = document.getElementById("img");
        img.exifdata = null;
        EXIF.getData(img, function () {
            let x1 = String(EXIF.getTag(this, "GPSLongitude"));
            document.getElementById("text1").innerText = x1;
            let y1 = String(EXIF.getTag(this, "GPSLatitude"));
            document.getElementById("text2").innerText = y1;

            let all = EXIF.getAllTags(this);
            // document.getElementById("gpsX").innerText = JSON.stringify(all, null, "\t");
            document.getElementById("dmsX").innerText = "dmsX: " + all["GPSLongitude"];
            document.getElementById("dmsY").innerText = "dmsY: " + all["GPSLatitude"];

            let x1Split = x1.split(',', 3);
            let ddX = parseFloat(x1Split[0]) + (parseFloat(x1Split[1]) / 60) + (parseFloat(x1Split[2]) / 3600);
            document.getElementById("ddX").innerText = "ddX: " + ddX;
            let y1Split = y1.split(',');
            let ddY = parseFloat(y1Split[0]) + (parseFloat(y1Split[1]) / 60) + (parseFloat(y1Split[2]) / 3600);
            document.getElementById("ddY").innerText = "ddY: " + ddY;
        });
    </script>
</body>
</html>
