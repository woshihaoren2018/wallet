package com.fc.nit.wallet.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;

import com.fc.nit.wallet.common.config.SystemConfig;


/**
 *以太坊链接
 */
@Service
@Scope("singleton")
public class EthConnectPool {

	@Autowired
	SystemConfig systemConfig;

	private static Admin admin;
	private static Web3j web3j;

	public Web3j getWeb3j() {
		if (web3j == null) {
			web3j = Web3j.build(new HttpService(systemConfig.getNetworkUrl()));
		}
		return web3j;
	}
	
	//TODO 使用失败可能需要自己的节点
	public Admin getAdmin() {
		if (admin == null) {
			admin = Admin.build(new HttpService(systemConfig.getNetworkUrl()));
		}
		return admin;
	}

}
