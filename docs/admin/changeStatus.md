# 🔹 Request

## Headers

```
Content-Type: application/json
Cookie: JSESSIONID={sessionId}
```

## Body

```json
{
  "status": "INACTIVE"
}
```

| 변수명    | 타입   | 설명                                                                    |
|--------|------|-----------------------------------------------------------------------|
| status | Enum | 변경할 관리자 상태 (`PENDING`, `ACTIVE`, `INACTIVE`, `SUSPENDED`, `REJECTED`) |

# 🔹 Response

## ⭕ 성공 - 200 OK

## Body

```json
{
  "status": 200,
  "message": "관리자 상태 변경 성공",
  "data": {
    "id": 2,
    "status": "INACTIVE"
  }
}
```

## ❌ 실패 - 400 Bad Request

```json
{
  "status": 400,
  "message": "유효하지 않은 상태값입니다.",
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