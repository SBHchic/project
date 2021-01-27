<%@ page language="java" contentType="text/html; charset=UTF-8"
 	pageEncoding="UTF-8"%>
<%@ page import = "user.UserDBBean" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
  
  String userID = (String)session.getAttribute("userID"); // 세션에 남아있는 아이디
  String userPassword  = request.getParameter("userPassword"); // 가져온 비밀번호

  UserDBBean manager = UserDBBean.getInstance();
  int check = manager.updatePassword(userID,userPassword);// 비밀번호 변경 메서드
  
  out.println(check); // 처리결과를 반환
%>