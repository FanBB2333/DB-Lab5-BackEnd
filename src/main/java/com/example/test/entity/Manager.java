package com.example.test.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
@Data
public class Manager {
    @Id
    private String id;
    private String passwd;
    private String name;
    private String contact;
}
