<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <!-- 2. jstl태그로!  -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
body {background-color: lightblue}
h1 {color:lightsalmon; font-size:30pt}
a:visited {
            color:royalblue;
            }
</style>
</head>

<body>
	<!-- <h1><a href="/web/test/testSession1">세션등록</a></h1>
	<h1><a href="/web/test/testSession2">세션삭제</a></h1>
	<h1><a href="/web/test/testInsert?a=가나다라&b=1234">testInsert</a></h1>절대경로를 이용해서! 링크를  클릭하면 testController로 넘어간다 -->
	
	<h1><a href ="nullpoint">예외처리</a></h1>
	
	<h1><a href="/web/guestbook/guestbookList">방명록</a></h1>
	<c:if test="${id == null}">
		
	<h1><a href="/web/member/signupForm">회원가입</a></h1>
	<h1><a href="/web/member/login">로그인</a></h1> 
	</c:if>


	 ${sessionScope.test} <br/> <!-- 세션을 임의적으로 만들어 ! -->
	
	
	<c:if test="${result == true}">
		<h3>가입성공!</h3>
	</c:if>
	<c:if test="${vo != null}"> <!-- test는 고정되있다 바꾸지 말기!!  -->
	<h3>회원정보 : ${vo} </h3><br/> <!-- 키값으로 저장한 vo를! 그러면 toString을 자동으로 호출해준다.!!-->
	</c:if>

	<c:if test="${id != null}"> <!-- test는 고정되있다 바꾸지 말기!!  -->
		<h3>${sessionScope.id} 님 환영합니다!</h3>
		<h1><a href="/web/board/boardList">게시판</a></h1>
		<h1><a href="/web/member/logout">로그아웃</a></h1> <br/>
	</c:if>
</body>
</html>