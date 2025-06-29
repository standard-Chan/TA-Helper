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
