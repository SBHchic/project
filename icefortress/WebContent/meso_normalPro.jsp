<%@ page language="java" contentType="text/html; charset=UTF-8"
 	pageEncoding="UTF-8"%>
<%@ page import = "starforce.Meso_Normal" %>

<% request.setCharacterEncoding("UTF-8");%>

<%
  // 사용자가 입력한 정보들
  int numberOfItems = Integer.parseInt(request.getParameter("numberOfItems"));
  int level  = Integer.parseInt(request.getParameter("level"));
  String tmpstr = request.getParameter("mesoOnHand");
  long mesoOnHand = Long.parseLong(tmpstr.substring(0,tmpstr.length()-2));
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
  
  double subresult = 0;
  String result ="";
  
  if (toad == false) {
	  Meso_Normal[] normalArr = new Meso_Normal[numberOfItems];
	  for (int i = 0; i < normalArr.length; i++){
	     	normalArr[i] = new Meso_Normal(level, fromStarforce, succededCatch, mapleEvent, discountPCRoom, discountMVPGrade, ignoreDestroy);
	  		Meso_Normal.goTo(normalArr[i], starforce, toadIgnoreDestroy, mesoOnHand);
	  }
	  subresult = Meso_Normal.succededCount(normalArr, mesoOnHand);
	  result = Meso_Normal.result(normalArr, fromStarforce, starforce);
	  result += "제한 - 보유메소 : " + tmpstr + "메소 <br>" + "<br>";
	  result += "성공 확률 : " + subresult + "%";
	  out.println(result);
  } else if (toad == true){
	  toadToStarforce = Integer.parseInt(request.getParameter("toadToStarforce"));
	  toadIgnoreDestroy = Boolean.parseBoolean(request.getParameter("toadIgnoreDestroy"));
	  
	  Meso_Normal[] normalArr = new Meso_Normal[numberOfItems];
	  for (int i = 0; i < normalArr.length; i++){
	     	normalArr[i] = new Meso_Normal(level, fromStarforce, succededCatch, mapleEvent, discountPCRoom, discountMVPGrade, ignoreDestroy, toadToStarforce);
	  		Meso_Normal.goTo(normalArr[i], starforce, toadIgnoreDestroy, mesoOnHand);
	  }
	  subresult = Meso_Normal.succededCount(normalArr, mesoOnHand);
	  result = Meso_Normal.result(normalArr, fromStarforce, starforce);
	  result += "제한 - 보유메소 : " + tmpstr + "메소 <br>" + "<br>";
	  result += "성공 확률 : " + subresult + "%";
	  out.println(result);
  }
%>