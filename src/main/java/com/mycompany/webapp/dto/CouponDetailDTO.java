package com.mycompany.webapp.dto;

import lombok.Data;

@Data
public class CouponDetailDTO {
	private String memberId;
	private String couponNo;
	private String orderDetailNo;
	private int state;
}
