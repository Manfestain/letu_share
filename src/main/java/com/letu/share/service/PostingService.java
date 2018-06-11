package com.letu.share.service;

import com.letu.share.dao.PostingDAO;
import com.letu.share.model.Posting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostingService {
    @Autowired
    PostingDAO postingDAO;

    // 通过userId获取帖子
    public List<Posting> getPostingByUserId(int userId) {
        return postingDAO.selectByUserId(userId);
    }

    // 通过Id获得帖子
    public Posting getPostingById(int Id) {
        return postingDAO.selectById(Id);
    }

    // 获取最新的三个帖子
    public List<Posting> getLatePosting() {
        return postingDAO.selectByLasteDate();
    }

    // 通过Id更新评论数
    public Map<String, String> updatePostingCount(int Id) {
        Map<String, String> map = new HashMap<String, String>();
        Posting posting = postingDAO.selectById(Id);
        int count = posting.getCount() + 1;
        postingDAO.updateCommentCount(count, Id);
        map.put("msg", "更新成功");
        return map;
    }


    // 添加帖子
    public Map<String, String> addPosting(int userId, String title, String content) {
        Map<String, String> map = new HashMap<String, String>();
        Posting posting = new Posting();

        // 使用过滤机制

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        posting.setTitle(title);
        posting.setContent(content);
        posting.setUserId(userId);
        posting.setCreatedDate(date);
        posting.setCount(0);
        postingDAO.addPosting(posting);

        map.put("msg", "添加成功");
        return map;
    }
}
