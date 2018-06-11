package com.letu.share.service;

import com.letu.share.dao.LoginTicketDAO;
import com.letu.share.model.LoginTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class LoginTicketService {

    @Autowired
    LoginTicketDAO loginTicketDAO;

    // 根据票据获取用户Id
    public int getUserIdByTicket(String ticket) {
        LoginTicket loginTicket = loginTicketDAO.selectByTicket(ticket);
        System.out.println("this is ticketService");
        return loginTicket.getUserId();
    }

    ///生成票据
    public String addUserTicket(int userId) {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);

        Date now = new Date();
        now.setTime(1000 * 60 * 60 * 24 * 15 + now.getTime());
        loginTicket.setExpired(now);
        loginTicket.setStatus(0);   ///0表示票据处于有效状态，1表示票据失效
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));

        loginTicketDAO.addTicket(loginTicket);

        return loginTicket.getTicket();
    }

    ///更新票据
    public String updateUserTicket(int userId) {
        LoginTicket loginTicket = loginTicketDAO.selectByUserId(userId);
        String ticket = loginTicket.getTicket();

        Date now = new Date();
        if (now.after(loginTicket.getExpired())) {
            now.setTime(1000 * 60 * 60 * 24 * 15  + now.getTime());
            ticket = UUID.randomUUID().toString().replaceAll("-", "");
            loginTicketDAO.updateStatus(ticket, 0, now, userId);
        }
        return ticket;
    }

}
