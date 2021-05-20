package com.example.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**

 * @author qiubo

 * @date 2019/1/20

 */

//@RestController
//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//@SpringBootApplication
@Controller
@RequestMapping(value="/api")
public class TestController {

    @Autowired
    private DataSource dataSource;

    @RequestMapping(value ="/test",method = RequestMethod.GET)
    String getTest(){
        return "test";
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

//    @RequestMapping(value ="/h",method = RequestMethod.GET)
    @ResponseBody
    @GetMapping("query")
    public Map<String, Object> map(){
        List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM book");
        return list.get(0);
    }
//    @Autowired
//    private Author entity;
//
//    @ResponseBody
//    @RequestMapping(value = "/helloworld", method = RequestMethod.GET)
//    public String hello(){//返回JSON
//        return "Hello World!";
//    }
//    @ResponseBody
//    @RequestMapping(value = "/helloworld2", method = RequestMethod.GET)
//    public Author hello2(){//返回JSON
//        return entity;
//    }
//    @RequestMapping(value = "/helloworld3", method = RequestMethod.GET)
//    public String hello3(Model model) {//返回页面
//        model.addAttribute("loginName", entity.getName());
//        model.addAttribute("loginId", "100");
//        return "helloworld";
//    }


}
