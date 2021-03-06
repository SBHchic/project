package starforce;

public class SuperiorStarforce extends Item {

	// 인스턴스 변수
	long starforcePrice;
	long[] pricePerLevel = { 59560, 0, 185079, 0, 0, 558322 }; // 레벨당 강화 비용 (헬리시움, 노바, 타일런트 순)
	int[] destroyPercent = { 1800, 3000, 4200, 6000, 9500, 13000, 16300, 48500, 49000, 49500 };
	int maxStarforce; // 현재 레벨에서 가능한 최대 스타포스

	// 생성자
	public SuperiorStarforce(int level, int fromStarforce, byte succededCatch) {
		super(level, fromStarforce, succededCatch);
		if (level < 95) {
			this.starforcePrice = pricePerLevel[0]; // 헬리시움
			this.maxStarforce = 3; // 레벨별 최대 스타포스
		} else if (level < 108) {
			this.starforcePrice = pricePerLevel[1]; // 구현 x
			this.maxStarforce = 5;
		} else if (level < 118) {
			this.starforcePrice = pricePerLevel[2]; // 노바
			this.maxStarforce = 8;
		} else if (level < 128) {
			this.starforcePrice = pricePerLevel[3]; // 구현 x
			this.maxStarforce = 10;
		} else if (level < 138) {
			this.starforcePrice = pricePerLevel[4]; // 구현 x
			this.maxStarforce = 12;
		} else {
			this.starforcePrice = pricePerLevel[5]; // 타일런트
			this.maxStarforce = 15;
		}
	}

	// 메서드
	
	public static int maxItemStarforce(int level){ // 아이템 레벨당 최대 스타포스
    	if(level < 95){
            return 3; // 레벨별 최대 스타포스 (헬리시움)
        } else if (level < 108){
        	return 5; // 구현 x
        } else if (level < 118){
        	return 8; // 노바
        } else if (level < 128){
        	return 10; // 구현 x
        } else if (level < 138){
        	return 12; // 구현 x
        } else {
        	return 15; // 타일런트
        }
    }
	
	public void goTo(int toStarforce) {
		do {
			// 강화 시작
			upgradeCount++;
			int percent = (int) (Math.random() * 100000);
			if (starforce == 0) {
				upgradePercent = (int) ((50 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
				sumUpgradePrice += starforcePrice;
				if (percent < upgradePercent) {
					starforce++;
					variableStarforce = 0;
				}
			} else if (starforce == 1) {
				upgradePercent = (int) ((50 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
				upgradePercent = (int) ((45 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
				upgradePercent = (int) ((40 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
				upgradePercent = (int) ((40 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
				upgradePercent = (int) ((40 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
				upgradePercent = (int) ((40 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
				upgradePercent = (int) ((40 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
				upgradePercent = (int) ((37 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
				upgradePercent = (int) ((35 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
				upgradePercent = (int) ((35 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
				upgradePercent = (int) ((3 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
				upgradePercent = (int) ((2 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
				upgradePercent = (int) ((1 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
		} while (starforce != toStarforce);
		upgradeCount += chanceTime;
	}
}
