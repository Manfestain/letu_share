package com.letu.share.dao;

import com.letu.share.model.OrderDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailDAO {
    String TABLE_NAME = "order_detail";
    String INSERT_FIELDS = "order_id, commodity_id, commodity_name, amount";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{orderId}, #{commodityId}, #{commodityName}, #{amount})"})
    int addOrderDetail(OrderDetail orderDetail);
}
