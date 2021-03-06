<%@ page language="java" contentType="text/html; charset=UTF-8"
 	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0"/>
<link rel="stylesheet" href="css/bootstrap.css"/>
<link rel="stylesheet" href="css/bootstrap-theme.css"/>
<link rel="stylesheet" href="css/custom.css"/>
<title>[리부트]얼음요새</title>
</head>
<body>
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
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">접속하기<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li class="active"><a href="loginForm.jsp">로그인</a></li>
						<li><a href="registerForm.jsp">회원가입</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</nav>
	<div class="container">
		<div class="col-lg-4"></div>
		<div class="col-lg-4">
			<div class="jumbotron" style="padding-top: 20px;">
				<h3 style="text-align: center;">로그인</h3>
				<div class="form-group">
					<input id="userID" name="userID" type="text" class="form-control" placeholder="아이디" maxlength="20" autofocus>
				</div>
				<div class="form-group">
					<input id="userPassword" name="userPassword" type="password" class="form-control" placeholder="비밀번호" maxlength="20">
				</div>
				<div style="padding-bottom: 10px">
					<button id="login" type="button" class="btn btn-primary form-control">로그인</button>
				</div>
				<a href="registerForm.jsp" class="btn btn-primary form-control">회원가입</a>
			</div>
		</div>
		<div class="col-lg-4"></div>
	</div>
<script src="js/jquery-3.5.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script src="login.js"></script>
</body>
</html>