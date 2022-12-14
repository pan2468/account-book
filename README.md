## π AccountBook

### 1. νλ‘μ νΈ λͺ©μ  
+ κ³ κ°μ λ³ΈμΈμ μλΉλ΄μ­μ κΈ°λ‘/κ΄λ¦¬νκΈ°
+ νμ(Member)μ κ°κ³λΆ(AccountBook) λ§€ννμ¬ RESTAPI κ΅¬ννκΈ°

### 2. μ μκΈ°κ° / μ°Έμ¬μΈμ
+ μ μκΈ°κ°: 2022-10-04 ~ 2022-10-11
+ μ°Έμ¬μΈμ: κ°μΈ νλ‘μ νΈ

### 3. μ¬μ© κΈ°μ (κΈ°μ μ€ν)
#### Back-End
+ Java 8
+ SpringBoot 2.6.12
+ Gradle
+ H2 Database
+ MySQL
+ TDD

### 4. IDE κ°λ°νκ²½
+ <a href="https://www.jetbrains.com/ko-kr/idea/">InteliJ IDEA </a>
+ <a href="https://www.postman.com/">Postman</a>

### 5. MSA μν€νμ²
> MSA(Micro Service Architecture)μ΄λ νλμ μ νλ¦¬μΌμ΄μμ λ΄μμ Έ μλ μλ²λ₯Ό μμ μ»΄ν¬λνΈ λ³λ‘ μͺΌκ°μ΄ κ΄λ¦¬νλ μν€νμ²μλλ€. λ§μ μ¬μ©μλ€μ΄ νλμ μλ²μ λμ©λ νΈλν½ λ°μμΌλ‘ μ₯μ κ° μκΈ°κΈ° λλ¬Έμ μ΄λ₯Ό λμ²νκΈ° μν΄μ λ‘λλ°Έλ°μ±μΌλ‘ μλ²λ₯Ό λΆμ°νμ¬ κ΄λ¦¬ν©λλ€. 

<img src="https://user-images.githubusercontent.com/58936137/194913580-9158fc4b-7aa1-4cc8-b814-92e00f8e266f.png" width="600px" height="350px">

### 6. νΈλ¬λΈμν κ²½ν

<details>
<summary>HelloControllerTest μ€νμ€λ₯</summary>
<div markdown="1">

-java.lang.AssertionError: Response content 
 Expected :hello
 Actual   :Hello World
 
