# YouTube Trend Intelligence Backend Code Convention

이 문서는 YouTube Trend Intelligence 프로젝트의 백엔드 개발 시 준수해야 할 최종 코드 컨벤션을 정의한다.
CodeRabbit AI 및 동료 리뷰어는 이 문서를 기준으로 코드 리뷰를 진행한다.

이 문서는 현재 코드 상태 설명서가 아니라, 최종 결과물까지 구현해 가는 동안 지속적으로 지켜야 하는 기준 문서다.

---

## 1. 기본 원칙

- 코드는 기능 중심으로 구조화한다.
- 명확성을 우선하며, 축약어와 모호한 이름을 지양한다.
- HTTP, 배치, 외부 API, DB 세부 구현은 도메인 로직과 분리한다.
- Controller에는 비즈니스 로직을 넣지 않는다.
- 원본 수집 데이터, 집계 데이터, API 응답 모델은 분리한다.
- 새 기능에는 테스트를 함께 추가한다.
- 운영 가능한 코드, 재실행 가능한 배치, 예측 가능한 구조를 우선한다.

---

## 2. Naming Conventions

### 2.1 기본 원칙

- 변수명, 메서드명, 클래스명은 영어로 작성한다.
- 이름만 보고 역할과 의도가 드러나야 한다.
- 불필요한 축약어를 사용하지 않는다.
  예: `cnt` 대신 `count`, `idx` 대신 `index`
- boolean 이름은 상태가 드러나게 작성한다.
  예: `isShortForm`, `hasNextPage`, `isCollected`

### 2.2 클래스 및 인터페이스

- Class: `UpperCamelCase`
  예: `VideoTrendController`
- Interface: `UpperCamelCase`
  인터페이스 이름에 `I` 접두사는 붙이지 않는다.
  예: `VideoRepository`
- Implementation: `Impl`을 붙이거나 구현 기술이 드러나는 이름을 사용한다.
  예: `VideoQueryRepositoryImpl`, `JdbcChannelRepository`

### 2.3 메서드 및 변수

- Method: `lowerCamelCase`
  예: `findHotVideos`
- Variable: `lowerCamelCase`
  예: `videoCount`
- Constant: `UPPER_SNAKE_CASE`
  예: `SHORT_FORM_MAX_SECONDS`

### 2.4 DTO / Entity / Value Object

- Entity: 접미사를 붙이지 않는다.
  예: `Video`, `Channel`, `VideoSnapshot`
- Request DTO: `Request` 접미사를 사용한다.
  예: `VideoTrendRequest`
- Response DTO: `Response` 접미사를 사용한다.
  예: `VideoTrendResponse`
- 외부 API DTO: 공급자 또는 목적이 드러나게 작성한다.
  예: `YoutubeVideoItem`, `YoutubeVideosResponse`
- 값 객체는 의미가 드러나게 작성한다.
  예: `TrendScore`, `DurationSeconds`, `ChannelId`

### 2.5 금지하는 이름

- `Manager`
- `Helper`
- `Util`
- `CommonService`
- `Temp`
- `TestController`

이름만으로 책임이 드러나지 않는 명칭은 금지한다.

---

## 3. Architecture & Layering

### 3.1 구조 원칙

패키지는 역할만으로 나누지 않고 기능 중심으로 구성한다.

권장 예시:

```text
com.jeong.youtubetrend
├─ video
│  ├─ api
│  ├─ application
│  ├─ domain
│  ├─ infrastructure
│  └─ batch
├─ channel
│  ├─ api
│  ├─ application
│  ├─ domain
│  └─ batch
├─ keyword
│  ├─ api
│  ├─ application
│  ├─ domain
│  └─ batch
├─ youtube
│  └─ infrastructure
├─ common
│  ├─ config
│  ├─ exception
│  ├─ response
│  └─ time
└─ support
   ├─ fixture
   └─ test
```

규칙:

- 최상위 기준은 기술이 아니라 기능이다.
- 기능 내부에서 `api`, `application`, `domain`, `infrastructure`, `batch`로 역할을 나눈다.
- 여러 기능에서 공통으로 쓰는 요소만 `common`에 둔다.
- `common`은 최소한으로 유지한다.

### 3.2 계층 책임

- `api`: 요청 검증, 요청/응답 변환, 서비스 호출, 응답 반환
- `application`: 유스케이스 실행, 트랜잭션 경계, 여러 컴포넌트 조합
- `domain`: 핵심 비즈니스 규칙, 엔티티, 값 객체, 도메인 서비스
- `infrastructure`: DB 구현, 외부 API 클라이언트, 캐시, 메시징
- `batch`: 수집, 집계, 재계산, 예측 생성 등 주기 실행 작업

규칙:

