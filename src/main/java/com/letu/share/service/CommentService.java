package com.letu.share.service;

import com.letu.share.dao.CommentDAO;
import com.letu.share.dao.PostingDAO;
import com.letu.share.model.Comment;
import com.letu.share.model.Posting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentService {
    @Autowired
    CommentDAO commentDAO;

    @Autowired
    PostingDAO postingDAO;

    @Autowired
    PostingService postingService;

    // 通过帖子Id获得评论
    public List<Comment> getCommentByPostingId(int postingId) {
        return commentDAO.selectByRecId(postingId);
    }


    // 添加评论
    // 当flag==1时表示操作商品评论表，0表示操作帖子评论表
    public Map<String, String> addComment(int flag, int recId, int sendId, String content) {
        Map<String, String> map = new HashMap<String, String>();
        Comment comment = new Comment();

        Posting posting = postingService.getPostingById(recId);
        if (posting == null) {
            map.put("msg", "主帖不存在！");
            return map;
        }

        if (StringUtils.isEmpty(content)) {
            map.put("msg", "评论内容不能为空！");
            return map;
        }

        Date date = new Date();
        comment.setRecId(recId);
        comment.setSendId(sendId);
        comment.setContent(content);
        comment.setCreatedDate(date);
        comment.setStatus(0);   // 0表示未读，1表示已读

        if (flag == 1) {
            commentDAO.addCommodityComment(comment);
        }
        if (flag == 0){
            int commentCount = posting.getCount();
            postingDAO.updateCommentCount(commentCount + 1, posting.getId());
            commentDAO.addPostingComment(comment);
        }

        map.put("msg", "评论添加成功");
        return map;
    }



}
