package starforce;

public class NumberOfItems_Superior extends Item {

	// 인스턴스 변수
    int[] destroyPercent = {1800, 3000, 4200, 6000, 9500, 13000, 16300, 48500, 49000, 49500}; // 파괴 확률
    int maxStarforce; // 현재 레벨에서 가능한 최대 스타포스
    
    // 생성자
    public NumberOfItems_Superior(int level, int fromStarforce, byte succededCatch){
        super(level, fromStarforce, succededCatch);
        if(level < 95){
            this.maxStarforce = 3; // 레벨별 최대 스타포스 (헬리시움)
        } else if (level < 108){
            this.maxStarforce = 5; // 구현 x
        } else if (level < 118){
            this.maxStarforce = 8; // 노바
        } else if (level < 128){
            this.maxStarforce = 10; // 구현 x
        } else if (level < 138){
            this.maxStarforce = 12; // 구현 x
        } else {
            this.maxStarforce = 15; // 타일런트
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
    
    public static double succededCount(NumberOfItems_Superior[] siArr, int success) { // 성공 확률 계산 메서드
    	int count = 0;
    	for (int i = 0; i < siArr.length; i++) {
    		if (success == siArr[i].starforce) {
    			count++;
    		}
    	}
    	return (double)count / siArr.length * 100;
    }
    
    public static String result(NumberOfItems_Superior[] siArr, int fromStarforce, int toStarforce) { // 결과값 출력 메서드
    	String result = "";
    	result += "==== " + siArr[0].level + "제 슈페리얼아이템의 " + siArr.length + "개 " + fromStarforce + "성 -> " + toStarforce + "성까지의 강화 ==== <br>";
    	result += "추가 설명 - 스타캐치 " + Item.starCatchToString(siArr[0].succededCatch) +"<br>";
    	return result;
    }
    
    public void goTo(int toStarforce, int numberOfRealItems) {
			do {
                // 강화 시작
                int percent = (int)(Math.random() * 100000); 
                if (starforce == 0) {
                    upgradePercent = (int)((50 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch); //합연산, 곱연산 같이 적용되는 식으로 변경
                    if (percent < upgradePercent) {
                        starforce++;
                        variableStarforce = 0;
                    } 
                } else if (starforce == 1) {
                    upgradePercent = (int)((50 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
                } else if (starforce == 2) {
                    upgradePercent = (int)((45 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
                } else if (starforce < 5) {
                    upgradePercent = (int)((40 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
                } else if (starforce == 5) {
                    upgradePercent = (int)((40 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
                } else if (starforce == 6) {
                    upgradePercent = (int)((40 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
                } else if (starforce == 7) {
                    upgradePercent = (int)((40 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
                } else if (starforce == 8) {
                    upgradePercent = (int)((40 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
                } else if (starforce == 9) {
                    upgradePercent = (int)((37 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
                } else if (starforce == 10) {
                    upgradePercent = (int)((35 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
                } else if (starforce == 11) {
                    upgradePercent = (int)((35 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
                } else if (starforce == 12) {
                    upgradePercent = (int)((3 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
                } else if (starforce == 13) {
                    upgradePercent = (int)((2 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
                } else if (starforce == 14) {
                    upgradePercent = (int)((1 * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
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
            } while(starforce != toStarforce && numberOfRealItems > destroyCount);
        
    }
}
