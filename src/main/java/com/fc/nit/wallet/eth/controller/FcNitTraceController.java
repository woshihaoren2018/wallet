package com.fc.nit.wallet.eth.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fc.nit.wallet.common.config.SystemConfig;
import com.fc.nit.wallet.common.response.RespEnums;
import com.fc.nit.wallet.eth.service.FcNitTraceService;
import com.fc.nit.wallet.util.EthConnectPool;

/**
 * 
 * 交易管理
 * 
 * @author jialy
 *
 */
@Controller
@RequestMapping("/nit/trace")
public class FcNitTraceController {

	Logger logger = Logger.getLogger(FcNitTraceController.class);
	
	@Autowired
	EthConnectPool ethConnectPool;
	
	@Autowired
	SystemConfig systemConfig;
	
	@Autowired
	FcNitTraceService fcNitTraceService;
	
	/**
	 * 
	 * 交易查询
	 * 
	 * @param transactionHash
	 * @return
	 */
	@RequestMapping(value = "/queryTrace", method = RequestMethod.POST)
	@ResponseBody
	public String queryTrace(String transactionHash) {
		String req = null;
		
		if (transactionHash.isEmpty()) {
			return RespEnums.getError(RespEnums.ERROR_1007);
		}
		
		try {
			req = fcNitTraceService.queryTrace(transactionHash);
		} catch (Exception e) {
			logger.error("交易查询失败 " + transactionHash + ":" + e.getMessage(), e);
			return RespEnums.getError(RespEnums.ERROR_1007);
		}
		return req;
	}

	/**
	 * 转账
	 * @param ownAddress 转出地址
	 * @param toAddress  转人地址
	 * @param password   密码
	 * @param privateKey 私钥
	 * @param amount     转账金额
	 * @return
	 */
	@RequestMapping(value = "/initiateTrade", method = RequestMethod.POST)
	@ResponseBody
	public String initiateTrade(String ownAddress,String toAddress,String password,String privateKey,String amount) {
		
		String req = null;
		
		try {
			req = fcNitTraceService.initiateTrade(ownAddress,toAddress, password, privateKey, amount);
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return req;
	}
	
//	
//	/**
//	 * 签名交易
//	 * @param ownAddress 转出地址
//	 * @param toAddress  转人地址
//	 * @param password   密码
//	 * @param privateKey 私钥
//	 * @param amount     转账金额
//	 * @return
//	 */
//	@RequestMapping(value = "/signTrade", method = RequestMethod.POST)
//	@ResponseBody
//	public BaseResponse signTrade(String ownAddress,String toAddress,String password,String privateKey,String amount) {
//		BaseResponse req = new BaseResponse();
//		JSONObject json =new JSONObject();
//		try {
//			
//	         //TODO 参数验证
//			//TODO 旷工费计算
//			 //设置需要的矿工费
//			BigInteger GAS_PRICE = BigInteger.valueOf(11_000_000_000L);
//			BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);
//			
//	        //转账人私钥
//	        Credentials credentials = password.isEmpty()?Credentials.create(privateKey):
//	        	WalletUtils.loadCredentials(password,systemConfig.getFileUrl()+ownAddress);
//			
//	        Web3j web3j = ethConnectPool.getWeb3j();
//	        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
//	                ownAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
//	        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
//	        
//	        BigInteger value = Convert.toWei(new BigDecimal(amount), Convert.Unit.ETHER).toBigInteger();
//	        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
//	                nonce, GAS_PRICE, GAS_LIMIT, toAddress, value);
//
//	        //	        //签名Transaction，这里要对交易做签名
//	        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
//	        String hexValue = Numeric.toHexString(signedMessage);
//	//
////	        //发送交易
//	        EthSendTransaction ethSendTransaction =
//	                web3j.ethSendRawTransaction(hexValue).sendAsync().get();
//	        String transactionHash = ethSendTransaction.getTransactionHash(); 
//	        json.put("transactionHash", transactionHash);
//		} catch (Exception e) {
//			logger.error(e);
//		}
//      return req;
//	}
}
