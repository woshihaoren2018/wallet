package com.fc.nit.wallet.eth.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fc.nit.wallet.common.response.RespEnums;
import com.fc.nit.wallet.eth.service.FcNitBalanceService;

/**
 * 
 * 余额管理
 * 
 * @author jialy
 *
 */
@Controller
@RequestMapping("/nit/balance")
public class FcNitBalanceController {

	Logger logger = Logger.getLogger(FcNitBalanceController.class);
	
	@Autowired
	FcNitBalanceService fcNitBalanceService;
	
	/**
	 * 
	 * 余额查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryBalance", method = RequestMethod.POST)
	@ResponseBody
	public String queryBalance(String address) {
		String req = null;
		
		if (address.isEmpty()) {
			return RespEnums.getError(RespEnums.ERROR_1004);
		}
		if (address.length() != 42) {
			return RespEnums.getError(RespEnums.ERROR_1006);
		}
		
		try {
			req = fcNitBalanceService.queryBalance(address);
		} catch (Exception e) {
			logger.error("余额查询失败 " + address + ":" + e.getMessage(), e);
			return RespEnums.getError(RespEnums.ERROR_1005);
		}
		
		return req;
	}
	
}
