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

<details>
<summary>TDD 조회 오류</summary>
<div markdown="1">
 
- error: unreported exception java.lang.Exception; must be caught or declared to be thrown
- 해결 원인: createAccountBook 메소드에 throws Exception 예외 처리하였기 때문에 오류발생;
 
 #### 기존 코드
 ~~~
    @Test
    @DisplayName("가계부 테스트 등록")
    public void createAccountBook()throws Exception{ //예외 처리 선언하여 발생
        int money = 15000;
        String memo = "테스트 등록";

        AccountBook book = new AccountBook();
        book.setMoney(15000);
        book.setMemo("테스트 등록");
        accountBookRepository.save(book);

        assertThat(book.getMoney()).isEqualTo(money);
        assertThat(book.getMemo()).isEqualTo(memo);
    }
 ~~~

 
 #### 개선 코드
 ~~~
    @Test
    @DisplayName("가계부 테스트 등록")
    public void createAccountBook(){ //예외 처리 지워 개선
        int money = 15000;
        String memo = "테스트 등록";

        AccountBook book = new AccountBook();
        book.setMoney(15000);
        book.setMemo("테스트 등록");
        accountBookRepository.save(book);

        assertThat(book.getMoney()).isEqualTo(money);
        assertThat(book.getMemo()).isEqualTo(memo);
    }
 ~~~
 
 <br>

 💡accountBookListTest 메소드 테스트 실행 후 오류없이 잘 해결되어 조회값이 잘 나올 수 있었습니다. 
 <br/><br/>
 <img src="https://user-images.githubusercontent.com/58936137/194743031-963f1fa1-0f87-42c7-b5be-03d244e21f3b.png" width="300px" height="100px">
 
</div>
</details> 
 
<details>
<summary>TDD 삭제 오류</summary>
<div markdown="1">

- org.springframework.beans.factory.UnsatisfiedDependencyException:
- 해결원인: @Autowired private MockMvc mockMvc; 사용하지 않고 선언하였기 때문에 오류발생 

 
<img src="https://user-images.githubusercontent.com/58936137/194743599-6af4af1a-308a-4b79-a626-0bf89532cf6a.png" width="600px" height="150px"> 
<br><br>
💡 @Autowired private MockMvc mockMvc; 코드 주석 후 테스트 실행하여 삭제가 잘 처리될 수 있었습니다. 
 
</div>
</details>

<details>
<summary>가계부 조회 오류</summary>
<div markdown="1">
 
- java.lang.IndexOutOfBoundsException: Index: 0, Size: 0

#### 기존코드
##### AccountBookController.class
~~~
// 가계부 조회
@GetMapping(value = "/account/list")
public AccountBook AccountBookList(AccountBook accountBook){

   List<AccountBook> list = accountBookService.listAccount(accountBook);

   return list.get(0);
}
~~~

