package somebody.is.madbro.toolbox;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import somebody.is.madbro.AntiBot;
import somebody.is.madbro.settings.Settings;

public class BotUtility {
	public AntiBot antibot = null;

	public BotUtility(AntiBot instance) {
		antibot = instance;
		// TODO Auto-generated constructor stub
	}

	public boolean toggle(Boolean e) {
		try {
			Settings.enabled = e;
			return true;
		} catch (Exception fe) {
			return false;
		}
	}
	
	public void returnMotd(CommandSender sender) {
		antibot.setInstall(new Date(antibot.getInstalldate()));
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
		sender.sendMessage(Settings.prefix + "AntiBot " + antibot.getVersion()
				+ " - Coded By .SuPaH sPii");
		sender.sendMessage(Settings.prefix + "Inspired by Wolflink289 <3");
		sender.sendMessage(Settings.prefix
				+ "Continued inspiration by Evenprime & Fafaffy <3");
		Random random = new Random();
		switch (random.nextInt(20)) {
		case 0:
			sender.sendMessage(Settings.prefix + "System Installed on "
					+ ChatColor.GREEN + sdf.format(antibot.getInstall()));
			break;
		case 1:
			sender.sendMessage(Settings.prefix
					+ "Keeping Pwnage out of game since " + ChatColor.GREEN
					+ sdf.format(antibot.getInstall()));
			break;
		case 2:
			sender.sendMessage(Settings.prefix + "Combatting spam since "
					+ ChatColor.GREEN + sdf.format(antibot.getInstall()));
			break;
		case 3:
			sender.sendMessage(Settings.prefix
					+ "Supporting PWN4G3 Bots since " + ChatColor.GREEN
					+ sdf.format(antibot.getInstall()));
			break;
		case 4:
			sender.sendMessage(Settings.prefix
					+ "Running PWN4G3 Bots to the void since "
					+ ChatColor.GREEN + sdf.format(antibot.getInstall()));
			break;
		case 5:
			sender.sendMessage(Settings.prefix + "Making people mad since "
					+ ChatColor.GREEN + sdf.format(antibot.getInstall()));
			break;
		case 6:
			sender.sendMessage(Settings.prefix + "Trolling spammers since "
					+ ChatColor.GREEN + sdf.format(antibot.getInstall()));
			break;
		case 7:
			sender.sendMessage(Settings.prefix
					+ "Supporting Wolflink289's idea since " + ChatColor.GREEN
					+ sdf.format(antibot.getInstall()));
			break;
		case 8:
			sender.sendMessage(Settings.prefix
					+ "Protecting this server since " + ChatColor.GREEN
					+ sdf.format(antibot.getInstall()));
			break;
		case 9:
			sender.sendMessage(Settings.prefix
					+ "All lights turned green since " + ChatColor.GREEN
					+ sdf.format(antibot.getInstall()));
			break;
		case 10:
			sender.sendMessage(Settings.prefix
					+ "Corrupting ability to spam since " + ChatColor.GREEN
					+ sdf.format(antibot.getInstall()));
			break;
		case 11:
			sender.sendMessage(Settings.prefix
					+ "Minecraft PWN4G3 Dun goof on " + ChatColor.GREEN
					+ sdf.format(antibot.getInstall()));
			break;
		case 12:
			sender.sendMessage(Settings.prefix
					+ "Making .SuPaH sPii proud since " + ChatColor.GREEN
					+ sdf.format(antibot.getInstall()));
			break;
		case 13:
			sender.sendMessage(Settings.prefix + "Injected the Vaccine on "
					+ ChatColor.GREEN + sdf.format(antibot.getInstall()));
			break;
		case 14:
			sender.sendMessage(Settings.prefix
					+ "Giving AIDS to spammers since " + ChatColor.GREEN
					+ sdf.format(antibot.getInstall()));
			break;
		case 15:
			sender.sendMessage(Settings.prefix
					+ "Turning spammer users to WTF faces since "
					+ ChatColor.GREEN + sdf.format(antibot.getInstall()));
			break;
		case 16:
			sender.sendMessage(Settings.prefix + "Chinese secret happened on "
					+ ChatColor.GREEN + sdf.format(antibot.getInstall()));
			break;
		case 17:
			sender.sendMessage(Settings.prefix
					+ "Been in Slim Shady's world since " + ChatColor.GREEN
					+ sdf.format(antibot.getInstall()));
			break;
		case 18:
			sender.sendMessage(Settings.prefix
					+ "Making other communities jelly since " + ChatColor.GREEN
					+ sdf.format(antibot.getInstall()));
			break;
		case 19:
			sender.sendMessage(Settings.prefix
					+ "Didn't have to buy anything since " + ChatColor.GREEN
					+ sdf.format(antibot.getInstall()));
			break;
		case 20:
			sender.sendMessage(Settings.prefix
					+ "Making Reanimation shit his pants since "
					+ ChatColor.GREEN + sdf.format(antibot.getInstall()));
			break;
		default:
			sender.sendMessage(Settings.prefix + "System Installed on "
					+ ChatColor.GREEN + sdf.format(antibot.getInstall()));
			break;
		}
	}

