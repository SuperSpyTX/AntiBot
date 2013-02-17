package com.superspytx.ab.abs;


public interface Handler {
	public boolean run(EventAction info);
	
	public void performActions(EventAction info);
}
