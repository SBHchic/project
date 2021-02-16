<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "board.GuildBoard_NoticeAndMeetingLogDBBean" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
	String title = request.getParameter("title");
	String content = request.getParameter("content");
	String userID = (String)session.getAttribute("userID");
	byte location = Byte.parseByte(request.getParameter("location"));
	
	GuildBoard_NoticeAndMeetingLogDBBean manager = GuildBoard_NoticeAndMeetingLogDBBean.getInstance();
	int result = manager.write(title, userID, content, location);
	
	out.println(result);
%>