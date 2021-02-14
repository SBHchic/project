<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "board.FreeBoardDBBean" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
	String title = request.getParameter("title");
	String content = request.getParameter("content");
	String userID = (String)session.getAttribute("userID");
	int writtenID = Integer.parseInt(request.getParameter("writtenID"));
	
	FreeBoardDBBean manager = FreeBoardDBBean.getInstance();
	int result = manager.writeReply(title, userID, content, writtenID);
	
	out.println(result);
%>