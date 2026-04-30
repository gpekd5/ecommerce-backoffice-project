## 🔹 기본 정보

- Method : `PATCH`
- URL : `/api/customers/{customerId}`
- 설명 : 고객의 이름, 이메일, 전화번호를 수정합니다

---

# 🔹 Request

## Headers

```
Content-Type: application/json
Cookie: JSESSIONID={sessionId}
```

## Path Variable

```
/customers/1
```

| 변수명 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| customerId | Long | O | 고객 고유 식별자 |

---

## Body

```
{
  "name":"홍길동수정",
  "email":"update@test.com",
  "phone":"010-9999-8888"
}
```

| 변수명 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| name | String | O | 고객 이름 |
| email | String | O | 고객 이메일 |
| phone | String | O | 고객 전화번호 |

---

# 🔹 Response

## ✅ 성공 - 200 OK

```
{
  "status":200,
  "message":"고객 정보 수정 성공",
  "data": {
    "id":1,
    "name":"홍길동수정",
    "email":"update@test.com",
    "phone":"010-9999-8888",
    "status":"ACTIVE"
  }
}
```

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| status | Integer | HTTP 상태 코드 |
| message | String | 응답 메시지 |
| id | Long | 고객 고유 식별자 |
| name | String | 고객 이름 |
| email | String | 고객 이메일 |
| phone | String | 고객 전화번호 |
| status (data) | CustomerStatus | 고객 상태 |

---

# 🔻 유효성 검증 규칙

| 항목 | 조건 |
| --- | --- |
| name | 공백 불가 |
| email | 이메일 형식 |
| phone | 010-XXXX-XXXX 형식 |

---

# ❌ 실패 - 400 Bad Request

### 필수값 누락

```
{
  "status":400,
  "message":"필수값이 누락되었습니다.",
  "data":null
}
```

### 이메일 형식 오류

```
{
  "status":400,
  "message":"이메일 형식이 올바르지 않습니다.",
  "data":null
}
```

### 전화번호 형식 오류

```
{
  "status":400,
  "message":"전화번호는 010-XXXX-XXXX 형식이어야 합니다.",
  "data":null
}
```

### 중복 이메일

```
{
  "status":400,
  "message":"이미 사용 중인 이메일입니다.",
  "data":null
}
```

---

# ❌ 실패 - 404 Not Found

### 고객이 존재하지 않는 경우

```
{
  "status":404,
  "message":"존재하지 않는 고객입니다.",
  "data":null
}
```

---

# ❌ 401 Unauthorized

```
{
  "status":401,
  "message":"인증이 필요합니다.",
  "data":null
}
```

---

# ❌ 403 Forbidden

```
{
  "status":403,
  "message":"접근 권한이 없습니다.",
  "data":null
}
```