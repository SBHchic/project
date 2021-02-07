<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="board.BoardQnADataBean" %>
<%@ page import="board.BoardQnADBBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="user.UserDBBean" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0" />
<link rel="stylesheet" href="css/bootstrap.css" />
<script src="js/jquery-3.5.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script src="login.js"></script>
<script src="boardQnA.js"></script>
<title>[리부트]얼음요새</title>
</head>
<body>
<%
	String userID = "";
	int grade = -1;
	UserDBBean manager = null;
	if (session.getAttribute("userID") != null) {
		userID = (String) session.getAttribute("userID");
		manager = UserDBBean.getInstance();
		grade = manager.checkGrade(userID);
	}
	
	if (grade == -1){
%>
<script>
	alert("로그인 이후 사용이 가능합니다.");
	window.history.back();
</script>
<%
	}
	
	int boardQnA_ID = 0;
	if (request.getParameter("boardQnA_ID") != null){
		boardQnA_ID = Integer.parseInt(request.getParameter("boardQnA_ID"));
	}
	int boardQnA_ReplyID = -1;
	if (request.getParameter("boardQnA_ReplyID") != null){
		boardQnA_ID = Integer.parseInt(request.getParameter("boardQnA_ReplyID"));
	}
	
	if (boardQnA_ID == 0 || boardQnA_ReplyID == -1){
%>
<script>
	alert("유효하지 않은 글입니다.");
	window.history.back();
</script>
<%
	}
	BoardQnADBBean manager2 = BoardQnADBBean.getInstance();
	BoardQnADataBean boardQnADataBean = manager2.viewWritten(boardQnA_ID, boardQnA_ReplyID);
	
	int check = manager2.checkAccessRights(userID, boardQnA_ID); // 접근 권한 확인
	if (!(check == 1)) { 
%>
<script>
	alert("접근권한이 없습니다.");
	window.history.back();
</script>
<%
	} else {
%>
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="main.jsp">[리부트]얼음요새</a>
		</div>
		<%
			if (userID.equals("") || userID == null) {
		%>
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="main.jsp">메인</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">접속하기<span
						class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="loginForm.jsp">로그인</a></li>
						<li><a href="registerForm.jsp">회원가입</a></li>
					</ul></li>
			</ul>
		</div>
		<%
			} else {
		%>
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="main.jsp">메인</a></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">아이템
						강화<span class="caret"></span>
				</a>
					<ul class="dropdown-menu">
						<li><a href="normalStarforceForm.jsp">일반 아이템</a></li>
						<li><a href="superiorStarforceForm.jsp">슈페리얼 아이템</a></li>
					</ul></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">강화
						성공 확률<span class="caret"></span>
				</a>
					<ul class="dropdown-menu">
						<li><a href="numberOfItems.jsp">아이템 개수 기준</a></li>
						<li><a href="meso.jsp">메소 기준</a></li>
					</ul></li>
				<li><a href="guildMembers.jsp">길드원</a></li>
				<li class="active"><a href="boardQnA.jsp">QnA</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">회원정보<span
						class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="modify.jsp">회원정보 수정</a></li>
						<li><a id="logout" href="#">로그아웃</a></li>
					</ul>
				</li>
			</ul>
		</div>
		<%
			}
		%>
	</nav>
	<div class="container">
		<div class="row">
			<form>
				<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
					<thead>
						<tr>
							<th colspan="2" style="background-color: #eeeeeee; text-align: center">글 수정</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input type="text" class="form-control" placeholder="글 제목" id="boardQnA_Title" maxlength="50" value="<%=boardQnADataBean.getBoardQnA_Title() %>"></td>
						</tr>
						<tr>
							<td><textarea class="form-control" placeholder="글 내용" id="boardQnA_Content" maxlength="2048"><%=boardQnADataBean.getBoardQnA_Content() %></textarea></td>
						</tr>
					</tbody>
				</table>
				<button id="previous" type="button" class="btn btn-primary">이전</button>
				<button id="submit_update" type="button" name="<%=boardQnADataBean.getBoardQnA_ID() %>,<%=boardQnADataBean.getBoardQnA_ReplyID() %>" class="btn btn-primary pull-right">수정</button>
			</form>
		</div>
	</div>
<%
	}
%>
</body>
</html>