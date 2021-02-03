<%@ page language="java" contentType="text/html; charset=UTF-8"
 	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equlv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0"/>
<link rel="stylesheet" href="css/bootstrap.css"/>
<%@ page import="guild.GuildMemberList" %>
<%@ page import="java.util.ArrayList" %>
<script src="js/jquery-3.5.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script src="login.js"></script>
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
				<li class="active"><a href="guildMembers.jsp">길드원</a></li>
				<li><a href="boardQnA.jsp">QnA</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">회원정보<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="modify.jsp">회원정보 수정</a></li>
						<li><a id="logout" href="#">로그아웃</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</nav>
	<div class="container">
		<div class="col-lg-2"></div>
		<div class="col-lg-8">
			<div class="jumbotron" style="padding-top: 20px;">
				<nav class="navbar navbar-default">
					<div class="collapse navbar-collapse">
						<ul class="nav navbar-nav">
							<li class="active"><a href="guildMembers.jsp">길드 구성원</a></li>
							<li><a href="nobelesseTable.jsp">길드원 노블표</a></li>
						</ul>
					</div>
				</nav>
				<h3 style="text-align: center;">길드 구성원</h3>
				<div class="row">
					<table class="table table-striped" style="border: 1px solid #dddddd">
						<thead>
							<tr>
								<th style="background-color:#eeeeeee; text-align:center">길드 마스터</th>
							</tr>
						</thead>
						<tbody>
							<%
								String guildMaster = GuildMemberList.getGuildMaster();
							%>
							<tr>
								<td style="text-align:center"><a href="https://maple.gg/u/<%=guildMaster %>"><%=guildMaster %></a></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="row">
					<table class="table table-striped" style="border: 1px solid #dddddd">
						<thead>
							<tr>
								<th colspan="3" style="background-color:#eeeeeee; text-align:center">길드 부마스터</th>
							</tr>
						</thead>
						<tbody>
							<colgroup>
								<col style="width:33.3%">
								<col style="width:33.3%">
								<col style="width:33.3%">
							</colgroup>
							<%
								ArrayList<String> submasterList = GuildMemberList.getGuildSubmasters();
								for (int i = 0; i < submasterList.size(); i+= 3){
									String guildSubmaster1 = "";
									String guildSubmaster2 = "";
									String guildSubmaster3 = "";
									if (i == submasterList.size() - 1){
										guildSubmaster1 = submasterList.get(i);
							%>
							<tr>
								<td style="text-align:center"><a href="https://maple.gg/u/<%=guildSubmaster1 %>"><%=guildSubmaster1 %></a></td>
							</tr>
							<%
									} else if (i == submasterList.size() - 2){
										guildSubmaster1 = submasterList.get(i);
										guildSubmaster2 = submasterList.get(i+1);
							%>
							<tr>
								<td style="text-align:center"><a href="https://maple.gg/u/<%=guildSubmaster1 %>"><%=guildSubmaster1 %></a></td>
								<td style="text-align:center"><a href="https://maple.gg/u/<%=guildSubmaster2 %>"><%=guildSubmaster2 %></a></td>
							</tr>
							<%
									} else {
										guildSubmaster1 = submasterList.get(i);
										guildSubmaster2 = submasterList.get(i+1);
										guildSubmaster3 = submasterList.get(i+2);
							%>
							<tr>
								<td style="text-align:center"><a href="https://maple.gg/u/<%=guildSubmaster1 %>"><%=guildSubmaster1 %></a></td>
								<td style="text-align:center"><a href="https://maple.gg/u/<%=guildSubmaster2 %>"><%=guildSubmaster2 %></a></td>
								<td style="text-align:center"><a href="https://maple.gg/u/<%=guildSubmaster3 %>"><%=guildSubmaster3 %></a></td>
							</tr>
							<%
									}
								}
							%>
						</tbody>
					</table>
				</div>
				<div class="row">
					<table class="table table-striped" style="border: 1px solid #dddddd">
						<thead>
							<tr>
								<th colspan="4" style="background-color:#eeeeeee; text-align:center">길드원</th>
							</tr>
						</thead>
						<tbody>
							<colgroup>
								<col style="width:25%">
								<col style="width:25%">
								<col style="width:25%">
								<col style="width:25%">
							</colgroup>
							<%
								ArrayList<String> memberList = GuildMemberList.getGuildMembers();
								for (int i = 0; i < memberList.size(); i+= 4){
									String guildMember1 = "";
									String guildMember2 = "";
									String guildMember3 = "";
									String guildMember4 = "";
									if (i == memberList.size() - 1){
										guildMember1 = memberList.get(i);
							%>
							<tr>
								<td style="text-align:center"><a href="https://maple.gg/u/<%=guildMember1 %>"><%=guildMember1 %></a></td>
							</tr>
							<%
									} else if (i == memberList.size() - 2){
										guildMember1 = memberList.get(i);
										guildMember2 = memberList.get(i+1);
							%>
							<tr>
								<td style="text-align:center"><a href="https://maple.gg/u/<%=guildMember1 %>"><%=guildMember1 %></a></td>
								<td style="text-align:center"><a href="https://maple.gg/u/<%=guildMember2 %>"><%=guildMember2 %></a></td>
							</tr>
							<%
									} else if (i == memberList.size() - 3){
										guildMember1 = memberList.get(i);
										guildMember2 = memberList.get(i+1);
										guildMember3 = memberList.get(i+2);
							%>
							<tr>
								<td style="text-align:center"><a href="https://maple.gg/u/<%=guildMember1 %>"><%=guildMember1 %></a></td>
								<td style="text-align:center"><a href="https://maple.gg/u/<%=guildMember2 %>"><%=guildMember2 %></a></td>
								<td style="text-align:center"><a href="https://maple.gg/u/<%=guildMember3 %>"><%=guildMember3 %></a></td>
							</tr>
							<%
									} else {
										guildMember1 = memberList.get(i);
										guildMember2 = memberList.get(i+1);
										guildMember3 = memberList.get(i+2);
										guildMember4 = memberList.get(i+3);
							%>
							<tr>
								<td style="text-align:center"><a href="https://maple.gg/u/<%=guildMember1 %>"><%=guildMember1 %></a></td>
								<td style="text-align:center"><a href="https://maple.gg/u/<%=guildMember2 %>"><%=guildMember2 %></a></td>
								<td style="text-align:center"><a href="https://maple.gg/u/<%=guildMember3 %>"><%=guildMember3 %></a></td>
								<td style="text-align:center"><a href="https://maple.gg/u/<%=guildMember4 %>"><%=guildMember4 %></a></td>
							</tr>
							<%
									}
								}
							%>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="col-lg-2"></div>
	</div>
</body>
</html>