package somebody.is.madbro.settings;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import somebody.is.madbro.AntiBotCore;

public class SettingsCore {

	public AntiBotCore antibot = null;

	public SettingsCore(AntiBotCore instance) {
		antibot = instance;
	}

	public void saveSettings(File dataFolder) {

		File Config = new File(dataFolder.getAbsolutePath() + File.separator
				+ "c.properties");

		if (!Config.exists()) {
			System.out.print("AntiBot: Configuration is missing, creating...");
			try {
				Config.createNewFile();
				Properties propConfig = new Properties();
				propConfig.setProperty("connect-message", Settings.connectMsg);
				propConfig.setProperty("kick-message", Settings.kickMsg);
				propConfig.setProperty("prefix", Settings.prefix);
				propConfig.setProperty("countryban-message",
						Settings.countryBanMsg);
				propConfig.setProperty("countrybans", "");
				propConfig.setProperty("connect-join-invasion",
						Settings.connectInvasion);
				propConfig.setProperty("joins-sec",
						Integer.toString(Settings.interval));
				propConfig.setProperty("whitelist-perms",
						Boolean.toString(Settings.useWhiteListPerms));
				propConfig.setProperty("op-perms",
						Boolean.toString(Settings.useOpPerms));
				propConfig.setProperty("orgy-notify",
						Boolean.toString(Settings.notify));
				propConfig.setProperty("country-whitelist-mode",
						Boolean.toString(Settings.whiteListCountry));
				propConfig.setProperty("debug-mode",
						Boolean.toString(Settings.debugmode));
				propConfig.setProperty("enable-by-default",
						Boolean.toString(Settings.enabled));
				propConfig.setProperty("joins",
						Integer.toString(Settings.accounts));
				propConfig.setProperty("enable-antispam",
						Boolean.toString(Settings.enableAntiSpam));
				propConfig.setProperty("enable-multiacc-detection",
						Boolean.toString(Settings.enableMultiAccs));
				propConfig.setProperty("chat-mute",
						Boolean.toString(Settings.chatMute));
				propConfig.setProperty("whitelist-when-triggered",
						Boolean.toString(Settings.whiteList));
				propConfig.setProperty("spam-time",
						Integer.toString(Settings.spamtime));
				propConfig.setProperty("spam-amount",
						Integer.toString(Settings.spamam));
				propConfig.setProperty("connection-time",
						Integer.toString(Settings.connectFor));
				propConfig.setProperty("ban-users",
						Boolean.toString(Settings.banUsers));
				propConfig.setProperty("enable-geoip",
						Boolean.toString(Settings.geoIP));
				propConfig.setProperty("install-date",
						Long.toString(System.currentTimeMillis()));
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(Config.getAbsolutePath()));
				propConfig
						.store(stream,
								"AntiBot V2 - The ultimate AntiSpam protection for Minecraft.");
			} catch (IOException ex) {
				System.out.println("AntiBot: Configuration creation failed.");
			}

		}
	}

	public boolean loadSettings(File dataFolder1) {

		System.out.print("AntiBot: Attempt to do the impossible - Eminem.");
		try {
			Properties propConfig = new Properties();
			BufferedInputStream stream = new BufferedInputStream(
					new FileInputStream(dataFolder1.getAbsolutePath()
							+ File.separator + "c.properties"));
			propConfig.load(stream);
			String load = null;
			Integer load2 = null;
			Boolean load3 = null;
			Long load4 = null;

			load = propConfig.getProperty("connect-message");
			if (load != null && load != Settings.connectMsg) {
				Settings.connectMsg = load;
			}

			load = propConfig.getProperty("kick-message");
			if (load != null && load != Settings.kickMsg) {
				Settings.kickMsg = load;
			}
			
			load = propConfig.getProperty("countryban-message");
			if (load != null && load != Settings.countryBanMsg) {
				Settings.countryBanMsg = load;
			}
			
			load = propConfig.getProperty("countrybans");
			if (load != null) {
				antibot.getUtility().getGeoIP().loadCountryBanList(load);
			}

			load = propConfig.getProperty("connect-join-invasion");
			if (load != null && load != Settings.connectInvasion) {
				Settings.connectInvasion = load;
			}

			load = propConfig.getProperty("prefix");
			if (load != null && load != Settings.prefix) {
				Settings.prefix = load;
			}

			load = propConfig.getProperty("joins-sec");
			if (load != null) {
				load2 = Integer.parseInt(load);
			} else {
				load2 = Settings.interval;
			}
			if (load != null && load2 > 999 && !load2.equals(Settings.interval)) {
				Settings.interval = load2;
				antibot.setDefaultinterval(Settings.interval);
			}

			load = propConfig.getProperty("whitelist-perms");
			if (load != null) {
				load3 = Boolean.parseBoolean(load);
			} else {
				load3 = Settings.useWhiteListPerms;
			}
			if (load != null && !load3.equals(Settings.useWhiteListPerms)) {
				Settings.useWhiteListPerms = load3;
			}

			load = propConfig.getProperty("op-perms");
			if (load != null) {
				load3 = Boolean.parseBoolean(load);
			} else {
				load3 = Settings.useOpPerms;
			}
			if (load != null && !load3.equals(Settings.useOpPerms)) {
				Settings.useOpPerms = load3;
			}

			load = propConfig.getProperty("whitelist-when-triggered");
			if (load != null) {
				load3 = Boolean.parseBoolean(load);
			} else {
				load3 = Settings.whiteList;
			}
			if (load != null && !load3.equals(Settings.whiteList)) {
				Settings.whiteList = load3;
			}

			load = propConfig.getProperty("orgy-notify");
			if (load != null) {
				load3 = Boolean.parseBoolean(load);
			} else {
				load3 = Settings.notify;
			}
			if (load != null && !load3.equals(Settings.notify)) {
				Settings.notify = load3;
			}
			
			load = propConfig.getProperty("country-whitelist-mode");
			if (load != null) {
				load3 = Boolean.parseBoolean(load);
			} else {
				load3 = Settings.whiteListCountry;
			}
			if (load != null && !load3.equals(Settings.whiteListCountry)) {
				Settings.whiteListCountry = load3;
			}

			load = propConfig.getProperty("ban-users");
			if (load != null) {
				load3 = Boolean.parseBoolean(load);
			} else {
				load3 = Settings.banUsers;
			}
			if (load != null && !load3.equals(Settings.banUsers)) {
				Settings.banUsers = load3;
			}
			
			load = propConfig.getProperty("enable-geoip");
			if (load != null) {
				load3 = Boolean.parseBoolean(load);
			} else {
				load3 = Settings.geoIP;
			}
			if (load != null && !load3.equals(Settings.geoIP)) {
				Settings.geoIP = load3;
			}

			load = propConfig.getProperty("enable-antispam");
			if (load != null) {
				load3 = Boolean.parseBoolean(load);
			} else {
				load3 = Settings.enableAntiSpam;
			}
			if (load != null && !load3.equals(Settings.enableAntiSpam)) {
				Settings.enableAntiSpam = load3;
			}

			load = propConfig.getProperty("enable-multiacc-detection");
			if (load != null) {
				load3 = Boolean.parseBoolean(load);
			} else {
				load3 = Settings.enableMultiAccs;
			}
			if (load != null && !load3.equals(Settings.enableMultiAccs)) {
				Settings.enableMultiAccs = load3;
			}

			load = propConfig.getProperty("chat-mute");
			if (load != null) {
				load3 = Boolean.parseBoolean(load);
			} else {
				load3 = Settings.chatMute;
			}
			if (load != null && !load3.equals(Settings.chatMute)) {
				Settings.chatMute = load3;
			}

			load = propConfig.getProperty("debug-mode");
			if (load != null) {
				load3 = Boolean.parseBoolean(load);
			} else {
				load3 = Settings.debugmode;
			}
			if (load != null && !load3.equals(Settings.debugmode)) {
				Settings.debugmode = load3;
				if (Settings.debugmode)
					System.out
							.print("AntiBot: WARNING! You're in Debug Mode! Do not use this on a live environment!");
			}

			load = propConfig.getProperty("spam-amount");
			if (load != null) {
				load2 = Integer.parseInt(load);
			} else {
				load2 = Settings.spamam;
			}
			if (load != null && !load2.equals(Settings.spamam)) {
				Settings.spamam = load2;
			}

			load = propConfig.getProperty("connection-time");
			if (load != null) {
				load2 = Integer.parseInt(load);
			} else {
				load2 = Settings.connectFor;
			}
			if (load != null && !load2.equals(Settings.connectFor)) {
				Settings.connectFor = load2;
			}

			load = propConfig.getProperty("spam-time");
			if (load != null) {
				load2 = Integer.parseInt(load);
			} else {
				load2 = Settings.spamtime;
			}
			if (load != null && !load2.equals(Settings.spamtime)) {
				Settings.spamtime = load2;
			}

			load = propConfig.getProperty("enable-by-default");
			if (load != null) {
				load3 = Boolean.parseBoolean(load);
			} else {
				load3 = Settings.enabled;
			}
			if (load != null && !load3.equals(Settings.enabled)) {
				Settings.enabled = load3;
			}

			load = propConfig.getProperty("install-date");
			if (load != null) {
				load4 = Long.parseLong(load);
			} else {
				load4 = antibot.getInstalldate();
				System.out
						.print("AntiBot: WARNING! Could not load system install date!");
			}
			if (load != null) {
				antibot.setInstalldate(load4);
			}

			load = propConfig.getProperty("joins");
			if (load != null) {
				load2 = Integer.parseInt(load);
			} else {
				load2 = Settings.accounts;
			}
			if (load != null && load2 > 0 && !load2.equals(Settings.accounts)) {
				Settings.accounts = load2;
				antibot.setDefaultaccounts(Settings.accounts);
			}

			System.out.print("AntiBot: Configuration Loaded Successfully!");
			return true;

		} catch (Exception e) {
			System.out.print("AntiBot: FUCKIN: " + e);
			return false;
		}
	}

	public boolean saveConfig(String prp, String val) {
		try {
			Properties propConfig = new Properties();
			BufferedInputStream stream = new BufferedInputStream(
					new FileInputStream(antibot.getDataFolder()
							.getAbsolutePath()
							+ File.separator
							+ "c.properties"));
			propConfig.load(stream);
			propConfig.setProperty(prp, val);
			BufferedOutputStream strea2m = new BufferedOutputStream(
					new FileOutputStream(antibot.getDataFolder()
							.getAbsolutePath()
							+ File.separator
							+ "c.properties"));
			propConfig
					.store(strea2m,
							"AntiBot V2 - The ultimate AntiSpam protection for Minecraft.");
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
