package com.springboot.controller;


import com.springboot.entity.AccountBook;
import com.springboot.service.AccountBookService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
public class AccountBookController {

    @Autowired
    AccountBookService accountBookService;

    // 가계부 등록
    @PostMapping(value = "/account/add")
    public AccountBook AccountBookSave(@ModelAttribute AccountBook accountBook) {
        // log.info("----- 등록 하기전 -------");
        return accountBookService.saveAccount(accountBook);
    }

    // 가계부 조회
    @GetMapping(value = "/account/list")
    public List<AccountBook> AccountBookList(@ModelAttribute AccountBook accountBook){

        List<AccountBook> list = accountBookService.listAccount(accountBook);

        return list;
    }
}
