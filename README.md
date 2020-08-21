# 개인 프로젝트 - 간판 검색기

## 이용 기술
<table>
    <thead>
    <tr bgcolor="#add8e6">
        <th>이름</th> <th>버전</th> <th>이름</th> <th>버전</th> <th>이름</th> <th>버전</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>AWS EC2 </td> <td>t2.micro</td>
        <td>maven </td> <td></td>
        <td>tess4j </td> <td>4.5.1</td>
    </tr>
    <tr>
        <td>tomcat </td> <td>9.0.31</td>
        <td>OpenJDK </td> <td>1.8.0_221</td>
        <td>Jackson </td> <td>2.11.0</td>
    </tr>
    <tr>
        <td>Spring </td> <td>5.2.3</td>
        <td>MySQL </td> <td>8.0.20</td>
        <td>Mybatis </td> <td>3.5.4</td>
    </tr>
    <tr>
        <td>commons-fileupload </td> <td>1.4</td>
        <td>commons-io </td> <td>2.5</td>
        <td></td> <td></td>
    </tr>
    </tbody>
</table>

- **AWS EC2**로 배포합니다.
- DBMS는 **MySQL**을 사용합니다.
- SQL Mapper로 **Mybatis**를 사용합니다.
- WAS는 **tomcat**을 이용합니다.
- 자바는 **OpenJDK 1.8**을 사용합니다.
- **Spring 프레임워크 MVC**를 사용합니다.
- 이미지 ROI(region of interest)지정에는 **https://jcrop.com/** 을 사용합니다.
- **tesseract**를 이용하여 간판을 OCR합니다.
- 이미지 EXIF 정보는 'test'를 이용하여 추출합니다.
- **네이버 검색 API**를 사용합니다.
- **네이버 geocode API**를 사용합니다.
- **네이버 Maps API**를 사용합니다.
- 형상 관리는 **GitHub**를 이용합니다.

## 기능 1: 간판 검색
1. 사용자는 간판의 사진을 업로드합니다.
2. 업로드한 사진에서 OCR 성능을 높이기 위해 간판의 영역(ROI)를 지정합니다.
3. 사용자가 ROI를 지정한 사진을 포함하여 서버에 요청합니다.
4. 서버는 사용자가 전송한 간판 사진과 ROI의 좌표값을 수신합니다.
5. 수신한 간판사진중 ROI좌표 내의 영역만 tesseract를 이용하여 OCR합니다.
6. OCR한 데이터를 '네이버 검색 API'에 전송하여 가게의 정보를 요청합니다.
7. 정보 중에 가게의 주소를 네이버 geocode API에 전송하여 위도와 경도 정보를 요청합니다.
8. 좌표 정보를 이용하여 검색 결과 화면에 가게 위치를 보여주는 네이버 지도를 출력합니다.
9. 검색 API가 반환한 가게의 정보를 결과 화면에 출력합니다.
10. 검색기록을 위한 간단 리뷰 작성이 가능합니다.
    1. 5점 만점의 별점을 매길 수 있습니다.
    2. 간단한 한줄 리뷰를 남길 수 있습니다.
11. 로그인한 사용자의 경우에는 데이터베이스에 검색기록을 저장합니다.
    1. 회원 가입시 사용자 전용의 검색기록 테이블을 DB에 생성합니다.
    2. 상호명, 메뉴, 연락처를 테이블에 저장하고 북마크 여부에는 0을 저장합니다.

## 기능 2: 회원 정보
<table>
<thread>
    <tr>
        <th>member</th>
    </tr>
</thread>
<tbody>
    <tr>
        <td>PK</td><td>memberId</td>
    </tr>
    <tr>
        <td></td><td>memberPw</td>
    </tr>
    <tr>
        <td></td><td>memberEmail</td>
    </tr>
</tbody>
</table>

#### 로그인
- 'member'라는 이름으로 세션에 로그인 정보를 저장합니다.
- 로그인시 '이전 검색 기록' 과 '즐겨찾기' 메뉴 이용이 가능합니다.
- 회원 사용자는 간판 검색시 간판사진과 가게 정보가 DB에 기록됩니다.
    - 기록된 정보들을 '이전 검색 기록' 메뉴에서 확인할 수 있으며, 원하는 정보만을 즐겨찾기에
    추가하여 '즐겨찾기' 메뉴에서 따로 확인할 수 있습니다.
