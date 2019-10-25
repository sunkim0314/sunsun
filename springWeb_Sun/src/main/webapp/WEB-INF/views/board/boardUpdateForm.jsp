<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>

<link rel="stylesheet" type="text/css" href="/web/resources/css/default.css" />
<script>
	function boardUpdate(){
		if(confirm("수정하시겠습니까?")){
			var updateForm = document.getElementById("updateForm");
			updateForm.submit();
		}
	}
</script> <!-- document.getElementById("Form태그의 id") form태그 안에 있느느 것을 객체생성해줌-->
</head>
<body>
<h1>[ 글 수정 ]</h1> 
<form action="/web/board/boardUpdate" id="updateForm" method="post" enctype="multipart/form-data"> <!-- enctype꼭쓰기!!!! -->
	<table>
		<tr>
			<th>번호</th>
			<td>${vo.boardNum}</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${sessionScope.id}</td> <!-- 세션에서 가져와도 되고 vo에서 가져와도 됨 -->
		</tr>
		<tr>
			<th>작성일</th>
			<td>${vo.inputdate}</td>
		</tr>
		<tr>
			<th>조회</th>
			<td>${vo.hit}</td>
		</tr>
		<tr>
			<th>제목</th>
			<td><input type="text" name="title" value="${vo.title}"/></td> <!-- 왜 value필요..? -->
		</tr>
		<tr>
			<th>첨부 파일</th>
			<td> <!-- 먼저 업데이트 쿼리를 실행시킨다음에 ㅅㅓ비스단에서 파일이 있으면  서버에서 삭제시켜줘야한다 -->
				<a href ="/web/board/boardDownload?boardNum=${vo.boardNum}">${vo.originalFilename}</a>
				<input type="file" name ="uploadFile"/>
			</td>
		</tr>
		<tr>
			<th>내용</th>
			<td> <textarea rows="3" name="content" required="required">${vo.content}</textarea></td>
		</tr>
		<tr>
			<td class="right" colspan="2">
				<input type="button" value="수정" onclick="boardUpdate()"/>
				<!-- 어떤 게시물을 수정할지 특정할 보드넘을 가져가야한다. + 파일을 삭제하기 위해서 savedFilename도 가져가야! -->
				<input type="hidden" name="savedFilename" value="${vo.savedFilename}"/>
				<input type="hidden" name="boardNum" value="${vo.boardNum}"/>
				<a href="/web/board/boardList"><input type="button" value="취소"></a>
			</td>
		</tr>
	</table>
	
</form>
</body>
</html>
