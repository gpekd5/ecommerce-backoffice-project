## 관리자 API 상세 명세서

| Method | URL                       | Desc      |
|:-------|:--------------------------|:----------|
| POST   | /admins/signup            | 관리자 회원가입  |
| GET    | /admins                   | 관리자 목록 조회 |
| GET    | /admins/{adminId}         | 관리자 상세 조회 |
| PATCH  | /admins/{adminId}         | 관리자 정보 수정 |
| PATCH  | /admins/{adminId}/role    | 관리자 역할 변경 |
| PATCH  | /admins/{adminId}/status  | 관리자 상태 변경 |
| PATCH  | /admins/{adminId}/approve | 관리자 승인    |
| PATCH  | /admins/{adminId}/reject  | 관리자 거부    |
| DELETE | /admins/{adminId}         | 관리자 삭제    |
| GET    | /admins/me                | 내 프로필 조회  |
| PATCH  | /admins/me                | 내 프로필 수정  |
| PATCH  | /admins/me/password       | 비밀번호 변경   |

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