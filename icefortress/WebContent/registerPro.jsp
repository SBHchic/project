<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "user.UserDBBean" %>
<%@ page import = "java.sql.Timestamp" %>

<% request.setCharacterEncoding("UTF-8");%>
<jsp:useBean id="member" class="user.UserDataBean">
    <jsp:setProperty name="member" property="*" />
</jsp:useBean>

<%  
  // 폼으로부터 넘어오지 않는 데이터인 가입날짜를 직접 데이터저장빈에 세팅
  member.setReg_date(new Timestamp(System.currentTimeMillis()));

  UserDBBean manager = UserDBBean.getInstance();
  // 사용자가 입력한 데이터저장빈 객체를 가지고 회원가입 처리 메서드호출
  int check = manager.insertMember(member);
  if (check == 1){
	  session.setAttribute("userID", member.getUserID());
  }
  out.println(check);
%>