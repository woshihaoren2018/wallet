package com.fc.nit.wallet.util;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

/**
 * 批量查询token余额
 */
public class TokenBalanceTask {

	public class Token {
		public String contractAddress;
		public int decimals;
		public String name;
	}

	private static Web3j web3j;

	//要查询的token合约地址
	private static List<Token> tokenList;

	//要查询的钱包地址
//	private static List<String> addressList;

	public static void main(String[] args) {
		web3j = Web3j.build(new HttpService(Environment.RPC_URL));
		try {
			processTask();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	private static void processTask() throws Exception {
		for (int i = 1; i < 12; i++) {
			Document doc = Jsoup.connect("https://etherscan.io/tokens?p=" + i).get();
			Elements container = doc.getElementsByTag("a");
			for (Element aa : container) {
				String token = aa.attr("href");
				if (token.toLowerCase().startsWith("/token/0x")) {
					token = token.replace("/token/", "");
//					String tokenName = TokenClient.getTokenName(web3j, token);
//					int tokenDecimals = TokenClient.getTokenDecimals(web3j, token); 
//					String tokenSymbol = TokenClient.getTokenSymbol(web3j, token);
//					BigInteger tokenTotalSupply = TokenClient.getTokenTotalSupply(web3j, token);
//					web3j.ethGetBalance("0x7f1732c4f47d3895338b379aa0705e3536d4a9e0", DefaultBlockParameterName.LATEST).sendAsync().get();
//					BigDecimal balance = new BigDecimal(TokenClient.getTokenBalance(web3j, "0x7f1732c4f47d3895338b379aa0705e3536d4a9e0", token));
//					balance.divide(BigDecimal.TEN.pow(tokenDecimals));
//					System.out.println("address " + "0x7f1732c4f47d3895338b379aa0705e3536d4a9e0" + " name " + tokenName + " balance " + balance);
//					st.add(token);
				}
			}
		}
		
		
		
//		}
//		}
	}
}
