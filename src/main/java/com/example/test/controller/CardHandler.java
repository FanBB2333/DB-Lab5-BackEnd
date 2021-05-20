package com.example.test.controller;


import com.example.test.entity.Book;
import com.example.test.entity.Card;
import com.example.test.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/card")
public class CardHandler {
    @Autowired
    private CardRepository cardRepository;
    @RequestMapping("/findAll")
    public List<Card> findAll(){
        return cardRepository.findAll();

    }

    @RequestMapping("/findByCno")
    public List<Card> findByCno(@RequestParam(value = "cno", required = true) String cno){
        return cardRepository.findByCno(cno);

    }

    @PostMapping("/add")
    public String addCard(@RequestBody Card card){
        List<Card> ifExist = cardRepository.findByCno(card.getCno());
        Card result;
        if(ifExist.isEmpty()){
            result = cardRepository.save(card);
            return "添加成功!";
        }
        else{
            return "已存在同卡号借书证!";
        }
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Integer deleteCardByCno(@RequestParam(value = "cno", required = true) String cno){
        return cardRepository.deleteCardByCno(cno);

    }
}
