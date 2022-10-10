package com.springboot.repository;

import com.springboot.entity.AccountBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {

    Optional<AccountBook> deleteAllById(Long id);

}