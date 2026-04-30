# 🔹 Request

## Headers

```
Content-Type: application/json
```

## Body

```json
{
  "email": "admin01@test.com",
  "password": "password123",
  "name": "김관리",
  "phone": "010-1234-5678",
  "role": "OPERATION_ADMIN"
}
```

| 변수명      | 타입     | 설명                                |
|----------|--------|-----------------------------------|
| email    | String | 관리자 이메일, 중복불가                     |
| password | String | 비밀번호 (MIN 8)                      |
| name     | String | 관리자 이름                            |
| phone    | String | 전화번호                              |
| role     | Enum   | 역할  (`SUPER`, `OPERATION`, `CS` ) |

# 🔹 Response

## ⭕ 성공 - 201 Created

## Body

```json
{
  "status": 201,
  "message": "관리자 회원가입 신청이 완료되었습니다.",
  "data": {
    "id": 1,
    "email": "admin01@test.com",
    "name": "김관리",
    "phone": "010-1234-5678",
    "role": "OPERATION_ADMIN",
    "status": "PENDING",
    "createdAt": "2026-04-24T10:00:00"
  }
}
```

## ❌ 실패 - 400 Bad Request

### 입력값 검증 실패

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
  "message": "비밀번호는 8자 이상 입력해야 합니다.",
  "data": null
}
```

```json
{
  "status": 400,
  "message": "전화번호 형식은 010-0000-0000 형식이어야 합니다.",
  "data": null
}
```

```json
{
  "status": 400,
  "message": "역할은 필수 선택값입니다.",
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