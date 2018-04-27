package com.letu.share.dao;

import com.letu.share.model.Posting;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostingDAO {
    String TABLE_NAME = "posting_info";
    String INSERT_FIELDS = "title, content, user_id, created_date, comment_count";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{title}, #{content}, #{userId}, #{createdDate}, #{count})"})
    int addPosting(Posting posting);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where user_id=#{userId}"})
    List<Posting> selectByUserId(int userId);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where id=#{Id}"})
    Posting selectById(int Id);

    @Update({"update", TABLE_NAME, "set comment_count=#{commentCount} where id=#{id}"})
    int updateCommentCount(@Param("commentCount") int commentCount,
                           @Param("id") int id);
}
