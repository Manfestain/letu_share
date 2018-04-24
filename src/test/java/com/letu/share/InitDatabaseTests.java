package com.letu.share;

import com.letu.share.dao.CommdityDAO;
import com.letu.share.dao.UserDAO;
import com.letu.share.model.User;
import com.letu.share.service.CommodityService;
import com.letu.share.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import sun.nio.ch.IOUtil;

import java.io.FileInputStream;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("/init-schema.sql")
public class InitDatabaseTests {

    @Autowired
    UserDAO userDAO;

    @Autowired
    UserService userService;

    @Autowired
    CommdityDAO commdityDAO;

    @Autowired
    CommodityService commodityService;

//    @Test
//    public void initDatabase() {
//        Random random = new Random();
//
//        for (int i = 0; i< 2; ++i) {
//            User user = new User();
//            user.setHeadURL(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
//            user.setName(String .format("User{%d}", i));
//            user.setGender("female");
//            user.setGrade(i);
//            user.setPassword("");
//            user.setSalt("");
//            user.setPhone(String.format("1882921434%d", i));
//            userDAO.addUser(user);
//        }
//
//        String username = "User{0}";
//        String username2 = "Jack";
//        User user = userDAO.selectByName(username);
//        System.out.println(user.getId());
//        user = userDAO.selectByName(username2);
//        System.out.println(user==null);
//
////        Map<String, String> map = userService.register("User{2}", "123", "13123238990", "male", 1);
////        System.out.println(map.get("msg"));
//
//
//    }

    @Test
    public void init_commodity() {
        String name = "肉夹馍";
        String type = "饼";
        String recommend = "是一种非常好吃的陕西美食";
        String region = "西安";
        float price = 5;
        try {
            FileInputStream inputStream = new FileInputStream("E:/roujiamo.jpg");
            Scanner scanner = new Scanner(inputStream);
            String content = scanner.useDelimiter("\\A").next();

            Map<String, String> map = commodityService.addCommodity(name, type, recommend, region, inputStream, price);
            System.out.println(map.get("key"));
            System.out.println(map.get("hash"));
        } catch (Exception e) {
            System.out.println("出错");
        }

    }

}
