# [개인 프로젝트 - 간판 검색기](http://jutabi.com)

<details markdown="1">
<summary style="font-size: 1.5rem">TODO LIST</summary>
<div markdown="1">

- (○) 다이어그램 그리기 
- (○) 블로그에 개발상황 연재하기
- (○) 스프링 프레임워크 사용
- (○) AWS EC2로 서비스 (ubuntu 18.04)
- (○) Tomcat 으로 Web Server
- (○) Github Private Repository 버전 관리
- (○) DB 적용 (MySQL)
- (○) 커넥션 풀 공부
- (○) Mybatis 적용
- (△) HTML, CSS 클린코드화
- (○) 이미지 파일 업로드
- (○) 로그인 기능
- (○) 검색기록 테이블 생성, 삭제
- (○) 검색기록 생성
- (○) 검색기록 보기
- (○) 검색기록 삭제
- (○) 즐겨찾기 보기
- (○) 즐겨찾기 추가
- (○) 이미지 OCR
- (○) 이미지 크롭
- (○) 이미지 크롭시 원본과 비율 일치
- (○) 별점 기능 추가
- (○) 한줄평 기능 추가
- (○) EXIF GPS 정보 추출
- (○) 네이버 reverse geocoding API
- (○) 가게 정보 네이버 검색 API로 요청
- (○) 네이버 Dynamic Map API
- (○) 이미지 테이블 삭제시 이미지도 삭제
- (○) 이미지 업로드하지 않고 제출 예외처리
- (○) 간판의 영역을 지정하지 않고 제출 예외처리
- (○) OCR 결과 없을 때 예외처리
- (○) 검색 API 반환 값에 정보 없을 때 예외처리
- (○) 'starbucks coffee'로 검색시 결과가 안나오는 현상 예외처리
- (○) GPS 정보 없을 때 예외처리 (reverse geocoding 쿼리문 없을 때)
- (○) 사진에 GPS 정보가 없다면 행정동을 입력하는 input태그 생성, 처리
- (○) 로그인에 Spring Security 적용
- (○) 예제 이미지 선택 가능
- (○) SSL 적용
- (X) 검색기록 페이징
- ----------------추가 항목----------------
- (○) 포트폴리오 블로그 생성
- (○) 개인프로젝트 설명 페이지 작성
- (X) 실사용자 전용 설명 (Web Page or Video)
- (○) 이력서 pdf version
- (f) 북마크, 즐겨찾기 스크롤 위치 저장
- (X) 하드코딩된 db로그인 정보 암호화
- (X) 댓글 기능
- (X) 점주 메뉴 생성
- (X) Docker

</div>
</details>

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
        <td>tomcat </td> <td>9.0.31</td>
        <td>tess4j </td> <td>4.5.1</td>
    </tr>
    <tr>
        <td>OpenJDK </td> <td>1.8.0_221</td>
        <td>Jackson </td> <td>2.11.0</td>
        <td>Spring MVC </td> <td>5.2.3</td>
    </tr>
    <tr>
        <td>MySQL </td> <td>8.0.20</td>
        <td>Mybatis </td> <td>3.5.4</td>
        <td>Spring Security </td> <td>5.2.2</td>
    </tr>
    <tr>
        <td>commons-fileupload </td> <td>1.4</td>
        <td>commons-io </td> <td>2.5</td>
        <td>Naver Dynamic Map API</td> <td>V3</td>
    </tr>
    <tr>
        <td>Naver Search API </td> <td></td>
        <td>Naver Geocoding API </td> <td></td>
        <td>Naver Reverse Geocoding API</td> <td></td>
    </tr>
    </tbody>
</table>

- **AWS EC2**로 배포합니다.
- DBMS는 **MySQL**을 사용합니다.
- SQL Mapper로 **Mybatis**를 사용합니다.
- WAS는 **tomcat9**을 이용합니다.
- 자바는 **OpenJDK 1.8**을 사용합니다.
- **Spring 프레임워크 MVC**를 사용합니다.
- **Spring Security 프레임워크**를 사용합니다.
- 이미지 ROI(region of interest)지정에는 **https://jcrop.com/** 을 사용합니다.
- **tess4j**를 이용하여 간판을 OCR합니다.
- 이미지 EXIF 정보는 **exif-js**를 이용하여 추출합니다. **https://github.com/exif-js/exif-js**
- **네이버 검색 API**를 사용합니다.
- **네이버 reverse geocode API**를 사용합니다.
- **네이버 geocode API**를 사용합니다.
- **네이버 Dynamic Maps API**를 사용합니다.
- 형상 관리는 **GitHub**를 이용합니다.

