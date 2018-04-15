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

    ///生成票据
    public String addUserTicket(int userId) {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);

        Date now = new Date();
        now.setTime(3600000 * 24 * 30 + now.getTime());
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
            now.setTime(3600000 * 24 * 30 + now.getTime());
            ticket = UUID.randomUUID().toString().replaceAll("-", "");
            loginTicketDAO.updateStatus(ticket, 0, now, userId);
        }

        return ticket;
    }

}
