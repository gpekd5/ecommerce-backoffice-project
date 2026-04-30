## 🔹 기본 정보

- Method : `PATCH`
- URL : `/customers/{customerId}/status`
- 설명 : 특정 고객의 상태를 변경합니다.

# 🔹 Request

## Headers

```
Content-Type: application/json
Cookie: JSESSIONID={sessionId}
```

## Path Variable

```
/customers/1/status
```

| 변수명 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| customerId | Long | O | 고객 고유 식별자 |

---

## Body

```
{
  "status":"INACTIVE"
}
```

| 변수명 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| status | String | O | 변경할 고객 상태 |

---

## 상태값

| 값 | 설명 |
| --- | --- |
| ACTIVE | 활성 |
| INACTIVE | 비활성 |
| SUSPENDED | 정지 |

---

# 🔹 Response

## ✅ 성공 - 200 OK

```
{
  "status":200,
  "message":"고객 상태 변경 성공",
  "data": {
    "id":1,
    "status":"INACTIVE"
  }
}
```

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| status | Integer | HTTP 상태 코드 |
| message | String | 응답 메시지 |
| id | Long | 고객 고유 식별자 |
| status (data) | CustomerStatus | 변경된 고객 상태 |

---

# ❌ 실패 - 400 Bad Request

### 상태값이 없는 경우

```
{
  "status":400,
  "message":"고객 상태는 필수입니다.",
  "data":null
}
```

### 유효하지 않은 상태값

```
{
  "status":400,
  "message":"유효하지 않은 고객 상태입니다.",
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