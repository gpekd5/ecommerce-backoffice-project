# 🔹 Request

## Headers

```
Cookie: JSESSIONID={sessionId}
```

## Body

없음

| 변수명     | 타입   | 설명         |
|---------|------|------------|
| adminId | Long | 승인할 관리자 ID |

# 🔹 Response

## ⭕ 성공 - 200 OK

## Body

```json
{
  "status": 200,
  "message": "관리자 승인 완료",
  "data": {
    "id": 3,
    "status": "ACTIVE",
    "approvedAt": "2026-04-24T13:00:00"
  }
}
```

## ❌ 실패 - 400 Bad Request

### 승인 대기 상태가 아닌 경우

```json
{
  "status": 400,
  "message": "승인대기 상태의 관리자만 승인할 수 있습니다.",
  "data": null
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