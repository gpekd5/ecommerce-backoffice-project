# 🔹 Request

## Headers

```json
Content-Type: application/json
```

## Body

```json
{
	"name": "아이폰15",
	"category": "ELECTRONICS",
	"price": 1500000,
	"stock": 100,
	"status": "AVAILABLE"
}
```

| 변수명 | 타입 | 설명 |
| --- | --- | --- |
| name | String | 상품명 |
| category | ENUM | 상품 카테고리 |
| price | Long | 가격 |
| stock | Long | 수량 |
| status | ENUM | 상품 현 상태 |

# 🔹 Response

## Headers

```json
Content-Type: application/json
```

## Body

```json
{
	"id": 2,
	"name": "아이폰15",
	"category": "ELECTRONICS",
	"price": 1500000,
	"stock": 100,
	"status": "AVAILABLE",
	"createdAt": "2026-04-24",
	"createdByName": "홍길동"
}
```

| 변수명 | 타입 | 설명 |
| --- | --- | --- |
| id | Long | 상품 고유식별자 |
| name | String | 상품명 |
| category | String | 카테고리 |
| price | Long | 가격 |
| stock | Long | 재고 |
| status | String | 상태 |
| createdAt | LocalDate | 등록일 |
| createdByName | String | 등록관리자명 |

## ⭕ 성공 - 201 Created