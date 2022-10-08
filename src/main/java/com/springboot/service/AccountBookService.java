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