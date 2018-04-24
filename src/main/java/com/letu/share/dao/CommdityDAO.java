package com.letu.share.dao;

import com.letu.share.model.Commodity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommdityDAO {
    String TABLE_NAME = "commodity_info";
    String INSERT_FIELDS = "name, picture, type, recommend, region, price";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{name}, #{picture}, #{type}, #{recommend}, #{region}, #{price})"})
    int addCommodity(Commodity commodity);


}
