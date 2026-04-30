# 🔹 Request

## Path Variable

| 변수명 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| id | Long | O | 주문 고유 식별자 |

## Headers

```json
Cookie: JSESSIONID={sessionId}
```

# 🔹 Response

## ✅ 성공 - 200 OK

```json
{
  "id": 1,
  "orderNumber": "20260424-001",
  "customerName": "홍길동",
  "customerEmail": "customer@example.com",
  "productName": "무선 마우스",
  "quantity": 3,
  "totalPrice": 45000,
  "orderedAt": "2026-04-24T15:30:00",
  "orderStatus": "PREPARING",
  "createdByAdminName": "관리자A",
  "createdByAdminEmail": "admin@example.com",
  "createdByAdminRole": "CS_MANAGER"
}
```

| 변수명 | 타입 | 설명 |
| --- | --- | --- |
| id | Long | 주문 고유 식별자 |
| orderNumber | String | 주문번호 |
| customerName | String | 고객명 |
| customerEmail | String | 고객 이메일 |
| productName | String | 상품명 |
| quantity | Integer | 주문 수량 |
| totalPrice | Integer | 총 주문 금액 |
| orderedAt | LocalDateTime | 주문일 |
| orderStatus | OrderStatus | 주문 상태 |
| createdByAdminName | String | 등록 관리자명 |
| createdByAdminEmail | String | 등록 관리자 이메일 |
| createdByAdminRole | String | 등록 관리자 역할 |

## ❌ 404 - Not Found

#### 주문이 존재하지 않는 경우

```json
{
  "status": 404,
  "message": "주문을 찾을 수 없습니다.",
  "data": null
}
```

## ❌ 401 - Unauthorized

#### 로그인하지 않은 경우

```json
{
  "status": 404,
  "message": "로그인한 관리자를 찾을 수 없습니다.",
  "data": null
}
```

## ❌ 403 - Forbidden

#### 관리자 권한이 없는 경우

```json
{
  "status": 403,
  "message": "접근 권한이 없습니다.",
  "data": null
}
```