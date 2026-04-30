# 🔹Request

## Headers

```json
Cookie: JSESSIONID={sessionId}
```

## Query Parameters

```json
GET /api/admin/orders?keyword=홍길동&page=1&size=10&sortBy=orderedAt&sortDirection=desc&status=PREPARING
```

| 변수명 | 타입 | 필수 | 기본값 | 설명 |
| --- | --- | --- | --- | --- |
| keyword | String | X | - | 주문번호 또는 고객명 검색 |
| page | Integer | X | 1 | 페이지 번호 |
| size | Integer | X | 10 | 페이지 당 개수 |
| sortBy | String | X | orderedAt | 정렬 기준 |
| sortDirection | String | X | desc | 정렬 순서 |
| status | orderStatus | X | - | 주문 상태 필터 |

### 정렬 기준

| 값 | 설명 |
| --- | --- |
| quantity | 수량 |
| totalPrice | 금액 |
| orderedAt | 주문일 |

### 정렬 순서

| 값 | 설명 |
| --- | --- |
| asc | 오름차순 |
| desc | 내림차순 |

| 값 | 설명 |
| --- | --- |
| PREPARING | 준비중 |
| SHIPPING | 배송중 |
| DELIVERED | 배송완료 |
| CANCELED | 주문취소 |

# 🔹Response

## ✅ 성공 - 200 OK

```json
{
  "orders": [
    {
      "id": 1,
      "orderNumber": "20260424-001",
      "customerName": "홍길동",
      "productName": "무선 마우스",
      "quantity": 3,
      "totalPrice": 45000,
      "orderedAt": "2026-04-24T15:30:00",
      "orderStatus": "PREPARING",
      "createdByAdminName": "관리자A"
    }
  ],
  "page": {
    "currentPage": 1,
    "size": 10,
    "totalElements": 57,
    "totalPages": 6
  }
}
```

| 변수명 | 타입 | 설명 |
| --- | --- | --- |
| orderId | Long | 주문 고유 식별자 |
| orderNumber | String | 주문번호 |
| customerName | String | 고객명 |
| productName | String | 상품명 |
| quantity | Integer | 주문 수량 |
| totalPrice | Integer | 주문 금액 |
| orderedAt | LocalDateTime | 주문일 |
| orderStatus | String | 주문 상태 |
| createdByAdminName | String | 등록 관리자명 |

| 변수명 | 타입 | 설명 |
| --- | --- | --- |
| currentPage | Integer | 현재 페이지 |
| size | Integer | 페이지당 개수 |
| totalElements | Long | 전체 주문 개수 |
| totalPages | Integer | 전체 페이지 수 |

## ❌ 400 Bad Request

#### 페이지 번호가 1보다 작은 경우

```json
{
  "status": 400,
  "message": "페이지 번호는 1 이상이어야 합니다.",
  "data": null
}
```

#### 페이지당 개수가 1보다 작은 경우

```json
{
  "status": 400,
  "message": "페이지당 개수는 1 이상이어야 합니다.",
  "data": null
}
```

#### 지원하지 않는 정렬 기준인 경우

```json
{
  "status": 400,
  "message": "지원하지 않는 정렬 기준입니다.",
  "data": null
}
```

#### 지원하지 않는 정렬 순서일 경우

```json
{
  "status": 400,
  "message": "정렬 순서는 asc 또는 desc만 가능합니다.",
  "data": null
}
```

#### 지원하지 않는 주문 상태인 경우

```json
{
  "status": 400,
  "message": "지원하지 않는 주문 상태입니다.",
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

## 관리자 권한이 없는 경우

```json
{
  "status": 403,
  "message": "접근 권한이 없습니다.",
  "data": null
}
```