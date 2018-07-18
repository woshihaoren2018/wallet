package com.fc.nit.wallet.util;

import java.math.BigDecimal;

public class BigDecimalUtil {

	public static BigDecimal weiToEth(BigDecimal wei) {
		 return wei.divide(new BigDecimal("1000000000000000000"));
	}
	public static BigDecimal weiToFFI(BigDecimal wei) {
		return wei.divide(new BigDecimal("1000000"));
	}
	
}
