## 🔹 기본 정보

- Method : `DELETE`
- URL : `/customers/{customerId}`
- 설명 :  
  특정 고객을 삭제 처리합니다. 실제 데이터 삭제가 아닌 `deletedAt`을 기록하는 논리 삭제 방식입니다.

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

| 변수명        | 타입   | 필수 | 설명        |
|------------|------|----|-----------|
| customerId | Long | O  | 고객 고유 식별자 |

---

# 🔹 Response

## ✅ 성공 - 204 No Content

```
204 No Content
```

| 항목     | 설명  |
|--------|-----|
| status | 204 |
| body   | 없음  |

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