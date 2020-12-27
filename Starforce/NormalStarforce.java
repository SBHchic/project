package project.starforce;

//import java.math.BigInteger; // 10만배열도 거뜬히 가능한 수준이라 사용할 가치를 느끼지 못함
//import java.time.*; // runtime을 위해 사용 (12.9 15:40) - 수정 : runtime을 나타낼 때 LocalTime 클래스를 사용하지 않고 System.currentTimeMillis()를 사용하기 때문에 import할 이유 없음.

/* 수정 노트
 * 1. goTo()메서드 유효성 검사 (11.25 21:30)
 *  - 추가 수정 : 예외 발생 (RuntimeException) (12.13 22:02)
 * 2. 스타포스캐치 적용 (11.27 4:50)
 *  - 추가 수정 : 합연산, 곱연산 계산을 위해 변수를 나눔 (0 : 스타포스캐치x, 1 : 합연산, 2 : 곱연산) (12.11 17:08)
 * 3. 파괴방지 2배가격 수정 - 계산 실수 (12.9 4:03) 
 * 4. 소요시간 생성(Runtime) - LocalTime클래스에서 System.currentTimeMillis()로 변경 (12.9 16:30)
 * 5. MyMeso클래스(자신의 자본)를 생성 후 서로의 배열을 비교하여 본인의 자본에서 성공할 확률 계산 (12. 10 2:10)
 * 6. 확률부분을 double타입에서 int타입으로 변경 (기존에 있는 합연산 부분만) (난수는 1000) (12.10 4:07)
 *  - double타입과 int타입 비교 결과 거의 일치한다는 것을 확인(천만배열 유효성 검사) 
 *  - 추가 수정 1) : 곱연산과 합연산 goTo메서드 통합을 위해 난수를 1000 -> 100000으로 변경 (12.11 16:24)
 *  - 추가 수정 2) : 통합메서드를 위해 경우의 수를 나누고 확률에 곱연산, 합연산 통합 (두개의 식을 만들지 않기 위해) (12.11 17:08)
 * 7. 파괴방지 17성 전까지 가능하도록 수정 (12.10 21:00)
 *  - 1) destroyPercent배열에 21 하나 더 추가(17성 이후와 17성 이전의 파괴확률을 나누기 위해) + 17성 이상의 destroyPercent배열의 index에 1을 더해줌
 *  - 2) 파괴방지가 포함된 생성자에서 for문 범위를 바꿈 (i < destroyPercent.length -> i < 4)
 * 8. 레벨 별 스타포스 한계치 고정 유효성 추가 (maxItemStarforce메서드 + 생성자 내부에 추가) (12.11 2:05)
 * 9. 할인에서 정확한 값보다 사소한 오차를 내고 최대한 오버플로우를 발생시키기 않는 방법으로 결정 (강화시도마다 0~99메소 오차라서 유의미한 오차는 아님) (12.11 3:57)
 * 10. 메서드 속도 향상을 위한 간단화 수정
 *  - 1) 찬스타임 안의 upgradeCount++을 지우고 마지막에 더함(upgradeCount += chanceTime;) (12.12 4:10) - 36줄 줄임
 *  - 2) sumUpgradePrice += starforcePrice; 의 중복을 피함 (12.14 3:04) - 152줄 줄임
 *  - 3) 파괴확률에서 variableStarforce = 0; 을 지움 (10성에서 성공하면 자동적으로 variableStarforce = 0이기 때문) (12.14 3:04) - 47줄 줄임
 *  - 4) min, max 통합 메서드 생성 (12.14 21:01) - 18줄 줄임 (메서드 3개, 출력 3개 줄임)
 * 11. 원활한 수정과 실험을 위한 패키지 구성 (Final, Clone, Test, Trash) (12.12 4:13)
 * 12. PC방, MVP등급 별 할인 적용 (12.12 5:41) + 할인 적용으로 인한 파괴방지 공식 재수정(12.12 16:31)
 *  - 추가 수정 : 할인 적용 괄호가 살짝 어긋난 점 확인 후 수정(12.13 2:45)
 * 13. 이벤트 별 goTo메서드 선택 가능 (switch문 이용) (12.13 3:09)
 *  - 1) 30 퍼센트 할인 (곱연산) (goTo메서드 내 switch문 case 1:) (12.13 2:35)
 *  - 2) 10성까지 스타포스 1+1 (goTo메서드 내 switch문 case 2:) (12.13 2:53)
 *  - 3) 5의배수 100퍼센트 강화 성공 (goTo메서드 내 switch문 case 3:) (15성 까지) (12.13 3:08)
 * 14. 출력 편의성 수정 (12.13 4:22)
 *  - 추가 설명(스타캐치, 파괴방지, PC방 할인, MVP 할인), 이벤트
 * 15. 파괴방지만 되고 파괴방지 가격은 오르지 않는 현상 수정(12.13 5:35) 12성 가격을 뺀 17성까지의 비용을 비교해서 적용됨을 확인함.
 */

