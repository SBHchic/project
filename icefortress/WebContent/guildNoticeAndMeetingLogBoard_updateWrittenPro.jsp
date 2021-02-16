<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "board.GuildBoard_NoticeAndMeetingLogDBBean" %>
<%@ page import = "board.GuildBoard_NoticeAndMeetingLogDataBean" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
	int writtenID = Integer.parseInt(request.getParameter("writtenID"));
	String title = request.getParameter("title");
	String content = request.getParameter("content");
	String userID = (String)session.getAttribute("userID");
	
	GuildBoard_NoticeAndMeetingLogDBBean manager = GuildBoard_NoticeAndMeetingLogDBBean.getInstance();
	GuildBoard_NoticeAndMeetingLogDataBean update = manager.viewWritten(writtenID);
	
	int result = manager.update(update, title, content);
	
	out.println(result);
%>