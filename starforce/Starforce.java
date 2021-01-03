package project.starforce;

import java.util.*;

abstract class Item {

    // 인스턴스 변수
    int level;
    int starforce;
    int upgradeCount = 0;
    int destroyCount = 0;
    long sumUpgradePrice = 0;
    long starforcePrice = 0;
    int upgradePercent = 0;
    byte variableStarforce = 0;
	int chanceTime = 0;
	byte succededCatch;
    int sumOfStarforceCatch; // 스타포스캐치 합연산
    float multipleOfStarforceCatch; // 스타포스캐치 곱연산

    // 생성자
    Item(int level, int fromStarforce, byte succededCatch){
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
    static String sumUpgradePriceMinMax(Item[] itemArr, ResultInfo ri){
		ri.minSumUpgradePrice = itemArr[0].sumUpgradePrice;
		ri.maxSumUpgradePrice = itemArr[0].sumUpgradePrice;
		for (int i = 1; i < itemArr.length; i++){
			if (ri.minSumUpgradePrice > itemArr[i].sumUpgradePrice){
				ri.minSumUpgradePrice = itemArr[i].sumUpgradePrice;
			} else if (ri.maxSumUpgradePrice < itemArr[i].sumUpgradePrice){
				ri.maxSumUpgradePrice = itemArr[i].sumUpgradePrice;
			}
		}
		return itemArr.length + "개의 아이템 중 스타포스 강화 최소비용은 " + ri.minSumUpgradePrice + "00메소 입니다."
				+ "\n" + itemArr.length + "개의 아이템 중 스타포스 강화 최대비용은 " + ri.maxSumUpgradePrice + "00메소 입니다.";
    }
    
    static String averageSumUpgradePrice(Item[] itemArr, ResultInfo ri) {
		long totalsum = 0;
		for (int i = 0; i < itemArr.length; i++) {
			totalsum += itemArr[i].sumUpgradePrice;
			}
		ri.averageSumUpgradePrice = totalsum/itemArr.length;
		return itemArr.length + "개의 아이템의 평균적인 스타포스 강화 비용은 " + ri.averageSumUpgradePrice + "00메소 입니다.";
	}
	
	static String upgradeCountMinMax(Item[] itemArr, ResultInfo ri){
		ri.minUpgradeCount = itemArr[0].upgradeCount;
		ri.maxUpgradeCount = itemArr[0].upgradeCount;
		for (int i = 1; i < itemArr.length; i++){
			if (ri.minUpgradeCount > itemArr[i].upgradeCount){
				ri.minUpgradeCount = itemArr[i].upgradeCount;
			} else if (ri.maxUpgradeCount < itemArr[i].upgradeCount){
				ri.maxUpgradeCount = itemArr[i].upgradeCount;
			}
		}
		return itemArr.length + "개의 아이템 중 가장 적은 강화 횟수는 " + ri.minUpgradeCount + "번입니다."
				+ "\n" + itemArr.length + "개의 아이템 중 가장 많은 강화 횟수는 " + ri.maxUpgradeCount + "번입니다.";
    }
    
    static String averageUpgradeCount(Item[] itemArr, ResultInfo ri) { 
		long totalsum = 0;
		for (int i = 0; i < itemArr.length; i++) {
			totalsum += itemArr[i].upgradeCount;
		}
		ri.averageUpgradeCount = totalsum/itemArr.length;
		return itemArr.length + "개의 아이템의 평균적인 강화 횟수는 " + ri.averageUpgradeCount + "번입니다.";
	}

	static String destroyCountMinMax(Item[] itemArr, ResultInfo ri){
		ri.minDestroyCount = itemArr[0].destroyCount;
		ri.maxDestroyCount = itemArr[0].destroyCount;
		for (int i = 1; i < itemArr.length; i++){
			if (ri.minDestroyCount > itemArr[i].destroyCount){
				ri.minDestroyCount = itemArr[i].destroyCount;
			} else if (ri.maxDestroyCount < itemArr[i].destroyCount){
				ri.maxDestroyCount = itemArr[i].destroyCount;
			}
		}
		return itemArr.length + "개의 아이템 중 가장 적은 파괴 횟수는 " + ri.minDestroyCount + "번입니다."
				+ "\n" + itemArr.length + "개의 아이템 중 가장 많은 파괴 횟수는 " + ri.maxDestroyCount + "번입니다.";
    }
    
    static String averageDestroyCount(Item[] itemArr, ResultInfo ri) { 
		long totalsum = 0;
		for (int i = 0; i < itemArr.length; i++) {
			totalsum += itemArr[i].destroyCount;
		}
		ri.averageDestroyCount = totalsum/itemArr.length;
		return itemArr.length + "개의 아이템의 평균적인 파괴 횟수는 " + ri.averageDestroyCount + "번입니다.";
	}

	static String chanceTimeCountMinMax(Item[] itemArr, ResultInfo ri){
		ri.minChanceTimeCount = itemArr[0].chanceTime;
		ri.maxChanceTimeCount = itemArr[0].chanceTime;
		for (int i = 1; i < itemArr.length; i++){
			if (ri.minChanceTimeCount > itemArr[i].chanceTime){
				ri.minChanceTimeCount = itemArr[i].chanceTime;
			} else if (ri.maxChanceTimeCount < itemArr[i].chanceTime){
				ri.maxChanceTimeCount = itemArr[i].chanceTime;
			}
		}
		return itemArr.length + "개의 아이템 중 가장 적은 찬스타임 횟수는 " + ri.minChanceTimeCount + "번입니다."
				+ "\n" + itemArr.length + "개의 아이템 중 가장 많은 찬스타임 횟수는 " + ri.maxChanceTimeCount + "번입니다.";
    }
    
    static String averageChanceTimeCount(Item[] itemArr, ResultInfo ri) { 
		long totalsum = 0;
		for (int i = 0; i < itemArr.length; i++) {
			totalsum += itemArr[i].chanceTime;
		}
		ri.averageChanceTimeCount = totalsum/itemArr.length;
		return itemArr.length + "개의 아이템의 평균적인 찬스타임 횟수는 " + ri.averageChanceTimeCount + "번입니다.";
    }
    
    static String starCatchToString(byte succededCatch) { // 스타캐치 출력 편의성
		
		if(succededCatch == 0){
			return "x";
		} else if (succededCatch == 1){
			return "합연산(4.5%p)";
		} else {
			return "곱연산(1.045배)";
        }
    }

}

class NormalItem extends Item {

