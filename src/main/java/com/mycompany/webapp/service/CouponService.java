package com.mycompany.webapp.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class CouponService {
   

   
   public JsonNode issueEventCoupon(String couponNo, String memberId) {
      HttpClient client = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build(); // HttpClient 생성
      HttpPost httpPost = new HttpPost("http://192.168.40.71:8080/issueEventCoupon"); // POST 메소드 URL 새성
 
      try {
         httpPost.setHeader("Accept", "application/json");
         httpPost.setHeader("Connection", "keep-alive");
         httpPost.setHeader("Content-Type", "application/json");
         //httpPost.setHeader("Access-Control-Allow-Headers","content-type");

         // json 메시지 입력
         httpPost.setEntity(new StringEntity("{\"couponNo\":\"" + couponNo + "\",\"memberId\":\"" + memberId + "\"}"));

         HttpResponse response = client.execute(httpPost);


         // Response 출력
         if (response.getStatusLine().getStatusCode() == 200) {
            ResponseHandler<String> handler = new BasicResponseHandler();
            String body = handler.handleResponse(response);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = objectMapper.readTree(body);
            return node;
         } else {
            System.out.println("response is error : " + response.getStatusLine().getStatusCode());
         }
      } catch (Exception e) {
         e.printStackTrace();
      }

      return null;
   }
}

   