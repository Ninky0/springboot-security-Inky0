# 회원 관리
**Point**
- JWT 토큰
- Redis
- OAuth2

## Access Token 만료 시간
- Refresh Token을 클라이언트에서 관리할 시, 토큰을 탈취 당할 경우 꽤 긴 시간동안 탈취한 토큰을 이용하여 AccessToken을 재발급 받을 수 있다.
- Access Token을 탈취 당할 경우, 토큰을 만료시킬 방법이 없으므로 -> Access Token 만료 시간을 짧게 설정했다. (30분)

## Refresh Token 관리 방법 (DB)
### ✅ Redis 
- TTL(Time-To-Live) : 저장한 데이터를 TTL 설정에 따라서 **자동으로 삭제**
  - 스케줄러와 같은 프로그램을 만들어서 직접 데이터를 지워주는 작업을 하지 않아도 됨
    
- 조회 (2번)
1) AccessToken으로 RefreshToken을 조회
2) 새로 발급한 AccessToken의 값으로 update

    - MySQL보다 조회 수가 한 번 더 많은데?! 왜 선택을 했느냐??
      - 캐싱용으로도 많이 쓰이는 만큼, Redis는 **읽기 속도**가 **매우 빠름**.
      
### ⚠️ MySQL
- 스케줄러와 같은 프로그램을 만들어서 Refresh Token을 만료시켜줘야함.

- 조회 (1번)
1) AccessToken에서 추출한 이메일로 조회한 회원의 refresh Token이 있는지 확인

    - 한 번의 쿼리가 나가더라도 이 쿼리는 한 유저당 30분에 한번 실행됨.

      50명이 1시간동안 서비스를 이용한다해도, 100번의 쿼리가 실행됨.

      MySQL로는 이미 여러개의 다른 비즈니스 로직을 처리하고 있을텐데, 토큰 관리까지 맡는다면 **많은 부하**를 주게 됨.



<h3 align="left">Languages and Tools:</h3>
<p align="left"> <a href="https://www.w3schools.com/css/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/css3/css3-original-wordmark.svg" alt="css3" width="40" height="40"/> </a> <a href="https://www.docker.com/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/docker/docker-original-wordmark.svg" alt="docker" width="40" height="40"/> </a> <a href="https://www.w3.org/html/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/html5/html5-original-wordmark.svg" alt="html5" width="40" height="40"/> </a> <a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/> </a> <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/javascript/javascript-original.svg" alt="javascript" width="40" height="40"/> </a> <a href="https://www.mysql.com/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/mysql/mysql-original-wordmark.svg" alt="mysql" width="40" height="40"/> </a> <a href="https://redis.io" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/redis/redis-original-wordmark.svg" alt="redis" width="40" height="40"/> </a> <a href="https://spring.io/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="40" height="40"/> </a> </p>

      
