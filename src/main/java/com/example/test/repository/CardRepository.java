package com.example.test.repository;

import com.example.test.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface CardRepository extends JpaRepository<Card, String> {
    public List<Card> findByCno(String bno);

    @Modifying
    @Transactional
    @Query(value = "delete from card where cno=?1 ;", nativeQuery = true)
    Integer deleteCardByCno(String cno);

}
