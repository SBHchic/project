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

SuperiorStarforce[] superiorArr = new SuperiorStarforce[(int)superiorStarforce.getNumberOfItems()];
for (int i = 0; i < superiorArr.length; i++) {
	superiorArr[i] = new SuperiorStarforce(superiorStarforce.getLevel(), superiorStarforce.getFromStarforce(), superiorStarforce.getSuccededCatch());
	superiorArr[i].goTo(superiorStarforce.getStarforce());
}
minSumUpgradePrice = SuperiorStarforce.sumUpgradePriceMin(superiorArr);
maxSumUpgradePrice = SuperiorStarforce.sumUpgradePriceMax(superiorArr);
averageSumUpgradePrice = SuperiorStarforce.sumUpgradePriceAverage(superiorArr);
minUpgradeCount = SuperiorStarforce.upgradeCountMin(superiorArr);
maxUpgradeCount = SuperiorStarforce.upgradeCountMax(superiorArr);
averageUpgradeCount = SuperiorStarforce.upgradeCountAverage(superiorArr);
minDestroyCount = SuperiorStarforce.destroyCountMin(superiorArr);
maxDestroyCount = SuperiorStarforce.destroyCountMax(superiorArr);
averageDestroyCount = SuperiorStarforce.destroyCountAverage(superiorArr);
minChanceTimeCount = SuperiorStarforce.chanceTimeMin(superiorArr);
maxChanceTimeCount = SuperiorStarforce.chanceTimeMax(superiorArr);
averageChanceTimeCount = SuperiorStarforce.chanceTimeAverage(superiorArr);

result = SuperiorStarforce.result(superiorArr, superiorStarforce.getFromStarforce(), superiorStarforce.getStarforce());
result += superiorArr.length + "개의 아이템 중 스타포스 강화 최소비용은 " + minSumUpgradePrice + "00메소 입니다. <br>";
result += superiorArr.length + "개의 아이템 중 스타포스 강화 최대비용은 " + maxSumUpgradePrice + "00메소 입니다. <br>";
result += superiorArr.length + "개의 아이템의 평균적인 스타포스 강화 비용은 " + averageSumUpgradePrice + "00메소 입니다. <br>";
result += superiorArr.length + "개의 아이템 중 가장 적은 강화 횟수는 " + minUpgradeCount + "번 입니다. <br>";
result += superiorArr.length + "개의 아이템 중 가장 많은 강화 횟수는 " + maxUpgradeCount + "번 입니다. <br>";
result += superiorArr.length + "개의 아이템의 평균적인 강화 횟수는 " + averageUpgradeCount + "번 입니다. <br>";
result += superiorArr.length + "개의 아이템 중 가장 적은 파괴 횟수는 " + minDestroyCount + "번 입니다. <br>";
result += superiorArr.length + "개의 아이템 중 가장 많은 파괴 횟수는 " + maxDestroyCount + "번 입니다. <br>";
result += superiorArr.length + "개의 아이템의 평균적인 파괴 횟수는 " + averageDestroyCount + "번 입니다. <br>";
result += superiorArr.length + "개의 아이템 중 가장 적은 찬스타임 횟수는 " + minChanceTimeCount + "번 입니다. <br>";
result += superiorArr.length + "개의 아이템 중 가장 많은 찬스타임 횟수는 " + maxChanceTimeCount + "번 입니다. <br>";
result += superiorArr.length + "개의 아이템의 평균적인 찬스타임 횟수는 " + averageChanceTimeCount + "번 입니다.";

superiorStarforce.setMinSumUpgradePrice(minSumUpgradePrice);
superiorStarforce.setMaxSumUpgradePrice(maxSumUpgradePrice);
superiorStarforce.setAverageSumUpgradePrice(averageSumUpgradePrice);
superiorStarforce.setMinUpgradeCount(minUpgradeCount);
superiorStarforce.setMaxUpgradeCount(maxUpgradeCount);
superiorStarforce.setAverageUpgradeCount(averageUpgradeCount);
superiorStarforce.setMinDestroyCount(minDestroyCount);
superiorStarforce.setMaxDestroyCount(maxDestroyCount);
superiorStarforce.setAverageDestroyCount(averageDestroyCount);
superiorStarforce.setMinChanceTimeCount(minChanceTimeCount);
superiorStarforce.setMaxChanceTimeCount(maxChanceTimeCount);
superiorStarforce.setAverageChanceTimeCount(averageChanceTimeCount);
	
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