<%@ page language="java" contentType="text/html; charset=UTF-8"
 	pageEncoding="UTF-8"%>
<%@ page import = "user.UserDBBean" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
  
  String userID = (String)session.getAttribute("userID"); // 세션에 남아있는 아이디
  String newUserID  = request.getParameter("newUserID"); // 가져온 아이디

  UserDBBean manager = UserDBBean.getInstance();
  int check = manager.updateID(userID,newUserID);// 아이디 변경 메서드
  
  if (check == 1){
	  int check2 = manager.deleteMember(userID);
	  if (check2 == 1){
		  session.invalidate();
	  }
  }
  out.println(check); // 처리결과를 반환
%>