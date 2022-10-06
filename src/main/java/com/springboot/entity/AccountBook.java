package com.springboot.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "account_book")
@Setter @Getter
public class AccountBook {

    @Id
    @Column(name = "account_book_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int money;

    private String memo;

    //private LocalDateTime bookDate;

}
