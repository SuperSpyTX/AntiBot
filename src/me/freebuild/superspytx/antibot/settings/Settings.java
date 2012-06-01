package me.freebuild.superspytx.antibot.settings;

public class Settings {
	
	public static int interval = 5000;
	public static int accounts = 4;
	public static int spamam = 4;
	public static int spamtime = 1500;
	public static int connectFor = 30000;
	public static long startdelay = 60L;
	public static int timetooverflow = 5000;
	public static int overflows = 5;
	public static String overflowedmessage = "SILENCE! Let's cool it down for %sec% seconds.";
	public static boolean flowEnabled = true;
	public static boolean notify = true;
	public static boolean useWhiteListPerms = false;
	public static boolean useOpPerms = false;
	public static boolean enabled = true;
	public static boolean debugmode = false;
	public static boolean whiteList = false;
	public static boolean enableAntiSpam = true;
	public static boolean chatMute = false;
	public static boolean enableMultiAccs = false;
	public static boolean banUsers = false;
	public static boolean geoIP = true;
	public static boolean whiteListCountry = false;
	public static boolean checkupdates = true;
	public static boolean delayedStart = true;
	public static String kickMsg = "[AntiBot] Tripwired protection!";
	public static String countryBanMsg = "Your country is banned from this server!";
	public static String connectMsg = "You are not on the whitelist!";
	public static String connectInvasion = "The server is currently under attack.";
	public static String prefix = "\247f[\247bAntiBot\247f] ";

}
