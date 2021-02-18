package starforce;

public class NormalStarforce extends Item {

	// �ν��Ͻ� ����
    int[] destroyPercent = {600, 1300, 1400, 2100, 2100, 2800, 7000, 19400, 29400, 39600, 0}; 
	boolean ignoreDestroy; // �ı����� ����
	int maxStarforce;
	byte mapleEvent = 0;
	boolean roomOfPC;
	float discountPCRoom = 0f;
	byte gradeOfMVP;
	float[] discountMVPGrade = {0f, 0.03f, 0.05f, 0.1f}; //�ǹ� : 3%, ��� : 5%, ���̾�,���� : 10%
	float discountMVP = 0f;
	int toadToStarforce = 0;
	boolean toadHammer = false;
	boolean toadIgnoreDestroy;
	
	// ������
    public NormalStarforce(int level, int fromStarforce, byte succededCatch){
        super(level, fromStarforce, succededCatch);
        this.maxStarforce = maxItemStarforce(level);
    }

    public NormalStarforce(int level, int fromStarforce, byte succededCatch, byte mapleEvent, boolean discountPCRoom, byte discountMVPGrade) {
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

    public NormalStarforce(int level, int fromStarforce, byte succededCatch, byte mapleEvent, boolean discountPCRoom, byte discountMVPGrade, boolean ignoreDestroy) {
        this(level, fromStarforce, succededCatch, mapleEvent, discountPCRoom, discountMVPGrade);
        this.ignoreDestroy = ignoreDestroy;
		if (ignoreDestroy == true) {
			for (int i = 0; i < 4; i++) {
				destroyPercent[i] = destroyPercent[10];
			}
        }
	}

	public NormalStarforce(int level, int fromStarforce, byte succededCatch, byte mapleEvent, boolean discountPCRoom, byte discountMVPGrade, boolean ignoreDestroy, int toadToStarforce){
		this(level, fromStarforce, succededCatch, mapleEvent, discountPCRoom, discountMVPGrade, ignoreDestroy);
		if (0 < toadToStarforce && toadToStarforce <= 25){
			toadHammer = true;
			this.toadToStarforce = toadToStarforce;
		} 
	}
	
	// �޼���
    public static int maxItemStarforce(int level){ // ������ ������ �ִ� ��Ÿ����
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
	
	void fromToadStarforceToNormalStarforce(ToadStarforce ti){
        this.starforce = --ti.starforce;
        this.upgradeCount += ti.upgradeCount;
        this.destroyCount += ti.destroyCount;
        this.sumUpgradePrice += ti.sumUpgradePrice;
        this.chanceTime += ti.chanceTime;
    }
	
	public static String result(NormalStarforce[] niArr, int fromStarforce, int toStarforce) { // ����� ��� �޼���
    	String result = "";
    	result += "==== " + niArr[0].level + "�� �븻 �������� " + niArr.length + "�� " + fromStarforce + "�� -> " + toStarforce + "�������� ��ȭ ==== <br>";
		result += "�߰� ���� - ��Ÿĳġ " + Item.starCatchToString(niArr[0].succededCatch) + ", �ı����� : " + NormalStarforce.booleanToString(niArr[0].ignoreDestroy) + "<br>";
		result += ", PC�� ���� : " + NormalStarforce.booleanToString(niArr[0].roomOfPC) + ", MVP ��� : " + NormalStarforce.MVPGradeToString(niArr[0].gradeOfMVP) + "<br>";
    	result += "��忩�� : " + NormalStarforce.booleanToString(niArr[0].toadHammer) + "<br>";
    	if (niArr[0].toadHammer) {
    		result += " ����� 0�� -> " + niArr[0].toadToStarforce + "��, ����� �ı����� : " + NormalStarforce.booleanToString(niArr[0].toadIgnoreDestroy) + "<br>";
    	}
    	result += "������ �̺�Ʈ ���� : " + NormalStarforce.mapleEventToString(niArr[0].mapleEvent) + "<br>";
    	return result;
    }
	
	public static int toadItemLevel(int level) { // ����� ��� ���� Ȯ�� �޼���
    	int toadItemLevel = 0;
    	if (level <= 110) {
    		toadItemLevel = level-20;
        } else {
        	toadItemLevel = level-10;
        }
    	return toadItemLevel;
    }
	
	public static NormalStarforce goTo(NormalStarforce ni, int toStarforce, boolean toadIgnoreDestroy) {
		ni.toadIgnoreDestroy = toadIgnoreDestroy;
			switch(ni.mapleEvent){
				case 0: // �̺�Ʈ x
				do {
					if (ni.toadHammer == true && ni.starforce == 0){
						ToadStarforce ti = new ToadStarforce(ni.level, ni.starforce, ni.succededCatch, ni.mapleEvent, ni.roomOfPC, ni.gradeOfMVP, ni.toadIgnoreDestroy);
						ti.goTo(ni.toadToStarforce);
						ni.fromToadStarforceToNormalStarforce(ti);
					} else{
						ni.upgradeCount++;
						int percent = (int)(Math.random() * 100000);
						if (ni.starforce < 3) {
							ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * (ni.starforce + 1)/25))*(1 - ni.discountMVP - ni.discountPCRoom)/100);
							ni.upgradePercent = (int)(((95 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch); 
							if (percent < ni.upgradePercent) {
								ni.starforce++;
							} 
						} else if (ni.starforce < 10) {
							ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * (ni.starforce) + 1)/25)*(1 - ni.discountMVP - ni.discountPCRoom)/100);
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
							} 
						} else if (ni.starforce == 10) {
							ni.sumUpgradePrice += (long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							}
						} else if (ni.starforce < 12) { 
							ni.sumUpgradePrice += (long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) { 
									ni.sumUpgradePrice += (long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 12) { 
							if (ni.ignoreDestroy == true) {
								ni.sumUpgradePrice += (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)/100
												+(long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							} else {
								ni.sumUpgradePrice += (long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							}
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
									ni.sumUpgradePrice += (long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 13) {
							if (ni.ignoreDestroy == true) {
								ni.sumUpgradePrice += (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)/100
												+(long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							} else {
								ni.sumUpgradePrice += (long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							}
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
									ni.sumUpgradePrice += (long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 14) {
							if (ni.ignoreDestroy == true) {
								ni.sumUpgradePrice += (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)/100
												+(long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							} else {
								ni.sumUpgradePrice += (long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							}
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
									ni.sumUpgradePrice += (long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 15) {
							if (ni.ignoreDestroy == true) {
								ni.sumUpgradePrice += (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)/100
												+(long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)*(1 - ni.discountMVP - ni.discountPCRoom))/100;;
							} else {
								ni.sumUpgradePrice += (long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)*(1 - ni.discountMVP - ni.discountPCRoom))/100;;
							}
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[3]) {
								ni.starforce = 0;
								ni.destroyCount++;
							}
						} else if (ni.starforce == 16) {
							if (ni.ignoreDestroy == true) {
								ni.sumUpgradePrice += (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)/100
												+(long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							} else {
								ni.sumUpgradePrice += (long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							}
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
									ni.sumUpgradePrice += (long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 17) {
							ni.sumUpgradePrice += (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)/100;
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
									ni.sumUpgradePrice += (long)((1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce < 20) {
							ni.sumUpgradePrice += (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)/100;
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
									ni.sumUpgradePrice += (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 20) {
							ni.sumUpgradePrice += (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)/100;
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[6]) {
								ni.starforce = 0;
								ni.destroyCount++;
							}
						} else if (ni.starforce == 21) {
							ni.sumUpgradePrice += (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)/100;
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
									ni.sumUpgradePrice += (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 22) {
							ni.sumUpgradePrice += (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)/100;
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
									ni.sumUpgradePrice += (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 23) {
							ni.sumUpgradePrice += (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)/100;
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
									ni.sumUpgradePrice += (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 24) {
							ni.sumUpgradePrice += (long)(1000+ Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200)/100;
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
				} while(ni.starforce != toStarforce);
				ni.upgradeCount += ni.chanceTime;
				break;

				case 1: // 30 �ۼ�Ʈ ���� (������) 
				do {
					if (ni.toadHammer == true && ni.starforce == 0){
						ToadStarforce ti = new ToadStarforce(ni.level, ni.starforce, ni.succededCatch, ni.mapleEvent, ni.roomOfPC, ni.gradeOfMVP, toadIgnoreDestroy);
						ti.goTo(ni.toadToStarforce);
						ni.fromToadStarforceToNormalStarforce(ti);
					} else {
						ni.upgradeCount++;
						int percent = (int)(Math.random() * 100000); 
						if (ni.starforce < 3) {
							ni.sumUpgradePrice += (long)((1000+ Math.pow((double)ni.level, 3.0) * (ni.starforce + 1)/25)*(1 - ni.discountMVP - ni.discountPCRoom)*0.7/100);
							ni.upgradePercent = (int)(((95 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch); 
							if (percent < ni.upgradePercent) {
								ni.starforce++;
							}
						} else if (ni.starforce < 10) {
							ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * (ni.starforce) + 1)/25)*(1 - ni.discountMVP - ni.discountPCRoom)*0.7/100);
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
							}
						} else if (ni.starforce == 10) {
							ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							}
						} else if (ni.starforce < 12) { 
							ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) { 
									ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 12) { 
							if (ni.ignoreDestroy == true) {
								ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
							} else {
								ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
							}
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
									ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 13) {
							if (ni.ignoreDestroy == true) {
								ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
							} else {
								ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
							}
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
									ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 14) {
							if (ni.ignoreDestroy == true) {
								ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
							} else {
								ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
							}
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
									ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 15) {
							if (ni.ignoreDestroy == true) {
								ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
							} else {
								ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
							}
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[3]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} 
						} else if (ni.starforce == 16) {
							if (ni.ignoreDestroy == true) {
								ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
							} else {
								ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
							}
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
									ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 17) {
							ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*0.7)/100;
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
									ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom)*0.7)/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce < 20) {
							ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*0.7)/100;
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
									ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*0.7)/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 20) {
							ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*0.7)/100;
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[6]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} 
						} else if (ni.starforce == 21) {
							ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*0.7)/100;
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
									ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*0.7)/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 22) {
							ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*0.7)/100;
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
									ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*0.7)/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 23) {
							ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*0.7)/100;
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
									ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*0.7)/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 24) {
							ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*0.7)/100;
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
				} while(ni.starforce != toStarforce);
				ni.upgradeCount += ni.chanceTime;
				break;

				case 2: // 10������ ��Ÿ���� 1+1 
				do {
					if (ni.toadHammer == true && ni.starforce == 0){
						ToadStarforce ti = new ToadStarforce(ni.level, ni.starforce, ni.succededCatch, ni.mapleEvent, ni.roomOfPC, ni.gradeOfMVP, toadIgnoreDestroy);
						ti.goTo(ni.toadToStarforce);
						ni.fromToadStarforceToNormalStarforce(ti);
					} else {
						ni.upgradeCount++;
						int percent = (int)(Math.random() * 100000); 
						if (ni.starforce < 3) {
							ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * (ni.starforce + 1)/25))*(1 - ni.discountMVP - ni.discountPCRoom)/100);
							ni.upgradePercent = (int)(((95 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch); 
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.starforce++; 
							} 
						} else if (ni.starforce < 10) {
							ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * (ni.starforce) + 1)/25)*(1 - ni.discountMVP - ni.discountPCRoom)/100);
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.starforce++;
							} 
						} else if (ni.starforce == 10) {
							ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.starforce++;
								ni.variableStarforce = 0;
							} 
						} else if (ni.starforce < 12) { 
							ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) { 
									ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.starforce++;
									ni.starforce++; 
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 12) { 
							if (ni.ignoreDestroy == true) {
								ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							} else {
								ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							}
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
									ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 13) {
							if (ni.ignoreDestroy == true) {
								ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							} else {
								ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							}
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
									ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 14) {
							if (ni.ignoreDestroy == true) {
								ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							} else {
								ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							}
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
									ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 15) {
							if (ni.ignoreDestroy == true) {
								ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom))/100;;
							} else {
								ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom))/100;;
							}
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[3]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} 
						} else if (ni.starforce == 16) {
							if (ni.ignoreDestroy == true) {
								ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							} else {
								ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							}
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
									ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 17) {
							ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
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
									ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce < 20) {
							ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
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
									ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 20) {
							ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[6]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} 
						} else if (ni.starforce == 21) {
							ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
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
									ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 22) {
							ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
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
									ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 23) {
							ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
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
									ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 24) {
							ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
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
				} while(ni.starforce < toStarforce);
				ni.upgradeCount += ni.chanceTime;
				break;

				case 3: // 5�ǹ�� 100�ۼ�Ʈ ��ȭ ���� (15�� ����)
				do {
					if (ni.toadHammer == true && ni.starforce == 0){
						ToadStarforce ti = new ToadStarforce(ni.level, ni.starforce, ni.succededCatch, ni.mapleEvent, ni.roomOfPC, ni.gradeOfMVP, toadIgnoreDestroy);
						ti.goTo(ni.toadToStarforce);
						ni.fromToadStarforceToNormalStarforce(ti);
					} else {
						ni.upgradeCount++;
						int percent = (int)(Math.random() * 100000); 
						if (ni.starforce < 3) {
							ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * (ni.starforce + 1)/25))*(1 - ni.discountMVP - ni.discountPCRoom)/100);
							ni.upgradePercent = (int)(((95 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch); 
							if (percent < ni.upgradePercent) {
								ni.starforce++;
							} 
						} else if (ni.starforce < 10) {
							ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * (ni.starforce) + 1)/25)*(1 - ni.discountMVP - ni.discountPCRoom)/100);
							if (ni.starforce == 5) { 
								ni.upgradePercent = 100000;
							}else {
								ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							}
							if (percent < ni.upgradePercent) {
								ni.starforce++;
							} 
						} else if (ni.starforce == 10) { 
							ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							ni.starforce++;
							ni.variableStarforce = 0;
						} else if (ni.starforce < 12) { 
							ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							ni.upgradePercent = (int)(((100 - (5 * ni.starforce)) * 1000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else {
								ni.starforce--;
								ni.variableStarforce--;
								if (ni.variableStarforce == -2) { 
									ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 12) { 
							if (ni.ignoreDestroy == true) {
								ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							} else {
								ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							}
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
									ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 13) {
							if (ni.ignoreDestroy == true) {
								ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							} else {
								ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							}
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
									ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 14) {
							if (ni.ignoreDestroy == true) {
								ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							} else {
								ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							}
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
									ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/400))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 15) { 
							ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							ni.starforce++;
							ni.variableStarforce = 0;
						} else if (ni.starforce == 16) {
							if (ni.ignoreDestroy == true) {
								ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100
												+(long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							} else {
								ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
							}
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
									ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 17) {
							ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
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
									ni.sumUpgradePrice += (long)((1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))*(1 - ni.discountMVP - ni.discountPCRoom))/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce < 20) {
							ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
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
									ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 20) {
							ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
							ni.upgradePercent = (int)((30000 + ni.sumOfStarforceCatch) * ni.multipleOfStarforceCatch);
							if (percent < ni.upgradePercent) {
								ni.starforce++;
								ni.variableStarforce = 0;
							} else if (percent < ni.upgradePercent + ni.destroyPercent[6]) {
								ni.starforce = 0;
								ni.destroyCount++;
							} 
						} else if (ni.starforce == 21) {
							ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
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
									ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 22) {
							ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
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
									ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 23) {
							ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
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
									ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
									ni.starforce++;
									ni.variableStarforce = 0;
									ni.chanceTime++;
								}
							}
						} else if (ni.starforce == 24) {
							ni.sumUpgradePrice += (long)(1000+ (Math.pow((double)ni.level, 3.0) * Math.pow(((double)(ni.starforce) + 1), 2.7)/200))/100;
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
				} while(ni.starforce != toStarforce);
				ni.upgradeCount += ni.chanceTime;
				break;
			}
			return ni;
	}
}

