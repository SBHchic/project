<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="board.BoardQnADataBean"%>
<%@ page import="board.BoardQnADBBean"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="user.UserDBBean"%>
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
	
	int pageNumber = 1;
	if (request.getParameter("pageNumber") != null) {
		pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
	}
	int pageSize = 10;
	
	ArrayList<BoardQnADataBean> listAll = null;
	BoardQnADBBean manager2 = BoardQnADBBean.getInstance();
	
	int start = (pageNumber-1) * pageSize +1; // 현재 페이지에서 시작글 번호
	if (grade < 2) {
		ArrayList<BoardQnADataBean> list = manager2.getList_Submaster();
		listAll = manager2.getList_Member(userID, list);
	} else {
		listAll = manager2.getList_Submaster();
	}
	
	int count = listAll.size(); // 전체 글의 개수 (보여질 수 있는)
	if (count == (pageNumber-1)*pageSize) { // 페이지 삭제 대비
		 pageNumber -=1;
	}
	
	ArrayList<BoardQnADataBean> list = manager2.getListView(listAll, start, pageSize);
	
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
					</ul></li>
			</ul>
		</div>
		<%
			}
		%>
	</nav>
	<%
		if (!(userID.equals("") || userID == null)) {
	%>
	<div class="container">
		<div class="row">
			<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th style="background-color: #eeeeeee; text-align: center">번호</th>
						<th style="background-color: #eeeeeee; text-align: center">제목</th>
						<th style="background-color: #eeeeeee; text-align: center">작성자</th>
						<th style="background-color: #eeeeeee; text-align: center">작성일</th>
					</tr>
				</thead>
				<tbody>
					<%
					int boardID = start;
					int replyCount = 0;
					for (int i = 0; i < list.size(); i++){
						if (list.get(i).getBoardQnA_ReplyID() > 0) { // 답글인 경우
							replyCount++;
							if (i == 0){ // 첫번째 글이면서 글의 첫 답글인 경우
								if (manager2.viewWritten(list.get(i).getBoardQnA_ID(), 0) != null){ // 글이 존재하는 경우
					%>
					<tr>
						<td><%=boardID %>-<%=list.get(i).getBoardQnA_ReplyID() %></td>
						<td><a href="boardQnA_view.jsp?boardQnA_ID=<%=list.get(i).getBoardQnA_ID() %>&boardQnA_ReplyID=<%=list.get(i).getBoardQnA_ReplyID()%>"><%=list.get(i).getBoardQnA_Title() %></a></td>
						<td><%=list.get(i).getUserID() %></td>
						<td><%=list.get(i).getBoardQnA_Reg_Date() %></td>
					</tr>
					<%
								} else { // 글이 존재하지 않는 경우
					%>
					<tr>
						<td><%=start+i %></td>
						<td colspan="3">삭제된 글입니다.</td>
					</tr>
					<%
									boardID = start+i;
					%>
					<tr>
						<td><%=boardID %>-<%=list.get(i).getBoardQnA_ReplyID() %></td>
						<td><a href="boardQnA_view.jsp?boardQnA_ID=<%=list.get(i).getBoardQnA_ID() %>&boardQnA_ReplyID=<%=list.get(i).getBoardQnA_ReplyID()%>"><%=list.get(i).getBoardQnA_Title() %></a></td>
						<td><%=list.get(i).getUserID() %></td>
						<td><%=list.get(i).getBoardQnA_Reg_Date() %></td>
					</tr>
					<%
								} 
							} else if (replyCount == 1) { // 첫번째 글이 아니면서 첫 답글인 경우
								if(!(list.get(i-1).getBoardQnA_ID() == list.get(i).getBoardQnA_ID())){ // 위의 글과 글 일련번호가 같을때
					%>
					<tr>
						<td><%=boardID %>-<%=list.get(i).getBoardQnA_ReplyID() %></td>
						<td><a href="boardQnA_view.jsp?boardQnA_ID=<%=list.get(i).getBoardQnA_ID() %>&boardQnA_ReplyID=<%=list.get(i).getBoardQnA_ReplyID()%>"><%=list.get(i).getBoardQnA_Title() %></a></td>
						<td><%=list.get(i).getUserID() %></td>
						<td><%=list.get(i).getBoardQnA_Reg_Date() %></td>
					</tr>
					<%
								} else { // 위의 글과 일련번호가 다를때
					%>
					<tr>
						<td><%=start+i %></td>
						<td colspan="3">삭제된 글입니다.</td>
					</tr>
					<%
									boardID = start+i;
					%>
					<tr>
						<td><%=boardID %>-<%=list.get(i).getBoardQnA_ReplyID() %></td>
						<td><a href="boardQnA_view.jsp?boardQnA_ID=<%=list.get(i).getBoardQnA_ID() %>&boardQnA_ReplyID=<%=list.get(i).getBoardQnA_ReplyID()%>"><%=list.get(i).getBoardQnA_Title() %></a></td>
						<td><%=list.get(i).getUserID() %></td>
						<td><%=list.get(i).getBoardQnA_Reg_Date() %></td>
					</tr>
					<%
								}
							} else { // 첫번째 글이 아니면서 첫 답글이 아닌 경우
					%>
					<tr>
						<td><%=boardID %>-<%=list.get(i).getBoardQnA_ReplyID() %></td>
						<td><a href="boardQnA_view.jsp?boardQnA_ID=<%=list.get(i).getBoardQnA_ID() %>&boardQnA_ReplyID=<%=list.get(i).getBoardQnA_ReplyID()%>"><%=list.get(i).getBoardQnA_Title() %></a></td>
						<td><%=list.get(i).getUserID() %></td>
						<td><%=list.get(i).getBoardQnA_Reg_Date() %></td>
					</tr>
					<%
							}
						} else { // 답글이 아닌 경우
					%>
					<tr>
						<td><%=start+i %></td>
						<td><a href="boardQnA_view.jsp?boardQnA_ID=<%=list.get(i).getBoardQnA_ID() %>&boardQnA_ReplyID=<%=list.get(i).getBoardQnA_ReplyID()%>"><%=list.get(i).getBoardQnA_Title() %></a></td>
						<td><%=list.get(i).getUserID() %></td>
						<td><%=list.get(i).getBoardQnA_Reg_Date() %></td>
					</tr>
					<%
							boardID = start+i;
							replyCount = 0;
						}
					}
					%>
				</tbody>
			</table>
			<%
				if (count > 0) {
					int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);
					int startPage = 1;
					
					if (pageNumber % pageSize != 0){
						startPage = pageNumber / pageSize * pageSize + 1;
					} else {
						startPage = (pageNumber / pageSize - 1) * pageSize + 1;
					}
					
					int pageBlock = 10;
					int endPage = startPage + pageBlock - 1;
					
					if (endPage > pageCount) {
						endPage = pageCount;
					}
					if (startPage > pageBlock) {
			%>
			<div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups" style="width: 500px; margin: auto;">
				<div class="btn-group me-2" role="group" aria-label="First group">
					<button id="previousButton" name="<%=startPage-1 %>" onclick="p(this)" type="button" class="btn btn-primary"
						style="color: #fff; background-color: #6c757d; border-color: #6c757d">&lt;&lt;</button>
				</div>
				<%
					}
					for (int i = startPage; i <= endPage; i++){
			%>
				<div class="btn-group me-2" role="group" aria-label="Second group">
					<%
						if(pageNumber == i) {
			%>
					<button id="pageNumbers" name="<%=i %>" onclick="p(this)" type="button" class="btn btn-primary"
						style="color: #fff; background-color: #343a40; border-color: #343a40"></button>
					<%
						} else {
			%>
					<button id="pageNumbers" name="<%=i %>" onclick="p(this)" type="button" class="btn btn-primary"
						style="color: #fff; background-color: #6c757d; border-color: #6c757d"></button>
					<%
						}
					}
			%>
				</div>
				<%
					if (endPage > pageCount) {
			%>
				<div class="btn-group me-2" role="group" aria-label="Third group">
					<button id="nextButton" name="<%=endPage + 1 %>" onclick="p(this)" type="button" class="btn btn-primary"
						style="color: #fff; background-color: #6c757d; border-color: #6c757d">&gt;&gt;</button>
				</div>
				<%
					}
				}
			%>
			</div>
			<a href="writeQnAForm.jsp" class="btn btn-primary pull-right">글쓰기</a>
		</div>
	</div>
	<%
		}
	%>
</body>
</html>