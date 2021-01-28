<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equlv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0" />
<link rel="stylesheet" href="css/bootstrap.css" />
<script src="js/jquery-3.5.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script src="login.js"></script>
<script src="substarforce_item.js"></script>
<title>[리부트]얼음요새</title>
</head>
<body>
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
				<li><a href="#">길드원</a></li>
				<li><a href="boardQnA.jsp">QnA</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">회원정보<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="modify.jsp">회원정보 수정</a></li>
						<li><a id="logout" href="#">로그아웃</a></li>
					</ul></li>
			</ul>
		</div>
	</nav>
	<div class="container">
		<div class="col-lg-4"></div>
		<div class="col-lg-4">
			<div class="jumbotron" style="padding-top: 20px;">
				<h3 style="text-align: center;">아이템 개수 기준 강화 성공 확률</h3>
				<h4 style="text-align: center;">세부사항을 작성해주세요.</h4>
				<form class="row g-3">
					<div class="col-md-6">
						<label for="numberOfItems" class="form-label">표본 개수</label> <input
							type="number" class="form-control" id="numberOfItems" autofocus
							required>
					</div>
					<div class="col-md-6">
						<label for="level" class="form-label">아이템 레벨</label> <input
							type="number" class="form-control" id="level" required>
					</div>
					<div class="col-md-4">
						<label for="numberOfRealItems" class="form-label">본템 개수 제한</label>
						<input type="number" class="form-control" id="numberOfRealItems"
							required>
					</div>
					<div class="col-md-4">
						<label for="starforce" class="form-label">목표 스타포스</label> <input
							type="number" class="form-control" id="starforce" required>
					</div>
					<div class="col-md-4">
						<label for="ignoreDestory" class="form-label">파괴방지</label> <select
							class="form-select form-control" id="ignoreDestory" required>
							<option selected value="false">파괴방지 X</option>
							<option value="true">파괴방지 O</option>
						</select>
					</div>
					<div class="col-md-6">
						<label for="succededCatch" class="form-label">스타캐치 여부</label>
						<div class="form-check">
							<input type="radio" name="succededCatch" class="form-check-input"
								id="succededCatch" value="0" checked> <label
								class="form-check-label" for="succededCatch">스타캐치 X</label>
						</div>
						<div class="mb-3 form-check">
							<input type="radio" name="succededCatch" class="form-check-input"
								id="succededCatch" value="1"> <label
								class="form-check-label" for="succededCatch">합연산 (4.5%p
								증가)</label>
						</div>
						<div class="mb-3 form-check">
							<input type="radio" name="succededCatch" class="form-check-input"
								id="succededCatch" value="2"> <label
								class="form-check-label" for="succededCatch">곱연산 (1.045
								배)</label>
						</div>
					</div>
					<div class="col-md-6">
						<label for="mapleEvent" class="form-label">이벤트 여부</label>
						<div class="form-check">
							<input type="radio" name="mapleEvent" class="form-check-input"
								id="mapleEvent" value="0" checked> <label
								class="form-check-label" for="mapleEvent">이벤트 X</label>
						</div>
						<div class="mb-3 form-check">
							<input type="radio" name="mapleEvent" class="form-check-input"
								id="mapleEvent" value="1"> <label
								class="form-check-label" for="mapleEvent">10성 이하 1+1성</label>
						</div>
						<div class="mb-3 form-check">
							<input type="radio" name="mapleEvent" class="form-check-input"
								id="mapleEvent" value="2"> <label
								class="form-check-label" for="mapleEvent">5,10,15성에서
								강화확률 100%</label>
						</div>
					</div>
					<div class="col-md-12">
						<label for="toad" class="form-label">토드 여부</label>
						<div class="form-check">
							<input type="radio" name="radio" class="form-check-input"
								id="toad" value="false" checked> <label
								class="form-check-label" for="toad">토드 X</label>
						</div>
						<div class="mb-3 form-check">
							<input type="radio" name="radio" class="form-check-input"
								id="toad" value="true"> <label class="form-check-label"
								for="toad">토드 O</label>
						</div>
					</div>
					<div id="selectToadProperty" hidden>
						<div class="col-md-4">
							<label for="numberOfToadItems" class="form-label">토드템 개수
								제한 </label> <input type="number" class="form-control"
								id="numberOfToadItems" required>
						</div>
						<div class="col-md-4">
							<label for="toadToStarforce" class="form-label">토드템 목표
								스타포스</label> <input type="number" class="form-control"
								id="toadToStarforce" required>
						</div>
						<div class="col-md-4">
							<label for="toadIgnoreDestory" class="form-label">토드템
								파괴방지</label> <select class="form-select form-control"
								id="toadIgnoreDestory" required>
								<option selected value="false">파괴방지 X</option>
								<option value="true">파괴방지 O</option>
							</select>
						</div>
					</div>
					<div class="col-12">
						<button id="privious" class="btn btn-primary">이전</button>
						<button id="reset" class="btn btn-primary" type="reset">리셋</button>
						<button id="finalSubmit" class="btn btn-primary pull-right">확인</button>
					</div>
				</form>
				<div id="result"></div>
				<!-- 화면 표시부분 -->
			</div>
		</div>
		<div class="col-lg-4"></div>
	</div>
</body>
</html>