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

// 메서드로 인해 출력될 결과값들
long minSumUpgradePrice;
long maxSumUpgradePrice;
long averageSumUpgradePrice;
int minUpgradeCount;
int maxUpgradeCount;
long averageUpgradeCount;
int minDestroyCount;
int maxDestroyCount;
long averageDestroyCount;
int minChanceTimeCount;
int maxChanceTimeCount;
long averageChanceTimeCount;

String result = "";

if (normalStarforce.isToad() == false) {
	NormalStarforce[] normalArr = new NormalStarforce[(int)normalStarforce.getNumberOfItems()];
	for (int i = 0; i < normalArr.length; i++) {
		normalArr[i] = new NormalStarforce(normalStarforce.getLevel(), normalStarforce.getFromStarforce(), normalStarforce.getSuccededCatch(), normalStarforce.getMapleEvent(), normalStarforce.isDiscountPCRoom(),
				normalStarforce.getDiscountMVPGrade(), normalStarforce.isIgnoreDestroy());
		NormalStarforce.goTo(normalArr[i], normalStarforce.getStarforce(), normalStarforce.isToadIgnoreDestroy());
	}
	minSumUpgradePrice = NormalStarforce.sumUpgradePriceMin(normalArr);
	maxSumUpgradePrice = NormalStarforce.sumUpgradePriceMax(normalArr);
	averageSumUpgradePrice = NormalStarforce.sumUpgradePriceAverage(normalArr);
	minUpgradeCount = NormalStarforce.upgradeCountMin(normalArr);
	maxUpgradeCount = NormalStarforce.upgradeCountMax(normalArr);
	averageUpgradeCount = NormalStarforce.upgradeCountAverage(normalArr);
	minDestroyCount = NormalStarforce.destroyCountMin(normalArr);
	maxDestroyCount = NormalStarforce.destroyCountMax(normalArr);
	averageDestroyCount = NormalStarforce.destroyCountAverage(normalArr);
	minChanceTimeCount = NormalStarforce.chanceTimeMin(normalArr);
	maxChanceTimeCount = NormalStarforce.chanceTimeMax(normalArr);
	averageChanceTimeCount = NormalStarforce.chanceTimeAverage(normalArr);

	result = NormalStarforce.result(normalArr, normalStarforce.getFromStarforce(), normalStarforce.getStarforce());
	result += normalArr.length + "개의 아이템 중 스타포스 강화 최소비용은 " + minSumUpgradePrice + "00메소 입니다. <br>";
	result += normalArr.length + "개의 아이템 중 스타포스 강화 최대비용은 " + maxSumUpgradePrice + "00메소 입니다. <br>";
	result += normalArr.length + "개의 아이템의 평균적인 스타포스 강화 비용은 " + averageSumUpgradePrice + "00메소 입니다. <br>";
	result += normalArr.length + "개의 아이템 중 가장 적은 강화 횟수는 " + minUpgradeCount + "번 입니다. <br>";
	result += normalArr.length + "개의 아이템 중 가장 많은 강화 횟수는 " + maxUpgradeCount + "번 입니다. <br>";
	result += normalArr.length + "개의 아이템의 평균적인 강화 횟수는 " + averageUpgradeCount + "번 입니다. <br>";
	result += normalArr.length + "개의 아이템 중 가장 적은 파괴 횟수는 " + minDestroyCount + "번 입니다. <br>";
	result += normalArr.length + "개의 아이템 중 가장 많은 파괴 횟수는 " + maxDestroyCount + "번 입니다. <br>";
	result += normalArr.length + "개의 아이템의 평균적인 파괴 횟수는 " + averageDestroyCount + "번 입니다. <br>";
	result += normalArr.length + "개의 아이템 중 가장 적은 찬스타임 횟수는 " + minChanceTimeCount + "번 입니다. <br>";
	result += normalArr.length + "개의 아이템 중 가장 많은 찬스타임 횟수는 " + maxChanceTimeCount + "번 입니다. <br>";
	result += normalArr.length + "개의 아이템의 평균적인 찬스타임 횟수는 " + averageChanceTimeCount + "번 입니다.";
	
} else {

	NormalStarforce[] normalArr = new NormalStarforce[(int)normalStarforce.getNumberOfItems()];
	for (int i = 0; i < normalArr.length; i++) {
		normalArr[i] = new NormalStarforce(normalStarforce.getLevel(), normalStarforce.getFromStarforce(), normalStarforce.getSuccededCatch(), normalStarforce.getMapleEvent(), normalStarforce.isDiscountPCRoom(),
				normalStarforce.getDiscountMVPGrade(), normalStarforce.isIgnoreDestroy(), normalStarforce.getToadToStarforce());
		NormalStarforce.goTo(normalArr[i], normalStarforce.getStarforce(), normalStarforce.isToadIgnoreDestroy());
	}
	minSumUpgradePrice = NormalStarforce.sumUpgradePriceMin(normalArr);
	maxSumUpgradePrice = NormalStarforce.sumUpgradePriceMax(normalArr);
	averageSumUpgradePrice = NormalStarforce.sumUpgradePriceAverage(normalArr);
	minUpgradeCount = NormalStarforce.upgradeCountMin(normalArr);
	maxUpgradeCount = NormalStarforce.upgradeCountMax(normalArr);
	averageUpgradeCount = NormalStarforce.upgradeCountAverage(normalArr);
	minDestroyCount = NormalStarforce.destroyCountMin(normalArr);
	maxDestroyCount = NormalStarforce.destroyCountMax(normalArr);
	averageDestroyCount = NormalStarforce.destroyCountAverage(normalArr);
	minChanceTimeCount = NormalStarforce.chanceTimeMin(normalArr);
	maxChanceTimeCount = NormalStarforce.chanceTimeMax(normalArr);
	averageChanceTimeCount = NormalStarforce.chanceTimeAverage(normalArr);

	result = NormalStarforce.result(normalArr, normalStarforce.getFromStarforce(), normalStarforce.getStarforce());
	result += normalArr.length + "개의 아이템 중 스타포스 강화 최소비용은 " + minSumUpgradePrice + "00메소 입니다. <br>";
	result += normalArr.length + "개의 아이템 중 스타포스 강화 최대비용은 " + maxSumUpgradePrice + "00메소 입니다. <br>";
	result += normalArr.length + "개의 아이템의 평균적인 스타포스 강화 비용은 " + averageSumUpgradePrice + "00메소 입니다. <br>";
	result += normalArr.length + "개의 아이템 중 가장 적은 강화 횟수는 " + minUpgradeCount + "번 입니다. <br>";
	result += normalArr.length + "개의 아이템 중 가장 많은 강화 횟수는 " + maxUpgradeCount + "번 입니다. <br>";
	result += normalArr.length + "개의 아이템의 평균적인 강화 횟수는 " + averageUpgradeCount + "번 입니다. <br>";
	result += normalArr.length + "개의 아이템 중 가장 적은 파괴 횟수는 " + minDestroyCount + "번 입니다. <br>";
	result += normalArr.length + "개의 아이템 중 가장 많은 파괴 횟수는 " + maxDestroyCount + "번 입니다. <br>";
	result += normalArr.length + "개의 아이템의 평균적인 파괴 횟수는 " + averageDestroyCount + "번 입니다. <br>";
	result += normalArr.length + "개의 아이템 중 가장 적은 찬스타임 횟수는 " + minChanceTimeCount + "번 입니다. <br>";
	result += normalArr.length + "개의 아이템 중 가장 많은 찬스타임 횟수는 " + maxChanceTimeCount + "번 입니다. <br>";
	result += normalArr.length + "개의 아이템의 평균적인 찬스타임 횟수는 " + averageChanceTimeCount + "번 입니다.";
}

