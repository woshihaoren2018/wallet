package com.fc.nit.wallet.eth.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fc.nit.wallet.common.response.RespEnums;
import com.fc.nit.wallet.eth.service.FcNitAddressService;

/**
 * 
 * 钱包地址管理
 * 
 * @author jialy
 *
 */
@Controller
@RequestMapping("/nit/eth/address")
public class FcNitAddressController {

	Logger logger = Logger.getLogger(FcNitAddressController.class);	

	@Autowired
	FcNitAddressService fcNitAddressService;

	/**
	 * 
	 * 静态生成钱包地址
	 * 
	 * @param password 钱包密码
	 * @return
	 */
	@RequestMapping(value = "/createAddress", method = RequestMethod.POST)
	@ResponseBody
	public String createAddress(String password) {
		String req = null;
		try {
			// 密码不可为空
			if (password.isEmpty()) {
				return RespEnums.getError(RespEnums.ERROR_1001);
			}
			// 密码长度小于9
			if (password.length() < 9) {
				return RespEnums.getError(RespEnums.ERROR_1002);
			}
			
			req = fcNitAddressService.createAddress(password);
			
		} catch (Exception e) {
			logger.error("password" + password + "静态生成钱包地址创建失败!"+e.getMessage(), e);
			return RespEnums.getError(RespEnums.ERROR_1003);
		}
		
		return req;
	}
	
	@RequestMapping(value = "/checkMnemonic", method = RequestMethod.POST)
	@ResponseBody
	public String checkMnemonic(String mnemonic,String big44) {
		String req = null;
		try {
//			"m/44'/60'/0'/0/0"
			 List<String> mnemonicList = Arrays.asList(mnemonic.split(" "));
			req = fcNitAddressService.checkMnemonic(mnemonicList,big44.split("/"));
			System.out.println(req);
		} catch (Exception e) {
			logger.error("mnemonic" + mnemonic + "静态生成钱包地址创建失败!"+e.getMessage(), e);
			return RespEnums.getError(RespEnums.ERROR_1003);
		}
		return req;
	}
	
	
	/**
	 * 
	 * 生成管理钱包地址
	 * 
	 * @param password
	 * @return
	 */
	//TODO 测试未通过
	@RequestMapping(value = "/createAdmin", method = RequestMethod.POST)
	@ResponseBody
	public String createAdmin(String password) {
		String req = null;
		try {
			
			// 密码不可为空
			if (password.isEmpty()) {
				return RespEnums.getError(RespEnums.ERROR_1001);
			}
			// 密码长度小于9
			if (password.length() < 9) {
				return RespEnums.getError(RespEnums.ERROR_1002);
			}
			
			req = fcNitAddressService.createAdmin(password);

		} catch (Exception e) {
			logger.error("password" + password + "生成管理钱包地址创建失败："+e.getMessage(), e);
			return RespEnums.getError(RespEnums.ERROR_1003);
		}
		
		return req;
	}

}