	public boolean flush() {
		try {
			if (antibot.getDataTrack().getBotTracker().reanibo) {
				antibot.getUtility().getDebug()
						.debug("Disabled Reanibios.");
				antibot.getDataTrack().getBotTracker().reanibo = false;
				Settings.interval = antibot.getDefaultinterval();
				antibot.getDataTrack().getBotTracker().connected.clear();
				Settings.accounts = antibot.getDefaultaccounts();
				antibot.getDataTrack().getBotTracker().lasttime = 0;
				antibot.getDataTrack().getBotTracker().botattempt = 0;
				if (Settings.notify && Settings.whiteList) {
					antibot.getServer()
							.broadcastMessage(
									Settings.prefix
											+ "\247aThe minecraft bot invasion has ended. Connection Throttling: \247cDisabled");
				}
			}
			antibot.getDataTrack().getBotTracker().botcts = 0;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean flush2() {
		try {
			antibot.getDataTrack().getBotTracker().reanibo = false;
			Settings.interval = antibot.getDefaultinterval();
			antibot.getDataTrack().getBotTracker().connected.clear();
			antibot.getDataTrack().getBotTracker().autokick.clear();
			antibot.getDataTrack().getBotTracker().autoipkick.clear();
			antibot.getDataTrack().getChatTracker().spammyPlayers.clear();
			antibot.getDataTrack().getBotTracker().ipList.clear();
			Settings.accounts = antibot.getDefaultaccounts();
			antibot.getDataTrack().getBotTracker().lasttime = 0;
			antibot.getDataTrack().getBotTracker().botattempt = 0;
			antibot.getDataTrack().getChatTracker().chatflowscurrent = 0;
			antibot.getDataTrack().getChatTracker().chatflowscurrent = 0;
			if(antibot.getDataTrack().getChatTracker().chatLockedDown) {
				antibot.getDataTrack().getChatTracker().chatLockedDown = false;
				antibot.getServer().broadcastMessage(
						Settings.prefix + ChatColor.GREEN
								+ "Chat has been unmuted!");
			}
			antibot.getDataTrack().getChatTracker().chatoverflows = 0;
			antibot.getDataTrack().getChatTracker().chatmutedlength = 5L;
			antibot.getDataTrack().getChatTracker().lasttime = 0L;
			if (Settings.notify && Settings.whiteList) {
				antibot.getServer()
						.broadcastMessage(
								Settings.prefix
										+ "\247aThe minecraft bot invasion has ended. Connection Throttling: \247cDisabled");
			}
			antibot.getDataTrack().getBotTracker().botcts = 0;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public int getRandomInt() {
		Random rdm = new Random();
		return rdm.nextInt(5000);
	}

	public int getRandomIntInvasion() {
		if (!antibot.getDataTrack().getBotTracker().reanibo) {
			return 0;
		}
		Random rdm = new Random();
		if (Settings.interval > 35000) {
			return rdm.nextInt(7000);
		} else if (Settings.interval > 45000) {
			return rdm.nextInt(25000);
		}
		return rdm.nextInt(5000);
	}

}
