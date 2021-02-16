<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "board.GuildBoard_NoticeAndMeetingLogDBBean" %>
<%@ page import = "board.GuildBoard_NoticeAndMeetingLogDataBean" %>
<%@ page import = "user.UserDBBean" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
	int writtenID = Integer.parseInt(request.getParameter("writtenID"));
	String userID = (String)session.getAttribute("userID");
	
	UserDBBean manager = UserDBBean.getInstance();
	int grade = manager.checkGrade(userID);
	
	GuildBoard_NoticeAndMeetingLogDBBean manager2 = GuildBoard_NoticeAndMeetingLogDBBean.getInstance();
	GuildBoard_NoticeAndMeetingLogDataBean written = manager2.viewWritten(writtenID);
	
	if (userID.equals(written.getUserID()) || grade > 1){
		int result = manager2.delete(writtenID);
		out.println(result);
	} else {
		out.println(0); // 접근 권한이 없음
	}
%>