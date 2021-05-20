package com.example.test.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
@Data
public class Book {
    @Id
    private String bno;
    private String btype;
    private String bname;
    private String press;
    private String year;
    private String author;
    private double price;
    private int total;
    private int stock;
}
