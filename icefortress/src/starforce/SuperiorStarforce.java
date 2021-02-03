package starforce;

public class SuperiorStarforce extends Item {

	// �ν��Ͻ� ����
	long starforcePrice;
	long[] pricePerLevel = { 59560, 0, 185079, 0, 0, 558322 }; // ������ ��ȭ ��� (�︮�ÿ�, ���, Ÿ�Ϸ�Ʈ ��)
	int[] destroyPercent = { 1800, 3000, 4200, 6000, 9500, 13000, 16300, 48500, 49000, 49500 };
	int maxStarforce; // ���� �������� ������ �ִ� ��Ÿ����

	// ������
	public SuperiorStarforce(int level, int fromStarforce, byte succededCatch) {
		super(level, fromStarforce, succededCatch);
		if (level < 95) {
			this.starforcePrice = pricePerLevel[0]; // �︮�ÿ�
			this.maxStarforce = 3; // ������ �ִ� ��Ÿ����
		} else if (level < 108) {
			this.starforcePrice = pricePerLevel[1]; // ���� x
			this.maxStarforce = 5;
		} else if (level < 118) {
			this.starforcePrice = pricePerLevel[2]; // ���
			this.maxStarforce = 8;
		} else if (level < 128) {
			this.starforcePrice = pricePerLevel[3]; // ���� x
			this.maxStarforce = 10;
		} else if (level < 138) {
			this.starforcePrice = pricePerLevel[4]; // ���� x
			this.maxStarforce = 12;
		} else {
			this.starforcePrice = pricePerLevel[5]; // Ÿ�Ϸ�Ʈ
			this.maxStarforce = 15;
		}
	}

	// �޼���
	
	public static int maxItemStarforce(int level){ // ������ ������ �ִ� ��Ÿ����
    	if(level < 95){
            return 3; // ������ �ִ� ��Ÿ���� (�︮�ÿ�)
        } else if (level < 108){
        	return 5; // ���� x
        } else if (level < 118){
        	return 8; // ���
        } else if (level < 128){
        	return 10; // ���� x
        } else if (level < 138){
        	return 12; // ���� x
        } else {
        	return 15; // Ÿ�Ϸ�Ʈ
        }
    }
	
	public static String result(SuperiorStarforce[] siArr, int fromStarforce, int toStarforce) { // ����� ��� �޼���
    	String result = "";
    	result += "==== " + siArr[0].level + "�� ���丮�� �������� " + siArr.length + "�� " + fromStarforce + "�� -> " + toStarforce + "�������� ��ȭ ==== <br>";
    	result += "�߰� ���� - ��Ÿĳġ " + Item.starCatchToString(siArr[0].succededCatch) +"<br>";
    	return result;
    }
	
	public void goTo(int toStarforce) {
		do {
			// ��ȭ ����
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