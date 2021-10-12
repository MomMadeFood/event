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
			result = "쿠폰발급 성공";
		}else {
			result = "이미 발급된 쿠폰입니다.";
		}
		logger.info("사용자 ID: "+reqParam.get("memberId")+", 결과 : "+result);
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
	            // 시간 측정 코드(o)
	            // Service 객체 호출 코드
	            logger.info(Thread.currentThread().getName() + ": 이벤트 처리");
	            logger.info(eventCount++ + "번째 접근");
	            if (eventCouponIssueCount > 2) {
	               return 1;
	            } else {
	               JsonNode resultNode = couponService.issueEventCoupon(couponNo, memberId);
	               System.out.println(resultNode.get("data"));
	               System.out.println(resultNode.get("data").get("result"));
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
	      logger.info(Thread.currentThread().getName() + ": 큐에 작업을 저장");

	      // 이벤트 처리가 완료될 때까지 대기
	      int result = future.get();
	      JSONObject jsonObject = new JSONObject();
	      if (result == 0) {
	         jsonObject.put("result", "success");
	      } else {
	         if(result==1)
	            jsonObject.put("message", "이미 종료된 이벤트입니다.");
	         else if(result==2)
	            jsonObject.put("message", "이미 발급된 쿠폰입니다.");
	         jsonObject.put("result", "fail");
	      }
	      
	      	
	    PrintWriter pw = response.getWriter();
	    String json = jsonObject.toString();
	    pw.println(json);
	    

		
	  }
}  
