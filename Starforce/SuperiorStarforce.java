package project.starforce;

// 할인, 이벤트 사용불가 + 파괴방지 x + 실패하면 무조건 하락
// 슈페리얼 아이템 : 헬리시움 (80제), 노바 (110제), 타일런트 (150제)
public class SuperiorStarforce {
    
    // 인스턴스 변수
    int level = 0;
    int starforce = 0;
    int upgradeCount = 0;
    int destroyCount = 0;
    long sumUpgradePrice = 0;
    long starforcePrice = 0;
    long[] pricePerLevel = {59560,0,185079,0,0,558322}; // 레벨당 강화 비용 (헬리시움, 노바, 타일런트 순)
    int upgradePercent = 0;
    int[] destroyPercent = {1800, 3000, 4200, 6000, 9500, 13000, 16300, 48500, 49000, 49500}; 
	byte variableStarforce = 0;
    int chanceTime = 0;
    int sumOfStarforceCatch; // 스타포스캐치 합연산
    float multipleOfStarforceCatch; // 스타포스캐치 곱연산
    int maxStarforce; // 현재 레벨에서 가능한 최대 스타포스

    SuperiorStarforce() {
        this(150, 0, (byte)0);
    }

    SuperiorStarforce(int level, int fromStarforce) { // level선택, fromStarforce 몇성부터 시작
		this.level = level;
		this.starforce = fromStarforce;
        if(level < 95){
            this.starforcePrice = pricePerLevel[0]; // 헬리시움
            this.maxStarforce = 3; // 레벨별 최대 스타포스
        } else if (level < 108){
            this.starforcePrice = pricePerLevel[1]; // 구현 x
            this.maxStarforce = 5;
        } else if (level < 118){
            this.starforcePrice = pricePerLevel[2]; // 노바
            this.maxStarforce = 8;
        } else if (level < 128){
            this.starforcePrice = pricePerLevel[3]; // 구현 x
            this.maxStarforce = 10;
        } else if (level < 138){
            this.starforcePrice = pricePerLevel[4]; // 구현 x
            this.maxStarforce = 12;
        } else {
            this.starforcePrice = pricePerLevel[5]; // 타일런트
            this.maxStarforce = 15;
        }
	}
	
	SuperiorStarforce(int level, int fromStarforce, byte succededCatch) { // level선택, fromStarforce 몇성부터 시작, 스타캐치 여부 선택
		this(level, fromStarforce);
		switch (succededCatch){
			case 0: // 스타포스캐치 해제
			this.sumOfStarforceCatch = 0;
			this.multipleOfStarforceCatch = 1f;
			break;
			
			case 1: // 스타포스캐치 성공 (합연산)
			this.sumOfStarforceCatch = 4500;
			this.multipleOfStarforceCatch = 1f;
			break;

			case 2: // 스타포스캐치 성공 (곱연산)
			this.sumOfStarforceCatch = 0;
			this.multipleOfStarforceCatch = 1.045f;
			break;
		}
    }
    
