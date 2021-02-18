<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="starforce.NormalStarforce"%>
<%@ page import="starforce.NormalStarforceDBBean"%>
<%@ page import="starforce.NormalStarforceDataBean"%>

<%
request.setCharacterEncoding("UTF-8");
%>
<!-- 사용자가 입력한 정보들 -->
<jsp:useBean id="normalStarforce" class="starforce.NormalStarforceDataBean">
	<jsp:setProperty name="normalStarforce" property="*" />
</jsp:useBean>
<%
String result = "";

if (normalStarforce.isToad() == false) {
	// form 형식으로 인해 토드쪽에서 잘못된 값이 들어올 수 있음
	normalStarforce.setToadToStarforce(0);
	normalStarforce.setToadIgnoreDestroy(false);

	NormalStarforce[] normalArr = new NormalStarforce[(int) normalStarforce.getNumberOfItems()];
	for (int i = 0; i < normalArr.length; i++) {
		normalArr[i] = new NormalStarforce(normalStarforce.getLevel(), normalStarforce.getFromStarforce(), normalStarforce.getSuccededCatch(),
		normalStarforce.getMapleEvent(), normalStarforce.isDiscountPCRoom(), normalStarforce.getDiscountMVPGrade(),
		normalStarforce.isIgnoreDestroy());
		NormalStarforce.goTo(normalArr[i], normalStarforce.getStarforce(), normalStarforce.isToadIgnoreDestroy());
	}

	normalStarforce.setMinSumUpgradePrice(NormalStarforce.sumUpgradePriceMin(normalArr));
	normalStarforce.setMaxSumUpgradePrice(NormalStarforce.sumUpgradePriceMax(normalArr));
	normalStarforce.setAverageSumUpgradePrice(NormalStarforce.sumUpgradePriceAverage(normalArr));
	normalStarforce.setMinUpgradeCount(NormalStarforce.upgradeCountMin(normalArr));
	normalStarforce.setMaxUpgradeCount(NormalStarforce.upgradeCountMax(normalArr));
	normalStarforce.setAverageUpgradeCount(NormalStarforce.upgradeCountAverage(normalArr));
	normalStarforce.setMinDestroyCount(NormalStarforce.destroyCountMin(normalArr));
	normalStarforce.setMaxDestroyCount(NormalStarforce.destroyCountMax(normalArr));
	normalStarforce.setAverageDestroyCount(NormalStarforce.destroyCountAverage(normalArr));
	normalStarforce.setMinChanceTimeCount(NormalStarforce.chanceTimeMin(normalArr));
	normalStarforce.setMaxChanceTimeCount(NormalStarforce.chanceTimeMax(normalArr));
	normalStarforce.setAverageChanceTimeCount(NormalStarforce.chanceTimeAverage(normalArr));

	result = NormalStarforceDBBean.condition(normalStarforce);
	result += NormalStarforceDBBean.result(normalStarforce);

} else {

	NormalStarforce[] normalArr = new NormalStarforce[(int) normalStarforce.getNumberOfItems()];
	for (int i = 0; i < normalArr.length; i++) {
		normalArr[i] = new NormalStarforce(normalStarforce.getLevel(), normalStarforce.getFromStarforce(), normalStarforce.getSuccededCatch(),
		normalStarforce.getMapleEvent(), normalStarforce.isDiscountPCRoom(), normalStarforce.getDiscountMVPGrade(),
		normalStarforce.isIgnoreDestroy(), normalStarforce.getToadToStarforce());
		NormalStarforce.goTo(normalArr[i], normalStarforce.getStarforce(), normalStarforce.isToadIgnoreDestroy());
	}

	normalStarforce.setMinSumUpgradePrice(NormalStarforce.sumUpgradePriceMin(normalArr));
	normalStarforce.setMaxSumUpgradePrice(NormalStarforce.sumUpgradePriceMax(normalArr));
	normalStarforce.setAverageSumUpgradePrice(NormalStarforce.sumUpgradePriceAverage(normalArr));
	normalStarforce.setMinUpgradeCount(NormalStarforce.upgradeCountMin(normalArr));
	normalStarforce.setMaxUpgradeCount(NormalStarforce.upgradeCountMax(normalArr));
	normalStarforce.setAverageUpgradeCount(NormalStarforce.upgradeCountAverage(normalArr));
	normalStarforce.setMinDestroyCount(NormalStarforce.destroyCountMin(normalArr));
	normalStarforce.setMaxDestroyCount(NormalStarforce.destroyCountMax(normalArr));
	normalStarforce.setAverageDestroyCount(NormalStarforce.destroyCountAverage(normalArr));
	normalStarforce.setMinChanceTimeCount(NormalStarforce.chanceTimeMin(normalArr));
	normalStarforce.setMaxChanceTimeCount(NormalStarforce.chanceTimeMax(normalArr));
	normalStarforce.setAverageChanceTimeCount(NormalStarforce.chanceTimeAverage(normalArr));

	result = NormalStarforceDBBean.condition(normalStarforce);
	result += NormalStarforceDBBean.result(normalStarforce);
}

NormalStarforceDBBean manager = NormalStarforceDBBean.getInstance();
if (normalStarforce.getNumberOfItems() >= 10000) {
	int check1 = manager.check(normalStarforce);
	if (check1 == 1) { // 해당 레코드 존재
		int check2 = manager.update(normalStarforce);
		if (check2 == -1) {
			result = "DB 업데이트 도중 실패";
		}
	} else if (check1 == -1) { // 해당 레코드 존재 x
		manager.insert(normalStarforce);
	} else {
		result = "데이터베이스 오류";
	}
}
out.println(result);
%>