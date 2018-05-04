package com.letu.share.dao;

import com.letu.share.model.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDAO {
    String TABLE_NAME = "order";
    String INSERT_FIELDS = "id, buyer_id, total_price, order_state, created_date, expect_date";
    String SELECT_FIELDS = INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{Id}, #{buyerId}, #{totalPrice}, #{state}, #{createdDate}, #{expectDate}"})
    int addOrder(Order order);
}
