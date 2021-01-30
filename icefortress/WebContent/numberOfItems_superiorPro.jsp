<%@ page language="java" contentType="text/html; charset=UTF-8"
 	pageEncoding="UTF-8"%>
<%@ page import = "starforce.NumberOfItems_Superior" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
  // 사용자가 입력한 정보들
  int numberOfItems = Integer.parseInt(request.getParameter("numberOfItems"));
  int level  = Integer.parseInt(request.getParameter("level"));
  int numberOfRealItems = Integer.parseInt(request.getParameter("numberOfRealItems"));
  int fromStarforce = Integer.parseInt(request.getParameter("fromStarforce"));
  int starforce = Integer.parseInt(request.getParameter("starforce"));
  byte succededCatch = Byte.parseByte(request.getParameter("succededCatch"));
  
  double subresult = 0;
  String result ="";
  
  NumberOfItems_Superior[] SuperiorArr = new NumberOfItems_Superior[numberOfItems];
  for (int i = 0; i < SuperiorArr.length; i++){
	  SuperiorArr[i] = new NumberOfItems_Superior(level, fromStarforce, succededCatch);
	  SuperiorArr[i].goTo(starforce, numberOfRealItems);
  }
  subresult = NumberOfItems_Superior.succededCount(SuperiorArr, starforce);
  result = NumberOfItems_Superior.result(SuperiorArr, fromStarforce, starforce);
  result += "제한 - 본템 개수 : " + numberOfRealItems + "개 <br>" + "<br>";
  result += "성공 확률 : " + subresult + "%";
  out.println(result);

%>