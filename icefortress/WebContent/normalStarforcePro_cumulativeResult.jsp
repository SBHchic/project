<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="starforce.NormalStarforce"%>
<%@ page import="starforce.NormalStarforceDBBean"%>
<%@ page import="starforce.NormalStarforceDataBean"%>
<script src="js/jquery-3.5.1.min.js"></script>

<% request.setCharacterEncoding("UTF-8"); %>
<!-- 사용자가 입력한 정보들 --> 
<jsp:useBean id="normalStarforce" class="starforce.NormalStarforceDataBean">
    <jsp:setProperty name="normalStarforce" property="*" />
</jsp:useBean>
<%

String result = "";

NormalStarforceDBBean manager = NormalStarforceDBBean.getInstance();
NormalStarforceDataBean existingResult = manager.select(normalStarforce);
if (existingResult == null){
	result = "누적 자료가 없습니다. <br>";
	result += "노말아이템 강화창에서 직접 시행해보시는건 어떠신가요?";
} else {
	
	result += "==== " + normalStarforce.getLevel() + "제 노말아이템의 " + existingResult.getNumberOfItems() + "개 " + normalStarforce.getFromStarforce() + "성 -> " + normalStarforce.getStarforce() + "성까지의 강화 ==== <br>";
	result += "추가 설명 - 스타캐치 " + NormalStarforce.starCatchToString(normalStarforce.getSuccededCatch()) + ", 파괴방지 : " + NormalStarforce.booleanToString(normalStarforce.isIgnoreDestroy()) + "<br>";
	result += ", PC방 할인 : " + NormalStarforce.booleanToString(normalStarforce.isDiscountPCRoom()) + ", MVP 등급 : " + NormalStarforce.MVPGradeToString(normalStarforce.getDiscountMVPGrade()) + "<br>";
	result += "토드여부 : " + NormalStarforce.booleanToString(normalStarforce.isToad()) + "<br>";
	if (normalStarforce.isToad()) {
		result += " 토드템 0성 -> " + normalStarforce.getToadToStarforce() + "성, 토드템 파괴방지 : " + NormalStarforce.booleanToString(normalStarforce.isToadIgnoreDestroy()) + "<br>";
	}
	result += "메이플 이벤트 적용 : " + NormalStarforce.mapleEventToString(normalStarforce.getMapleEvent()) + "<br>";
	result += existingResult.getNumberOfItems() + "개의 아이템 중 스타포스 강화 최소비용은 " + existingResult.getMinSumUpgradePrice() + "00메소 입니다. <br>";
	result += existingResult.getNumberOfItems() + "개의 아이템 중 스타포스 강화 최대비용은 " + existingResult.getMaxSumUpgradePrice() + "00메소 입니다. <br>";
	result += existingResult.getNumberOfItems() + "개의 아이템의 평균적인 스타포스 강화 비용은 " + existingResult.getAverageSumUpgradePrice() + "00메소 입니다. <br>";
	result += existingResult.getNumberOfItems() + "개의 아이템 중 가장 적은 강화 횟수는 " + existingResult.getMinUpgradeCount() + "번 입니다. <br>";
	result += existingResult.getNumberOfItems() + "개의 아이템 중 가장 많은 강화 횟수는 " + existingResult.getMaxUpgradeCount() + "번 입니다. <br>";
	result += existingResult.getNumberOfItems() + "개의 아이템의 평균적인 강화 횟수는 " + existingResult.getAverageUpgradeCount() + "번 입니다. <br>";
	result += existingResult.getNumberOfItems() + "개의 아이템 중 가장 적은 파괴 횟수는 " + existingResult.getMinDestroyCount() + "번 입니다. <br>";
	result += existingResult.getNumberOfItems() + "개의 아이템 중 가장 많은 파괴 횟수는 " + existingResult.getMaxDestroyCount() + "번 입니다. <br>";
	result += existingResult.getNumberOfItems() + "개의 아이템의 평균적인 파괴 횟수는 " + existingResult.getAverageDestroyCount() + "번 입니다. <br>";
	result += existingResult.getNumberOfItems() + "개의 아이템 중 가장 적은 찬스타임 횟수는 " + existingResult.getMinChanceTimeCount() + "번 입니다. <br>";
	result += existingResult.getNumberOfItems() + "개의 아이템 중 가장 많은 찬스타임 횟수는 " + existingResult.getMaxChanceTimeCount() + "번 입니다. <br>";
	result += existingResult.getNumberOfItems() + "개의 아이템의 평균적인 찬스타임 횟수는 " + existingResult.getAverageChanceTimeCount() + "번 입니다.";
}
out.println(result);
%>