# 전역 예외처리

## 목적

Controller 또는 Service 계층에서 발생하는 예외를 중앙에서 처리하여,
클라이언트에게 일관된 에러 응답을 반환한다.

## 적용 위치

global/exception

## 주요 클래스

- ServiceException
- BadRequestException
- UnauthorizedException
- ForbiddenException
- NotFoundException
- ConflictException
- GlobalExceptionHandler

## 처리 예외

| 예외 클래스                          | HTTP Status | 사용 상황                 |
|---------------------------------|-------------|-----------------------|
| BadRequestException             | 400         | 요청값이 잘못된 경우           |
| UnauthorizedException           | 401         | 로그인이 필요한 경우           |
| ForbiddenException              | 403         | 권한이 없는 경우             |
| NotFoundException               | 404         | 조회 대상이 없는 경우          |
| ConflictException               | 409         | 데이터 중복 또는 상태 충돌       |
| MethodArgumentNotValidException | 400         | @Valid 검증 실패          |
| IllegalArgumentException        | 400         | 잘못된 인자값 또는 Enum 변환 실패 |

## 응답 예시

```
이미 사용중인 이메일입니다.
```

## 적용 예시

```
thrownewForbiddenException("슈퍼 관리자만 접근할 수 있습니다.");
```

```

로그인 검증 인터셉터는 이렇게.

```markdown
# 로그인 검증 인터셉터

## 목적

인증이 필요한 API에 접근하기 전에 세션 로그인 여부를 확인한다.

## 적용 위치

global/interceptor

## 검증 기준

- 세션이 존재하는지 확인
- 세션에 loginUser 값이 존재하는지 확인

## 실패 처리

로그인 정보가 없으면 UnauthorizedException 발생

```java
throw new UnauthorizedException("로그인이 필요합니다.");
```

## 실패 응답

```
로그인이 필요합니다.
```

```

정리하면 지금 체크표에는 **API Path가 없는 공통 기능도 그대로 넣어도 돼.**
대신 `Api Path` 칸은 `없음` 또는 `-`로 적고, 설명에 “공통 로직”이라고 써두면 된다.
```