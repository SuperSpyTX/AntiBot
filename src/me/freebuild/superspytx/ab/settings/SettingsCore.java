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

public class SettingsCore {
	
	public int reloads = 0;
	public Map<String, Object> config = new HashMap<String, Object>();
	public Map<String, Object> langs = new HashMap<String, Object>();
	
	public void loadDefaults() {
		/* Main Configuration */
		config.put("AntiBot.Main.Prefix", Language.prefix);
		config.put("AntiBot.Main.EnableByDefault", Settings.enabled);
		config.put("AntiBot.Main.Notifications", Settings.notify);
		config.put("AntiBot.DelayedStart.Enabled", Settings.delayedStart);
		config.put("AntiBot.DelayedStart.Time", Settings.startdelay);
		config.put("AntiBot.Bot.Accounts", Settings.accounts);
		config.put("AntiBot.Bot.Seconds", Settings.interval);
		config.put("AntiBot.Bot.ConnectionTime", Settings.connectFor);
		config.put("AntiBot.AntiSpam.Enabled", Settings.enableAntiSpam);
		config.put("AntiBot.AntiSpam.Amount", Settings.spamam);
		config.put("AntiBot.AntiSpam.StringDiffMin", Settings.spamdiffct);
		config.put("AntiBot.AntiSpam.Time", Settings.spamtime);
		config.put("AntiBot.ChatFlow.Enabled", Settings.flowEnabled);
		config.put("AntiBot.ChatFlow.Kicking", Settings.flowKicks);
		config.put("AntiBot.ChatFlow.Amount", Settings.overflows);
		config.put("AntiBot.ChatFlow.Time", Settings.timetooverflow);
		config.put("AntiBot.Captcha.Enabled", Settings.captchaEnabled);
		config.put("AntiBot.Captcha.MaxAttempts", Settings.captchaAttempts);
		config.put("AntiBot.Captcha.IdleKicks", Settings.captchaIdleKicks);
		config.put("AntiBot.Captcha.Triggers.ChatOverflow", Settings.forceCaptchaOnChatFlow);
		config.put("AntiBot.Captcha.Triggers.ChatSpam", Settings.forceCaptchaOnChatSpam);
		config.put("AntiBot.Captcha.Triggers.BotSpam", Settings.forceCaptchaOnBotSpam);
		config.put("AntiBot.CountryBans.Enabled", Settings.geoIP);
		config.put("AntiBot.CountryBans.WhitelistMode", Settings.whiteListCountry);
		config.put("AntiBot.CountryBans.Countries", Arrays.asList(new String[0]));
		config.put("AntiBot.LoginDelay.Enabled", Settings.logindelayEnabled);
		config.put("AntiBot.LoginDelay.Delay", Settings.loginDelay);
		config.put("AntiBot.LoginDelay.HoldTime", Settings.loginHold);
		config.put("AntiBot.TouchTheseAnd.AWildTnTWillAppearInYourCode.AndItWillSuck.InstallDate", Settings.installdate);
		config.put("AntiBot.TouchTheseAnd.AWildTnTWillAppearInYourCode.AndItWillSuck.CheckUpdates", Settings.checkupdates);
		config.put("AntiBot.TouchTheseAnd.AWildTnTWillAppearInYourCode.AndItWillSuck.DebugMode", Settings.debugmode);
		config.put("AntiBot.TouchTheseAnd.AWildTnTWillAppearInYourCode.AndItWillSuck.ABVersion", AB.getVersion());
		
		/* Language Configuration */
		langs.put("AntiBot.Messages.Kick", Language.kickMsg);
		langs.put("AntiBot.Messages.Captcha.Kick", Language.captchaKick);
		langs.put("AntiBot.Messages.Captcha.OneAttemptLeft", Language.captoneLeft);
		langs.put("AntiBot.Messages.Captcha.AttemptsLeft", Language.captattemptsLeft);
		langs.put("AntiBot.Messages.ChatOverflow", Language.overflowedMessage);
		langs.put("AntiBot.Messages.CountryBan", Language.countryBanMsg);
		langs.put("AntiBot.Messages.LoginDelay", Language.loginDelayMsg);
		langs.put("AntiBot.Messages.Admins.DSNotify", Language.adminDSNotify);
		langs.put("AntiBot.Messages.Admins.NVNotify", Language.adminNVNotify);
		langs.put("AntiBot.Messages.Admins.NBNotify", Language.adminNBNotify);
		
		AB.getInstance().getConfig().addDefaults(config);
		FileConfiguration lang = YamlConfiguration.loadConfiguration(new File(AB.getInstance().getDataFolder(), "language.yml"));
		lang.addDefaults(langs);
		lang.options().copyDefaults(true);
		try {
			lang.save(new File(AB.getInstance().getDataFolder(), "language.yml"));
		} catch (IOException e) {
			AB.log("Failed to save language configuration.");
			e.printStackTrace();
		} // OMG SO PRO!
		AB.getInstance().getConfig().options().copyDefaults(true);
		AB.getInstance().saveConfig();
	}
	
