package me.freebuild.superspytx.settings;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import me.freebuild.superspytx.AntiBot;
import net.h31ix.anticheat.api.*;
import net.h31ix.anticheat.manage.CheckType;

public class SettingsCore
{

    public AntiBot antibot = null;
    public int reloads = 0;

    public SettingsCore(AntiBot instance)
    {
        antibot = instance;
    }

    public void saveSettings(File dataFolder)
    {

        File Config = new File(dataFolder.getAbsolutePath() + File.separator + "c.properties");

        if (!Config.exists())
        {
            System.out.print("AntiBot: Configuration is missing, creating...");
            try
            {
                Config.createNewFile();
                Properties propConfig = new Properties();
                propConfig.setProperty("connect-message", Settings.connectMsg);
                propConfig.setProperty("kick-message", Settings.kickMsg);
                propConfig.setProperty("captcha-failed-message", Settings.captchafail);
                propConfig.setProperty("prefix", Settings.prefix);
                propConfig.setProperty("flow-message", Settings.overflowedmessage);
                propConfig.setProperty("countryban-message", Settings.countryBanMsg);
                propConfig.setProperty("countrybans", "");
                propConfig.setProperty("connect-join-invasion", Settings.connectInvasion);
                propConfig.setProperty("joins-sec", Integer.toString(Settings.interval));
                propConfig.setProperty("whitelist-perms", Boolean.toString(Settings.useWhiteListPerms));
                propConfig.setProperty("op-perms", Boolean.toString(Settings.useOpPerms));
                propConfig.setProperty("check-updates", Boolean.toString(Settings.checkupdates));
                propConfig.setProperty("orgy-notify", Boolean.toString(Settings.notify));
                propConfig.setProperty("delayed-start", Boolean.toString(Settings.delayedStart));
                propConfig.setProperty("country-whitelist-mode", Boolean.toString(Settings.whiteListCountry));
                propConfig.setProperty("debug-mode", Boolean.toString(Settings.debugmode));
                propConfig.setProperty("enable-by-default", Boolean.toString(Settings.enabled));
                propConfig.setProperty("enable-captcha", Boolean.toString(Settings.captchaEnabled));
                propConfig.setProperty("joins", Integer.toString(Settings.accounts));
                propConfig.setProperty("enable-antispam", Boolean.toString(Settings.enableAntiSpam));
                propConfig.setProperty("enable-multiacc-detection", Boolean.toString(Settings.enableMultiAccs));
                propConfig.setProperty("captcha-on-join", Boolean.toString(Settings.forceCaptchaOnJoin));
                propConfig.setProperty("captcha-on-chat-overflow", Boolean.toString(Settings.forceCaptchaOnChatFlow));
                propConfig.setProperty("captcha-on-multi-account", Boolean.toString(Settings.forceCaptchaOnMultiAcc));
                propConfig.setProperty("captcha-on-bot-spam", Boolean.toString(Settings.forceCaptchaOnBotSpam));
                propConfig.setProperty("chat-mute", Boolean.toString(Settings.chatMute));
                propConfig.setProperty("whitelist-when-triggered", Boolean.toString(Settings.whiteList));
                propConfig.setProperty("start-delay", Long.toString(Settings.startdelay));
                propConfig.setProperty("spam-time", Integer.toString(Settings.spamtime));
                propConfig.setProperty("spam-amount", Integer.toString(Settings.spamam));
                propConfig.setProperty("connection-time", Integer.toString(Settings.connectFor));
                propConfig.setProperty("flow-time", Integer.toString(Settings.timetooverflow));
                propConfig.setProperty("flow-amount", Integer.toString(Settings.overflows));
                propConfig.setProperty("flow-enabled", Boolean.toString(Settings.flowEnabled));
                propConfig.setProperty("ban-users", Boolean.toString(Settings.banUsers));
                propConfig.setProperty("enable-geoip", Boolean.toString(Settings.geoIP));
                propConfig.setProperty("install-date", Long.toString(System.currentTimeMillis()));
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(Config.getAbsolutePath()));
                propConfig.store(stream, "AntiBot V2 - The ultimate AntiSpam protection for Minecraft.");
            }
            catch (IOException ex)
            {
                System.out.println("AntiBot: Configuration creation failed.");
            }

        }
    }

    public boolean loadSettings(File dataFolder1)
    {

        System.out.print("AntiBot: Attempt to do the impossible - Eminem.");
        try
        {
            Properties propConfig = new Properties();
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream(dataFolder1.getAbsolutePath() + File.separator + "c.properties"));
            propConfig.load(stream);
            String load = null;
            Integer load2 = null;
            Boolean load3 = null;
            Long load4 = null;

            load = propConfig.getProperty("connect-message");
            if (load != null && load != Settings.connectMsg)
            {
                Settings.connectMsg = load;
            }

            load = propConfig.getProperty("kick-message");
            if (load != null && load != Settings.kickMsg)
            {
                Settings.kickMsg = load;
            }

            load = propConfig.getProperty("captcha-failed-message");
            if (load != null && load != Settings.captchafail)
            {
                Settings.captchafail = load;
            }

            load = propConfig.getProperty("flow-message");
            if (load != null && load != Settings.overflowedmessage)
            {
                Settings.overflowedmessage = load;
            }

            load = propConfig.getProperty("countryban-message");
            if (load != null && load != Settings.countryBanMsg)
            {
                Settings.countryBanMsg = load;
            }

            load = propConfig.getProperty("countrybans");
            if (load != null)
            {
                antibot.getUtility().getGeoIP().loadCountryBanList(load);
            }

            load = propConfig.getProperty("connect-join-invasion");
            if (load != null && load != Settings.connectInvasion)
            {
                Settings.connectInvasion = load;
            }

            load = propConfig.getProperty("prefix");
            if (load != null && load != Settings.prefix)
            {
                Settings.prefix = load;
            }

            load = propConfig.getProperty("joins-sec");
            if (load != null)
            {
                load2 = Integer.parseInt(load);
            }
            else
            {
                load2 = Settings.interval;
            }
            if (load != null && load2 > 999 && !load2.equals(Settings.interval))
            {
                Settings.interval = load2;
                antibot.setDefaultinterval(Settings.interval);
            }

            load = propConfig.getProperty("whitelist-perms");
            if (load != null)
            {
                load3 = Boolean.parseBoolean(load);
            }
            else
            {
                load3 = Settings.useWhiteListPerms;
            }
            if (load != null && !load3.equals(Settings.useWhiteListPerms))
            {
                Settings.useWhiteListPerms = load3;
            }

            load = propConfig.getProperty("op-perms");
            if (load != null)
            {
                load3 = Boolean.parseBoolean(load);
            }
            else
            {
                load3 = Settings.useOpPerms;
            }
            if (load != null && !load3.equals(Settings.useOpPerms))
            {
                Settings.useOpPerms = load3;
            }

            load = propConfig.getProperty("flow-enabled");
            if (load != null)
            {
                load3 = Boolean.parseBoolean(load);
            }
            else
            {
                load3 = Settings.flowEnabled;
            }
            if (load != null && !load3.equals(Settings.flowEnabled))
            {
                Settings.flowEnabled = load3;
            }

            if (reloads < 1)
            {
                load = propConfig.getProperty("delayed-start");
                if (load != null)
                {
                    load3 = Boolean.parseBoolean(load);
                }
                else
                {
                    load3 = Settings.delayedStart;
                }
                if (load != null && !load3.equals(Settings.delayedStart))
                {
                    Settings.delayedStart = load3;
                }
            }

            load = propConfig.getProperty("whitelist-when-triggered");
            if (load != null)
            {
                load3 = Boolean.parseBoolean(load);
            }
            else
            {
                load3 = Settings.whiteList;
            }
            if (load != null && !load3.equals(Settings.whiteList))
            {
                Settings.whiteList = load3;
            }

            load = propConfig.getProperty("enable-captcha");
            if (load != null)
            {
                load3 = Boolean.parseBoolean(load);
            }
            else
            {
                load3 = Settings.captchaEnabled;
            }
            if (load != null && !load3.equals(Settings.captchaEnabled))
            {
                Settings.captchaEnabled = load3;
            }

            load = propConfig.getProperty("check-updates");
            if (load != null)
            {
                load3 = Boolean.parseBoolean(load);
            }
            else
            {
                load3 = Settings.checkupdates;
            }
            if (load != null && !load3.equals(Settings.checkupdates))
            {
                Settings.checkupdates = load3;
            }

            load = propConfig.getProperty("captcha-on-join");
            if (load != null)
            {
                load3 = Boolean.parseBoolean(load);
            }
            else
            {
                load3 = Settings.forceCaptchaOnJoin;
            }
            if (load != null && !load3.equals(Settings.forceCaptchaOnJoin))
            {
                Settings.forceCaptchaOnJoin = load3;
            }

            load = propConfig.getProperty("captcha-on-chat-overflow");
            if (load != null)
            {
                load3 = Boolean.parseBoolean(load);
            }
            else
            {
                load3 = Settings.forceCaptchaOnChatFlow;
            }
            if (load != null && !load3.equals(Settings.forceCaptchaOnChatFlow))
            {
                Settings.forceCaptchaOnChatFlow = load3;
            }

            load = propConfig.getProperty("captcha-on-multi-account");
            if (load != null)
            {
                load3 = Boolean.parseBoolean(load);
            }
            else
            {
                load3 = Settings.forceCaptchaOnMultiAcc;
            }
            if (load != null && !load3.equals(Settings.forceCaptchaOnMultiAcc))
            {
                Settings.forceCaptchaOnMultiAcc = load3;
            }

            load = propConfig.getProperty("captcha-on-bot-spam");
            if (load != null)
            {
                load3 = Boolean.parseBoolean(load);
            }
            else
            {
                load3 = Settings.forceCaptchaOnBotSpam;
            }
            if (load != null && !load3.equals(Settings.forceCaptchaOnBotSpam))
            {
                Settings.forceCaptchaOnBotSpam = load3;
            }

            load = propConfig.getProperty("orgy-notify");
            if (load != null)
            {
                load3 = Boolean.parseBoolean(load);
            }
            else
            {
                load3 = Settings.notify;
            }
            if (load != null && !load3.equals(Settings.notify))
            {
                Settings.notify = load3;
            }

            load = propConfig.getProperty("country-whitelist-mode");
            if (load != null)
            {
                load3 = Boolean.parseBoolean(load);
            }
            else
            {
                load3 = Settings.whiteListCountry;
            }
            if (load != null && !load3.equals(Settings.whiteListCountry))
            {
                Settings.whiteListCountry = load3;
            }

            load = propConfig.getProperty("ban-users");
            if (load != null)
            {
                load3 = Boolean.parseBoolean(load);
            }
            else
            {
                load3 = Settings.banUsers;
            }
            if (load != null && !load3.equals(Settings.banUsers))
            {
                Settings.banUsers = load3;
            }

            load = propConfig.getProperty("enable-geoip");
            if (load != null)
            {
                load3 = Boolean.parseBoolean(load);
            }
            else
            {
                load3 = Settings.geoIP;
            }
            if (load != null && !load3.equals(Settings.geoIP))
            {
                Settings.geoIP = load3;
            }

            load = propConfig.getProperty("enable-antispam");
            if (load != null)
            {
                load3 = Boolean.parseBoolean(load);
            }
            else
            {
                load3 = Settings.enableAntiSpam;
            }
            if (load != null && !load3.equals(Settings.enableAntiSpam))
            {
                Settings.enableAntiSpam = load3;
            }

            // we need to disable AntiCheat's anti spam because AntiBot has one
            // already.... or does it?
            // or do we?
            // Thanks H31IX for approving AntiBot :D
            antibot.getServer().getScheduler().scheduleAsyncDelayedTask(antibot, new Runnable()
            {

                @Override
                public void run()
                {
                    if (antibot.getServer().getPluginManager().getPlugin("AntiCheat") != null)
                    {
                        if (Settings.enableAntiSpam)
                        {
                            AnticheatAPI.deactivateCheck(CheckType.SPAM);
                        }
                        else
                        {
                            AnticheatAPI.activateCheck(CheckType.SPAM);
                        }
                    }
                }

            }, 600L); //start in 30 seconds.

            load = propConfig.getProperty("enable-multiacc-detection");
            if (load != null)
            {
                load3 = Boolean.parseBoolean(load);
            }
            else
            {
                load3 = Settings.enableMultiAccs;
            }
            if (load != null && !load3.equals(Settings.enableMultiAccs))
            {
                Settings.enableMultiAccs = load3;
            }

            load = propConfig.getProperty("chat-mute");
            if (load != null)
            {
                load3 = Boolean.parseBoolean(load);
            }
            else
            {
                load3 = Settings.chatMute;
            }
            if (load != null && !load3.equals(Settings.chatMute))
            {
                Settings.chatMute = load3;
            }

            load = propConfig.getProperty("debug-mode");
            if (load != null)
            {
                load3 = Boolean.parseBoolean(load);
            }
            else
            {
                load3 = Settings.debugmode;
            }
            if (load != null && !load3.equals(Settings.debugmode))
            {
                Settings.debugmode = load3;
                if (Settings.debugmode)
                    System.out.print("AntiBot: WARNING! You're in Debug Mode! Do not use this on a live environment!");
            }

            load = propConfig.getProperty("spam-amount");
            if (load != null)
            {
                load2 = Integer.parseInt(load);
            }
            else
            {
                load2 = Settings.spamam;
            }
            if (load != null && !load2.equals(Settings.spamam))
            {
                Settings.spamam = load2;
            }

            load = propConfig.getProperty("connection-time");
            if (load != null)
            {
                load2 = Integer.parseInt(load);
            }
            else
            {
                load2 = Settings.connectFor;
            }
            if (load != null && !load2.equals(Settings.connectFor))
            {
                Settings.connectFor = load2;
            }

            load = propConfig.getProperty("flow-time");
            if (load != null)
            {
                load2 = Integer.parseInt(load);
            }
            else
            {
                load2 = Settings.timetooverflow;
            }
            if (load != null && !load2.equals(Settings.timetooverflow))
            {
                Settings.timetooverflow = load2;
            }

            load = propConfig.getProperty("flow-amount");
            if (load != null)
            {
                load2 = Integer.parseInt(load);
            }
            else
            {
                load2 = Settings.overflows;
            }
            if (load != null && !load2.equals(Settings.overflows))
            {
                Settings.overflows = load2;
            }

            load = propConfig.getProperty("start-delay");
            if (load != null)
            {
                load4 = Long.parseLong(load);
            }
            else
            {
                load4 = Settings.startdelay;
            }
            if (load != null && load4 < 120L && !load4.equals(Settings.startdelay))
            {
                Settings.startdelay = load2;
            }

            load = propConfig.getProperty("spam-time");
            if (load != null)
            {
                load2 = Integer.parseInt(load);
            }
            else
            {
                load2 = Settings.spamtime;
            }
            if (load != null && !load2.equals(Settings.spamtime))
            {
                Settings.spamtime = load2;
            }

            load = propConfig.getProperty("enable-by-default");
            if (load != null)
            {
                load3 = Boolean.parseBoolean(load);
            }
            else
            {
                load3 = Settings.enabled;
            }
            if (load != null && !load3.equals(Settings.enabled))
            {
                Settings.enabled = load3;
            }

            load = propConfig.getProperty("install-date");
            if (load != null)
            {
                load4 = Long.parseLong(load);
            }
            else
            {
                load4 = antibot.getInstalldate();
                System.out.print("AntiBot: WARNING! Could not load system install date!");
            }
            if (load != null)
            {
                antibot.setInstalldate(load4);
            }

            load = propConfig.getProperty("joins");
            if (load != null)
            {
                load2 = Integer.parseInt(load);
            }
            else
            {
                load2 = Settings.accounts;
            }
            if (load != null && load2 > 0 && !load2.equals(Settings.accounts))
            {
                Settings.accounts = load2;
                antibot.setDefaultaccounts(Settings.accounts);
            }
            try
            {
                boolean development = (antibot.getVersion().contains("-DEV"));
                if (development)
                {
                    Settings.checkupdates = false;
                }
            }
            catch (NullPointerException e)
            {
                // server startup.
            }

            System.out.print("AntiBot: Configuration Loaded Successfully!");
            reloads++;
            return true;

        }
        catch (Exception e)
        {
            System.out.print("AntiBot: FUCKIN: " + e);
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveConfig(String prp, String val)
    {
        try
        {
            Properties propConfig = new Properties();
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream(antibot.getDataFolder().getAbsolutePath() + File.separator + "c.properties"));
            propConfig.load(stream);
            propConfig.setProperty(prp, val);
            BufferedOutputStream strea2m = new BufferedOutputStream(new FileOutputStream(antibot.getDataFolder().getAbsolutePath() + File.separator + "c.properties"));
            propConfig.store(strea2m, "AntiBot V2 - The ultimate AntiSpam protection for Minecraft.");
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
