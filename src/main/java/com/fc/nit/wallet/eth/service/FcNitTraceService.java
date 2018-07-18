package com.fc.nit.wallet.eth.service;

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
public class FcNitTraceService {

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
		
		Credentials credentials = password.isEmpty()?Credentials.create(privateKey):
        WalletUtils.loadCredentials(password,systemConfig.getFileUrl() + ownAddress);
        
        TransactionReceipt transactionReceipt = Transfer.sendFunds(ethConnectPool.getWeb3j(), 
        credentials,toAddress,new BigDecimal(amount), Convert.Unit.ETHER).send();
        JSONObject json =new JSONObject();
        json.put("transactionHash", transactionReceipt.getTransactionHash());
        
		return RespEnums.getOk(json);
	}
}
