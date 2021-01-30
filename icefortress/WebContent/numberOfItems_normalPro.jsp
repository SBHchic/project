<%@ page language="java" contentType="text/html; charset=UTF-8"
 	pageEncoding="UTF-8"%>
<%@ page import = "starforce.NumberOfItems_Normal" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
  // ???? ??? ???
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
	  result += "?? - ?? ?? : " + numberOfRealItems + "<br>" + "<br>";
	  result += "?? ?? : " + subresult + "%";
	  out.println(result);
  } else {
	  NumberOfItems_Normal[] normalArr = new NumberOfItems_Normal[numberOfItems];
	  for (int i = 0; i < normalArr.length; i++){
	     	normalArr[i] = new NumberOfItems_Normal(level, fromStarforce, succededCatch, mapleEvent, ignoreDestroy, toadToStarforce);
	  		NumberOfItems_Normal.goTo(normalArr[i], starforce, toadIgnoreDestroy, numberOfRealItems, numberOfToadItems);
	  }
	  subresult = NumberOfItems_Normal.succededCount(normalArr, starforce);
	  result = NumberOfItems_Normal.result(normalArr, fromStarforce, starforce);
	  result += "?? - ?? ?? : " + numberOfRealItems + ", ??? ?? : " + numberOfToadItems + "<br>" + "<br>";
	  result += "?? ?? : " + subresult + "%";
	  out.println(result);
  }
%>