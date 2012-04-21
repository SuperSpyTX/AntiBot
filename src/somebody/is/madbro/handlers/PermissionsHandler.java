package somebody.is.madbro.handlers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import somebody.is.madbro.AntiBotCore;
import somebody.is.madbro.settings.Settings;

public class PermissionsHandler {

	public AntiBotCore antibot = null;
	
	public PermissionsHandler(AntiBotCore instance) {
		antibot = instance;
		// TODO Auto-generated constructor stub
	}

	public void returnMotd(CommandSender sender) {
		antibot.setInstall(new Date(antibot.getInstalldate()));
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
		sender.sendMessage(Settings.prefix + "AntiBot " + antibot.getVersion()
				+ " - Coded By .SuPaH sPii");
		sender.sendMessage(Settings.prefix
				+ "Inspired by Wolflink289 <3");
		sender.sendMessage(Settings.prefix
				+ "Continued inspiration by Evenprime & Fafaffy <3");
		Random random = new Random();
		switch (random.nextInt(20)) {
		case 0:
			sender.sendMessage(Settings.prefix
					+ "System Installed on " + ChatColor.GREEN
					+ sdf.format(antibot.getInstall()));
			break;
		case 1:
			sender.sendMessage(Settings.prefix
					+ "Keeping Pwnage out of game since " + ChatColor.GREEN
					+ sdf.format(antibot.getInstall()));
			break;
		case 2:
			sender.sendMessage(Settings.prefix
					+ "Combatting spam since " + ChatColor.GREEN
					+ sdf.format(antibot.getInstall()));
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
			sender.sendMessage(Settings.prefix
					+ "Making people mad since " + ChatColor.GREEN
					+ sdf.format(antibot.getInstall()));
			break;
		case 6:
			sender.sendMessage(Settings.prefix
					+ "Trolling spammers since " + ChatColor.GREEN
					+ sdf.format(antibot.getInstall()));
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
			sender.sendMessage(Settings.prefix
					+ "Injected the Vaccine on " + ChatColor.GREEN
					+ sdf.format(antibot.getInstall()));
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
			sender.sendMessage(Settings.prefix
					+ "Chinese secret happened on " + ChatColor.GREEN
					+ sdf.format(antibot.getInstall()));
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
			sender.sendMessage(Settings.prefix
					+ "System Installed on " + ChatColor.GREEN
					+ sdf.format(antibot.getInstall()));
			break;
		}
	}

	public void noPermission(CommandSender sender) {
		sender.sendMessage(Settings.prefix
				+ "\247cSorry, you don't have privileges.");
	}

	public boolean ownPermission(String perm, Player player, Integer level) {
		if (player.hasPermission(perm)) {
			return true;
		}
		if (player.isOp() && Settings.useOpPerms) {
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
	
	public boolean useWhitelist(Player pl) {
		if (Settings.useWhiteListPerms) {
			return pl.isWhitelisted();
		} else {
			return false;
		}
	}

	public boolean useOp(Player pl) {
		if (Settings.useOpPerms) {
			return pl.isOp();
		} else {
			return false;
		}
	}

	public boolean hasPerms(Player pl) {
		if (useOp(pl)
				|| useWhitelist(pl)
				|| ownPermission("AntiBot.join", pl, 1)) {
			return true;
		} else {
			return false;
		}
	}

}
