<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${boardVO.title}</title>

<link rel="stylesheet" type="text/css"
	href="/web/resources/css/default.css" />
</head>
<body>
	<!-- 
	location.href 
	location은 주소창이라고 생각한다
	href속성에다가 문자열로 주솟값을 넘겨주면...! 이동한다.
 -->
	<script>
		function boardDelete() {
			if (confirm("삭제하시겠습니까?")) {
				location.href = "/web/board/boardDelete?boardNum=${vo.boardNum}";
			}
		}
		
		function replyWrite() {
			var replytext = document.getElementById("replytext");
			if (replytext.value.length == 0) {
				alert("댓글을 입력하시오");
				return;
			}
			document.getElementById("replyWrite").submit();
		}
		
		function replyModify(replynum, text){
			document.getElementById("replytext").value = text;
			document.getElementById("replysubmit").value = "댓글수정";
			
			document.getElementById("replysubmit").onclick = function(){
				var updatetext = document.getElementById("replytext").value;
				location.href="/web/board/replyUpdate?replynum=" + replynum + "&boardNum=" + "${vo.boardNum}&replytext=" + updatetext;
			}
		}
		
		function replyDelete(replynum){
			if (confirm("댓글을 삭제하시겠습니까?")) {
				location.href = "/web/board/replyDelete?replynum="+ replynum
						+"&boardNum=" + ${vo.boardNum}; 
			}
		}
	</script>
	<c:choose>

		<c:when test="${updateResult == true}">
			<script>
				alert("수정 완료");
			</script>
		</c:when>
		<c:when test="${updateResult == false}">
			<script>
				alert("수정 실패");
			</script>
		</c:when>
	</c:choose>
<!-- --------------------------------------------------------------------------------------------------------------------------------------  -->	
	<h1>[ 글 읽기 ]</h1>
	<table>
		<tr>
			<td class="right" colspan="2"><c:if
					test="${vo.userid == sessionScope.id}">
					<!-- 작성ㅈ바id와 현재 점속하고있는 id가 같으면 수정삭제 버튼 보여주기 -->
					<a href="/web/board/boardUpdateForm?boardNum=${vo.boardNum}"><input
						type="button" value="수정"></a>
					<%-- <a href="/web/board/boardDelete?boardNum=${vo.boardNum}"><input type="button" value="삭제"></a> --%>
					<!-- get방식으로 로직 요청 하면  url에 글번호 type하면   다른유저도 삭제 가능하므로 현재접속자 만 삭제 가능하게끔!! delete문에 글번호와 아이디가 일치해야 ! -->
					<input type="button" value="삭제" onclick="boardDelete()">
				</c:if> <a href="/web/board/boardList"><input type="button" value="목록"></a>
			</td>
		</tr>
		<tr>
			<th>번호</th>
			<td>${vo.boardNum}</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${vo.userid}</td>
		</tr>
		<tr>
			<th>작성일</th>
			<td><fmt:parseDate value="${vo.inputdate}" var="parsedInputdate" pattern="yyyy-MM-dd HH:mm:ss"/>
				<fmt:formatDate value="${parsedInputdate}" pattern="yyyy년 MM월 dd일 HH시 mm분 ss초"/></td>
				
		</tr>
		<tr>
			<th>조회</th>
			<!-- 조회수 처리!! dao에서!! -->
			<td>${vo.hit}</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${vo.title}</td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td><a href ="/web/board/boardDownload?boardNum=${vo.boardNum}">${vo.originalFilename}</a></td>
		</tr>
		<tr>
			<th>내용</th>
			<td><textarea readonly="readonly">${vo.content}</textarea></td>
		</tr>
	</table>
	<br/>
	
<!-- ------------------------------------------------------------------------------------------------------------------------------------------- -->	
	
<!-- 댓글입력 -->	
	<form action="/web/board/replyWrite" id="replyWrite" method="post">
		<input type="hidden" name="boardNum" value="${vo.boardNum}" />
		<table id="replyInput">
			<tr>
				<td>
					<input type="text" id="replytext" name="replytext"/> 
					<input type="button" id="replysubmit" value="댓글입력" onclick="replyWrite()" />
				</td>
			</tr>
		</table>
	</form>
<!-- 댓글 출력 -->
	<div id="replydisplay">
		<table class="reply">
			<c:forEach items="${replylist}" var="reply">
				<tr>
					<td class="replytext">${reply.replytext}</td>
					<td class="replyid"><span>${reply.userid}</span></td>
					<td class="replydate"><span>${reply.inputdate}</span></td>
				<c:if test="${sessionScope.id == reply.userid}">
					<td class="replybtn">
					<input type="hidden" name ="replynum" value="${reply.replynum}"/>
					<input type="button" value="삭제" onclick="replyDelete('${reply.replynum}')"/> 
					<input type="button" value="수정" onclick="replyModify('${reply.replynum}','${reply.replytext}')" />
					</td>
				</c:if>
				</tr>
			
			</c:forEach>
		</table>
	</div>
</body>
</html>
