package com.example.test.entity;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Data
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer record_id;
    private String bno;
    private String cno;
    private Timestamp borrow_date;
    private Timestamp return_date;
    private String id;


}