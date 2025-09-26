<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="cp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
</head>
<body>
	<a href="/">
		<button type="button">home</button>
	</a>
	<div>목록 수 : ${fn:length(performanceInfoList)}</div>
	<table border="1" cellspacing="0" cellpadding="8"
		style="width: 100%; border-collapse: collapse; text-align: center;">
		<thead>
			<tr style="background-color: #f2f2f2;">
				<th>공연명</th>
				<th>기간</th>
				<th>장소</th>
				<th>런타임</th>
				<th>일정</th>
				<th>장르</th>
				<th>포스터</th>
				<th>예매 링크</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach var="info" items="${performanceInfoList}">
				<tr>
					<td>${info.perTitle}</td>
					<td>${info.perStartD}~${info.perEndD}</td>
					<td>${info.perPlace}</td>
					<td>${info.perRunT}</td>
					<td>${info.perSche}</td>
					<td>${info.perGenre}</td>
					<td><img src="${info.perPoster}"
						style="width: 100px; height: 120px"></td>
					<td><c:forEach var="ticket" items="${info.perTicket}">
							<a href="${ticket.url}" target="_blank">${ticket.name}</a>
							<br />
						</c:forEach></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>