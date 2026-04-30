# 🔹 Request

## Headers

```
Content-Type: application/json
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
  "message": "로그아웃이 완료되었습니다.",
  "data": null
}
```

## ❌ 실패 - 401 Unauthorized

```json
{
  "status":401,
  "message": "인증 정보가 유효하지 않습니다.",
  "data":null
}
```