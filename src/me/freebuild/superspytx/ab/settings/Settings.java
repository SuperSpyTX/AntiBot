package me.freebuild.superspytx.ab.settings;

import me.freebuild.superspytx.ab.AntiBot;

public class Settings
{
    public static AntiBot core = null;
    public static int interval = 5000;
    public static int accounts = 4;
    public static int spamam = 4;
    public static int spamtime = 1500;
    public static int connectFor = 30000;
    public static long startdelay = 60L;
    public static int timetooverflow = 5000;
    public static int overflows = 5;
    public static int loginDelay = 10000;
    public static int loginHold = 10000;
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
    public static boolean logindelayEnabled = true;
    public static boolean checkupdates = true;
    public static boolean delayedStart = true;
    public static boolean captchaEnabled = true;
    public static boolean forceCaptchaOnJoin = false;
    public static boolean forceCaptchaOnChatFlow = true;
    public static boolean forceCaptchaOnMultiAcc = true;
    public static boolean forceCaptchaOnBotSpam = true;
    public static String kickMsg = "[AntiBot] Tripwired protection!";
    public static String captchafail = "[AntiBot] CAPTCHA Failed!";
    public static String countryBanMsg = "Your country is banned from this server!";
    public static String connectMsg = "You are not on the whitelist!";
    public static String connectInvasion = "The server is currently under attack.";
    public static String prefix = "\247f[\247bAntiBot\247f] ";

}
