<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "board.FreeBoardDBBean" %>
<%@ page import = "board.FreeBoardDataBean" %>
<%@ page import = "user.UserDBBean" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
	int writtenID = Integer.parseInt(request.getParameter("writtenID"));
	int replyID = Integer.parseInt(request.getParameter("replyID"));
	int commentID = Integer.parseInt(request.getParameter("commentID"));
	int commentID_Re = Integer.parseInt(request.getParameter("commentID_Re"));
	String userID = (String)session.getAttribute("userID");
	
	UserDBBean manager = UserDBBean.getInstance();
	int grade = manager.checkGrade(userID);
	
	FreeBoardDBBean manager2 = FreeBoardDBBean.getInstance();
	FreeBoardDataBean comment = manager2.viewComment(writtenID, replyID, commentID, commentID_Re);
	
	if (userID.equals(comment.getUserID()) || grade > 1){
		int result = manager2.deleteComment(comment);
		out.println(result);
	} else {
		out.println(0); // 접근 권한이 없음
	}
%>