package com.fc.nit.wallet.erc20.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;

import com.fc.nit.wallet.common.response.RespEnums;
import com.fc.nit.wallet.util.BigDecimalUtil;
import com.fc.nit.wallet.util.EthConnectPool;

import net.sf.json.JSONObject;

@Service
public class Erc20BalanceService {

	@Autowired
	EthConnectPool ethConnectPool;
	
	public String queryBalance(String address) throws Exception {
		EthGetBalance ethGetBalance = ethConnectPool.getWeb3j().ethGetBalance(address, 
		DefaultBlockParameterName.LATEST).sendAsync().get();
		
		JSONObject json = new JSONObject();
		json.put("balance", BigDecimalUtil.weiToEth(new BigDecimal(ethGetBalance.getBalance())).toPlainString());
		return RespEnums.getOk(json);
	}

}
