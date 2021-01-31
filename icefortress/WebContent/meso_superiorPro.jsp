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
  
  double subresult = 0;
  String result ="";
  
  Meso_Superior[] SuperiorArr = new Meso_Superior[numberOfItems];
  for (int i = 0; i < SuperiorArr.length; i++){
	  SuperiorArr[i] = new Meso_Superior(level, fromStarforce, succededCatch);
	  SuperiorArr[i].goTo(starforce, mesoOnHand);
  }
  subresult = Meso_Superior.succededCount(SuperiorArr, mesoOnHand);
  result = Meso_Superior.result(SuperiorArr, fromStarforce, starforce);
  result += "제한 - 보유메소 : " + tmpstr + "메소 <br>" + "<br>";
  result += "성공 확률 : " + subresult + "%";
  out.println(result);

%>