package com.example.test.repository;

import com.example.test.entity.Book;
import com.example.test.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;


public interface RecordRepository extends JpaRepository<Record, String> {

    public List<Record> findByCnoAndBno(String cno, String bno);

    @Query(value = "select * from record where cno=?1 and return_date is null ;", nativeQuery = true)
    public List<Record> findUnreturnedBook(String cno);


    @Query(value = "select MAX(return_date) from record where bno=?1 and return_date is not null ;", nativeQuery = true)
    public String findLatestReturn(String bno);

}