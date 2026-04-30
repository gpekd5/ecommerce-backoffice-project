## 🔹기본 정보

- Method: `GET`
- URL: `/customers`
- 설명:  고객 목록을 조회하며 검색, 필터, 정렬, 페이징을 지원하고 주문 기반 집계 정보를 함께 반환합다.

## 🔹Request

### Header

| 이름 | 값 | 설명 |
| --- | --- | --- |
| Cookie | JSESSIONID=xxxx | 로그인 세션 정보 |

### Query Parameters

| 변수명 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| keyword | String | X | 고객 이름 또는 이메일 검색 키워드 |
| status | CustomerStatus | X | 고객 상태 필터 |
| page | int | X | 페이지 번호, 기본값 1 |
| size | int | X | 페이지당 데이터 개수, 기본값 10 |
| sortBy | String | X | 정렬 기준: `name`, `email`, `createdAt` |
| sortDir | String | X | 정렬 방향: `asc`, `desc` |

## 상태값

| 값 | 설명 |
| --- | --- |
| ACTIVE | 활성 |
| INACTIVE | 비활성 |
| SUSPENDED | 정지 |

## 🔹Response

## ✅ 성공 - 201 Created

```
{
  "items": [
    {
      "id": 1,
      "name": "홍길동",
      "email": "gildong@test.com",
      "phone": "010-1234-5678",
      "status": "ACTIVE",
      "createdAt": "2026-04-24T14:00:00",
      "totalOrderCount": 3,
      "totalOrderAmount": 125000
    }
  ],
  "page": 1,
  "size": 10,
  "totalCount": 1,
  "totalPages": 1
}
```

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| items | List | 고객 목록 |
| id | Long | 고객 고유 식별자 |
| name | String | 고객 이름 |
| email | String | 고객 이메일 |
| phone | String | 고객 전화번호 |
| status | CustomerStatus | 고객 상태 |
| createdAt | LocalDateTime | 고객 가입일 |
| totalOrderCount | Long | 총 주문 수 |
| totalOrderAmount | Long | 총 구매 금액 |
| page | int | 현재 페이지 |
| size | int | 페이지당 데이터 개수 |
| totalCount | long | 전체 고객 수 |
| totalPages | int | 전체 페이지 수 |

### 

## ❌ 실패 - 400 Bad Request

### 유효하지 않은 고객 상태인 경우

```
{
  "status":400,
  "message":"유효하지 않은 고객 상태입니다.",
  "data":null
}
```

### 페이지 번호가 1보다 작은 경우

```
{
  "status":400,
  "message":"페이지 번호는 1 이상이어야 합니다.",
  "data":null
}
```

### 페이지 크기가 1보다 작은 경우

```
{
  "status":400,
  "message":"페이지 크기는 1 이상이어야 합니다.",
  "data":null
}
```

### 유효하지 않은 정렬 기준인 경우

```
{
  "status":400,
  "message":"유효하지 않은 정렬 기준입니다.",
  "data":null
}
```

---

## ❌ 401 Unauthorized

### 로그인이 되지 않은 경우

```
{
  "status":401,
  "message":"인증이 필요합니다.",
  "data":null
}
```

---

## ❌ 403 Forbidden

### 관리자 권한이 없는 경우

```
{
  "status":403,
  "message":"접근 권한이 없습니다.",
  "data":null
}
```