# 🔹 Request

## Headers

```json
Cookie: JSESSIONID={sessionId}
```

## Path Variable

| 변수명 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| orderId | Long | O | 주문 고유 식별자 |

## Body

```json
{
  "orderCancelReason": "고객 요청으로 인한 주문 취소"
}
```

# 🔹 Response

## ✅ 성공 - 200 OK

```json
{
  "id": 1,
  "orderNumber": "20260424-001",
  "orderStatus": "CANCELED",
  "cancelReason": "고객 요청으로 인한 주문 취소",
  "canceledAt": "2026-04-24T16:30:00",
  "productId": 10,
  "productName": "무선 마우스",
  "restoredQuantity": 3,
  "currentStock": 8,
  "productStatus": "ON_SALE"
}
```

| 변수명 | 타입 | 설명 |
| --- | --- | --- |
| id | Long | 주문 고유 식별자 |
| orderNumber | String | 주문번호 |
| orderStatus | String | 변경된 주문 상태 |
| cancelReason | String | 주문 취소 사유 |
| canceledAt | LocalDateTime | 주문 취소 일시 |
| productId | Long | 상품 고유 식별자 |
| productName | String | 상품명 |
| restoredQuantity | Integer | 복구된 재고 수량 |
| currentStock | Integer | 복구 후 현재 재고 |
| productStatus | ProductStatus | 복구 후 상품 상태 |

## ❌ 400 Bad Request

#### 취소 사유가 없는 경우

```json
{
  "status": 400,
  "message": "주문 취소 사유는 필수입니다.",
  "data": null
}
```

#### 준비 중 상태가 아닌 주문을 취소하는 경우

```json
{
  "status": 400,
  "message": "준비 중인 상태의 주문만 취소할 수 있습니다.",
  "data": null
}
```

#### 이미 취소된 주문인 경우

```json
{
  "status": 400,
  "message": "이미 취소된 주문입니다.",
  "data": null
}
```

## ❌ 404 - Not Found

#### 주문이 존재하지 않는 경우

```json
{
  "status": 404,
  "message": "주문을 찾을 수 없습니다.",
  "data": null
}
```

#### 상품이 존재하지 않는 경우 (데이터 불일치 등)

```json
{
  "status": 404,
  "message": "상품을 찾을 수 없습니다.",
  "data": null
}
```

## ❌ 401 - Unauthorized

#### 로그인하지 않은 경우

```json
{
  "status": 401,
  "message": "인증이 필요합니다.",
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