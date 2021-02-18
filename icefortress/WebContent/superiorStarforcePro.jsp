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

SuperiorStarforce[] superiorArr = new SuperiorStarforce[(int)superiorStarforce.getNumberOfItems()];
for (int i = 0; i < superiorArr.length; i++) {
	superiorArr[i] = new SuperiorStarforce(superiorStarforce.getLevel(), superiorStarforce.getFromStarforce(), superiorStarforce.getSuccededCatch());
	superiorArr[i].goTo(superiorStarforce.getStarforce());
}

superiorStarforce.setMinSumUpgradePrice(SuperiorStarforce.sumUpgradePriceMin(superiorArr));
superiorStarforce.setMaxSumUpgradePrice(SuperiorStarforce.sumUpgradePriceMax(superiorArr));
superiorStarforce.setAverageSumUpgradePrice(SuperiorStarforce.sumUpgradePriceAverage(superiorArr));
superiorStarforce.setMinUpgradeCount(SuperiorStarforce.upgradeCountMin(superiorArr));
superiorStarforce.setMaxUpgradeCount(SuperiorStarforce.upgradeCountMax(superiorArr));
superiorStarforce.setAverageUpgradeCount(SuperiorStarforce.upgradeCountAverage(superiorArr));
superiorStarforce.setMinDestroyCount(SuperiorStarforce.destroyCountMin(superiorArr));
superiorStarforce.setMaxDestroyCount(SuperiorStarforce.destroyCountMax(superiorArr));
superiorStarforce.setAverageDestroyCount(SuperiorStarforce.destroyCountAverage(superiorArr));
superiorStarforce.setMinChanceTimeCount(SuperiorStarforce.chanceTimeMin(superiorArr));
superiorStarforce.setMaxChanceTimeCount(SuperiorStarforce.chanceTimeMax(superiorArr));
superiorStarforce.setAverageChanceTimeCount(SuperiorStarforce.chanceTimeAverage(superiorArr));

result = SuperiorStarforceDBBean.condition(superiorStarforce);
result += SuperiorStarforceDBBean.result(superiorStarforce);

SuperiorStarforceDBBean manager = SuperiorStarforceDBBean.getInstance();
if (superiorStarforce.getNumberOfItems() >= 10000){
	int check1 = manager.check(superiorStarforce);
	if (check1 == 1) { // 해당 레코드 존재
		int check2 = manager.update(superiorStarforce);
		if (check2 == -1){
			result = "DB 업데이트 도중 실패";
		}
	} else if (check1 == -1){ // 해당 레코드 존재 x
		manager.insert(superiorStarforce);
	} else {
		result = "데이터베이스 오류";
	}
}
out.println(result);

%>