/* 추가해야 할 부분
 * 1. 어플처럼 구현(최종) + 클래스로 가능하면 나눠서 구현(상속포함) (노말, 슈페리얼)
 * OK - 2. (해결 수정노트 13. 1) 스타포스 30퍼 할인 이벤트 - 전구간
 *  - 할인된 비용: 강화비용 × (1 - MVP+PC방) × 0.7
 *  - 파괴방지 비용은 할인된 비용의 2배가 이나리 할인 비용에 원래 비용을 더한 값
 * OK - 3. (해결 수정노트 13. 2) 스타포스 1+1 (10성까지) 이벤트
 * OK - 4. (해결 수정노트 13. 3) 스타포스 5성배수 100퍼센트 성공 (15성까지) 이벤트
 * OK - 5. (해결 : 수정노트 6.) 확률부분이 clear한건지 확인해야 함 (float의 0가까이의 신뢰도) + 스타캐치가 합연산인지 곱연산인지 선택
 * OK - 1) (해결 : 수정노트 6.) float에서 0 가까이의 신뢰도 - OK (float -> int형으로 변경하면서 신뢰도 상승) 
 * OK - 1) (해결 : 수정노트 6.) 합연산 : 만약 4.5%p 증가한다면 난수를 int로 설정해서 1000개중에서 뽑으면 됨
 * OK - 2) (해결 : 수정노트 6. 1) 곱연산 : 만약 1.045배로 증가한다면 난수를 int로 설정해서 100000개중에서 뽑으면 됨
 * OK - double타입과 int타입 난수 뽑는 속도를 실험해 본 결과 double타입 100보다 int타입 100000이 조금 더 빠름 - 시간 차이는 거의 없음
 * OK - 따라서 double에서 int로 변경 예정
 * OK - 6. (해결 : 수정노트 12.) MVP 등급별 할인 확인 후 추가 (공식 확인해야함) - 파괴방지와의 관계 (할인된 가격에 그냥 파괴방지 가격 더한 값)
 *  - 할인 혜택은 17성까지만 적용되며 슈페리얼 아이템은 할인 대상에서 제외
 *  - 실버 : 3%, 골드 : 5%, 다이아,레드 : 10%
 *  - 할인된 비용: 강화비용 × (1 - MVP+PC방)
 * OK - 7. (해결 : 수정노트 12.) PC방 할인 추가 (공식 확인해야함) - 파괴방지와의 관계 (할인된 가격에 그냥 파괴방지 가격 더한 값)
 *  - PC방 할인 : 5%
 * 8. 파괴확률과 스타캐치의 상관관계 - 스타캐치가 파괴확률을 줄여주는 것인지 (이건 공개된 자료가 아니라 건드리질 못함) (무기한 보류)
 * 9. 구간별 스타캐치, 파괴방지 체크
 *  - 1) 구간별 스타캐치 체크
 *  - 2) 구간별 파괴방지 체크
 * OK - 10. (해결 : 수정노트 5.)본인 자본을 설정하고 본인 자본에서 가능할 확률 계산
 * 11. 몇성까지 성공할 확률 (최소, 최대, 평균) - 이건 굳이 필요한가?? (우선순위 미룸)
 * OK - 12. (해결 : 수정노트 4.) 시작시간 소요시간 확인 할 수 있는 방법
 * 13. 한글 문법적인 면 수정
 * 14. 아이템 별 강화로 인한 능력치 향상 폭 확인
 * OK - 15. (해결 : 수정노트 11.) 패키지 별로 나누어서(class clone을 생성) 수정한게 괜찮은지 확인할 수 있어야 함. (clone, tmp, result 패키지)
 * 16. 토드로 나눠서 인스턴스를 고려할 수 있는지 생각해보기
 * 17. 레벨 별로 스타포스 한계치 고정 
 * OK - 1) (해결 : 수정노트 8.) 일반템 : 95 미만 5성, 95~107 8성, 108~117 10성, 118~127 15성, 128~137 20성, 138이상 25성
 *  - 2) 슈페리얼템 : 95 미만 3성, 95~107 5성, 108~117 8성, 118~127 10성, 128~137 12성, 138이상 15성
 * OK - 18. (해결 : 수정노트 7.) 파괴방지 다시 짜야댐 (12.10 20:53 현재 17성 이상에서도 파괴방지가 적용됨)
 * OK - 19. (해결 : 수정노트 9.) 진짜 오버플로우가 없으려나..? - 근데 아직까지는 나오지 않은 듯 (나왔다면 최소비용에서 음수가 나왔어야함) 따라서 보류! 
 *  - 1) try-catch문 사용, 예외는 ArithmeticException 인듯
 *  - 2) itemArr[i].sumUpgradePrice가 음수인지를 확인하면 됨(완벽한 방법은 아님)
 *  - 할인을 적용할 때 생각 - 원래 메소로 계산할 경우 오버플로우 발생할 가능성은 높은 대신 정확한 가격을 얻을 수 있지만
 *  - 사소한 오차를 내고 최대한 오버플로우를 발생시키기 않는 방법을 택하는게 효율적일 듯 (강화시도마다 0~99메소 오차라서 유의미한 오차는 아님)
 * 20. 속도 향상을 위한 간단화 작업
 *  - 1) goTo메서드에서는 if-else if 문이 switch문보다 조금 더 빠르다는 걸 확인했음 (17성, 22성 둘다 runtime으로 확인함) (12.12 3:49)
 *  - 2) 찬스타임 안의 upgradeCount++을 지우고 마지막에 더함 (12.12 4:10)
 *  - 3) ArrayList로 다시 넣고 정렬하는것 보다 원래대로 배열에서 for문으로 최소 최대 찾는게 더 빠름 (12.13 21:50)
 * OK - 21. (해결 : 수정노트 15.) 파괴방지만 되고 파괴방지 가격은 안오름 - 생성자에 인스턴스변수 저장을 안함
 * OK - 22. (해결 : 수정노트 10. 4) min, max 메서드 통합하기 (if - else if문 이용하기) 
 */

