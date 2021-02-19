<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "board.BoardQnADBBean" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
	String boardQnA_Title = request.getParameter("boardQnA_Title");
	String boardQnA_Content = request.getParameter("boardQnA_Content");
	String userID = (String)session.getAttribute("userID");
	byte notice = 0;
	if (request.getParameter("notice") != null){
		notice = Byte.parseByte(request.getParameter("notice"));
	}
	
	BoardQnADBBean manager = BoardQnADBBean.getInstance();
	int result = manager.write(boardQnA_Title, userID, boardQnA_Content, notice);
	
	out.println(result);
%>