- ν΄κ²° μμΈ: HelloController λ©μλμ HelloControllerTest λ©μλμ κ°μ΄ μΌμΉνμ§ μμ λ°μ
 
 #### κΈ°μ‘΄ μ½λ 
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
 
 #### κ°μ  μ½λ
 ~~~
 HelloController.class
    
 @GetMapping("/hello")
 public String hello(){

   return "hello"; // λ³κ²½
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
<summary>ν°μΊ£ μ€ν μ€λ₯</summary>
<div markdown="1">

- Caused by: org.springframework.boot.web.server.WebServerException: Unable to start embedded Tomcat
- org.springframework.context.ApplicationContextException: Unable to start web server; nested exception is org.springframework.boot.web.server.WebServerException: Unable to start embedded Tomcat
 
- ν΄κ²° μμΈ: runtimeOnly 'mysql:mysql-connector-java' μμ΄ λ°μ
 
 #### build.gradle
 ~~~
 runtimeOnly 'mysql:mysql-connector-java' //μΆκ°νμ¬ ν΄κ²°
 ~~~
</div>
</details> 

<details>
<summary>H2 μ€νμ€λ₯</summary>
<div markdown="1">

- Error starting ApplicationContext. To display the conditions report re-run your application with 'debug' enabled.
- ν΄κ²° μμΈ: application.properties MySQL μ€μ  μνμ¬ μ€λ₯ λ°μ

#### μ€νμ€λ₯ κ°μ  
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
<summary>REST API λ±λ‘ μ€λ₯</summary>
<div markdown="1">

- "error": "Unsupported Media Type"
- ν΄κ²°μμΈ: κΈ°μ‘΄μ controller μλ²μμ @RestController μ μΈνμκΈ° λλ¬Έμ @Responseody return λ°νμΌλ‘ μ€λ₯ λ°μ

#### κΈ°μ‘΄ μ½λ
~~~
    @PostMapping(value = "/account/add")
    public AccountBook saveAccount(@ResponseBody AccountBook accountBook) { // @ResponseBody μ΄λΈνμ΄μ μ€λ₯  
        log.info("----- λ±λ‘ νκΈ°μ  -------");
        return accountBookService.saveAccount(accountBook);
    }
~~~
+ @RestController μ΄λΈνμ΄μ μ μΈνμκΈ° λλ¬Έμ @ResponseBody μ μΈ μ€λ₯κ° λ°μν©λλ€.

#### κ°μ  μ½λ
~~~
    @PostMapping(value = "/account/add")
    public AccountBook saveAccount(@ModelAttribute AccountBook accountBook) { // @ModelAttribute λ³κ²½
        log.info("----- λ±λ‘ νκΈ°μ  -------");
        return accountBookService.saveAccount(accountBook);
    }
~~~
+ @ModelAttribute μ΄λΈνμ΄μ μ μΈνμ¬ μ¬μ©μκ° μμ²­κ°μ μλ² λ§€κ°λ³μμ λ³΄λ΄μ νλΌλ―Έν°λ‘ λ°μ΅λλ€.

</div>
</details>

<details>
<summary>TDD μ‘°ν μ€λ₯</summary>
<div markdown="1">
 
- error: unreported exception java.lang.Exception; must be caught or declared to be thrown
- ν΄κ²° μμΈ: createAccountBook λ©μλμ throws Exception μμΈ μ²λ¦¬νμκΈ° λλ¬Έμ μ€λ₯λ°μ;
 
 #### κΈ°μ‘΄ μ½λ
 ~~~
    @Test
    @DisplayName("κ°κ³λΆ νμ€νΈ λ±λ‘")
    public void createAccountBook()throws Exception{ //μμΈ μ²λ¦¬ μ μΈνμ¬ λ°μ
        int money = 15000;
        String memo = "νμ€νΈ λ±λ‘";

        AccountBook book = new AccountBook();
        book.setMoney(15000);
        book.setMemo("νμ€νΈ λ±λ‘");
        accountBookRepository.save(book);

        assertThat(book.getMoney()).isEqualTo(money);
        assertThat(book.getMemo()).isEqualTo(memo);
    }
 ~~~

 
 #### κ°μ  μ½λ
 ~~~
    @Test
    @DisplayName("κ°κ³λΆ νμ€νΈ λ±λ‘")
    public void createAccountBook(){ //μμΈ μ²λ¦¬ μ§μ κ°μ 
        int money = 15000;
        String memo = "νμ€νΈ λ±λ‘";

        AccountBook book = new AccountBook();
        book.setMoney(15000);
        book.setMemo("νμ€νΈ λ±λ‘");
        accountBookRepository.save(book);

        assertThat(book.getMoney()).isEqualTo(money);
        assertThat(book.getMemo()).isEqualTo(memo);
    }
 ~~~
 
 <br>

 π‘accountBookListTest λ©μλ νμ€νΈ μ€ν ν μ€λ₯μμ΄ μ ν΄κ²°λμ΄ μ‘°νκ°μ΄ μ λμ¬ μ μμμ΅λλ€. 
 <br/><br/>
 <img src="https://user-images.githubusercontent.com/58936137/194743031-963f1fa1-0f87-42c7-b5be-03d244e21f3b.png" width="300px" height="100px">
 
</div>
</details> 
 
<details>
<summary>TDD μ­μ  μ€λ₯</summary>
<div markdown="1">

- org.springframework.beans.factory.UnsatisfiedDependencyException:
- ν΄κ²°μμΈ: @Autowired private MockMvc mockMvc; μ¬μ©νμ§ μκ³  μ μΈνμκΈ° λλ¬Έμ μ€λ₯λ°μ 

 
<img src="https://user-images.githubusercontent.com/58936137/194743599-6af4af1a-308a-4b79-a626-0bf89532cf6a.png" width="600px" height="150px"> 
<br><br>
π‘ @Autowired private MockMvc mockMvc; μ½λ μ£Όμ ν νμ€νΈ μ€ννμ¬ μ­μ κ° μ μ²λ¦¬λ  μ μμμ΅λλ€. 
 
</div>
</details>

<details>
<summary>κ°κ³λΆ μ‘°ν μ€λ₯</summary>
<div markdown="1">
 
- java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
- ν΄κ²° μμΈ: λ©μλ μ μΈλΆμ List μ»¬λ μμ μ μΈνμ§ μμ μ€λ₯ λ°μ 

#### κΈ°μ‘΄ μ½λ
##### AccountBookController.class
~~~
// κ°κ³λΆ μ‘°ν
@GetMapping(value = "/account/list")
public AccountBook AccountBookList(AccountBook accountBook){

   List<AccountBook> list = accountBookService.listAccount(accountBook);

   return list.get(0);
}
~~~

#### κ°μ  μ½λ
##### AccountBookController.class
~~~
    // κ°κ³λΆ μ‘°ν
    @GetMapping(value = "/account/list")
    public List<AccountBook> AccountBookList(@ModelAttribute AccountBook accountBook){ // List μ»¬λ μ νλ μμν¬μ @ModelAttribute μ΄λΈνμ΄μ μ μΈ ν κ°μ 

        List<AccountBook> list = accountBookService.listAccount(accountBook);

        return list;
    }
 ~~~
 + κ°κ³λΆμ List λͺ©λ‘ μ λ³΄λ₯Ό μ‘°ν νκΈ°μν΄μ λ©μλ μ μΈλΆμ List μ»¬λ μμΌλ‘ λ³κ²½ ν κ°μ μ νμμ΅λλ€.
 
 <img src="https://user-images.githubusercontent.com/58936137/194746464-bad5d555-367a-42bf-922d-83d9f00ee05f.png" height="250px">
 

</div>
</details>


### π‘ κΈ°μ μ  issue ν΄κ²° κ³Όμ 
<details>
<summary>HelloController νμ€νΈ μ€ν</summary>
<div markdown="1">

 #### 1. controller ν¨ν€μ§ μμ±νκΈ° <br>
 controller ν¨ν€μ§ μμ± > HelloController.class μμ±<br><br>
 <img src="https://user-images.githubusercontent.com/58936137/194320720-e025ded6-cdc2-46e4-8695-1dc4e750cd31.png" width="200px" height="50px">
 <br>
 
 #### 2. Create Test<br>
 Ctrl + Shift + T > Create New Test ν΄λ¦­ > Create Test μ€μ  ν OKλ²νΌ ν΄λ¦­ <br><br>
 <img src="https://user-images.githubusercontent.com/58936137/194322496-9fadcf62-01c9-4660-a35b-367ef7d6e6cf.png" width="350px" height="300px"> 
 <br>
 <img src="https://user-images.githubusercontent.com/58936137/194325201-df1e9e6c-b893-40ec-8a15-722e3994c7dc.png" width="300px" height="100px">
 
 #### 3. Test μ½λ μμ±νκΈ°
 
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
+ @RunWith(SpringRunner.class) μ μΈ ν νμ€νΈ μ½λ μ»΄νμΌνμ¬ μ€νν©λλ€.
+ @WebMvcTest μ΄λΈνμ΄μ μ μΈ ν μΉ MVC κΈ°λ°μΌλ‘ νμ€νΈ μ½λ μ€νν©λλ€.
+ @Autowired μΈλΆ κ°μ²΄ Bean μ°Ώμ μμ‘΄μ± μ£Όμν©λλ€.  
+ @Test μ§μ ν λ©μλ νμ€νΈ μ€νν©λλ€. 

 <br>
 <img src="https://user-images.githubusercontent.com/58936137/194327470-35ce7e59-3d03-40df-839f-c333a52b6cb2.png" width="900px" height="150px">
 
 </div>
</details>

<details>
<summary>HelloController.class μ€ν</summary>
<div markdown="1">

 #### 1. HelloController μ½λ μμ±
 
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
 + @RestController μ΄λΈνμ΄μ μ μΈ ν λͺ¨λ  λ©μλ JSON κΈ°λ₯μ μ£Όμ΄ return λ°νν©λλ€.
 + @GetMapping() μ§μ ν μ£Όμ μ°Ώμ μ κ·Όν©λλ€.

 ##### 2. ν¬λ‘¬ View νλ©΄
 
 <img src="https://user-images.githubusercontent.com/58936137/194329238-7691d770-a70c-4542-a84c-0b7edc18d00d.png" width="300px" height="300px">
 
</div>
</details>

<details>
<summary>λλ©μΈ λͺ¨λΈ μ€κ³</summary>
<div markdown="1">

#### 1. Member, AccountBook Entity μμ±νκΈ°

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
+ Lombok λΌμ΄λΈλ¬λ¦¬ ν΅ν΄μ @Getter, @Setter μ΄μ©ν©λλ€.
+ @Entity μ΄λΈνμ΄μ μ μΈνμ¬ λλ©μΈ λͺ¨λΈ μμ±ν©λλ€.
+ @Table(name="member") νμ΄λΈ μ΄λ¦ μ§μ ν©λλ€. 
+ μ»¬λΌκ°μ id, email, password μ€μ ν©λλ€.

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
+ Lombok λΌμ΄λΈλ¬λ¦¬ ν΅ν΄μ @Getter, @Setter μ΄μ©ν©λλ€.
+ @Entity μ΄λΈνμ΄μ μ μΈνμ¬ λλ©μΈ λͺ¨λΈ μμ±ν©λλ€.
+ @Table(name="member") νμ΄λΈ μ΄λ¦ μ§μ ν©λλ€. 
+ μ»¬λΌκ°μ id, money, memo μ€μ ν©λλ€.

##### application.properties

~~~
spring.jpa.hibernate.ddl-auto=create // μΆκ°
~~~
+ application.properties μ€μ νμ¬ Run μ€ν > console.logμμ Entity Table μμ±λλ κ²μ νμΈν  μ μμ΅λλ€.
<br>

<img src="https://user-images.githubusercontent.com/58936137/194365720-63465b82-14cf-41f2-8eb3-8e17c6ce442b.png" width="400px" height="500px">

</div>
</details>

<details>
<summary>κ°κ³λΆ TDD</summary>
<div markdown="1">


 #### 2. νμ€νΈ μ½λ μμ±
 
 Ctrl + Shift + T > CreateTest μ€μ  ν OKλ²νΌ ν΄λ¦­
 
 <img src="https://user-images.githubusercontent.com/58936137/194695632-dfd2bc82-c28b-4dd7-9397-d7533a3ef27a.png" width="300px" height="100px">
 
 #### νμ€νΈ λ±λ‘ κ΅¬ννκΈ°
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
    
    // λ±λ‘
    @Test
    @DisplayName("κ°κ³λΆ νμ€νΈ λ±λ‘")
    public void createAccountBook(){
        AccountBook book = new AccountBook();
        book.setMoney(15000);
        book.setMemo("νμ€νΈ λ±λ‘");
        accountBookRepository.save(book);
    }
 }
 ~~~
 + @SpringBooTest ν΅ν©νμ€νΈ μ€μ νμ¬ μ€νν©λλ€.
 + @TestPropertySource μΈλΆ νκ²½μ€μ  μ λ³΄λ₯Ό κ°μ§κ³  μ΅λλ€.
 + @Autowired μ΄λΈνμ΄μ ν΅ν΄μ AccountBookRepository μμ‘΄μ± μ£Όμμ ν©λλ€.
 + @Test μ€ννμ¬ JpaRepository save()λ©μλλ‘ Entity κ°μ μ μ₯ν©λλ€.
 
 <br>
 
 <img src="https://user-images.githubusercontent.com/58936137/194696260-0b817ef6-ae19-4f06-83eb-63cfb6f618e0.png" height="150px">
 
 #### νμ€νΈ μ‘°ν κ΅¬ννκΈ°
 ##### AccountBookServiceTest.class
 ~~~
    // μ‘°ν
    @Test
    @DisplayName("κ°κ³λΆ νμ€νΈ μ‘°ν")
    public void accountBookListTest(){
        int money = 15000;
        String memo = "νμ€νΈ λ±λ‘";

        this.createAccountBook();
        List<AccountBook> accountBooks = accountBookRepository.findAll();
        AccountBook list = accountBooks.get(0);

        assertThat(list.getMoney()).isEqualTo(money);
        assertThat(list.getMemo()).isEqualTo(memo);
    }
 ~~~
 + @Test μ΄λΈνμ΄μ μ μΈνμ¬ λ©μλ μ§μ νμ¬ νμ€νΈ μ€νν©λλ€.
 + μ§μ­ λ³μ int money, String memo μ‘°ν κ°μ΄λ λμΌνμ§ νμΈνκΈ°μν΄μ μ μΈνμμ΅λλ€.
 + JpaRepository findAll() λ©μλλ₯Ό μ΄μ©νμ¬ μ‘°ν κ° μΆλ ₯ν©λλ€.
 
 <br>
 
 <img src="https://user-images.githubusercontent.com/58936137/194744324-06ab4be1-4f5f-4e6c-b9f8-f958f8e50913.png"  height="150px">
 
 #### νμ€νΈ μμΈμ‘°ν κ΅¬ννκΈ°
 ##### AccountBookServiceTest.class
 ~~~
     //μμΈ μ‘°ν
    @Test
    @DisplayName("κ°κ³λΆ μμΈ μ‘°ν")
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
 + @Test μ΄λΈνμ΄μ μ μΈνμ¬ λ©μλ μ§μ νμ¬ νμ€νΈ μ€νν©λλ€.
 + JpaRepository findAll() λ©μλ ν΅ν΄μ μ‘°ν κ° μ μ²΄ μΆλ ₯ν©λλ€.
 + findAll() μ‘°ν μΆλ ₯ κ°μμ id κ°μ findById() λ©μλμ λ£μ΄ μμΈ μ‘°ν μΆλ ₯ν©λλ€.
 
 <img src="https://user-images.githubusercontent.com/58936137/194744428-ed3ca83f-9c82-4cd9-861b-9b5635eb4ba6.png" height="150px">
 
 #### νμ€νΈ μμ  κ΅¬ννκΈ°
 ##### AccountBookServiceTest.class
 ~~~
    @Test
    @DisplayName("κ°κ³λΆ νμ€νΈ μμ ")
    public void AccountBookUpdateTest(){
        int money = 20000;
        String memo = "νμ€νΈ μμ ";

        this.createAccountBook();
        List<AccountBook> bookList = accountBookRepository.findAll();

        AccountBook accountBook = bookList.get(0);
        accountBook.setMoney(20000);
        accountBook.setMemo("νμ€νΈ μμ ");

        accountBookRepository.save(accountBook);

        assertThat(accountBook.getMoney()).isEqualTo(money);
        assertThat(accountBook.getMemo()).isEqualTo(memo);

    }
 ~~~
 + μ§μ­λ³μ int money, String memo μμ  κ°μ΄λ λμΌνμ§ νμΈνκΈ° μν΄μ μ μΈν©λλ€. 
 + JpaRepository findAll() λ©μλ ν΅ν΄μ μ‘°ν κ° μΆλ ₯ν©λλ€.
 + μ‘°ν κ°μμ Setter ν΅ν΄μ μμ ν©λλ€.
 + Entity λλ©μΈ κ°μ save() λ©μλ ν΅ν΄μ μ μ₯ν©λλ€.
 
 <img src="https://user-images.githubusercontent.com/58936137/194744587-f372e40e-c553-4816-aef7-84cd9f763ab1.png" width="800px" height="200px">
 
 #### νμ€νΈ μ­μ  κ΅¬ννκΈ°
 ##### AccountBookServiceTest.class
 ~~~
    @Test
    @DisplayName("κ°κ³λΆ νμ€νΈ μ­μ ")
    public void AccountBookDeleteTest(){
        Long num = 1L;
        this.createAccountBook();
        accountBookRepository.deleteById(num);

        List<AccountBook> accountBooks = accountBookRepository.findAll();

        System.out.println(accountBooks.toString());
    }
 ~~~
 + Long num = 1L; λ³μ μ μΈν©λλ€.
 + JpaRepository deleteById() λ©μλμ id κ°μ λ£μ΄ κ°κ³λΆ μ‘°ννμ¬ μ­μ ν©λλ€.
 + findAll() λ©μλ μ‘°ν κ°μ μΆλ ₯νμ¬ null μΈμ§ νμΈν©λλ€.
 
 <img src="https://user-images.githubusercontent.com/58936137/194744818-2f8a24a9-f448-44de-89c2-6718767ef86b.png" height="150px">
 
 </div>
