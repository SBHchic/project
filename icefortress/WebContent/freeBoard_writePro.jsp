<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "board.FreeBoardDBBean" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
	String title = request.getParameter("title");
	String content = request.getParameter("content");
	String userID = (String)session.getAttribute("userID");
	byte notice = 0;
	if (request.getParameter("notice") != null){
		notice = 1;
	}
	
	FreeBoardDBBean manager = FreeBoardDBBean.getInstance();
	int result = manager.write(title, userID, content, notice);
	
	out.println(result);
%>