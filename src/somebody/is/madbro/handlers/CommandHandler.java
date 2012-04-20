package somebody.is.madbro.handlers;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import somebody.is.madbro.AntiBotCore;
import somebody.is.madbro.settings.Settings;

public class CommandHandler extends HandlerCore {

	public CommandHandler(AntiBotCore instance) {
		super(instance);
		// TODO Auto-generated constructor stub
	}
	
	public boolean handle(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		Player player = null;
		try {
			player = (Player) sender;
		} catch (Exception e) {
			return true;
		}

		if (args.length < 1) {
			antibot.getHandler().getPermissions().returnMotd(sender);
			return true;
		}

		if (player != null) {
			if (!antibot.getHandler().getPermissions().ownPermission("AntiBot.admin",
					player, 1)) {
				antibot.getHandler().getPermissions().noPermission(sender);
				return true;
			}
		}

		if (args[0].compareToIgnoreCase("help") == 0) {
			sender.sendMessage(Settings.prefix + "AntiBot Help:");
			sender.sendMessage(Settings.prefix + "");
			sender.sendMessage(Settings.prefix + "/antibot help - Help Menu");
			if (antibot.getHandler().getPermissions().ownPermission(
					"AntiBot.admin.reload", player, 1)) {
				sender.sendMessage(Settings.prefix
						+ "/antibot reload - Reload configuration");
			}
			if (antibot.getHandler().getPermissions().ownPermission(
					"AntiBot.admin.info", player, 3)) {
				sender.sendMessage(Settings.prefix
						+ "/antibot info - Check current status of AntiBot.");
			}
			if (antibot.getHandler().getPermissions().ownPermission(
					"AntiBot.admin.attack", player, 3)) {
				sender.sendMessage(Settings.prefix
						+ "/antibot run - Turn on invasion mode manually.");
			}
			if (antibot.getHandler().getPermissions().ownPermission(
					"AntiBot.admin.remkickplayer", player, 1)) {
				sender.sendMessage(Settings.prefix
						+ "/antibot remkick [player] - Removes a player from the autokick list.");
			}
			if (antibot.getHandler().getPermissions().ownPermission(
					"AntiBot.admin.flush", player, 2)) {
				sender.sendMessage(Settings.prefix
						+ "/antibot flush - Flush the connection throttling.");
			}
			if (antibot.getHandler().getPermissions().ownPermission(
					"AntiBot.admin.changeconf", player, 2)) {
				sender.sendMessage(Settings.prefix
						+ "/antibot int [val] - Change intervals.");
				sender.sendMessage(Settings.prefix
						+ "/antibot acc [val] - Change accounts.");
				sender.sendMessage(Settings.prefix
						+ "/antibot notify [true/false] - Change whether you get notified or not.");
			}
			if (antibot.getHandler().getPermissions().ownPermission(
					"AntiBot.admin.toggle", player, 3)) {
				sender.sendMessage(Settings.prefix
						+ "/antibot off - Turn off AntiBot.");
				sender.sendMessage(Settings.prefix
						+ "/antibot on - Turn on AntiBot.");
			}
			sender.sendMessage(Settings.prefix
					+ "/antibot version - Check this version of AntiBot.");
			return true;
		}
		if (args[0].compareToIgnoreCase("reload") == 0) {
			if (antibot.getHandler().getPermissions().ownPermission(
					"AntiBot.admin.reload", player, 1)) {
				if (antibot.getSettings().loadSettings(antibot.getDataFolder())) {
					sender.sendMessage(Settings.prefix + ChatColor.GREEN
							+ "Reloaded configuration successfully!");
				} else {
					sender.sendMessage(Settings.prefix + ChatColor.RED
							+ "Configuration failed to reload.");
				}
			} else {
				antibot.getHandler().getPermissions().noPermission(sender);
			}
			return true;
		}
		if (args[0].compareToIgnoreCase("run") == 0) {
			if (antibot.getHandler().getPermissions().ownPermission(
					"AntiBot.admin.attack", player, 2)) {
				if (!antibot.getHandler().getBotHandler().reanibo) {
					if (Settings.notify) {
						antibot.getServer()
								.broadcastMessage(
										"\247f[\247bAntiBot\247f] \247cOh no! A minecraft bot invasion has began. Connection Throttling: \247aEnabled");
					}
					antibot.getHandler().getBotHandler().reanibo = true;
					antibot.getUtility().getDebugUtility().debug("Tripswitched!");
					antibot.getHandler().getBotHandler().kickConnected();
				}
				antibot.getHandler().getBotHandler().botattempt = System.currentTimeMillis();
				antibot.getHandler().getBotHandler().botcts += 1;
			}
			return true;
		}
		if (args[0].compareToIgnoreCase("remkick") == 0) {
			if (antibot.getHandler().getPermissions().ownPermission(
					"AntiBot.admin.remkickplayer", player, 1)) {
				try {
					antibot.getHandler().getBotHandler().autokick.remove(args[1]);
					sender.sendMessage(Settings.prefix + "\247aRemoved "
							+ args[1] + " successfully!");
				} catch (Exception e) {
					sender.sendMessage(Settings.prefix
							+ "\247cFailed to remove kicked player.");
				}
			}

			return true;
		}

		if (args[0].compareToIgnoreCase("flush") == 0) {
			if (antibot.getHandler().getPermissions().ownPermission(
					"AntiBot.admin.flush", player, 2)) {
				if (antibot.getHandler().getBotHandler().flush()) {
					sender.sendMessage(Settings.prefix + ChatColor.GREEN
							+ "System flushed successfully!");
				} else {
					sender.sendMessage(Settings.prefix + ChatColor.RED
							+ "System failed to flush.");
				}
			} else {
				antibot.getHandler().getPermissions().noPermission(sender);
			}
			return true;
			// Reload here.
		}
		if (args[0].compareToIgnoreCase("on") == 0) {
			if (antibot.getHandler().getPermissions().ownPermission(
					"AntiBot.admin.toggle", player, 3)) {
				if (Settings.enabled == true) {
					sender.sendMessage(Settings.prefix
							+ "The system is already enabled!");
				} else {
					if (antibot.getHandler().getBotHandler().toggle(true)) {
						sender.sendMessage(Settings.prefix + ChatColor.GREEN
								+ "System has been enabled!");
					} else {
						sender.sendMessage(Settings.prefix + ChatColor.RED
								+ "Error while trying to enable AntiBot.");
					}
				}

			} else {
				antibot.getHandler().getPermissions().noPermission(sender);
			}
			return true;
			// Reload here.
		}
		if (args[0].compareToIgnoreCase("off") == 0) {
			if (antibot.getHandler().getPermissions().ownPermission(
					"AntiBot.admin.toggle", player, 3)) {
				if (Settings.enabled == false) {
					sender.sendMessage(Settings.prefix
							+ "The system is already disabled!");
				} else {
					if (antibot.getHandler().getBotHandler().toggle(false)) {
						sender.sendMessage(Settings.prefix + ChatColor.RED
								+ "System has been disabled!");
					} else {
						sender.sendMessage(Settings.prefix + ChatColor.RED
								+ "Error while trying to disable AntiBot.");
					}
				}
			} else {
				antibot.getHandler().getPermissions().noPermission(sender);
			}
			return true;
			// Reload here.
		}
		if (args[0].compareToIgnoreCase("int") == 0) {
			if (antibot.getHandler().getPermissions().ownPermission(
					"AntiBot.admin.changeconf", player, 2)) {
				if (args.length < 2) {
					sender.sendMessage(Settings.prefix + ChatColor.RED
							+ "Please type an interval after /antibot int.");
					return true;
				}
				try {
					Integer toval = Integer.parseInt(args[1]);
					if (toval < 1) {
						sender.sendMessage(Settings.prefix + ChatColor.RED
								+ "You cannot set this value less than 1.");
					}
					if (toval > 60) {
						sender.sendMessage(Settings.prefix + ChatColor.RED
								+ "You cannot set this value greater than 60.");
					}
					toval = toval * 1000;

					Settings.interval = toval;
					if (antibot.getSettings().saveConfig("joins-sec",
							Integer.toString(toval))) {
						sender.sendMessage(Settings.prefix + ChatColor.GREEN
								+ "Changed intervals successfully!");
					} else {
						sender.sendMessage(Settings.prefix
								+ ChatColor.RED
								+ "Error while trying to save interval to config.");
					}
				} catch (Exception e) {
					sender.sendMessage(Settings.prefix + ChatColor.RED
							+ "Error while trying to change interval.");
				}
			} else {
				antibot.getHandler().getPermissions().noPermission(sender);
			}
			return true;
		}
		if (args[0].compareToIgnoreCase("notify") == 0) {
			if (antibot.getHandler().getPermissions().ownPermission(
					"AntiBot.admin.changeconf", player, 2)) {
				if (args.length != 2) {
					sender.sendMessage(Settings.prefix + ChatColor.RED
							+ "Please type true/false after /antibot notify.");
					return true;
				}
				try {
					String a = args[1];
					if (!a.equals("true") && !a.equals("false")) {
						sender.sendMessage(Settings.prefix + ChatColor.RED
								+ "The value must either be true or false.");
						return true;
					}
					Settings.notify = Boolean.parseBoolean(a);
					if (antibot.getSettings().saveConfig("orgy-notify", a)) {
						sender.sendMessage(Settings.prefix + ChatColor.GREEN
								+ "Changed notification status successfully!");
					} else {
						sender.sendMessage(Settings.prefix
								+ ChatColor.RED
								+ "Error while trying to save notification status to config.");
					}

				} catch (Exception e) {
					e.printStackTrace();
					sender.sendMessage(Settings.prefix
							+ ChatColor.RED
							+ "Error while trying to change notification status.");
				}

			} else {
				antibot.getHandler().getPermissions().noPermission(sender);
			}
			return true;
		}
		if (args[0].compareToIgnoreCase("acc") == 0) {
			if (antibot.getHandler().getPermissions().ownPermission(
					"AntiBot.admin.changeconf", player, 2)) {
				if (args.length < 2) {
					sender.sendMessage(Settings.prefix
							+ ChatColor.RED
							+ "Please type an # of accounts after /antibot acc.");
					return true;
				}
				try {
					Integer toval = Integer.parseInt(args[1]);
					if (toval > 10) {
						sender.sendMessage(Settings.prefix + ChatColor.RED
								+ "You cannot set this value greater than 10.");
					}
					if (toval < 3) {
						sender.sendMessage(Settings.prefix + ChatColor.RED
								+ "You cannot set this value less than 3.");
					}

					Settings.accounts = toval;
					if (antibot.getSettings().saveConfig("joins",
							Integer.toString(toval))) {
						sender.sendMessage(Settings.prefix + ChatColor.GREEN
								+ "Changed # of accounts successfully!");
					} else {
						sender.sendMessage(Settings.prefix
								+ ChatColor.RED
								+ "Error while trying to save # of accounts to config.");
					}
				} catch (Exception e) {
					sender.sendMessage(Settings.prefix + ChatColor.RED
							+ "Error while trying to change # of accounts.");
				}
			} else {
				antibot.getHandler().getPermissions().noPermission(sender);
			}
			return true;
		}
		if (args[0].compareToIgnoreCase("info") == 0) {
			if (antibot.getHandler().getPermissions().ownPermission(
					"AntiBot.admin.info", player, 3)) {
				sender.sendMessage(Settings.prefix + "AntiBot System Info:");
				sender.sendMessage(Settings.prefix + "");
				if (Settings.interval > 100000) {
					sender.sendMessage(Settings.prefix
							+ "\247cSystem currently needs flushing.");
					sender.sendMessage(Settings.prefix
							+ "\247cTo remove this message, type /antibot flush");
					sender.sendMessage(Settings.prefix + "");
				}
				long math = antibot.getHandler().getBotHandler().time - antibot.getHandler().getBotHandler().lasttime;
				antibot.debug("Secs between last login: " + math, sender);
				antibot.debug("Current Intervals: " + Settings.interval, sender);
				antibot.debug("Logged in: " + antibot.getHandler().getBotHandler().botcts, sender);
				antibot.debug("# of Accounts: " + Settings.accounts, sender);
				sender.sendMessage(Settings.prefix + "");
				if (antibot.getHandler().getBotHandler().reanibo) {
					antibot.debug("Connection Throttling: " + ChatColor.GREEN
							+ "Enabled", sender);
				} else {
					antibot.debug("Connection Throttling: " + ChatColor.RED
							+ "Disabled", sender);
				}
			} else {
				antibot.getHandler().getPermissions().noPermission(sender);
			}
			return true;
		}

		if (args[0].compareToIgnoreCase("version") == 0) {
			antibot.getHandler().getPermissions().returnMotd(sender);
			return true;
		}
		sender.sendMessage(Settings.prefix + "Unknown system command.");
		return false;
	}

}