class MyMeso{ // 본인의 자본 클래스
	
	// 인스턴스 변수
	long recentMeso;

	// 생성자
	MyMeso() {
		this.recentMeso = 1000000000L;
	}

	MyMeso(long recentMeso){
		this.recentMeso = recentMeso;
	}

	// 메서드
	
}

public class NormalStarforce {

	// 인스턴스 변수
	int level = 0;
	int starforce = 0;
	int upgradeCount = 0; // 강화 횟수 - 찬스타임도 포함됨
	int destroyCount = 0; // 파괴 횟수
	long sumUpgradePrice = 0; // 총 강화 비용
	long starforcePrice = 0;
	int upgradePercent = 0;
//	byte downgradePercent = (byte)(100 - upgradePercent - destroyPercent[i]);
	int[] destroyPercent = {600, 1300, 1400, 2100, 2100, 2800, 7000, 19400, 29400, 39600, 0}; // 파괴 확률을 배열로 만들어 적절한 곳에 적용 - (17성 전까지로 수정하기위해 같은 21을 한개 더 넣음) (12.10 21:00)
	byte variableStarforce = 0;
	int chanceTime = 0;
	// 스타포스캐치 적용 (11.27 4:50)
	int sumOfStarforceCatch; // 스타포스캐치 합연산
	float multipleOfStarforceCatch; // 스타포스캐치 곱연산
	boolean ignoreDestroy; // 파괴방지 여부
	int maxStarforce;
	byte mapleEvent = 0;
	float discountPCRoom = 0f;
	float[] discountMVPGrade = {0f, 0.03f, 0.05f, 0.1f}; //실버 : 3%, 골드 : 5%, 다이아,레드 : 10%
	float discountMVP = 0f;
	
	
	// 생성자
	NormalStarforce() { // 기본생성자 = 160제 0성부터 스타캐치 x 
		this(160, 0, (byte)0, false, (byte)0);
	}
	
