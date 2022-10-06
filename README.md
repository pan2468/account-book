## 📌 AccountBook

### 1. 프로젝트 목적 
+ 고객은 본인의 소비내역을 기록/관리하기
+ 회원(Member)와 가계부(AccountBook) 매핑하여 RESTAPI 구현하기

### 2. 제작기간 / 참여인원
+ 제작기간: 2022-10-04 ~ 진행
+ 참여인원: 개인 프로젝트

### 3. 사용 기술(기술스택)
#### Back-End
+ Java 8
+ SpringBoot 2.6.12
+ Gradle
+ H2 Database
+ TDD

### 4. IDE 개발환경
+ InteliJ IDEA

### 5. MSA 아키텍처

### 6. 트러블슈팅 경험

<details>
<summary>HelloControllerTest 실행오류</summary>
<div markdown="1">

-java.lang.AssertionError: Response content 
 Expected :hello
 Actual   :Hello World
 
 원인: HelloController 메소드와 HelloControllerTest 메소드와 값이 일치하지 않아 발생
 
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


### 7. 기술적 issue 해결 과정
<details>
<summary>HelloController 테스트 실행</summary>
<div markdown="1">

 #### 1. 패키지 생성 <br>
 controller 패키지 생성 > HelloController.class 생성<br><br>
 <img src="https://user-images.githubusercontent.com/58936137/194320720-e025ded6-cdc2-46e4-8695-1dc4e750cd31.png" width="200px" height="50px">
 <br>
 
 #### 2. Create Test<br>
 Ctrl + Shift + T > Create New Test 클릭 > Create Test 설정 후 OK버튼 클릭 <br><br>
 <img src="https://user-images.githubusercontent.com/58936137/194322496-9fadcf62-01c9-4660-a35b-367ef7d6e6cf.png" width="350px" height="300px"> 
 <br>
 <img src="https://user-images.githubusercontent.com/58936137/194325201-df1e9e6c-b893-40ec-8a15-722e3994c7dc.png" width="300px" height="100px">
 
 </div>
</details>

### 8. 화면 구성도

  


