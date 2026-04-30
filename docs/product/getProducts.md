# 🔹 Request

## Headers

```json
Content-Type: application/json
```

## Path / Query Parameters

| 파라미터     | 타입      | 기본값       | 필수여부 | 상세설명                                      |
|----------|---------|-----------|------|-------------------------------------------|
| keyword  | String  | -         | N    | 상품명 검색어 (부분 일치)                           |
| page     | Integer | 1         | N    | 요청 페이지 번호 (1부터 시작)                        |
| size     | Integer | 10        | N    | 페이지당 출력할 상품 개수                            |
| sort     | String  | createdAt | N    | 정렬 기준 (price, stock, createdAt)           |
| order    | String  | desc      | N    | 정렬 순서 (asc, desc)                         |
| category | String  | -         | N    | 카테고리 필터 (ELECTRONICS, FASHION, FOOD 등)    |
| status   | String  | -         | N    | 상태 필터 (AVAILABLE, SOLD_OUT, DISCONTINUED) |

## Body

```json

```

| 변수명 | 타입 | 설명 |
|-----|----|----|
|     |    |    |

# 🔹 Response

## Headers

```json
Content-Type: application/json
```

## Body

```json
[
  {
    "id": 2,
    "name": "아이폰15",
    "category": "ELECTRONICS",
    "price": 1500000,
    "stock": 100,
    "status": "AVAILABLE",
    "createdAt": "2026-04-24",
    "createdByName": "홍길동"
  },
  {
    "id": 1,
    "name": "아이폰13",
    "category": "ELECTRONICS",
    "price": 1000000,
    "stock": 100,
    "status": "AVAILABLE",
    "createdAt": "2026-04-24",
    "createdByName": "홍길동"
  }
],
"pageInfo": {
"currentPage": 1, // 현재 페이지 번호
"size": 10, // 한 페이지당 보여줄 개수
"totalElements": 2, // 전체 상품 개수 (DB 카운트 결과)
"totalPages": 1       // 전체 페이지 수 (totalElements / size를 올림한 값)
}
```

| 변수명           | 타입        | 설명       |
|---------------|-----------|----------|
| id            | Long      | 상품 고유식별자 |
| name          | String    | 상품명      |
| category      | String    | 카테고리     |
| price         | Long      | 가격       |
| stock         | Long      | 재고       |
| status        | String    | 상태       |
| createdAt     | LocalDate | 등록일      |
| createdByName | String    | 등록관리자명   |

## ⭕ 성공 - 201 Created