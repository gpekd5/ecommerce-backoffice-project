# **1. 중괄호 { } (brace)**

괄호는 if, else, for, do 및 while문에 코드가 없거나 단 하나라도 생략하지 않습니다.

{ 의 경우

- 여는 중괄호 전에는 개행하지 않습니다.
- 여는 중괄호 뒤에서는 개행합니다.

} 의 경우

- 닫는 괄호 앞에서 개행합니다.
- 닫는 괄호 뒤의 개행은, 중괄호가 끝나거나 생성자, 메소드, 클래스가 끝날 때 개행합니다.
  그러므로, else나 , 앞에서는 개행하지 않습니다.

good example

```java
return () -> {
  while (condition()) {
    method();
  }
};

return new MyClass() {
  @Override 
	public void method() {
    if (condition()) {
      try {
        something();
      } catch (ProblemException e) {
        recover();
      }
    } else if (otherCondition()) {
      somethingElse();
    } else {
      lastThing();
    }
  }
};
```

# 2. 비어있는 블록

빈 블록은 개행하거나, 개행하지 않거나 상관없습니다. 하지만 멀티 블록일 경우 개행해주어야 합니다.

```java
// This is acceptable
void doNothing() {}

// This is equally acceptable
void doNothingElse() {
}

// This is not acceptable: No concise empty blocks in a multi-block statement
try {
doSomething();
} catch (Exception e) {} // 개행 해야 함
```

# 3. 네이밍

## 1. 식별자

- 영문/숫자/언더스코어만 허용합니다.
- 상수에는 단어 사이의 구분을 위하여 언더스코어(_)를 사용합니다.
- 식별자의 이름을 한구겅 발음대로 영어로 옮겨 표기하지 않습니다.
    - 좋은 예 : intangibleAssets(무형자산)
    - 나쁜 예 : mooHyungJasan(무형자산)

## 2. 패키지

- 기본적으로 소문자로 구성합니다.

```java
// 좋은 예
package com.navercorp.apigateway;

// 나쁜 예
package com.navercorp.apiGateway;
package com.navercorp.api_gateway;
```

## 3. 클래스 및 인터페이스

- 대문자 카멜 표기법을 적용합니다.
    - 좋은 예 : Comment
    - 나쁜 예 : comment
- 클래스 이름은 동사가 아닌 명사나 명사절로 짓습니다.
- 인터페이스 이름은 형용사/형용사절로 짓습니다.

## 4. 메서드 및 변수

- 소문자 카멜 표기법(ex. accessToken)을 사용합니다.
- 메서드 명은 기본적으로 동사로 시작합니다.
    - 예시 : findUserId()
- 임시 변수 외에는 한 글자로 이루어진 변수 이름을 사용하지 않습니다.

# 4. 계층별 메서드 규칙

## 1. 클래스명

- 패키지 구조 및 dto 클래스 명은 아래와 같은 방식으로 합니다.

![image.png](docs/image.png)

## 2. 컨트롤러 규칙

- return 타입은 아래와 동일하게 합니다.

```java
@PostMapping
public ResponseEntity<CreateScheduleResponseDto> createSchedule(
        @Valid @RequestBody CreateScheduleRequestDto request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(request));
}
```

## 3. 서비스 규칙

- DTO에 정적 팩토리 메서드를 합니다.

```java
return CreateScheduleResponseDto.from(saveSchedule);
```
