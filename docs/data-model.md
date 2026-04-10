# 데이터 모델 설계 초안

## 1. 설계 방향

이 프로젝트의 데이터 모델은 다음 원칙을 따른다.

- 원본 스냅샷과 집계 데이터를 분리한다.
- 영상, 채널, 키워드를 독립된 분석 대상으로 다룬다.
- 기간별 조회를 위해 집계 테이블을 둔다.
- 향후 예측 기능을 고려해 피처 생성이 가능한 구조를 만든다.

## 2. 주요 개체

### video
영상의 고정 메타데이터

### channel
채널의 고정 메타데이터

### video_snapshot
특정 시점의 영상 상태

### channel_snapshot
특정 시점의 채널 상태

### keyword_snapshot
특정 시점의 키워드 상태

### trend_aggregate
기간별 집계 결과

### prediction_result
예측 결과 저장

## 3. 테이블 개요

## 3.1 video

목적:
영상의 고정 정보 저장

컬럼 초안:
- id
- youtube_video_id
- title
- description
- channel_id
- published_at
- duration_seconds
- is_short_form
- category_id
- default_language
- created_at
- updated_at

## 3.2 channel

목적:
채널의 고정 정보 저장

컬럼 초안:
- id
- youtube_channel_id
- title
- description
- published_at
- custom_url
- country
- created_at
- updated_at

## 3.3 video_snapshot

목적:
특정 시점의 영상 통계와 점수 저장

컬럼 초안:
- id
- video_id
- collected_at
- view_count
- like_count
- comment_count
- source_endpoint
- source_region
- source_category
- source_query
- source_rank
- hot_score
- rising_score
- revival_score

## 3.4 channel_snapshot

목적:
특정 시점의 채널 통계와 점수 저장

컬럼 초안:
- id
- channel_id
- collected_at
- subscriber_count
- hidden_subscriber_count
- view_count
- video_count
- rising_score
- breakout_score
- subscriber_momentum_score

## 3.5 keyword_snapshot

목적:
특정 시점의 키워드 상태 저장

컬럼 초안:
- id
- keyword
- collected_at
- source_type
- source_region
- source_query
- frequency
- related_video_count
- related_channel_count
- hot_score
- rising_score

## 3.6 trend_aggregate

목적:
기간별 조회에 사용할 집계 결과 저장

컬럼 초안:
- id
- entity_type
- entity_id
- bucket_type
- bucket_start
- window_size
- form
- source_scope
- hot_score
- rising_score
- revival_score
- rank
- delta_rank

## 3.7 prediction_result

목적:
예측 결과 저장

컬럼 초안:
- id
- entity_type
- entity_id
- predicted_at
- target_window
- prediction_score
- label
- model_version

## 4. 관계 구조

- channel 1 : N video
- video 1 : N video_snapshot
- channel 1 : N channel_snapshot

키워드는 독립 엔티티로 시작하되, 필요 시 keyword와 video의 관계 테이블을 추가할 수 있다.
또한 keyword_snapshot는 source_type과 source_query를 통해 수집 출처를 추적할 수 있어야 한다.

## 5. 집계 구조

원본 스냅샷은 가능한 한 사실 그대로 보관한다.  
API 응답용 조회는 trend_aggregate를 활용한다.

집계 시에도 출처 범위를 잃지 않도록 `source_scope` 또는 동등한 메타데이터를 유지한다.

예:
- minute
- hour
- day
- week
- month
- year

## 6. Redis 키 구조 초안

- trend:videos:hot:{window}
- trend:videos:rising:{window}
- trend:videos:revival:{window}
- trend:channels:rising:{window}
- trend:channels:breakout:{window}
- trend:keywords:hot:{window}
- trend:keywords:rising:{window}
- prediction:{entityType}:{window}

예:
- trend:videos:hot:1h
- trend:channels:rising:24h
- trend:keywords:hot:7d

## 7. 보관 정책 초안

- 원본 스냅샷: 장기 보관
- 분 단위 집계: 짧은 기간 보관
- 시 / 일 / 주 / 월 집계: 장기 보관
- 예측 결과: 모델 비교를 위해 보관

## 8. 설계 시 주의점

- 원본과 집계를 섞지 않는다.
- 조회 성능을 위해 집계를 적극 활용한다.
- 점수 계산 필드는 이후 조정 가능성을 고려해 유연하게 둔다.
- API 원본이 아닌 내부 계산값과 원본 통계를 명확히 구분한다.
