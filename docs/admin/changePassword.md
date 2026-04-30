# 🔹 Request

## Headers

```
Content-Type: application/json
Cookie: JSESSIONID={sessionId}
```

## Body

```json
{
  "currentPassword": "oldPassword123",
  "newPassword": "newPassword123",
  "newPasswordConfirm ": "newPassword123"
}
```

| 변수명                | 타입     | 설명        |
|--------------------|--------|-----------|
| currentPassword    | String | 현재 비밀번호   |
| newPassword        | String | 새 비밀번호    |
| newPasswordConfirm | String | 새 비밀번호 확인 |

# 🔹 Response

## ⭕ 성공 - 200 OK

## Body

```json
{
  "status": 200,
  "message": "비밀번호가 변경 되었습니다.",
  "data": null
}
```

## ❌ 실패 - 400 Bad Request

### 입력값 검증 실패

```json
{
  "status": 400,
  "message": "비밀번호는 최소 8자 이상이어야 합니다.",
  "data": null
}
```

### 새 비밀번호 확인 불일치

```json
{
  "status": 400,
  "message": "새 포스팅이 일치하지 않습니다.",
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

### 현재 비밀번호 불일치

```json
{
  "status": 401,
  "message": "현재 비밀번호가 일치하지 않습니다.",
  "data": null
}
```