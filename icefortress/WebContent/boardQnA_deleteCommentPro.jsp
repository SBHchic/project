<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "board.BoardQnADBBean" %>
<%@ page import = "board.BoardQnADataBean" %>
<%@ page import = "user.UserDBBean" %>

<script src="../js/jquery-3.5.1.min.js"></script>

<% request.setCharacterEncoding("UTF-8");%>

<%
	int boardQnA_ID = Integer.parseInt(request.getParameter("boardQnA_ID"));
	int boardQnA_ReplyID = Integer.parseInt(request.getParameter("boardQnA_ReplyID"));
	int boardQnA_CommentID = Integer.parseInt(request.getParameter("boardQnA_CommentID"));
	int boardQnA_CommentID_Re = Integer.parseInt(request.getParameter("boardQnA_CommentID_Re"));
	String userID = (String)session.getAttribute("userID");
	
	UserDBBean manager = UserDBBean.getInstance();
	int grade = manager.checkGrade(userID);
	
	BoardQnADBBean manager2 = BoardQnADBBean.getInstance();
	BoardQnADataBean comment = manager2.viewComment(boardQnA_ID, boardQnA_ReplyID, boardQnA_CommentID, boardQnA_CommentID_Re);
	
	if (userID.equals(comment.getUserID()) || grade > 1){
		int result = manager2.deleteComment(comment);
		out.println(result);
	} else {
		out.println(0); // 접근 권한이 없음
	}
%>