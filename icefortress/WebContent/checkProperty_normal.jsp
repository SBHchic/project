<%@ page language="java" contentType="text/html; charset=UTF-8"
 	pageEncoding="UTF-8"%>
<%@ page import = "starforce.NormalStarforce" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
  // 사용자가 입력한 정보들
  long numberOfItems = Long.parseLong(request.getParameter("numberOfItems"));
  int level  = Integer.parseInt(request.getParameter("level"));
  int fromStarforce = Integer.parseInt(request.getParameter("fromStarforce"));
  int starforce = Integer.parseInt(request.getParameter("starforce"));
  boolean toad = Boolean.parseBoolean(request.getParameter("toad"));
  int toadToStarforce = 0;
  if (toad){
	  toadToStarforce = Integer.parseInt(request.getParameter("toadToStarforce"));
  }
  
  boolean result = true;
  if (numberOfItems <= 0 || numberOfItems >= 100000000 || level < 20 || starforce <= 0 || fromStarforce < 0){
	  result = false;
  } else if (fromStarforce >= starforce || NormalStarforce.maxItemStarforce(level) < starforce){
	  result = false;
  } else if (toad) {
	  if (NormalStarforce.maxItemStarforce(NormalStarforce.toadItemLevel(level)) < toadToStarforce || toadToStarforce > (starforce + 1) || toadToStarforce <= 0) {
		  result = false;
	  }
  }

  out.println(result); // 처리결과를 반환
%>