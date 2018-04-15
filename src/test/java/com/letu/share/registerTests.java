package com.letu.share;

import com.letu.share.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class registerTests {

    @Autowired
    UserService userService;

    @Test
    public void initRegister() {
        String username = "Bob";
        String phonenumber = "13145679099";
        String password = "123";
        String gender = "男";
        int grade = 1;
        Map<String, String> map = userService.register(username, password, phonenumber, gender, grade);
        if (map.get("msg") == "请重新输入性别！") {
            System.out.println("YES");
        }

    }

}