## 기능 1: 간판 검색
1. 사용자는 간판의 사진을 업로드합니다.
2. 업로드한 사진에서 OCR 성능을 높이기 위해 사용자는 **간판의 영역(ROI)를 지정**합니다.
3. javascript에서 사용자가 업로드한 사진의 EXIF정보 중 gps 정보(위도, 경도)를 추출합니다.
    1. 사진에 GPS정보가 없다면 사용자에게 행정동을 입력하게 합니다.
4. DMS로 반환된 좌표를 **DD좌표로 변경**합니다.
5. 사용자가 ROI를 지정한 사진과 gps 좌표(ddX, ddY)를 포함하여 서버에 요청합니다.
6. 서버는 사용자가 전송한 ROI의 좌표값과 gps 좌표를 수신합니다.
7. 수신한 간판사진중 ROI좌표 내의 영역만 **tesseract를 이용하여 OCR**합니다.
8. 받아온 ddX와 ddY를 이용하여 네이버 Reverse Geocoding API에 지번 주소를 요청합니다.
9. 지번주소 중 **'행정동' 데이터를 추출**합니다.
10. **행정동 + " " + OCR한 데이터**를 '네이버 검색 API'에 전송하여 가게의 정보를 요청합니다.
11. 정보 중 가게의 도로명 주소를 네이버 geocode API에 전송하여 위도와 경도 정보를 요청합니다.
(사진의 gps정보와 네이버 검색 api가 반환하는 결과의 오차를 대비하여 geocoding 수행)
12. 좌표 정보를 이용하여 검색 결과 화면에 가게 위치를 보여주는 동적 네이버 지도를 출력합니다.
13. 검색 API가 반환한 가게의 정보를 결과 화면에 출력합니다.
14. 검색기록을 위한 간단 리뷰 작성이 가능합니다.
    1. 5점 만점의 별점을 매길 수 있습니다.
    2. 간단한 한줄 리뷰를 남길 수 있습니다.
15. 로그인한 사용자의 경우에는 데이터베이스에 검색기록을 저장합니다.
    1. 회원 가입시 **사용자 전용**의 검색기록 테이블을 DB에 생성합니다.
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
        <td>PK</td><td>username</td><td>varchar(10)</td>
    </tr>
    <tr>
        <td></td><td>password</td><td>varchar(10)</td>
    </tr>
    <tr>
        <td></td><td>email</td><td>varchar(20)</td>
    </tr>
    <tr>
        <td></td><td>authorities</td><td>varchar(50)</td>
    </tr>
    <tr>
        <td></td><td>accountNonExpired</td><td>boolean</td>
    </tr>
    <tr>
        <td></td><td>accountNonLocked</td><td>boolean</td>
    </tr>
    <tr>
        <td></td><td>credentialsNonExpired</td><td>boolean</td>
    </tr>
    <tr>
        <td></td><td>enabled</td><td>boolean</td>
    </tr>
</tbody>
</table>

##### **스프링 시큐리티 프레임워크**를 이용합니다.

#### 로그인
- 스프링 시큐리티를 이용하여 사용자의 로그인을 진행합니다.
- 로그인시 '이전 검색 기록' 과 '즐겨찾기' 메뉴 이용이 가능합니다.
- 회원 사용자는 간판 검색시 간판사진과 가게 정보, 별점과 한줄평이 DB에 기록됩니다.
    - 기록된 정보들을 '이전 검색 기록' 메뉴에서 확인할 수 있으며, 원하는 정보만을 즐겨찾기에
    추가하여 '즐겨찾기' 메뉴에서 따로 확인할 수 있습니다.
