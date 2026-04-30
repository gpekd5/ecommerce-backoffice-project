# 🔹 Request

## Headers

```json
Content-Type: application/json
```

## Path / Query Parameters

| 파라미터    | 타입      | 기본값       | 필수여부 | 상세설명                      |
|---------|---------|-----------|------|---------------------------|
| keyword | String  | -         | N    | 검색어 (고객명 또는 상품명 포함 시 조회)  |
| page    | Integer | 1         | N    | 요청 페이지 번호 (1부터 시작)        |
| size    | Integer | 10        | N    | 페이지당 출력할 리뷰 개수            |
| sort    | String  | createdAt | N    | 정렬 기준 (rating, createdAt) |
| order   | String  | desc      | N    | 정렬 순서 (asc, desc)         |
| rating  | Integer | -         | N    | 평점 필터 (1~5 정수)            |

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
    "order_number": 20260423001,
    "customer_name": "이순신",
    "product_name": "아이폰15",
    "rating": 5,
    "comment": "역시 최신 폰이 더 좋네요 ㅎㅎㅎ",
    "createdAt": "2026-04-24"
  },
  {
    "id": 1,
    "order_number": 20260422001,
    "customer_name": "이순신",
    "product_name": "아이폰13",
    "rating": 4,
    "comment": "적당히 좋네요",
    "createdAt": "2026-04-23"
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
| id            | Long      | 리뷰 고유식별자 |
| order_number  | String    | 주문번호     |
| customer_name | String    | 고객명      |
| product_name  | Long      | 상품명      |
| rating        | Long      | 평점       |
| comment       | String    | 리뷰내용     |
| createdAt     | LocalDate | 작성일      |

## ⭕ 성공 - 200 Ok