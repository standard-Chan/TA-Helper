## 실수

### 403 Forbidden

Spring Security를 사용하면 가능한 URL이 아닌 경우 404 에러가 아니라 403 에러가 발생한다.
이는 Security 필터 우선순위 때문에 그런것.

만약 다른 URL은 정상적으로 작동하는데, 특정 URL에 403에러 Forbidden 이 발생한다면 URL이 옳바른지 확인하도록 하자. 