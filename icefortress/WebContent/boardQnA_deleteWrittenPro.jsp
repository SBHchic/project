<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "board.BoardQnADBBean" %>
<%@ page import = "board.BoardQnADataBean" %>
<%@ page import = "user.UserDBBean" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
	int boardQnA_ID = Integer.parseInt(request.getParameter("boardQnA_ID"));
	int boardQnA_ReplyID = Integer.parseInt(request.getParameter("boardQnA_ReplyID"));
	String userID = (String)session.getAttribute("userID");
	
	UserDBBean manager = UserDBBean.getInstance();
	int grade = manager.checkGrade(userID);
	
	BoardQnADBBean manager2 = BoardQnADBBean.getInstance();
	BoardQnADataBean written = manager2.viewWritten(boardQnA_ID, boardQnA_ReplyID);
	
	if (userID.equals(written.getUserID()) || grade > 1){
		int result = manager2.delete(boardQnA_ID, boardQnA_ReplyID);
		out.println(result);
	} else {
		out.println(0); // 접근 권한이 없음
	}
%>