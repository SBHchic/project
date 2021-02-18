package starforce;

abstract class Item {

    // 인스턴스 변수
    int level;
    int starforce;
    int upgradeCount = 0;
    int destroyCount = 0;
    long sumUpgradePrice = 0;
    int upgradePercent = 0;
    byte variableStarforce = 0;
    int chanceTime = 0;
	byte succededCatch;
    int sumOfStarforceCatch; // 스타포스캐치 합연산
    float multipleOfStarforceCatch; // 스타포스캐치 곱연산

    // 생성자
    public Item(int level, int fromStarforce, byte succededCatch){
        this.level = level;
		this.starforce = fromStarforce;
		this.succededCatch = succededCatch;
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
    public static String starCatchToString(byte succededCatch) { // 스타캐치 출력 편의성
		
		if(succededCatch == 0){
			return "X";
		} else if (succededCatch == 1){
			return "합연산(4.5%p)";
		} else {
			return "곱연산(1.045배)";
        }
    }

    public static String booleanToString(boolean booleanSelect){ // boolean형태 출력 편의성
		if (booleanSelect == true){
			return "O";
		}
		return "X";
	}

	public static String mapleEventToString(byte mapleEvent){ // 이벤트 출력 편의성
		if (mapleEvent == 0){
			return "X";
		} else if (mapleEvent == 1) {
			return "강화비용 30% 할인 이벤트";
		} else if (mapleEvent == 2){
			return "스타포스 1+1 이벤트 (10성까지)";
		} else {
			return "5의 배수의 스타포스 100퍼센트 성공 (15성까지)";
		}
    }

	public static String MVPGradeToString(byte discountMVPGrade){ // MVP등급 출력 편의성
		if (discountMVPGrade == 0){
			return "X";
		} else if (discountMVPGrade == 1){
			return "실버 (3%)";
		} else if (discountMVPGrade == 2){
			return "골드 (5%)";
		} else {
			return "다이아 or 레드 (10%)";
		}
	}
    
    public static int toadItemLevel(int level) { // 토드템 허용 여부 확인 메서드
    	int toadItemLevel = 0;
    	if (level <= 110) {
    		toadItemLevel = level-20;
        } else {
        	toadItemLevel = level-10;
        }
    	return toadItemLevel;
    }
    
    public static long sumUpgradePriceMin(Item[] itemArr) { // 최소 강화 비용을 구하는 메서드
    	long min = itemArr[0].sumUpgradePrice;
    	for (int i = 1; i < itemArr.length; i++) {
    		if (min > itemArr[i].sumUpgradePrice) {
    			min = itemArr[i].sumUpgradePrice;
    		}
    	}
    	return min;
    }
    
    public static long sumUpgradePriceMax(Item[] itemArr) { // 최대 강화 비용을 구하는 메서드
    	long max = itemArr[0].sumUpgradePrice;
    	for (int i = 1; i < itemArr.length; i++) {
    		if (max < itemArr[i].sumUpgradePrice) {
    			max = itemArr[i].sumUpgradePrice;
    		}
    	}
    	return max;
    }
    
    public static long sumUpgradePriceAverage(Item[] itemArr) { // 평균 강화 비용을 구하는 메서드
    	long totalsum = 0;
    	for (int i = 0; i < itemArr.length; i++) {
    		totalsum += itemArr[i].sumUpgradePrice;
    	}
    	return totalsum/itemArr.length;
    }
    
    public static int upgradeCountMin(Item[] itemArr) { // 최소 강화 횟수를 구하는 메서드
    	int min = itemArr[0].upgradeCount;
    	for (int i = 1; i < itemArr.length; i++) {
    		if (min > itemArr[i].upgradeCount) {
    			min = itemArr[i].upgradeCount;
    		}
    	}
    	return min;
    }
    
    public static int upgradeCountMax(Item[] itemArr) { // 최대 강화 횟수를 구하는 메서드
    	int max = itemArr[0].upgradeCount;
    	for (int i = 1; i < itemArr.length; i++) {
    		if (max < itemArr[i].upgradeCount) {
    			max = itemArr[i].upgradeCount;
    		}
    	}
    	return max;
    }
    
     public static long upgradeCountAverage(Item[] itemArr) { // 평균 강화 횟수를 구하는 메서드
    	long totalsum = 0;
    	for (int i = 0; i < itemArr.length; i++) {
    		totalsum += itemArr[i].upgradeCount;
    	}
    	return totalsum/itemArr.length;
    }
    
     public static int destroyCountMin(Item[] itemArr) { // 최소 파괴 횟수를 구하는 메서드
    	int min = itemArr[0].destroyCount;
    	for (int i = 1; i < itemArr.length; i++) {
    		if (min > itemArr[i].destroyCount) {
    			min = itemArr[i].destroyCount;
    		}
    	}
    	return min;
    }
    
     public static int destroyCountMax(Item[] itemArr) { // 최대 파괴 횟수를 구하는 메서드
    	int max = itemArr[0].destroyCount;
    	for (int i = 1; i < itemArr.length; i++) {
    		if (max < itemArr[i].destroyCount) {
    			max = itemArr[i].destroyCount;
    		}
    	}
    	return max;
    }
    
     public static long destroyCountAverage(Item[] itemArr) { // 평균 파괴 횟수를 구하는 메서드
    	long totalsum = 0;
    	for (int i = 0; i < itemArr.length; i++) {
    		totalsum += itemArr[i].destroyCount;
    	}
    	return totalsum/itemArr.length;
    }
    
     public static int chanceTimeMin(Item[] itemArr) { // 최소 찬스타임 횟수를 구하는 메서드
    	int min = itemArr[0].chanceTime;
    	for (int i = 1; i < itemArr.length; i++) {
    		if (min > itemArr[i].chanceTime) {
    			min = itemArr[i].chanceTime;
    		}
    	}
    	return min;
    }
    
     public static int chanceTimeMax(Item[] itemArr) { // 최대 찬스타임 횟수를 구하는 메서드
    	int max = itemArr[0].chanceTime;
    	for (int i = 1; i < itemArr.length; i++) {
    		if (max < itemArr[i].chanceTime) {
    			max = itemArr[i].chanceTime;
    		}
    	}
    	return max;
    }
    
     public static long chanceTimeAverage(Item[] itemArr) { // 평균 찬스타임 횟수를 구하는 메서드
    	long totalsum = 0;
    	for (int i = 0; i < itemArr.length; i++) {
    		totalsum += itemArr[i].chanceTime;
    	}
    	return totalsum/itemArr.length;
    }
    
}

public class NumberOfItems_Normal extends Item {

