package me.freebuild.superspytx.ab.settings;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.freebuild.superspytx.ab.AntiBot;
import net.h31ix.anticheat.api.*;
import net.h31ix.anticheat.manage.CheckType;

public class SettingsCore
{

    public AntiBot antibot = null;
    public int reloads = 0;
    public Map<String, Object> config = new HashMap<String, Object>();
    public Map<String, Object> langs = new HashMap<String, Object>();

    public SettingsCore(AntiBot instance)
    {
        antibot = instance;
    }

    public void loadDefaults()
    {
        Settings.core = antibot;
        config.put("AntiBot.Main.Prefix", Settings.prefix);
        config.put("AntiBot.Main.EnableByDefault", Settings.enabled);
        config.put("AntiBot.Main.Notifications", Settings.notify);
        config.put("AntiBot.Main.OldBehavior", Settings.whiteList);
        config.put("AntiBot.Main.BanPlayers", Settings.banUsers);
        config.put("AntiBot.PermissionModes.UseOp", Settings.useOpPerms);
        config.put("AntiBot.PermissionModes.UseBukkitWhiteList", Settings.useWhiteListPerms);
        config.put("AntiBot.DelayedStart.Enabled", Settings.delayedStart);
        config.put("AntiBot.DelayedStart.Time", Settings.startdelay);
        config.put("AntiBot.Bot.Accounts", Settings.accounts);
        config.put("AntiBot.Bot.Seconds", Settings.interval);
        config.put("AntiBot.Bot.EnableMultiAccDetector", Settings.enableMultiAccs);
        config.put("AntiBot.Bot.ConnectionTime", Settings.connectFor);
        config.put("AntiBot.AntiSpam.Enabled", Settings.enableAntiSpam);
        config.put("AntiBot.AntiSpam.Amount", Settings.spamam);
        config.put("AntiBot.AntiSpam.Time", Settings.spamtime);
        config.put("AntiBot.AntiSpam.MutePlayerIfCaptchaNotEnabled", Settings.chatMute);
        config.put("AntiBot.ChatFlow.Enabled", Settings.flowEnabled);
        config.put("AntiBot.ChatFlow.Amount", Settings.overflows);
        config.put("AntiBot.ChatFlow.Time", Settings.timetooverflow);
        config.put("AntiBot.Captcha.Enabled", Settings.captchaEnabled);
        config.put("AntiBot.Captcha.Triggers.PlayerJoin", Settings.forceCaptchaOnJoin);
        config.put("AntiBot.Captcha.Triggers.ChatOverflow", Settings.forceCaptchaOnChatFlow);
        config.put("AntiBot.Captcha.Triggers.BotSpam", Settings.forceCaptchaOnBotSpam);
        config.put("AntiBot.Captcha.Triggers.MultiAccount", Settings.forceCaptchaOnMultiAcc);
        config.put("AntiBot.CountryBans.Enabled", Settings.geoIP);
        config.put("AntiBot.CountryBans.WhitelistMode", Settings.whiteListCountry);
        config.put("AntiBot.CountryBans.Countries", Arrays.asList(new String[0]));
        config.put("AntiBot.LoginDelay.Enabled", Settings.logindelayEnabled);
        config.put("AntiBot.LoginDelay.Delay", Settings.loginDelay);
        config.put("AntiBot.LoginDelay.HoldTime", Settings.loginHold);
        langs.put("AntiBot.Messages.Kick", Settings.kickMsg);
        langs.put("AntiBot.Messages.Connect", Settings.connectMsg);
        langs.put("AntiBot.Messages.CaptchaFailure", Settings.captchafail);
        langs.put("AntiBot.Messages.ChatOverflow", Settings.overflowedmessage);
        langs.put("AntiBot.Messages.CountryBan", Settings.countryBanMsg);
        langs.put("AntiBot.Messages.ConnectInvasion", Settings.connectInvasion);
        config.put("AntiBot.TouchTheseAndYouDieAHorribleDeath.SeriouslyImNotTryingToCopyMbaxter.InstallDate", System.currentTimeMillis());
        config.put("AntiBot.TouchTheseAndYouDieAHorribleDeath.SeriouslyImNotTryingToCopyMbaxter.CheckUpdates", Settings.checkupdates);
        config.put("AntiBot.TouchTheseAndYouDieAHorribleDeath.SeriouslyImNotTryingToCopyMbaxter.DebugMode", Settings.debugmode);
        config.put("AntiBot.TouchTheseAndYouDieAHorribleDeath.SeriouslyImNotTryingToCopyMbaxter.ABVersion", antibot.getVersion());

        antibot.getConfig().addDefaults(config);
        FileConfiguration lang = YamlConfiguration.loadConfiguration(new File(antibot.getDataFolder(), "language.yml"));
        lang.addDefaults(langs);
        lang.options().copyDefaults(true);
        try
        {
            lang.save(new File(antibot.getDataFolder(), "language.yml"));
        }
        catch (IOException e)
        {
        }

        antibot.getConfig().options().copyDefaults(true);
        antibot.saveConfig();
    }

