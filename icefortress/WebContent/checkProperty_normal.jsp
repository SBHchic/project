<%@ page language="java" contentType="text/html; charset=UTF-8"
 	pageEncoding="UTF-8"%>
<%@ page import = "starforce.NormalStarforce" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
  // 사용자가 입력한 정보들
  int numberOfItems = Integer.parseInt(request.getParameter("numberOfItems"));
  int level  = Integer.parseInt(request.getParameter("level"));
  int fromStarforce = Integer.parseInt(request.getParameter("fromStarforce"));
  int starforce = Integer.parseInt(request.getParameter("starforce"));
  boolean ignoreDestroy = Boolean.parseBoolean(request.getParameter("ignoreDestroy"));
  boolean discountPCRoom = Boolean.parseBoolean(request.getParameter("discountPCRoom"));
  byte succededCatch = Byte.parseByte(request.getParameter("succededCatch"));
  byte mapleEvent = Byte.parseByte(request.getParameter("mapleEvent"));
  byte discountMVPGrade = Byte.parseByte(request.getParameter("discountMVPGrade"));
  boolean toad = Boolean.parseBoolean(request.getParameter("toad"));
  int toadToStarforce = 0;
  boolean toadIgnoreDestroy = false;
  if (toad){
	  toadToStarforce = Integer.parseInt(request.getParameter("toadToStarforce"));
	  toadIgnoreDestroy = Boolean.parseBoolean(request.getParameter("toadIgnoreDestroy"));  
  }
  
  boolean result = true;
  if (numberOfItems <= 0 || level <= 0 || starforce <= 0 || fromStarforce < 0){
	  result = false;
  } else if (fromStarforce >= starforce || NormalStarforce.maxItemStarforce(level) < starforce){
	  result = false;
  } else if (toad == true) {
	  if (NormalStarforce.maxItemStarforce(NormalStarforce.toadItemLevel(level)) < toadToStarforce || toadToStarforce > (starforce + 1) || toadToStarforce <= 0) {
		  result = false;
	  }
  }

  out.println(result); // 처리결과를 반환
%>