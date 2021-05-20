package com.example.test.controller;

import com.example.test.entity.Manager;
import com.example.test.entity.Record;
import com.example.test.repository.ManagerRepository;
import com.example.test.utils.Sha256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
public class ManagerHandler {
    @Autowired
    private ManagerRepository managerRepository;

//    @Autowired
    private Sha256 decrypt = new Sha256();


    @RequestMapping("/findAll")
    public List<Manager> findAll(){
        return managerRepository.findAll();
    }

    @PostMapping("/login")
    public Manager addCard(@RequestParam(value = "id", required = true) String id,
                          @RequestParam(value = "pwd", required = true) String pwd
    ){
        List<Manager> ifExist = managerRepository.findPwd(id);

        String decrypted;
        decrypted = Sha256.getSHA256(pwd);

        if(ifExist.isEmpty()){
            Manager man = new Manager();
            man.setId("Username not found");
            return man;
        }
        else{
            if( decrypted.equals(ifExist.get(0).getPasswd()) ){
                return ifExist.get(0);
//                return "Success!";
            }
            else{
                Manager man = new Manager();
                man.setId("Wrong Password");
                return man;
//                return "Wrong Password!";
            }
        }
    }


}
