package com.mycompany.webapp.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.CouponDetailDTO;

@Mapper
public interface CouponDAO {

	int updateCouponById(String couponNo);
}
