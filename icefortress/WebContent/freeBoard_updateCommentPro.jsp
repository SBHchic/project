<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "board.FreeBoardDBBean" %>
<%@ page import = "board.FreeBoardDataBean" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
	String content = request.getParameter("content");
	String userID = (String)session.getAttribute("userID");
	int writtenID = Integer.parseInt(request.getParameter("writtenID"));
	int replyID = Integer.parseInt(request.getParameter("replyID"));
	int commentID = Integer.parseInt(request.getParameter("commentID"));
	int commentID_Re = Integer.parseInt(request.getParameter("commentID_Re"));
	
	FreeBoardDBBean manager = FreeBoardDBBean.getInstance();
	FreeBoardDataBean comment = manager.viewComment(writtenID, replyID, commentID, commentID_Re);
	int result = manager.updateComment(comment, content);
	
	out.println(result);
%>