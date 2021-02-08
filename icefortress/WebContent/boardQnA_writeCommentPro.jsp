<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "board.BoardQnADBBean" %>

<script src="../js/jquery-3.5.1.min.js"></script>

<% request.setCharacterEncoding("UTF-8");%>

<%
	String boardQnA_Content = request.getParameter("boardQnA_Content");
	String userID = (String)session.getAttribute("userID");
	int boardQnA_ID = Integer.parseInt(request.getParameter("boardQnA_ID"));
	int boardQnA_ReplyID = Integer.parseInt(request.getParameter("boardQnA_ReplyID"));
	
	BoardQnADBBean manager = BoardQnADBBean.getInstance();
	int result = manager.writeComment(userID, boardQnA_Content, boardQnA_ID, boardQnA_ReplyID);
	
	out.println(result);
%>