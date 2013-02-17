package com.superspytx.ab.handlers.chat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.superspytx.ab.AB;
import com.superspytx.ab.abs.EventAction;
import com.superspytx.ab.abs.Handler;
import com.superspytx.ab.settings.Language;
import com.superspytx.ab.settings.Permissions;
import com.superspytx.ab.settings.Settings;
import com.superspytx.ab.tils.Tils;
import com.superspytx.ab.workflow.GD;

public class CommandHandler implements Handler {
	
	@Override
	public boolean run(EventAction info) {
		CommandSender sender = info.sender;
		Command cmd = info.cmd;
		String label = info.label;
		String[] args = info.args;
		
		if (sender == null || cmd == null || label == null || args == null) return false;
		
		Player player = null;
		try {
			player = (Player) sender;
		} catch (Exception e) {
			// console!
		}
		
		if (args.length < 1) {
			returnMotd(sender);
			return true;
		}
		
		if (player != null) {
			if (!Permissions.ADMIN.getPermission(player, sender)) { return true; }
		}
		
		if (args[0].compareToIgnoreCase("help") == 0) {
			sender.sendMessage(Language.prefix + "AntiBot Help:");
			sender.sendMessage(Language.prefix + "");
			sender.sendMessage(Language.prefix + "/antibot help - Help Menu");
			if (Permissions.ADMIN_RELOAD.getPermission(player)) {
				sender.sendMessage(Language.prefix + "/antibot reload - Reload configuration");
			}
			if (Permissions.ADMIN_INFO.getPermission(player)) {
				sender.sendMessage(Language.prefix + "/antibot info - Check current status of AntiBot.");
			}
			if (Permissions.ADMIN_CHATMUTE.getPermission(player)) {
				sender.sendMessage(Language.prefix + "/antibot chatmute - Toggle chat flow's global chat mute.");
			}
			if (Permissions.ADMIN_FLUSH.getPermission(player)) {
				sender.sendMessage(Language.prefix + "/antibot flush - Flushes all the data.");
			}
			if (Permissions.ADMIN_TOGGLE.getPermission(player)) {
				sender.sendMessage(Language.prefix + "/antibot off - Turn off AntiBot.");
				sender.sendMessage(Language.prefix + "/antibot on - Turn on AntiBot.");
			}
			sender.sendMessage(Language.prefix + "/antibot version - Check this version of AntiBot.");
			return true;
		}
		if (args[0].compareToIgnoreCase("reload") == 0) {
			if (Permissions.ADMIN_RELOAD.getPermission(player, sender)) {
				if (AB.getSettingsCore().loadSettings()) {
					sender.sendMessage(Language.prefix + ChatColor.GREEN + "Reloaded configuration successfully!");
				} else {
					sender.sendMessage(Language.prefix + ChatColor.RED + "Configuration failed to reload.");
				}
			}
			return true;
		}
		
		if (args[0].compareToIgnoreCase("chatmute") == 0) {
			if (Permissions.ADMIN_CHATMUTE.getPermission(player, sender)) {
				boolean b = !GD.cf_gm;
				try {
					b = (args[0].equalsIgnoreCase("on") ? true : (args[0].equalsIgnoreCase("off") ? false : !GD.cf_gm));
				} catch (Exception shutup) {}
				
				GD.cf_gm = b;
				if (Settings.notify && Settings.enabled) {
					if (b) AB.getInstance().getServer().broadcastMessage(Language.prefix + ChatColor.DARK_AQUA + Language.overflowedMessage.replace("%sec%", "infinity, and beyond"));
					if (!b) AB.getInstance().getServer().broadcastMessage(Language.prefix + ChatColor.GREEN + "Chat has been unmuted by " + sender.getName() + "!");
					
					GD.cf_cts = 0;
				}
			}
			
			return true;
		}
		
		if (args[0].compareToIgnoreCase("on") == 0) {
			if (Permissions.ADMIN_TOGGLE.getPermission(player, sender)) {
				if (Settings.enabled) {
					sender.sendMessage(Language.prefix + "The system is already enabled!");
				} else {
					Settings.enabled = true;
					sender.sendMessage(Language.prefix + ChatColor.GREEN + "System has been enabled!");
				}
				
			}
			return true;
			// Reload here.
		}
		
		if (args[0].compareToIgnoreCase("off") == 0) {
			if (Permissions.ADMIN_TOGGLE.getPermission(player, sender)) {
				if (!Settings.enabled) {
					sender.sendMessage(Language.prefix + "The system is already disabled!");
				} else {
					Settings.enabled = false;
					sender.sendMessage(Language.prefix + ChatColor.RED + "System has been disabled!");
				}
				
				if (GD.cf_gm) sender.sendMessage(Language.prefix + ChatColor.DARK_RED + "ERROR: You have left the server chat muted! Nobody is able to talk.");
			}
			return true;
			// Reload here.
		}
		if (args[0].compareToIgnoreCase("info") == 0) {
			if (Permissions.ADMIN_INFO.getPermission(player, sender)) {
				sender.sendMessage(Language.prefix + "AntiBot System Info:");
				sender.sendMessage(Language.prefix + "");
				sender.sendMessage(Language.prefix + "Secs between last login: " + Tils.getLongDiff(GD.b_lc));
				sender.sendMessage(Language.prefix + "Current Intervals: " + Settings.interval);
				sender.sendMessage(Language.prefix + "Logged in: " + GD.b_cp.size());
				sender.sendMessage(Language.prefix + "# of Accounts: " + Settings.accounts);
			}
			return true;
		}
		
		if (args[0].compareToIgnoreCase("flush") == 0) {
			if (Permissions.ADMIN_FLUSH.getPermission(player, sender)) {
				AB.reload();
				sender.sendMessage(Language.prefix + ChatColor.GREEN + "Flushed data successfully!");
			}
			return true;
		}
		
		if (args[0].compareToIgnoreCase("version") == 0) {
			returnMotd(sender);
			return true;
		}
		sender.sendMessage(Language.prefix + ChatColor.RED + "Unknown system command.");
		return false;
	}
	
