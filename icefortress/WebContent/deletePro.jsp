<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "user.UserDBBean" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
  String userID = (String)session.getAttribute("userID");
  String userPassword  = request.getParameter("userPassword");
	
  UserDBBean manager = UserDBBean.getInstance();
  // 회원탈퇴처리 메서드 수행 후 탈퇴 상황 값 반환
  int check = manager.deleteMember(userID,userPassword);
	
  if(check == 1) // 탈퇴 성공시
	 session.invalidate(); //세션을 무효화
  
  out.println(check);
%>