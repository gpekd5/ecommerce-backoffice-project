# 🛒 EcommerceBackOfficeProject

Spring 기반의 고객 및 상품 관리 시스템입니다.

## 코드 컨벤션

### [코드 컨벤션 문서 링크](docs/codeConvention.md)

## GitHub Rules

### [GitHub Rules 문서 링크](docs/gitHubRules.md)

## 비즈니스 로직 플로우 차트

```mermaid
flowchart TD

%% =========================
%% AUTH FLOW
%% =========================
A[관리자 로그인 요청] --> B{유효성 검증}
B -->|실패| X[에러 반환]
B -->|성공| C[관리자 조회]

C --> D{상태 확인}
D -->|비활성/정지/승인대기| X
D -->|활성| E[비밀번호 검증]

E -->|불일치| X
E -->|일치| F[세션 생성]
F --> G[로그인 성공 응답]

%% =========================
%% PRODUCT FLOW
%% =========================
H[상품 등록 요청] --> I{유효성 검증}
I -->|실패| X
I -->|성공| J[상품 저장]
J --> K[상품 등록 완료]

L[재고 변경] --> M{재고 > 0 ?}
M -->|YES| N[상태 = 판매중]
M -->|NO| O[상태 = 품절]

P[상품 상태 = 단종] --> Q[상태 유지]

%% =========================
%% ORDER FLOW
%% =========================
R[주문 생성 요청] --> S{유효성 검증}
S -->|실패| X

S --> T[상품 조회]
T --> U{재고 충분?}

U -->|NO| X
U -->|YES| V[재고 차감]

V --> W[주문 생성]
W --> Y[상태 = 준비중]
Y --> Z[주문 완료]

%% =========================
%% ORDER STATUS FLOW
%% =========================
AA[주문 상태 변경 요청] --> AB{현재 상태}
AB -->|준비중| AC[배송중]
AC --> AD[배송완료]

AB -->|배송중| AD
AB -->|배송완료| AE[변경 불가]

%% =========================
%% ORDER CANCEL FLOW
%% =========================
AF[주문 취소 요청] --> AG{준비중 상태?}
AG -->|NO| X
AG -->|YES| AH[취소 처리]

AH --> AI[재고 복구]
AI --> AJ[상태 = 취소됨]

%% =========================
%% REVIEW FLOW
%% =========================
AK[리뷰 작성] --> AL{주문 존재?}
AL -->|NO| X
AL -->|YES| AM[리뷰 저장]
AM --> AN[리뷰 등록 완료]

%% =========================
%% DASHBOARD FLOW
%% =========================
AO[대시보드 조회] --> AP[통계 집계]
AP --> AQ[매출 / 주문 / 고객 / 상품 데이터 계산]
AQ --> AR[차트 데이터 생성]
AR --> AS[응답 반환]
```

## 개발 환경

- **Language:** Java 17
- **Framework & Environment:**
    - **Spring Boot 4**
    - **Spring Data JPA**
    - **Bean Validation**
- **Database:** MySQL
- **Security:**
    - **BCrypt**
- **Tools:** Postman

## 패키지 구조

<details>
<summary>패키지 구조 확인</summary>

![image.png](docs/images/projectArchitecture.png)

```
ecommercebackofficeproject
├─ admin
│  ├─ controller
│  ├─ dto
│  │  ├─ request
│  │  └─ response
│  ├─ entity
│  ├─ repository
│  ├─ service
│  └─ type
├─ auth
│  ├─ controller
│  ├─ dto
│  ├─ jwt
│  └─ service
├─ customer
│  ├─ controller
│  ├─ dto
│  │  ├─ request
│  │  └─ response
│  ├─ entity
│  ├─ repository
│  ├─ service
│  └─ type
├─ dashboard
│  ├─ controller
│  ├─ dto
│  └─ service
├─ global
│  ├─ common
│  ├─ config
│  ├─ exception
│  ├─ response
│  └─ security
├─ order
│  ├─ controller
│  ├─ dto
│  │  ├─ request
│  │  └─ response
│  ├─ entity
│  ├─ repository
│  ├─ service
│  └─ type
├─ product
│  ├─ controller
│  ├─ dto
│  │  ├─ request
│  │  └─ response
│  ├─ entity
│  ├─ repository
│  ├─ service
│  └─ type
└─ review
   ├─ controller
   ├─ dto
   │  └─ response
   ├─ entity
   ├─ repository
   └─ service
```

</details>

### 패키지 역할

`admin`

<details>

<summary>관리자 도메인 핵심 패키지</summary>

- 관리자 가입 신청 / 승인 / 거부 / 상태 변경 / 역할 변경 / 삭제 / 조회
- 관리자 상태(승인대기, 활성, 정지 등) 및 역할(슈퍼, 운영, CS) 관련 비즈니스 로직 처리

</details>

`auth`

<details>

<summary>인증(Authentication) 및 세션 관리 담당</summary>

- 로그인 / 로그아웃 처리
- 세션 저장 (관리자 ID, 이메일, 역할)
- 쿠키 기반 인증 (HttpOnly)
- PasswordEncoder, 인증 검증 로직 포함
- “로그인 가능 상태 체크” (활성만 허용)

</details>

`customer`

<details>
<summary>고객 도메인 관리 패키지</summary>

- 고객 조회 / 수정 / 상태 변경 / 삭제
- 고객 리스트 + 상세 조회 + 페이징 + 필터링
- 고객 상태(활성, 비활성, 정지) 관리
- 고객별 주문 통계 집계 처리