    // 인스턴스 변수
    int[] destroyPercent = {600, 1300, 1400, 2100, 2100, 2800, 7000, 19400, 29400, 39600, 0}; 
	boolean ignoreDestroy; // 파괴방지 여부
	int maxStarforce;
	byte mapleEvent = 0;
	int toadToStarforce = 0;
	boolean toadHammer = false;
	boolean toadIgnoreDestroy;
    int toadDestroyCount = 0;
    
    // 생성자
    public NumberOfItems_Normal(int level, int fromStarforce, byte succededCatch){
        super(level, fromStarforce, succededCatch);
        this.maxStarforce = maxItemStarforce(level);
    }

    public NumberOfItems_Normal(int level, int fromStarforce, byte succededCatch, byte mapleEvent) {
        this(level, fromStarforce, succededCatch);
		this.mapleEvent = mapleEvent;
    }

    public NumberOfItems_Normal(int level, int fromStarforce, byte succededCatch, byte mapleEvent, boolean ignoreDestroy) {
        this(level, fromStarforce, succededCatch, mapleEvent);
        this.ignoreDestroy = ignoreDestroy;
		if (ignoreDestroy == true) {
			for (int i = 0; i < 4; i++) {
				destroyPercent[i] = destroyPercent[10];
			}
        }
	}

	public NumberOfItems_Normal(int level, int fromStarforce, byte succededCatch, byte mapleEvent, boolean ignoreDestroy, int toadToStarforce){
		this(level, fromStarforce, succededCatch, mapleEvent, ignoreDestroy);
		if (0 < toadToStarforce && toadToStarforce <= 25){
			toadHammer = true;
			this.toadToStarforce = toadToStarforce;
		} 
	}

    // 메서드
    public static int maxItemStarforce(int level){ // 아이템 레벨당 최대 스타포스
        if (level < 95) {
			return 5;
		} else if (level < 108) {
			return 8;
		} else if (level < 118) {
			return 10;
		} else if (level < 128) {
			return 15;
		} else if (level < 138) {
			return 20;
		} else {
			return 25;
		}
    }

	public static String mapleEventToString(byte mapleEvent){ // 이벤트 출력 편의성
		if (mapleEvent == 0){
			return "X";
		} else if (mapleEvent == 1) {
			return "스타포스 1+1 이벤트 (10성까지)";
		} else {
			return "5의 배수의 스타포스 100퍼센트 성공 (15성까지)";
		}
    }

