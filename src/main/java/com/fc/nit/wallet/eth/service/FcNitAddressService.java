package com.fc.nit.wallet.eth.service;

import java.io.File;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.utils.Numeric;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.nit.wallet.common.config.SystemConfig;
import com.fc.nit.wallet.common.response.RespEnums;
import com.fc.nit.wallet.util.EthConnectPool;

import net.sf.json.JSONObject;

@Service
public class FcNitAddressService {

	Logger logger = Logger.getLogger(FcNitAddressService.class);
	
	@Autowired
	EthConnectPool ethConnectPool;
	
	@Autowired
	SystemConfig systemConfig;
	
	public String createAddress(String password) throws Exception {

		DeterministicSeed deterministicSeed = new DeterministicSeed(new SecureRandom(), 128,"", System.currentTimeMillis() / 1000);
		DeterministicKeyChain chain = DeterministicKeyChain.builder().seed(deterministicSeed).build();
		//TODO 暂时默认M/44H/60H/0H/0/0
		Credentials credentials = Credentials.create(chain.getKeyByPath(HDUtils.parsePath("M/44H/60H/0H/0/0"), true).getPrivKey().toString(16));
		ECKeyPair ecKeyPair = credentials.getEcKeyPair();
		WalletUtils.generateWalletFile(password, credentials.getEcKeyPair(), new File(systemConfig.getFileUrl()), false);
		
		WalletFile walletFile =  Wallet.createLight(password, ecKeyPair);
		ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
		String keystore = objectMapper.writeValueAsString(walletFile);
		
		JSONObject json = new JSONObject();
		json.put("address", credentials.getAddress());
		json.put("mnemonic", Arrays.toString(deterministicSeed.getMnemonicCode().toArray()).replaceAll("\\[|\\,|\\]", ""));
		//TODO 暂时默认M/44H/60H/0H/0/0
		json.put("hd", "m/44'/60'/0'/0/0");
		json.put("keystore", keystore);
		json.put("privateKey", ecKeyPair.getPrivateKey().toString(16));
		// TODO 绑定账号设备
		return RespEnums.getOk(json);
	}

	public String checkMnemonic(List<String> mnemonicList,String[] pathArray) {
		
		DeterministicSeed ds = new DeterministicSeed(mnemonicList, null, "", System.currentTimeMillis() / 1000);
		
		DeterministicKey dkKey = HDKeyDerivation.createMasterPrivateKey(ds.getSeedBytes());
		for (int i = 1; i < pathArray.length; i++) {
			ChildNumber childNumber;
			if (pathArray[i].endsWith("'")) {
				int number = Integer.parseInt(pathArray[i].substring(0,
						pathArray[i].length() - 1));
				childNumber = new ChildNumber(number, true);
			} else {
				int number = Integer.parseInt(pathArray[i]);
				childNumber = new ChildNumber(number, false);
			}
			dkKey = HDKeyDerivation.deriveChildKey(dkKey, childNumber);
		}

		ECKeyPair keyPair = ECKeyPair.create(dkKey.getPrivKeyBytes());

		String address = Numeric.prependHexPrefix(Keys.getAddress(keyPair.getPublicKey().toString(16)));
		 
		JSONObject json = new JSONObject();
		json.put("address", address);
		// TODO 绑定账号
		return RespEnums.getOk(json);
	}

	public String createAdmin(String password) {
		return null;
	}
	
}
