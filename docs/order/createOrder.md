# 🔹 Request

## Headers

```
Content-Type: application/json
Cookie: JSESSIONID={sessionId}
```

## Body

```json
{
  "customerId": 1,
  "productId": 1,
  "quantity": 3
}
```

| 변수명        | 타입   | 설명        |
|------------|------|-----------|
| customerId | Long | 고객 고유 식별자 |
| productId  | Long | 상품 고유 식별자 |
| quantity   | int  | 수량        |

# 🔹Response

## ✅ 성공 - 201 Created

```json
{
  "status": 201,
  "message": "주문을 생성하였습니다.",
  "data": {
    "id": 1,
    "orderNumber": "20260424-001",
    "customerId": 1,
    "productId": 10,
    "quantity": 3,
    "totalPrice": 45000,
    "orderStatus": "PREPARING",
    "orderedAt": "2026-04-24T15:30:00",
    "createdByAdminId": 2
  }
}
```

| 필드명              | 타입            | 설명           |
|------------------|---------------|--------------|
| status           | Integer       | HTTP 상태 코드   |
| message          | String        | 응답 메시지       |
| id               | Long          | 주문 고유 식별자    |
| orderNumber      | String        | 주문번호         |
| customerId       | Long          | 고객 고유 식별자    |
| productId        | Long          | 상품 고유 식별자    |
| quantity         | Integer       | 주문 수량        |
| totalPrice       | Integer       | 총 주문 금액      |
| orderStatus      | OrderStatus   | 주문 상태        |
| orderedAt        | LocalDateTime | 주문 생성 일시     |
| createdByAdminId | Long          | 주문 등록 관리자 ID |

## ❌ 실패 - 400 Bad Request

#### 수량이 1 미만인 경우

```json
{
  "status": 400,
  "message": "주문 수량은 1개 이상이어야 합니다."
}
```

#### 요청값 누락 (Validation 실패)

```json
{
  "status": 400,
  "message": "회원 정보가 누락되었습니다.",
  "data": null
}
```

```json
{
  "status": 400,
  "message": "상품 정보가 누락되었습니다.",
  "data": null
}
```

```json
{
  "status": 400,
  "message": "구매 수량이 누락되었습니다.",
  "data": null
}
```

#### 상품이 단종인 경우

```json
{
  "status": 400,
  "message": "단종된 상품은 주문할 수 없습니다.",
  "data": null
}
```

#### 상품이 품절인 경우

```json
{
  "status": 400,
  "message": "품절된 상품은 주문할 수 없습니다.",
  "data": null
}
```

#### 재고 부족

```json
{
  "status": 400,
  "message": "상품 재고가 부족합니다.",
  "data": null
}
```

## ❌ 실패 - 404 Not Found

#### 고객이 존재하지 않는 경우

```json
{
  "status": 404,
  "message": "고객을 찾을 수 없습니다.",
  "data": null
}
```

#### 상품이 존재하지 않는 경우

```json
{
  "status": 404,
  "message": "상품을 찾을 수 없습니다.",
  "data": null
}
```

## ❌ 401 Unauthorized

#### 로그인이 되지 않은 경우

```json
{
  "status": 401,
  "message": "인증이 필요합니다.",
  "data": null
}
```

## ❌ 403 Forbidden

#### 관리자가 아닌 경우

```json
{
  "status": 403,
  "message": "접근 권한이 없습니다.",
  "data": null
}
```