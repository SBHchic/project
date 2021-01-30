<%@ page language="java" contentType="text/html; charset=UTF-8"
 	pageEncoding="UTF-8"%>
<%@ page import = "starforce.NumberOfItems_Normal" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
  // 사용자가 입력한 정보들
  int numberOfItems = Integer.parseInt(request.getParameter("numberOfItems"));
  int level  = Integer.parseInt(request.getParameter("level"));
  int numberOfRealItems = Integer.parseInt(request.getParameter("numberOfRealItems"));
  int fromStarforce = Integer.parseInt(request.getParameter("fromStarforce"));
  int starforce = Integer.parseInt(request.getParameter("starforce"));
  boolean ignoreDestroy = Boolean.parseBoolean(request.getParameter("ignoreDestroy"));
  byte succededCatch = Byte.parseByte(request.getParameter("succededCatch"));
  byte mapleEvent = Byte.parseByte(request.getParameter("mapleEvent"));
  boolean toad = Boolean.parseBoolean(request.getParameter("toad"));
  int numberOfToadItems = 0;
  int toadToStarforce = 0;
  boolean toadIgnoreDestroy = false;
  if (toad){
	  numberOfToadItems = Integer.parseInt(request.getParameter("numberOfToadItems"));
	  toadToStarforce = Integer.parseInt(request.getParameter("toadToStarforce"));
	  toadIgnoreDestroy = Boolean.parseBoolean(request.getParameter("toadIgnoreDestroy"));  
  }
  
  boolean result = true;
  if (numberOfItems <= 0 || level <= 0 || numberOfRealItems <= 0 || starforce <= 0 || fromStarforce < 0){
	  result = false;
  } else if (fromStarforce >= starforce || NumberOfItems_Normal.maxItemStarforce(level) < starforce){
	  result = false;
  } else if (toad == true) {
	  if (NumberOfItems_Normal.maxItemStarforce(NumberOfItems_Normal.toadItemLevel(level)) < toadToStarforce || toadToStarforce > (starforce + 1) || numberOfToadItems <= 0 || toadToStarforce <= 0) {
		  result = false;
	  }
  }

  out.println(result); // 처리결과를 반환
%>