<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="starforce.NormalStarforce"%>
<%@ page import="starforce.NormalStarforceDBBean"%>
<%@ page import="starforce.NormalStarforceDataBean"%>

<% request.setCharacterEncoding("UTF-8"); %>
<!-- 사용자가 입력한 정보들 --> 
<jsp:useBean id="normalStarforce" class="starforce.NormalStarforceDataBean">
    <jsp:setProperty name="normalStarforce" property="*" />
</jsp:useBean>
<%

String result = "";
if (!normalStarforce.isToad()) {
	normalStarforce.setToadToStarforce(0);
	normalStarforce.setToadIgnoreDestroy(false);
}

NormalStarforceDBBean manager = NormalStarforceDBBean.getInstance();
NormalStarforceDataBean existingResult = manager.select(normalStarforce);
if (existingResult == null){
	result = "누적 자료가 없습니다. <br>";
	result += "노말 아이템 강화 창에서 직접 시행해보시는건 어떠신가요?";
} else {
	normalStarforce.setNumberOfItems(existingResult.getNumberOfItems());
	result = NormalStarforceDBBean.condition(normalStarforce);
	result += NormalStarforceDBBean.result(existingResult);
}

out.println(result);
%>