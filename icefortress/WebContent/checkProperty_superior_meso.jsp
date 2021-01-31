<%@ page language="java" contentType="text/html; charset=UTF-8"
 	pageEncoding="UTF-8"%>
<%@ page import = "starforce.Meso_Superior" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
  // 사용자가 입력한 정보들
  int numberOfItems = Integer.parseInt(request.getParameter("numberOfItems"));
  int level  = Integer.parseInt(request.getParameter("level"));
  String tmpstr = request.getParameter("mesoOnHand");
  long mesoOnHand = Long.parseLong(tmpstr.substring(0,tmpstr.length()-2));
  int fromStarforce = Integer.parseInt(request.getParameter("fromStarforce"));
  int starforce = Integer.parseInt(request.getParameter("starforce"));
  byte succededCatch = Byte.parseByte(request.getParameter("succededCatch"));
  
  boolean result = true;
  if (numberOfItems <= 0 || level <= 0 || mesoOnHand < 100 || starforce <= 0 || fromStarforce < 0){
	  result = false;
  } else if (fromStarforce >= starforce || Meso_Superior.maxItemStarforce(level) < starforce){
	  result = false;
  }
  out.println(result); // 처리결과를 반환
%>