- Controller는 Service 없이 Repository나 외부 API Client를 직접 호출하지 않는다.
- Service는 트랜잭션과 흐름 제어를 담당한다.
- Repository는 DB 접근만 담당한다.
- Domain은 HTTP, SQL, 외부 API 세부사항을 몰라야 한다.
- Infrastructure는 기술 구현체이며 도메인 규칙을 가지지 않는다.

### 3.3 의존성 주입

- 생성자 주입을 필수 원칙으로 한다.
- `@Autowired` 필드 주입은 금지한다.
- Lombok 사용 시 `@RequiredArgsConstructor`를 권장한다.

---

## 4. API Conventions

### 4.1 엔드포인트 원칙

- 운영 API는 명확한 리소스 또는 유스케이스를 표현해야 한다.
- 디버그 API와 운영 API는 경로와 패키지를 분리한다.
- 내부 실험용 API는 운영 경로에 포함하지 않는다.

예:

- `/api/trends/videos/hot`
- `/api/trends/videos/rising`
- `/api/trends/channels/rising`
- `/api/trends/keywords/hot`
- `/debug/youtube/most-popular`

### 4.2 요청/응답 모델

- Controller는 Entity를 직접 반환하지 않는다.
- Request DTO와 Response DTO를 분리한다.
- 외부 API DTO를 내부 API 응답으로 직접 노출하지 않는다.
- 응답 모델에는 단위와 의미가 명확해야 한다.

### 4.3 검증

- 입력값 검증은 API 경계에서 우선 수행한다.
- 비즈니스 규칙 검증은 domain 또는 application 레이어에서 다시 보장한다.
- 잘못된 입력과 비즈니스 예외는 구분한다.

---

## 5. Domain Modeling

### 5.1 도메인 우선

- 도메인 객체는 DB 구조보다 비즈니스 의미를 우선한다.
- 핵심 비즈니스 로직은 가능한 도메인에 응집시킨다.
- 식별성과 상태 변화가 필요한 경우에만 Entity를 사용한다.
- 값 의미가 분명하면 Value Object를 사용한다.

### 5.2 Entity 규칙

- Entity에는 Setter를 두지 않는다.
- 상태 변경은 의도가 드러나는 메서드로 수행한다.
  예: `markAsCollected()`, `changeTitle()`
- 불완전한 상태의 Entity 생성을 허용하지 않는다.
- 시간 정보는 `OffsetDateTime` 또는 `Instant`를 우선 사용한다.

### 5.3 원본 데이터와 집계 데이터

- 원본 수집 데이터와 집계 데이터는 분리 저장한다.
- 스냅샷 데이터와 API 응답용 결과 데이터는 분리한다.
- 재계산 가능한 시스템을 위해 원본 데이터를 가능한 보존한다.

---

## 6. Repository & Persistence

### 6.1 Repository 규칙

- Repository는 DB 접근만 담당한다.
- 조회 메서드는 목적이 드러나게 작성한다.
  예: `findTop100ByCollectedAtBetweenOrderByTrendScoreDesc`
- Controller에서 Repository를 직접 호출하지 않는다.

### 6.2 DB 스키마 규칙

- 스키마 변경은 반드시 Flyway로 관리한다.
- 테이블/컬럼/인덱스는 `snake_case`를 사용한다.
- null 허용 여부, unique, foreign key, index를 명확히 정의한다.
- 시계열 조회 성능을 고려한 인덱스를 설계한다.

### 6.3 Flyway 규칙

- 파일명은 `V{버전}__{설명}.sql` 규칙을 따른다.
  예: `V1__init_video_tables.sql`
- 마이그레이션은 재현 가능해야 하며 수동 DB 변경을 금지한다.

---

## 7. External Integration

### 7.1 외부 API 분리

- YouTube API 연동은 전용 클라이언트와 DTO 계층으로 분리한다.
- 외부 응답 DTO는 외부 계약을 표현할 뿐 내부 도메인을 대표하지 않는다.
- 외부 API 관련 예외, 재시도, 타임아웃은 infrastructure에서 처리한다.

### 7.2 변환 규칙

- 외부 DTO -> 내부 수집 모델 -> 도메인/영속 모델 순서로 변환한다.
- 파싱 로직은 명시적으로 구현하고 테스트한다.
- 외부 데이터 품질 문제를 전제로 방어적으로 작성한다.

---

## 8. Batch & Collection

### 8.1 배치 설계

- 수집, 집계, 예측 생성은 분리 가능한 작업 단위로 나눈다.
- 배치는 재실행 가능해야 한다.
- 입력, 처리, 저장 책임을 분리한다.

### 8.2 멱등성

- 같은 데이터를 다시 수집하거나 집계해도 결과가 비정상적으로 중복되지 않아야 한다.
- 수집 작업은 upsert 또는 중복 방지 전략을 가져야 한다.
- 집계 작업은 기준 시점과 대상 범위를 명확히 기록해야 한다.

### 8.3 운영 관점

- 배치 시작/종료/처리 건수/실패 건수를 로그나 메트릭으로 남긴다.
- 부분 실패 허용 여부와 재시도 정책을 명확히 한다.

