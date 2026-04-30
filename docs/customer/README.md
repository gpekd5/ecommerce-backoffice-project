## 고객 API 상세 명세서

| Method | URL                            | Desc      |
|:-------|:-------------------------------|:----------|
| GET    | /customers                     | 고객 리스트 조회 |
| GET    | /customers/{customerId}        | 고객 상세 조회  |
| PATCH  | /customers/{customerId}        | 고객 정보 수정  |
| PATCH  | /customers/{customerId}/status | 고객 상태 변경  |
| DELETE | /customers/{customerId}        | 고객 삭제     |

### [고객 리스트 조회](getCustomerList.md)
### [고객 상세 조회](getDetailCustomer.md)
### [고객 정보 수정](updateCustomer.md)
### [고객 상태 변경](changeState.md)
### [고객 삭제](deleteCustomer.md)