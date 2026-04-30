# 🔹 Request

## Headers

```json
Content-Type: application/json
```

## Path / Query Parameters

| 파라미터 | 타입   | 기본값 | 필수여부 | 상세설명       |
|------|------|-----|------|------------|
| id   | Long | -   | Y    | product_id |

## Body

```json
{
  "name": "아이폰16",
  "category": "ELECTRONICS",
  "price": 1800000
}
```

| 변수명      | 타입     | 설명   |
|----------|--------|------|
| name     | String | 상품명  |
| category | String | 카테고리 |
| price    | Long   | 가격   |

# 🔹 Response

## Headers

```json
Content-Type: application/json
```

## Body

```json
{
  "id": 2,
  "name": "아이폰16",
  "category": "ELECTRONICS",
  "price": 1800000,
  "stock": 100,
  "status": "AVAILABLE",
  "createdAt": "2026-04-24",
  "createdByName": "홍길동",
  "createdByEmail": "hong@sparta.com"
}
```

| 변수명            | 타입        | 설명         |
|----------------|-----------|------------|
| id             | Long      | 상품 고유식별자   |
| name           | String    | 상품명        |
| category       | String    | 카테고리       |
| price          | Long      | 가격         |
| stock          | Long      | 재고         |
| status         | String    | 상태         |
| createdAt      | LocalDate | 등록일        |
| createdByName  | String    | 등록 관리자명    |
| createdByEmail | String    | 등록 관리자 이메일 |

## ⭕ 성공 - 200 Ok