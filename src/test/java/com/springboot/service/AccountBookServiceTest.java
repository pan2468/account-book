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