	public boolean loadSettings() {
		
		AB.log("Attempt to do the impossible - Eminem.");
		
		AB.getInstance().reloadConfig();
		
		FileConfiguration lang = YamlConfiguration.loadConfiguration(new File(AB.getInstance().getDataFolder(), "language.yml"));
		
		try {
			for (Entry<String, Object> oh : config.entrySet()) {
				String conf = oh.getKey().replace("AntiBot.", "");
				Object duh = AB.getInstance().getConfig().get(oh.getKey());
				
				if (conf.equalsIgnoreCase("Main.prefix")) {
					Language.prefix = (String) duh;
				} else if (conf.equalsIgnoreCase("Main.EnableByDefault") && !Settings.delayingStart) {
					Settings.enabled = (Boolean) duh;
				} else if (conf.equalsIgnoreCase("Main.Notifications")) {
					Settings.notify = (Boolean) duh;
				} else if (conf.equalsIgnoreCase("DelayedStart.Enabled") && reloads < 1) {
					Settings.delayedStart = (Boolean) duh;
				} else if (conf.equalsIgnoreCase("DelayedStart.Time")) {
					Settings.startdelay = Long.parseLong(Integer.toString((Integer) duh));
				} else if (conf.equalsIgnoreCase("Bot.Accounts")) {
					Settings.accounts = (Integer) duh;
				} else if (conf.equalsIgnoreCase("Bot.Seconds")) {
					Settings.interval = (Integer) duh;
				} else if (conf.equalsIgnoreCase("Bot.ConnectionTime")) {
					Settings.connectFor = (Integer) duh;
				} else if (conf.equalsIgnoreCase("AntiSpam.Enabled")) {
					Settings.enableAntiSpam = (Boolean) duh;
				} else if (conf.equalsIgnoreCase("AntiSpam.Amount")) {
					Settings.spamam = (Integer) duh;
				} else if (conf.equalsIgnoreCase("AntiSpam.StringDiffMin")) {
					Settings.spamdiffct = (Integer) duh;
				} else if (conf.equalsIgnoreCase("AntiSpam.Time")) {
					Settings.spamtime = (Integer) duh;
				} else if (conf.equalsIgnoreCase("ChatFlow.Enabled")) {
					Settings.flowEnabled = (Boolean) duh;
				} else if (conf.equalsIgnoreCase("ChatFlow.Kicking")) {
					Settings.flowKicks = (Boolean) duh;
				} else if (conf.equalsIgnoreCase("ChatFlow.Amount")) {
					Settings.overflows = (Integer) duh;
				} else if (conf.equalsIgnoreCase("ChatFlow.Time")) {
					Settings.timetooverflow = (Integer) duh;
				} else if (conf.equalsIgnoreCase("Captcha.Enabled")) {
					Settings.captchaEnabled = (Boolean) duh;
				} else if (conf.equalsIgnoreCase("Captcha.MaxAttempts")) {
					Settings.captchaAttempts = (Integer) duh;
				} else if (conf.equalsIgnoreCase("Captcha.IdleKicks")) {
					Settings.captchaIdleKicks = (Boolean) duh;
				} else if (conf.equalsIgnoreCase("Captcha.Triggers.ChatOverflow")) {
					Settings.forceCaptchaOnChatFlow = (Boolean) duh;
				} else if (conf.equalsIgnoreCase("Captcha.Triggers.ChatSpam")) {
					Settings.forceCaptchaOnChatSpam = (Boolean) duh;
				} else if (conf.equalsIgnoreCase("Captcha.Triggers.BotSpam")) {
					Settings.forceCaptchaOnBotSpam = (Boolean) duh;
				} else if (conf.equalsIgnoreCase("CountryBans.Enabled")) {
					Settings.geoIP = (Boolean) duh;
				} else if (conf.equalsIgnoreCase("CountryBans.WhitelistMode")) {
					Settings.whiteListCountry = (Boolean) duh;
				} else if (conf.equalsIgnoreCase("CountryBans.Countries")) {
					GD.cb_cds = AB.getInstance().getConfig().getStringList(oh.getKey());
					/*  AB.getInstance().getDataTrack().getLoginTracker().countryBans = AB.getInstance().getConfig().getStringList(oh.getKey());
					  for(String s : AB.getInstance().getDataTrack().getLoginTracker().countryBans)
					  {
					      AB.getInstance().getUtility().getDebug().debug(s);
					  } */
				} else if (conf.equalsIgnoreCase("LoginDelay.Enabled")) {
					Settings.logindelayEnabled = (Boolean) duh;
				} else if (conf.equalsIgnoreCase("LoginDelay.Delay")) {
					Settings.loginDelay = (Integer) duh;
				} else if (conf.equalsIgnoreCase("LoginDelay.HoldTime")) {
					Settings.loginHold = (Integer) duh;
				} else if (conf.equalsIgnoreCase("TouchTheseAnd.AWildTnTWillAppearInYourCode.AndItWillSuck.InstallDate")) {
					try {
						Settings.installdate = ((Long) duh);
					} catch (Exception e) {
						Settings.installdate = ((Integer) duh);
					}
				} else if (conf.equalsIgnoreCase("TouchTheseAnd.AWildTnTWillAppearInYourCode.AndItWillSuck.CheckUpdates")) {
					Settings.checkupdates = (Boolean) duh;
				} else if (conf.equalsIgnoreCase("TouchTheseAnd.AWildTnTWillAppearInYourCode.AndItWillSuck.DebugMode")) {
					Settings.debugmode = (Boolean) duh;
				} else if (conf.equalsIgnoreCase("TouchTheseAnd.AWildTnTWillAppearInYourCode.AndItWillSuck.ABVersion")) {
					// TODO: Configuration Updates or deprecate.
				}
				
			}
			
			// we need to disable AntiCheat's anti spam because AntiBot has one
			// already.... or does it?
			// or do we?
			// Thanks H31IX for approving AntiBot <3 -- lul
			AB.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(AB.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					if (AB.getInstance().getServer().getPluginManager().getPlugin("AntiCheat") != null) {
						try {
							if (Settings.enableAntiSpam) {
								net.h31ix.anticheat.api.AnticheatAPI.deactivateCheck(net.h31ix.anticheat.manage.CheckType.SPAM);
							} else {
								net.h31ix.anticheat.api.AnticheatAPI.activateCheck(net.h31ix.anticheat.manage.CheckType.SPAM);
							}
						} catch (Throwable e) {
							// Sounds like a different plugin.
						}
					}
				}
				
			}, 600L); // start in 30 seconds.
			
