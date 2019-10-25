<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>방명록</title>
<script>
/* <자바스크립트  바디태그 내부 or 헤드태그 내부에 작성!> 
	function : 메소드 선언 
	return값의 데이터타입정해져있지않다. return 받을 수도 있고 안받을수 있다.
	confirm 텍스트박스 팝업뜨고 확인 누르면 true 취소누르면 false
	var: 변수선언 
	document.getElementById(문자열) : 문자열은 id 값을 가지고있는 html태그를 객체생성시켜 반환해줌
	form.submit : form을 서브밋시킴 url을 요청보냄
	메서드 호출은 글쓰기 버튼에서 할것이다
*/
	function guestbookWrite(){
		if(confirm("등록하시겠습니까?")){
			var form = document.getElementById("writeForm");
			form.submit();
		}
	}
	function mainpage() {
		location.href = "/web/"
	}
	function guestbookDelete(){
		return confirm("삭제하시겠습니까?")
	}
</script>
</head>
<body><!-- 자바의 switch와 비슷  when태그는 case나 if와 비슷!!-->
<c:choose> 
	<c:when test="${writeResult == true}">
		<script>alert("등록 완료");</script>
	</c:when>
	<c:when test="${writeResult == false}">
		<script>alert("등록 실패");</script>
	</c:when>
	<c:when test="${deleteResult == true}">
		<script>alert("삭제 완료");</script>
	</c:when>
	<c:when test="${deleteResult == false}">
		<script>alert("삭제 실패");</script>
	</c:when>
</c:choose>
	<h1>방명록</h1>
	<form action="/web/guestbook/write" id="writeForm" method="POST" enctype="multipart/form-data"> <!-- 특정 html태그를 찾아갈수있는 고유한값  => 중복 XX-->
	<fieldset>
		<legend>
		<input type="button" value="글쓰기" onclick="guestbookWrite()"/>
		<input type="button" value="메인화면" onclick="mainpage()"/>
		</legend> <!-- 버튼을 눌렀을때  onclick의 자바스크립트의 메서드 실행된다 -->
		<p>작성자: <input type="text" name="name"/></p>
		첨부파일: <input type="file" name="uploadFile"><br>
		<p>내용: <textarea name="content" rows="3"></textarea></p>
		비밀번호: <input type="password" name="pwd" /><br />
	</fieldset>
	</form>
	<br />
	<!-- 특정 작성자가 쓴 게시글 검색 -->
	<form action="/web/guestbook/guestbookList" method="get">
		<input type="hidden" name="searchItem" value="name"/>
		작성자 <input type="text" name="searchKeyword"/> <!-- 검색시 2갸의 값을 가지고 서버에 요철을 보낸다. -->
		<input type="submit" value="검색"/>
	</form>
	<!-- 방명록 리스트 출력 -->
	<c:forEach items="${list}" var="guestbook">
		
		<fieldset>
			<legend>#${guestbook.seq}</legend>
			<p>작성자: ${guestbook.name}</p>
			<p>작성일: 
				<fmt:parseDate value="${guestbook.regdate}" var="parsedRegdate" pattern="yyyy-MM-dd HH:mm:ss"/> <!-- parsedRegdate에  vo.regdate넣기 -->
			 	<fmt:formatDate value="${parsedRegdate}" pattern ="yyyy-MM-dd"/> <!-- 이렇게 바꿔서 화면애 뿌리겠다 -->
			</p>
			<p> <!-- 다운로드 ;; 게스물번호 같이 넘겨주기 -->
				첨부파일: <br/>
				<img src="/web/guestbook/download?seq=${guestbook.seq}"><br/>
				<a href ="/web/guestbook/download?seq=${guestbook.seq}">${guestbook.originalFilename}</a>
			</p>
			<pre>${guestbook.content}</pre>
	<%-- 	<form action="/web/guestbook/delete" id="deleteForm" method="POST">	
			<!-- id 값은 고유해야하는데 foreach문으로 매번 같은 id를 생성하고 있기때문에 제대로 실행이 안된다.-->
			비밀번호: <input type="password" name="pwd" /> &nbsp; 
			 <input type="hidden" name="seq" value="${guestbook.seq}">
			 <input type="button" value="글삭제" onclick="guestbookDelete"/><br />
		</form> --%>
		<form action="/web/guestbook/delete"  method="POST">	 
			비밀번호: <input type="password" name="pwd" /> &nbsp; 
			 <input type="hidden" name="seq" value="${guestbook.seq}">
			 <input type="submit" value="글삭제" onclick= "return guestbookDelete()"/><br /> 
			 <!-- submit하기전에 onclick실행됨 => guestbookDelete()가에서 확인 눌르면 true 취소누르면 false => true이면  submit실행, false이면 submit이 작동 안한다.-->
		</form>
		</fieldset>
		
		<br />
		<br />
	</c:forEach>
</body>
</html>