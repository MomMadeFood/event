package com.mycompany.webapp.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.mycompany.webapp.controller.HomeController;
import com.mycompany.webapp.dao.CouponDAO;
import com.mycompany.webapp.dao.CouponDetailDAO;
import com.mycompany.webapp.dto.CouponDetailDTO;
import com.mycompany.webapp.exception.CouponException;

@Service
public class CouponService {
	
	@Resource
	private CouponDetailDAO couponDetailDAO;
	
	@Resource
	private CouponDAO couponDAO;
	
	@Resource
	private TransactionTemplate transactionTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(CouponService.class);
	
	public Map<String,String> issueCoupon(String memberId, String couponNo) {
		
		logger.info("실행");
		
		Map<String,String> resultMap = new HashMap<String, String>();
		Map<String,String> result = transactionTemplate.execute(new TransactionCallback<Map<String,String> >() {
			@Override
			public Map<String,String> doInTransaction(TransactionStatus status) {
				try {
					
					
					CouponDetailDTO coupon = new CouponDetailDTO();
					coupon.setCouponNo(couponNo);
					coupon.setMemberId(memberId);
					
					
					int count = couponDetailDAO.selectCountByCouponDetail(coupon);
					
					if(count>=1) {
						throw new CouponException("이미 발급한 쿠폰입니다.");
					}

					int updateResult = couponDAO.updateCouponById(couponNo);
					
					if(updateResult==0) {
						throw new CouponException("쿠폰 정보 업데이트 중 오류가 발생했습니다.");
					}

					updateResult = couponDetailDAO.insertCouponDetailByCouponDetail(coupon);
					if(updateResult==0) {
						throw new CouponException("쿠폰 발급중 오류가 발생했습니다.");
					}
					resultMap.put("result","success");
					
					return resultMap;
				}catch (CouponException e) {
					resultMap.put("result","fail");
					resultMap.put("message",e.getMessage());
					return resultMap;
				}catch(Exception e) {
					resultMap.put("result","fail");
					resultMap.put("message","예상치못한 오류 발생");
					return resultMap;
				}
				
			}
		});
		return result;
	}
    
}