</details>


<details>
<summary>κ°κ³λΆ λ±λ‘</summary>
<div markdown="1">

 #### 1. Controller, Service, Repository μ½λμμ±

 
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

    // κ°κ³λΆ λ±λ‘
    @PostMapping(value = "/account/add")
    public AccountBook saveAccount(@ModelAttribute AccountBook accountBook) {
        // log.info("----- λ±λ‘ νκΈ°μ  -------");
        return accountBookService.saveAccount(accountBook);
    }
}
 ~~~
 + @RestController μ΄λΈνμ΄μ μ μΈνμ¬ λͺ¨λ λ©μλμκ² json κΈ°λ₯μ μ£Όμ΄ return λ°νν©λλ€.
 + @Log4j2 μ€λ₯λ₯Ό μ½κ² μ°ΎκΈ° μν΄μ log κΈ°λ‘μ λ¨κΉλλ€.
 + @Autowired AccountBookService μμ‘΄μ±μ μ£Όμμ ν©λλ€.
 + @PostMappingμΌλ‘ μλ² λ§€κ°λ³μμ κ°μ λ³΄λ΄μ΄ Insert ν©λλ€.
 
  
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
 + @Service λΉμ¦λμ€ λ‘μ§μ νμ¬ μ€κ°λ€λ¦¬ μ­ν μ λ΄λΉν©λλ€.
 + @Transactional λͺ¨λ  λ©μλμκ² commitκ³Ό Rollback κΈ°λ₯μ μ£Όμ΄ μ μΈν©λλ€.
 + @Autowired AccountBookRepository μμ‘΄μ± μ£Όμμ ν©λλ€.
 + saveAccount λ©μλλ λ§€κ°λ³μμ κ°μ λ°μ Insert μ½μμ μνν©λλ€.
 
 ##### AccountBookRepository.class
 ~~~
 package com.springboot.repository;