	NormalStarforce(int level, int fromStarforce) { // level선택, fromStarforce 몇성부터 시작
		this.level = level;
		this.starforce = fromStarforce;
		this.maxStarforce = maxItemStarforce(level); // 레벨 별 최대 스타포스
	}
	
	NormalStarforce(int level, int fromStarforce, byte succededCatch) { // level선택, fromStarforce 몇성부터 시작, 스타캐치 여부 선택
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

	NormalStarforce(int level, int fromStarforce, byte succededCatch, boolean ignoreDestroy) { // level선택, fromStarforce 몇성부터 시작, 스타캐치 여부 선택, 파괴방지 여부 선택
		this(level, fromStarforce, succededCatch);
		this.ignoreDestroy = ignoreDestroy;
		if (ignoreDestroy == true) { // 파괴방지를 했을 경우에 - (17성 전까지로 수정) (12.10 21:00)
			for (int i = 0; i < 4; i++) {
				destroyPercent[i] = destroyPercent[10]; // goTo() 메서드의 destroyPercent[3]까지를 다 0으로 돌림 (destroyPercent[10] = 0 이기 때문)
			}
		}
	}
	// 이벤트를 위한 생성자 (0 : x, 1 : 30퍼 할인, 2: 1+1, 3. 15 16 100퍼)
	NormalStarforce(int level, int fromStarforce, byte succededCatch, boolean ignoreDestroy, byte mapleEvent) {
		this(level, fromStarforce, succededCatch, ignoreDestroy);
		this.mapleEvent = mapleEvent; 
	}
	// PC방 할인여부
	NormalStarforce(int level, int fromStarforce, byte succededCatch, boolean ignoreDestroy, byte mapleEvent, boolean discountPCRoom) {
		this(level, fromStarforce, succededCatch, ignoreDestroy, mapleEvent);
		if (discountPCRoom == true) {
			this.discountPCRoom = 0.05f;
		}
	}
	// MVP 등급 할인여부
	NormalStarforce(int level, int fromStarforce, byte succededCatch, boolean ignoreDestroy, byte mapleEvent, boolean discountPCRoom, byte discountMVPGrade) {
		this(level, fromStarforce, succededCatch, ignoreDestroy, mapleEvent, discountPCRoom);
		if (discountMVPGrade == 1) {
			this.discountMVP = this.discountMVPGrade[1];
		} else if (discountMVPGrade == 2) {
			this.discountMVP = this.discountMVPGrade[2];
		} else if (discountMVPGrade == 3) {
			this.discountMVP = this.discountMVPGrade[3];
		}
	}
	
	// 메서드
    void goTo(int toStarforce) { // 스타포스를 몇성까지 올릴 것인지
		// 유효성 검사 (11.25 21:30)
		// 레벨 별 스타포스 한계치 고정 유효성 추가 (12.11 2:05)
		if (starforce >= toStarforce || starforce < 0 || maxStarforce <= toStarforce || maxStarforce <= starforce) { // 첫번째 조건에서 파괴된 경우를 위해 반복문에서 뺌
			throw new RuntimeException("유효하지 않은 강화입니다."); // 잘못됨을 느끼면 아이템의 개수가 몇개인지 상관 없이 예외 던짐 (12.13 22:02)
		} else {
			switch(mapleEvent){
				case 0: // 이벤트 x
				do {
					// 강화 시작
					upgradeCount++;
					int percent = (int)(Math.random() * 100000); // double -> int 수정, 합연산 수정을 위해 1000으로 생성 -> 곱연산 통합을 위해 100000으로 생성
					if (starforce < 3) {
						starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * (starforce + 1)/25))*(1 - discountMVP - discountPCRoom)/100);
						upgradePercent = (int)(((95 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch); //합연산, 곱연산 같이 적용되는 식으로 변경
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
					} else if (starforce < 12) { // 11성 까지는 파괴방지가 없음
						starforcePrice = (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)*(1 - discountMVP - discountPCRoom))/100;
						upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						sumUpgradePrice += starforcePrice;
						if (percent < upgradePercent) {
							starforce++;
							variableStarforce = 0;
						} else {
							starforce--;
							variableStarforce--;
							if (variableStarforce == -2) { // 찬스타임
		//						System.out.println("현재 스타포스 : " + starforce);
		//						System.out.println("누적 스타포스변화 : " + variableStarforce);
								starforcePrice = (long)((1000+ Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/400)*(1 - discountMVP - discountPCRoom))/100;
		//						System.out.println("현재 강화가격 : " + starforcePrice);
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
		//						System.out.println("강화 후 현재 스타포스 : " + starforce);
		//						System.out.println("강화 후 누적 스타포스 변화 : " + variableStarforce);
								chanceTime++;
							}
						}
					} else if (starforce == 12) { // 12성부터 파방 가능
						// 
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
						// 17성 이후부터는 파괴방지를 할 수 없음 + PC방, MVP등급 할인 못받음
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
							/* 24성에서는 떨어져도 찬스타임이 생길 이유가 전혀 없음
							if (variableStarforce == -2) {
								starforcePrice = (long)(1000+ (Math.pow((double)level, 3.0) * Math.pow(((double)(starforce) + 1), 2.7)/200))/100;
								sumUpgradePrice += starforcePrice;
								starforce++;
								variableStarforce = 0;
								chanceTime++;
							}
							*/
						}
					}
				} while(starforce != toStarforce);
				upgradeCount += chanceTime; // 찬스타임안의 upgradeCount++을 지우고 마지막에 더함 (12.12 4:10)
				break;

				case 1: // 30 퍼센트 할인 (곱연산) (12.13 2:35)
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
						// 
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
				} while(starforce != toStarforce);
				upgradeCount += chanceTime;
				break;

