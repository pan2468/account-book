## 📌 AccountBook

### 1. 프로젝트 목적 
+ 고객은 본인의 소비내역을 기록/관리하기
+ 회원(Member)와 가계부(AccountBook) 매핑하여 RESTAPI 구현하기

### 2. 제작기간 / 참여인원
+ 제작기간: 2022-10-04 ~ 2022-10-11
+ 참여인원: 개인 프로젝트

### 3. 사용 기술(기술스택)
#### Back-End
+ Java 8
+ SpringBoot 2.6.12
+ Gradle
+ H2 Database
+ MySQL
+ TDD

### 4. IDE 개발환경
+ <a href="https://www.jetbrains.com/ko-kr/idea/">InteliJ IDEA </a>

### 5. MSA 아키텍처

### 6. 트러블슈팅 경험

<details>
<summary>HelloControllerTest 실행오류</summary>
<div markdown="1">

-java.lang.AssertionError: Response content 
 Expected :hello
 Actual   :Hello World
 
- 해결 원인: HelloController 메소드와 HelloControllerTest 메소드와 값이 일치하지 않아 발생
 
 ### 기존코드 
 ~~~
 HelloController.class
    
 @GetMapping("/hello")
 public String hello(){

   return "Hello World";
 }
 
 
 HelloControllerTest.class
 
 @Test
 public void hello() throws Exception{
 String hello = "hello";

  mvc.perform(get("/hello"))
              .andExpect(status().isOk())
              .andExpect(content().string(hello));
  }
 
 
 ~~~
 
 ### 개선코드
 ~~~
 HelloController.class
    
 @GetMapping("/hello")
 public String hello(){

   return "hello"; // 변경
 }
 
 
HelloControllerTest.class
 
 @Test
 public void hello() throws Exception{
 String hello = "hello";

  mvc.perform(get("/hello"))
              .andExpect(status().isOk())
              .andExpect(content().string(hello));
  }
 ~~~

</div>
</details> 

<details>
<summary>톰캣 실행 오류</summary>
<div markdown="1">

- Caused by: org.springframework.boot.web.server.WebServerException: Unable to start embedded Tomcat
- org.springframework.context.ApplicationContextException: Unable to start web server; nested exception is org.springframework.boot.web.server.WebServerException: Unable to start embedded Tomcat
 
- 해결 원인: runtimeOnly 'mysql:mysql-connector-java' 없어 발생
 
 #### build.gradle
 ~~~
 runtimeOnly 'mysql:mysql-connector-java' //추가하여 해결
 ~~~
</div>
</details> 

<details>
<summary>H2 실행오류</summary>
<div markdown="1">

- Error starting ApplicationContext. To display the conditions report re-run your application with 'debug' enabled.
- 해결 원인: application.properties MySQL 설정 안하여 오류 발생

### 실행오류 개선 
#### application.properties
 ~~~
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/account_book?serverTimezone=UTC

spring.datasource.username=root
spring.datasource.password=1234
 ~~~
</div>
</details> 

<details>
<summary>AccountBook 테스트 등록 실행오류</summary>
<div markdown="1"

- Caused by: java.lang.IllegalStateException: Cannot load driver class: org.h2.Driver

</div>
</details> 

### 💡 기술적 issue 해결 과정
<details>
<summary>HelloController 테스트 실행</summary>
<div markdown="1">

 #### 1. controller 패키지 생성하기 <br>
 controller 패키지 생성 > HelloController.class 생성<br><br>
 <img src="https://user-images.githubusercontent.com/58936137/194320720-e025ded6-cdc2-46e4-8695-1dc4e750cd31.png" width="200px" height="50px">
 <br>
 
 #### 2. Create Test<br>
 Ctrl + Shift + T > Create New Test 클릭 > Create Test 설정 후 OK버튼 클릭 <br><br>
 <img src="https://user-images.githubusercontent.com/58936137/194322496-9fadcf62-01c9-4660-a35b-367ef7d6e6cf.png" width="350px" height="300px"> 
 <br>
 <img src="https://user-images.githubusercontent.com/58936137/194325201-df1e9e6c-b893-40ec-8a15-722e3994c7dc.png" width="300px" height="100px">
 
 #### 3. Test 코드 작성하기
 
 ##### HelloControllerTest.class
 ~~~
 package com.springboot.accountbook.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void hello() throws Exception{
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

}
~~~
+ @RunWith(SpringRunner.class) 선언 후 테스트 코드 컴파일하여 실행합니다.
+ @WebMvcTest 어노테이션 선언 후 웹 MVC 기반으로 테스트 코드 실행합니다.
+ @Autowired 외부 객체 Bean 찿아 의존성 주입합니다.  
+ @Test 지정한 메소드 테스트 실행합니다. 

 <br>
 <img src="https://user-images.githubusercontent.com/58936137/194327470-35ce7e59-3d03-40df-839f-c333a52b6cb2.png" width="900px" height="150px">
 
 </div>
</details>

<details>
<summary>HelloController.class 실행</summary>
<div markdown="1">

 #### 1. HelloController 코드 작성
 
 ##### HelloController.class
 ~~~
 package com.springboot.accountbook.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){

        return "hello";
    }
}

 ~~~
 + @RestController 어노테이션 선언 후 모든 메소드 JSON 기능을 주어 return 반환합니다.
 + @GetMapping() 지정한 주소 찿아 접근합니다.

 ##### 2. 크롬 View 화면
 
 <img src="https://user-images.githubusercontent.com/58936137/194329238-7691d770-a70c-4542-a84c-0b7edc18d00d.png" width="300px" height="300px">
 
</div>
</details>

<details>
<summary>도메인 모델 설계</summary>
<div markdown="1">

#### 1. Member, AccountBook Entity 생성하기

##### Member.class

~~~
package com.springboot.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Setter @Getter
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    //private LocalDateTime memberData;


}
~~~
+ Lombok 라이브러리 통해서 @Getter, @Setter 이용합니다.
+ @Entity 어노테이션 선언하여 도메인 모델 생성합니다.
+ @Table(name="member") 테이블 이름 지정합니다. 
+ 컬럼값은 id, email, password 설정합니다.

##### AccountBook.class

~~~
package com.springboot.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "account")
@Setter @Getter
public class AccountBook {

    @Id
    @Column(name = "account_book_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int money;

    private String memo;

    //private LocalDateTime bookDate;

}

~~~
+ Lombok 라이브러리 통해서 @Getter, @Setter 이용합니다.
+ @Entity 어노테이션 선언하여 도메인 모델 생성합니다.
+ @Table(name="member") 테이블 이름 지정합니다. 
+ 컬럼값은 id, money, memo 설정합니다.

##### application.properties

~~~
spring.jpa.hibernate.ddl-auto=create // 추가
~~~
+ application.properties 설정하여 Run 실행 > console.log에서 Entity Table 생성되는 것을 확인할 수 있습니다.
<br>

<img src="https://user-images.githubusercontent.com/58936137/194365720-63465b82-14cf-41f2-8eb3-8e17c6ce442b.png" width="400px" height="500px">

</div>
</details>

<details>
<summary>가계부 등록하기</summary>
<div markdown="1">


</div>
</details>



  


