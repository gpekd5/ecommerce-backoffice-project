# 🔹 Request

## Headers

```
Content-Type: application/json
Cookie: JSESSIONID={sessionId}
```

## Body

```json
{
  "rejectReason": "승인 기준 미충족"
}
```

| 변수명          | 타입     | 설명        |
|--------------|--------|-----------|
| rejectReason | String | 관리자 거부 사유 |

# 🔹 Response

## ⭕ 성공 - 200 OK

## Body

```json
{
  "status": 200,
  "message": "관리자 거부 완료",
  "data": {
    "id": 4,
    "status": "REJECTED",
    "rejectedAt": "2026-04-24T13:30:00",
    "rejectReason": "승인 기준 미충족"
  }
}
```

## ❌ 실패 - 400 Bad Request

### 거부 사유가 입력이 안된 경우

```json
{
  "status": 400,
  "message": "거부 사유는 필수입니다.",
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