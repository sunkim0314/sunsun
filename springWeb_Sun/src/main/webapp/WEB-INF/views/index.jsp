<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <!-- 2. jstl태그로!  -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>parameter 전송</title>
</head>
<body>
	<!-- 190704 수업부분!! -->
	
	<a href="send5">model 객체실습</a><br/>
	<c:if test="${data != null && vo != null}"> <!-- test는 고정되있다 바꾸지 말기!!  -->
	data : ${data}<br/>
	vo.a : ${vo.a}<br/>
	vo.b : ${vo.b}<br/>	
	requestScope.data : ${requestScope.data}<br/>
	requestScope.vo.a : ${requestScope.vo.a}<br/>
	requestScope.vo.b : ${requestScope.vo.b}<br/>
	</c:if> <!-- 로그인 한사람한테만 보이는 혹은 로그인 안한 사람에게만 보이는 메뉴를 만들 수 있음  ex) 로그아웃버튼은 로그인한사람에게만 보인다. -->
	
	<!-- 190704 수업부분!! -->
	<img  src="resources/forky.png"/><br/> <!-- 중요중요! -->
	<img  src="/web/resources/forky.png"/><br/>
	<!-- 1. 절대 경로 지정: 프로젝트를  처음 실행했을때  http://localhost:8888/web/ 중의  '/web/'이  루트 컨텍스트-->
	<img  src='<c:url value = "resources/forky.png"/>'/> <br/><!--  2. jstl태그; 주의! 홑따옴표로쓰기  value의 속성은 쌍따옴표  + 절대 경로로 쓰면 안된다-->
	<ul>
		<li>
			<h2><a href= "send1?a=테스트&b=1234">a 태그로 전송</a></h2>
			 <!-- 링크를 클릭을 하면  get방식으로 서버에  전송  ==> HomeController.java파일 검색 
				==> value 속성값이 send1과 동일한 method르르 실행한다.-->
		</li>
		<li>
			<h2>form 태그로 전송(get)</h2>
			<form action="send2" method ="get">
				<input type="text" name ="a"><br/>
				<input type="text" name ="b"><br/>
				<input type ="submit" value="전송!!"><br/>
			</form>
		</li>
			<li>
			<h2>form 태그로 전송(post)</h2>
			<form action="send2" method ="POST"> <!-- HomeController의 value와 action이 같아야한다!! -->
				<input type="text" name ="a"><br/>
				<input type="text" name ="b"><br/>
				<input type ="submit" value="전송!!"><br/>
			</form>
		</li>
		<li>
			<h2><a href ="send4?a=테스트&b=1231">a 태그(VO)</a></h2> <!-- GET 방식!! -->
		</li>
		
		<li>
			<h2>form 태그(VO)로  전송(post)</h2>
			<form action="send4" method ="POST">
				<input type="text" name ="a"><br/>
				<input type="text" name ="b"><br/>
				<input type ="submit" value="전송!!"><br/>
			</form>
		</li>
	</ul>
</body>
</html>