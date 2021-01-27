<%@ page language="java" contentType="text/html; charset=UTF-8"
 	pageEncoding="UTF-8"%>
<%@ page import = "user.UserDBBean" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
  // 사용자가 입력한 아이디와 비밀번호
  String userID = request.getParameter("userID");
  String userPassword  = request.getParameter("userPassword");

  UserDBBean manager = UserDBBean.getInstance();
  int check = manager.userCheck(userID,userPassword);// 사용자 인증처리 메서드

  if(check==1) { // 사용자인증 성공시 세션속성을 설정
	session.setAttribute("userID",userID);
  }
  out.println(check); // 처리결과를 반환
%>