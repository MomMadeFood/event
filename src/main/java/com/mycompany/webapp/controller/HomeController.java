package com.mycompany.webapp.controller;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.mycompany.webapp.service.CouponService;

@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static List<String> list = new ArrayList<String>();
	
	private ExecutorService executorService = Executors.newFixedThreadPool(1);
	   

	   
	private static int eventCouponIssueCount = 1;
	private static int eventCount = 1;
	@Resource
	private CouponService couponService;
	

	
	@GetMapping("/")
	public String content(Model model,HttpServletRequest request) {
	

		return "home";
	}
	/*
	@RequestMapping(value="/issueEventCoupon", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody Map<String, Object> issueEventCoupon(@RequestBody Map<String, Object> reqParam){
		
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String,String> data = couponService.issueCoupon((String)reqParam.get("memberId"), (String)reqParam.get("couponNo"));

		String result ="";
		if(data.get("result").equals("success")) {
			result = "???????????? ??????";
		}else {
			result = "?????? ????????? ???????????????.";
		}
		logger.info("????????? ID: "+reqParam.get("memberId")+", ?????? : "+result);
		map.put("data",data);
	    return map;
	}
	*/
	
	@PostMapping(value = "/issueEventCoupon")
	public void issueEventCoupon(String couponNo,String memberId,String memberIp,HttpServletResponse response) throws Exception {
		
		    response.setHeader("Access-Control-Allow-Origin", "*"); 
		    response.setHeader("Access-Control-Allow-Methods", "OPTIONS"); 
		    response.setHeader("Access-Control-Allow-Headers", "content-type");
		    response.setContentType("application/json; charset=UTF-8");
		   
		    
		    
		
	      Callable<Integer> task = new Callable<Integer>() {
	         @Override
	         public Integer call() throws Exception {
	            logger.info(eventCount++ + "?????? ??????");
	            if (eventCouponIssueCount > 2) {
	               return 1;
	            } else {
	               JsonNode resultNode = couponService.issueEventCoupon(couponNo, memberId);
	               System.out.println(resultNode.get("data"));
	               String result = resultNode.get("data").get("result").toString();
	               if(result.equals("\"success\"")) {
	                  eventCouponIssueCount++;
	                  return 0;
	               }else {
	                  return 2;
	               }
	               
	            }
	         }
	      };
	      Future<Integer> future = executorService.submit(task);

	      // ????????? ????????? ????????? ????????? ??????
	      int result = future.get();
	      JSONObject jsonObject = new JSONObject();
	      if (result == 0) {
	         jsonObject.put("result", "success");
	      } else {
	         if(result==1)
	            jsonObject.put("message", "?????? ????????? ??????????????????.");
	         else if(result==2)
	            jsonObject.put("message", "?????? ????????? ???????????????.");
	         jsonObject.put("result", "fail");
	      }
	      
	      	
	    PrintWriter pw = response.getWriter();
	    String json = jsonObject.toString();
	    pw.println(json);
	    

		
	  }
}  
