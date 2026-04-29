# 🔹 Request

## Headers

```
Content-Type: application/json
Cookie: JSESSIONID={sessionId}
```

## Body

```json
{
  "name": "김홍기",
  "email": "gpekd5@test.com",
  "phone": "010-2222-3333"
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
  "message": "내 프로필 수정 성공",
  "data": {
    "id": 1,
    "name": "김홍기",
    "email": "gpekd5@test.com",
    "phone": "010-2222-3333"
  }
}
```

## ❌ 실패 - 400 Bad Request

### 입력값 검증 실패

```json
{
  "status": 400,
  "message": "이름은 필수 입력값입니다.",
  "data": null
}
```

```json
{
  "status": 400,
  "message": "올바른 이메일 형식이 아닙니다.",
  "data": null
}
```

```json
{
  "status": 400,
  "message": "올바른 전화번호 형식이 아닙니다.",
  "data": null
}
```

## ❌ 실패 - 401 Unauthorized

### 로그인하지 않은 경우

```json
{
  "status": 401,
  "message": "로그인이 필요합니다.",
  "data": null
}
```

## ❌ 실패 - 409 Conflict

### 이메일 중복

```json
{
  "status": 409,
  "message": "이미 사용 중인 이메일입니다.",
  "data": null
}
```