# 🥗Health Partner
![HealthPartner](https://github.com/HMisu/Health_Partner_React/assets/37448404/348b09b4-da6a-4c7a-a068-ba1e93254f49)


## 프로젝트 소개
식단 및 다이어리 기록 웹 사이트
- Spring Boot + React
## 1. 개발 기간
2024.02~2024.05
## 2. 팀원 구성
- 1인 프로젝트
## 3. 프로젝트 구조
<details>
<summary>구조 보기(React)</summary>

```
Health-Partner
├─.idea
├─node_modules
├─public
│  ├─assets
│  │  ├─emotion
│  │  └─screenshots
│  └─fonts
└─src
    ├─components
    │  ├─api
    │  ├─member
    │  ├─recommend
    │  ├─record
    │  ├─todo
    │  └─ui
    │      └─layout
    ├─pages
    │  ├─home
    │  ├─record
    │  ├─sign
    │  └─todo
    ├─scss
    │  └─ui
    │      └─layout
    ├─slices
    ├─store
    └─util
```
</details>
<details>
<summary>구조 보기(Spring Boot)</summary>

```
Health-Partner
├─ .gitignore
├─ build.gradle
├─ gradlew
├─ gradlew.bat
├─ settings.gradle
└─src
    ├─main
       ├─generated
       │  └─com
       │      └─bit
       │          └─healthpartnerboot
       │              └─entity
       ├─java
       │  └─com
       │      └─bit
       │          └─healthpartnerboot
       │              ├─config
       │              ├─controller
       │              ├─converter
       │              ├─document
       │              ├─dto
       │              ├─entity
       │              ├─handler
       │              ├─hash
       │              ├─jwt
       │              ├─listener
       │              ├─oauth2
       │              ├─repository
       │              │  ├─jpa
       │              │  ├─mongo
       │              │  └─redis
       │              ├─service
       │              │  └─impl
       │              └─support
       └─resources
           ├─static
           └─templates
```
</details>

## 4. ERD
![health_partner](https://github.com/HMisu/Health_Partner_React/assets/37448404/007e7505-0dff-492f-aee5-fda0e1deae33)


## 기능
### [로그인 및 회원가입]
- (1)이메일 인증을 통한 로그인 및 회원가입, (2)OAuth2 인증을 통한 소셜 로그인 두 가지 방법을 사용해 서비스를 이용할 수 있습니다.
![로그인및회원가입](https://github.com/HMisu/Health_Partner_React/assets/37448404/d8592852-0b03-4692-b071-326a381e45a0)

### [대시보드]
- 여러 정보들을 한 눈에 볼 수 있는 페이지입니다.
- 월별 및 주별로 다이어리와 일별 음수량을 조회할 수 있습니다.
- 사용자의 키, 몸무게, BMI를 그래프로 확인하고 기록할 수 있습니다.
- 하루 음수량을 버튼으로 기록할 수 있습니다.
![h-home](https://github.com/HMisu/Health_Partner_React/assets/37448404/da27474a-88a5-4499-bc12-d134a9b8ba38)

### [다이어리 목록 및 식단 분석]
- 월별 및 주별로 다이어리와 일별 음수량을 조회할 수 있습니다.
- 사용자의 기초대사량과 일일 에너지 소비량을 계산하고, 오늘 하루 기록된 식단에서 부족한 칼로리와 영양 성분을 파악합니다.
- 계산된 잉여 칼로리와 영양 성분에 따라 사용자에게 식을 추천합니다.
![h-monthly](https://github.com/HMisu/Health_Partner_React/assets/37448404/131c3735-d793-41bb-8720-2ea8e3bd2087)

### [다이어리 작성]
- 제목, 내용, 식단, 체크리스트를 기록합니다.
- 사용자가 이름으로 식품을 검색하고, 식단을 아침, 점심, 저녁, 간식으로 구분하여 기록할 수 있습니다.
![h-todo](https://github.com/HMisu/Health_Partner_React/assets/37448404/28b867e1-6d4b-471d-8fb5-069d83a6b9c5)

### [관리자 페이지:통계데이터]
- 회원 정보와 프로필 이미지를 변경할 수 있습니다.
- 일일 에너지 소비량을 계산하기 위한 활동 수준을 변경할 수 있습니다.
![h-my](https://github.com/HMisu/Health_Partner_React/assets/37448404/07108403-b7f1-413a-b49b-bf7c9f56c0e5)
