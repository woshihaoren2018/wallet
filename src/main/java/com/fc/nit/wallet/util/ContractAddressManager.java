package com.fc.nit.wallet.util;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class ContractAddressManager {
	
	public static void main(String[] args) {
		try {
			Set<String> st = new LinkedHashSet<String>();
			for (int i = 1; i < 12; i++) {
				Document doc = Jsoup.connect("https://etherscan.io/tokens?p=" + i).get();
				Elements container = doc.getElementsByTag("a");
				for (Element aa : container) {
					String token = aa.attr("href");
					if (token.toLowerCase().startsWith("/token/0x")) {
						token = token.replace("/token/", "");
						st.add(token);
					}
				}
			}
			for (String str : st) {
				System.out.println(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}