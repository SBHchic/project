<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "board.FreeBoardDBBean" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
	String content = request.getParameter("content");
	String userID = (String)session.getAttribute("userID");
	int writtenID = Integer.parseInt(request.getParameter("writtenID"));
	int replyID = Integer.parseInt(request.getParameter("replyID"));
	int commentID = Integer.parseInt(request.getParameter("commentID"));
	
	FreeBoardDBBean manager = FreeBoardDBBean.getInstance();
	int result = manager.writeCommentReply(userID, content, writtenID, replyID, commentID);
	
	out.println(result);
%>