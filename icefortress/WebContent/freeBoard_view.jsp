<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="board.FreeBoardDataBean"%>
<%@ page import="board.FreeBoardDBBean"%>
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
	}

	int writtenID = 0;
	if (request.getParameter("writtenID") != null) {
	writtenID = Integer.parseInt(request.getParameter("writtenID"));
	}
	int replyID = -1;
	if (request.getParameter("replyID") != null) {
	replyID = Integer.parseInt(request.getParameter("replyID"));
	}

	FreeBoardDBBean manager2 = FreeBoardDBBean.getInstance();
	FreeBoardDataBean written = manager2.viewWritten(writtenID, replyID);
	
	if (writtenID == 0 || replyID == -1 || written.getAvailable() == 0) {
	%>
	<script>
		alert("유효하지 않은 글입니다.");
		window.history.back();
	</script>
	<%
	}

	ArrayList<FreeBoardDataBean> commentList = manager2.getCommentList(written);
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
						<li class="active"><a href="freeBoard.jsp">자유 게시판</a></li>
						<li><a href="freeBoard.jsp">QnA</a></li>
						<%
							if (grade >= 1){
						%>
						<li><a href="guildBoard.jsp">길드원 게시판</a></li>
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
		<div class="row">
			<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
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
				href="freeBoard_updateWrittenForm.jsp?writtenID=<%=written.getWrittenID()%>&replyID=<%=written.getReplyID()%>"
				class="btn btn-primary">수정</a>
			<button id="deleteWritten" name="<%=written.getWrittenID()%>,<%=written.getReplyID()%>" type="button"
				class="btn btn-primary">삭제</button>
			<%
			} else if (grade > 1) {
			%>
			<button id="deleteWritten" name="<%=written.getWrittenID()%>,<%=written.getReplyID()%>" type="button"
				class="btn btn-primary">삭제</button>
			<%
			}
			
			if (!(written.getNotice() == 1)){
			%>
			<a href="freeBoard_writeReplyForm.jsp?writtenID=<%=written.getWrittenID()%>" class="btn btn-primary pull-right">답글쓰기</a>
			<%
			}
			%>
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
									if (commentList.get(i).getCommentID_Re() == 0) { // 가져온 객체가 댓글이라면
								%>
								<li style="list-style: none">
									<div>
										<div
											id="comment_<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>">
											<div>
												<div id="idplace">
													<strong> <span><%=commentList.get(i).getUserID()%></span>
													</strong> <span>(<%=commentList.get(i).getReg_Date()%>)</span>
													<%
													if (userID.equals(commentList.get(i).getUserID())) {
													%>
													<span style="float: right"><a href="javascript:;"
														name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
														onclick="deleteComment(this)">[삭제]</a></span> <span style="float: right"><a
														name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
														href="javascript:;" onclick="update(this)">[수정]</a></span>
													<%
													} else if (grade > 1) {
													%>
													<span style="float: right"><a href="javascript:;"
														name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
														onclick="deleteComment(this)">[삭제]</a></span>
													<%
													}
													%>
												</div>
												<div id="contentplace">
													<span><%=commentList.get(i).getContent().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>")%></span>
												</div>
												<div id="buttonplace" style="text-align: right">
													<a
														name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>"
														href="javascript:;" onclick="commentReplyForm(this)">[답글]</a>
												</div>
											</div>
										</div>
										<div
											id="commentUpdate_<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
											hidden>
											<form>
												<table class="table table-sm" style="border: 1px solid #dddddd">
													<tr>
														<td style="width: 20%; text-align: center; vertical-align: middle"><strong> <span><%=userID%></span></strong></td>
														<td style="width: 60%; align: center"><textarea class="form-control"
																id="content_<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
																maxlength="2048"><%=commentList.get(i).getContent()%></textarea></td>
														<td style="width: 20%; text-align: center; vertical-align: middle"><a
															name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
															href="javascript:;" onclick="updateComment(this)" class="btn btn-secondary"
															style="color: #fff; background-color: #212529; border-color: #212529">수정</a> <a class="btn btn-primary"
															name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
															href="javascript:;" onclick="update(this)">취소</a></td>
													</tr>
												</table>
											</form>
										</div>
										<div
											id="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>re"
											hidden>
											<form>
												<table class="table table-sm" style="border: 1px solid #dddddd">
													<tr>
														<td style="width: 20%; text-align: center; vertical-align: middle">
															<!-- 대댓글 작성창 --> <strong> <span><%=userID%></span>
														</strong>
														</td>
														<td style="width: 60%; align: center"><textarea class="form-control"
																id="contentRe_<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>"
																maxlength="2048"></textarea></td>
														<td style="width: 20%; text-align: center; vertical-align: middle"><a class="btn btn-secondary"
															style="color: #fff; background-color: #212529; border-color: #212529"
															name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>"
															href="javascript:;" onclick="writeCommentReply(this)">등록</a></td>
													</tr>
												</table>
											</form>
										</div>
									</div><hr style="border-width: 2px; border-color: #dddddd">
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
													name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>"
													href="javascript:;" onclick="commentReplyForm(this)">[답글]</a>
											</div>
										</div>
										<div
											id="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>re"
											hidden>
											<form>
												<table class="table table-sm" style="border: 1px solid #dddddd">
													<tr>
														<td style="width: 20%; text-align: center; vertical-align: middle"><strong> <span><%=userID%></span>
														</strong></td>
														<td style="width: 60%; align: center"><textarea class="form-control"
																id="contentRe_<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>"
																maxlength="2048"></textarea></td>
														<td style="width: 20%; text-align: center; vertical-align: middle"><a class="btn btn-secondary"
															style="color: #fff; background-color: #212529; border-color: #212529"
															name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>"
															href="javascript:;" onclick="writeCommentReply(this)">등록</a></td>
													</tr>
												</table>
											</form>
										</div>
									</div><hr style="border-width: 2px; border-color: #dddddd">
								</li>
								<li style="list-style: none; padding-left: 20px; background-color: #eeeeee">
									<div
										id="comment_<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>">
										<div>
											<div id="idplace">
												<strong> <span><%=commentList.get(i).getUserID()%></span>
												</strong> <span>(<%=commentList.get(i).getReg_Date()%>)</span>
												<%
												if (userID.equals(commentList.get(i).getUserID())) {
												%>
												<span style="float: right"><a href="javascript:;"
													name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
													onclick="deleteComment(this)">[삭제]</a></span> <span style="float: right"><a
													name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
													href="javascript:;" onclick="update(this)">[수정]</a></span>
												<%
												} else if (grade > 1) {
												%>
												<span style="float: right"><a href="javascript:;"
													name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
													onclick="deleteComment(this)">[삭제]</a></span>
												<%
												}
												%>
											</div>
											<div id="contentplace">
												<span><%=commentList.get(i).getContent().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>")%></span>
											</div>
											<br>
										</div>
									</div>
									<div
										id="commentUpdate_<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
										hidden>
										<form>
											<table class="table table-sm" style="border: 1px solid #dddddd">
												<tr>
													<td style="width: 20%; text-align: center; vertical-align: middle"><strong> <span><%=userID%></span></strong></td>
													<td style="width: 60%; align: center"><textarea class="form-control"
															id="content_<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
															maxlength="2048"><%=commentList.get(i).getContent()%></textarea></td>
													<td style="width: 20%; text-align: center; vertical-align: middle"><a
														name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
														href="javascript:;" onclick="updateComment(this)" class="btn btn-secondary"
														style="color: #fff; background-color: #212529; border-color: #212529">수정</a> <a class="btn btn-primary"
														name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
														href="javascript:;" onclick="update(this)">취소</a></td>
												</tr>
											</table>
										</form>
									</div><hr style="border-width: 2px; border-color: #dddddd">
								</li>
								<%
								} else {
								if (commentList.get(i - 1).getCommentID() == commentList.get(i).getCommentID()) { // 대댓글의 댓글이 삭제되지 않은 경우
								%>
								<li style="list-style: none; padding-left: 20px; background-color: #eeeeee">
									<div
										id="comment_<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>">
										<div>
											<div id="idplace">
												<strong> <span><%=commentList.get(i).getUserID()%></span>
												</strong> <span>(<%=commentList.get(i).getReg_Date()%>)</span>
												<%
												if (userID.equals(commentList.get(i).getUserID())) {
												%>
												<span style="float: right"><a href="javascript:;"
													name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
													onclick="deleteComment(this)">[삭제]</a></span> <span style="float: right"><a
													name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
													href="javascript:;" onclick="update(this)">[수정]</a></span>
												<%
												} else if (grade > 1) {
												%>
												<span style="float: right"><a href="javascript:;"
													name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
													onclick="deleteComment(this)">[삭제]</a></span>
												<%
												}
												%>
											</div>
											<div id="contentplace">
												<span><%=commentList.get(i).getContent().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>")%></span>
											</div>
											<br>
										</div>
									</div>
									<div
										id="commentUpdate_<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
										hidden>
										<form>
											<table class="table table-sm" style="border: 1px solid #dddddd">
												<tr>
													<td style="width: 20%; text-align: center; vertical-align: middle"><strong> <span><%=userID%></span></strong></td>
													<td style="width: 60%; align: center"><textarea class="form-control"
															id="content_<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
															maxlength="2048"><%=commentList.get(i).getContent()%></textarea></td>
													<td style="width: 20%; text-align: center; vertical-align: middle"><a
														name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
														href="javascript:;" onclick="updateComment(this)" class="btn btn-secondary"
														style="color: #fff; background-color: #212529; border-color: #212529">수정</a> <a class="btn btn-primary"
														name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
														href="javascript:;" onclick="update(this)">취소</a></td>
												</tr>
											</table>
										</form>
									</div><hr style="border-width: 2px; border-color: #dddddd">
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
													name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>"
													href="javascript:;" onclick="commentReplyForm(this)">[답글]</a>
											</div>
										</div>
										<div
											id="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>re"
											hidden>
											<form>
												<table class="table table-sm" style="border: 1px solid #dddddd">
													<tr>
														<td style="width: 20%; text-align: center; vertical-align: middle"><strong> <span><%=userID%></span>
														</strong></td>
														<td style="width: 60%; align: center"><textarea class="form-control"
																id="contentRe_<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>"
																maxlength="2048"></textarea></td>
														<td style="width: 20%; text-align: center; vertical-align: middle"><a class="btn btn-secondary"
															style="color: #fff; background-color: #212529; border-color: #212529"
															name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>"
															href="javascript:;" onclick="writeCommentReply(this)">등록</a></td>
													</tr>
												</table>
											</form>
										</div>
									</div><hr style="border-width: 2px; border-color: #dddddd">
								</li>
								<li style="list-style: none; padding-left: 20px; background-color: #eeeeee">
									<div
										id="comment_<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>">
										<div>
											<div id="idplace">
												<strong> <span><%=commentList.get(i).getUserID()%></span>
												</strong> <span>(<%=commentList.get(i).getReg_Date()%>)</span>
												<%
												if (userID.equals(commentList.get(i).getUserID())) {
												%>
												<span style="float: right"><a href="javascript:;"
													name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
													onclick="deleteComment(this)">[삭제]</a></span> <span style="float: right"><a
													name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
													href="javascript:;" onclick="update(this)">[수정]</a></span>
												<%
												} else if (grade > 1) {
												%>
												<span style="float: right"><a href="javascript:;"
													name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
													onclick="deleteComment(this)">[삭제]</a></span>
												<%
												}
												%>
											</div>
											<div id="contentplace">
												<span><%=commentList.get(i).getContent().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>")%></span>
											</div>
											<br>
										</div>
									</div>
									<div
										id="commentUpdate_<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
										hidden>
										<form>
											<table class="table table-sm" style="border: 1px solid #dddddd">
												<tr>
													<td style="width: 20%; text-align: center; vertical-align: middle"><strong> <span><%=userID%></span></strong></td>
													<td style="width: 60%; align: center"><textarea class="form-control"
															id="content_<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
															maxlength="2048"><%=commentList.get(i).getContent()%></textarea></td>
													<td style="width: 20%; text-align: center; vertical-align: middle"><a
														name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
														href="javascript:;" onclick="updateComment(this)" class="btn btn-secondary"
														style="color: #fff; background-color: #212529; border-color: #212529">수정</a> <a class="btn btn-primary"
														name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
														href="javascript:;" onclick="update(this)">취소</a></td>
												</tr>
											</table>
										</form>
									</div><hr style="border-width: 2px; border-color: #dddddd">
								</li>
								<%
								}
								}
								} else { // 첫번째 코멘트가 아닌 경우
								%>
								<li style="list-style: none; padding-left: 20px; background-color: #eeeeee">
									<div
										id="comment_<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>">
										<div>
											<div id="idplace">
												<strong> <span><%=commentList.get(i).getUserID()%></span>
												</strong> <span>(<%=commentList.get(i).getReg_Date()%>)</span>
												<%
												if (userID.equals(commentList.get(i).getUserID())) {
												%>
												<span style="float: right"><a href="javascript:;"
													name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
													onclick="deleteComment(this)">[삭제]</a></span> <span style="float: right"><a
													name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
													href="javascript:;" onclick="update(this)">[수정]</a></span>
												<%
												} else if (grade > 1) {
												%>
												<span style="float: right"><a href="javascript:;"
													name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
													onclick="deleteComment(this)">[삭제]</a></span>
												<%
												}
												%>
											</div>
											<div id="contentplace">
												<span><%=commentList.get(i).getContent().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>")%></span>
											</div>
											<br>
										</div>
									</div>
									<div
										id="commentUpdate_<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
										hidden>
										<form>
											<table class="table table-sm" style="border: 1px solid #dddddd">
												<tr>
													<td style="width: 20%; text-align: center; vertical-align: middle"><strong> <span><%=userID%></span></strong></td>
													<td style="width: 60%; align: center"><textarea class="form-control"
															id="content_<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
															maxlength="2048"><%=commentList.get(i).getContent()%></textarea></td>
													<td style="width: 20%; text-align: center; vertical-align: middle"><a
														name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
														href="javascript:;" onclick="updateComment(this)" class="btn btn-secondary"
														style="color: #fff; background-color: #212529; border-color: #212529">수정</a> <a class="btn btn-primary"
														name="<%=commentList.get(i).getWrittenID()%>_<%=commentList.get(i).getReplyID()%>_<%=commentList.get(i).getCommentID()%>_<%=commentList.get(i).getCommentID_Re()%>"
														href="javascript:;" onclick="update(this)">취소</a></td>
												</tr>
											</table>
										</form>
									</div><hr style="border-width: 2px; border-color: #dddddd">
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
											<td style="width: 60%; text-align: center; vertical-align: middle"><textarea class="form-control" id="content"
													maxlength="2048"></textarea></td>
											<td style="width: 20%; text-align: center; vertical-align: middle">
												<button id="writeComment" type="button" class="btn btn-secondary" name="<%=writtenID %>,<%=replyID %>" style="color: #fff; background-color: #212529; border-color: #212529">등록</button>
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
<script src="js/jquery-3.5.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script src="freeBoard.js"></script>
</body>
</html>