normalStarforce.setMinSumUpgradePrice(minSumUpgradePrice);
normalStarforce.setMaxSumUpgradePrice(maxSumUpgradePrice);
normalStarforce.setAverageSumUpgradePrice(averageSumUpgradePrice);
normalStarforce.setMinUpgradeCount(minUpgradeCount);
normalStarforce.setMaxUpgradeCount(maxUpgradeCount);
normalStarforce.setAverageUpgradeCount(averageUpgradeCount);
normalStarforce.setMinDestroyCount(minDestroyCount);
normalStarforce.setMaxDestroyCount(maxDestroyCount);
normalStarforce.setAverageDestroyCount(averageDestroyCount);
normalStarforce.setMinChanceTimeCount(minChanceTimeCount);
normalStarforce.setMaxChanceTimeCount(maxChanceTimeCount);
normalStarforce.setAverageChanceTimeCount(averageChanceTimeCount);
	
NormalStarforceDBBean manager = NormalStarforceDBBean.getInstance();
if (normalStarforce.getNumberOfItems() >= 10000){
	int check1 = manager.check(normalStarforce);
	if (check1 == 1) { // 해당 레코드 존재
		int check2 = manager.update(normalStarforce);
		if (check2 == -1){
			result = "DB 업데이트 도중 실패";
		}
	} else if (check1 == -1){ // 해당 레코드 존재 x
		manager.insert(normalStarforce);
	} else {
		result = "데이터베이스 오류";
	}
}
out.println(result);

%>