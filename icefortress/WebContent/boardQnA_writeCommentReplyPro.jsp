<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "board.BoardQnADBBean" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
	String boardQnA_Content = request.getParameter("boardQnA_Content");
	String userID = (String)session.getAttribute("userID");
	int boardQnA_ID = Integer.parseInt(request.getParameter("boardQnA_ID"));
	int boardQnA_ReplyID = Integer.parseInt(request.getParameter("boardQnA_ReplyID"));
	int boardQnA_CommentID = Integer.parseInt(request.getParameter("boardQnA_CommentID"));
	
	BoardQnADBBean manager = BoardQnADBBean.getInstance();
	int result = manager.writeCommentReply(userID, boardQnA_Content, boardQnA_ID, boardQnA_ReplyID, boardQnA_CommentID);
	
	out.println(result);
%>