import com.springboot.entity.AccountBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {

}
 ~~~
 + JpaRepositoy μμμ λ°μ CRUD λ©μλλ₯Ό μ¬μ μ λ°μ΅λλ€.
 
 #### 2. Postman μ€ν
 
 <img src="https://user-images.githubusercontent.com/58936137/194908699-02b9732f-eb38-42e5-9dd6-2a49068ee89e.png" width="" height="100px">
 
 + URL μΏΌλ¦¬μ€νΈλ§μ κ°μ λ£μ΄ POSTνμμΌλ‘ λ³΄λλλ€.
 <br>
 <img src="https://user-images.githubusercontent.com/58936137/194908961-ccc6dfc3-3d39-4432-88cc-5e5bb2c4a843.png" width="700px" height="300px">
 
 + Body > Pretty μμ λ±λ‘μ΄ μλλ κ²μ νμΈν  μ μμ΅λλ€.
 
 
</div>
</details>

<details>
<summary>κ°κ³λΆ μ‘°ν</summary>
<div markdown="1">

 #### μ‘°ν μΆλ ₯νκΈ°
 
 ##### AccountBookController.class
 ~~~
     // κ°κ³λΆ μ‘°ν
    @GetMapping(value = "/list")
    public List<AccountBook> AccountBookList(){
        AccountBook accountBook = new AccountBook();   
    
        List<AccountBook> list = accountBookService.listAccount();

        return list;
    }

 ~~~
 + @GetMapping() URL μμμ ν΅ν΄ λͺ©λ‘ μ‘°ν κ°μ μΆλ ₯ν©λλ€. 
 
 ##### AccountBookService.class
 ~~~
     public List<AccountBook> listAccount(){
        List<AccountBook> accountBooks  = accountBookRepository.findAll();

        return accountBooks;
    }
 ~~~
 + JpaRepository findAll()λ©μλ μ΄μ©ν΄μ μ‘°ν κ°μ μΆλ ₯ν©λλ€.
 
