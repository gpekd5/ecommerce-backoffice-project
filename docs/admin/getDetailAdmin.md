# 🔹 Request

## Headers

```
Cookie: JSESSIONID={sessionId}
```

## Body

없음

| 변수명     | 타입   | 설명     |
|---------|------|--------|
| adminId | Long | 관리자 ID |

# 🔹 Response

## ⭕ 성공 - 200 OK

## Body

```json
{
  "status": 200,
  "message": "관리자 상세 조회 성공",
  "data": {
    "id": 1,
    "email": "admin@test.com",
    "name": "김관리",
    "phone": "010-1234-5678",
    "role": "SUPER_ADMIN",
    "status": "ACTIVE",
    "createdAt": "2026-04-24T10:00:00",
    "approvedAt": "2026-04-24T12:00:00",
    "rejectedAt": null,
    "rejectReason": null
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

## ❌ 실패 - 403 Forbidden

## **SUPER 권한이 아닌 경우**

```json
{
  "status": 403,
  "message": "접근 권한이 없습니다.",
  "data": null
}
```

## ❌ 실패 - 404 Not Found

```json
{
  "status": 404,
  "message": "존재하지 않는 관리자입니다.",
  "data": null
}
```