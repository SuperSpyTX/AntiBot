package somebody.is.mad;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiBot extends JavaPlugin {

	private final BotListener botlistener = new BotListener(this);
	private File dataFolder;
	private long installdate;
	private Date install;
	public int defaultinterval;
	public int defaultaccounts;
	public HashMap<String, String> iplist = new HashMap<String, String>();

	public void onEnable() {

		// Configuration.
		dataFolder = getDataFolder();
		if (!dataFolder.exists()) {
			System.out.print("AntiBot: Missing Folder. Creating..");
			dataFolder.mkdir();
		}

		File Config = new File(dataFolder.getAbsolutePath() + File.separator
				+ "c.properties");

		if (!Config.exists()) {
			System.out.print("AntiBot: Configuration is missing, creating...");
			try {
				Config.createNewFile();
				Properties propConfig = new Properties();
				propConfig.setProperty("connect-message",
						botlistener.connectMsg);
				propConfig.setProperty("kick-message", botlistener.kickMsg);
				propConfig.setProperty("connect-join-invasion",
						botlistener.connectInvasion);
				propConfig.setProperty("joins-sec",
						Integer.toString(botlistener.interval));
				propConfig.setProperty("whitelist-perms",
						Boolean.toString(botlistener.useWhiteListPerms));
				propConfig.setProperty("op-perms",
						Boolean.toString(botlistener.useOpPerms));
				propConfig.setProperty("orgy-notify",
						Boolean.toString(botlistener.notify));
				propConfig.setProperty("debug-mode",
						Boolean.toString(botlistener.debugmode));
				propConfig.setProperty("enable-by-default",
						Boolean.toString(botlistener.enabled));
				propConfig.setProperty("joins",
						Integer.toString(botlistener.accounts));
				propConfig.setProperty("whitelist-when-triggered",
						Boolean.toString(botlistener.whiteList));
				propConfig.setProperty("spam-time",
						Integer.toString(botlistener.spamtime));
				propConfig.setProperty("spam-amount",
						Integer.toString(botlistener.spamam));
				propConfig.setProperty("silent-chat-kick",
						Boolean.toString(botlistener.silentChatKick));
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

		loadSekritTools();
		getServer().getPluginManager().registerEvents(botlistener, this);
		PluginDescriptionFile pdfFile = getDescription();
		System.out.println(pdfFile.getName() + " version "
				+ pdfFile.getVersion() + " is enabled!");

	}

	public void returnMotd(CommandSender sender) {
		install = new Date(installdate);
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
		sender.sendMessage("\247f[\247bAntiBot\247f] "
				+ "AntiBot 2.3 - Coded By .SuPaH sPii");
		sender.sendMessage("\247f[\247bAntiBot\247f] "
				+ "Inspired by Wolflink289 <3");
		sender.sendMessage("\247f[\247bAntiBot\247f] "
				+ "Continued inspiration by Evenprime & Fafaffy <3");
		Random random = new Random();
		switch (random.nextInt(20)) {
		case 0:
			sender.sendMessage("\247f[\247bAntiBot\247f] "
					+ "System Installed on " + ChatColor.GREEN
					+ sdf.format(install));
			break;
		case 1:
			sender.sendMessage("\247f[\247bAntiBot\247f] "
					+ "Keeping Pwnage out of game since " + ChatColor.GREEN
					+ sdf.format(install));
			break;
		case 2:
			sender.sendMessage("\247f[\247bAntiBot\247f] "
					+ "Combatting spam since " + ChatColor.GREEN
					+ sdf.format(install));
			break;
		case 3:
			sender.sendMessage("\247f[\247bAntiBot\247f] "
					+ "Supporting PWN4G3 Bots since " + ChatColor.GREEN
					+ sdf.format(install));
			break;
		case 4:
			sender.sendMessage("\247f[\247bAntiBot\247f] "
					+ "Running PWN4G3 Bots to the void since "
					+ ChatColor.GREEN + sdf.format(install));
			break;
		case 5:
			sender.sendMessage("\247f[\247bAntiBot\247f] "
					+ "Making people mad since " + ChatColor.GREEN
					+ sdf.format(install));
			break;
		case 6:
			sender.sendMessage("\247f[\247bAntiBot\247f] "
					+ "Trolling spammers since " + ChatColor.GREEN
					+ sdf.format(install));
			break;
		case 7:
			sender.sendMessage("\247f[\247bAntiBot\247f] "
					+ "Supporting Wolflink289's idea since " + ChatColor.GREEN
					+ sdf.format(install));
			break;
		case 8:
			sender.sendMessage("\247f[\247bAntiBot\247f] "
					+ "Protecting this server since " + ChatColor.GREEN
					+ sdf.format(install));
			break;
		case 9:
			sender.sendMessage("\247f[\247bAntiBot\247f] "
					+ "All lights turned green since " + ChatColor.GREEN
					+ sdf.format(install));
			break;
		case 10:
			sender.sendMessage("\247f[\247bAntiBot\247f] "
					+ "Corrupting ability to spam since " + ChatColor.GREEN
					+ sdf.format(install));
			break;
		case 11:
			sender.sendMessage("\247f[\247bAntiBot\247f] "
					+ "Minecraft PWN4G3 Dun goof on " + ChatColor.GREEN
					+ sdf.format(install));
			break;
		case 12:
			sender.sendMessage("\247f[\247bAntiBot\247f] "
					+ "Making .SuPaH sPii proud since " + ChatColor.GREEN
					+ sdf.format(install));
			break;
		case 13:
			sender.sendMessage("\247f[\247bAntiBot\247f] "
					+ "Injected the Vaccine on " + ChatColor.GREEN
					+ sdf.format(install));
			break;
		case 14:
			sender.sendMessage("\247f[\247bAntiBot\247f] "
					+ "Giving AIDS to spammers since " + ChatColor.GREEN
					+ sdf.format(install));
			break;
		case 15:
			sender.sendMessage("\247f[\247bAntiBot\247f] "
					+ "Turning spammer users to WTF faces since "
					+ ChatColor.GREEN + sdf.format(install));
			break;
		case 16:
			sender.sendMessage("\247f[\247bAntiBot\247f] "
					+ "Chinese secret happened on " + ChatColor.GREEN
					+ sdf.format(install));
			break;
		case 17:
			sender.sendMessage("\247f[\247bAntiBot\247f] "
					+ "Been in Slim Shady's world since " + ChatColor.GREEN
					+ sdf.format(install));
			break;
		case 18:
			sender.sendMessage("\247f[\247bAntiBot\247f] "
					+ "Making other communities jelly since " + ChatColor.GREEN
					+ sdf.format(install));
			break;
		case 19:
			sender.sendMessage("\247f[\247bAntiBot\247f] "
					+ "Didn't have to buy anything since " + ChatColor.GREEN
					+ sdf.format(install));
			break;
		case 20:
			sender.sendMessage("\247f[\247bAntiBot\247f] "
					+ "Making Reanimation shit his pants since "
					+ ChatColor.GREEN + sdf.format(install));
			break;
		default:
			sender.sendMessage("\247f[\247bAntiBot\247f] "
					+ "System Installed on " + ChatColor.GREEN
					+ sdf.format(install));
			break;
		}
	}

	public void noPermission(CommandSender sender) {
		sender.sendMessage("\247f[\247bAntiBot\247f] "
				+ "\247cSorry, you don't have privileges.");
	}

	public boolean ownPermission(String perm, Player player, Integer level) {
		if (player.hasPermission(perm)) {
			return true;
		}
		if (player.isOp() && botlistener.useOpPerms) {
			return true;
		}
		if (player.hasPermission("AntiBot.admin")) {
			return true;
		}
		if (player.hasPermission("AntiBot.admin.root") && level <= 3) {
			return true;
		}
		if (player.hasPermission("AntiBot.admin.plus") && level <= 2) {
			return true;
		}
		if (player.hasPermission("AntiBot.admin.basic") && level <= 1) {
			return true;
		}

		return false;

	}

	public boolean saveConfig(String prp, String val) {
		try {
			Properties propConfig = new Properties();
			BufferedInputStream stream = new BufferedInputStream(
					new FileInputStream(dataFolder.getAbsolutePath()
							+ File.separator + "c.properties"));
			propConfig.load(stream);
			propConfig.setProperty(prp, val);
			BufferedOutputStream strea2m = new BufferedOutputStream(
					new FileOutputStream(dataFolder.getAbsolutePath()
							+ File.separator + "c.properties"));
			propConfig
					.store(strea2m,
							"AntiBot V2 - The ultimate AntiSpam protection for Minecraft.");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		Player player = null;
		try {
			player = (Player) sender;
		} catch (Exception e) {
			return true;
		}

		if (args.length < 1) {
			returnMotd(sender);
			return true;
		}

		if (player != null) {
			if (!ownPermission("AntiBot.admin", player, 1)) {
				noPermission(sender);
				return true;
			}
		}

		if (args[0].compareToIgnoreCase("help") == 0) {
			sender.sendMessage("\247f[\247bAntiBot\247f] " + "AntiBot Help:");
			sender.sendMessage("\247f[\247bAntiBot\247f] " + "");
			sender.sendMessage("\247f[\247bAntiBot\247f] "
					+ "/antibot help - Help Menu");
			if (ownPermission("AntiBot.admin.reload", player, 1)) {
				sender.sendMessage("\247f[\247bAntiBot\247f] "
						+ "/antibot reload - Reload configuration");
			}
			if (ownPermission("AntiBot.admin.info", player, 3)) {
				sender.sendMessage("\247f[\247bAntiBot\247f] "
						+ "/antibot info - Check current status of AntiBot.");
			}
			if (ownPermission("AntiBot.admin.attack", player, 3)) {
				sender.sendMessage("\247f[\247bAntiBot\247f] "
						+ "/antibot run - Turn on invasion mode manually.");
			}
			if (ownPermission("AntiBot.admin.remkickplayer", player, 1)) {
				sender.sendMessage("\247f[\247bAntiBot\247f] "
						+ "/antibot remkick [player] - Removes a player from the autokick list.");
			}
			if (ownPermission("AntiBot.admin.flush", player, 2)) {
				sender.sendMessage("\247f[\247bAntiBot\247f] "
						+ "/antibot flush - Flush the connection throttling.");
			}
			if (ownPermission("AntiBot.admin.changeconf", player, 2)) {
				sender.sendMessage("\247f[\247bAntiBot\247f] "
						+ "/antibot int [val] - Change intervals.");
				sender.sendMessage("\247f[\247bAntiBot\247f] "
						+ "/antibot acc [val] - Change accounts.");
				sender.sendMessage("\247f[\247bAntiBot\247f] "
						+ "/antibot notify [true/false] - Change whether you get notified or not.");
			}
			if (ownPermission("AntiBot.admin.toggle", player, 3)) {
				sender.sendMessage("\247f[\247bAntiBot\247f] "
						+ "/antibot off - Turn off AntiBot.");
				sender.sendMessage("\247f[\247bAntiBot\247f] "
						+ "/antibot on - Turn on AntiBot.");
			}
			sender.sendMessage("\247f[\247bAntiBot\247f] "
					+ "/antibot version - Check this version of AntiBot.");
			return true;
		}
		if (args[0].compareToIgnoreCase("reload") == 0) {
			if (ownPermission("AntiBot.admin.reload", player, 1)) {
				if (loadSekritTools()) {
					sender.sendMessage("\247f[\247bAntiBot\247f] "
							+ ChatColor.GREEN
							+ "Reloaded configuration successfully!");
				} else {
					sender.sendMessage("\247f[\247bAntiBot\247f] "
							+ ChatColor.RED + "Configuration failed to reload.");
				}
			} else {
				noPermission(sender);
			}
			return true;
		}
		if (args[0].compareToIgnoreCase("run") == 0) {
			if (ownPermission("AntiBot.admin.attack", player, 2)) {
				if (!botlistener.reanibo) {
					if (botlistener.notify) {
						getServer()
								.broadcastMessage(
										"\247f[\247bAntiBot\247f] \247cOh no! A minecraft bot invasion has began. Connection Throttling: \247aEnabled");
					}
					botlistener.reanibo = true;
					botlistener.debug("Tripswitched!");
					botlistener.kickConnected();
				}
				botlistener.botattempt = System.currentTimeMillis();
				botlistener.botcts += 1;
			}
			return true;
		}
		if (args[0].compareToIgnoreCase("remkick") == 0) {
			if (ownPermission("AntiBot.admin.remkickplayer", player, 1)) {
				try {
					botlistener.autokick.remove(args[1]);
					sender.sendMessage("\247f[\247bAntiBot\247f] "
							+ "\247aRemoved " + args[1] + " successfully!");
				} catch (Exception e) {
					sender.sendMessage("\247f[\247bAntiBot\247f] "
							+ "\247cFailed to remove kicked player.");
				}
			}

			return true;
		}

		if (args[0].compareToIgnoreCase("flush") == 0) {
			if (ownPermission("AntiBot.admin.flush", player, 2)) {
				if (botlistener.flush()) {
					sender.sendMessage("\247f[\247bAntiBot\247f] "
							+ ChatColor.GREEN + "System flushed successfully!");
				} else {
					sender.sendMessage("\247f[\247bAntiBot\247f] "
							+ ChatColor.RED + "System failed to flush.");
				}
			} else {
				noPermission(sender);
			}
			return true;
			// Reload here.
		}
		if (args[0].compareToIgnoreCase("on") == 0) {
			if (ownPermission("AntiBot.admin.toggle", player, 3)) {
				if (botlistener.enabled == true) {
					sender.sendMessage("\247f[\247bAntiBot\247f] "
							+ "The system is already enabled!");
				} else {
					if (botlistener.toggle(true)) {
						sender.sendMessage("\247f[\247bAntiBot\247f] "
								+ ChatColor.GREEN + "System has been enabled!");
					} else {
						sender.sendMessage("\247f[\247bAntiBot\247f] "
								+ ChatColor.RED
								+ "Error while trying to enable AntiBot.");
					}
				}

			} else {
				noPermission(sender);
			}
			return true;
			// Reload here.
		}
		if (args[0].compareToIgnoreCase("off") == 0) {
			if (ownPermission("AntiBot.admin.toggle", player, 3)) {
				if (botlistener.enabled == false) {
					sender.sendMessage("\247f[\247bAntiBot\247f] "
							+ "The system is already disabled!");
				} else {
					if (botlistener.toggle(false)) {
						sender.sendMessage("\247f[\247bAntiBot\247f] "
								+ ChatColor.RED + "System has been disabled!");
					} else {
						sender.sendMessage("\247f[\247bAntiBot\247f] "
								+ ChatColor.RED
								+ "Error while trying to disable AntiBot.");
					}
				}
			} else {
				noPermission(sender);
			}
			return true;
			// Reload here.
		}
		if (args[0].compareToIgnoreCase("int") == 0) {
			if (ownPermission("AntiBot.admin.changeconf", player, 2)) {
				if (args.length < 2) {
					sender.sendMessage("\247f[\247bAntiBot\247f] "
							+ ChatColor.RED
							+ "Please type an interval after /antibot int.");
					return true;
				}
				try {
					Integer toval = Integer.parseInt(args[1]);
					if (toval < 1) {
						sender.sendMessage("\247f[\247bAntiBot\247f] "
								+ ChatColor.RED
								+ "You cannot set this value less than 1.");
					}
					if (toval > 60) {
						sender.sendMessage("\247f[\247bAntiBot\247f] "
								+ ChatColor.RED
								+ "You cannot set this value greater than 60.");
					}
					toval = toval * 1000;

					botlistener.interval = toval;
					if (saveConfig("joins-sec", Integer.toString(toval))) {
						sender.sendMessage("\247f[\247bAntiBot\247f] "
								+ ChatColor.GREEN
								+ "Changed intervals successfully!");
					} else {
						sender.sendMessage("\247f[\247bAntiBot\247f] "
								+ ChatColor.RED
								+ "Error while trying to save interval to config.");
					}
				} catch (Exception e) {
					sender.sendMessage("\247f[\247bAntiBot\247f] "
							+ ChatColor.RED
							+ "Error while trying to change interval.");
				}
			} else {
				noPermission(sender);
			}
			return true;
		}
		if (args[0].compareToIgnoreCase("notify") == 0) {
			if (ownPermission("AntiBot.admin.changeconf", player, 2)) {
				if (args.length != 2) {
					sender.sendMessage("\247f[\247bAntiBot\247f] "
							+ ChatColor.RED
							+ "Please type true/false after /antibot notify.");
					return true;
				}
				try {
					String a = args[1];
					if(!a.equals("true") && !a.equals("false")) {
						sender.sendMessage("\247f[\247bAntiBot\247f] "
								+ ChatColor.RED
								+ "The value must either be true or false.");
						return true;
					}
					botlistener.notify = Boolean.parseBoolean(a);
					if (saveConfig("orgy-notify", a)) {
						sender.sendMessage("\247f[\247bAntiBot\247f] "
								+ ChatColor.GREEN
								+ "Changed notification status successfully!");
					} else {
						sender.sendMessage("\247f[\247bAntiBot\247f] "
								+ ChatColor.RED
								+ "Error while trying to save notification status to config.");
					}
					
				} catch(Exception e) {
					e.printStackTrace();
					sender.sendMessage("\247f[\247bAntiBot\247f] "
							+ ChatColor.RED
							+ "Error while trying to change notification status.");
				}
				
			} else {
				noPermission(sender);
			}
			return true;
		}
		if (args[0].compareToIgnoreCase("acc") == 0) {
			if (ownPermission("AntiBot.admin.changeconf", player, 2)) {
				if (args.length < 2) {
					sender.sendMessage("\247f[\247bAntiBot\247f] "
							+ ChatColor.RED
							+ "Please type an # of accounts after /antibot acc.");
					return true;
				}
				try {
					Integer toval = Integer.parseInt(args[1]);
					if (toval > 10) {
						sender.sendMessage("\247f[\247bAntiBot\247f] "
								+ ChatColor.RED
								+ "You cannot set this value greater than 10.");
					}
					if (toval < 3) {
						sender.sendMessage("\247f[\247bAntiBot\247f] "
								+ ChatColor.RED
								+ "You cannot set this value less than 3.");
					}

					botlistener.accounts = toval;
					if (saveConfig("joins", Integer.toString(toval))) {
						sender.sendMessage("\247f[\247bAntiBot\247f] "
								+ ChatColor.GREEN
								+ "Changed # of accounts successfully!");
					} else {
						sender.sendMessage("\247f[\247bAntiBot\247f] "
								+ ChatColor.RED
								+ "Error while trying to save # of accounts to config.");
					}
				} catch (Exception e) {
					sender.sendMessage("\247f[\247bAntiBot\247f] "
							+ ChatColor.RED
							+ "Error while trying to change # of accounts.");
				}
			} else {
				noPermission(sender);
			}
			return true;
		}
		if (args[0].compareToIgnoreCase("info") == 0) {
			if (ownPermission("AntiBot.admin.info", player, 3)) {
				sender.sendMessage("\247f[\247bAntiBot\247f] "
						+ "AntiBot System Info:");
				sender.sendMessage("\247f[\247bAntiBot\247f] " + "");
				if (botlistener.interval > 100000) {
					sender.sendMessage("\247f[\247bAntiBot\247f] "
							+ "\247cSystem currently needs flushing.");
					sender.sendMessage("\247f[\247bAntiBot\247f] "
							+ "\247cTo remove this message, type /antibot flush");
					sender.sendMessage("\247f[\247bAntiBot\247f] " + "");
				}
				long math = botlistener.time - botlistener.lasttime;
				debug("Secs between last login: " + math, sender);
				debug("Current Intervals: " + botlistener.interval, sender);
				debug("Logged in: " + botlistener.botcts, sender);
				debug("# of Accounts: " + botlistener.accounts, sender);
				sender.sendMessage("\247f[\247bAntiBot\247f] " + "");
				if (botlistener.reanibo) {
					debug("Connection Throttling: " + ChatColor.GREEN
							+ "Enabled", sender);
				} else {
					debug("Connection Throttling: " + ChatColor.RED
							+ "Disabled", sender);
				}
			} else {
				noPermission(sender);
			}
			return true;
		}

		if (args[0].compareToIgnoreCase("version") == 0) {
			returnMotd(sender);
			return true;
		}
		sender.sendMessage("\247f[\247bAntiBot\247f] "
				+ "Unknown system command.");
		return false;
	}

	public void debug(String msg, CommandSender sender) {
		sender.sendMessage("\247f[\247bAntiBot\247f] " + msg);
	}

	public boolean loadSekritTools() {
		System.out.print("AntiBot: Attempt to do the impossible - Eminem.");
		try {
			Properties propConfig = new Properties();
			BufferedInputStream stream = new BufferedInputStream(
					new FileInputStream(dataFolder.getAbsolutePath()
							+ File.separator + "c.properties"));
			propConfig.load(stream);
			String load = null;
			Integer load2 = null;
			Boolean load3 = null;
			Long load4 = null;

			load = propConfig.getProperty("connect-message");
			if (load != null && load != botlistener.connectMsg) {
				botlistener.connectMsg = load;
			}

			load = propConfig.getProperty("kick-message");
			if (load != null && load != botlistener.kickMsg) {
				botlistener.kickMsg = load;
			}

			load = propConfig.getProperty("connect-join-invasion");
			if (load != null && load != botlistener.connectInvasion) {
				botlistener.connectInvasion = load;
			}

			load = propConfig.getProperty("joins-sec");
			if (load != null) {
				load2 = Integer.parseInt(load);
			} else {
				load2 = botlistener.interval;
			}
			if (load != null && load2 > 999
					&& !load2.equals(botlistener.interval)) {
				botlistener.interval = load2;
				this.defaultinterval = botlistener.interval;
			}

			load = propConfig.getProperty("whitelist-perms");
			if (load != null) {
				load3 = Boolean.parseBoolean(load);
			} else {
				load3 = botlistener.useWhiteListPerms;
			}
			if (load != null && !load3.equals(botlistener.useWhiteListPerms)) {
				botlistener.useWhiteListPerms = load3;
			}

			load = propConfig.getProperty("op-perms");
			if (load != null) {
				load3 = Boolean.parseBoolean(load);
			} else {
				load3 = botlistener.useOpPerms;
			}
			if (load != null && !load3.equals(botlistener.useOpPerms)) {
				botlistener.useOpPerms = load3;
			}

			load = propConfig.getProperty("whitelist-when-triggered");
			if (load != null) {
				load3 = Boolean.parseBoolean(load);
			} else {
				load3 = botlistener.whiteList;
			}
			if (load != null && !load3.equals(botlistener.whiteList)) {
				botlistener.whiteList = load3;
			}

			load = propConfig.getProperty("orgy-notify");
			if (load != null) {
				load3 = Boolean.parseBoolean(load);
			} else {
				load3 = botlistener.notify;
			}
			if (load != null) {
				botlistener.notify = load3;
			}

			load = propConfig.getProperty("debug-mode");
			if (load != null) {
				load3 = Boolean.parseBoolean(load);
			} else {
				load3 = botlistener.debugmode;
			}
			if (load != null && !load3.equals(botlistener.debugmode)) {
				botlistener.debugmode = load3;
				if (botlistener.debugmode)
					System.out
							.print("AntiBot: WARNING! You're in Debug Mode! Do not use this on a live environment!");
			}

			load = propConfig.getProperty("spam-amount");
			if (load != null) {
				load2 = Integer.parseInt(load);
			} else {
				load2 = botlistener.spamam;
			}
			if (load != null && !load2.equals(botlistener.spamam)) {
				botlistener.spamam = load2;
			}

			load = propConfig.getProperty("spam-time");
			if (load != null) {
				load2 = Integer.parseInt(load);
			} else {
				load2 = botlistener.spamtime;
			}
			if (load != null && !load2.equals(botlistener.spamtime)) {
				botlistener.spamtime = load2;
			}

			load = propConfig.getProperty("enable-by-default");
			if (load != null) {
				load3 = Boolean.parseBoolean(load);
			} else {
				load3 = botlistener.enabled;
			}
			if (load != null && !load3.equals(botlistener.enabled)) {
				botlistener.enabled = load3;
			}

			load = propConfig.getProperty("install-date");
			if (load != null) {
				load4 = Long.parseLong(load);
			} else {
				load4 = installdate;
				System.out
						.print("AntiBot: WARNING! Could not load system install date!");
			}
			if (load != null) {
				installdate = load4;
			}

			load = propConfig.getProperty("joins");
			if (load != null) {
				load2 = Integer.parseInt(load);
			} else {
				load2 = botlistener.accounts;
			}
			if (load != null && load2 > 2
					&& !load2.equals(botlistener.accounts)) {
				botlistener.accounts = load2;
				this.defaultaccounts = botlistener.accounts;
			}

			System.out.print("AntiBot: Configuration Loaded Successfully!");
			return true;

		} catch (Exception e) {
			System.out.print("AntiBot: FUCKIN: " + e);
			return false;
		}

	}

	public void onDisable() {
		System.out.println("Oh noes! Your server's condom slid off!");
	}
}