    // 인스턴스 변수
    int[] destroyPercent = {600, 1300, 1400, 2100, 2100, 2800, 7000, 19400, 29400, 39600, 0}; 
	boolean ignoreDestroy; // 파괴방지 여부
	int maxStarforce;
	byte mapleEvent = 0;
	boolean roomOfPC;
	float discountPCRoom = 0f;
	byte gradeOfMVP;
	float[] discountMVPGrade = {0f, 0.03f, 0.05f, 0.1f}; //실버 : 3%, 골드 : 5%, 다이아,레드 : 10%
	float discountMVP = 0f;
	int toadToStarforce = 0;
	boolean toadHammer = false;
	boolean toadIgnoreDestroy;
    
    // 생성자
    NormalItem(int level, int fromStarforce, byte succededCatch){
        super(level, fromStarforce, succededCatch);
        this.maxStarforce = maxItemStarforce(level);
    }

    NormalItem(int level, int fromStarforce, byte succededCatch, byte mapleEvent, boolean discountPCRoom, byte discountMVPGrade) {
        this(level, fromStarforce, succededCatch);
		this.mapleEvent = mapleEvent;
		roomOfPC = discountPCRoom;
		gradeOfMVP = discountMVPGrade;
        if (discountPCRoom == true) {
			this.discountPCRoom = 0.05f;
		}
		if (discountMVPGrade == 1) {
			this.discountMVP = this.discountMVPGrade[1];
		} else if (discountMVPGrade == 2) {
			this.discountMVP = this.discountMVPGrade[2];
		} else if (discountMVPGrade == 3) {
			this.discountMVP = this.discountMVPGrade[3];
		}
    }

    NormalItem(int level, int fromStarforce, byte succededCatch, byte mapleEvent, boolean discountPCRoom, byte discountMVPGrade , boolean ignoreDestroy) {
        this(level, fromStarforce, succededCatch, mapleEvent, discountPCRoom, discountMVPGrade);
        this.ignoreDestroy = ignoreDestroy;
		if (ignoreDestroy == true) {
			for (int i = 0; i < 4; i++) {
				destroyPercent[i] = destroyPercent[10];
			}
        }
	}

	NormalItem(int level, int fromStarforce, byte succededCatch, byte mapleEvent, boolean discountPCRoom, byte discountMVPGrade , boolean ignoreDestroy, int toadToStarforce){
		this(level, fromStarforce, succededCatch, mapleEvent, discountPCRoom, discountMVPGrade, ignoreDestroy);
		if (0 < toadToStarforce && toadToStarforce <= 25){
			toadHammer = true;
			this.toadToStarforce = toadToStarforce;
		} 
	}

