package com.example.test.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
@Data
public class Card {
    @Id
    private String cno;
    private String name;
    private String department;
    private String type;


}
