package com.example.test.repository;

import com.example.test.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager, String> {

    @Query(value = "select * from manager where id=?1 ;", nativeQuery = true)
    public List<Manager> findPwd(String id);
}
