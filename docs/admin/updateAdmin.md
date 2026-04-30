# 🔹 Request

## Headers

```
Content-Type: application/json
Cookie: JSESSIONID={sessionId}
```

## Body

```json
{
  "name": "김운영",
  "email": "operation@test.com",
  "phone": "010-1111-2222"
}
```

| 변수명   | 타입     | 설명      |
|-------|--------|---------|
| name  | String | 관리자 이름  |
| email | String | 관리자 이메일 |
| phone | String | 전화번호    |

# 🔹 Response

## ⭕ 성공 - 200 OK

## Body

```json
{
  "status": 200,
  "message": "관리자 정보 수정 성공",
  "data": {
    "id": 1,
    "name": "김운영",
    "email": "operation@test.com",
    "phone": "010-1111-2222"
  }
}
```

## ❌ 실패 - 400 Bad Request

```json
{
  "status": 400,
  "message": "수정 가능한 값이 없습니다.",
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

## ❌ 실패 - 409 Conflict

```json
{
  "status": 409,
  "message": "이미 사용 중인 이메일입니다.",
  "data": null
}
```