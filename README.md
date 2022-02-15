# BTAKA 프로젝트

## 스터디 모집 및 멘토링을 위한 컨셉 서비스

### 프로젝트 구성 및 기술 스택:

- BACKEND
  - 프로젝트 구성
    * [API-GW] : API 게이트웨이
    * [AUTH] : 로그인, 회원가입 및 회원 검증 기능
    * [BOARD-STUDY] : 스터디 모집 글 관리
    * [EUREKA] : API 상태 체크 및 LB 구성을 위한 EUREKA 서버
    * [USER] : 사용자의 상세정보 (결제 정보 등등등) 을 관리하기 위한 서버
    * [ORDER] (향후 용도 변경 예정)
    * [COMMON] : 각 서버들의 공통 코드
    * [JWT] : 각 서버 API 권한 관리를 위해 도입
  - 기술 스택
    * [Spring Boot 2.6.3]
    * [Spring Webflux]
    * [Spring Cloud Gateway]
    * [Spring Cloud Eureka]
    * [Spring Security]
    * [Spring Data]
    * [Mongodb]
    * [Postres (R2DB)]
    * [Graphql] (향후 도입 가능하다면...)
    * [Docker Swarm]

- FRONTEND
  - 프로젝트 구성
    * ???
  - 기술 스택
    * [Reactjs]

### 프로젝트 구동 방법
1. Docker 설치 (https://www.docker.com/products/docker-desktop)
2. 터미널 창을 이용하여 env 디렉토리로 이동
3. 컨테이너 실행 명령어 입력 (docker-compose up)
4. 컨테이너 실행 후 env > sql > mongo.sql 실행
5. btaka-eureka 서버 구동 (구동중이지 않을 경우 각 서버에서 dicovery client 에러 발생)
6. btaka-api-gw 서버 구동 (아직 api-gw 연계 안됨)
7. btaka-board-study, btaka-auth 서버 구동

### 프로젝트 정보 (Notion)
작성 예정...

* [프로젝트 프로토 타입 완료 후 Notion 작성...]

