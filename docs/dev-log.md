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