    void fromToadItemToNumberOfItems_Normal(NumberOfItems_ToadItem ti){ // 토드템에서 본템으로 토드할때 옮겨오는 값들
        this.starforce = --ti.starforce;
        this.toadDestroyCount += ti.destroyCount;
    }
    
    public static double succededCount(NumberOfItems_Normal[] niArr, int success) { // 성공 확률 계산 메서드
    	int count = 0;
    	for (int i = 0; i < niArr.length; i++) {
    		if (success == niArr[i].starforce) {
    			count++;
    		}
    	}
    	return (double)count/niArr.length*100;
    }
    public static String result(NumberOfItems_Normal[] niArr, int fromStarforce, int toStarforce) { // 결과값 출력 메서드
    	String result = "";
    	result += "==== " + niArr[0].level + "제 노말아이템의 " + niArr.length + "개 " + fromStarforce + "성 -> " + toStarforce + "성까지의 강화 ==== <br>";
    	result += "추가 설명 - 스타캐치 " + NumberOfItems_Normal.starCatchToString(niArr[0].succededCatch) + ", 파괴방지 : " + NumberOfItems_Normal.booleanToString(niArr[0].ignoreDestroy) + "<br>";
    	result += "토드여부 : " + NumberOfItems_Normal.booleanToString(niArr[0].toadHammer) + "<br>";
    	if (niArr[0].toadHammer) {
    		result += " 토드템 0성 -> " + niArr[0].toadToStarforce + "성, 토드템 파괴방지 : " + NumberOfItems_Normal.booleanToString(niArr[0].toadIgnoreDestroy) + "<br>";
    	}
    	result += "메이플 이벤트 적용 : " + NumberOfItems_Normal.mapleEventToString(niArr[0].mapleEvent) + "<br>";
    	return result;
    }
    
