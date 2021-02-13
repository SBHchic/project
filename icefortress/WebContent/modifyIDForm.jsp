<%@ page language="java" contentType="text/html; charset=UTF-8"
 	pageEncoding="UTF-8"%>
<%@ page import="user.UserDBBean" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0"/>
<link rel="stylesheet" href="css/bootstrap.css"/>
<link rel="stylesheet" href="css/bootstrap-theme.css"/>
<link rel="stylesheet" href="css/custom.css"/>
<title>[리부트]얼음요새</title>
<% request.setCharacterEncoding("UTF-8"); %>
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
	%>
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="main.jsp">[리부트]얼음요새</a>
		</div>
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="main.jsp">메인</a></li>
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">아이템 강화<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="normalStarforceForm.jsp">일반 아이템</a></li>
						<li><a href="superiorStarforceForm.jsp">슈페리얼 아이템</a></li>
					</ul>
				</li>
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">강화 성공 확률<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="numberOfItems.jsp">아이템 개수 기준</a></li>
						<li><a href="meso.jsp">메소 기준</a></li>
					</ul>
				</li>
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
						<li class="active"><a href="modify.jsp">회원정보 수정</a></li>
						<li><a href="logout.jsp">로그아웃</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</nav>
	<div class="container">
		<div class="col-lg-4"></div>
		<div class="col-lg-4">
			<div class="jumbotron" style="padding-top: 20px;">
				<h3 style="text-align: center;">아이디 변경</h3>
				<div class="form-group">
					<input id="newUserID" name="newUserID" type="text" class="form-control" placeholder="아이디" maxlength="20">
					<input id="checkID" type="button" class="btn btn-primary pull-right" value="중복확인">
				</div>
				<input id="modifyingID" type="submit" class="btn btn-primary" value="아이디 변경">
				<input id="cancel" type="button" class="btn btn-primary" value="취소">
			</div>
		</div>
		<div class="col-lg-4"></div>
	</div>
<script src="js/jquery-3.5.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script src="modify.js"></script>
</body>
</html>