---

## 9. Coding Style & Clean Code

### 9.1 Lombok 사용

- `@Data` 사용을 금지한다.
- Entity에는 `@Setter`를 사용하지 않는다.
- 필요한 Lombok만 선택적으로 사용한다.
  예: `@Getter`, `@RequiredArgsConstructor`, `@Builder`
- 생성자 파라미터가 많고 의미가 명확할 때 `@Builder`를 사용한다.

### 9.2 불변성

- 모든 필드는 가능한 `private final`로 선언한다.
- 변경이 필요 없는 객체는 불변으로 설계한다.
- DTO에는 Java `record` 사용을 권장한다.

### 9.3 메서드 설계

- Early Return을 우선 사용한다.
- `else`로 중첩을 깊게 만들지 않는다.
- 1 메서드 1 책임 원칙을 지킨다.
- 메서드명이 구현이 아니라 의도를 설명해야 한다.

### 9.4 코드 스타일

- Java 기본 들여쓰기는 4칸을 사용한다.
- 의미 없는 공백과 불필요한 빈 줄을 남기지 않는다.
- 매직 넘버는 상수로 추출한다.
- 주석은 코드가 설명하지 못하는 의도만 적는다.

---

## 10. Exception Handling

- 예외를 `try-catch`로 먹어버리는 행위를 금지한다.
- 비즈니스 예외는 `RuntimeException` 기반의 커스텀 예외를 사용한다.
- 예외는 `@RestControllerAdvice` 기반 `GlobalExceptionHandler`에서 일관되게 처리한다.
- 입력 오류, 비즈니스 오류, 외부 연동 오류, 시스템 오류를 구분한다.

권장 예외 예시:

- `BadRequestException`
- `NotFoundException`
- `ConflictException`
- `ExternalApiException`
- `BatchExecutionException`

---

## 11. Logging

- `System.out.println` 사용을 금지한다.
- 운영상 중요한 이벤트만 적절한 레벨로 기록한다.
- 민감 정보, API Key, 토큰은 로그에 남기지 않는다.
- 동일 실패를 과도하게 반복 로그하지 않는다.

최소 로그 대상:

- 외부 API 호출 실패
- 배치 시작/종료
- 저장 실패
- 비정상 데이터 스킵
- 주요 집계 결과 생성

---

## 12. Testing

### 12.1 기본 원칙

- JUnit 5 + AssertJ를 표준으로 한다.
- 새 기능에는 테스트를 함께 추가한다.
- 버그 수정에는 회귀 테스트를 추가하는 것을 원칙으로 한다.

### 12.2 테스트 구조

- `given` - `when` - `then` 패턴을 준수한다.
- 테스트 메서드에는 `@DisplayName`을 사용해 한글로 의도를 명확히 표현한다.

예:

- `@DisplayName("인기 영상 수집 성공 시 스냅샷이 저장된다")`
- `@DisplayName("잘못된 duration 문자열이 들어오면 예외가 발생한다")`

### 12.3 테스트 범위

- 정상 케이스
- 경계값
- null/빈 값
- 외부 데이터 이상 케이스
- 중복 수집/재실행 케이스
- 시간 계산 관련 케이스

### 12.4 테스트 종류

- 단위 테스트: 파싱, 매핑, 점수 계산, 도메인 로직
- 통합 테스트: Repository, 외부 API 연동 경계, 배치 흐름

---

## 13. Configuration

- 민감값은 코드와 문서에 하드코딩하지 않는다.
- 환경별 설정 분리가 가능해야 한다.
- `@ConfigurationProperties` 사용을 우선한다.
- 설정 키는 의미와 범위가 드러나야 한다.

예:

- `youtube.api.key`
- `youtube.api.base-url`
- `trend.collection.interval`

---

## 14. Review Checklist

리뷰어와 CodeRabbit AI는 아래 항목을 우선 확인한다.

- 네이밍이 역할과 의도를 명확하게 드러내는가
- Controller에 비즈니스 로직이 들어가 있지 않은가
- Service, Domain, Repository, Infrastructure 책임이 섞이지 않았는가
- Entity, DTO, 외부 API DTO가 분리되어 있는가
- 배치/수집 로직이 재실행 가능하고 멱등성을 고려했는가
- 예외가 일관되게 처리되는가
- 새 기능에 테스트가 포함되었는가
- 로그와 설정에 민감 정보가 노출되지 않는가

---

## 15. 최종 원칙

최종 결과물까지 구현해 가는 동안 가장 중요하게 지켜야 할 기준은 아래와 같다.

- 기능 중심으로 구조를 설계한다.
- 도메인 규칙과 기술 구현을 분리한다.
- 수집 데이터, 집계 데이터, 조회 API를 분리한다.
- 재실행 가능하고 테스트 가능한 코드를 작성한다.
- 읽기 쉬운 이름과 작은 책임을 유지한다.
