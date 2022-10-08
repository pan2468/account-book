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
 
 #### 기존코드 
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
 
 #### 개선코드
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

#### 실행오류 개선 
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
<summary>REST API 등록 오류</summary>
<div markdown="1">

- "error": "Unsupported Media Type"
- 해결원인: 기존에 controller 서버에서 @RestController 선언하였기 때문에 @Responseody return 반환으로 오류 발생

#### 기존코드
~~~
    @PostMapping(value = "/account/add")
    public AccountBook saveAccount(@ResponseBody AccountBook accountBook) { // @ResponseBody 어노테이션 오류  
        log.info("----- 등록 하기전 -------");
        return accountBookService.saveAccount(accountBook);
    }
~~~
+ @RestController 어노테이션 선언하였기 때문에 @ResponseBody 선언 오류가 발생합니다.

#### 개선코드
~~~
    @PostMapping(value = "/account/add")
    public AccountBook saveAccount(@ModelAttribute AccountBook accountBook) { // @ModelAttribute 변경
        log.info("----- 등록 하기전 -------");
        return accountBookService.saveAccount(accountBook);
    }
~~~
+ @ModelAttribute 어노테이션 선언하여 사용자가 요청값을 서버 매개변수에 보내서 파라미터로 받습니다.

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

#### 1. Repository 테스트 코드 실행 

Ctrl + Shift + T > CreateTest 설정 후 OK버튼 클릭
<br>

<img src="https://user-images.githubusercontent.com/58936137/194695015-dac18951-4d81-43d3-954a-74943da710d4.png" width="300px" height="100px">

##### AccountBookRepositoryTest.class
~~~
package com.springboot.repository;

import com.springboot.entity.AccountBook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class AccountBookRepositoryTest {

    @Autowired
    AccountBookRepository accountBookRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("가계부 등록하기")
    public void createAccountBook(){
        AccountBook book = new AccountBook();
        book.setMoney(10000);
        book.setMemo("안녕");
        accountBookRepository.save(book);
    }
}
~~~
+ @SpringBootTest 통합테스트 실행환경 하기 위해 선언합니다.
+ @TestPropertySource 외부 환경설정 정보를 가지고 옵니다.
+ @PersistenceContext 어노테이션 선언하여 엔티티에 저장할 값을 EntityManager 영속성컨텍스트 가상환경 데이터베이스에 저장합니다. 

 <br>
 
 <img src="https://user-images.githubusercontent.com/58936137/194695449-cc52578b-f4e3-42aa-8e1b-9a5fd45cac56.png" width="800px" height="150px">

 <br>
 
 #### 2. Service 테스트 코드 실행
 
 Ctrl + Shift + T > CreateTest 설정 후 OK버튼 클릭
 
 <img src="https://user-images.githubusercontent.com/58936137/194695632-dfd2bc82-c28b-4dd7-9397-d7533a3ef27a.png" width="300px" height="100px">
 
 ##### AccountBookServiceTest.class
 
 ~~~
 package com.springboot.service;

import com.springboot.entity.AccountBook;
import com.springboot.repository.AccountBookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class AccountBookServiceTest {

    @Autowired
    AccountBookService accountBookService;

    @Autowired
    AccountBookRepository accountBookRepository;

    @Test
    @DisplayName("가계부 등록 테스트")
    public void createBook(){
        AccountBook book = new AccountBook();
        book.setMoney(15000);
        book.setMemo("테스트 등록");
        accountBookRepository.save(book);
    }
 }
 ~~~
 + @SpringBooTest 통합테스트 설정하여 실행합니다.
 + @TestPropertySource 외부 환경설정 정보를 가지고 옵니다.
 + @Autowired 어노테이션 통해서 AccountBookRepository 의존성 주입을 합니다.
 + @Test 실행하여 JpaRepository save()메소드로 Entity 값을 저장합니다.
 
 <br>
 
 <img src="https://user-images.githubusercontent.com/58936137/194695906-3b2db736-2f4d-4c00-8195-bd948628c4d1.png" width="800px" height="150px">

</div>
</details>



  