    public static NumberOfItems_Normal goTo(NumberOfItems_Normal ni, int toStarforce, boolean toadIgnoreDestroy, int numberOfRealItems, int numberOfToadItems) { // 스타포스 시행 메서드
		ni.toadIgnoreDestroy = toadIgnoreDestroy;
			switch(ni.mapleEvent){
				case 0: // 이벤트 x
				do {
					if (ni.toadHammer == true && ni.starforce == 0){
						NumberOfItems_ToadItem ti = new NumberOfItems_ToadItem(ni.level, ni.starforce, ni.succededCatch, ni.mapleEvent, ni.toadIgnoreDestroy);
						ti.goTo(ni.toadToStarforce, numberOfToadItems);
						ni.fromToadItemToNumberOfItems_Normal(ti);
					} else{
						int percent = (int)(Math.random() * 100000);
						if (ni.starforce < 3) {
							ni.upgradePercent = (int)(((95 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch); 
							if (percent < ni.upgradePercent) {
								ni.starforce++;
							} 
						} else if (ni.starforce < 10) {
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
							} 
						} else if (ni.starforce == 10) {
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							}
						} else if (ni.starforce < 12) { 
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) { 
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce == 12) { 
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[0]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) {
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce == 13) {
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[1]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) {
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce == 14) {
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[2]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) {
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce == 15) {
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[3]) {
								ni.starforce = 0;
								ni.destroyCount++;
							}
						} else if (ni.starforce == 16) {
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[3]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) {
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce == 17) {
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[4]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) {
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce < 20) {
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[5]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) {
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce == 20) {
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[6]) {
								ni.starforce = 0;
								ni.destroyCount++;
							}
						} else if (ni.starforce == 21) {
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[6]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) {
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce == 22) {
							ni.upgradePercent = (int)((3000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[7]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) {
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce == 23) {
							ni.upgradePercent = (int)((2000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[8]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) {
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce == 24) {
							ni.upgradePercent = (int)((1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[9]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
							}
						}
					}
					if (ni.toadHammer == true && numberOfToadItems <= ni.toadDestroyCount) {
						ni.starforce = 0;
						break;
					}
				} while(ni.starforce != toStarforce && numberOfRealItems > ni.destroyCount);
				break;

				case 1: // 10성까지 스타포스 1+1 
				do {
					if (ni.toadHammer == true && ni.starforce == 0){
						NumberOfItems_ToadItem ti = new NumberOfItems_ToadItem(ni.level, ni.starforce, ni.succededCatch, ni.mapleEvent, toadIgnoreDestroy);
						ti.goTo(ni.toadToStarforce, numberOfToadItems);
						ni.fromToadItemToNumberOfItems_Normal(ti);
					} else {
						int percent = (int)(Math.random() * 100000); 
						if (ni.starforce < 3) {
							ni.upgradePercent = (int)(((95 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch); 
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.starforce++; 
							} 
						} else if (ni.starforce < 10) {
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.starforce++;
							} 
						} else if (ni.starforce == 10) {
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.starforce++;
								ni.variableStarforce = 0;
							} 
						} else if (ni.starforce < 12) { 
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) { 
									ni.starforce++;
									ni.starforce++; 
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce == 12) { 
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[0]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) {
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce == 13) {
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[1]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) {
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce == 14) {
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[2]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) {
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce == 15) {
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[3]) {
								ni.starforce = 0;
								ni.destroyCount++;
							}
						} else if (ni.starforce == 16) {
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[3]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) {
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce == 17) {
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[4]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) {
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce < 20) {
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[5]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) {
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce == 20) {
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[6]) {
								ni.starforce = 0;
								ni.destroyCount++;
							}
						} else if (ni.starforce == 21) {
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[6]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) {
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce == 22) {
							ni.upgradePercent = (int)((3000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[7]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) {
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce == 23) {
							ni.upgradePercent = (int)((2000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[8]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) {
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce == 24) {
							ni.upgradePercent = (int)((1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[9]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
							}
						}
					}
					if (ni.toadHammer == true && numberOfToadItems <= ni.toadDestroyCount) {
						ni.starforce = 0;
						break;
					}
				} while(ni.starforce != toStarforce && numberOfRealItems > ni.destroyCount);
				break;

				case 2: // 5의배수 100퍼센트 강화 성공 (15성 까지)
				do {
					if (ni.toadHammer == true && ni.starforce == 0){
						NumberOfItems_ToadItem ti = new NumberOfItems_ToadItem(ni.level, ni.starforce, ni.succededCatch, ni.mapleEvent, toadIgnoreDestroy);
						ti.goTo(ni.toadToStarforce, numberOfToadItems);
						ni.fromToadItemToNumberOfItems_Normal(ti);
					} else {
						int percent = (int)(Math.random() * 100000); 
						if (ni.starforce < 3) {
							ni.upgradePercent = (int)(((95 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch); 
							if (percent < ni.upgradePercent) {
								ni.starforce++;
							} 
						} else if (ni.starforce < 10) {
							if (ni.starforce == 5) { 
								ni.upgradePercent = 100000;
							}else {
								ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							}
							if (percent < ni.upgradePercent) {
								ni.starforce++;
							} 
						} else if (ni.starforce == 10) { 
							ni.starforce++;
							ni.variableStarforce = 0;
						} else if (ni.starforce < 12) { 
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) { 
									ni.starforce++;
									ni.starforce++; 
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce == 12) { 
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[0]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) {
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce == 13) {
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[1]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) {
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce == 14) {
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[2]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) {
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce == 15) { 
							ni.starforce++;
							ni.variableStarforce = 0;
						} else if (ni.starforce == 16) {
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[3]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) {
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce == 17) {
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[4]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) {
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce < 20) {
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[5]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) {
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce == 20) {
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[6]) {
								ni.starforce = 0;
								ni.destroyCount++;
							}
						} else if (ni.starforce == 21) {
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[6]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) {
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce == 22) {
							ni.upgradePercent = (int)((3000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[7]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) {
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce == 23) {
							ni.upgradePercent = (int)((2000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[8]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) {
									ni.starforce++;
									ni.variableStarforce = 0;
								}
							}
						} else if (ni.starforce == 24) {
							ni.upgradePercent = (int)((1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[9]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
							}
						}
					}
					if (ni.toadHammer == true && numberOfToadItems <= ni.toadDestroyCount) {
						ni.starforce = 0;
						break;
					}
				} while(ni.starforce != toStarforce && numberOfRealItems > ni.destroyCount);
				break;
			}
			return ni;
		}
}

class NumberOfItems_ToadItem extends NumberOfItems_Normal{

    // 인스턴스 변수
	int toadToStarforce;

    // 생성자
    NumberOfItems_ToadItem(int level, int fromStarforce, byte succededCatch, byte mapleEvent, boolean toadIgnoreDestroy){
        super(level, fromStarforce, succededCatch, mapleEvent);
        if (this.level <= 110) {
            this.level -= 20;
        } else {
            this.level -= 10;
        }
        ignoreDestroy = toadIgnoreDestroy;
		if (ignoreDestroy == true) {
			for (int i = 0; i < 4; i++) {
				destroyPercent[i] = destroyPercent[10];
			}
        }
    }

    // 메서드
    public void goTo(int toToadItemStarforce, int numberOfToadItems){
			switch(mapleEvent){
				case 0: // 이벤트 x
				do {
					int percent = (int)(Math.random() * 100000);
					if (starforce < 3) {
						upgradePercent = (int)(((95 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch); 
						if (percent < upgradePercent) {
							starforce++;
						} 
					} else if (starforce < 10) {
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
						} 
					} else if (starforce == 10) {
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						}
					} else if (starforce < 12) { 
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else {
							starforce--;
							variableStarforce--;
							if (variableStarforce == -2) { 
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce == 12) {
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce == 13) {
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce == 14) {
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce == 15) {
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else if (percent < upgradePercent + destroyPercent[3]) {
							starforce = 0;
							destroyCount++;
						}
					} else if (starforce == 16) {
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce == 17) {
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce < 20) {
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce == 20) {
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else if (percent < upgradePercent + destroyPercent[6]) {
							starforce = 0;
							destroyCount++;
						}
					} else if (starforce == 21) {
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce == 22) {
						upgradePercent = (int)((3000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce == 23) {
						upgradePercent = (int)((2000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce == 24) {
						upgradePercent = (int)((1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
				} while(starforce != toToadItemStarforce && destroyCount < numberOfToadItems);
				break;
				
				case 1: // 10성까지 스타포스 1+1 
				do {
					int percent = (int)(Math.random() * 100000); 
					if (starforce < 3) {
						upgradePercent = (int)(((95 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch); 
						if (percent < upgradePercent) {
							starforce++;
							starforce++; 
						} 
					} else if (starforce < 10) {
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
							starforce++;
						} 
					} else if (starforce == 10) {
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
							starforce++;
							variableStarforce = 0;
						} 
					} else if (starforce < 12) { 
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else {
							starforce--;
							variableStarforce--;
							if (variableStarforce == -2) { 
								starforce++;
								starforce++; 
								variableStarforce = 0;
							}
						}
					} else if (starforce == 12) {
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce == 13) {
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce == 14) {
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce == 15) {
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else if (percent < upgradePercent + destroyPercent[3]) {
							starforce = 0;
							destroyCount++;
						}
					} else if (starforce == 16) {
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce == 17) {
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce < 20) {
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce == 20) {
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else if (percent < upgradePercent + destroyPercent[6]) {
							starforce = 0;
							destroyCount++;
						}
					} else if (starforce == 21) {
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce == 22) {
						upgradePercent = (int)((3000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce == 23) {
						upgradePercent = (int)((2000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce == 24) {
						upgradePercent = (int)((1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
				} while(starforce < toToadItemStarforce && destroyCount < numberOfToadItems);
				break;

				case 2: // 5의배수 100퍼센트 강화 성공 (15성 까지)
				do {
					int percent = (int)(Math.random() * 100000); 
					if (starforce < 3) {
						upgradePercent = (int)(((95 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch); 
						if (percent < upgradePercent) {
							starforce++;
						} 
					} else if (starforce < 10) {
						if (starforce == 5) { 
							upgradePercent = 100000;
						}else {
							upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						}
						if (percent < upgradePercent) {
							starforce++;
						} 
					} else if (starforce == 10) { 
						starforce++;
						variableStarforce = 0;
					} else if (starforce < 12) { 
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else {
							starforce--;
							variableStarforce--;
							if (variableStarforce == -2) { 
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce == 12) {
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce == 13) {
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce == 14) {
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce == 15) { 
						starforce++;
						variableStarforce = 0;
					} else if (starforce == 16) {
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce == 17) {
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce < 20) {
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce == 20) {
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else if (percent < upgradePercent + destroyPercent[6]) {
							starforce = 0;
							destroyCount++;
						}
					} else if (starforce == 21) {
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce == 22) {
						upgradePercent = (int)((3000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce == 23) {
						upgradePercent = (int)((2000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforce++;
								variableStarforce = 0;
							}
						}
					} else if (starforce == 24) {
						upgradePercent = (int)((1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
				} while(starforce != toToadItemStarforce && destroyCount < numberOfToadItems);
				break;
			}
		}
}


