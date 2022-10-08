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