<img src="https://user-images.githubusercontent.com/58936137/194908961-ccc6dfc3-3d39-4432-88cc-5e5bb2c4a843.png" width="700px" height="300px"><br>
+ μ μ²΄ μΆλ ₯μ΄ μ λλ κ²μ νμΈν  μ μμ΅λλ€.
 

</div>
</details>
 
<details>
<summary>κ°κ³λΆ μμΈ μ‘°ν</summary>
<div markdown="1">

 #### μμΈ μ‘°ν μΆλ ₯νκΈ°
 
 ##### AccountBookController.class
 ~~~
      // κ°κ³λΆ μμΈμ‘°ν
    @GetMapping(value = "/detail/{id}")
    public Optional<AccountBook> AccountBookDetail(@PathVariable("id") Long id){

        Optional<AccountBook> detail = accountBookService.detail(id);

        return detail;
    }
 ~~~
 + @PathVariable() μ΄λΈνμ΄μ μ μΈνμ¬ μμ²­ κ°μ λ°μ λ§€κ° λ³μ μ μ₯ν©λλ€.
 + AccountBookService.class μμΈ μ‘°νλ₯Ό νκΈ° μν΄μ  detail() λ©μλμ id κ°μ λ³΄λλλ€.
 
 ##### AccountBookService.class
 ~~~
     public Optional<AccountBook> detail(Long id) {
        Optional<AccountBook> detail = accountBookRepository.findById(id);

        return detail;
    }
 ~~~
 + id κ°μ λ°μ findById()λ©μλμ λ£μ΄ μ‘°νλ₯Ό μΆλ ₯ν©λλ€.
 
 <img src="https://user-images.githubusercontent.com/58936137/194912451-dedbcb7a-0622-4796-ba94-8a74f8421e06.png" width="" height="100px"><br>
 + URL μμμ ν΅ν΄μ id κ°μ μμ²­ν©λλ€.
 
 <br>
 
 <img src="https://user-images.githubusercontent.com/58936137/194912570-d6d22a24-c80b-4321-8745-54f991168aee.png" width="700px" height="200px"><br>
 + id κ°μ λ°μ μ‘°νκ° μ μΆλ ₯λλ κ²μ νμΈνμμ΅λλ€.

