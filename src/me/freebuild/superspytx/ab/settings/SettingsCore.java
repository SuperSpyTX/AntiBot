package me.freebuild.superspytx.ab.settings;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.freebuild.superspytx.ab.AB;
import me.freebuild.superspytx.ab.settings.Settings;
import me.freebuild.superspytx.ab.workflow.GD;

public class SettingsCore
{

    public int reloads = 0;
    public Map<String, Object> config = new HashMap<String, Object>();
    public Map<String, Object> langs = new HashMap<String, Object>();

    public void loadDefaults()
    {
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
        config.put("AntiBot.AntiSpam.StringDiffMin", Settings.spamdiffct);
        config.put("AntiBot.AntiSpam.Time", Settings.spamtime);
        config.put("AntiBot.ChatFlow.Enabled", Settings.flowEnabled);
        config.put("AntiBot.ChatFlow.Amount", Settings.overflows);
        config.put("AntiBot.ChatFlow.Time", Settings.timetooverflow);
        config.put("AntiBot.Captcha.Enabled", Settings.captchaEnabled);
        config.put("AntiBot.Captcha.MaxAttempts", Settings.captchaAttempts);
        config.put("AntiBot.Captcha.Triggers.PlayerJoin", Settings.forceCaptchaOnJoin);
        config.put("AntiBot.Captcha.Triggers.ChatOverflow", Settings.forceCaptchaOnChatFlow);
        config.put("AntiBot.Captcha.Triggers.ChatSpam", Settings.forceCaptchaOnChatSpam);
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
        langs.put("AntiBot.Messages.LoginDelay", Settings.loginDelayMsg);
        config.put("AntiBot.TouchTheseAnd.MbaxterWillComeAfterYou.AndKittiesWillDie.InstallDate", System.currentTimeMillis());
        config.put("AntiBot.TouchTheseAnd.MbaxterWillComeAfterYou.AndKittiesWillDie.CheckUpdates", Settings.checkupdates);
        config.put("AntiBot.TouchTheseAnd.MbaxterWillComeAfterYou.AndKittiesWillDie.DebugMode", Settings.debugmode);
       // config.put("AntiBot.TouchTheseAnd.MbaxterWillComeAfterYou.AndKittiesWillDie.ABVersion", AB.getInstance().getVersion());

        AB.getInstance().getConfig().addDefaults(config);
        FileConfiguration lang = YamlConfiguration.loadConfiguration(new File(AB.getInstance().getDataFolder(), "language.yml"));
        lang.addDefaults(langs);
        lang.options().copyDefaults(true);
        try {lang.save(new File(AB.getInstance().getDataFolder(), "language.yml"));}catch (IOException e){ AB.log("Failed to save language configuration."); e.printStackTrace(); } // OMG SO PRO!
        AB.getInstance().getConfig().options().copyDefaults(true);
        AB.getInstance().saveConfig();
    }

