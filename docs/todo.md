# TODO

## 1. 문서화
- [x] README 작성
- [x] service-plan 작성
- [x] mvp 작성
- [x] metrics-definition 작성
- [x] data-collection-plan 작성
- [x] data-model 작성
- [x] api-spec 작성
- [x] development-roadmap 작성
- [x] architecture 작성
- [x] prediction-plan 작성
- [ ] 문서 세부 내용 보정

## 2. API 검증
- [ ] YouTube API 키 발급
- [ ] videos.list 테스트
- [ ] channels.list 테스트
- [ ] duration 파싱 테스트
- [ ] sample 응답 저장

## 3. 백엔드 초기 세팅
- [ ] Spring Boot 프로젝트 생성
- [ ] 환경변수 설정
- [ ] DB 연결
- [ ] Redis 연결
- [ ] 기본 health check API 추가

## 4. 데이터 모델 구현
- [ ] video 엔티티 구현
- [ ] channel 엔티티 구현
- [ ] video_snapshot 구현
- [ ] channel_snapshot 구현
- [ ] 마이그레이션 설정

## 5. 수집 기능
- [ ] 인기 영상 수집기 구현
- [ ] 채널 정보 수집기 구현
- [ ] 수집 주기 설정
- [ ] 수집 실패 재시도 정책 정리

## 6. 조회 기능
- [ ] /trends/videos 구현
- [ ] /trends/channels 구현
- [ ] 영상 상세 API 구현
- [ ] 채널 상세 API 구현
- [ ] 기간 필터 구현
- [ ] short / long 필터 구현

## 7. 분석 기능
- [ ] Hot Video 점수 계산
- [ ] Rising Video 점수 계산
- [ ] Rising Channel 점수 계산
- [ ] Breakout New Channel 기준 설계

## 8. 키워드 기능
- [ ] 제목 기반 키워드 추출
- [ ] 불용어 제거
- [ ] keyword_snapshot 저장
- [ ] /trends/keywords 구현

## 9. 예측 기능
- [ ] 예측용 피처 설계
- [ ] baseline 예측 로직 구현
- [ ] prediction_result 저장
- [ ] /predictions 구현

## 10. 운영 / 고도화
- [ ] Redis 캐시 적용
- [ ] 배치 고도화
- [ ] 로그 정리
- [ ] 모니터링 포인트 정리