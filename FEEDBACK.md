## 실수

## 403 Forbidden

Spring Security를 사용하면 가능한 URL이 아닌 경우 404 에러가 아니라 403 에러가 발생한다.
이는 Security 필터 우선순위 때문에 그런것.

만약 다른 URL은 정상적으로 작동하는데, 특정 URL에 403에러 Forbidden 이 발생한다면 URL이 옳바른지 확인하도록 하자.

---

## Security requestMatchers 적용 순서

> authorizeHttpRequests의 requestMatcher 는 위치에 따라서 적용 순서가 달라진다.

```java
http.~.authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/login/**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
                )
```

```java
http.~.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/login/**").permitAll()
                        .anyRequest().permitAll()
                )
```

첫번째 코드는 /api/login/** 을 먼저 허용해주고 두번째 코드는 /api/** 를 먼저 차단한다. 

Spring Security에서는 먼저 적용한 requestMatchers 부터 차례대로 필터링을 진행한다. 
따라서 두번째 코드의 아래 `/api/login/**` 은 위의 `/api/**`에 차단되어 허가되지 않는다.


## 연관된 데이터인 경우 삭제가 안되는 문제
연관관계에 있는 데이터를 어떻게 처리해야할지에 대해 난해함을 겪음.
연관이 없고 독립적인 테이블 (academy, staff, class_type) 을 우선하여 처리하였다. 
### class, extra-class 
위 데이터는 모든 주간 기록 데이터의 핵심이므로 
데이터의 삭제가 많이 일어나지 않을 것이라고 판단하여 연관관계가 하나라도 있는 경우 
삭제가 불가능하도록 구현하였다.

### Student
Student는 주간기록과 많은 연관관계가 맺어져 있지만, 
업무 중에 실수로 다른 수업 주차 기록에 해당 학생 정보를 입력하여 처리하는 실수 가능성이 있으므로
사용자의 편의를 위해서 삭제가 가능하고 연관관계를 일괄적으로 삭제하는 방향으로 구현하였다. 

3개의 테이블과 연관관계를 끊고, 삭제처리를 진행하였다.

