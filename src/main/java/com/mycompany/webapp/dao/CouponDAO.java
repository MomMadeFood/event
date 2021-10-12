package com.mycompany.webapp.dao;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CouponDAO {

	int updateCouponById(String couponNo);
}
