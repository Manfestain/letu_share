package com.letu.share.service;

import com.letu.share.Util.LetuUtil;
import com.letu.share.dao.LoginTicketDAO;
import com.letu.share.dao.UserDAO;
import com.letu.share.model.LoginTicket;
import com.letu.share.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    LoginTicketService loginTicketService;

    // 通过用户Id查询用户
    public User getUserById(int userId) {
        return userDAO.selectById(userId);
    }


    ///处理用户登陆时的验证
    public Map<String, String> login(String username, String password) {
        Map<String, String> map = new HashMap<String, String>();
        User user = userDAO.selectByName(username);

        if (StringUtils.isEmpty(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isEmpty(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }
        if (!LetuUtil.MD5(password + user.getSalt()).equals(user.getPassword())) {
            map.put("msg", "密码不正确");
            return map;
        }

        String ticket = loginTicketService.updateUserTicket(user.getId());
        map.put("ticket", ticket);

        return map;
    }

    ///处理用户注册时的验证
    public Map<String, String> register(String username, String password, String phonenumber, String gender, int grade) {
        Map<String, String> map = new HashMap<String, String>();

        User user = userDAO.selectByName(username);
        if (user != null) {
            map.put("msg", "该用户名已经被注册了！！！");
            return map;
        }

        user = null;
        user = userDAO.selectByPhone(phonenumber);
        if (user != null) {
            map.put("msg", "该手机号码已被注册，请找重新输入！！！");
            return map;
        }

        if (gender.equals("男")) {
            gender = "male";
        }
        if (gender.equals("女")) {
            gender = "female";
        }
        if (gender.equals("male") || gender.equals("female")) {
            gender = gender;
        } else {
            map.put("msg", "请重新输入性别！");
            return map;
        }

        user = new User();
        user.setName(username);
        user.setPhone(phonenumber);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        user.setPassword(LetuUtil.MD5(password + user.getSalt()));
        user.setGrade(grade);
        user.setGender(gender);
        user.setHeadURL(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));

        userDAO.addUser(user);
        System.out.println("add user done!");
        String ticket = loginTicketService.addUserTicket(user.getId());
        map.put("ticket", ticket);
        return map;

    }
}
