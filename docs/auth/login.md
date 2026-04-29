# 🔹 Request

## Headers

```
Content-Type: application/json
```

## Body

```json
{
  "email": "admin@sparta.com",
  "password": "sparta1234"
}
```

| 변수명 | 타입 | 설명 |
| --- | --- | --- |
| email | String | 관리자 이메일 |
| password | String | 비밀번호 |

# 🔹 Response

## ⭕ 성공 - 200 OK

```
🔹 세션 관리 정책

- 로그인 성공 시 서버에서 세션을 생성합니다.
- 세션에는 관리자의 `id`, `email`, `role` 정보를 저장합니다.
- 서버는 응답 헤더에 `Set-Cookie`를 통해 세션 ID를 전달합니다.
- 클라이언트는 이후 요청부터 `Cookie` 헤더에 세션 ID를 포함하여 요청합니다.
- 세션 유효 시간은 24시간으로 설정합니다.
- 로그아웃 시 서버 세션을 무효화합니다.
- 쿠키는 `HttpOnly` 옵션을 적용하여 클라이언트 스크립트에서 접근할 수 없도록 합니다.
```

## Headers

```
Set-Cookie: JSESSIONID={sessionId}; Path=/; HttpOnly
```

## Body

```json
{
  "status": 200,
  "message": "로그인에 성공했습니다.",
  "data": {
    "id": 1,
    "email": "admin@sparta.com",
    "name": "관리자",
    "role": "SUPER_ADMIN",
    "status": "ACTIVE"
  }
}
```

## ❌ 실패 - 401 Unauthorized

```json
{
  "status": 401,
  "message": "이메일 또는 비밀번호가 올바르지 않습니다.",
  "data": null
}
```

## ❌ 실패 - 403 Forbidden

### 계정 승인대기 상태

```json
{
  "status": 403,
  "message":"승인 대기 중인 관리자 계정입니다.",
  "data": null
}
```

### 계정 거부 상태

```json
{
  "status": 403,
  "message":"승인 거부된 관리자 계정입니다.",
  "data": null
}
```

### 계정 정지 상태

```json
{
  "status": 403,
  "message":"정지된 관리자 계정입니다.",
  "data": null
}
```

### 계정 비활성 상태

```json
{
  "status": 403,
  "message": "비활성화된 관리자 계정입니다.",
  "data": null
}
```