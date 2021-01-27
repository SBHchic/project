<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "user.UserDBBean" %>

<% request.setCharacterEncoding("UTF-8");%>
<% 
   // 사용자의 id값은 세션속성값으로부터 얻어냄
   String userID = (String)session.getAttribute("userID");
   String checkUserPassword = request.getParameter("userPassword");

   UserDBBean manager = UserDBBean.getInstance();
   int check = manager.userCheck(userID, checkUserPassword);
   
   out.println(check);
%>