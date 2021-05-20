package com.example.test.controller;

import com.example.test.entity.Record;
import com.example.test.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/record")
public class RecordHandler {
    @Autowired
    private RecordRepository recordRepository;

    @RequestMapping("/findAll")
    public List<Record> findAll(){
        return recordRepository.findAll();
    }

    @RequestMapping("/findLatest")
    public String findLatest(@RequestParam(value = "bno", required = true) String bno){
        return recordRepository.findLatestReturn(bno);
    }

    @RequestMapping("/remove")
    public List<Record> findUnreturnedBook(@RequestParam(value = "cno", required = true) String cno){
        List<Record> books;
        books = recordRepository.findUnreturnedBook(cno);
        return books;
    }
}
