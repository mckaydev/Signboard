<%--
  Created by IntelliJ IDEA.
  User: simi7
  Date: 2020-08-19
  Time: 오후 5:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
<%--    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">--%>
    <title>간단한 지도 표시하기</title>
    <script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=ai9wkb82y8"></script>
    <script src="/resources/js/naverMap.js"></script>
</head>
<body>
    <h2>Naver Map API</h2>
    <div id="map" style="width:100%;height:400px;"></div>
    <script>
        naverMapConf(126.9641010, 37.5598996);
    </script>
</body>
</html>