</div>
</details>

<details>
<summary>κ°κ³λΆ μμ νκΈ°</summary>
<div markdown="1">


 #### μμ νμ¬ μΆλ ₯νκΈ°
 
##### AccountBookController.class
~~~
    // κ°κ³λΆ μμ νκΈ°
    @PutMapping(value = "/update")
    public AccountBook AccountBookUpdate(@ModelAttribute AccountBook accountBook){

        AccountBook update = accountBookService.update(accountBook);

        return update;
    }
~~~  
+ @ModelAttribute μ΄λΈνμ΄μ μ μΈνμ¬ μμ ν  κ°μ λ°μ λ§€κ°λ³μμ μ μ₯ν©λλ€.
+ λ§€κ°λ³μ κ°μ AccountBookService ν΄λμ€ update λ©μλμ λ³΄λλλ€.

##### AccountBookService.class
~~~
   public AccountBook update(AccountBook accountBook) {

        AccountBook update = accountBookRepository.save(accountBook);

        return update;
    }
~~~
+ λ§€κ°λ³μ κ°μ λ°μ save() λ©μλμ μμ ν  κ°μ λ£μ΄ μ μ₯ν©λλ€.

 <img src="https://user-images.githubusercontent.com/58936137/194915250-cc251b43-2345-495f-b098-72a4353e025e.png" width="" height="100px"><br>
 + URL μμμ ν΅ν΄μ μμ ν  κ°μ μμ²­ν©λλ€.
 
 <br>
 
 <img src="https://user-images.githubusercontent.com/58936137/194915368-94af6aa5-1fc8-4378-8b04-04c50ae2e8a0.png" width="700px" height="200px"><br>
 + λͺ©λ‘μ νμΈνλ©΄ μ μμ λλ κ²μ νμΈνμμ΅λλ€.


