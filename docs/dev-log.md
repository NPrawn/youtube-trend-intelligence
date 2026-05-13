# Dev Log

## 2026-04-10

### V0 시작

- Spring Boot 백엔드 프로젝트를 `backend/`에 생성했다.
- 로컬 PostgreSQL 개발 환경을 `docker-compose.yml`로 구성했다.
- `application.yml`에 DB 및 YouTube API 설정을 추가했다.
- `GET /health` 엔드포인트를 추가해 서버 기동을 확인했다.
- Flyway `V1__init_video_tables.sql`로 `video`, `video_snapshot` 테이블을 만들었다.
- YouTube API 설정 바인딩, `RestClient`, DTO, `YoutubeVideoClient`를 추가했다.
- `/debug/youtube/most-popular` 엔드포인트로 `videos.list(mostPopular)` 수동 검증 흐름을 만들었다.

### 해결한 문제

- IntelliJ가 루트에서 `backend`를 Gradle 프로젝트로 인식하지 못해 `backend` 모듈로 다시 붙였다.
- Flyway 마이그레이션 파일명 규칙 문제를 수정했다.
- 초기 SQL의 `primary key` 오타를 수정했다.
- YouTube API `part` 파라미터 오타(`statistices`)를 `statistics`로 수정했다.

### 현재 상태

- 서버 실행 가능
- PostgreSQL 연결 가능
- Flyway 마이그레이션 적용 가능
- YouTube mostPopular API 호출 검증 단계까지 진행

### 다음 작업

- YouTube duration 파서 추가
- API 응답을 내부 모델로 변환
- `video`, `video_snapshot` 저장 로직 구현
- 수집 결과 조회 API 구현

## 2026-04-15

### 인기 영상 저장 흐름 연결

- `YoutubeDurationParser`와 관련 테스트를 추가했다.
- YouTube `videos.list(mostPopular)` 응답을 내부 모델 `CollectedVideo`로 변환하는 매퍼를 추가했다.
- `video`, `video_snapshot` 엔티티와 저장 매퍼를 연결해 수집 결과를 DB에 저장하는 서비스를 구현했다.
- 동일한 `youtube_video_id`가 이미 있으면 `video`를 재사용하고 `video_snapshot`만 추가 저장하도록 처리했다.
- 수동 검증용 `GET /debug/videos/collect-most-popular` 엔드포인트를 추가했다.
- 저장 로직 단위 테스트와 통합 테스트를 추가했다.

### 현재 상태

- 인기 영상 수집 결과를 내부 모델로 변환할 수 있다.
- 변환된 데이터를 `video`, `video_snapshot`에 저장할 수 있다.
- 동일 비디오 재수집 시 snapshot 누적 저장을 검증했다.
- 수집/저장 흐름 전체 테스트가 통과한다.

### 남은 보정

- `maxResults`가 실제 YouTube API 요청에 반영되도록 클라이언트를 보정해야 한다.
- `sourceCategory`를 수집 요청부터 저장까지 일관되게 전달하도록 정리해야 한다.

## 2026-05-12

### 최근 Hot Video 조회 API 추가

- 저장된 `video`, `video_snapshot` 데이터를 기반으로 `GET /trends/videos` API를 추가했다.
- 가장 최근 `collectedAt` 기준 snapshot만 조회하도록 구현했다.
- `region`, `form(all/short/long)`, `limit` 필터를 지원하도록 했다.
- 응답 DTO와 조회 전용 service를 분리했다.
- service 단위 테스트와 integration test를 추가했다.

### 현재 상태

- 인기 영상 수집/저장 흐름이 동작한다.
- 최신 Hot Video 조회 API가 동작한다.
- 아직 `rising`, `revival`, `window` 기반 분석은 구현하지 않았다.

### 다음 작업 후보

- `GET /trends/videos`의 기간(window) 조회 확장
- `GET /trends/channels` 구현
- 채널 수집 및 snapshot 저장 구조 추가

## 2026-05-13

### 채널 저장 구조 추가

- `channel`, `channel_snapshot` 테이블을 추가했다.
- `Channel`, `ChannelSnapshot` 엔티티와 repository를 추가했다.
- 수집된 채널 데이터를 표현하는 `CollectedChannel` 모델을 추가했다.
- `CollectedChannel`을 `Channel`, `ChannelSnapshot`으로 변환하는 매퍼를 추가했다.
- 채널 기본 정보는 재사용하고, 채널 snapshot은 누적 저장하는 `ChannelPersistenceService`를 추가했다.
- 매퍼 단위 테스트, 저장 서비스 단위 테스트, 저장 통합 테스트를 추가했다.

### 현재 상태

- 영상 수집/저장/조회 흐름이 동작한다.
- 채널 저장용 테이블과 애플리케이션 저장 구조가 준비됐다.
- 아직 YouTube `channels.list` 연동과 실제 채널 수집 흐름은 구현하지 않았다.

### 다음 작업 후보

- YouTube `channels.list` 클라이언트 구현
- 인기 영상 수집 결과에서 채널 ID를 추출해 채널 정보를 수집하는 흐름 연결
- `GET /trends/channels` 구현
