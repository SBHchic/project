<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="board.GuildBoard_NoticeAndMeetingLogDataBean"%>
<%@ page import="board.GuildBoard_NoticeAndMeetingLogDBBean"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="user.UserDBBean"%>
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

	if (grade == -1) {
	%>
	<script>
		alert("로그인 이후 사용이 가능합니다.");
		window.history.back();
	</script>
	<%
	} else if (grade < 2) {
	%>
	<script>
		alert("운영진부터 사용 가능합니다.");
		window.history.back();
	</script>
	<%
	}

	int writtenID = 0;
	if (request.getParameter("writtenID") != null) {
	writtenID = Integer.parseInt(request.getParameter("writtenID"));
	}

	GuildBoard_NoticeAndMeetingLogDBBean manager2 = GuildBoard_NoticeAndMeetingLogDBBean.getInstance();
	GuildBoard_NoticeAndMeetingLogDataBean written = manager2.viewWritten(writtenID);
	
	if (writtenID == 0 || written.getAvailable() == 0 || written.getLocation() != 0) {
	%>
	<script>
		alert("유효하지 않은 글입니다.");
		window.history.back();
	</script>
	<%
	}
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
						<%
							if (grade >= 1){
						%>
						<li class="active"><a href="guildBoard.jsp">길드원 게시판</a></li>
						<%
							}
						%>
					</ul>
				</li>
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">길드원<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="guildMembers.jsp">길드 구성원</a></li>
						<%
							if (grade >= 1){
						%>
						<li><a href="nobelesseTable.jsp">길드원 노블표</a></li>
						<%
							}
						%>
					</ul>
				</li>
				
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">회원정보<span class="caret"></span></a>
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
		<div class="jumbotron" style="padding-top: 20px;">
			<nav class="navbar navbar-default">
				<div class="collapse navbar-collapse">
					<ul class="nav navbar-nav">
						<li><a href="guildBoard.jsp">길드원 게시판</a></li>
						<li><a href="guildNoticeBoard.jsp">길드원 공지사항</a></li>
						<%
						if (grade > 1){
						%>
						<li class="active"><a href="meetingLogBoard.jsp">운영진 회의록</a></li>
						<%
						}
						%>
					</ul>
				</div>
			</nav>
		</div>
		<div class="row">
			<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd; padding-top: 20px">
				<thead>
					<tr>
						<th colspan="6" style="background-color: #eeeeee; text-align: center"><%=written.getTitle().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>")%></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>작성자</td>
						<td colspan="2"><%=written.getUserID()%></td>
						<td>작성일</td>
						<td colspan="2"><%=written.getReg_Date()%></td>
					</tr>
					<tr>
						<td colspan="6" style="min-height: 200px; text-align: left"><%=written.getContent().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>")%></td>
					</tr>
				</tbody>
			</table>
			<button id="previous" type="button" class="btn btn-primary">목록</button>
			<%
			if (userID.equals(written.getUserID())) {
			%>
			<a
				href="meetingLogBoard_updateWrittenForm.jsp?writtenID=<%=written.getWrittenID()%>"
				class="btn btn-primary">수정</a>
			<button id="deleteWritten" name="<%=written.getWrittenID()%>_<%=written.getLocation() %>" type="button"
				class="btn btn-primary">삭제</button>
			<%
			} else if (grade > 1) {
			%>
			<button id="deleteWritten" name="<%=written.getWrittenID()%>_<%=written.getLocation() %>" type="button"
				class="btn btn-primary">삭제</button>
			<%
			}
			%>
		</div>
	</div>
<script src="js/jquery-3.5.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script src="guildNoticeAndMeetingLogBoard.js"></script>
</body>
</html>