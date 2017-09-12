package com.huishu.ait.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 账号未审核异常
 * 
 * @author yindq
 * @create 2017年9月7日
 */

public class AccountStartException extends AuthenticationException {
	
	private static final long serialVersionUID = 5024425056491608508L;

	public AccountStartException() {
        super();
    }
	
	public AccountStartException(String message) {
        super(message);
    }
	
	public AccountStartException(Throwable cause) {
        super(cause);
    }
	
	public AccountStartException(String message, Throwable cause) {
        super(message, cause);
    }
	
}