#### 회원 가입
- 'member' 테이블의 'username'이 기본키인 것을 이용하여 중복을 관리합니다.
- 중복되는 Id가 아니라면 사용자가 입력한 데이터를 'member' 테이블에 저장합니다.
    - 만약 Id가 중복된다면 회원가입 페이지를 리로딩합니다.
- 회원가입시 사용자의 계정은 "USER" authority 권한을 부여받습니다.
- 스프링 시큐리티 세션에서 사용할 accountNonExpired, accountNonLocked, 
credentialsNonExpired, enabled 속성들에 "true"를 기본값으로 부여받습니다.
#### 로그 아웃
- 스프링 시큐리티의 logout 기능을 이용하여 진행합니다.
#### 정보 수정
- 사용자가 변경할 이메일과 비밀번호를 입력하면 'username'를 통해서 데이터베이스 내의
사용자 정보를 수정합니다.
#### 회원 탈퇴
1. 사용자의 'username'를 이용하여 'srchhisto' 스키마의 'username'테이블에 접근합니다.
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
            <td>PK</td><td>imageFileName</td><td>varchar(100)</td>
        </tr>
        <tr>
            <td></td><td>gpsAddress</td><td>varchar(40)</td>
        </tr>
        <tr>
            <td></td><td>roadAddress</td><td>varchar(100)</td>
        </tr>
        <tr>
            <td></td><td>storeName</td><td>varchar(20)</td>
        </tr>
        <tr>
            <td></td><td>storeMenu</td><td>varchar(100)</td>
        </tr>
        <tr>
            <td></td><td>storePhone</td><td>varchar(20)</td>
        </tr>
        <tr>
            <td></td><td>rate</td><td>varchar(1)</td>
        </tr>
        <tr>
            <td></td><td>aLineReview</td><td>varchar(200)</td>
        </tr>
        <tr>
            <td></td><td>isBookmarked</td><td>booliean</td>
        </tr>
        </tbody>
    </table>
    
- 메뉴에 접속하면 검색했던 기록들이 각각의 'div' 태그를 가집니다.
- 각각의 기록들은 '북마크 추가' 와 '기록 삭제' 버튼이 존재합니다.
- '북마크 추가' 버튼을 누르면 '회원 ID' 테이블에서 해당 기록의 'isBookmarked' 
속성의 값을 0에서 1로 변경합니다.
- 북마크에 추가되어 있다면 '북마크 삭제' 버튼이 '북마크 추가' 버튼을 대신합니다.
- '북마크 삭제' 버튼은 테이블에서 'isBookmarked' 속성의 값을 1에서 0으로 변경합니다.
- '기록 삭제'버튼을 누르면 'imageFileName' 속성값을 통해 서버의 로컬 저장소에 저장되어 있는
사진 파일을 삭제합니다. 그리고 테이블에서 해당하는 데이터를 삭제합니다.
## 기능 4: '즐겨찾기'
- 'srchhisto' 스키마의 'username' 테이블 내의 데이터들 중 'isBookmarked' 속성 값이 1인
데이터들만을 보여줍니다.
- '북마크 삭제' 버튼과 '기록 삭제' 버튼이 각각의 기록마다 존재합니다.

## + 이전 검색 기록과 즐겨찾기
- '이전 검색 기록' 과 '즐겨찾기' 는 동일한 jsp 뷰 파일로 반환합니다. (priorSearch.jsp)
- 스프링 컨트롤러 내에서 request mapping을 통해 둘을 구분합니다.
- 쿠키를 사용하여 현재 사용자가 접속한 기능을 저장합니다.
    - 같은 jsp 뷰 파일을 사용하기 때문에 북마크 추가, 삭제, 기록 삭제등의 연산 후
    새로운 뷰를 반환할 때 전에 접속했던 기능으로 반환해줍니다.
1. 스프링 컨트롤러에서 SQL Mapper인 Mybatis를 통해 DB에서 검색기록을 받는데 JSON으로 변환합니다.
2. JSON 파일을 .jsp로 넘겨주고 .jsp에서는 자바스크립트를 통해 들어오는 JSON파일의 길이(데이터의 갯수)
에 따라 '.insertBefore'을 사용하여 검색기록을 화면에 동적 생성합니다.
