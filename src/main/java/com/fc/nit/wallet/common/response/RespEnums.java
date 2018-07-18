package com.fc.nit.wallet.common.response;

import net.sf.json.JSONObject;

public enum RespEnums {

	/**密码不能为空*/
	ERROR_1001("1001","密码不能为空"),
	/**密码长度小于9位*/
	ERROR_1002("1002","密码长度小于9位"),
	/**钱包创建异常,请重试*/
	ERROR_1003("1003","钱包创建异常,请重试"),
	/**钱包地址为空*/
	ERROR_1004("1004","钱包地址为空"),
	/**余额查询异常,请重试*/
	ERROR_1005("1005","余额查询异常,请重试"),
	/**钱包地址错误*/
	ERROR_1006("1006","钱包地址错误"),
	/**交易哈希为空*/
	ERROR_1007("1007","交易哈希为空"),
	/**交易查询异常,请重试*/
	ERROR_1008("1008","交易查询异常,请重试");
    
    private String code;
    private String msg;
    
    private RespEnums() {
    }
    
    private RespEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
   

    /**
     * 错误提示
     * @param errEnums
     * @return
     */
    public static String getError(RespEnums errEnums) {
    	JSONObject json = new JSONObject();
		json.put("code",errEnums.code);
		json.put("msg", errEnums.msg);
    	json.put("data", new Object());
    	return json.toString();
    }
    
    /**
     * 正确提示
     * @param errEnums
     * @param data
     * @return
     */
    public static String getOk(Object data) {
    	JSONObject json = new JSONObject();
    		json.put("code", "0000");
    		json.put("msg", "成功");
    	json.put("data", data);
    	return json.toString();
    }
}