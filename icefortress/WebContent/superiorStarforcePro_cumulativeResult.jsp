<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="starforce.SuperiorStarforce"%>
<%@ page import="starforce.SuperiorStarforceDBBean"%>
<%@ page import="starforce.SuperiorStarforceDataBean"%>

<% request.setCharacterEncoding("UTF-8"); %>
<!-- 사용자가 입력한 정보들 --> 
<jsp:useBean id="superiorStarforce" class="starforce.SuperiorStarforceDataBean">
    <jsp:setProperty name="superiorStarforce" property="*" />
</jsp:useBean>
<%

String result = "";

SuperiorStarforceDBBean manager = SuperiorStarforceDBBean.getInstance();
SuperiorStarforceDataBean existingResult = manager.select(superiorStarforce);
if (existingResult == null){
	result = "누적 자료가 없습니다. <br>";
	result += "슈페리얼 아이템 강화 창에서 직접 시행해보시는건 어떠신가요?";
} else {
	superiorStarforce.setNumberOfItems(existingResult.getNumberOfItems());
	result = SuperiorStarforceDBBean.condition(superiorStarforce);
	result += SuperiorStarforceDBBean.result(existingResult);
}

out.println(result);
%>