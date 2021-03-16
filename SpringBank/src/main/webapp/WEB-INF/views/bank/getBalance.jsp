<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>getBalance.jsp</title>
</head>
<body>
<div align="center">
	<h2>잔액조회</h2>
	<table>
		<tr>
			<th>은행명</th>
			<th>상품명</th>
			<th>잔액</th>
		</tr>
		<tr>
			<td>${balance.bank_name }</td>
			<td>${balance.product_name }</td>
			<td>${balance.balance_amt }</td>
		</tr>
	</table>

</div>
</body>
</html>