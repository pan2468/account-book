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
    @DisplayName("Repository 테스트 등록")
    public void createAccountBook(){
        AccountBook book = new AccountBook();
        book.setMoney(10000);
        book.setMemo("안녕");
        accountBookRepository.save(book);
    }
}