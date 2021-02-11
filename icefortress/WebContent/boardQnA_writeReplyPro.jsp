<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "board.BoardQnADBBean" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
	String boardQnA_Title = request.getParameter("boardQnA_Title");
	String boardQnA_Content = request.getParameter("boardQnA_Content");
	String userID = (String)session.getAttribute("userID");
	int boardQnA_ID = Integer.parseInt(request.getParameter("boardQnA_ID"));
	
	BoardQnADBBean manager = BoardQnADBBean.getInstance();
	int result = manager.writeReply(boardQnA_Title, userID, boardQnA_Content, boardQnA_ID);
	
	out.println(result);
%>