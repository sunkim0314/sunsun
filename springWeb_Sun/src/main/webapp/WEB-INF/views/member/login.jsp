<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>세션 로그인 연습</title> <!-- 셀렉트문으로 비교  -->
</head>
<body>
	<h1>[ 로그인 ]</h1>
	<form action="memberLogin" method= "post"> <!-- HomeController의 value와 action이 같아야한다!! -->
		ID : <input type ="text" name = "userid"/><br/>
		Password : <input type ="password" name = "userpwd"/><br/>
		 <input type="submit" value="로그인"><br/>
		${fail}
	</form>
</body>
</html>