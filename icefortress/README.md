# [리부트] 얼음요새 길드 홈페이지
---
### 개발 환경  
* Java (jdk-15)
* JSP 
* JavaScript (Jquary-3.5.1.min)
* MySQL (8.0)
* HTML5+CSS3 (Bootstrap-3.3.2)
* eclipase IDE (2020-12)
* apache-tomcat-9.0.41
---
### 추가 계획
1. ~~회원가입, 회원 수정, 회원 탈퇴, 로그인, 로그아웃~~ - 2021.01.27 완료
2. ~~스타포스 시행~~ - 2021.02.02 완료  
  (1) ~~스타포스 성공 확률 - 여러가지 선택지 선택 후 수행 -> 결과 반환 (아이템 개수 기준, 메소 기준)~~ - 2021.01.31 완료  
  (2) ~~스타포스 평균 - 여러가지 선택지 선택 후 수행 -> 결과 반환 + 데이터베이스 추가(일정 기준 이상) + 누적합산 추가~~ - 2021.02.02 완료 (기준 : 10000개 이상)  
    1) ~~결과에 대한 게시판 추가 (자동)~~ - 게시판 대신 제출한 세부사항으로 검색하여 데이터를 가져오는 형식(위의 강화 결과와 같은 형식)  
3. ~~스프레드 시트 (노블포인트 확인 가능한 곳~~ - 길드원 등급 이상 제한) - 2021.02.03 완료  
4. ~~길드원~~ - 2021.02.03 완료  
5. ~~QnA 게시판~~ - 2021.02.11 완료
6. 길드 운영진 한정 관리페이지
7. 관리자 한정 관리페이지
8. MVC2 모델로 변경
---
## 홈페이지 기능 설명
---
1. 비로그인 기능
2. 비길드원 기능
3. 길드원 기능
4. 운영진 기능
5. 관리자 기능
---
### 비로그인 기능
* 메인화면만 볼 수 있음 + 회원가입
---
### 비길드원 기능
* 비로그인 기능 ++  
1. 스타포스 성공 확률 - (노말아이템, 슈페리얼 아이템) 세부사항 작성 후 수행 -> 결과 반환 (아이템 기준, 메소 기준)  
2. 스타포스 평균 - (노말아이템, 슈페리얼 아이템) 세부사항 작성 후 수행 -> 결과 반환 + 표본 개수 10000개 이상일 경우 레코드 자동 추가  
  (1) 스타포스 누적 결과 - (노말아이템, 슈페리얼 아이템) 세부사항 작성 후 검색 -> 데이터베이스에서 조건에 맞는 레코드를 결과 형식으로 출력  
3. 길드 구성원 확인
4. QnA 게시판 사용 가능 (본인글과 본인글에 대한 답변만 확인 가능)  
5. 자유 게시판 사용 가능
---
### 길드원 기능
* 비길드원 기능 ++  
1. 본인 길드노블레스 스킬의 사용가능여부 확인 (스프레드 시트)  
2. 길드 게시판 사용 가능
---
### 운영진 기능
* 길드원 기능 ++  
1. QnA게시판에서 모든 글 확인 가능  
2. 운영진 회의록 게시판 사용 가능  
3. 게시판의 모든 글 삭제 가능
---
### 관리자 기능
* 운영진 기능 ++  
---
