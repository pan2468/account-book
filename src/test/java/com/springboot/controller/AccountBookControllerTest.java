package com.springboot.controller;

import com.springboot.entity.AccountBook;
import com.springboot.service.AccountBookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application.properties")
class AccountBookControllerTest {

    @Autowired
    AccountBookService accountBookService;

    @Test
    @DisplayName("가계부 테스트 등록")
    public void createBook(){
        AccountBook book = new AccountBook();
        book.setMoney(15000);
        book.setMemo("테스트 실행");
        accountBookService.saveAccount(book);
    }

}