package com.mycompany.webapp.controller;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.webapp.service.CouponService;

@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Resource
	private CouponService couponService;
	

	  @RequestMapping(value="/issueEventCoupon", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	  public @ResponseBody Map<String, Object> issueEventCoupon(@RequestBody Map<String, Object> reqParam){
		  
		  Map<String, Object> map = new HashMap<String, Object>();
		  Map<String,String> data = couponService.issueCoupon((String)reqParam.get("memberId"), (String)reqParam.get("couponNo"));
		  map.put("data",data);
	      return map;
	  }
	  
	  
}  
