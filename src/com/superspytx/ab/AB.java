package com.superspytx.ab;

public class AB extends AntiBot {
	// alias for AntiBot plugin so it makes my life easier with adding debug code.
	
	// fix a potential issue.
	public static AntiBot getInstance() {
		return AntiBot.getInstance();
	}
}