    public boolean loadSettings()
    {

        AB.log("AntiBot: Attempt to do the impossible - Eminem.");

        AB.getInstance().reloadConfig();

        FileConfiguration lang = YamlConfiguration.loadConfiguration(new File(AB.getInstance().getDataFolder(), "language.yml"));

        try
        {
            for (Entry<String, Object> oh : config.entrySet())
            {
                String conf = oh.getKey().replace("AntiBot.", "");
                Object duh = AB.getInstance().getConfig().get(oh.getKey());

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
                else if (conf.equalsIgnoreCase("AntiSpam.StringDiffMin"))
                {
                    Settings.spamdiffct = (Integer) duh;
                }
                else if (conf.equalsIgnoreCase("AntiSpam.Time"))
                {
                    Settings.spamtime = (Integer) duh;
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
                else if (conf.equalsIgnoreCase("Captcha.MaxAttempts"))
                {
                    Settings.captchaAttempts = (Integer) duh;
                }
                else if (conf.equalsIgnoreCase("Captcha.Triggers.PlayerJoin"))
                {
                    Settings.forceCaptchaOnJoin = (Boolean) duh;
                }
                else if (conf.equalsIgnoreCase("Captcha.Triggers.ChatOverflow"))
                {
                    Settings.forceCaptchaOnChatFlow = (Boolean) duh;
                }
                else if (conf.equalsIgnoreCase("Captcha.Triggers.ChatSpam"))
                {
                    Settings.forceCaptchaOnChatSpam = (Boolean) duh;
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
                    GD.cb_cds = AB.getInstance().getConfig().getStringList(oh.getKey());
                  /*  AB.getInstance().getDataTrack().getLoginTracker().countryBans = AB.getInstance().getConfig().getStringList(oh.getKey());
                    for(String s : AB.getInstance().getDataTrack().getLoginTracker().countryBans)
                    {
                        AB.getInstance().getUtility().getDebug().debug(s);
                    } */
                }
                else if (conf.equalsIgnoreCase("LoginDelay.Enabled"))
                {
                    Settings.logindelayEnabled = (Boolean) duh;
                }
                else if (conf.equalsIgnoreCase("LoginDelay.Delay"))
                {
                    Settings.loginDelay = (Long) duh;
                }
                else if (conf.equalsIgnoreCase("LoginDelay.HoldTime"))
                {
                    Settings.loginHold = (Long) duh;
                }
                else if (conf.equalsIgnoreCase("TouchTheseAnd.MbaxterWillComeAfterYou.AndKittiesWillDie.InstallDate"))
                {
                    GD.setInstallDate((Long) duh);
                }
                else if (conf.equalsIgnoreCase("TouchTheseAnd.MbaxterWillComeAfterYou.AndKittiesWillDie.CheckUpdates"))
                {
                    Settings.checkupdates = (Boolean) duh;
                }
                else if (conf.equalsIgnoreCase("TouchTheseAnd.MbaxterWillComeAfterYou.AndKittiesWillDie.DebugMode"))
                {
                    Settings.debugmode = (Boolean) duh;
                }
                else if (conf.equalsIgnoreCase("TouchTheseAnd.MbaxterWillComeAfterYou.AndKittiesWillDie.ABVersion"))
                {
                    //TODO: Configuration Updates or deprecate.
                }

            }

            // we need to disable AntiCheat's anti spam because AntiBot has one
            // already.... or does it?
            // or do we?
            // Thanks H31IX for approving AntiBot <3 -- lul
            AB.getInstance().getServer().getScheduler().scheduleAsyncDelayedTask(AB.getInstance(), new Runnable()
            {

                @Override
                public void run()
                {
                    if (AB.getInstance().getServer().getPluginManager().getPlugin("AntiCheat") != null)
                    {
                        if (Settings.enableAntiSpam)
                        {
                            net.h31ix.anticheat.api.AnticheatAPI.deactivateCheck(net.h31ix.anticheat.manage.CheckType.SPAM);
                        }
                        else
                        {
                        	net.h31ix.anticheat.api.AnticheatAPI.activateCheck(net.h31ix.anticheat.manage.CheckType.SPAM);
                        }
                    }
                }

            }, 600L); //start in 30 seconds.

           /* if (AB.getInstance().firsttime)
            {
                AB.getInstance().setInstalldate(System.currentTimeMillis());
                AB.getInstance().getConfig().set("TouchTheseAnd.MbaxterWillComeAfterYou.AndKittiesWillDie.InstallDate", AB.getInstance().getInstalldate());
                AB.getInstance().firsttime = false;
            } */

            // load messages
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
                    else if (conf.equalsIgnoreCase("Messages.LoginDelay"))
                    {
                    	Settings.loginDelayMsg = duh;
                    }
                }
            }
            catch (Exception e)
            {
                //fail.
            }

            AB.log("AntiBot: Configuration Loaded Successfully!");
            reloads++;
            return true;

        }
        catch (Exception e)
        {
            AB.log("AntiBot: " + e);
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
