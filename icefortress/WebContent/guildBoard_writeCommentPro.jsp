<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "board.GuildBoardDBBean" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
	String content = request.getParameter("content");
	String userID = (String)session.getAttribute("userID");
	int writtenID = Integer.parseInt(request.getParameter("writtenID"));
	int replyID = Integer.parseInt(request.getParameter("replyID"));
	
	GuildBoardDBBean manager = GuildBoardDBBean.getInstance();
	int result = manager.writeComment(userID, content, writtenID, replyID);
	
	out.println(result);
%>