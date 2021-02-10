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

	if (grade == -1) {
	%>
	<script>
		alert("로그인 이후 사용이 가능합니다.");
		window.history.back();
	</script>
	<%
	}

	int boardQnA_ID = 0;
	if (request.getParameter("boardQnA_ID") != null) {
	boardQnA_ID = Integer.parseInt(request.getParameter("boardQnA_ID"));
	}
	int boardQnA_ReplyID = -1;
	if (request.getParameter("boardQnA_ReplyID") != null) {
	boardQnA_ReplyID = Integer.parseInt(request.getParameter("boardQnA_ReplyID"));
	}

	if (boardQnA_ID == 0 || boardQnA_ReplyID == -1) {
	%>
	<script>
		alert("유효하지 않은 글입니다.");
		window.history.back();
	</script>
	<%
	}
	BoardQnADBBean manager2 = BoardQnADBBean.getInstance();
	BoardQnADataBean written = manager2.viewWritten(boardQnA_ID, boardQnA_ReplyID);

	ArrayList<BoardQnADataBean> commentList = manager2.getCommentList(written);

	int check = manager2.checkAccessRights(userID, boardQnA_ID); // 접근 권한 확인
	if (!(check == 1 || grade > 1)) {
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
					</ul></li>
			</ul>
		</div>
		<%
		}
		%>
	</nav>
	<div class="container">
		<div class="row">
			<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="6" style="background-color: #eeeeee; text-align: center"><%=written.getBoardQnA_Title().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>")%></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>작성자</td>
						<td colspan="2"><%=written.getUserID()%></td>
						<td>작성일</td>
						<td colspan="2"><%=written.getBoardQnA_Reg_Date()%></td>
					</tr>
					<tr>
						<td colspan="6" style="min-height: 200px; text-align: left"><%=written.getBoardQnA_Content().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>")%></td>
					</tr>
				</tbody>
			</table>
			<button id="previous" type="button" class="btn btn-primary">목록</button>
			<%
			if (userID != null && userID.equals(written.getUserID())) {
			%>
			<a
				href="boardQnA_updateWrittenForm.jsp?boardQnA_ID=<%=written.getBoardQnA_ID()%>&boardQnA_ReplyID=<%=written.getBoardQnA_ReplyID()%>"
				class="btn btn-primary">수정</a>
			<button id="deleteWritten" name="<%=written.getBoardQnA_ID()%>,<%=written.getBoardQnA_ReplyID()%>" type="button"
				class="btn btn-primary">삭제</button>
			<%
			} else if (grade > 1) {
			%>
			<button id="deleteWritten" name="<%=written.getBoardQnA_ID()%>,<%=written.getBoardQnA_ReplyID()%>" type="button"
				class="btn btn-primary">삭제</button>
			<%
			}
			%>
			<a href="boardQnA_writeReplyForm.jsp?boardQnA_ID=<%=written.getBoardQnA_ID()%>" class="btn btn-primary pull-right">답글쓰기</a>
		</div>
	</div>
	<div class="container">
		<div class="jumbotron" style="padding-top: 20px; background-color: #f5f5f5">
			<table class="table table-sm" style="border: 1px solid #dddddd">
				<thead>
					<tr>
						<th><h4>
								<strong>코멘트</strong>
							</h4></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							<ul>
								<%
								int commentReplyCount = 0;
								for (int i = 0; i < commentList.size(); i++) {
									if (commentList.get(i).getBoardQnA_CommentID_Re() == 0) { // 가져온 객체가 댓글이라면
								%>
								<li style="list-style: none">
									<div>
										<div
											id="comment_<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>">
											<div>
												<div id="idplace">
													<!-- 답글을 눌렀을때 정보를 얻기 위해서는 ID,CommentID를 알면 될듯
												이부분의 답글작성부분만 보이게 하려면 위의 div 아이디를 CommentID로 설정하고
												아래의 div 아이디를 같은 아이디 + re로 설정한 다음 onclick과 js를 이용하면 될듯
											 -->
													<strong> <span><%=commentList.get(i).getUserID()%></span>
													</strong> <span><%=commentList.get(i).getBoardQnA_Reg_Date()%></span>
													<%
													if (userID.equals(commentList.get(i).getUserID())) {
													%>
													<span style="float: right"><a href="javascript:;"
														name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
														onclick="deleteComment(this)">[삭제]</a></span> <span style="float: right"><a
														name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
														href="javascript:;" onclick="update(this)">[수정]</a></span>
													<%
													} else if (grade > 1) {
													%>
													<span style="float: right"><a href="javascript:;"
														name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
														onclick="deleteComment(this)">[삭제]</a></span>
													<%
													}
													%>
												</div>
												<div id="contentplace">
													<span><%=commentList.get(i).getBoardQnA_Content().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>")%></span>
												</div>
												<div id="buttonplace" style="text-align: right">
													<a
														name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>"
														href="javascript:;" onclick="commentReply(this)">[답글]</a>
												</div>
											</div>
										</div>
										<div
											id="commentUpdate_<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
											hidden>
											<form>
												<table class="table table-sm" style="border: 1px solid #dddddd">
													<tr>
														<td style="width: 20%; text-align: center; vertical-align: middle"><strong> <span><%=userID%></span></strong></td>
														<td style="width: 60%; align: center"><textarea class="form-control"
																id="boardQnA_Content_<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
																maxlength="2048"><%=commentList.get(i).getBoardQnA_Content()%></textarea></td>
														<td style="width: 20%; text-align: center; vertical-align: middle"><a
															name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
															href="javascript:;" onclick="updateComment(this)" class="btn btn-primary"
															style="color: #fff; background-color: #212529; border-color: #212529">수정</a> <a class="btn btn-primary"
															name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
															href="javascript:;" onclick="update(this)">취소</a></td>
													</tr>
												</table>
											</form>
										</div>
										<div
											id="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>re"
											hidden>
											<form>
												<table class="table table-sm" style="border: 1px solid #dddddd">
													<tr>
														<td style="width: 20%; text-align: center; vertical-align: middle">
															<!-- 대댓글 작성창 --> <strong> <span><%=userID%></span>
														</strong>
														</td>
														<td style="width: 60%; align: center"><textarea class="form-control"
																id="boardQnA_ContentRe_<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>"
																maxlength="2048"></textarea></td>
														<td style="width: 20%; text-align: center; vertical-align: middle"><a class="btn btn-primary"
															style="color: #fff; background-color: #212529; border-color: #212529"
															name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>"
															href="javascript:;" onclick="writeCommentReply(this)">등록</a></td>
													</tr>
												</table>
											</form>
										</div>
									</div>
								</li>
								<%
								commentReplyCount = 0;
								} else { // 대댓글인 경우
								commentReplyCount++;
								if (commentReplyCount == 1) { // 첫번째 코멘트인 경우
									if (i == 0) { // 댓글의 첫번째일 때
								%>
								<li style="list-style: none">
									<div>
										<div id="comment">
											<div id="idplace">
												<strong> <span>****</span></strong>
											</div>
											<div id="contentplace">
												<span>삭제된 댓글입니다.</span>
											</div>
											<div id="buttonplace" style="text-align: right">
												<a
													name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>"
													href="javascript:;" onclick="commentReply(this)">[답글]</a>
											</div>
										</div>
										<div
											id="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>re"
											hidden>
											<form>
												<table class="table table-sm" style="border: 1px solid #dddddd">
													<tr>
														<td style="width: 20%; text-align: center; vertical-align: middle"><strong> <span><%=userID%></span>
														</strong></td>
														<td style="width: 60%; align: center"><textarea class="form-control"
																id="boardQnA_ContentRe_<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>"
																maxlength="2048"></textarea></td>
														<td style="width: 20%; text-align: center; vertical-align: middle"><a class="btn btn-primary"
															style="color: #fff; background-color: #212529; border-color: #212529"
															name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>"
															href="javascript:;" onclick="writeCommentReply(this)">등록</a></td>
													</tr>
												</table>
											</form>
										</div>
									</div>
								</li>
								<li style="list-style: none; padding-left: 20px; background-color: #eeeeee">
									<div
										id="comment_<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>">
										<div>
											<div id="idplace">
												<strong> <span><%=commentList.get(i).getUserID()%></span>
												</strong> <span><%=commentList.get(i).getBoardQnA_Reg_Date()%></span>
												<%
												if (userID.equals(commentList.get(i).getUserID())) {
												%>
												<span style="float: right"><a href="javascript:;"
													name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
													onclick="deleteComment(this)">[삭제]</a></span> <span style="float: right"><a
													name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
													href="javascript:;" onclick="update(this)">[수정]</a></span>
												<%
												} else if (grade > 1) {
												%>
												<span style="float: right"><a href="javascript:;"
													name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
													onclick="deleteComment(this)">[삭제]</a></span>
												<%
												}
												%>
											</div>
											<div id="contentplace">
												<span><%=commentList.get(i).getBoardQnA_Content().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>")%></span>
											</div>
											<br>
										</div>
									</div>
									<div
										id="commentUpdate_<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
										hidden>
										<form>
											<table class="table table-sm" style="border: 1px solid #dddddd">
												<tr>
													<td style="width: 20%; text-align: center; vertical-align: middle"><strong> <span><%=userID%></span></strong></td>
													<td style="width: 60%; align: center"><textarea class="form-control"
															id="boardQnA_Content_<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
															maxlength="2048"><%=commentList.get(i).getBoardQnA_Content()%></textarea></td>
													<td style="width: 20%; text-align: center; vertical-align: middle"><a
														name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
														href="javascript:;" onclick="updateComment(this)" class="btn btn-primary"
														style="color: #fff; background-color: #212529; border-color: #212529">수정</a> <a class="btn btn-primary"
														name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
														href="javascript:;" onclick="update(this)">취소</a></td>
												</tr>
											</table>
										</form>
									</div>
								</li>
								<%
								} else {
								if (commentList.get(i - 1).getBoardQnA_CommentID() == commentList.get(i).getBoardQnA_CommentID()) { // 대댓글의 댓글이 삭제되지 않은 경우
								%>
								<li style="list-style: none; padding-left: 20px; background-color: #eeeeee">
									<div
										id="comment_<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>">
										<div>
											<div id="idplace">
												<strong> <span><%=commentList.get(i).getUserID()%></span>
												</strong> <span><%=commentList.get(i).getBoardQnA_Reg_Date()%></span>
												<%
												if (userID.equals(commentList.get(i).getUserID())) {
												%>
												<span style="float: right"><a href="javascript:;"
													name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
													onclick="deleteComment(this)">[삭제]</a></span> <span style="float: right"><a
													name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
													href="javascript:;" onclick="update(this)">[수정]</a></span>
												<%
												} else if (grade > 1) {
												%>
												<span style="float: right"><a href="javascript:;"
													name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
													onclick="deleteComment(this)">[삭제]</a></span>
												<%
												}
												%>
											</div>
											<div id="contentplace">
												<span><%=commentList.get(i).getBoardQnA_Content().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>")%></span>
											</div>
											<br>
										</div>
									</div>
									<div
										id="commentUpdate_<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
										hidden>
										<form>
											<table class="table table-sm" style="border: 1px solid #dddddd">
												<tr>
													<td style="width: 20%; text-align: center; vertical-align: middle"><strong> <span><%=userID%></span></strong></td>
													<td style="width: 60%; align: center"><textarea class="form-control"
															id="boardQnA_Content_<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
															maxlength="2048"><%=commentList.get(i).getBoardQnA_Content()%></textarea></td>
													<td style="width: 20%; text-align: center; vertical-align: middle"><a
														name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
														href="javascript:;" onclick="updateComment(this)" class="btn btn-primary"
														style="color: #fff; background-color: #212529; border-color: #212529">수정</a> <a class="btn btn-primary"
														name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
														href="javascript:;" onclick="update(this)">취소</a></td>
												</tr>
											</table>
										</form>
									</div>
								</li>
								<%
								} else { // 대댓글의 댓글이 삭제된 경우
								%>
								<li style="list-style: none">
									<div>
										<div id="comment">
											<div id="idplace">
												<strong> <span>****</span></strong>
											</div>
											<div id="contentplace">
												<span>삭제된 댓글입니다.</span>
											</div>
											<div id="buttonplace" style="text-align: right">
												<a
													name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>"
													href="javascript:;" onclick="commentReply(this)">[답글]</a>
											</div>
										</div>
										<div
											id="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>re"
											hidden>
											<form>
												<table class="table table-sm" style="border: 1px solid #dddddd">
													<tr>
														<td style="width: 20%; text-align: center; vertical-align: middle"><strong> <span><%=userID%></span>
														</strong></td>
														<td style="width: 60%; align: center"><textarea class="form-control"
																id="boardQnA_ContentRe_<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>"
																maxlength="2048"></textarea></td>
														<td style="width: 20%; text-align: center; vertical-align: middle"><a
															name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>"
															href="javascript:;" onclick="writeCommentReply(this)">등록</a></td>
													</tr>
												</table>
											</form>
										</div>
									</div>
								</li>
								<li style="list-style: none; padding-left: 20px; background-color: #eeeeee">
									<div
										id="comment_<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>">
										<div>
											<div id="idplace">
												<strong> <span><%=commentList.get(i).getUserID()%></span>
												</strong> <span><%=commentList.get(i).getBoardQnA_Reg_Date()%></span>
												<%
												if (userID.equals(commentList.get(i).getUserID())) {
												%>
												<span style="float: right"><a href="javascript:;"
													name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
													onclick="deleteComment(this)">[삭제]</a></span> <span style="float: right"><a
													name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
													href="javascript:;" onclick="update(this)">[수정]</a></span>
												<%
												} else if (grade > 1) {
												%>
												<span style="float: right"><a href="javascript:;"
													name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
													onclick="deleteComment(this)">[삭제]</a></span>
												<%
												}
												%>
											</div>
											<div id="contentplace">
												<span><%=commentList.get(i).getBoardQnA_Content().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>")%></span>
											</div>
											<br>
										</div>
									</div>
									<div
										id="commentUpdate_<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
										hidden>
										<form>
											<table class="table table-sm" style="border: 1px solid #dddddd">
												<tr>
													<td style="width: 20%; text-align: center; vertical-align: middle"><strong> <span><%=userID%></span></strong></td>
													<td style="width: 60%; align: center"><textarea class="form-control"
															id="boardQnA_Content_<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
															maxlength="2048"><%=commentList.get(i).getBoardQnA_Content()%></textarea></td>
													<td style="width: 20%; text-align: center; vertical-align: middle"><a
														name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
														href="javascript:;" onclick="updateComment(this)" class="btn btn-primary"
														style="color: #fff; background-color: #212529; border-color: #212529">수정</a> <a class="btn btn-primary"
														name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
														href="javascript:;" onclick="update(this)">취소</a></td>
												</tr>
											</table>
										</form>
									</div>
								</li>
								<%
								}
								}
								} else { // 첫번째 코멘트가 아닌 경우
								%>
								<li style="list-style: none; padding-left: 20px; background-color: #eeeeee">
									<div
										id="comment_<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>">
										<div>
											<div id="idplace">
												<strong> <span><%=commentList.get(i).getUserID()%></span>
												</strong> <span><%=commentList.get(i).getBoardQnA_Reg_Date()%></span>
												<%
												if (userID.equals(commentList.get(i).getUserID())) {
												%>
												<span style="float: right"><a href="javascript:;"
													name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
													onclick="deleteComment(this)">[삭제]</a></span> <span style="float: right"><a
													name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
													href="javascript:;" onclick="update(this)">[수정]</a></span>
												<%
												} else if (grade > 1) {
												%>
												<span style="float: right"><a href="javascript:;"
													name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
													onclick="deleteComment(this)">[삭제]</a></span>
												<%
												}
												%>
											</div>
											<div id="contentplace">
												<span><%=commentList.get(i).getBoardQnA_Content().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>")%></span>
											</div>
											<br>
										</div>
									</div>
									<div
										id="commentUpdate_<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
										hidden>
										<form>
											<table class="table table-sm" style="border: 1px solid #dddddd">
												<tr>
													<td style="width: 20%; text-align: center; vertical-align: middle"><strong> <span><%=userID%></span></strong></td>
													<td style="width: 60%; align: center"><textarea class="form-control"
															id="boardQnA_Content_<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
															maxlength="2048"><%=commentList.get(i).getBoardQnA_Content()%></textarea></td>
													<td style="width: 20%; text-align: center; vertical-align: middle"><a
														name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
														href="javascript:;" onclick="updateComment(this)" class="btn btn-primary"
														style="color: #fff; background-color: #212529; border-color: #212529">수정</a> <a class="btn btn-primary"
														name="<%=commentList.get(i).getBoardQnA_ID()%>,<%=commentList.get(i).getBoardQnA_ReplyID()%>,<%=commentList.get(i).getBoardQnA_CommentID()%>,<%=commentList.get(i).getBoardQnA_CommentID_Re()%>"
														href="javascript:;" onclick="update(this)">취소</a></td>
												</tr>
											</table>
										</form>
									</div>
								</li>
								<%
								}
								}
								}
								%>
							</ul>
							<div>
								<form>
									<table class="table table-sm" style="border: 1px solid #dddddd">
										<tr>
											<!-- 댓글 작성창 -->
											<!-- 댓글 작성창과 대댓글 작성창은 거의 비슷하지만 CommentID, CommentID_Re가 달라짐 -->
											<td style="width: 20%; text-align: center; vertical-align: middle"><strong> <span><%=userID%></span>
											</strong></td>
											<td style="width: 60%; text-align: center; vertical-align: middle"><textarea class="form-control" id="boardQnA_Content"
													maxlength="2048"></textarea></td>
											<td style="width: 20%; text-align: center; vertical-align: middle">
												<button id="writeComment" type="button" class="btn" style="color: #fff; background-color: #212529; border-color: #212529">등록</button>
											</td>
										</tr>
									</table>
								</form>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<%
	}
	%>
</body>
</html>