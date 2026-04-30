## 상품 API 상세 명세서

| Method | URL                          | Desc     |
|:-------|:-----------------------------|:---------|
| POST   | /products                    | 상품 등록    |
| GET    | /products                    | 상품 전체조회  |
| GET    | /products/{productId}        | 상품 상세조회  |
| PATCH  | /products/{productId}        | 상품 정보 수정 |
| PATCH  | /products/{productId}/status | 상품 상태 변경 |
| DELETE | /products/{productId}        | 상품 삭제    |

### [상품 등록](createProduct.md)

### [상품 전체 조회](getProducts.md)

### [상품 상세 조회](getProduct.md)

### [상품 정보 수정](updateProduct.md)

### [상품 상태 변경](updateProductStatus.md)

### [상품 삭제](deleteProduct.md)