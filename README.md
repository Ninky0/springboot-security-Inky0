# 회원 관리
**Point**
- OAuth2
- JWT 토큰
- Redis
  - Docker

## OAuth2
### OAuth2를 선택한 이유
- 서비스의 자체 회원가입을 사용할 시, 서비스 제공자는 두 가지를 책임져야한다.
  - 회원이 기입한 개인 정보에 접근할 수 있는 중요 식별 정보를 관리해야 한다.
  - 회원이 아이디/비밀번호를 복구할 수 있는 방법을 제공해야 한다.

👍 OAuth2를 이용하면, 두 책임은 해당 OAuth2 서비스를 제공하는 플랫폼에 있다.

➔ 본 서비스는 책임지지 않아도 되므로, 개발 생산성이 향상된다.

<p></p>

- 서비스의 자체 회원가입을 사용할 시, 사용자에게 요구하는 입력 정보가 많아진다.

👍 OAuth2를 이용하면, 해당 OAuth2 서비스에서 기존에 사용하던 계정으로 로그인할 수 있다.

➔ 본 서비스를 자연스럽게 사용할 수 있어, 사용자 경험이 증가한다.

<p></p>

## Access Token 만료 시간
- Refresh Token을 클라이언트에서 관리할 시, 토큰을 탈취 당할 경우 꽤 긴 시간동안 탈취한 토큰을 이용하여 AccessToken을 재발급 받을 수 있다.
- Access Token을 탈취 당할 경우, 토큰을 만료시킬 방법이 없으므로 -> Access Token 만료 시간을 짧게 설정했다. (30분)

## Refresh Token 관리 방법 (DB)
### ✅ Redis - 채택
- TTL(Time-To-Live) : 저장한 데이터를 TTL 설정에 따라서 **자동으로 삭제** 해준다.
  - 스케줄러와 같은 프로그램을 만들어서 직접 데이터를 지워주는 작업을 하지 않아도 된다.
    
- 조회 (2번)
1) AccessToken으로 RefreshToken을 조회
2) 새로 발급한 AccessToken의 값으로 update

    - MySQL보다 조회 수가 한 번 더 많은데?! 왜 선택을 했느냐??
      - 캐싱용으로도 많이 쓰이는 만큼, Redis는 **읽기 속도**가 **매우 빠름**.
      
### ⚠️ MySQL
- 스케줄러와 같은 프로그램을 만들어서 Refresh Token을 만료시켜줘야한다.

- 조회 (1번)
1) AccessToken에서 추출한 이메일로 조회한 회원의 refresh Token이 있는지 확인

    - 한 번의 쿼리가 나가더라도 이 쿼리는 한 유저당 30분에 한번 실행된다.
      50명이 1시간동안 서비스를 이용한다해도, 100번의 쿼리가 실행된다.
      MySQL로는 이미 여러개의 다른 비즈니스 로직(기본 회원정보 저장 포함)을 처리하고 있을텐데,
      토큰 관리까지 맡는다면 **많은 부하**를 주게 된다.

### Docker
실제로 Redis를 로컬 또는 생산 환경에 설치하고 설정하는 대신, Docker를 사용하였다. 
*(레디스를 처음 써보기도하지만, 마침, 도커를 막 배우기 시작했기에, 실습겸..사겸사)*

- Docker 사용 **장점**
  - 환경 일관성 : Docker는 "한 번 설정하면 어디서든 실행된다(Write Once, Run Anywhere)"를 보장한다. 이로 인해 개발, 테스트, 운영 환경 간의 차이로 인한 문제를 최소화할 수 있다.
  - 간편한 관리 : 복잡한 설치 및 구성 과정 없이 Docker Hub와 같은 Registry에서 이미지를 가져와 간편하게 어플리케이션을 배포 및 실행할 수 있다.
  - 리소스 효율성 : 컨테이너는 가상 머신보다 훨씬 적은 자원을 사용하며, 더 빠르게 시작할 수 있다.

- Docker와 Redis
  - 'redis:alpine' 이미지 : Redis 서버와 그 실행에 필요한 최소한의 Alpine Linux 환경을 포함한다.
  - Redis 컨테이너 : 설치된 Redis 서버처럼 작동하지만, 실제로 호스트 시스템에는 Redis가 설치되지 않는다.
  - 포트매핑 : 호스트와 컨테이너 간에 포트 매핑을 설정하여 외부에서 컨테이너의 서비스에 접근할 수 있다.
  - 볼륨 : 컨테이너가 삭제되어도 데이터는 유지된다.(데이터 영속성)
<p> 🔎 연결 (.yml)</p>
<img width="791" alt="image" src="https://github.com/user-attachments/assets/affe07ac-828e-411f-9fd8-1a8ce530e955">

<h3 align="left">Languages and Tools:</h3>
<p align="left"> 
    <a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/> </a> 
<a href="https://www.w3.org/html/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/html5/html5-original-wordmark.svg" alt="html5" width="40" height="40"/> </a> <a href="https://www.w3schools.com/css/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/css3/css3-original-wordmark.svg" alt="css3" width="40" height="40"/> </a> <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/javascript/javascript-original.svg" alt="javascript" width="40" height="40"/> </a> 

<a href="https://spring.io/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="40" height="40"/> </a> 
<a href="https://www.docker.com/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/docker/docker-original-wordmark.svg" alt="docker" width="40" height="40"/> </a> 
  <a href="https://www.mysql.com/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/mysql/mysql-original-wordmark.svg" alt="mysql" width="40" height="40"/> </a> <a href="https://redis.io" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/redis/redis-original-wordmark.svg" alt="redis" width="40" height="40"/> </a> </p>
  
  
  

  





      
