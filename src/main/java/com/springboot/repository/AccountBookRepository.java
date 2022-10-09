package com.springboot.repository;

import com.springboot.entity.AccountBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {

    AccountBook deleteAllById(Long number);
}