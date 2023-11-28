# jwt-study

- 사용기술 : Java11, Spring Boot2.7.17, JPA, H2, Gradle, JWT, swagger, JUnit5
- swagger 주소 : http://localhost:8080/swagger-ui/
- h2 console 주소 : http://localhost:8080/h2-console/login.do

# 요구 사항 구현 여부
1. 회원 가입 API
    - 필수 파라미터(아이디, 패스워드, 이름, 주민등록번호) - **구현 완료**
    - 민감정보(주민등록번호, 비밀번호) 암호화 - **구현 완료**
2. 로그인 API
    - 필수 파라미터(아이디, 패스워드) - **구현 완료**
    - token 발급 - **구현 완료**
3. 내 정보 보기 API
    - 인증 토큰을 이용하여 자기 정보만 볼 수 있어야 함 - **구현 완료**

# 구현 방법
1. 회원 가입 API
   - 필수 파라미터 체크는 javax.validation를 활용하여 구현하였으며 RestControllerAdvice를 추가해 BindException은 한 곳에서 처리하게 구현했습니다.
   - 비밀번호는 PasswordEncoder, 주민등록번호는 StringCryptoConverter를 이용해 암호화 했습니다.
   - 회원 가입 하기 전 아이디 중복 체크 및 주민등록번호 중복 체크 후 JPA를 통해 회원을 DB에 저장합니다.
2. 로그인 API
   - 로그인 인증 처리는 AuthenticationManager의 authenticate메소드에서 수행하는데 AuthenticationManager 빈을 생성할때 CustomUserDetailsService와 PasswordEncoder를 주입해서 DB에서 해당 계정을 조회 한 후 비밀번호를 비교할 수 있게 구현했습니다.
   - 로그인 인증이 성공했을 경우 TokenProvider를 이용해 JWT Token을 생성합니다. 해당 토큰에는 유저의 아이디값이 들어갑니다.
3. 내 정보 보기 API
   - JWT Token을 header에서 꺼내와서 Controller에서 사용하기 위해 @Login 어노테이션을 구현하였습니다.
   - Spring Security를 이용해 JwtFilter를 등록해주고 필터에서 유효한 토큰을 풀어서 SecurityContext를 등록해줍니다.
   - 등록된 SecurityContext를 이용해 LoginResolver에서 Users테이블을 조회한 후 해당 Entity를 리턴해줍니다.
   - @Login이 사용된 api에서 Users Entity를 확인할 수 있습니다.
