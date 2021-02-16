<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="board.GuildBoardDataBean"%>
<%@ page import="board.GuildBoardDBBean"%>
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
<style type="text/css">
a, a:hover {
	color: #000000;
	text-decoration: none;
}
</style>
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
		window.history.back();
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
	
	int pageNumber = 1;
	if (request.getParameter("pageNumber") != null) {
		pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
	}
	int pageSize = 10;
	
	GuildBoardDBBean manager2 = GuildBoardDBBean.getInstance();
	
	int start = (pageNumber-1) * pageSize +1; // 현재 페이지에서 시작글 번호
	ArrayList<GuildBoardDataBean> listAll = manager2.getList();
	
	int count = listAll.size(); // 전체 글의 개수 (보여질 수 있는)
	if (count == (pageNumber-1)*pageSize) { // 페이지 삭제 대비
		 pageNumber -=1;
	}
	
	ArrayList<GuildBoardDataBean> list = manager2.getListView(listAll, start, pageSize);
	ArrayList<GuildBoardDataBean> noticeList = manager2.getNoticeList();
	
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
						<li class="active"><a href="guildBoard.jsp">길드원 게시판</a></li>
						<li><a href="guildNoticeBoard.jsp">길드원 공지사항</a></li>
						<%
						if (grade > 1){
						%>
						<li><a href="meetingLogBoard.jsp">운영진 회의록</a></li>
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
						<th style="width: 10%; background-color: #eeeeeee; text-align: center">번호</th>
						<th style="width: 50%; background-color: #eeeeeee; text-align: center">제목</th>
						<th style="width: 15%; background-color: #eeeeeee; text-align: center">작성자</th>
						<th style="width: 25%; background-color: #eeeeeee; text-align: center">작성일</th>
					</tr>
				</thead>
				<tbody>
					<%
					for (int i = 0; i < noticeList.size(); i++){
					%>
					<tr>
						<td><b>공지</b></td>
						<td><a href="guildBoard_view.jsp?writtenID=<%=noticeList.get(i).getWrittenID() %>&replyID=<%=noticeList.get(i).getReplyID()%>"><%=noticeList.get(i).getTitle() %></a></td>
						<td><%=noticeList.get(i).getUserID() %></td>
						<td><%=noticeList.get(i).getReg_Date() %></td>
					</tr>
					<%
					}
					int boardID = start;
					int replyCount = 0;
					for (int i = 0; i < list.size(); i++){
						if (list.get(i).getReplyID() > 0) { // 답글인 경우
							replyCount++;
							if (i == 0){ // 첫번째 글이면서 글의 첫 답글인 경우
								if (manager2.viewWritten(list.get(i).getWrittenID(), 0).getAvailable() == 1){ // 글이 존재하는 경우
					%>
					<tr>
						<td><%=boardID %>-<%=replyCount %></td>
						<td><a href="guildBoard_view.jsp?writtenID=<%=list.get(i).getWrittenID() %>&replyID=<%=list.get(i).getReplyID()%>"><%=list.get(i).getTitle() %></a></td>
						<td><%=list.get(i).getUserID() %></td>
						<td><%=list.get(i).getReg_Date() %></td>
					</tr>
					<%
								} else { // 글이 존재하지 않는 경우
					%>
					<tr>
						<td><%=start+i %></td>
						<td>삭제된 글입니다.</td>
						<td>****</td>
						<td> </td>
					</tr>
					<%
									boardID = start+i;
					%>
					<tr>
						<td><%=boardID %>-<%=replyCount %></td>
						<td><a href="guildBoard_view.jsp?writtenID=<%=list.get(i).getWrittenID() %>&replyID=<%=list.get(i).getReplyID()%>"><%=list.get(i).getTitle() %></a></td>
						<td><%=list.get(i).getUserID() %></td>
						<td><%=list.get(i).getReg_Date() %></td>
					</tr>
					<%
								} 
							} else if (replyCount == 1) { // 첫번째 글이 아니면서 첫 답글인 경우
								if(list.get(i-1).getWrittenID() == list.get(i).getWrittenID()){ // 위의 글과 글 일련번호가 같을때
					%>
					<tr>
						<td><%=boardID %>-<%=replyCount %></td>
						<td><a href="guildBoard_view.jsp?writtenID=<%=list.get(i).getWrittenID() %>&replyID=<%=list.get(i).getReplyID()%>"><%=list.get(i).getTitle() %></a></td>
						<td><%=list.get(i).getUserID() %></td>
						<td><%=list.get(i).getReg_Date() %></td>
					</tr>
					<%
								} else { // 위의 글과 일련번호가 다를때
					%>
					<tr>
						<td><%=start+i %></td>
						<td>삭제된 글입니다.</td>
						<td>****</td>
						<td> </td>
					</tr>
					<%
									boardID = start+i;
					%>
					<tr>
						<td><%=boardID %>-<%=replyCount %></td>
						<td><a href="guildBoard_view.jsp?writtenID=<%=list.get(i).getWrittenID() %>&replyID=<%=list.get(i).getReplyID()%>"><%=list.get(i).getTitle() %></a></td>
						<td><%=list.get(i).getUserID() %></td>
						<td><%=list.get(i).getReg_Date() %></td>
					</tr>
					<%
								}
							} else { // 첫번째 글이 아니면서 첫 답글이 아닌 경우
					%>
					<tr>
						<td><%=boardID %>-<%=replyCount %></td>
						<td><a href="guildBoard_view.jsp?writtenID=<%=list.get(i).getWrittenID() %>&replyID=<%=list.get(i).getReplyID()%>"><%=list.get(i).getTitle() %></a></td>
						<td><%=list.get(i).getUserID() %></td>
						<td><%=list.get(i).getReg_Date() %></td>
					</tr>
					<%
							}
						} else { // 답글이 아닌 경우
					%>
					<tr>
						<td><%=start+i %></td>
						<td><a href="guildBoard_view.jsp?writtenID=<%=list.get(i).getWrittenID() %>&replyID=<%=list.get(i).getReplyID()%>"><%=list.get(i).getTitle() %></a></td>
						<td><%=list.get(i).getUserID() %></td>
						<td><%=list.get(i).getReg_Date() %></td>
					</tr>
					<%
							boardID = start+i;
							replyCount = 0;
						}
					}
					%>
				</tbody>
			</table>
			<div class="btn-toolbar text-center" role="toolbar" aria-label="Toolbar with button groups">
			<%
				if (count > 0) {
					int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1); // 총 페이지의 개수
					int pageBlock = 10;
					
					int startPage;
					
					if (pageNumber % pageBlock == 1){
						startPage = pageNumber;
					} else {
						startPage = ((pageNumber - 1) / pageBlock) * pageBlock + 1;
					}
					
					int endPage = startPage + pageBlock - 1;
					
					if (endPage > pageCount) {
						endPage = pageCount;
					}
					if (startPage > pageBlock) {
			%>
				<div class="btn-group me-2" role="group" aria-label="First group" style="float:none; margin:0 auto">
					<button id="previousButton" name="<%=startPage-1 %>" onclick="page(this)" type="button" class="btn btn-secondary">&lt;&lt;</button>
				</div>
				<%
					}
					for (int i = startPage; i <= endPage; i++){
			%>
				<div class="btn-group me-2" role="group" aria-label="Second group" style="float:none; margin:0 auto">
					<%
						if(pageNumber == i) {
			%>
					<button id="pageNumbers" name="<%=i %>" onclick="page(this)" type="button" class="btn btn-secondary"
						style="color: #fff; background-color: #343a40; border-color: #343a40"><%=i %></button>
					<%
						} else {
			%>
					<button id="pageNumbers" name="<%=i %>" onclick="page(this)" type="button" class="btn btn-secondary"><%=i %></button>
					<%
						}
					}
			%>
				</div>
				<%
					if (endPage < pageCount) {
			%>
				<div class="btn-group me-2" role="group" aria-label="Third group" style="float:none; margin:0 auto">
					<button id="nextButton" name="<%=endPage + 1 %>" onclick="page(this)" type="button" class="btn btn-secondary">&gt;&gt;</button>
				</div>
				<%
					}
				}
			%>
			</div><br>
		</div>
		<div style="text-align: right">
			<a href="guildBoard_writeForm.jsp" class="btn btn-primary pull-right">글쓰기</a>
		</div>
	</div>
<script src="js/jquery-3.5.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script src="guildBoard.js"></script>
</body>
</html>