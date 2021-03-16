<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>insertBoard.jsp</title>
</head>
<body>
	<!-- input type="file"이 하나라도 있으면 encType="multipart/form-data" 반드시 입력할것. -->
	<form action="insertNewBoard" method="post" encType="multipart/form-data"> 
		작성자	<input type="text" name="writer"><br/>
		제목		<input type="text" name="title"><br/>
		내용		<textarea name="content"></textarea><br/>
		첨부파일	<input type="file" name="uploadFile" multiple="multiple" /><br/> <!-- multiple="multiple" -> 파일 여러개 받을 수 있음 (배열로 받음) -->
				<input type="submit" value="저장">
	</form>

</body>
</html>