package com.fc.nit.wallet.erc20.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import com.fc.nit.wallet.common.config.SystemConfig;
import com.fc.nit.wallet.common.response.RespEnums;
import com.fc.nit.wallet.util.EthConnectPool;

import net.sf.json.JSONObject;

@Service
public class Erc20TraceService {

	@Autowired
	EthConnectPool ethConnectPool;
	
	@Autowired
	SystemConfig systemConfig;
	
	public String queryTrace(String transactionHash) throws Exception {
		 Optional<Transaction> transaction = ethConnectPool.getWeb3j().ethGetTransactionByHash(transactionHash).send().getTransaction();
		 JSONObject json = new JSONObject();
		 json.put("state", transaction.get());
		return RespEnums.getOk(json);
	}

	public String initiateTrade(String ownAddress, String toAddress, String password, String privateKey,
			String amount) throws Exception {
		
		Credentials credentials = password.isEmpty() ? Credentials.create(privateKey):
        WalletUtils.loadCredentials(password,systemConfig.getFileUrl() + ownAddress);
        
        TransactionReceipt transactionReceipt = Transfer.sendFunds(ethConnectPool.getWeb3j(), 
        credentials,toAddress,new BigDecimal(amount), Convert.Unit.ETHER).send();
        JSONObject json =new JSONObject();
        json.put("transactionHash", transactionReceipt.getTransactionHash());
        
		return RespEnums.getOk(json);
	}
	
	public String erc20InitiateTrade(String ownAddress, String toAddress, String password, String privateKey,
			String amount) throws Exception {
		
		// 加载钱包
		Credentials credentials = password.isEmpty() ? Credentials.create(privateKey)
				: WalletUtils.loadCredentials(password, systemConfig.getFileUrl() + ownAddress);

		// 获取NONCE // 交易的发起者在之前进行过的交易数量
		BigInteger nonce  = ethConnectPool.getWeb3j().ethGetTransactionCount(ownAddress, 
				DefaultBlockParameterName.LATEST).sendAsync().get().getTransactionCount();

		// 创建交易 注意金额 保留小数点后8位 要转化为整数 比如0.00000001 转化为1
		Function function = new Function("transfer", // 交易的方法名称
				Arrays.asList(new Address(toAddress), new Uint256(new BigInteger(amount))),
				Arrays.asList(new TypeReference<Address>() {
				}, new TypeReference<Uint256>() {}));
		String encodedFunction = FunctionEncoder.encode(function);
		// 智能合约事物
		BigInteger gasPrice = BigInteger.valueOf(3_000_000_000L);
		BigInteger gasLimit = BigInteger.valueOf(4_300_000L);
		RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
				"0x35264431f3541350E75b248c4F4Db7c9eE657499", encodedFunction);

		byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
		String hexValue = Numeric.toHexString(signedMessage);
		// 发送事务
		EthSendTransaction ethSendTransaction = ethConnectPool.getWeb3j().ethSendRawTransaction(hexValue).sendAsync().get();

		JSONObject json = new JSONObject();
		json.put("transactionHash", ethSendTransaction.getTransactionHash());

		return RespEnums.getOk(json);
	}

}
