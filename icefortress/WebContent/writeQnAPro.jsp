<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "board.BoardQnADBBean" %>

<script src="../js/jquery-3.5.1.min.js"></script>

<% request.setCharacterEncoding("UTF-8");%>

<%
	String boardQnA_Title = request.getParameter("boardQnA_Title");
	String boardQnA_Content = request.getParameter("boardQnA_Content");
	String userID = (String)session.getAttribute("userID");
	
	BoardQnADBBean manager = BoardQnADBBean.getInstance();
	int result = manager.write(boardQnA_Title, userID, boardQnA_Content);
	
	out.println(result);
%>