</details>

`dashboard`

<details>

<summary>통계 및 집계 전용 패키지</summary>

- 전체 시스템 요약 데이터 / 위젯 / 차트 데이터 제공
- 매출, 주문 상태 분포, 고객 상태 분포, 카테고리 통계 등
- 최근 주문 조회

</details>

`global`

<details>
<summary>전역 공통 기능 패키지</summary>
- 공통 응답 포맷
- 전역 예외 처리
- 공통 BaseEntity
- 인터셉터, 필터, 설정(Config)
</details>

`order`

<details>
<summary>주문 도메인 핵심 패키지</summary>
- 주문 생성 / 조회 / 상태 변경 / 취소
- 주문 상태 흐름 관리
- 주문 생성 시 재고 차감 / 총 금액 계산 / 주문번호 생성
</details>

`product`

<details>
<summary>상품 도메인 관리 패키지</summary>
- 상품 등록 / 조회 / 수정 / 삭제
- 재고 관리 + 상태 자동 변경
- 단종 상태 예외 처리
- 카테고리 / 가격 / 상태 필터링 기능
</details>

`review`

<details>
<summary>리뷰 도메인 관리 패키지</summary>
- 리뷰 조회 / 상세 조회 / 삭제
- 평점 기반 필터링 및 정렬
- 상품 상세에서 리뷰 통계 제공
- 관리자 리뷰 관리 기능
</details>

## ERD

```mermaid
erDiagram

%% =========================
%% RELATIONSHIPS
%% =========================
  ADMINS    ||--o{ PRODUCTS : manages
  ADMINS    ||--o{ ORDERS   : processes

  CUSTOMERS ||--o{ ORDERS   : places
  CUSTOMERS ||--o{ REVIEWS  : writes

  PRODUCTS  ||--o{ ORDERS   : included_in
  PRODUCTS  ||--o{ REVIEWS  : reviewed

  ORDERS    ||--o| REVIEWS  : generates


%% =========================
%% ADMIN DOMAIN
%% =========================
  ADMINS {
    BIGINT   id PK
    VARCHAR  email UK
    VARCHAR  password
    VARCHAR  name
    VARCHAR  phone
    ENUM     role
    ENUM     status
    DATETIME approved_at
    DATETIME rejected_at
    VARCHAR  reject_reason
    DATETIME created_at
    DATETIME updated_at
    DATETIME deleted_at
  }


%% =========================
%% CUSTOMER DOMAIN
%% =========================
  CUSTOMERS {
    BIGINT   id PK
    VARCHAR  email UK
    VARCHAR  phone UK
    VARCHAR  name
    ENUM     status
    DATETIME created_at
    DATETIME updated_at
    DATETIME deleted_at
  }


%% =========================
%% PRODUCT DOMAIN
%% =========================
  PRODUCTS {
    BIGINT   id PK
    BIGINT   admin_id FK
    VARCHAR  name
    ENUM     category
    INT      price
    INT      stock
    ENUM     status
    DATETIME created_at
    DATETIME updated_at
    DATETIME deleted_at
  }


%% =========================
%% ORDER DOMAIN
%% =========================
  ORDERS {
    BIGINT   id PK
    BIGINT   admin_id FK
    BIGINT   customer_id FK
    BIGINT   product_id FK
    VARCHAR  order_number
    ENUM     order_status
    INT      quantity
    INT      total_price
    VARCHAR  order_cancel_reason
    DATETIME created_at
    DATETIME updated_at
    DATETIME deleted_at
  }


%% =========================
%% REVIEW DOMAIN
%% =========================
  REVIEWS {
    BIGINT   id PK
    BIGINT   customer_id FK
    BIGINT   product_id FK
    BIGINT   order_id FK
    INT      rating
    VARCHAR  comment
    DATETIME created_at
    DATETIME updated_at
    DATETIME deleted_at
  }
```

## API 요약

### 인증

#### [인증 API 문서 링크](docs/auth/README.md)

### 관리자

#### [관리자 API 문서 링크](docs/admin/README.md)

### 고객

#### [고객 API 문서 링크](docs/customer/README.md)

### 상품

#### [상품 API 문서 링크](docs/product/README.md)

### 리뷰

#### [리뷰 API 문서 링크](docs/review/README.md)

### 주문

#### [주문 API 문서 링크](docs/order/README.md)

### 대시보드

#### [대시보드 API 문서 링크](docs/dashboard/README.md)

### 공통 로직 관리

#### [공통 로직 API 문서 링크](docs/global/README.md)

## 트러블 슈팅 내용

### [1. 로그인 여부 확인 로직 공통화와 Interceptor 적용](https://github.com/gpekd5/ecommerce-backoffice-project/wiki/%5BTroubleShooting%5D-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EC%97%AC%EB%B6%80-%ED%99%95%EC%9D%B8-%EB%A1%9C%EC%A7%81-%EA%B3%B5%ED%86%B5%ED%99%94%EC%99%80-Interceptor-%EC%A0%81%EC%9A%A9)

### [2. GlobalExceptionHandler를 통한 예외 처리 공통화](https://github.com/gpekd5/ecommerce-backoffice-project/wiki/%5BTroubleShooting%5D-GlobalExceptionHandler%EB%A5%BC-%ED%86%B5%ED%95%9C-%EC%98%88%EC%99%B8-%EC%B2%98%EB%A6%AC-%EA%B3%B5%ED%86%B5%ED%99%94)