    // 메서드
    void goTo(int toStarforce) { // 스타포스를 몇성까지 올릴 것인지
		if (starforce >= toStarforce || starforce < 0 || maxStarforce <= toStarforce || maxStarforce <= starforce) { // 첫번째 조건에서 파괴된 경우를 위해 반복문에서 뺌
			throw new RuntimeException("유효하지 않은 강화입니다."); // 잘못됨을 느끼면 아이템의 개수가 몇개인지 상관 없이 예외 던짐 
		} else {
			do {
                // 강화 시작
                upgradeCount++;
                int percent = (int)(Math.random() * 100000); 
                if (starforce == 0) {
                    upgradePercent = (int)((50 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch); //합연산, 곱연산 같이 적용되는 식으로 변경
                    sumUpgradePrice += starforcePrice;
                    if (percent < upgradePercent) {
                        starforce++;
                        variableStarforce = 0;
                    } 
                } else if (starforce == 1) {
                    upgradePercent = (int)((50 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
                    sumUpgradePrice += starforcePrice;
                    if (percent < upgradePercent) {
                        starforce++;
                        variableStarforce = 0;
                    } else {
                        starforce--;
                        variableStarforce--;
                        if (variableStarforce == -2) {
                            sumUpgradePrice += starforcePrice;
                            starforce++;
                            variableStarforce = 0;
                            chanceTime++;
                        }
                    }
                } else if (starforce == 2) {
                    upgradePercent = (int)((45 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
                    sumUpgradePrice += starforcePrice;
                    if (percent < upgradePercent) {
                        starforce++;
                        variableStarforce = 0;
                    } else {
                        starforce--;
                        variableStarforce--;
                        if (variableStarforce == -2) {
                            sumUpgradePrice += starforcePrice;
                            starforce++;
                            variableStarforce = 0;
                            chanceTime++;
                        }
                    }
                } else if (starforce < 5) {
                    upgradePercent = (int)((40 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
                    sumUpgradePrice += starforcePrice;
                    if (percent < upgradePercent) {
                        starforce++;
                        variableStarforce = 0;
                    } else {
                        starforce--;
                        variableStarforce--;
                        if (variableStarforce == -2) {
                            sumUpgradePrice += starforcePrice;
                            starforce++;
                            variableStarforce = 0;
                            chanceTime++;
                        }
                    }
                } else if (starforce == 5) {
                    upgradePercent = (int)((40 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
                    sumUpgradePrice += starforcePrice;
                    if (percent < upgradePercent) {
                        starforce++;
                        variableStarforce = 0;
                    } else if (percent < upgradePercent + destroyPercent[0]) {
                        starforce = 0;
                        destroyCount++;
                    } else {
                        starforce--;
                        variableStarforce--;
                        if (variableStarforce == -2) {
                            sumUpgradePrice += starforcePrice;
                            starforce++;
                            variableStarforce = 0;
                            chanceTime++;
                        }
                    }
                } else if (starforce == 6) {
                    upgradePercent = (int)((40 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
                    sumUpgradePrice += starforcePrice;
                    if (percent < upgradePercent) {
                        starforce++;
                        variableStarforce = 0;
                    } else if (percent < upgradePercent + destroyPercent[1]) {
                        starforce = 0;
                        destroyCount++;
                    } else {
                        starforce--;
                        variableStarforce--;
                        if (variableStarforce == -2) {
                            sumUpgradePrice += starforcePrice;
                            starforce++;
                            variableStarforce = 0;
                            chanceTime++;
                        }
                    }
                } else if (starforce == 7) {
                    upgradePercent = (int)((40 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
                    sumUpgradePrice += starforcePrice;
                    if (percent < upgradePercent) {
                        starforce++;
                        variableStarforce = 0;
                    } else if (percent < upgradePercent + destroyPercent[2]) {
                        starforce = 0;
                        destroyCount++;
                    } else {
                        starforce--;
                        variableStarforce--;
                        if (variableStarforce == -2) {
                            sumUpgradePrice += starforcePrice;
                            starforce++;
                            variableStarforce = 0;
                            chanceTime++;
                        }
                    }
                } else if (starforce == 8) {
                    upgradePercent = (int)((40 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
                    sumUpgradePrice += starforcePrice;
                    if (percent < upgradePercent) {
                        starforce++;
                        variableStarforce = 0;
                    } else if (percent < upgradePercent + destroyPercent[3]) {
                        starforce = 0;
                        destroyCount++;
                    } else {
                        starforce--;
                        variableStarforce--;
                        if (variableStarforce == -2) {
                            sumUpgradePrice += starforcePrice;
                            starforce++;
                            variableStarforce = 0;
                            chanceTime++;
                        }
                    }
                } else if (starforce == 9) {
                    upgradePercent = (int)((37 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
                    sumUpgradePrice += starforcePrice;
                    if (percent < upgradePercent) {
                        starforce++;
                        variableStarforce = 0;
                    } else if (percent < upgradePercent + destroyPercent[4]) {
                        starforce = 0;
                        destroyCount++;
                    } else {
                        starforce--;
                        variableStarforce--;
                        if (variableStarforce == -2) {
                            sumUpgradePrice += starforcePrice;
                            starforce++;
                            variableStarforce = 0;
                            chanceTime++;
                        }
                    }
                } else if (starforce == 10) {
                    upgradePercent = (int)((35 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
                    sumUpgradePrice += starforcePrice;
                    if (percent < upgradePercent) {
                        starforce++;
                        variableStarforce = 0;
                    } else if (percent < upgradePercent + destroyPercent[5]) {
                        starforce = 0;
                        destroyCount++;
                    } else {
                        starforce--;
                        variableStarforce--;
                        if (variableStarforce == -2) {
                            sumUpgradePrice += starforcePrice;
                            starforce++;
                            variableStarforce = 0;
                            chanceTime++;
                        }
                    }
                } else if (starforce == 11) {
                    upgradePercent = (int)((35 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
                    sumUpgradePrice += starforcePrice;
                    if (percent < upgradePercent) {
                        starforce++;
                        variableStarforce = 0;
                    } else if (percent < upgradePercent + destroyPercent[6]) {
                        starforce = 0;
                        destroyCount++;
                    } else {
                        starforce--;
                        variableStarforce--;
                        if (variableStarforce == -2) {
                            sumUpgradePrice += starforcePrice;
                            starforce++;
                            variableStarforce = 0;
                            chanceTime++;
                        }
                    }
                } else if (starforce == 12) {
                    upgradePercent = (int)((3 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
                    sumUpgradePrice += starforcePrice;
                    if (percent < upgradePercent) {
                        starforce++;
                        variableStarforce = 0;
                    } else if (percent < upgradePercent + destroyPercent[7]) {
                        starforce = 0;
                        destroyCount++;
                    } else {
                        starforce--;
                        variableStarforce--;
                        if (variableStarforce == -2) {
                            sumUpgradePrice += starforcePrice;
                            starforce++;
                            variableStarforce = 0;
                            chanceTime++;
                        }
                    }
                } else if (starforce == 13) {
                    upgradePercent = (int)((2 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
                    sumUpgradePrice += starforcePrice;
                    if (percent < upgradePercent) {
                        starforce++;
                        variableStarforce = 0;
                    } else if (percent < upgradePercent + destroyPercent[8]) {
                        starforce = 0;
                        destroyCount++;
                    } else {
                        starforce--;
                        variableStarforce--;
                        if (variableStarforce == -2) {
                            sumUpgradePrice += starforcePrice;
                            starforce++;
                            variableStarforce = 0;
                            chanceTime++;
                        }
                    }
                } else if (starforce == 14) {
                    upgradePercent = (int)((1 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
                    sumUpgradePrice += starforcePrice;
                    if (percent < upgradePercent) {
                        starforce++;
                        variableStarforce = 0;
                    } else if (percent < upgradePercent + destroyPercent[9]) {
                        starforce = 0;
                        destroyCount++;
                    } else {
                        starforce--;
                        variableStarforce--;
                    }
                }
            } while(starforce != toStarforce);
            upgradeCount += chanceTime;
        }
    }

    static String sumUpgradePriceMinMax(SuperiorStarforce[] itemArr){
		long min = itemArr[0].sumUpgradePrice;
		long max = itemArr[0].sumUpgradePrice;
		for (int i = 1; i < itemArr.length; i++){
			if (min > itemArr[i].sumUpgradePrice){
				min = itemArr[i].sumUpgradePrice;
			} else if (max < itemArr[i].sumUpgradePrice){
				max = itemArr[i].sumUpgradePrice;
			}
		}
		return itemArr.length + "개의 아이템 중 스타포스 강화 최소비용은 " + min + "00메소 입니다."
				+ "\n" + itemArr.length + "개의 아이템 중 스타포스 강화 최대비용은 " + max + "00메소 입니다.";
    }
    
    static String averageSumUpgradePrice(SuperiorStarforce[] itemArr) { 
		long totalsum = 0;
		for (int i = 0; i < itemArr.length; i++) {
			totalsum += itemArr[i].sumUpgradePrice;
			}
		long average = totalsum/itemArr.length;
		return itemArr.length + "개의 아이템의 평균적인 스타포스 강화 비용은 " + average + "00메소 입니다.";
	}
	
	static String upgradeCountMinMax(SuperiorStarforce[] itemArr){
		long min = itemArr[0].upgradeCount;
		long max = itemArr[0].upgradeCount;
		for (int i = 1; i < itemArr.length; i++){
			if (min > itemArr[i].upgradeCount){
				min = itemArr[i].upgradeCount;
			} else if (max < itemArr[i].upgradeCount){
				max = itemArr[i].upgradeCount;
			}
		}
		return itemArr.length + "개의 아이템 중 가장 적은 강화 횟수는 " + min + "번입니다."
				+ "\n" + itemArr.length + "개의 아이템 중 가장 많은 강화 횟수는 " + max + "번입니다.";
    }
    
    static String averageUpgradeCount(SuperiorStarforce[] itemArr) { 
		long totalsum = 0;
		for (int i = 0; i < itemArr.length; i++) {
			totalsum += itemArr[i].upgradeCount;
		}
		long average = totalsum/itemArr.length;
		return itemArr.length + "개의 아이템의 평균적인 강화 횟수는 " + average + "번입니다.";
	}

	static String destroyCountMinMax(SuperiorStarforce[] itemArr){
		long min = itemArr[0].destroyCount;
		long max = itemArr[0].destroyCount;
		for (int i = 1; i < itemArr.length; i++){
			if (min > itemArr[i].destroyCount){
				min = itemArr[i].destroyCount;
			} else if (max < itemArr[i].destroyCount){
				max = itemArr[i].destroyCount;
			}
		}
		return itemArr.length + "개의 아이템 중 가장 적은 파괴 횟수는 " + min + "번입니다."
				+ "\n" + itemArr.length + "개의 아이템 중 가장 많은 파괴 횟수는 " + max + "번입니다.";
    }
    
    static String averageDestroyCount(SuperiorStarforce[] itemArr) { 
		long totalsum = 0;
		for (int i = 0; i < itemArr.length; i++) {
			totalsum += itemArr[i].destroyCount;
		}
		long average = totalsum/itemArr.length;
		return itemArr.length + "개의 아이템의 평균적인 파괴 횟수는 " + average + "번입니다.";
	}

	static String chanceTimeCountMinMax(SuperiorStarforce[] itemArr){
		long min = itemArr[0].chanceTime;
		long max = itemArr[0].chanceTime;
		for (int i = 1; i < itemArr.length; i++){
			if (min > itemArr[i].chanceTime){
				min = itemArr[i].chanceTime;
			} else if (max < itemArr[i].chanceTime){
				max = itemArr[i].chanceTime;
			}
		}
		return itemArr.length + "개의 아이템 중 가장 적은 찬스타임 횟수는 " + min + "번입니다."
				+ "\n" + itemArr.length + "개의 아이템 중 가장 많은 찬스타임 횟수는 " + max + "번입니다.";
    }
    
    static String averageChanceTimeCount(SuperiorStarforce[] itemArr) { 
		long totalsum = 0;
		for (int i = 0; i < itemArr.length; i++) {
			totalsum += itemArr[i].chanceTime;
		}
		long average = totalsum/itemArr.length;
		return itemArr.length + "개의 아이템의 평균적인 찬스타임 횟수는 " + average + "번입니다.";
    }

    static String starCatchToString(byte succededCatch) { 
		if(succededCatch == 0){
			return "x";
		} else if (succededCatch == 1){
			return "합연산(4.5%p)";
		} else {
			return "곱연산(1.045배)";
		}
    }
    
    public static void main(String[] args) {
        long beforeRun = System.currentTimeMillis(); // 메서드를 돌리기 전의 시간

		// 변수 수정해야하는 부분
		int level = 150; // 아이템 레벨 ( 헬리시움 (80제), 노바 (110제), 타일런트 (150제) )
		int fromStarforce = 0; // 시작 스타포스
		int toStarforce = 12; // 도달 스타포스
		byte succededCatch = 0; // 스타캐치 여부 (0 : 스타포스캐치x, 1 : 합연산, 2 : 곱연산)
		int countItem = 100000; // 아이템의 개수 (표본)
//		long recentMeso = 15000000000L; // 현재 메소

		// 현재 메소로 성공할 확률을 구하기 위한 카운트
//		int succededcount = 0;
/*		
		// 현재 가지고 있는 메소 배열
		MyMeso[] mesoArr = new MyMeso[countItem];
		for (int i = 0; i < countItem; i++) {
			mesoArr[i] = new MyMeso(recentMeso);
		}
*/
		// 아이템 배열의 강화
		SuperiorStarforce[] itemArr = new SuperiorStarforce[countItem];
		for (int i = 0; i < countItem; i++) {
			itemArr[i] = new SuperiorStarforce(level, fromStarforce, succededCatch);
			itemArr[i].goTo(toStarforce);
		}
/*
		// 현재 메소와 강화비용 비교
		for (int i = 0; i < countItem; i++){
			if (mesoArr[i].recentMeso - (itemArr[i].sumUpgradePrice *100) > 0) { // 100 곱한 이유 : sumUpgradePrice에 100을 나눠놨던 것
				succededcount++;
			}
		}
*/
		// 현재 메소로 성공할 확률
//		float succededRecentPercent = (float)succededcount/countItem *100;
		
		System.out.println("==== " + level + "제 아이템의 " + itemArr.length + "개 " + fromStarforce + "성 -> " + toStarforce + "성까지의 강화 ====");
		System.out.println("추가 설명 : 스타캐치 " + starCatchToString(succededCatch));
		System.out.println(sumUpgradePriceMinMax(itemArr));
		System.out.println(averageSumUpgradePrice(itemArr));
		System.out.println(upgradeCountMinMax(itemArr));
		System.out.println(averageUpgradeCount(itemArr));
		System.out.println(destroyCountMinMax(itemArr));
		System.out.println(averageDestroyCount(itemArr));
		System.out.println(chanceTimeCountMinMax(itemArr));
		System.out.println(averageChanceTimeCount(itemArr));
//		System.out.println(recentMeso + "메소로 성공할 확률은 " + succededRecentPercent + "%입니다."); // 현재자본으로 성공할 확률 필요할 때 사용
		
		long afterRun = System.currentTimeMillis(); // 메서드를 돌린 후의 시간

		// runtime 생성
		long runtime = afterRun - beforeRun; 
		System.out.printf("RunTime : %d시간 %d분 %d초%n", runtime/ (60*60*1000), runtime /(60*1000) % 60, runtime /1000 % 60);
		
	}
}
