<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "board.GuildBoardDBBean" %>
<%@ page import = "board.GuildBoardDataBean" %>
<%@ page import = "user.UserDBBean" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
	int writtenID = Integer.parseInt(request.getParameter("writtenID"));
	int replyID = Integer.parseInt(request.getParameter("replyID"));
	String userID = (String)session.getAttribute("userID");
	
	UserDBBean manager = UserDBBean.getInstance();
	int grade = manager.checkGrade(userID);
	
	GuildBoardDBBean manager2 = GuildBoardDBBean.getInstance();
	GuildBoardDataBean written = manager2.viewWritten(writtenID, replyID);
	
	if (userID.equals(written.getUserID()) || grade > 1){
		int result = manager2.delete(writtenID, replyID);
		out.println(result);
	} else {
		out.println(0); // 접근 권한이 없음
	}
%>