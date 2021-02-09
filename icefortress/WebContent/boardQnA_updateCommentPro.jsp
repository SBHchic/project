<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "board.BoardQnADBBean" %>
<%@ page import = "board.BoardQnADataBean" %>

<script src="../js/jquery-3.5.1.min.js"></script>

<% request.setCharacterEncoding("UTF-8");%>

<%
	String boardQnA_Content = request.getParameter("boardQnA_Content");
	String userID = (String)session.getAttribute("userID");
	int boardQnA_ID = Integer.parseInt(request.getParameter("boardQnA_ID"));
	int boardQnA_ReplyID = Integer.parseInt(request.getParameter("boardQnA_ReplyID"));
	int boardQnA_CommentID = Integer.parseInt(request.getParameter("boardQnA_CommentID"));
	int boardQnA_CommentID_Re = Integer.parseInt(request.getParameter("boardQnA_CommentID_Re"));
	
	BoardQnADBBean manager = BoardQnADBBean.getInstance();
	BoardQnADataBean comment = manager.viewComment(boardQnA_ID, boardQnA_ReplyID, boardQnA_CommentID, boardQnA_CommentID_Re);
	int result = manager.updateComment(comment, boardQnA_Content);
	
	out.println(result);
%>