#### 회원 가입
- 'member' 테이블의 'memberId'가 기본키인 것을 이용하여 중복을 관리합니다.
- 중복되는 Id가 아니라면 사용자가 입력한 데이터를 'member' 테이블에 저장합니다.
    - 만약 Id가 중복된다면 회원가입 페이지를 리로딩합니다.
#### 로그 아웃
- 요청한 사용자의 'memberId'를 이용하여 세션에서 로그인 정보를 지웁니다.
#### 정보 수정
- 사용자가 변경할 이메일과 비밀번호를 입력하면 'memeberId'를 통해서 데이터베이스 내의
사용자 정보를 수정합니다.
#### 회원 탈퇴
1. 사용자의 'memberId'를 이용하여 'srchhisto' 스키마의 'memberId'테이블에 접근합니다.
2. 검색 기록 테이블에서 사진의 위치를 반환받아 전부 삭제합니다.
3. 'srchhisto' 스키마의 'memberId'테이블을 삭제합니다.
4. 'signboard'테이블의 'member'테이블에서 사용자의 정보를 삭제합니다.

## 기능 3: '이전 검색 기록'
- 회원 사용자는 각각 'srchhisto' 스키마에 테이블명이 '회원 ID' 로 이루어진 테이블을 갖습니다.  
ex) 회원 Id가 'test1234' 일 때, 테이블명은 'test1234'
- **회원 가입시**에 테이블이 생성됩니다.
- 테이블 구조는 다음과 같습니다.
    <table>
        <thead>
        <tr>
            <th>MemberId</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>PK</td><td>imageFileName</td></tr>
        <tr>
            <td></td><td>gpsAddress</td></tr>
        <tr>
            <td></td><td>storeName</td></tr>
        <tr>
            <td></td><td>storeMenu</td></tr>
        <tr>
            <td></td><td>storePhone</td></tr>
        <tr>
            <td></td><td>rate</td></tr>
        <tr>
            <td></td><td>aLineReview</td></tr>
        <tr>
            <td></td><td>isBookmarked</td></tr>
        </tbody>
    </table>
    
- 메뉴에 접속하면 검색했던 기록들이 각각의 'div' 태그를 가집니다.
- 각각의 기록들은 '북마크 추가' 와 '기록 삭제' 버튼이 존재합니다.
- '북마크 추가' 버튼을 누르면 '${MemberId}' 테이블에서 해당 기록의 'isBookmarked' 
속성의 값을 0에서 1로 변경합니다.
- 북마크에 추가되어 있다면 '북마크 삭제' 버튼이 '북마크 추가' 버튼을 대신합니다.
- '북마크 삭제' 버튼은 테이블에서 'isBookmarked' 속성의 값을 1에서 0으로 변경합니다.
- '기록 삭제'버튼을 누르면 'imageFileName' 속성값을 통해 서버의 로컬 저장소에 저장되어 있는
사진 파일을 삭제합니다. 그리고 테이블에서 해당하는 데이터를 삭제합니다.
## 기능 4: '즐겨찾기'
- 'srchhisto' 스키마의 'memberId' 테이블 내의 데이터들 중 'isBookmarked' 속성 값이 1인
데이터들만을 보여줍니다.
- '북마크 삭제' 버튼과 '기록 삭제' 버튼이 각각의 객체마다 존재합니다.

## + 이전 검색 기록과 즐겨찾기
- '이전 검색 기록' 과 '즐겨찾기' 는 동일한 jsp 뷰 파일로 반환합니다. (priorSearch.jsp)
- 스프링 컨트롤러 내에서 request mapping을 통해 둘을 구분합니다.
- 쿠키를 사용하여 현재 사용자가 접속한 기능을 저장합니다.
    - 같은 jsp 뷰 파일을 사용하기 때문에 북마크 추가, 삭제, 기록 삭제등의 연산 후
    새로운 뷰를 반환할 때 전에 접속했던 기능으로 반환해줍니다.
1. 스프링 컨트롤러에서 SQL Mapper인 Mybatis를 통해 DB에서 검색기록을 받는데 JSON으로 변환합니다.
2. JSON 파일을 .jsp로 넘겨주고 .jsp에서는 자바스크립트를 통해 들어오는 JSON파일의 길이(데이터의 갯수)
에 따라 '.insertBefore'을 사용하여 검색기록을 화면에 출력합니다.
