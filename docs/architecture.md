# 시스템 아키텍처

## 1. 아키텍처 목표

이 시스템의 목적은 외부 데이터를 주기적으로 수집하고, 시간에 따른 변화를 저장한 뒤, 이를 집계하고 분석해 API로 제공하는 것이다.

따라서 구조는 크게 다음 흐름으로 나뉜다.

1. 수집
2. 저장
3. 집계 / 분석
4. 캐시
5. 조회 API

## 2. 전체 흐름

유튜브 API → Collector → Raw Data Storage → Aggregator / Batch → Trend Storage → Redis Cache → API Server

## 3. 구성 요소

## 3.1 Collector

역할:
- 외부 API 호출
- 인기 영상 수집
- 채널 정보 수집
- 추후 키워드 기반 검색 수집

초기에는 단일 Spring Boot 내부 배치 또는 별도 스크립트 형태로 시작할 수 있다.

## 3.2 Raw Data Storage

역할:
- 원본 메타데이터 저장
- 스냅샷 저장
- 재집계와 디버깅을 위한 기준 데이터 유지

주요 저장 대상:
- video
- channel
- video_snapshot
- channel_snapshot
- keyword_snapshot

## 3.3 Aggregator / Batch

역할:
- 기간별 집계 생성
- Hot / Rising / Revival 점수 계산
- 채널 성장 점수 계산
- 키워드 점수 계산
- 예측용 피처 생성

초기에는 Spring Batch 또는 애플리케이션 내부 배치로 처리한다.

## 3.4 Trend Storage

역할:
- API 응답용 집계 결과 저장
- 기간별 랭킹 결과 저장

주요 저장 대상:
- trend_aggregate
- prediction_result

## 3.5 Redis Cache

역할:
- 조회 빈도가 높은 랭킹 캐시
- 빠른 응답 제공
- 동일 요청에 대한 반복 연산 감소

## 3.6 API Server

역할:
- 트렌드 조회 API 제공
- 상세 조회 API 제공
- prediction API 제공

## 4. 초기 구현 구조

초기에는 단일 Spring Boot 프로젝트에서 다음 모듈을 함께 운영할 수 있다.

- collector
- batch
- api

프로젝트가 커지면 다음처럼 분리할 수 있다.

- collector 모듈
- analyzer / batch 모듈
- api 모듈

## 5. 데이터 흐름 상세

### 5.1 수집 흐름
Collector가 YouTube API를 호출한다.  
응답에서 필요한 필드를 추출해 video / channel / snapshot 테이블에 저장한다.

### 5.2 집계 흐름
Batch가 snapshot 데이터를 읽는다.  
기간별 점수를 계산해 trend_aggregate에 저장한다.

### 5.3 조회 흐름
API Server가 trend_aggregate 또는 Redis를 조회해 결과를 반환한다.

## 6. Redis 적용 포인트

초기에는 DB 조회만으로도 시작할 수 있다.  
이후 다음 조회에 Redis를 적용한다.

- /trends/videos
- /trends/channels
- /trends/keywords
- /predictions

## 7. 예측 기능 위치

초기에는 배치 기반 예측으로 시작한다.  
즉, 일정 주기마다 예측 점수를 계산해 prediction_result에 저장하고, API는 이를 조회하는 구조로 간다.

향후 필요 시 예측 모델을 별도 서비스로 분리할 수 있다.

## 8. 설계 원칙

- 원본 데이터와 집계 데이터를 분리한다.
- 수집과 조회를 직접 연결하지 않는다.
- 점수 계산은 재실행 가능해야 한다.
- 확장 가능한 구조로 시작하되 초기 구현은 단순하게 유지한다.