#### 개선코드
##### AccountBookController.class
~~~
    // 가계부 조회
    @GetMapping(value = "/account/list")
    public List<AccountBook> AccountBookList(@ModelAttribute AccountBook accountBook){ // List 컬렉션 프레임워크와 @ModelAttribute 어노테이션 선언 후 개선

        List<AccountBook> list = accountBookService.listAccount(accountBook);

        return list;
    }
 ~~~
 + 가계부에 List 목록 정보를 조회 하기위해서 메소드 선언부에 List 컬렉션으로 변경 후 개선을 하였습니다.
 
 <img src="https://user-images.githubusercontent.com/58936137/194746464-bad5d555-367a-42bf-922d-83d9f00ee05f.png" height="150px">
 

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
<summary>가계부 TDD</summary>
<div markdown="1">


 #### 2. 테스트 코드 작성
 
 Ctrl + Shift + T > CreateTest 설정 후 OK버튼 클릭
 
 <img src="https://user-images.githubusercontent.com/58936137/194695632-dfd2bc82-c28b-4dd7-9397-d7533a3ef27a.png" width="300px" height="100px">
 
 #### 테스트 등록 구현하기
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
    
    // 등록
    @Test
    @DisplayName("가계부 테스트 등록")
    public void createAccountBook(){
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
 
 <img src="https://user-images.githubusercontent.com/58936137/194696260-0b817ef6-ae19-4f06-83eb-63cfb6f618e0.png" height="150px">
 
 #### 테스트 조회 구현하기
 ##### AccountBookServiceTest.class
 ~~~
    // 조회
    @Test
    @DisplayName("가계부 테스트 조회")
    public void accountBookListTest(){
        int money = 15000;
        String memo = "테스트 등록";

        this.createAccountBook();
        List<AccountBook> accountBooks = accountBookRepository.findAll();
        AccountBook list = accountBooks.get(0);

        assertThat(list.getMoney()).isEqualTo(money);
        assertThat(list.getMemo()).isEqualTo(memo);
    }
 ~~~
 + @Test 어노테이션 선언하여 메소드 지정하여 테스트 실행합니다.
 + 지역 변수 int money, String memo 조회 값이랑 동일한지 확인하기위해서 선언하였습니다.
 + JpaRepository findAll() 메소드를 이용하여 조회 값 출력합니다.
 
 <br>
 
 <img src="https://user-images.githubusercontent.com/58936137/194744324-06ab4be1-4f5f-4e6c-b9f8-f958f8e50913.png"  height="150px">
 
 #### 테스트 상세조회 구현하기
 ##### AccountBookServiceTest.class
 ~~~
     //상세 조회
    @Test
    @DisplayName("가계부 상세 조회")
    public void AccountBookDetailTest(){
        this.createAccountBook();
        List<AccountBook> bookList = accountBookRepository.findAll();

        AccountBook accountBook = bookList.get(0);
        AccountBook list = accountBookRepository.findById(accountBook.getId())
                .orElseThrow(EntityNotFoundException::new);

        assertEquals(list.getId(), accountBook.getId());
        assertEquals(list.getMoney(), accountBook.getMoney());
        assertEquals(list.getMemo(), accountBook.getMemo());
    }
 ~~~
 + @Test 어노테이션 선언하여 메소드 지정하여 테스트 실행합니다.
 + JpaRepository findAll() 메소드 통해서 조회 값 전체 출력합니다.
 + findAll() 조회 출력 값에서 id 값을 findById() 메소드에 넣어 상세 조회 출력합니다.
 
 <img src="https://user-images.githubusercontent.com/58936137/194744428-ed3ca83f-9c82-4cd9-861b-9b5635eb4ba6.png" height="150px">
 
 #### 테스트 수정 구현하기
 ##### AccountBookServiceTest.class
 ~~~
    @Test
    @DisplayName("가계부 테스트 수정")
    public void AccountBookUpdateTest(){
        int money = 20000;
        String memo = "테스트 수정";

        this.createAccountBook();
        List<AccountBook> bookList = accountBookRepository.findAll();

        AccountBook accountBook = bookList.get(0);
        accountBook.setMoney(20000);
        accountBook.setMemo("테스트 수정");

        accountBookRepository.save(accountBook);

        assertThat(accountBook.getMoney()).isEqualTo(money);
        assertThat(accountBook.getMemo()).isEqualTo(memo);

    }
 ~~~
 + 지역변수 int money, String memo 수정 값이랑 동일한지 확인하기 위해서 선언합니다. 
 + JpaRepository findAll() 메소드 통해서 조회 값 출력합니다.
 + 조회 값에서 Setter 통해서 수정합니다.
 + Entity 도메인 값을 save() 메소드 통해서 저장합니다.
 
 <img src="https://user-images.githubusercontent.com/58936137/194744587-f372e40e-c553-4816-aef7-84cd9f763ab1.png" width="800px" height="200px">
 
 #### 테스트 삭제 구현하기
 ##### AccountBookServiceTest.class
 ~~~
    @Test
    @DisplayName("가계부 테스트 삭제")
    public void AccountBookDeleteTest(){
        Long num = 1L;
        this.createAccountBook();
        accountBookRepository.deleteById(num);

        List<AccountBook> accountBooks = accountBookRepository.findAll();

        System.out.println(accountBooks.toString());
    }
 ~~~
 + Long num = 1L; 변수 선언합니다.
 + JpaRepository deleteById() 메소드에 id 값을 넣어 가계부 조회하여 삭제합니다.
 + findAll() 메소드 조회 값을 출력하여 null 인지 확인합니다.
 
 <img src="https://user-images.githubusercontent.com/58936137/194744818-2f8a24a9-f448-44de-89c2-6718767ef86b.png" height="150px">
 
 </div>
</details>


<details>
<summary>가계부 등록</summary>
<div markdown="1">

 #### 1. Controller, Service, Repository 코드작성

 
 ##### AccountBookController.class
 
 ~~~
 package com.springboot.controller;


import com.springboot.entity.AccountBook;
import com.springboot.service.AccountBookService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class AccountBookController {

    @Autowired
    AccountBookService accountBookService;

    // 가계부 등록
    @PostMapping(value = "/account/add")
    public AccountBook saveAccount(@ModelAttribute AccountBook accountBook) {
        // log.info("----- 등록 하기전 -------");
        return accountBookService.saveAccount(accountBook);
    }
}
 ~~~
 + @RestController 어노테이션 선언하여 모든메소드에게 json 기능을 주어 return 반환합니다.
 + @Log4j2 오류를 쉽게 찾기 위해서 log 기록을 남깁니다.
 + @Autowired AccountBookService 의존성을 주입을 합니다.
 + @PostMapping으로 서버 매개변수에 값을 보내어 Insert 합니다.
 
  
 ##### AccountBookService.class
 ~~~
 package com.springboot.service;


import com.springboot.entity.AccountBook;
import com.springboot.repository.AccountBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountBookService {

    @Autowired
    AccountBookRepository accountBookRepository;

    public AccountBook saveAccount(AccountBook accountBook) {
        accountBookRepository.save(accountBook);

        return accountBook;
    }
}
 ~~~
 + @Service 비즈니스 로직을 하여 중간다리 역할을 담당합니다.
 + @Transactional 모든 메소드에게 commit과 Rollback 기능을 주어 선언합니다.
 + @Autowired AccountBookRepository 의존성 주입을 합니다.
 + saveAccount 메소드는 매개변수에 값을 받아 Insert 삽입을 수행합니다.
 
 ##### AccountBookRepository.class
 ~~~
 package com.springboot.repository;

import com.springboot.entity.AccountBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {

}
 ~~~
 + JpaRepositoy 상속을 받아 CRUD 메소드를 재정의 받습니다.
 
 #### 2. Postman 실행
 
 <img src="https://user-images.githubusercontent.com/58936137/194698166-8215146c-9e46-45b5-8414-ae8ae18a9be6.png" width="600px" height="300px">
 
 + URL 쿼리스트링에 값을 넣어 POST형식으로 보냅니다.
 
 <img src="https://user-images.githubusercontent.com/58936137/194698305-05c64232-9149-43e3-bfb2-cf13bc2053a8.png" width="600px" height="300px">
 
 + Body > Pretty 에서 등록이 잘되는 것을 확인할 수 있습니다.
 
 
</div>
</details>

<details>
<summary>가계부 조회</summary>
<div markdown="1">



</div>
</details>
  


