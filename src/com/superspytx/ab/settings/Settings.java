package com.superspytx.ab.settings;

import com.superspytx.ab.AntiBot;

public class Settings {
	public static AntiBot core = null;
	public static int interval = 5000; // Bot: Interval
	public static int accounts = 4;
	public static int spamam = 4;
	public static int spamtime = 1500;
	public static int spamdiffct = 3;
	public static int connectFor = 30000;
	public static int timetooverflow = 5000;
	public static int overflows = 5;
	public static int captchaAttempts = 3;
	public static long startdelay = 60L;
	public static long loginDelay = (long) 10000L;
	public static long loginHold = (long) 10000L;
	public static long installdate = 0L;
	public static String version = "";
	public static boolean newVersion = false;
	public static boolean spamBotProtection = true;
	public static boolean delayingStart = false;
	public static boolean flowEnabled = true;
	public static boolean flowKicks = true;
	public static boolean notify = true;
	public static boolean captchaIdleKicks = true;
	public static boolean enabled = true;
	public static boolean debugmode = false; // TODO: set to false!
	public static boolean enableAntiSpam = true;
	public static boolean geoIP = true;
	public static boolean whiteListCountry = false;
	public static boolean logindelayEnabled = true;
	public static boolean checkupdates = true;
	public static boolean delayedStart = false;
	public static boolean captchaEnabled = true;
	public static boolean forceCaptchaOnChatSpam = true;
	public static boolean forceCaptchaOnChatFlow = true;
	public static boolean forceCaptchaOnBotSpam = true;
	private static boolean setup = false;
	
	public static boolean isSet() {
		return setup;
	}
	
	public static void setup() {
		setup = true;
	}
}
