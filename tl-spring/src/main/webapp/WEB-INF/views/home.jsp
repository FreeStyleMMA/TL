<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="cp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
</head>
<body>
	<h1>Kopis api</h1>
	<hr>
	<h2>공연 목록 보기</h2>
	<form method="get">
		<input name="startdate" placeholder="공연 시작 일자(필수)"> 
		<input name="enddate" placeholder="공연 종료 일자(필수)"> 
		<input name="cpage" placeholder="현재 페이지(필수)"> 
		<input name="rows" placeholder="페이지당 목록 수(필수)"> 
		<input name="shprfnm" placeholder="공연명"> <br> 
		<input name="shprfnmfct" placeholder="공연시설명">
		<input name="shcate" placeholder="장르코드">
		<input name="prfplccd" placeholder="공연장코드"> 
		<input name="signgucode" placeholder="지역(시도)코드"> 
		<input name="signgucodesub" placeholder="지역(구군)코드"> <br> 
		<input name="kidstate" placeholder="아동공연여부(Y,N)"> 
		<input name="prfstate" placeholder="공연상태 코드"> 
		<input name="openrun" placeholder="오픈런(Y,N)"> 
		<input name="afterdate" placeholder="해당일자 이후 등록/수정된 항목출력">
		<input name="requestType" placeholder="요청 타입"> <br>
		<input type="submit" value="테이블 보기" formaction="${cp}/tl/getPerformanceInfoTable">
		<input type="submit" value="JSON 보기" formaction="${cp}/tl/getPerformanceInfo">
		<input type="submit" value="PfmList 보기" formaction="${cp}/tl/getPerformanceList">
		<input type="submit" value="PfmDetail 보기" formaction="${cp}/tl/getPerformanceDetailList">
		<input type="submit" value="PfmPeace 보기" formaction="${cp}/tl/getPerformancePeaceList">
		<input type="submit" value="PfmStatus 보기" formaction="${cp}/tl/getPerformanceStatusList">
	</form>
	<pre>
요청 타입
recommend 추천 공연
rank      공연 랭킹
기본 값     공연 목록
--------------------------------------------------
공연상태 코드
01 공연예정
02 공연중
03 공연완료
--------------------------------------------------
장르 코드
AAAA 연극
BBBC 무용(서양/한국무용)
BBBE 대중무용
CCCA 서양음악(클래식)
CCCC 한국음악(국악)
CCCD 대중음악
EEEA 복합
EEEB 서커스/마술
GGGA 뮤지컬
--------------------------------------------------
공연시설특성 코드
1 중앙정부
2 문예회관
3 기타(공공)
4 대학로
5 민간(대학로 외)
6 기타(해외등)
7 기타(비공연장)
--------------------------------------------------
지역(시도)코드			지역(구군)코드
11 서울특별시       1111 서울특별시 종로구
                    1114 서울특별시 중구
26 부산광역시       2611 부산광역시 중구
                    2614 부산광역시 서구
27 대구광역시       2711 대구광역시 중구
                    2714 대구광역시 동구
28 인천광역시       2811 인천광역시 중구
                    2814 인천광역시 동구
29 광주광역시       2911 광주광역시 동구
                    2914 광주광역시 서구
30 대전광역시       3011 대전광역시 동구
                    3014 대전광역시 중구
31 울산광역시       3111 울산광역시 중구
                    3114 울산광역시 남구
36 세종특별자치시   3611 세종특별자치시
41 경기도           4111 경기도 수원시 장안구
                    4114 경기도 수원시 권선구
</pre>
</body>
</html>