			if (Settings.installdate < 1000L) {
				Settings.installdate = System.currentTimeMillis();
				AB.getInstance().getConfig().set("AntiBot.TouchTheseAnd.AWildTnTWillAppearInYourCode.AndItWillSuck.InstallDate", Settings.installdate);
				AB.getInstance().saveConfig();
			}
			
			// load messages
			try {
				for (Entry<String, Object> oh : langs.entrySet()) {
					String conf = oh.getKey().replace("AntiBot.", "");
					String duh = lang.getString(oh.getKey());
					
					if (conf.equalsIgnoreCase("Messages.Kick")) {
						Language.kickMsg = duh;
					} else if (conf.equalsIgnoreCase("Messages.Captcha.Kick")) {
						Language.captchaKick = duh;
					} else if (conf.equalsIgnoreCase("Messages.Captcha.OneAttemptLeft")) {
						Language.captoneLeft = duh;
					} else if (conf.equalsIgnoreCase("Messages.Captcha.AttemptsLeft")) {
						Language.captattemptsLeft = duh;
					} else if (conf.equalsIgnoreCase("Messages.ChatOverflow")) {
						Language.overflowedMessage = duh;
					} else if (conf.equalsIgnoreCase("Messages.CountryBan")) {
						Language.countryBanMsg = duh;
					} else if (conf.equalsIgnoreCase("Messages.LoginDelay")) {
						Language.loginDelayMsg = duh;
					} else if (conf.equalsIgnoreCase("Messages.Admins.DSNotify")) {
						Language.adminDSNotify = duh;
					} else if (conf.equalsIgnoreCase("Messages.Admins.NVNotify")) {
						Language.adminNVNotify = duh;
					} else if (conf.equalsIgnoreCase("Messages.Admins.NBNotify")) {
						Language.adminNBNotify = duh;
					}
					
				}
			} catch (Exception e) {
				// fail.
			}
			
			AB.log("Configuration Loaded Successfully!");
			reloads++;
			return true;
			
		} catch (Exception e) {
			AB.log("Exception while loading config!: " + e);
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean saveConfig(String prp, String val) {
		// TODO: Update ingame configuration changing stuff.
		return false;
	}
}