    // 메서드
    public int maxItemStarforce(int level){
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

    static String booleanToString(boolean booleanSelect){ // boolean형태 출력 편의성
		if (booleanSelect == true){
			return "o";
		}
		return "x";
	}
	
	static String MVPGradeToString(byte discountMVPGrade){ // MVP등급 출력 편의성
		if (discountMVPGrade == 0){
			return "x";
		} else if (discountMVPGrade == 1){
			return "실버 (3%)";
		} else if (discountMVPGrade == 2){
			return "골드 (5%)";
		} else {
			return "다이아 or 레드 (10%)";
		}
	}

	static String mapleEventToString(byte mapleEvent){ // 이벤트 출력 편의성
		if (mapleEvent == 0){
			return "x";
		} else if (mapleEvent == 1) {
			return "30퍼센트 할인 이벤트 (전구간)";
		} else if (mapleEvent == 2){
			return "스타포스 1+1 이벤트 (10성까지)";
		} else {
			return "5의 배수의 스타포스 100퍼센트 성공 (15성까지)";
		}
    }

    void fromToadItemToNormalItem(ToadItem ti){
        this.starforce = --ti.starforce;
        this.upgradeCount += ti.upgradeCount;
        this.destroyCount += ti.destroyCount;
        this.sumUpgradePrice += ti.sumUpgradePrice;
        this.chanceTime += ti.chanceTime;
    }
    
    static NormalItem goTo(NormalItem ni, int toStarforce, boolean toadIgnoreDestroy) {
		ni.toadIgnoreDestroy = toadIgnoreDestroy;
        if (ni.starforce >= toStarforce || ni.starforce < 0 || ni.maxStarforce <= toStarforce || ni.maxStarforce <= ni.starforce) { 
			throw new RuntimeException("유효하지 않은 강화입니다.");
		} else {
			switch(ni.mapleEvent){
				case 0: // 이벤트 x
				do {
					if (ni.toadHammer == true && ni.starforce == 0){
						ToadItem ti = new ToadItem(ni.level, ni.starforce, ni.succededCatch, ni.mapleEvent, ni.roomOfPC, ni.gradeOfMVP, ni.toadIgnoreDestroy);
						ti.goTo(ni.toadToStarforce);
						ni.fromToadItemToNormalItem(ti);
					} else{
						ni.upgradeCount++;
						int percent = (int)(Math.random() * 100000);
						if (ni.starforce < 3) {
							ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * (ni.starforce + 1)/25))*(1 - ni.discountMVP - ni.discountPCRoom)/100);
							ni.upgradePercent = (int)(((95 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch); 
							ni.sumUpgradePrice += ni.starforcePrice;
							if (percent < ni.upgradePercent) {
								ni.starforce++;
							} 
						} else if (ni.starforce < 10) {
							ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * (ni.starforce) + 1)/25)*(1 - ni.discountMVP - ni.discountPCRoom)/100);
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
							if (percent < ni.upgradePercent) {
								ni.starforce++;
							} 
						} else if (ni.starforce == 10) {
							ni.starforcePrice = (long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							}
						} else if (ni.starforce < 12) { 
							ni.starforcePrice = (long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) { 
									ni.starforcePrice = (long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 12) { 
							if (ni.ignoreDestroy == true) {
								ni.starforcePrice = (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)/100
												+(long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							} else {
								ni.starforcePrice = (long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							}
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 13) {
							if (ni.ignoreDestroy == true) {
								ni.starforcePrice = (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)/100
												+(long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							} else {
								ni.starforcePrice = (long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							}
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 14) {
							if (ni.ignoreDestroy == true) {
								ni.starforcePrice = (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)/100
												+(long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							} else {
								ni.starforcePrice = (long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							}
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 15) {
							if (ni.ignoreDestroy == true) {
								ni.starforcePrice = (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)/100
												+(long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)*(1 - ni.discountMVP - ni.discountPCRoom))/100;;
							} else {
								ni.starforcePrice = (long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)*(1 - ni.discountMVP - ni.discountPCRoom))/100;;
							}
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[3]) {
								ni.starforce = 0;
								ni.destroyCount++;
							}
						} else if (ni.starforce == 16) {
							if (ni.ignoreDestroy == true) {
								ni.starforcePrice = (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)/100
												+(long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							} else {
								ni.starforcePrice = (long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							}
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 17) {
							ni.starforcePrice = (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)/100;
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce < 20) {
							ni.starforcePrice = (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)/100;
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 20) {
							ni.starforcePrice = (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)/100;
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[6]) {
								ni.starforce = 0;
								ni.destroyCount++;
							}
						} else if (ni.starforce == 21) {
							ni.starforcePrice = (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)/100;
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 22) {
							ni.starforcePrice = (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)/100;
							ni.upgradePercent = (int)((3000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 23) {
							ni.starforcePrice = (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)/100;
							ni.upgradePercent = (int)((2000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 24) {
							ni.starforcePrice = (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)/100;
							ni.upgradePercent = (int)((1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
				} while(ni.starforce != toStarforce);
				ni.upgradeCount += ni.chanceTime;
				break;

				case 1: // 30 퍼센트 할인 (곱연산) 
				do {
					if (ni.toadHammer == true && ni.starforce == 0){
						ToadItem ti = new ToadItem(ni.level, ni.starforce, ni.succededCatch, ni.mapleEvent, ni.roomOfPC, ni.gradeOfMVP, toadIgnoreDestroy);
						ti.goTo(ni.toadToStarforce);
						ni.fromToadItemToNormalItem(ti);
					} else {
						ni.upgradeCount++;
						int percent = (int)(Math.random() * 100000); 
						if (ni.starforce < 3) {
							ni.starforcePrice = (long)((1000+ Math.pow((double)ni.level, 3.0) * (ni.starforce + 1)/25)*(1 - ni.discountMVP - ni.discountPCRoom)*0.7/100);
							ni.upgradePercent = (int)(((95 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch); 
							ni.sumUpgradePrice += ni.starforcePrice;
							if (percent < ni.upgradePercent) {
								ni.starforce++;
							}
						} else if (ni.starforce < 10) {
							ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * (ni.starforce) + 1)/25)*(1 - ni.discountMVP - ni.discountPCRoom)*0.7/100);
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
							if (percent < ni.upgradePercent) {
								ni.starforce++;
							}
						} else if (ni.starforce == 10) {
							ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							}
						} else if (ni.starforce < 12) { 
							ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) { 
									ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 12) { 
							if (ni.ignoreDestroy == true) {
								ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
							} else {
								ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
							}
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 13) {
							if (ni.ignoreDestroy == true) {
								ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
							} else {
								ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
							}
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 14) {
							if (ni.ignoreDestroy == true) {
								ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
							} else {
								ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
							}
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 15) {
							if (ni.ignoreDestroy == true) {
								ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
							} else {
								ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
							}
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[3]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} 
						} else if (ni.starforce == 16) {
							if (ni.ignoreDestroy == true) {
								ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
							} else {
								ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
							}
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 17) {
							ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*0.7)/100;
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce < 20) {
							ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*0.7)/100;
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*0.7)/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 20) {
							ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*0.7)/100;
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[6]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} 
						} else if (ni.starforce == 21) {
							ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*0.7)/100;
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*0.7)/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 22) {
							ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*0.7)/100;
							ni.upgradePercent = (int)((3000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*0.7)/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 23) {
							ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*0.7)/100;
							ni.upgradePercent = (int)((2000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*0.7)/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 24) {
							ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*0.7)/100;
							ni.upgradePercent = (int)((1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
				} while(ni.starforce != toStarforce);
				ni.upgradeCount += ni.chanceTime;
				break;

				case 2: // 10성까지 스타포스 1+1 
				do {
					if (ni.toadHammer == true && ni.starforce == 0){
						ToadItem ti = new ToadItem(ni.level, ni.starforce, ni.succededCatch, ni.mapleEvent, ni.roomOfPC, ni.gradeOfMVP, toadIgnoreDestroy);
						ti.goTo(ni.toadToStarforce);
						ni.fromToadItemToNormalItem(ti);
					} else {
						ni.upgradeCount++;
						int percent = (int)(Math.random() * 100000); 
						if (ni.starforce < 3) {
							ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * (ni.starforce + 1)/25))*(1 - ni.discountMVP - ni.discountPCRoom)/100);
							ni.upgradePercent = (int)(((95 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch); 
							ni.sumUpgradePrice += ni.starforcePrice;
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.starforce++; 
							} 
						} else if (ni.starforce < 10) {
							ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * (ni.starforce) + 1)/25)*(1 - ni.discountMVP - ni.discountPCRoom)/100);
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.starforce++;
							} 
						} else if (ni.starforce == 10) {
							ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.starforce++;
								ni.variableStarforce = 0;
							} 
						} else if (ni.starforce < 12) { 
							ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) { 
									ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.starforce++; 
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 12) { 
							// 
							if (ni.ignoreDestroy == true) {
								ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							} else {
								ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							}
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 13) {
							if (ni.ignoreDestroy == true) {
								ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							} else {
								ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							}
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 14) {
							if (ni.ignoreDestroy == true) {
								ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							} else {
								ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							}
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 15) {
							if (ni.ignoreDestroy == true) {
								ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom))/100;;
							} else {
								ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom))/100;;
							}
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[3]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} 
						} else if (ni.starforce == 16) {
							if (ni.ignoreDestroy == true) {
								ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							} else {
								ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							}
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 17) {
							ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce < 20) {
							ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 20) {
							ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[6]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} 
						} else if (ni.starforce == 21) {
							ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 22) {
							ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
							ni.upgradePercent = (int)((3000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 23) {
							ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
							ni.upgradePercent = (int)((2000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 24) {
							ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
							ni.upgradePercent = (int)((1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
				} while(ni.starforce < toStarforce);
				ni.upgradeCount += ni.chanceTime;
				break;

				case 3: // 5의배수 100퍼센트 강화 성공 (15성 까지)
				do {
					if (ni.toadHammer == true && ni.starforce == 0){
						ToadItem ti = new ToadItem(ni.level, ni.starforce, ni.succededCatch, ni.mapleEvent, ni.roomOfPC, ni.gradeOfMVP, toadIgnoreDestroy);
						ti.goTo(ni.toadToStarforce);
						ni.fromToadItemToNormalItem(ti);
					} else {
						ni.upgradeCount++;
						int percent = (int)(Math.random() * 100000); 
						if (ni.starforce < 3) {
							ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * (ni.starforce + 1)/25))*(1 - ni.discountMVP - ni.discountPCRoom)/100);
							ni.upgradePercent = (int)(((95 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch); 
							ni.sumUpgradePrice += ni.starforcePrice;
							if (percent < ni.upgradePercent) {
								ni.starforce++;
							} 
						} else if (ni.starforce < 10) {
							ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * (ni.starforce) + 1)/25)*(1 - ni.discountMVP - ni.discountPCRoom)/100);
							if (ni.starforce == 5) { 
								ni.upgradePercent = 100000;
							}else {
								ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							}
							ni.sumUpgradePrice += ni.starforcePrice;
							if (percent < ni.upgradePercent) {
								ni.starforce++;
							} 
						} else if (ni.starforce == 10) { 
							ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
								ni.sumUpgradePrice += ni.starforcePrice;
								ni.starforce++;
								ni.variableStarforce = 0;
						} else if (ni.starforce < 12) { 
							ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) { 
									ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 12) { 
							// 
							if (ni.ignoreDestroy == true) {
								ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							} else {
								ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							}
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 13) {
							if (ni.ignoreDestroy == true) {
								ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							} else {
								ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							}
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 14) {
							if (ni.ignoreDestroy == true) {
								ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							} else {
								ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							}
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 15) { 
							ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							ni.sumUpgradePrice += ni.starforcePrice;
							ni.starforce++;
							ni.variableStarforce = 0;
						} else if (ni.starforce == 16) {
							if (ni.ignoreDestroy == true) {
								ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							} else {
								ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							}
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 17) {
							ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce < 20) {
							ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 20) {
							ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[6]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} 
						} else if (ni.starforce == 21) {
							ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 22) {
							ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
							ni.upgradePercent = (int)((3000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 23) {
							ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
							ni.upgradePercent = (int)((2000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
									ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
									ni.sumUpgradePrice += ni.starforcePrice;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 24) {
							ni.starforcePrice = (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
							ni.upgradePercent = (int)((1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							ni.sumUpgradePrice += ni.starforcePrice;
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
				} while(ni.starforce != toStarforce);
				ni.upgradeCount += ni.chanceTime;
				break;
			}
			return ni;
		}
    }

}

class ToadItem extends NormalItem{

    // 인스턴스 변수
	int toadToStarforce;

    // 생성자
    ToadItem(int level, int fromStarforce, byte succededCatch, byte mapleEvent, boolean discountPCRoom, byte discountMVPGrade, boolean toadIgnoreDestroy){
        super(level, fromStarforce, succededCatch, mapleEvent, discountPCRoom, discountMVPGrade);
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
    void goTo(int toToadItemStarforce){
        if (starforce >= toToadItemStarforce || starforce < 0 || maxStarforce <= toToadItemStarforce || maxStarforce <= starforce) { 
            throw new RuntimeException("유효하지 않은 강화입니다."); 
		} else if (level > 150) {
            throw new RuntimeException("토드시스템은 150제 토드아이템까지 가능합니다.");
        } else {
			switch(mapleEvent){
				case 0: // 이벤트 x
				do {
					upgradeCount++;
					int percent = (int)(Math.random() * 100000);
					if (starforce < 3) {
						starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * (starforce + 1)/25))*(1 - discountMVP - discountPCRoom)/100);
						upgradePercent = (int)(((95 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch); 
						sumUpgradePrice += starforcePrice;
						if (percent < upgradePercent) {
							starforce++;
						} 
					} else if (starforce < 10) {
						starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * (starforce) + 1)/25)*(1 - discountMVP - discountPCRoom)/100);
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						sumUpgradePrice += starforcePrice;
						if (percent < upgradePercent) {
							starforce++;
						} 
					} else if (starforce == 10) {
						starforcePrice = (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)*(1 - discountMVP - discountPCRoom))/100;
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						sumUpgradePrice += starforcePrice;
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						}
					} else if (starforce < 12) { 
						starforcePrice = (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)*(1 - discountMVP - discountPCRoom))/100;
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						sumUpgradePrice += starforcePrice;
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else {
							starforce--;
							variableStarforce--;
							if (variableStarforce == -2) { 
								starforcePrice = (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)*(1 - discountMVP - discountPCRoom))/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 12) { 
						if (ignoreDestroy == true) {
							starforcePrice = (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)/100
											+(long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)*(1 - discountMVP - discountPCRoom))/100;
						} else {
							starforcePrice = (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)*(1 - discountMVP - discountPCRoom))/100;
						}
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)*(1 - discountMVP - discountPCRoom))/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 13) {
						if (ignoreDestroy == true) {
							starforcePrice = (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)/100
											+(long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)*(1 - discountMVP - discountPCRoom))/100;
						} else {
							starforcePrice = (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)*(1 - discountMVP - discountPCRoom))/100;
						}
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)*(1 - discountMVP - discountPCRoom))/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 14) {
						if (ignoreDestroy == true) {
							starforcePrice = (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)/100
											+(long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)*(1 - discountMVP - discountPCRoom))/100;
						} else {
							starforcePrice = (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)*(1 - discountMVP - discountPCRoom))/100;
						}
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)*(1 - discountMVP - discountPCRoom))/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 15) {
						if (ignoreDestroy == true) {
							starforcePrice = (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)/100
											+(long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)*(1 - discountMVP - discountPCRoom))/100;;
						} else {
							starforcePrice = (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)*(1 - discountMVP - discountPCRoom))/100;;
						}
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						sumUpgradePrice += starforcePrice;
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else if (percent < upgradePercent + destroyPercent[3]) {
							starforce = 0;
							destroyCount++;
						}
					} else if (starforce == 16) {
						if (ignoreDestroy == true) {
							starforcePrice = (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)/100
											+(long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)*(1 - discountMVP - discountPCRoom))/100;
						} else {
							starforcePrice = (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)*(1 - discountMVP - discountPCRoom))/100;
						}
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)*(1 - discountMVP - discountPCRoom))/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 17) {
						starforcePrice = (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)/100;
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)*(1 - discountMVP - discountPCRoom))/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce < 20) {
						starforcePrice = (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)/100;
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 20) {
						starforcePrice = (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)/100;
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						sumUpgradePrice += starforcePrice;
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else if (percent < upgradePercent + destroyPercent[6]) {
							starforce = 0;
							destroyCount++;
						}
					} else if (starforce == 21) {
						starforcePrice = (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)/100;
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 22) {
						starforcePrice = (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)/100;
						upgradePercent = (int)((3000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 23) {
						starforcePrice = (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)/100;
						upgradePercent = (int)((2000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 24) {
						starforcePrice = (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)/100;
						upgradePercent = (int)((1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
				} while(starforce != toToadItemStarforce);
				upgradeCount += chanceTime; 
				break;

				case 1: // 30 퍼센트 할인 (곱연산) 
				do {
					upgradeCount++;
					int percent = (int)(Math.random() * 100000); 
					if (starforce < 3) {
						starforcePrice = (long)((1000+ Math.pow((double)level, 3.0) * (starforce + 1)/25)*(1 - discountMVP - discountPCRoom)*0.7/100);
						upgradePercent = (int)(((95 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch); 
						sumUpgradePrice += starforcePrice;
						if (percent < upgradePercent) {
							starforce++;
						}
					} else if (starforce < 10) {
						starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * (starforce) + 1)/25)*(1 - discountMVP - discountPCRoom)*0.7/100);
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						sumUpgradePrice += starforcePrice;
						if (percent < upgradePercent) {
							starforce++;
						}
					} else if (starforce == 10) {
						starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom)*0.7)/100;
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						sumUpgradePrice += starforcePrice;
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						}
					} else if (starforce < 12) { 
						starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom)*0.7)/100;
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						sumUpgradePrice += starforcePrice;
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else {
							starforce--;
							variableStarforce--;
							if (variableStarforce == -2) { 
								starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom)*0.7)/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 12) { 
						if (ignoreDestroy == true) {
							starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom)*0.7)/100;
						} else {
							starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom)*0.7)/100;
						}
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom)*0.7)/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 13) {
						if (ignoreDestroy == true) {
							starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom)*0.7)/100;
						} else {
							starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom)*0.7)/100;
						}
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom)*0.7)/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 14) {
						if (ignoreDestroy == true) {
							starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom)*0.7)/100;
						} else {
							starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom)*0.7)/100;
						}
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom)*0.7)/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 15) {
						if (ignoreDestroy == true) {
							starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom)*0.7)/100;
						} else {
							starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom)*0.7)/100;
						}
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						sumUpgradePrice += starforcePrice;
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else if (percent < upgradePercent + destroyPercent[3]) {
							starforce = 0;
							destroyCount++;
						} 
					} else if (starforce == 16) {
						if (ignoreDestroy == true) {
							starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom)*0.7)/100;
						} else {
							starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom)*0.7)/100;
						}
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom)*0.7)/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 17) {
						starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*0.7)/100;
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom)*0.7)/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce < 20) {
						starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*0.7)/100;
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*0.7)/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 20) {
						starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*0.7)/100;
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						sumUpgradePrice += starforcePrice;
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else if (percent < upgradePercent + destroyPercent[6]) {
							starforce = 0;
							destroyCount++;
						} 
					} else if (starforce == 21) {
						starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*0.7)/100;
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*0.7)/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 22) {
						starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*0.7)/100;
						upgradePercent = (int)((3000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*0.7)/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 23) {
						starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*0.7)/100;
						upgradePercent = (int)((2000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*0.7)/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 24) {
						starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*0.7)/100;
						upgradePercent = (int)((1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
				} while(starforce != toToadItemStarforce);
				upgradeCount += chanceTime;
				break;

				case 2: // 10성까지 스타포스 1+1 
				do {
					upgradeCount++;
					int percent = (int)(Math.random() * 100000); 
					if (starforce < 3) {
						starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * (starforce + 1)/25))*(1 - discountMVP - discountPCRoom)/100);
						upgradePercent = (int)(((95 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch); 
						sumUpgradePrice += starforcePrice;
						if (percent < upgradePercent) {
							starforce++;
							starforce++; 
						} 
					} else if (starforce < 10) {
						starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * (starforce) + 1)/25)*(1 - discountMVP - discountPCRoom)/100);
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						sumUpgradePrice += starforcePrice;
						if (percent < upgradePercent) {
							starforce++;
							starforce++;
						} 
					} else if (starforce == 10) {
						starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						sumUpgradePrice += starforcePrice;
						if (percent < upgradePercent) {
							starforce++;
							starforce++;
							variableStarforce = 0;
						} 
					} else if (starforce < 12) { 
						starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						sumUpgradePrice += starforcePrice;
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else {
							starforce--;
							variableStarforce--;
							if (variableStarforce == -2) { 
								starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								starforce++; 
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 12) { 
						// 
						if (ignoreDestroy == true) {
							starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						} else {
							starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						}
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 13) {
						if (ignoreDestroy == true) {
							starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						} else {
							starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						}
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 14) {
						if (ignoreDestroy == true) {
							starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						} else {
							starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						}
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 15) {
						if (ignoreDestroy == true) {
							starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom))/100;;
						} else {
							starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom))/100;;
						}
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						sumUpgradePrice += starforcePrice;
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else if (percent < upgradePercent + destroyPercent[3]) {
							starforce = 0;
							destroyCount++;
						} 
					} else if (starforce == 16) {
						if (ignoreDestroy == true) {
							starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom))/100;
						} else {
							starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom))/100;
						}
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom))/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 17) {
						starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom))/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce < 20) {
						starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 20) {
						starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						sumUpgradePrice += starforcePrice;
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else if (percent < upgradePercent + destroyPercent[6]) {
							starforce = 0;
							destroyCount++;
						} 
					} else if (starforce == 21) {
						starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 22) {
						starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
						upgradePercent = (int)((3000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 23) {
						starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
						upgradePercent = (int)((2000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 24) {
						starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
						upgradePercent = (int)((1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
				} while(starforce < toToadItemStarforce);
				upgradeCount += chanceTime;
				break;

				case 3: // 5의배수 100퍼센트 강화 성공 (15성 까지)
				do {
					upgradeCount++;
					int percent = (int)(Math.random() * 100000); 
					if (starforce < 3) {
						starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * (starforce + 1)/25))*(1 - discountMVP - discountPCRoom)/100);
						upgradePercent = (int)(((95 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch); 
						sumUpgradePrice += starforcePrice;
						if (percent < upgradePercent) {
							starforce++;
						} 
					} else if (starforce < 10) {
						starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * (starforce) + 1)/25)*(1 - discountMVP - discountPCRoom)/100);
						if (starforce == 5) { 
							upgradePercent = 100000;
						}else {
							upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						}
						sumUpgradePrice += starforcePrice;
						if (percent < upgradePercent) {
							starforce++;
						} 
					} else if (starforce == 10) { 
						starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
							sumUpgradePrice += starforcePrice;
							starforce++;
							variableStarforce = 0;
					} else if (starforce < 12) { 
						starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						sumUpgradePrice += starforcePrice;
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else {
							starforce--;
							variableStarforce--;
							if (variableStarforce == -2) { 
								starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 12) { 
						// 
						if (ignoreDestroy == true) {
							starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						} else {
							starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						}
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 13) {
						if (ignoreDestroy == true) {
							starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						} else {
							starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						}
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 14) {
						if (ignoreDestroy == true) {
							starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						} else {
							starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						}
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 15) { 
						starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom))/100;
						sumUpgradePrice += starforcePrice;
						starforce++;
						variableStarforce = 0;
					} else if (starforce == 16) {
						if (ignoreDestroy == true) {
							starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom))/100;
						} else {
							starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom))/100;
						}
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom))/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 17) {
						starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom))/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce < 20) {
						starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 20) {
						starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						sumUpgradePrice += starforcePrice;
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else if (percent < upgradePercent + destroyPercent[6]) {
							starforce = 0;
							destroyCount++;
						} 
					} else if (starforce == 21) {
						starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 22) {
						starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
						upgradePercent = (int)((3000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 23) {
						starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
						upgradePercent = (int)((2000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
								starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 24) {
						starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
						upgradePercent = (int)((1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
				} while(starforce != toToadItemStarforce);
				upgradeCount += chanceTime;
				break;
			}
		}
    }
}

class SuperiorItem extends Item {

    // 인스턴스 변수
    long[] pricePerLevel = {59560,0,185079,0,0,558322}; // 레벨당 강화 비용 (헬리시움, 노바, 타일런트 순)
    int[] destroyPercent = {1800, 3000, 4200, 6000, 9500, 13000, 16300, 48500, 49000, 49500}; 
    int maxStarforce; // 현재 레벨에서 가능한 최대 스타포스
    
    // 생성자
    SuperiorItem(int level, int fromStarforce, byte succededCatch){
        super(level, fromStarforce, succededCatch);
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

    // 메서드
    void goTo(int toStarforce) {
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
}

class ResultInfo{

    // 인스턴스 변수
    static int count = 0;
    int serialNo;
	String item = "";
	int level;
	int fromStarforce;
	int toStarforce; // 도달 스타포스
	byte succededCatch; // 스타캐치 여부 (0 : 스타포스캐치x, 1 : 합연산, 2 : 곱연산)
	boolean ignoreDestroy; // 파괴방지 여부
	int countItem; // 아이템의 개수 (표본)
	boolean discountPCRoom; // PC방 할인
	byte discountMVPGrade; //MVP 등급 할인 (0 : x, 1 : 실버, 2 : 골드, 3 : 다이아, 레드)
	byte mapleEvent; // 0 : x, 1 : 30퍼 할인, 2: 1+1, 3. 15 16 100퍼
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
	boolean toadHammer;
	int toadToStarforce;
	boolean toadIgnoreDestroy;
    
    // 인스턴스 초기화 블럭
    {
        ++count;
        serialNo = count;
    }

	// 생성자
	ResultInfo() {

	}

	// 메서드
	void savedProperty(Item[] itemArr){
		if (itemArr[0] instanceof NormalItem){
			NormalItem ni = (NormalItem)itemArr[0];
			this.item = "노말";
			this.level = ni.level;
			this.toStarforce = ni.starforce; // 도달 스타포스
			this.ignoreDestroy = ni.ignoreDestroy; // 파괴방지 여부
			this.countItem = itemArr.length; // 아이템의 개수 (표본)
			this.mapleEvent = ni.mapleEvent; // 0 : x, 1 : 30퍼 할인, 2: 1+1, 3. 15 16 100퍼
			this.succededCatch = ni.succededCatch;
			discountPCRoom = ni.roomOfPC;
			discountMVPGrade = ni.gradeOfMVP;
			this.toadHammer = ni.toadHammer;
			this.toadToStarforce = ni.toadToStarforce;
			this.toadIgnoreDestroy = ni.toadIgnoreDestroy;
		} else if (itemArr[0] instanceof SuperiorItem){
			SuperiorItem si = (SuperiorItem)itemArr[0];
			this.item = "슈페리얼";
			this.level = si.level;
			this.toStarforce = si.starforce;
			this.countItem = itemArr.length;
		}
	}

	void printProperty(){
		System.out.println("==== " + this.level + "제 아이템의 " + this.countItem + "개 " + this.fromStarforce + "성 -> " + this.toStarforce + "성까지의 강화 ====");
		System.out.print("추가 설명 : 스타캐치 " + Item.starCatchToString(this.succededCatch));
		if ((this.item).equals("노말")){
			System.out.println(", 파괴방지 : " + NormalItem.booleanToString(this.ignoreDestroy)
				+ ", PC방 할인 : " + NormalItem.booleanToString(this.discountPCRoom) + ", MVP 등급 : " + NormalItem.MVPGradeToString(this.discountMVPGrade));
			System.out.print("토드여부 : " + NormalItem.booleanToString(this.toadHammer)); 
			if (this.toadHammer == true) {
				System.out.print(" 토드템 0성 -> " + this.toadToStarforce + "성, 토드템 파괴방지 : " + NormalItem.booleanToString(this.toadIgnoreDestroy));
			}
		}
		System.out.println();
		if ((this.item).equals("노말")){
			System.out.println("메이플 이벤트 적용 : " + NormalItem.mapleEventToString(this.mapleEvent));
		}
		System.out.println(this.countItem + "개의 아이템 중 스타포스 강화 최소비용은 " + this.minSumUpgradePrice + "00메소 입니다.");
		System.out.println(this.countItem + "개의 아이템 중 스타포스 강화 최대비용은 " + this.maxSumUpgradePrice + "00메소 입니다.");
		System.out.println(this.countItem + "개의 아이템의 평균적인 스타포스 강화 비용은 " + this.averageSumUpgradePrice + "00메소 입니다.");
		System.out.println(this.countItem + "개의 아이템 중 가장 적은 강화 횟수는 " + this.minUpgradeCount + "번입니다.");
		System.out.println(this.countItem + "개의 아이템 중 가장 많은 강화 횟수는 " + this.maxUpgradeCount + "번입니다.");
		System.out.println(this.countItem + "개의 아이템의 평균적인 강화 횟수는 " + this.averageUpgradeCount + "번입니다.");
		System.out.println(this.countItem + "개의 아이템 중 가장 적은 파괴 횟수는 " + this.minDestroyCount + "번입니다.");
		System.out.println(this.countItem + "개의 아이템 중 가장 많은 파괴 횟수는 " + this.maxDestroyCount + "번입니다.");
		System.out.println(this.countItem + "개의 아이템의 평균적인 파괴 횟수는 " + this.averageDestroyCount + "번입니다.");
		System.out.println(this.countItem + "개의 아이템 중 가장 적은 찬스타임 횟수는 " + this.minChanceTimeCount + "번입니다.");
		System.out.println(this.countItem + "개의 아이템 중 가장 많은 찬스타임 횟수는 " + this.maxChanceTimeCount + "번입니다.");
		System.out.println(this.countItem + "개의 아이템의 평균적인 찬스타임 횟수는 " + this.averageChanceTimeCount + "번입니다.");
	}

	public String toString(){
		if (this.item.equals("노말")){
			if (this.toadHammer == true){
				return this.serialNo + ") : " + this.countItem + "개 " + this.level + "제 " + this.item + " 아이템 " + this.fromStarforce + "성 -> " + this.toStarforce + "성" + "\n"
				 + "스타캐치 : " + Item.starCatchToString(this.succededCatch) + ", 파괴방지 : " + NormalItem.booleanToString(this.ignoreDestroy)
				 + ", PC방 할인 : " + NormalItem.booleanToString(this.discountPCRoom) + ", MVP 등급 : " + NormalItem.MVPGradeToString(this.discountMVPGrade) + "\n"
				 + ", 토드 여부 : " + NormalItem.booleanToString(this.toadHammer) + ", 토드템 0성 -> " + this.toadToStarforce + "성, 토드템 파괴방지 : " + NormalItem.booleanToString(this.toadIgnoreDestroy) + "\n"
				 + "메이플 이벤트 적용 : " + NormalItem.mapleEventToString(this.mapleEvent) + "\n\n";
			}
			return this.serialNo + ") : " + this.countItem + "개 " + this.level + "제 " + this.item + " 아이템 " + this.fromStarforce + "성 -> " + this.toStarforce + "성" + "\n"
			 + "스타캐치 : " + Item.starCatchToString(this.succededCatch) + ", 파괴방지 : " + NormalItem.booleanToString(this.ignoreDestroy)
			 + ", PC방 할인 : " + NormalItem.booleanToString(this.discountPCRoom) + ", MVP 등급 : " + NormalItem.MVPGradeToString(this.discountMVPGrade) + "\n"
			 + "메이플 이벤트 적용 : " + NormalItem.mapleEventToString(this.mapleEvent) + "\n\n";
		}
		return this.serialNo + ") : " + this.countItem + "개 " + this.level + "제 " + this.item + " 아이템 " + this.fromStarforce + "성 -> " + this.toStarforce + "성" + "\n"
		 + "스타캐치 : " + Item.starCatchToString(this.succededCatch) + "\n\n";
	}
	
}

public class Starforce {
    public static void main(String[] args){
        int menu = 0;
        ArrayList<ResultInfo> list = new ArrayList<ResultInfo>();
        Scanner s = new Scanner(System.in);
        while(true){
            System.out.println("스타포스 시뮬레이터");
            System.out.println("1. 노말 아이템 강화");
            System.out.println("2. 슈페리얼 아이템 강화");
            System.out.println("3. 최근 시뮬레이션 목록");
            System.out.println("원하는 메뉴(1~3)를 선택하세요. (종료 : -1)");
            menu = Integer.parseInt(s.nextLine());
            if (menu == -1){
                System.out.println("스타포스 시뮬레이터를 종료합니다.");
                break;
            } else if (!(1 <= menu && menu <= 3)){
                System.out.println("메뉴를 잘못 선택하셨습니다. (종료 : -1)");
                continue;
            }
            switch(menu){
				case 1:
				while(true){
					System.out.println("노말 아이템 강화 메뉴입니다.");
 	            	System.out.println("양식 : 아이템개수 목표스타포스 레벨 시작스타포스 스타캐치여부");
   	            	System.out.println("참고) 스타캐치여부 : 0: 스타캐치X, 1: 합연산(4.5%p), 2: 곱연산(1.045배)");
   	            	System.out.println("아이템의 정보를 입력하세요.");
   	        	    String[] argArr1 = null;
   	    	        String input1 = s.nextLine();
    	            input1 = input1.trim();
            	    argArr1 = input1.split(" ");

    	            System.out.println("선택적으로 필요한 옵션을 입력하세요");
        	        System.out.println("양식 : 파괴방지여부 이벤트 피시방할인 MVP등급별할인 토드템강화 토드템파괴방지여부");
            	    System.out.println("참고1) 파괴방지여부, 피시방 할인, 토드템 파괴방지여부 : true: O, false: X");
                	System.out.println("참고2) 이벤트 : 0: X 1: 30%할인, 2: 1+1, 3: 5배수");
					System.out.println("참고3) MVP등급 : 0: X 1: 실버, 2: 골드, 3: 다이아,레드");
					System.out.println("참고4) 토드템 강화 : 0: 토드미사용, 1~25: 1~25성까지 토드템 강화");
    	            String[] argArr2 = null;
        	        String input2 = s.nextLine();
            	    input2 = input2.trim();
                	argArr2 = input2.split(" ");

	                NormalItem[] normal = new NormalItem[Integer.parseInt(argArr1[0])];
    	            for (int i = 0; i < normal.length; i++){
        	            normal[i] = new NormalItem(Integer.parseInt(argArr1[2]), Integer.parseInt(argArr1[3]),
            	        Byte.parseByte(argArr1[4]), Byte.parseByte(argArr2[1]), Boolean.parseBoolean(argArr2[2]) ,
                        Byte.parseByte(argArr2[3]), Boolean.parseBoolean(argArr2[0]), Integer.parseInt(argArr2[4]));
                    	NormalItem.goTo(normal[i], Integer.parseInt(argArr1[1]), Boolean.parseBoolean(argArr2[5]));
	                }
					ResultInfo ri = new ResultInfo();
					ri.fromStarforce = Integer.parseInt(argArr1[3]);
					ri.toadIgnoreDestroy = Boolean.parseBoolean(argArr2[5]);
					ri.savedProperty(normal);
        	        System.out.println("==== " + Integer.parseInt(argArr1[2]) + "제 아이템의 " + normal.length + "개 " + Integer.parseInt(argArr1[3]) + "성 -> " + Integer.parseInt(argArr1[1]) + "성까지의 강화 ====");
	        		System.out.println("추가 설명 : 스타캐치 " + Item.starCatchToString(Byte.parseByte(argArr1[4])) + ", 파괴방지 : " + NormalItem.booleanToString(Boolean.parseBoolean(argArr2[0]))
										 + ", PC방 할인 : " + NormalItem.booleanToString(Boolean.parseBoolean(argArr2[2])) + ", MVP 등급 : " + NormalItem.MVPGradeToString(Byte.parseByte(argArr2[3])));
					System.out.print("토드여부 : " + NormalItem.booleanToString(ri.toadHammer)); 
					if (ri.toadHammer == true) {
						System.out.print(" 토드템 0성 -> " + ri.toadToStarforce + "성, 토드템 파괴방지 : " + NormalItem.booleanToString(ri.toadIgnoreDestroy));
					}
					System.out.println();
		        	System.out.println("메이플 이벤트 적용 : " + NormalItem.mapleEventToString(Byte.parseByte(argArr2[1])));

		        	System.out.println(Item.sumUpgradePriceMinMax(normal, ri));
    	    		System.out.println(Item.averageSumUpgradePrice(normal, ri));
        			System.out.println(Item.upgradeCountMinMax(normal, ri));
        			System.out.println(Item.averageUpgradeCount(normal, ri));
        			System.out.println(Item.destroyCountMinMax(normal, ri));
	        		System.out.println(Item.averageDestroyCount(normal, ri));
    	    		System.out.println(Item.chanceTimeCountMinMax(normal, ri));
        			System.out.println(Item.averageChanceTimeCount(normal, ri));
					list.add(ri);
				
					System.out.println();
					System.out.println("해당 시뮬레이션이 저장되었습니다.");
					System.out.print("계속 진행하시겠습니까? (yes or no)");
					String decision = s.nextLine().trim();
					if (decision.equals("no")){
						break;
					}
				}
                break;

				case 2:
				while(true){
					System.out.println("슈페리얼 아이템 강화 메뉴입니다.");
 	            	System.out.println("양식 : 아이템개수 목표스타포스 레벨 시작스타포스 스타캐치여부");
   	            	System.out.println("참고) 스타캐치여부 : 0: 스타캐치X, 1: 합연산(4.5%p), 2: 곱연산(1.045배)");
   	            	System.out.println("아이템의 정보를 입력하세요.");
   	        	    String[] argArr1 = null;
   	    	        String input1 = s.nextLine();
    	            input1 = input1.trim();
            	    argArr1 = input1.split(" ");

	                SuperiorItem[] superior = new SuperiorItem[Integer.parseInt(argArr1[0])];
    	            for (int i = 0; i < superior.length; i++){
        	            superior[i] = new SuperiorItem(Integer.parseInt(argArr1[2]), Integer.parseInt(argArr1[3]),
            	        Byte.parseByte(argArr1[4]));
                    	superior[i].goTo(Integer.parseInt(argArr1[1]));
	                }
					ResultInfo ri = new ResultInfo();
					ri.fromStarforce = Integer.parseInt(argArr1[3]);
					ri.succededCatch = Byte.parseByte(argArr1[4]);
					ri.savedProperty(superior);
        	        System.out.println("==== " + Integer.parseInt(argArr1[2]) + "제 아이템의 " + superior.length + "개 " + Integer.parseInt(argArr1[3]) + "성 -> " + Integer.parseInt(argArr1[1]) + "성까지의 강화 ====");
	        		System.out.println("추가 설명 : 스타캐치 " + Item.starCatchToString(Byte.parseByte(argArr1[4])));

		        	System.out.println(Item.sumUpgradePriceMinMax(superior, ri));
    	    		System.out.println(Item.averageSumUpgradePrice(superior, ri));
        			System.out.println(Item.upgradeCountMinMax(superior, ri));
        			System.out.println(Item.averageUpgradeCount(superior, ri));
        			System.out.println(Item.destroyCountMinMax(superior, ri));
	        		System.out.println(Item.averageDestroyCount(superior, ri));
    	    		System.out.println(Item.chanceTimeCountMinMax(superior, ri));
        			System.out.println(Item.averageChanceTimeCount(superior, ri));
					list.add(ri);
				
					System.out.println();
					System.out.println("해당 시뮬레이션이 저장되었습니다.");
					System.out.print("계속 진행하시겠습니까? (yes or no)");
					String decision = s.nextLine().trim();
					if (decision.equals("no")){
						break;
					}
				}
				break;
				
				case 3:
				while (true){
					System.out.println("최근 시뮬레이션 결과목록입니다.");
					System.out.println(list);
					System.out.println("어떤 결과를 보시겠습니까? (목록순 번호 기입)");
					String selection = s.nextLine();
					Object obj = list.get(Integer.parseInt(selection)-1);
					if (obj instanceof ResultInfo){
						ResultInfo tmp = (ResultInfo)obj;
						tmp.printProperty();
					}
					System.out.print("목록을 계속 보시겠습니까? (yes or no)");
					String decision = s.nextLine().trim();
					if (decision.equals("no")){
						break;
					}
				}
				break;
            }

        }
        s.close();
    }
}
