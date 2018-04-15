package com.letu.share;

import com.letu.share.dao.UserDAO;
import com.letu.share.model.User;
import com.letu.share.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("/init-schema.sql")
public class InitDatabaseTests {

    @Autowired
    UserDAO userDAO;

    @Autowired
    UserService userService;

    @Test
    public void initDatabase() {
        Random random = new Random();

        for (int i = 0; i< 2; ++i) {
            User user = new User();
            user.setHeadURL(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String .format("User{%d}", i));
            user.setGender("female");
            user.setGrade(i);
            user.setPassword("");
            user.setSalt("");
            user.setPhone(String.format("1882921434%d", i));
            userDAO.addUser(user);
        }

        String username = "User{0}";
        String username2 = "Jack";
        User user = userDAO.selectByName(username);
        System.out.println(user.getId());
        user = userDAO.selectByName(username2);
        System.out.println(user==null);

//        Map<String, String> map = userService.register("User{2}", "123", "13123238990", "male", 1);
//        System.out.println(map.get("msg"));


    }

}