				case 2: // 10성까지 스타포스 1+1 (12.13 2:53)
				do {
					upgradeCount++;
					int percent = (int)(Math.random() * 100000); 
					if (starforce < 3) {
						starforcePrice = (long)((1000+ (Math.pow((double)level, 3.0) * (starforce + 1)/25))*(1 - discountMVP - discountPCRoom)/100);
						upgradePercent = (int)(((95 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch); 
						sumUpgradePrice += starforcePrice;
						if (percent < upgradePercent) {
							starforce++;
							starforce++; // 10성까지 스타포스 1+1 이므로 스타포스 증감연산자 두번
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
								starforce++; // 11성에서 떨어지면 10성이므로 스타포스 증감연산자 두번 적용
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
				} while(starforce != toStarforce);
				upgradeCount += chanceTime;
				break;

				case 3: // 5의배수 100퍼센트 강화 성공 (15성 까지) (12.13 3:08)
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
						if (starforce == 5) { // 5성일 때 강화 확률 100퍼센트
							upgradePercent = 100000;
						}else {
							upgradePercent = (int)(((100 - (5 * starforce)) * 1000 + sumOfStarforceCatch) * multipleOfStarforceCatch);
						}
						sumUpgradePrice += starforcePrice;
						if (percent < upgradePercent) {
							starforce++;
						} 
					} else if (starforce == 10) { // 10성일 때 강화 확률 100퍼센트
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
					} else if (starforce == 15) { // 15성일 때 강화 확률 100퍼센트
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
				} while(starforce != toStarforce);
				upgradeCount += chanceTime;
				break;
			}
		}
	}
	
