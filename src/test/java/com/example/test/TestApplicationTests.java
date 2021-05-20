package com.example.test;

import com.example.test.entity.Book;
import com.example.test.repository.BookRepository;
import com.example.test.repository.CardRepository;
import com.example.test.repository.RecordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestApplicationTests {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RecordRepository recordRepository;

    @Test
    void contextLoads() {
        System.out.println(recordRepository.findAll());

    }

    @Test
    void save() {
        Book book = new Book();
        book.setBno("6897");
        book.setAuthor("123");
        book.setBname("aaa");
        bookRepository.save(book);
    }

}
