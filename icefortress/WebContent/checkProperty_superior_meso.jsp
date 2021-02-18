<%@ page language="java" contentType="text/html; charset=UTF-8"
 	pageEncoding="UTF-8"%>
<%@ page import = "starforce.Meso_Superior" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
  // 사용자가 입력한 정보들
  int numberOfItems = Integer.parseInt(request.getParameter("numberOfItems"));
  int level  = Integer.parseInt(request.getParameter("level"));
  long mesoOnHand = Long.parseLong(request.getParameter("mesoOnHand"));
  int fromStarforce = Integer.parseInt(request.getParameter("fromStarforce"));
  int starforce = Integer.parseInt(request.getParameter("starforce"));
  
  boolean result = true;
  if (numberOfItems <= 0 || numberOfItems >= 100000000 || level <= 0 || mesoOnHand < 100 || starforce <= 0 || fromStarforce < 0){
	  result = false;
  } else if (fromStarforce >= starforce || Meso_Superior.maxItemStarforce(level) < starforce){
	  result = false;
  }
  out.println(result); // 처리결과를 반환
%>