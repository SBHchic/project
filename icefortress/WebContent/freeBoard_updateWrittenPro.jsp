<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "board.FreeBoardDBBean" %>
<%@ page import = "board.FreeBoardDataBean" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
	int writtenID = Integer.parseInt(request.getParameter("writtenID"));
	int replyID = Integer.parseInt(request.getParameter("replyID"));
	String title = request.getParameter("title");
	String content = request.getParameter("content");
	String userID = (String)session.getAttribute("userID");
	byte notice = 0;
	if (request.getParameter("notice") != null){
		notice = 1;
	}
	
	FreeBoardDBBean manager = FreeBoardDBBean.getInstance();
	FreeBoardDataBean update = manager.viewWritten(writtenID, replyID);
	
	int result = manager.update(update, title, content, notice);
	
	out.println(result);
%>