    public boolean loadSettings()
    {

        System.out.print("AntiBot: Attempt to do the impossible - Eminem.");

        antibot.reloadConfig();

        FileConfiguration lang = YamlConfiguration.loadConfiguration(new File(antibot.getDataFolder(), "language.yml"));

        try
        {
            for (Entry<String, Object> oh : config.entrySet())
            {
                String conf = oh.getKey().replace("AntiBot.", "");
                Object duh = antibot.getConfig().get(oh.getKey());

                if (conf.equalsIgnoreCase("Main.prefix"))
                {
                    Settings.prefix = (String) duh;
                }
                else if (conf.equalsIgnoreCase("Main.EnableByDefault"))
                {
                    Settings.enabled = (Boolean) duh;
                }
                else if (conf.equalsIgnoreCase("Main.Notifications"))
                {
                    Settings.notify = (Boolean) duh;
                }
                else if (conf.equalsIgnoreCase("Main.OldBehavior"))
                {
                    Settings.whiteList = (Boolean) duh;
                }
                else if (conf.equalsIgnoreCase("Main.BanPlayers"))
                {
                    Settings.banUsers = (Boolean) duh;
                }
                else if (conf.equalsIgnoreCase("PermissionModes.UseOp"))
                {
                    Settings.useOpPerms = (Boolean) duh;
                }
                else if (conf.equalsIgnoreCase("PermissionModes.UseBukkitWhiteList"))
                {
                    Settings.useWhiteListPerms = (Boolean) duh;
                }
                else if (conf.equalsIgnoreCase("DelayedStart.Enabled") && reloads < 1)
                {
                    Settings.delayedStart = (Boolean) duh;
                }
                else if (conf.equalsIgnoreCase("DelayedStart.Time"))
                {
                    Settings.startdelay = Long.parseLong(Integer.toString((Integer) duh));
                }
                else if (conf.equalsIgnoreCase("Bot.Accounts"))
                {
                    Settings.accounts = (Integer) duh;
                }
                else if (conf.equalsIgnoreCase("Bot.Seconds"))
                {
                    Settings.interval = (Integer) duh;
                }
                else if (conf.equalsIgnoreCase("Bot.EnableMultiAccDetector"))
                {
                    Settings.enableMultiAccs = (Boolean) duh;
                }
                else if (conf.equalsIgnoreCase("Bot.ConnectionTime"))
                {
                    Settings.connectFor = (Integer) duh;
                }
                else if (conf.equalsIgnoreCase("AntiSpam.Enabled"))
                {
                    Settings.enableAntiSpam = (Boolean) duh;
                }
                else if (conf.equalsIgnoreCase("AntiSpam.Amount"))
                {
                    Settings.spamam = (Integer) duh;
                }
                else if (conf.equalsIgnoreCase("AntiSpam.Time"))
                {
                    Settings.spamtime = (Integer) duh;
                }
                else if (conf.equalsIgnoreCase("AntiSpam.MutePlayerIfCaptchaNotEnabled"))
                {
                    Settings.chatMute = (Boolean) duh;
                }
                else if (conf.equalsIgnoreCase("ChatFlow.Enabled"))
                {
                    Settings.flowEnabled = (Boolean) duh;
                }
                else if (conf.equalsIgnoreCase("ChatFlow.Amount"))
                {
                    Settings.overflows = (Integer) duh;
                }
                else if (conf.equalsIgnoreCase("ChatFlow.Time"))
                {
                    Settings.timetooverflow = (Integer) duh;
                }
                else if (conf.equalsIgnoreCase("Captcha.Enabled"))
                {
                    Settings.captchaEnabled = (Boolean) duh;
                }
                else if (conf.equalsIgnoreCase("Captcha.Triggers.PlayerJoin"))
                {
                    Settings.forceCaptchaOnJoin = (Boolean) duh;
                }
                else if (conf.equalsIgnoreCase("Captcha.Triggers.ChatOverflow"))
                {
                    Settings.forceCaptchaOnChatFlow = (Boolean) duh;
                }
                else if (conf.equalsIgnoreCase("Captcha.Triggers.BotSpam"))
                {
                    Settings.forceCaptchaOnBotSpam = (Boolean) duh;
                }
                else if (conf.equalsIgnoreCase("Captcha.Triggers.MultiAccount"))
                {
                    Settings.forceCaptchaOnMultiAcc = (Boolean) duh;
                }
                else if (conf.equalsIgnoreCase("CountryBans.Enabled"))
                {
                    Settings.geoIP = (Boolean) duh;
                }
                else if (conf.equalsIgnoreCase("CountryBans.WhitelistMode"))
                {
                    Settings.whiteListCountry = (Boolean) duh;
                }
                else if (conf.equalsIgnoreCase("CountryBans.Countries"))
                {
                    antibot.getDataTrack().getLoginTracker().countryBans = antibot.getConfig().getStringList(oh.getKey());
                    for(String s : antibot.getDataTrack().getLoginTracker().countryBans)
                    {
                        antibot.getUtility().getDebug().debug(s);
                    }
                }
                else if (conf.equalsIgnoreCase("LoginDelay.Enabled"))
                {
                    Settings.logindelayEnabled = (Boolean) duh;
                }
                else if (conf.equalsIgnoreCase("LoginDelay.Delay"))
                {
                    Settings.loginDelay = (Integer) duh;
                }
                else if (conf.equalsIgnoreCase("LoginDelay.HoldTime"))
                {
                    Settings.loginHold = (Integer) duh;
                }
                else if (conf.equalsIgnoreCase("TouchTheseAndYouDieAHorribleDeath.SeriouslyImNotTryingToCopyMbaxter.InstallDate"))
                {
                    antibot.setInstalldate((Long) duh);
                }
                else if (conf.equalsIgnoreCase("TouchTheseAndYouDieAHorribleDeath.SeriouslyImNotTryingToCopyMbaxter.CheckUpdates"))
                {
                    Settings.checkupdates = (Boolean) duh;
                }
                else if (conf.equalsIgnoreCase("TouchTheseAndYouDieAHorribleDeath.SeriouslyImNotTryingToCopyMbaxter.DebugMode"))
                {
                    Settings.debugmode = (Boolean) duh;
                }
                else if (conf.equalsIgnoreCase("TouchTheseAndYouDieAHorribleDeath.SeriouslyImNotTryingToCopyMbaxter.ABVersion"))
                {
                    //TODO: Configuration Updates or deprecate.
                }

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

            if (antibot.firsttime)
            {
                antibot.setInstalldate(System.currentTimeMillis());
                antibot.getConfig().set("TouchTheseAndYouDieAHorribleDeath.SeriouslyImNotTryingToCopyMbaxter.InstallDate", antibot.getInstalldate());
                antibot.firsttime = false;
            }

            // load messages
            langs.put("AntiBot.Messages.Kick", Settings.kickMsg);
            langs.put("AntiBot.Messages.Connect", Settings.connectMsg);
            langs.put("AntiBot.Messages.CaptchaFailure", Settings.captchafail);
            langs.put("AntiBot.Messages.ChatOverflow", Settings.overflowedmessage);
            langs.put("AntiBot.Messages.CountryBan", Settings.countryBanMsg);
            langs.put("AntiBot.Messages.ConnectInvasion", Settings.connectInvasion);
            try
            {
                for (Entry<String, Object> oh : langs.entrySet())
                {
                    String conf = oh.getKey().replace("AntiBot.", "");
                    String duh = lang.getString(oh.getKey());

                    if (conf.equalsIgnoreCase("Messages.Kick"))
                    {
                        Settings.kickMsg = duh;
                    }
                    else if (conf.equalsIgnoreCase("Messages.Connect"))
                    {
                        Settings.connectMsg = duh;
                    }
                    else if (conf.equalsIgnoreCase("Messages.CaptchaFailure"))
                    {
                        Settings.captchafail = duh;
                    }
                    else if (conf.equalsIgnoreCase("Messages.ChatOverflow"))
                    {
                        Settings.overflowedmessage = duh;
                    }
                    else if (conf.equalsIgnoreCase("Messages.CountryBan"))
                    {
                        Settings.countryBanMsg = duh;
                    }
                    else if (conf.equalsIgnoreCase("Messages.ConnectInvasion"))
                    {
                        Settings.connectInvasion = duh;
                    }
                }
            }
            catch (Exception e)
            {
                //fail.
            }

            try
            {
                boolean development = (antibot.getVersion().contains("-SNAPSHOT"));
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
        //TODO: Update ingame configuration changing stuff.
        return false;
    }
}
