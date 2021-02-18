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
  
  double subresult = 0;
  String result ="";
  
  if (!toad) {
	  NumberOfItems_Normal[] normalArr = new NumberOfItems_Normal[numberOfItems];
	  for (int i = 0; i < normalArr.length; i++){
	     	normalArr[i] = new NumberOfItems_Normal(level, fromStarforce, succededCatch, mapleEvent, ignoreDestroy);
	  		NumberOfItems_Normal.goTo(normalArr[i], starforce, toadIgnoreDestroy, numberOfRealItems, numberOfToadItems);
	  }
	  subresult = NumberOfItems_Normal.succededCount(normalArr, starforce);
	  result = NumberOfItems_Normal.result(normalArr, fromStarforce, starforce);
	  result += "제한 - 본템 개수 : " + numberOfRealItems + "개 <br>" + "<br>";
	  result += "성공 확률 : " + subresult + "%";
  } else {
	  numberOfToadItems = Integer.parseInt(request.getParameter("numberOfToadItems"));
	  toadToStarforce = Integer.parseInt(request.getParameter("toadToStarforce"));
	  toadIgnoreDestroy = Boolean.parseBoolean(request.getParameter("toadIgnoreDestroy"));
	  
	  NumberOfItems_Normal[] normalArr = new NumberOfItems_Normal[numberOfItems];
	  for (int i = 0; i < normalArr.length; i++){
	     	normalArr[i] = new NumberOfItems_Normal(level, fromStarforce, succededCatch, mapleEvent, ignoreDestroy, toadToStarforce);
	  		NumberOfItems_Normal.goTo(normalArr[i], starforce, toadIgnoreDestroy, numberOfRealItems, numberOfToadItems);
	  }
	  subresult = NumberOfItems_Normal.succededCount(normalArr, starforce);
	  result = NumberOfItems_Normal.result(normalArr, fromStarforce, starforce);
	  result += "제한 - 본템 개수 : " + numberOfRealItems + "개, 토드템 개수 : " + numberOfToadItems + "개 <br>" + "<br>";
	  result += "성공 확률 : " + subresult + "%";
  }
  
  out.println(result);
%>