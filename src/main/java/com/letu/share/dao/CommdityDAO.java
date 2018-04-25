package com.letu.share.dao;

import com.letu.share.model.Commodity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommdityDAO {
    String TABLE_NAME = "commodity_info";
    String INSERT_FIELDS = "name, picture, type, recommend, region, price, user_id";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{name}, #{picture}, #{type}, #{recommend}, #{region}, #{price}, #{userId})"})
    int addCommodity(Commodity commodity);

    @Delete({"delete from", TABLE_NAME, "where id=#{id}"})
    void deleteById(int id);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where user_id=#{userId}"})
    List<Commodity> selectByUserId(int userId);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where type=#{type}"})
    List<Commodity> selectByType(String type);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where price=#{price}"})
    List<Commodity> selectByPrice(float price);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where id=#{id}"})
    Commodity selectById(int id);


}