</div>
</details>

<details>
<summary>κ°κ³λΆ μ­μ νκΈ°</summary>
<div markdown="1">


 #### μ­μ νμ¬ μΆλ ₯νκΈ°
 
##### AccountBookController.class
~~~
     // κ°κ³λΆ μ­μ νκΈ°
    @DeleteMapping(value = "/delete/{id}")
    public Optional<AccountBook> AccountBookDelete(@PathVariable("id") Long id){

        Optional<AccountBook> delete = accountBookService.delete(id);

        return delete;
    }
~~~  
 + @PathVariable() μ΄λΈνμ΄μ μ μΈνμ¬ μμ²­ κ°μ λ°μ λ§€κ° λ³μ μ μ₯ν©λλ€.
 + AccountBookService.class delete() λ©μλμ id κ°μ λ³΄λλλ€.
##### AccountBookService.class
~~~
     public Optional<AccountBook> delete(Long id){

        Optional<AccountBook> delete = accountBookRepository.deleteAllById(id);

        return delete;
    }
~~~
+ id κ°μ λ°μ deleteAllById() λ©μλ λ£μ΄ μ­μ  μ²λ¦¬λ₯Ό μ§νν©λλ€.

<img src="https://user-images.githubusercontent.com/58936137/194916892-19020a75-0d3b-4f41-bc7d-2818dd04a044.png" width="" height="100px"><br>
+ URL μμμ ν΅ν΄μ id κ°μ μμ²­νμ¬ μ­μ  μ²λ¦¬λ₯Ό ν©λλ€.
 
<br>
 
<img src="https://user-images.githubusercontent.com/58936137/194917185-f7b7fa7b-14d9-4aa6-bea0-144a7ff62354.png" width="700px" height="250px"><br>
+ λͺ©λ‘μ νμΈνλ©΄ μ­μ κ° μ μ²λ¦¬κ° λλ κ²μ νμΈνμμ΅λλ€. 
 

</div>
</details>

  


