package com.springboot.service;

import com.springboot.entity.AccountBook;
import com.springboot.repository.AccountBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountBookService {

    @Autowired
    AccountBookRepository accountBookRepository;

    public AccountBook saveAccount(AccountBook accountBook) {
        accountBookRepository.save(accountBook);

        return accountBook;
    }

    public List<AccountBook> listAccount(AccountBook accountBook){
        List<AccountBook> accountBooks  = accountBookRepository.findAll();

        return accountBooks;
    }

    public Optional<AccountBook> detail(Long id) {
        Optional<AccountBook> detail = accountBookRepository.findById(id);

        return detail;
    }

    public AccountBook update(AccountBook accountBook) {

        AccountBook update = accountBookRepository.save(accountBook);

        return update;
    }

    public Optional<AccountBook> delete(Long id){

        Optional<AccountBook> delete = accountBookRepository.deleteAllById(id);

        return delete;
    }
}