## 🔹 기본 정보

- Method : `GET`
- URL : `/customers/{customerId}`
- 설명 : 특정 고객의 상세 정보를 조회합니다.

## 🔹 Request

### Header

| 이름 | 값 | 설명 |
| --- | --- | --- |
| Cookie | JSESSIONID=xxxx | 로그인 세션 정보 |

### Body

- 없음

### Path Variable

| 이름 | 타입 | 설명 |
| --- | --- | --- |
| customerId | Long | 고객 ID |

## 🔹 Response

## ✅ 성공 - 200 OK

```
{
  "id":1,
  "name":"홍길동",
  "email":"gildong@test.com",
  "phone":"010-1111-1111",
  "status":"ACTIVE",
  "createdAt":"2026-04-27T10:00:00",
  "totalOrderCount":2,
  "totalOrderAmount":125000
}
```

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| id | Long | 고객 고유 식별자 |
| name | String | 고객 이름 |
| email | String | 고객 이메일 |
| phone | String | 고객 전화번호 |
| status | CustomerStatus | 고객 상태 |
| createdAt | LocalDateTime | 고객 가입일 |
| totalOrderCount | Long | 총 주문 수 |
| totalOrderAmount | Long | 총 구매 금액 |

---

## ❌ 실패 - 404 Not Found

### 고객이 존재하지 않는 경우

```
{
  "status":404,
  "message":"존재하지 않는 고객입니다.",
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