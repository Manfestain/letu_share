package com.letu.share.service;

import com.letu.share.dao.CommentDAO;
import com.letu.share.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CommentService {
    @Autowired
    CommentDAO commentDAO;

    // 添加评论表
    // 当flag==1时表示操作商品评论表，0表示操作帖子评论表
    public Map<String, String> addComment(int flag, int recId, int sendId, String content) {
        Map<String, String> map = new HashMap<String, String>();
        Comment comment = new Comment();

        //过滤文本信息

        Date date = new Date();
        comment.setRecId(recId);
        comment.setSendId(sendId);
        comment.setContent(content);
        comment.setCreatedDate(date);
        comment.setStatus(0);   // 0表示未读，1表示已读

        if (flag == 1) {
            commentDAO.addCommodityComment(comment);
        } else {
            commentDAO.addPostingComment(comment);
        }
        map.put("msg", "评论添加成功");
        return map;
    }
}
