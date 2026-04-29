# 🔹 Request

## Headers

```
Cookie: JSESSIONID={sessionId}
```

## Body

없음

## Query Parameters 예시

```
keyword=김&page=1&size=10&sortBy=createdAt&direction=desc&role=OPERATION&status=ACTIVE
```

| 변수명       | 타입     | 설명                                                               |
|-----------|--------|------------------------------------------------------------------|
| keyword   | String | 이름/이메일 검색어                                                       |
| page      | Long   | 페이지 번호                                                           |
| size      | Long   | 페이지당 개수                                                          |
| sortBy    | String | 정렬 기준                                                            |
| direction | String | asc / desc                                                       |
| role      | Enum   | 역할 필터 (`SUPER`, `OPERATION`, `CS` )                              |
| status    | Enum   | 상태 필터 (`ACTIVE`, `INACTIVE`, `SUSPENDED`, `PENDING`, `REJECTED`) |

# 🔹 Response

## ⭕ 성공 - 200 OK

## Body

```json
{
  "status": 200,
  "message": "관리자 목록 조회 성공",
  "data": {
    "content": [
      {
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
    ],
    "currentPage": 1,
    "size": 10,
    "totalElements": 1,
    "totalPages": 1
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