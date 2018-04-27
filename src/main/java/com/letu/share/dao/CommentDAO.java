package com.letu.share.dao;

import com.letu.share.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentDAO {
    // 商品评论表
    String COMMODITY_COMMENT = "commodity_comment";
    String COMMODITY_FIELDS = "commodity_id, send_id, content, created_date, status";
    String COMMODITY_SELECT = "id, " + COMMODITY_FIELDS;

    @Insert({"insert into", COMMODITY_COMMENT, "(", COMMODITY_FIELDS, ")",
            "values (#{recId}, #{sendId}, #{content}, #{createdDate}, #{status})"})
    int addCommodityComment(Comment comment);

    @Select({"select", COMMODITY_SELECT, "from", COMMODITY_COMMENT, "where commodity_id=#{commodityId}"})
    List<Comment> selectByCommodityId(int commodityId);


    // 帖子评论表
    String POSTING_COMMENT = "posting_comment";
    String POSTING_FIELDS = "rec_id, send_id, content, created_date, status";
    String POSTING_SELECT = "id, " + POSTING_FIELDS;

    @Insert({"insert into", POSTING_COMMENT, "(", POSTING_FIELDS,
            ") values (#{recId}, #{sendId}, #{content}, #{createdDate}, #{status})"})
    int addPostingComment(Comment comment);

    @Select({"select", POSTING_SELECT, "from", POSTING_COMMENT, "where rec_id=#{recId}"})
    List<Comment> selectByRecId(int recId);

}
