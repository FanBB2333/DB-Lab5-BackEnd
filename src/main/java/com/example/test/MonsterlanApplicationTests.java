package com.example.test;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MonsterlanApplicationTests {
    @Autowired
    private DataSource dataSource;

    @Test
    public void springDataSourceTest(){
        //输出为true
        System.out.println(dataSource instanceof HikariDataSource);
//        System.out.println(dataSource instanceof MyDataSource);
        try{
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from card");
            while (resultSet.next()){
                System.out.println(resultSet.getString("name"));
            }
            statement.close();
            connection.close();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

}