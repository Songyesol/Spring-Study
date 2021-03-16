<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>auth.jsp</title>
</head>
<body>
	<a href="auth">사용자 인증</a>
	<a href="userInfo?access_token=${access_token }" >사용자정보</a>
	<hr>
	access_token : ${access_token }
	<c:if test="${not empty userInfo }">
	<c:forEach items="${userInfo }" var="info">
	사용자정보 : ${info.id }
			  ${info.name }
			  <c:forEach items="${userInfo.res_list }" var="list">
			  	${list.fintech_use_num }
			  </c:forEach>
	</c:forEach>
	</c:if>
	
</body>
</html>