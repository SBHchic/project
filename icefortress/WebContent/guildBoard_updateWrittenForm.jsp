<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="board.GuildBoardDataBean" %>
<%@ page import="board.GuildBoardDBBean" %>
<%@ page import="user.UserDBBean" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0" />
<link rel="stylesheet" href="css/bootstrap.css" />
<link rel="stylesheet" href="css/bootstrap-theme.css"/>
<link rel="stylesheet" href="css/custom.css"/>
<title>[리부트]얼음요새</title>
</head>
<body>
<%
	String userID = "";
	UserDBBean manager = UserDBBean.getInstance();
	if (session.getAttribute("userID") != null) {
		userID = (String) session.getAttribute("userID");
	}
	int grade = manager.checkGrade(userID);
	
	if (grade == -1){
%>
<script>
	alert("로그인 이후 사용이 가능합니다.");
	location.href="loginForm.jsp";
</script>
<%
	} else if (grade < 1) {
	%>
	<script>
		alert("길드원부터 사용 가능합니다.");
		window.history.back();
	</script>
	<%
	}
	
	int writtenID = 0;
	if (request.getParameter("writtenID") != null){
		writtenID = Integer.parseInt(request.getParameter("writtenID"));
	}
	int replyID = -1;
	if (request.getParameter("replyID") != null){
		replyID = Integer.parseInt(request.getParameter("replyID"));
	}
	int pageNumber = 1;
	if (request.getParameter("pageNumber") != null){
		pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
	}
	
	GuildBoardDBBean manager2 = GuildBoardDBBean.getInstance();
	GuildBoardDataBean written = manager2.viewWritten(writtenID, replyID);

	if (writtenID == 0 || replyID == -1 || written.getAvailable() == 0){
%>
<script>
	alert("존재하지 않는 글입니다.");
	window.history.back();
</script>
<%
	}
	
	if (!(userID.equals(written.getUserID()))) { 
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
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">게시판<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="freeBoard.jsp">자유 게시판</a></li>
						<li><a href="boardQnA.jsp">QnA</a></li>
						<li class="active"><a href="guildBoard.jsp">길드원 게시판</a></li>
					</ul>
				</li>
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">길드원<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="guildMembers.jsp">길드 구성원</a></li>
						<li><a href="nobelesseTable.jsp">길드원 노블표</a></li>
					</ul>
				</li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">회원정보<span
						class="caret"></span></a>
					<ul class="dropdown-menu">
					<%
						if(grade >= 2){
					%>
						<li><a href="manager.jsp">관리</a></li>
					<%
						}
					%>
						<li><a href="modify.jsp">회원정보 수정</a></li>
						<li><a href="logout.jsp">로그아웃</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</nav>
	<div class="container">
		<div class="row">
			<form>
				<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
					<thead>
						<tr>
							<th style="background-color: #eeeeeee; text-align: center">글 수정</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input type="text" class="form-control" placeholder="글 제목" id="title" maxlength="50" value="<%=written.getTitle() %>"></td>
						</tr>
						<tr>
							<td><textarea class="form-control" rows="20" placeholder="글 내용" id="content" maxlength="2048"><%=written.getContent() %></textarea></td>
						</tr>
						<%
							if (grade > 1){
						%>
						<tr>
							<td>
								<div class="form-check">
									<%
									if (written.getNotice() == 1){
								%>
									<input type="checkbox" class="form-check-input" id="notice" value="1" checked>
								<%
									} else {
								%>
									<input type="checkbox" class="form-check-input" id="notice" value="1">
								<%
									}
								%>
									<label class="form-check-label" for="notice">공지사항으로 작성</label>
								</div>
							</td>
						</tr>
						<%
							}
						%>
					</tbody>
				</table>
				<button id="previous" type="button" class="btn btn-primary">이전</button>
				<button id="submit_update" type="button" name="<%=written.getWrittenID() %>,<%=written.getReplyID() %>,<%=pageNumber %>" class="btn btn-primary pull-right">수정</button>
			</form>
		</div>
	</div>
<%
	}
%>
<script src="js/jquery-3.5.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script src="guildBoard.js"></script>
</body>
</html>