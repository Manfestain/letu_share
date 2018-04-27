package com.letu.share;

import com.letu.share.model.Comment;
import com.letu.share.service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentTests {
    @Autowired
    CommentService commentService;

    @Test
    public void addPostingComment() {
        int postingId = 1;
        int sendId = 4;
        String content = "没人理";
        Map<String, String> map = commentService.addComment(0, postingId, sendId, content);
    }
}
