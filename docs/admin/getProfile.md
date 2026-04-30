# 🔹 Request

## Headers

```
Cookie: JSESSIONID={sessionId}
```

## Body

없음

# 🔹 Response

## ⭕ 성공 - 200 OK

## Body

```json
{
  "status": 200,
  "message": "내 프로필 조회 성공",
  "data": {
    "id": 1,
    "email": "admin@test.com",
    "name": "김관리",
    "phone": "010-1234-5678"
  }
}
```

## ❌ 실패 - 401 Unauthorized

## **로그인하지 않은 경우**

```json
{
  "status": 401,
  "message": "로그인이 필요합니다.",
  "data": null
}
```