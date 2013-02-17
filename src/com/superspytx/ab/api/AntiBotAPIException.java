package com.superspytx.ab.api;

public class AntiBotAPIException extends Exception {
	
	private String msg;
	
	public AntiBotAPIException(String str) {
		this.msg = str;
	}
	
	public String getMessage() {
		return msg;
	}
	
	private static final long serialVersionUID = 122284875L;
	
}
