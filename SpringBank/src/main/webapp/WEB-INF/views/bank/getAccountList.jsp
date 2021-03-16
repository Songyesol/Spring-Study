<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>getAccountList.jsp</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

</head>
<body>
<div align="center">
	<div>
		<h2>계좌조회</h2>
		<table id="tbl">
		<tr>
			<th>은행명</th>
			<th>계좌별칭</th>
			<th>계좌주</th>
			<th>핀테크ID</th>
		</tr>
		<c:if test="${not empty list }" >
		<c:forEach items="${list.res_list}" var="account">
			<tr>
				<td>${account.bank_name }</td>
				<td>${account.account_alias }</td>
				<td>${account.account_holder_name }</td>
				<td><a href="#" onclick="getBalance('${account.fintech_use_num }')">ajax로 데이터만 받아오기</a></td>
				<td><a href="#" onclick="getBalance2('${account.fintech_use_num }')">ajax로 페이지 전체 받아오기</a></td>
				
			</tr>
		</c:forEach>
		</c:if>
		</table>
	</div>
	<hr>
	<div id="result">

	</div>
</div>
<script>
	function getBalance(fin){
		var param= "fintech_use_num="+fin
		//ajax 호출
		$.ajax({
			url: "ajaxGetBalance",
			data : param ,
			dataType:"json",
			method:"GET" ,
			success : function(response){
				$('#result').html('<h2>잔액조회(ajax)</h2>'+'<br>'+response.bank_name +'<br>'+response.product_name +'<br>'+ response.balance_amt);
				//('#result').empty();
				//for(value in response){
				//	#("#result").html(response[value]+"<br>");
				// }
			}
		})
	}
	
	 function getBalance2(fin){
		var param= "fintech_use_num="+fin
		//ajax 호출
		$.ajax({
			url: "getBalance",
			data : param ,
			success : function(response){
				//$('#result').html('<h2>잔액조회(ajax)</h2>'+'<br>'+response.bank_name +'<br>'+response.product_name +'<br>'+ response.balance_amt);
				//('#result').empty();
				//for(value in response){
				//	#("#result").html(response[value]+"<br>");
				// }
				$("#result").html(response);
				
			}
		})
	} 
</script>
</body>
</html>