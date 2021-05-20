package com.example.test.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
//@RunWith(SpringRunner.class)
class CardRepositoryTest {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private RecordRepository recordRepository;

    @Test
    void findAll(){
        System.out.println(recordRepository.findAll());
    }

}