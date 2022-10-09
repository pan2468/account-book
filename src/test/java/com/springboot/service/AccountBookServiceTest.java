package com.springboot.service;

import com.springboot.entity.AccountBook;
import com.springboot.repository.AccountBookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application.properties")
class AccountBookServiceTest {

    @Autowired
    AccountBookService accountBookService;

    @Autowired
    AccountBookRepository accountBookRepository;

//    @Autowired
//    private MockMvc mockMvc;

    // 등록
    @Test
    @DisplayName("가계부 테스트 등록")
    public void createAccountBook(){
        int money = 15000;
        String memo = "테스트 등록";

        AccountBook book = new AccountBook();
        book.setMoney(15000);
        book.setMemo("테스트 등록");
        accountBookRepository.save(book);

        assertThat(book.getMoney()).isEqualTo(money);
        assertThat(book.getMemo()).isEqualTo(memo);
    }

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

    @Test
    @DisplayName("가계부 테스트 삭제")
    public void AccountBookDeleteTest(){
        Long num = 1L;
        this.createAccountBook();
        accountBookRepository.deleteById(num);

        List<AccountBook> accountBooks = accountBookRepository.findAll();

        System.out.println(accountBooks.toString());
    }
}