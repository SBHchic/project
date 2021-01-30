package starforce;

abstract class Item {

    // �ν��Ͻ� ����
    int level;
    int starforce;
    int destroyCount = 0;
    int upgradePercent = 0;
    byte variableStarforce = 0;
	byte succededCatch;
    int sumOfStarforceCatch; // ��Ÿ����ĳġ �տ���
    float multipleOfStarforceCatch; // ��Ÿ����ĳġ ������

    // ������
    public Item(int level, int fromStarforce, byte succededCatch){
        this.level = level;
		this.starforce = fromStarforce;
		this.succededCatch = succededCatch;
        switch (succededCatch){
			case 0: // ��Ÿ����ĳġ ����
			this.sumOfStarforceCatch = 0;
			this.multipleOfStarforceCatch = 1f;
			break;
			
			case 1: // ��Ÿ����ĳġ ���� (�տ���)
			this.sumOfStarforceCatch = 4500;
			this.multipleOfStarforceCatch = 1f;
			break;

			case 2: // ��Ÿ����ĳġ ���� (������)
			this.sumOfStarforceCatch = 0;
			this.multipleOfStarforceCatch = 1.045f;
			break;
		}
    }

    // �޼���
    static String starCatchToString(byte succededCatch) { // ��Ÿĳġ ��� ���Ǽ�
		
		if(succededCatch == 0){
			return "x";
		} else if (succededCatch == 1){
			return "�տ���(4.5%p)";
		} else {
			return "������(1.045��)";
        }
    }

}

public class NumberOfItems_Normal extends Item {

    // �ν��Ͻ� ����
    int[] destroyPercent = {600, 1300, 1400, 2100, 2100, 2800, 7000, 19400, 29400, 39600, 0}; 
	boolean ignoreDestroy; // �ı����� ����
	int maxStarforce;
	byte mapleEvent = 0;
	int toadToStarforce = 0;
	boolean toadHammer = false;
	boolean toadIgnoreDestroy;
    int toadDestroyCount = 0;
    
    // ������
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

    static String booleanToString(boolean booleanSelect){ // boolean���� ��� ���Ǽ�
		if (booleanSelect == true){
			return "O";
		}
		return "X";
	}

	static String mapleEventToString(byte mapleEvent){ // �̺�Ʈ ��� ���Ǽ�
		if (mapleEvent == 0){
			return "x";
		} else if (mapleEvent == 1) {
			return "��Ÿ���� 1+1 �̺�Ʈ (10������)";
		} else {
			return "5�� ����� ��Ÿ���� 100�ۼ�Ʈ ���� (15������)";
		}
    }

    void fromToadItemToNumberOfItems_Normal(ToadItem ti){ // ����ۿ��� �������� ����Ҷ� �Űܿ��� ����
        this.starforce = --ti.starforce;
        this.toadDestroyCount += ti.destroyCount;
    }
    
    public static double succededCount(NumberOfItems_Normal[] niArr, int success) { // ���� Ȯ�� ��� �޼���
    	int count = 0;
    	for (int i = 0; i < niArr.length; i++) {
    		if (success == niArr[i].starforce) {
    			count++;
    		}
    	}
    	return (double)count/niArr.length*100;
    }
    public static String result(NumberOfItems_Normal[] niArr, int fromStarforce, int toStarforce) { // ����� ��� �޼���
    	String result = "";
    	result += "==== " + niArr[0].level + "�� �븻�������� " + niArr.length + "�� " + fromStarforce + "�� -> " + toStarforce + "�������� ��ȭ ==== <br>";
    	result += "�߰� ���� - ��Ÿĳġ " + Item.starCatchToString(niArr[0].succededCatch) + ", �ı����� : " + NumberOfItems_Normal.booleanToString(niArr[0].ignoreDestroy) + "<br>";
    	result += "��忩�� : " + NumberOfItems_Normal.booleanToString(niArr[0].toadHammer) + "<br>";
    	if (niArr[0].toadHammer) {
    		result += " ����� 0�� -> " + niArr[0].toadToStarforce + "��, ����� �ı����� : " + NumberOfItems_Normal.booleanToString(niArr[0].toadIgnoreDestroy) + "<br>";
    	}
    	result += "������ �̺�Ʈ ���� : " + NumberOfItems_Normal.mapleEventToString(niArr[0].mapleEvent) + "<br>";
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
    
    public static NumberOfItems_Normal goTo(NumberOfItems_Normal ni, int toStarforce, boolean toadIgnoreDestroy, int numberOfRealItems, int numberOfToadItems) { // ��Ÿ���� ���� �޼���
		ni.toadIgnoreDestroy = toadIgnoreDestroy;
			switch(ni.mapleEvent){
				case 0: // �̺�Ʈ x
				do {
					if (ni.toadHammer == true && ni.starforce == 0){
						ToadItem ti = new ToadItem(ni.level, ni.starforce, ni.succededCatch, ni.mapleEvent, ni.toadIgnoreDestroy);
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

				case 1: // 10������ ��Ÿ���� 1+1 
				do {
					if (ni.toadHammer == true && ni.starforce == 0){
						ToadItem ti = new ToadItem(ni.level, ni.starforce, ni.succededCatch, ni.mapleEvent, toadIgnoreDestroy);
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

				case 2: // 5�ǹ�� 100�ۼ�Ʈ ��ȭ ���� (15�� ����)
				do {
					if (ni.toadHammer == true && ni.starforce == 0){
						ToadItem ti = new ToadItem(ni.level, ni.starforce, ni.succededCatch, ni.mapleEvent, toadIgnoreDestroy);
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

class ToadItem extends NumberOfItems_Normal{

    // �ν��Ͻ� ����
	int toadToStarforce;

    // ������
    ToadItem(int level, int fromStarforce, byte succededCatch, byte mapleEvent, boolean toadIgnoreDestroy){
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

    // �޼���
    public void goTo(int toToadItemStarforce, int numberOfToadItems){
			switch(mapleEvent){
				case 0: // �̺�Ʈ x
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
				
				case 1: // 10������ ��Ÿ���� 1+1 
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

				case 2: // 5�ǹ�� 100�ۼ�Ʈ ��ȭ ���� (15�� ����)
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


