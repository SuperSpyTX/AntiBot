package com.superspytx.ab.settings;

public class Language {
	/* This is incomplete.  Infact, it's still needs to be done.  
	 * I'm leaving this the way it is for now until I get around to it.
	 * If you would like to complete this for me, you can make a PR.
	 * Thanks if you decide to.
	 */
	
	public static String prefix = "\247f[\247bAntiBot\247f] ";
	public static String kickMsg = "[AntiBot] Tripwired protection!";
	
	/* Admin Notifications */
	public static String adminDSNotify = "\247cAntiBot is currently under delayed start.  Chat Spam is not protected yet.";
	public static String adminNVNotify = "\247aAntiBot has a new version available! New version: &nv& Your version: &ov&";
	public static String adminNBNotify = "\247aAntiBot has a new development build available! New build #&nv& Your build #&ov&";
	
	/* Country Ban */
	public static String countryBanMsg = "Your country is banned from this server!";
	
	/* Chat Flow */
	public static String overflowedMessage = "SILENCE! Let's cool it down for %sec% seconds.";
	
	/* CAPTCHA */
	public static String captchaKick = "[AntiBot] CAPTCHA Failed!";
	public static String captoneLeft = "one attempt left";
	public static String captattemptsLeft = "attempts left";
	
	/* Login Delay */
	public static String loginDelayMsg = "You logged in too quickly! Please wait 10 second(s).";
}
