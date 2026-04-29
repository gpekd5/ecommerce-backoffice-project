## 관리자 API 상세 명세서

| Method | URL                            | Desc      |
|:-------|:-------------------------------|:----------|
| GET    | /customers                     | 고객 리스트 조회 |
| GET    | /customers/{customerId}        | 고객 상세 조회  |
| PATCH  | /customers/{customerId}        | 고객 정보 수정  |
| PATCH  | /customers/{customerId}/status | 고객 상태 변경  |
| DELETE | /customers/{customerId}        | 고객 삭제     |

### [관리자 회원가입](signupAdmin.md)
### [관리자 목록 조회](getAdminList.md)
### [관리자 상세 조회](getDetailAdmin.md)
### [관리자 정보 수정](updateAdmin.md)
### [관리자 역할 변경](changeRole.md)
### [관리자 상태 변경](changeStatus.md)
### [관리자 승인](acceptAdmin.md)
### [관리자 거부](rejectAdmin.md)
### [관리자 삭제](deleteAdmin.md)
### [내 프로필 조회](getProfile.md)
### [내 프로필 수정](updateProfile.md)
### [비밀번호 변경](changePassword.md)