# MVP 정의

## 1. MVP 목적

MVP의 목적은 전체 서비스의 모든 기능을 한 번에 구현하는 것이 아니라, 핵심 가치가 실제로 동작하는 최소 기능 세트를 빠르게 검증하는 것이다.

이 프로젝트에서 MVP는 다음 질문에 답할 수 있어야 한다.

- 유튜브 데이터를 주기적으로 수집할 수 있는가
- 수집할 데이터를 저장하고 다시 조회할 수 있는가
- 현재 뜨는 영상 흐름을 보여줄 수 있는가
- 채널 단위의 기본 성장 흐름을 보여줄 수 있는가
- 숏폼과 롱폼을 구분할 수 있는가

또한 MVP는 다음 현실적 질문에도 답해야 한다.

- YouTube API quota 안에서 수집 주기를 유지할 수 있는가
- 인기 영상 표본만으로도 의미 있는 Hot / Rising Channel 분석이 가능한가
- 결과를 "표본 기반 트렌드"로 설명할 수 있는가

## 2. MVP 핵심 범위

### 포함 기능

- YouTube Data API 연동
- 인기 영상 수집
- 영상 메타데이터 저장
- 영상 스냅샷 저장
- 숏폼 / 롱폼 분류
- 최근 Hot Video 조회 API
- 최근 Rising Channel 기본 분석 API
- 수집 출처 메타데이터 저장

### 제외 기능

- 키워드 추출
- Revival Video 탐지
- 단기 예측
- 고급 대시보드
- 정교한 점수 튜닝
- 검색 기반 키워드 확장 수집
- 유튜브 전체 트렌드 전수 탐지

## 3. MVP가 제공해야 하는 사용자 가치

MVP 단계에서도 사용자는 다음을 확인할 수 있어야 한다.

- 최근 어떤 영상이 주목받는지
- 최근 어떤 채널이 성장세를 보이는지
- 숏폼과 롱폼의 흐름이 어떻게 다른지

단, 이 결과는 전체 유튜브를 대표하는 공식 순위가 아니라 초기 수집 표본 기준 분석이라는 점을 함께 전달해야 한다.


## 4. MVP에서 다루는 데이터

### 영상
- videoId
- title
- channelId
- channelTitle
- publishedAt
- duration
- categoryId
- viewCount
- likeCount
- commentCount
- collectedAt
- sourceRegion
- sourceCategory
- sourceRank

### 채널
- channelId
- title
- publishedAt
- subscriberCount
- viewCount
- videoCount
- collectedAt
- hiddenSubscriberCount

## 5. MVP의 성공 기준

다음 조건을 충족하면 MVP를 성공으로 본다.

- 인기 영상 수집이 주기적으로 동작한다.
- 수집 데이터를 DB에 저장할 수 있다.
- 숏폼 / 롱폼 분류가 동작한다.
- 최근 Hot Video 목록을 조회할 수 있다.
- Rising Channel의 기본 점수를 계산할 수 있다.
- 기간 필터를 적용해 조회할 수 있다.
- 수집 출처와 범위를 함께 설명할 수 있다.

## 6. MVP 데모 시나리오

1. 수집기를 실행한다.
2. 인기 영상 데이터가 저장된다.
3. 최근 1시간 Hot Video API를 호출한다.
4. short-form 필터를 적용해 조회한다.
5. Rising Channel API를 호출해 최근 성장 채널을 본다.
6. 응답 meta에서 표본 범위를 확인한다.

## 7. MVP 이후 확장 방향

MVP 이후에는 다음을 순차적으로 확장한다.

- 키워드 추출
- Rising Video / Revival Video
- Breakout New Channel
- Prediction
- 고급 캐시 및 배치 고도화
