package com.springboot.controller;


import com.springboot.entity.AccountBook;
import com.springboot.service.AccountBookService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Log4j2
@RequestMapping("/account")
public class AccountBookController {

    @Autowired
    AccountBookService accountBookService;

    // 가계부 등록
    @PostMapping(value = "/add")
    public AccountBook AccountBookSave(@ModelAttribute AccountBook accountBook) {
        // log.info("----- 등록 하기전 -------");
        return accountBookService.saveAccount(accountBook);
    }

    // 가계부 조회
    @GetMapping(value = "/list")
    public List<AccountBook> AccountBookList(@ModelAttribute AccountBook accountBook){

        List<AccountBook> list = accountBookService.listAccount(accountBook);

        return list;
    }
     // 가계부 상세조회
    @GetMapping(value = "/detail/{id}")
    public Optional<AccountBook> AccountBookDetail(@PathVariable("id") Long id){

        Optional<AccountBook> detail = accountBookService.detail(id);

        return detail;
    }

    @PutMapping(value = "/update")
    public AccountBook AccountBookUpdate(@ModelAttribute AccountBook accountBook){

        AccountBook update = accountBookService.update(accountBook);

        return update;
    }

    @DeleteMapping(value = "/delete/{id}")
    public Optional<AccountBook> AccountBookDelete(@PathVariable("id") Long id){

        Optional<AccountBook> delete = accountBookService.delete(id);

        return delete;
    }
}
