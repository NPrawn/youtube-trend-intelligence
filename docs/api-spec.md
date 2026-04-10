# API 명세 초안

## 1. 목적

이 문서는 서비스가 외부에 제공할 API의 초안 명세를 정의한다.

초기에는 조회 API 중심으로 설계하며, 추후 관리용 API나 내부용 API를 확장할 수 있다.

## 2. 공통 규칙

### 기간 파라미터
window를 사용한다.

예:
- 10m
- 1h
- 24h
- 7d
- 30d
- 1y

### 형태 파라미터
form을 사용한다.

예:
- all
- short
- long

### 타입 파라미터
type을 사용한다.

예:
- hot
- rising
- revival
- breakout
- subscriberMomentum

### 범위 파라미터
초기에는 다음 파라미터를 함께 고려한다.

- region
- category
- source
- language

## 3. API 목록

## 3.1 GET /trends/videos

설명:
기간별 영상 트렌드 조회

query:
- window
- type
- form
- region
- category
- limit

예:
- /trends/videos?window=1h&type=hot&form=all
- /trends/videos?window=24h&type=rising&form=short

응답 예시 필드:
- videoId
- title
- channelId
- channelTitle
- publishedAt
- durationSeconds
- isShortForm
- hotScore
- risingScore
- revivalScore
- rank
- deltaRank
- source
- region
- category

## 3.2 GET /trends/channels

설명:
기간별 채널 트렌드 조회

query:
- window
- type
- form
- region
- category
- limit

예:
- /trends/channels?window=24h&type=rising
- /trends/channels?window=7d&type=breakout&form=short

응답 예시 필드:
- channelId
- title
- publishedAt
- risingScore
- breakoutScore
- subscriberMomentumScore
- recentTopVideoCount
- rank
- deltaRank
- source
- region

## 3.3 GET /trends/keywords

설명:
기간별 키워드 트렌드 조회

query:
- window
- type
- region
- language
- limit

예:
- /trends/keywords?window=24h&type=hot
- /trends/keywords?window=7d&type=rising

응답 예시 필드:
- keyword
- frequency
- relatedVideoCount
- relatedChannelCount
- hotScore
- risingScore
- rank
- deltaRank
- source
- region

## 3.4 GET /trends/videos/{videoId}

설명:
특정 영상 상세 조회

응답 예시 필드:
- video 기본 정보
- 최근 점수 추이
- 최근 순위 추이
- short / long 여부
- 연관 키워드
- 소속 채널 정보
- 수집 출처 정보

## 3.5 GET /trends/channels/{channelId}

설명:
특정 채널 상세 조회

응답 예시 필드:
- channel 기본 정보
- 최근 점수 추이
- 최근 상위 영상 목록
- 최근 성장 지표
- short-heavy / long-heavy 여부
- 수집 출처 정보

## 3.6 GET /trends/keywords/{keyword}

설명:
특정 키워드 상세 조회

응답 예시 필드:
- keyword
- 최근 빈도 추이
- 관련 영상 목록
- 관련 채널 목록
- 최근 점수 추이

## 3.7 GET /predictions

설명:
예측 결과 조회

query:
- entityType
- targetWindow
- limit

예:
- /predictions?entityType=video&targetWindow=2h
- /predictions?entityType=channel&targetWindow=24h

응답 예시 필드:
- entityType
- entityId
- title or keyword
- predictionScore
- targetWindow
- modelVersion

## 4. 응답 형식 초안

### 성공 응답
- success
- data
- meta

meta 예시:
- window
- region
- category
- source
- sampledAt
- coverageNote

### 실패 응답
- success
- errorCode
- message

## 5. 초기 우선순위

1차 구현 우선순위는 다음과 같다.

1. GET /trends/videos
2. GET /trends/channels
3. GET /trends/videos/{videoId}
4. GET /trends/channels/{channelId}

키워드 API와 prediction API는 후순위로 둔다.

## 6. 해석 원칙

- 모든 랭킹은 수집 표본 기준 결과다.
- `source=mostPopular`과 `source=searchExpanded`는 분리 해석할 수 있어야 한다.
- short / long 비교는 기본적으로 같은 form 내부에서 수행한다.