class ToadStarforce extends NormalStarforce{

    // �ν��Ͻ� ����
	int toadToStarforce;

    // ������
    ToadStarforce(int level, int fromStarforce, byte succededCatch, byte mapleEvent, boolean discountPCRoom, byte discountMVPGrade, boolean toadIgnoreDestroy){
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

    // �޼���
    void goTo(int toToadStarforceStarforce){
			switch(mapleEvent){
				case 0: // �̺�Ʈ x
				do {
					upgradeCount++;
					int percent = (int)(Math.random() * 100000);
					if (starforce < 3) {
						sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * (starforce + 1)/25))*(1 - discountMVP - discountPCRoom)/100);
						upgradePercent = (int)(((95 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch); 
						if (percent < upgradePercent) {
							starforce++;
						} 
					} else if (starforce < 10) {
						sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * (starforce) + 1)/25)*(1 - discountMVP - discountPCRoom)/100);
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
						} 
					} else if (starforce == 10) {
						sumUpgradePrice += (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)*(1 - discountMVP - discountPCRoom))/100;
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						}
					} else if (starforce < 12) { 
						sumUpgradePrice += (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)*(1 - discountMVP - discountPCRoom))/100;
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else {
							starforce--;
							variableStarforce--;
							if (variableStarforce == -2) { 
								sumUpgradePrice += (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)*(1 - discountMVP - discountPCRoom))/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 12) { 
						if (ignoreDestroy == true) {
							sumUpgradePrice += (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)/100
											+(long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)*(1 - discountMVP - discountPCRoom))/100;
						} else {
							sumUpgradePrice += (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)*(1 - discountMVP - discountPCRoom))/100;
						}
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
								sumUpgradePrice += (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)*(1 - discountMVP - discountPCRoom))/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 13) {
						if (ignoreDestroy == true) {
							sumUpgradePrice += (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)/100
											+(long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)*(1 - discountMVP - discountPCRoom))/100;
						} else {
							sumUpgradePrice += (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)*(1 - discountMVP - discountPCRoom))/100;
						}
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
								sumUpgradePrice += (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)*(1 - discountMVP - discountPCRoom))/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 14) {
						if (ignoreDestroy == true) {
							sumUpgradePrice += (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)/100
											+(long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)*(1 - discountMVP - discountPCRoom))/100;
						} else {
							sumUpgradePrice += (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)*(1 - discountMVP - discountPCRoom))/100;
						}
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
								sumUpgradePrice += (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)*(1 - discountMVP - discountPCRoom))/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 15) {
						if (ignoreDestroy == true) {
							sumUpgradePrice += (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)/100
											+(long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)*(1 - discountMVP - discountPCRoom))/100;;
						} else {
							sumUpgradePrice += (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)*(1 - discountMVP - discountPCRoom))/100;;
						}
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else if (percent < upgradePercent + destroyPercent[3]) {
							starforce = 0;
							destroyCount++;
						}
					} else if (starforce == 16) {
						if (ignoreDestroy == true) {
							sumUpgradePrice += (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)/100
											+(long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)*(1 - discountMVP - discountPCRoom))/100;
						} else {
							sumUpgradePrice += (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)*(1 - discountMVP - discountPCRoom))/100;
						}
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
								sumUpgradePrice += (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)*(1 - discountMVP - discountPCRoom))/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 17) {
						sumUpgradePrice += (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)/100;
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
								sumUpgradePrice += (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)*(1 - discountMVP - discountPCRoom))/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce < 20) {
						sumUpgradePrice += (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)/100;
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
								sumUpgradePrice += (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 20) {
						sumUpgradePrice += (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)/100;
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else if (percent < upgradePercent + destroyPercent[6]) {
							starforce = 0;
							destroyCount++;
						}
					} else if (starforce == 21) {
						sumUpgradePrice += (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)/100;
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
								sumUpgradePrice += (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 22) {
						sumUpgradePrice += (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)/100;
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
								sumUpgradePrice += (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 23) {
						sumUpgradePrice += (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)/100;
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
								sumUpgradePrice += (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 24) {
						sumUpgradePrice += (long)(1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200)/100;
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
				} while(starforce != toToadStarforceStarforce);
				break;

				case 1: // 30 �ۼ�Ʈ ���� (������) 
				do {
					upgradeCount++;
					int percent = (int)(Math.random() * 100000); 
					if (starforce < 3) {
						sumUpgradePrice += (long)((1000+ Math.pow((double)level, 3.0) * (starforce + 1)/25)*(1 - discountMVP - discountPCRoom)*0.7/100);
						upgradePercent = (int)(((95 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch); 
						if (percent < upgradePercent) {
							starforce++;
						}
					} else if (starforce < 10) {
						sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * (starforce) + 1)/25)*(1 - discountMVP - discountPCRoom)*0.7/100);
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
						}
					} else if (starforce == 10) {
						sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom)*0.7)/100;
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						}
					} else if (starforce < 12) { 
						sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom)*0.7)/100;
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else {
							starforce--;
							variableStarforce--;
							if (variableStarforce == -2) { 
								sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom)*0.7)/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 12) { 
						if (ignoreDestroy == true) {
							sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom)*0.7)/100;
						} else {
							sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom)*0.7)/100;
						}
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
								sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom)*0.7)/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 13) {
						if (ignoreDestroy == true) {
							sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom)*0.7)/100;
						} else {
							sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom)*0.7)/100;
						}
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
								sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom)*0.7)/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 14) {
						if (ignoreDestroy == true) {
							sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom)*0.7)/100;
						} else {
							sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom)*0.7)/100;
						}
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
								sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom)*0.7)/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 15) {
						if (ignoreDestroy == true) {
							sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom)*0.7)/100;
						} else {
							sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom)*0.7)/100;
						}
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else if (percent < upgradePercent + destroyPercent[3]) {
							starforce = 0;
							destroyCount++;
						} 
					} else if (starforce == 16) {
						if (ignoreDestroy == true) {
							sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom)*0.7)/100;
						} else {
							sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom)*0.7)/100;
						}
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
								sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom)*0.7)/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 17) {
						sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*0.7)/100;
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
								sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom)*0.7)/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce < 20) {
						sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*0.7)/100;
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
								sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*0.7)/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 20) {
						sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*0.7)/100;
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else if (percent < upgradePercent + destroyPercent[6]) {
							starforce = 0;
							destroyCount++;
						} 
					} else if (starforce == 21) {
						sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*0.7)/100;
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
								sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*0.7)/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 22) {
						sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*0.7)/100;
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
								sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*0.7)/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 23) {
						sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*0.7)/100;
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
								sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*0.7)/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 24) {
						sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*0.7)/100;
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
				} while(starforce != toToadStarforceStarforce);
				break;

				case 2: // 10������ ��Ÿ���� 1+1 
				do {
					upgradeCount++;
					int percent = (int)(Math.random() * 100000); 
					if (starforce < 3) {
						sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * (starforce + 1)/25))*(1 - discountMVP - discountPCRoom)/100);
						upgradePercent = (int)(((95 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch); 
						if (percent < upgradePercent) {
							starforce++;
							starforce++; 
						} 
					} else if (starforce < 10) {
						sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * (starforce) + 1)/25)*(1 - discountMVP - discountPCRoom)/100);
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
							starforce++;
						} 
					} else if (starforce == 10) {
						sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
							starforce++;
							variableStarforce = 0;
						} 
					} else if (starforce < 12) { 
						sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else {
							starforce--;
							variableStarforce--;
							if (variableStarforce == -2) { 
								sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
								starforce++;
								starforce++; 
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 12) { 
						if (ignoreDestroy == true) {
							sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						} else {
							sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						}
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
								sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 13) {
						if (ignoreDestroy == true) {
							sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						} else {
							sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						}
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
								sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 14) {
						if (ignoreDestroy == true) {
							sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						} else {
							sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						}
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
								sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 15) {
						if (ignoreDestroy == true) {
							sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom))/100;;
						} else {
							sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom))/100;;
						}
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else if (percent < upgradePercent + destroyPercent[3]) {
							starforce = 0;
							destroyCount++;
						} 
					} else if (starforce == 16) {
						if (ignoreDestroy == true) {
							sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom))/100;
						} else {
							sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom))/100;
						}
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
								sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom))/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 17) {
						sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
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
								sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom))/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce < 20) {
						sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
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
								sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 20) {
						sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else if (percent < upgradePercent + destroyPercent[6]) {
							starforce = 0;
							destroyCount++;
						} 
					} else if (starforce == 21) {
						sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
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
								sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 22) {
						sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
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
								sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 23) {
						sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
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
								sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 24) {
						sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
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
				} while(starforce < toToadStarforceStarforce);
				break;

				case 3: // 5�ǹ�� 100�ۼ�Ʈ ��ȭ ���� (15�� ����)
				do {
					upgradeCount++;
					int percent = (int)(Math.random() * 100000); 
					if (starforce < 3) {
						sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * (starforce + 1)/25))*(1 - discountMVP - discountPCRoom)/100);
						upgradePercent = (int)(((95 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch); 
						if (percent < upgradePercent) {
							starforce++;
						} 
					} else if (starforce < 10) {
						sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * (starforce) + 1)/25)*(1 - discountMVP - discountPCRoom)/100);
						if (starforce == 5) { 
							upgradePercent = 100000;
						}else {
							upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						}
						if (percent < upgradePercent) {
							starforce++;
						} 
					} else if (starforce == 10) { 
						sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						starforce++;
						variableStarforce = 0;
					} else if (starforce < 12) { 
						sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else {
							starforce--;
							variableStarforce--;
							if (variableStarforce == -2) { 
								sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 12) { 
						if (ignoreDestroy == true) {
							sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						} else {
							sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						}
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
								sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 13) {
						if (ignoreDestroy == true) {
							sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						} else {
							sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						}
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
								sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 14) {
						if (ignoreDestroy == true) {
							sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						} else {
							sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
						}
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
								sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400))*(1 - discountMVP - discountPCRoom))/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 15) { 
						sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom))/100;
						starforce++;
						variableStarforce = 0;
					} else if (starforce == 16) {
						if (ignoreDestroy == true) {
							sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100
											+(long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom))/100;
						} else {
							sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom))/100;
						}
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
								sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom))/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 17) {
						sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
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
								sumUpgradePrice += (long)((1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))*(1 - discountMVP - discountPCRoom))/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce < 20) {
						sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
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
								sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 20) {
						sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
						upgradePercent = (int)((30000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else if (percent < upgradePercent + destroyPercent[6]) {
							starforce = 0;
							destroyCount++;
						} 
					} else if (starforce == 21) {
						sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
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
								sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 22) {
						sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
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
								sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 23) {
						sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
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
								sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
						}
					} else if (starforce == 24) {
						sumUpgradePrice += (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
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
				} while(starforce != toToadStarforceStarforce);
				break;
			}
    }
}