	// min, max 통합메서드 구현 (12.14 20:43)
	static String sumUpgradePriceMinMax(NormalStarforce[] itemArr){
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
/* 메서드 두개를 통합하여 필요없어짐
	static String minSumUpgradePrice(NormalStarforce[] itemArr) { // 배열 안의 인스턴스 중 강화 최소비용 (11.24 00:30)
		long tmp = itemArr[0].sumUpgradePrice;
		for (int i = 0; i < itemArr.length; i++) {
			if (tmp > itemArr[i].sumUpgradePrice) {
				tmp = itemArr[i].sumUpgradePrice;
			}
		}
		return itemArr.length + "개의 아이템 중 스타포스 강화 최소비용은 " + tmp + "00메소 입니다.";
	}
	
	static String maxSumUpgradePrice(NormalStarforce[] itemArr) { // 배열 안의 인스턴스 중 강화 최대비용 (11.24 00:30)
		long tmp = itemArr[0].sumUpgradePrice;
		for (int i = 0; i < itemArr.length; i++) {
			if (tmp < itemArr[i].sumUpgradePrice) {
				tmp = itemArr[i].sumUpgradePrice;
			}
		}
		return itemArr.length + "개의 아이템 중 스타포스 강화 최대비용은 " + tmp + "00메소 입니다.";
	}
*/	
	static String averageSumUpgradePrice(NormalStarforce[] itemArr) { // 배열 안의 인스턴스의 강화 평균비용 (11.24 00:30)
		long totalsum = 0;
		for (int i = 0; i < itemArr.length; i++) {
			totalsum += itemArr[i].sumUpgradePrice;
			}
		long average = totalsum/itemArr.length;
		return itemArr.length + "개의 아이템의 평균적인 스타포스 강화 비용은 " + average + "00메소 입니다.";
	}
	
	static String upgradeCountMinMax(NormalStarforce[] itemArr){
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
/*	
	static String minUpgradeCount(NormalStarforce[] itemArr) { // 배열 안의 인스턴스 중 강화 최저횟수 (11.24 00:30)
		long tmp = itemArr[0].upgradeCount;
		for (int i = 0; i < itemArr.length; i++) {
			if (tmp > itemArr[i].upgradeCount) {
				tmp = itemArr[i].upgradeCount;
			}
		}
		return itemArr.length + "개의 아이템 중 가장 적은 강화 횟수는 " + tmp + "번입니다.";
	}
	
	static String maxUpgradeCount(NormalStarforce[] itemArr) { // 배열 안의 인스턴스 중 강화 최대횟수 (11.24 00:30)
		long tmp = itemArr[0].upgradeCount;
		for (int i = 0; i < itemArr.length; i++) {
			if (tmp < itemArr[i].upgradeCount) {
				tmp = itemArr[i].upgradeCount;
			}
		}
		return itemArr.length + "개의 아이템 중 가장 많은 강화 횟수는 " + tmp + "번입니다.";
	}
*/	
	static String averageUpgradeCount(NormalStarforce[] itemArr) { // 배열 안의 인스턴스 중 강화 평균횟수 (11.24 00:30)
		long totalsum = 0;
		for (int i = 0; i < itemArr.length; i++) {
			totalsum += itemArr[i].upgradeCount;
		}
		long average = totalsum/itemArr.length;
		return itemArr.length + "개의 아이템의 평균적인 강화 횟수는 " + average + "번입니다.";
	}

	static String destroyCountMinMax(NormalStarforce[] itemArr){
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
/*	
	static String minDestroyCount(NormalStarforce[] itemArr) { // 배열 안의 인스턴스 중 파괴 최저횟수 (11.25 20:30)
		long tmp = itemArr[0].destroyCount;
		for (int i = 0; i < itemArr.length; i++) {
			if (tmp > itemArr[i].destroyCount) {
				tmp = itemArr[i].destroyCount;
			}
		}
		return itemArr.length + "개의 아이템 중 가장 적은 파괴 횟수는 " + tmp + "번입니다.";
	}
	
	static String maxDestroyCount(NormalStarforce[] itemArr) { // 배열 안의 인스턴스 중 파괴 최대횟수 (11.25 20:30)
		long tmp = itemArr[0].destroyCount;
		for (int i = 0; i < itemArr.length; i++) {
			if (tmp < itemArr[i].destroyCount) {
				tmp = itemArr[i].destroyCount;
			}
		}
		return itemArr.length + "개의 아이템 중 가장 많은 파괴 횟수는 " + tmp + "번입니다.";
	}
*/	
	static String averageDestroyCount(NormalStarforce[] itemArr) { // 배열 안의 인스턴스의 파괴 평균횟수 (11.25 20:30)
		long totalsum = 0;
		for (int i = 0; i < itemArr.length; i++) {
			totalsum += itemArr[i].destroyCount;
		}
		long average = totalsum/itemArr.length;
		return itemArr.length + "개의 아이템의 평균적인 파괴 횟수는 " + average + "번입니다.";
	}

	static String chanceTimeCountMinMax(NormalStarforce[] itemArr){
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
/*	
	static String minChanceTimeCount(NormalStarforce[] itemArr) { // 배열 안의 인스턴스 중 찬스타임 최소횟수 (11.25 20:30)
		long tmp = itemArr[0].chanceTime;
		for (int i = 0; i < itemArr.length; i++) {
			if (tmp > itemArr[i].chanceTime) {
				tmp = itemArr[i].chanceTime;
			}
		}
		return itemArr.length + "개의 아이템 중 가장 적은 찬스타임 횟수는 " + tmp + "번입니다.";
	}
	
	static String maxChanceTimeCount(NormalStarforce[] itemArr) { // 배열 안의 인스턴스 중 찬스타임 최대횟수 (11.25 20:30)
		long tmp = itemArr[0].chanceTime;
		for (int i = 0; i < itemArr.length; i++) {
			if (tmp < itemArr[i].chanceTime) {
				tmp = itemArr[i].chanceTime;
			}
		}
		return itemArr.length + "개의 아이템 중 가장 많은 찬스타임 횟수는 " + tmp + "번입니다.";
	}
*/	
	static String averageChanceTimeCount(NormalStarforce[] itemArr) { // 배열 안의 인스턴스의 찬스타임 평균횟수 (11.25 20:30)
		long totalsum = 0;
		for (int i = 0; i < itemArr.length; i++) {
			totalsum += itemArr[i].chanceTime;
		}
		long average = totalsum/itemArr.length;
		return itemArr.length + "개의 아이템의 평균적인 찬스타임 횟수는 " + average + "번입니다.";
	}
	
	// 레벨 별 스타포스 한계치 고정 (12.11 1:57)
	// 일반템 : 95 미만 5성, 95~107 8성, 108~117 10성, 118~127 15성, 128~137 20성, 138이상 25성
	int maxItemStarforce(int level) {
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

	static String starCatchToString(byte succededCatch) { // 스타캐치 출력 편의성
		
		if(succededCatch == 0){
			return "x";
		} else if (succededCatch == 1){
			return "합연산(4.5%p)";
		} else {
			return "곱연산(1.045배)";
		}
		
		/* 왜 switch문은 안되는거지...??
		switch(succededCatch){
			case 0:
			return "x".toString();
			break;
			case 1:
			return "합연산(4.5%p)".toString();
			break;
			case 2:
			return "곱연산(1.045배)".toString();
			break;
		}
		*/
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
	
	/* (11.27 6:17)
	 * 160제 17성 - 약 1354만메소의 평균적인 결과가 나옴( *100) -> 보정 : 7000억 배열 정도 사용 가능 (10만배열 소요시간 : 3초)
	 * 160제 22성 - 약 2억메소의 평균적인 결과가 나옴( *100) -> 보정 : 400억 배열 정도 사용 가능 (10만배열 소요시간 : 14초)
	 * 160제 23성 - 약 40억메소의 평균적인 결과가 나옴( *100) -> 보정 : 20억 배열 정도 사용 가능 (10만배열 소요시간 : 3분 19초)
	 * 160제 24성 - 약 1793억메소의 평균적인 결과가 나옴( *100) -> 보정 : 5000만 배열 정도 사용 가능 (10만배열 소요시간 : 2시간 36분 15초)
	 * 160제 25성 - 약 18조메소의 평균적인 결과가 나옴( *100) -> 보정 : 50만 배열 정도 사용 가능 (1000배열 소요시간 : 2시간 43분 37초)
	 */
	
	public static void main(String[] args) {

		//시간 구축
		/* LocalTime클래스에서 System.currentTimeMillis()로 변경 (수정노트 4.)
		LocalTime beforeRuntime = LocalTime.now(); // 메서드를 돌리기 전 시간
		int bfRtHour = beforeRuntime.getHour();
		int bfRtMinute = beforeRuntime.getMinute();
		int bfRtSecond = beforeRuntime.getSecond();
		int bfRtSecondChange = bfRtHour *60*60 + bfRtMinute * 60 + bfRtSecond; // 초단위로 환산
		*/
		long beforeRun = System.currentTimeMillis(); // 메서드를 돌리기 전의 시간

		// 변수 수정해야하는 부분
		int level = 200; // 아이템 레벨
		int fromStarforce = 0; // 시작 스타포스
		int toStarforce = 17; // 도달 스타포스
		byte succededCatch = 1; // 스타캐치 여부 (0 : 스타포스캐치x, 1 : 합연산, 2 : 곱연산)
		boolean ignoreDestroy = false; // 파괴방지 여부
		int countItem = 100000; // 아이템의 개수 (표본)
		boolean discountPCRoom = false; // PC방 할인
		byte discountMVPGrade = 0; //MVP 등급 할인 (0 : x, 1 : 실버, 2 : 골드, 3 : 다이아, 레드)
		byte mapleEvent = 0; // 0 : x, 1 : 30퍼 할인, 2: 1+1, 3. 15 16 100퍼
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
		NormalStarforce[] itemArr = new NormalStarforce[countItem];
		for (int i = 0; i < countItem; i++) {
			itemArr[i] = new NormalStarforce(level, fromStarforce, succededCatch, ignoreDestroy, mapleEvent, discountPCRoom, discountMVPGrade);
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
		System.out.println("추가 설명 : 스타캐치 " + starCatchToString(succededCatch) + ", 파괴방지 : " + booleanToString(ignoreDestroy)
									 + ", PC방 할인 : " + booleanToString(discountPCRoom) + ", MVP 등급 : " + MVPGradeToString(discountMVPGrade));
		System.out.println("메이플 이벤트 적용 : " + mapleEventToString(mapleEvent));

		System.out.println(sumUpgradePriceMinMax(itemArr));
		System.out.println(averageSumUpgradePrice(itemArr));
		System.out.println(upgradeCountMinMax(itemArr));
		System.out.println(averageUpgradeCount(itemArr));
		System.out.println(destroyCountMinMax(itemArr));
		System.out.println(averageDestroyCount(itemArr));
		System.out.println(chanceTimeCountMinMax(itemArr));
		System.out.println(averageChanceTimeCount(itemArr));
//		System.out.println(recentMeso + "메소로 성공할 확률은 " + succededRecentPercent + "%입니다."); // 현재자본으로 성공할 확률 필요할 때 사용
		

		/* LocalTime클래스에서 System.currentTimeMillis()로 변경 (수정노트 4.)
		LocalTime afterRuntime = LocalTime.now(); // 메서드를 돌린 후의 시간
		int afRtHour = afterRuntime.getHour();
		if (afRtHour < bfRtHour) {
			afRtHour += 24;
		}
		int afRtMinute = afterRuntime.getMinute();
		int afRtSecond = afterRuntime.getSecond();
		int afRtSecondChange = afRtHour *60 *60 + afRtMinute * 60 + afRtSecond;
//		System.out.println("beforeRuntime : " + beforeRuntime);
//		System.out.println("afterRuntime : " + afterRuntime);
		*/
		long afterRun = System.currentTimeMillis(); // 메서드를 돌린 후의 시간

		// runtime 생성
		long runtime = afterRun - beforeRun; 
//		System.out.println(runtime); // runtime 확인
		System.out.printf("RunTime : %d시간 %d분 %d초%n", runtime/ (60*60*1000), runtime /(60*1000) % 60, runtime /1000 % 60);
		

	}

}