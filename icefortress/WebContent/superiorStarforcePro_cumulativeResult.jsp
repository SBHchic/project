<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="starforce.SuperiorStarforce"%>
<%@ page import="starforce.SuperiorStarforceDBBean"%>
<%@ page import="starforce.SuperiorStarforceDataBean"%>
<script src="js/jquery-3.5.1.min.js"></script>

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
	result += "슈페리얼아이템 강화창에서 직접 시행해보시는건 어떠신가요?";
} else {
	
	result += "==== " + superiorStarforce.getLevel() + "제 슈페리얼아이템의 " + existingResult.getNumberOfItems() + "개 " + superiorStarforce.getFromStarforce() + "성 -> " + superiorStarforce.getStarforce() + "성까지의 강화 ==== <br>";
	result += "추가 설명 - 스타캐치 " + SuperiorStarforce.starCatchToString(superiorStarforce.getSuccededCatch()) + "<br>";
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