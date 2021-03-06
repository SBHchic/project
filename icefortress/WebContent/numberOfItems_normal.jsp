<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	
	if (grade == -1) {
	%>
	<script>
		alert("로그인 이후 사용이 가능합니다.");
		location.href="loginForm.jsp";
	</script>
	<%
	}
	%>
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="main.jsp">[리부트]얼음요새</a>
		</div>
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="main.jsp">메인</a></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">아이템 강화<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="normalStarforceForm.jsp">일반 아이템</a></li>
						<li><a href="superiorStarforceForm.jsp">슈페리얼 아이템</a></li>
					</ul></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">강화 성공 확률<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li class="active"><a href="numberOfItems.jsp">아이템 개수 기준</a></li>
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
		<div class="col-lg-2"></div>
		<div class="col-lg-8">
			<div class="jumbotron" style="padding-top: 20px;">
				<h3 style="text-align: center;">노말아이템 개수 기준 강화 성공 확률</h3>
				<h4 style="text-align: center;">세부사항을 작성해주세요.</h4>
				<form class="row">
					<div class="col-sm-4 pb-custom-3">
						<label for="numberOfItems" class="form-label">표본 개수</label> <input
							type="number" class="form-control" placeholder="10000" id="numberOfItems"
							required>
					</div>
					<div class="col-sm-4 pb-custom-3">
						<label for="level" class="form-label">아이템 레벨</label> <input
							type="number" class="form-control" placeholder="160" id="level" required>
					</div>
					<div class="col-sm-4 pb-custom-3">
						<label for="numberOfRealItems" class="form-label">본템 개수 제한</label>
						<input type="number" class="form-control" placeholder="5" id="numberOfRealItems"
							required>
					</div>
					<div class="col-sm-4 pb-custom-3">
						<label for="fromStarforce" class="form-label">시작 스타포스</label> <input
							type="number" class="form-control" placeholder="0" id="fromStarforce" required>
					</div>
					<div class="col-sm-4 pb-custom-3">
						<label for="starforce" class="form-label">목표 스타포스</label> <input
							type="number" class="form-control" placeholder="22" id="starforce" required>
					</div>
					<div class="col-sm-4 pb-custom-3">
						<label for="ignoreDestroy" class="form-label">파괴방지</label> <select
							class="form-select form-control" id="ignoreDestroy" required>
							<option value="false">파괴방지 X</option>
							<option value="true">파괴방지 O</option>
						</select>
					</div>
					<div class="col-sm-6 pb-custom-3">
						<label for="succededCatch" class="form-label">스타캐치 여부</label>
						<div class="form-check">
							<input type="radio" name="succededCatch" class="form-check-input"
								id="succededCatch" value="0" checked> <label
								class="form-check-label" for="succededCatch">스타캐치 X</label>
						</div>
						<div class="form-check">
							<input type="radio" name="succededCatch" class="form-check-input"
								id="succededCatch" value="1"> <label
								class="form-check-label" for="succededCatch">합연산 (4.5%p
								증가)</label>
						</div>
						<div class="form-check">
							<input type="radio" name="succededCatch" class="form-check-input"
								id="succededCatch" value="2"> <label
								class="form-check-label" for="succededCatch">곱연산 (1.045
								배)</label>
						</div>
					</div>
					<div class="col-sm-6 pb-custom-3">
						<label for="mapleEvent" class="form-label">이벤트 여부</label>
						<div class="form-check">
							<input type="radio" name="mapleEvent" class="form-check-input"
								id="mapleEvent" value="0" checked> <label
								class="form-check-label" for="mapleEvent">이벤트 X</label>
						</div>
						<div class="form-check">
							<input type="radio" name="mapleEvent" class="form-check-input"
								id="mapleEvent" value="1"> <label
								class="form-check-label" for="mapleEvent">10성 이하 1+1성</label>
						</div>
						<div class="form-check">
							<input type="radio" name="mapleEvent" class="form-check-input"
								id="mapleEvent" value="2"> <label
								class="form-check-label" for="mapleEvent">5,10,15성에서
								강화확률 100%</label>
						</div>
					</div>
					<div class="col-md-12 pb-custom-3">
						<label for="toad" class="form-label">토드 여부</label>
						<div class="form-check">
							<input type="radio" name="toad" class="form-check-input"
								id="toad" value="false" checked> <label
								class="form-check-label" for="toad">토드 X</label>
						</div>
						<div class="form-check">
							<input type="radio" name="toad" class="form-check-input"
								id="toad" value="true"> <label class="form-check-label"
								for="toad">토드 O</label>
						</div>
					</div>
					<div id="selectToadProperty" hidden>
						<div class="col-sm-6 pb-custom-3">
							<label for="numberOfToadItems" class="form-label">토드템 개수
								제한 </label> <input type="number" class="form-control" placeholder="5"
								id="numberOfToadItems" required>
						</div>
						<div class="col-sm-6 pb-custom-3">
							<label for="toadToStarforce" class="form-label">토드템 목표
								스타포스</label> <input type="number" class="form-control" placeholder="16"
								id="toadToStarforce" required>
						</div>
						<div class="col-md-12 pb-custom-3">
							<label for="toadIgnoreDestroy" class="form-label">토드템
								파괴방지</label> <select class="form-select form-control"
								id="toadIgnoreDestroy" required>
								<option value="false">파괴방지 X</option>
								<option value="true">파괴방지 O</option>
							</select>
						</div>
					</div>
					<div class="col-12">
						<button id="previous" class="btn btn-primary">이전</button>
						<button id="reset" class="btn btn-danger" onclick="location.reload()">리셋</button>
						<button id="finalSubmit_normal" type="button" class="btn btn-primary pull-right">시행</button>
					</div>
				</form>
			</div>
			<div id="resultbox_normal" class="jumbotron"
				style="padding-top: 20px; height: auto;" hidden>
				<form class="row g-2">
					<div class="col-md-12">
						<label class="form-label">시행 결과</label>
						<div id="result_normal"></div>
					</div>
				</form>
			</div>
		</div>
		<div class="col-lg-2"></div>
	</div>
<script src="js/jquery-3.5.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script src="substarforce_item.js"></script>
</body>
</html>