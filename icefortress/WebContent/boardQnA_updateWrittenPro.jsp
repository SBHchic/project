<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "board.BoardQnADBBean" %>
<%@ page import = "board.BoardQnADataBean" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
	int boardQnA_ID = Integer.parseInt(request.getParameter("boardQnA_ID"));
	int boardQnA_ReplyID = Integer.parseInt(request.getParameter("boardQnA_ReplyID"));
	String boardQnA_Title = request.getParameter("boardQnA_Title");
	String boardQnA_Content = request.getParameter("boardQnA_Content");
	String userID = (String)session.getAttribute("userID");
	
	BoardQnADBBean manager = BoardQnADBBean.getInstance();
	BoardQnADataBean update = manager.viewWritten(boardQnA_ID, boardQnA_ReplyID);
	
	int result = manager.update(update, boardQnA_Title, boardQnA_Content);
	
	out.println(result);
%>