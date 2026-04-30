# 🔹 Request

## Headers

```json
Content-Type: application/json
```

## Path / Query Parameters

| 파라미터 | 타입 | 기본값 | 필수여부 | 상세설명 |
| --- | --- | --- | --- | --- |
| id | Long | - | Y | review_id |

## Body

```json

```

| 변수명 | 타입 | 설명 |
| --- | --- | --- |
|  |  |  |
|  |  |  |

# 🔹 Response

## Headers

```json
Content-Type: application/json
```

## Body

```json
{
	"product_name": "아이폰15",
	"customer_name": "이순신",
	"customer_email": "lee@naver.com",
	"createdAt": "2026-04-24"
	"rating": 5,
	"comment": "역시 최신 폰이 더 좋네요 ㅎㅎㅎ",

}
```

| 변수명 | 타입 | 설명 |
| --- | --- | --- |
| product_name | Long | 상품명 |
| customer_name | String | 고객명 |
| customer_email | String | 고객 이메일 |
| createdAt | LocalDate | 작성일 |
| rating | Long | 평점 |
| comment | String | 리뷰내용 |

## ⭕ 성공 - 200 Ok