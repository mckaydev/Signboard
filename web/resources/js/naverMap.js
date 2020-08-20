function naverMapConf(x, y) {
    var HOME_PATH = window.HOME_PATH || '.';
    var store = new naver.maps.LatLng(y, x),
        map = new naver.maps.Map('map', {
            center: store,
            zoom: 18
        }),
        marker = new naver.maps.Marker({
            map: map,
            position: store
        });
    // var contentString = [
    //     '<div class="iw_inner">',
    //     '   <h3>서울특별시청</h3>',
    //     '   <p>서울특별시 중구 태평로1가 31 | 서울특별시 중구 세종대로 110 서울특별시청<br />',
    //     '       <img src="'+ HOME_PATH +'/img/example/hi-seoul.jpg" width="55" height="55" alt="서울시청" class="thumb" /><br />',
    //     '       02-120 | 공공,사회기관 &gt; 특별,광역시청<br />',
    //     '       <a href="http://www.seoul.go.kr" target="_blank">www.seoul.go.kr/</a>',
    //     '   </p>',
    //     '</div>'
    // ].join('');
    //
    // var infowindow = new naver.maps.InfoWindow({
    //     content: contentString,
    //     maxWidth: 600,
    //     backgroundColor: "#eee",
    //     borderColor: "#2db400",
    //     borderWidth: 3,
    //     anchorSize: new naver.maps.Size(30, 30),
    //     anchorSkew: true,
    //     anchorColor: "#eee",
    //     pixelOffset: new naver.maps.Point(20, -20)
    // });
    //
    // naver.maps.Event.addListener(marker, "click", function(e) {
    //     if (infowindow.getMap()) {
    //         infowindow.close();
    //     } else {
    //         infowindow.open(map, marker);
    //     }
    // });
}