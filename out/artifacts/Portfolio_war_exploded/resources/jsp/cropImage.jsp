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
            <img src="/resources/img/test.jpg" id="target" alt="[JcropExample]"/>
            <script>
                const jcrop = Jcrop.attach('target', {
                    shadeColor: 'light',
                    onchange: showCoords,
                    onSelect: showCoords,
                    onRelease:  clearCoords
                });
                const widget = Jcrop.Widget.create();

                $('#coords').on('change','input',function(e){
                    let x1 = $('#x1').val(),
                        x2 = $('#x2').val(),
                        y1 = $('#y1').val(),
                        y2 = $('#y2').val();
                    jcrop.setSelect([x1,y1,x2,y2]);
                });

                function showCoords(c) {
                    $('#x1').val(c.x);
                    $('#y1').val(c.y);
                    $('#x2').val(c.x2);
                    $('#y2').val(c.y2);
                    $('#w').val(c.w);
                    $('#h').val(c.h);
                }

                function clearCoords()
                {
                    $('#coords input').val('');
                }
                // let locInfo = document.createElement("p");
                // locInfo.appendChild(document.createTextNode(jcrop.top))
                // locInfo.appendChild(document.createTextNode(jcrop.left))
                // document.getElementById("infoBox").appendChild(locInfo);
                // jcrop.addClass('jcrop-ux-fade-more');
            </script>
            <form action="cropResult" id="coords" class="coords">
                <label>x1<input id="x1" name="x1" type="text"></label>
                <label>y1<input id="y1" name="y1" type="text"></label>
                <label>x2<input id="x2" name="x2" type="text"></label>
                <label>y2<input id="y2" name="y2" type="text"></label>
                <label>w<input id="w" name="w" type="text"></label>
                <label>h<input id="h" name="h" type="text"></label>
                <button type="submit">제출</button>
            </form>
        </div>
    </div>
</body>
</html>
