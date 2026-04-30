## 1. 목적

우리 팀은 협업 과정에서 브랜치 역할을 명확히 분리하고, 기능 개발 / 배포 / 긴급 수정을 체계적으로 관리하기 위해 Git Flow 전략을 사용한다.

Git Flow를 통해 다음을 목표로 한다.

- 안정적인 배포 브랜치 유지
- 기능별 작업 분리
- 충돌 및 실수 최소화
- 코드 리뷰 및 테스트 흐름 명확화

---

## 2. 브랜치 전략

### master

- 최종 통합 브랜치
- 완성된 작업만 병합하는 브랜치
- 직접 작업하지 않는다

### feature

- 개인 작업 및 기능 개발용 브랜치
- 각자 맡은 기능 단위로 생성한다
- 작업 완료 후 master 브랜치로 병합한다

---

## 3. 브랜치 흐름

```
master
 └─ 최종 통합 브랜치

feature/*
 └─ master에서 생성 → 기능 개발 후 master로 merge
```

---

## 4. 브랜치 네이밍 규칙

### feature 브랜치

```
feature/이름-기능명
```

예시

```
feature/khg-login
feature/khg-admin
feature/khg-admin-create
```

---

## 5. 작업 절차

### 5-1. 기능 개발

1. develop 최신화
2. feature 브랜치 생성
3. 기능 개발 진행
4. feature 브랜치를 원격 저장소에 push
5. Pull Request 생성
6. 리뷰 후 master에 merge

예시 명령어:

```
git checkout develop
git pull origin develop
git checkout -b feature/khg-login
```

작업 후:

```
git add .
git commit -m "feat: 로그인 기능 추가"
git push origin feature/khg-login
```

---

---

---

## 6. Pull Request 규칙

- 모든 작업은 Pull Request를 통해 병합한다
- 직접 main, develop에 push하지 않는다
- PR 작성 시 작업 내용을 명확히 적는다
- 최소 1명 이상의 리뷰 후 병합한다
- 리뷰 없이 self merge하지 않는다 (팀 규칙에 따라 예외 가능)

### PR 제목 예시

```
[Feature] 로그인 기능 추가
[Feature] 관리자 생성 기능 추가
[Fix] 관리자 등록 오류 수정
```

### PR 본문 예시

```
## 작업 내용
- 댓글 생성 API 구현
- 댓글 저장 로직 추가
- 댓글 DTO 작성

## 변경 사항
- Comment 엔티티 추가
- CommentService save 메서드 구현
- CommentController POST API 추가

## 체크리스트
- [ ] 로컬 테스트 완료
- [ ] 예외 처리 확인
- [ ] 리뷰 요청 완료
```

---

## 7. 커밋 메시지 규칙

커밋 메시지는 작업 목적이 드러나도록 작성한다.

### 형식

```
타입: 작업 내용
```

### 예시 타입

- `feat`: 새로운 기능 추가
- `fix`: 버그 수정
- `refactor`: 리팩토링
- `docs`: 문서 수정
- `test`: 테스트 코드 추가/수정
- `chore`: 설정, 빌드, 기타 작업

### 예시

```
feat: 로그인 기능 추가
fix: 비밀번호 검증 오류 수정
refactor: 일정 조회 로직 분리
docs: API 문서 수정
```

---

## 8. 병합 방식

팀에서 병합 방식은 다음 중 하나로 통일한다.

### merge commit

- 브랜치 이력이 그대로 남음
- 흐름 추적이 쉬움

### squash merge

- 여러 커밋을 하나로 합쳐서 병합
- 히스토리가 깔끔해짐

예시 규칙:

- feature 브랜치 → master 병합
- 필요 시 squash merge 사용
- 병합 전 PR 리뷰 진행

---

## 9. 주의 사항

- master 브랜치에 직접 push 금지
- 작업 시작 전 반드시 최신 master pull
- 하나의 feature 브랜치에는 하나의 기능만 작업
- 브랜치 이름은 `feature/이름-기능명` 형식으로 통일
- 충돌 발생 시 임의로 덮어쓰지 않고 팀원과 공유 후 해결
- PR 범위를 너무 크게 만들지 않기

---

## 10. 예시 시나리오

### 로그인 기능 개발

1. `master`에서 `feature/khg-login` 생성
2. 로그인 기능 구현
3. 원격 저장소에 push
4. PR 생성
5. 리뷰 후 `master`에 merge

### 관리자 등록 기능 개발

1. `master`에서 `feature/khg-admin-create` 생성
2. 관리자 등록 기능 구현
3. 원격 저장소에 push
4. PR 생성
5. 리뷰 후 `master`에 merge

---

## 11. 우리 팀 브랜치 정책 요약

| 브랜치       | 역할           | 생성 기준       | 병합 대상  |
|-----------|--------------|-------------|--------|
| master    | 최종 통합 브랜치    | 기본 브랜치      | -      |
| feature/* | 개인 기능 개발 브랜치 | master에서 분기 | master |

### 깃헙 커밋 규칙

| 작업 타입       | 작업내용                      |
|-------------|---------------------------|
| ✨ update    | 해당 파일에 새로운 기능이 생김         |
| 🎉 add      | 없던 파일을 생성함, 초기 세팅         |
| 🐛 bugfix   | 버그 수정                     |
| ♻️ refactor | 코드 리팩토링                   |
| 🩹 fix      | 코드 수정                     |
| 🚚 move     | 파일 옮김/정리                  |
| 🔥 del      | 기능/파일을 삭제                 |
| 🍻 test     | 테스트 코드를 작성                |
| 💄 style    | css                       |
| 🙈 gitfix   | gitignore 수정              |
| 🔨script    | package.json 변경(npm 설치 등) |