	@Override
	public void performActions(EventAction info) {
		
	}
	
	public String returnStatus() {
		if (!Settings.enabled) {
			if (Settings.delayedStart) {
				return ChatColor.RED + "Disabled" + ChatColor.WHITE + " (" + ChatColor.YELLOW + "Delayed Start" + ChatColor.WHITE + ")";
			} else {
				return ChatColor.RED + "Disabled";
			}
		} else {
			return ChatColor.GREEN + "Enabled";
		}
	}
	
	public void returnMotd(CommandSender sender) {
		Date localdate = new Date(Settings.installdate);
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
		sender.sendMessage(Language.prefix + "AntiBot " + AB.getVersion() + " - By .SuPaH sPii");
		sender.sendMessage(Language.prefix + "Inspired by Wolflink289 <3");
		sender.sendMessage(Language.prefix + "Continued inspiration by Evenprime & Fafaffy <3");
		sender.sendMessage(Language.prefix + "Recontinuted interest by H31IX & horde of users affected by Chat Spam in #anticheat");
		
		// return status if player has admin permissions.
		Player player = null;
		try {
			player = (Player) sender;
		} catch (Exception e) {
			// console!
		}
		if (Permissions.ADMIN_NOTIFY.getPermission(player)) {
			sender.sendMessage(Language.prefix + "System Status: " + returnStatus());
		}
		Random random = new Random();
		switch (random.nextInt(20)) {
			case 0:
				sender.sendMessage(Language.prefix + "System Installed on " + ChatColor.GREEN + sdf.format(localdate));
				break;
			case 1:
				sender.sendMessage(Language.prefix + "Keeping PWN4G3 & Paradigm out of the game since " + ChatColor.GREEN + sdf.format(localdate));
				break;
			case 2:
				sender.sendMessage(Language.prefix + "Combatting spam since " + ChatColor.GREEN + sdf.format(localdate));
				break;
			case 3:
				sender.sendMessage(Language.prefix + "Supporting PWN4G3 & Paradigm Bots since " + ChatColor.GREEN + sdf.format(localdate));
				break;
			case 4:
				sender.sendMessage(Language.prefix + "Running PWN4G3 & Paradigm Bots to the void since " + ChatColor.GREEN + sdf.format(localdate));
				break;
			case 5:
				sender.sendMessage(Language.prefix + "Making people mad since " + ChatColor.GREEN + sdf.format(localdate));
				break;
			case 6:
				sender.sendMessage(Language.prefix + "Trolling spammers since " + ChatColor.GREEN + sdf.format(localdate));
				break;
			case 7:
				sender.sendMessage(Language.prefix + "Supporting Wolflink289's idea since " + ChatColor.GREEN + sdf.format(localdate));
				break;
			case 8:
				sender.sendMessage(Language.prefix + "Protecting this server since " + ChatColor.GREEN + sdf.format(localdate));
				break;
			case 9:
				sender.sendMessage(Language.prefix + "All lights turned green since " + ChatColor.GREEN + sdf.format(localdate));
				break;
			case 10:
				sender.sendMessage(Language.prefix + "Corrupting ability to spam since " + ChatColor.GREEN + sdf.format(localdate));
				break;
			case 11:
				sender.sendMessage(Language.prefix + "Minecraft PWN4G3 & Paradigm dun goof'd on " + ChatColor.GREEN + sdf.format(localdate));
				break;
			case 12:
				sender.sendMessage(Language.prefix + "Making .SuPaH sPii proud since " + ChatColor.GREEN + sdf.format(localdate));
				break;
			case 13:
				sender.sendMessage(Language.prefix + "Injected the Vaccine on " + ChatColor.GREEN + sdf.format(localdate));
				break;
			case 14:
				sender.sendMessage(Language.prefix + "Giving AIDS to spammers since " + ChatColor.GREEN + sdf.format(localdate));
				break;
			case 15:
				sender.sendMessage(Language.prefix + "Turning spammer users to WTF faces since " + ChatColor.GREEN + sdf.format(localdate));
				break;
			case 16:
				sender.sendMessage(Language.prefix + "Chinese secret happened on " + ChatColor.GREEN + sdf.format(localdate));
				break;
			case 17:
				sender.sendMessage(Language.prefix + "Been in Slim Shady's world since " + ChatColor.GREEN + sdf.format(localdate));
				break;
			case 18:
				sender.sendMessage(Language.prefix + "Making other communities jelly since " + ChatColor.GREEN + sdf.format(localdate));
				break;
			case 19:
				sender.sendMessage(Language.prefix + "Didn't have to buy anything since " + ChatColor.GREEN + sdf.format(localdate));
				break;
			case 20:
				sender.sendMessage(Language.prefix + "Making Jogn shit his pants since " + ChatColor.GREEN + sdf.format(localdate));
				break;
			default:
				sender.sendMessage(Language.prefix + "System Installed on " + ChatColor.GREEN + sdf.format(localdate));
				break;
		}
	}
	
}
