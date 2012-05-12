package somebody.is.madbro.handlers.permissions;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import somebody.is.madbro.AntiBotCore;
import somebody.is.madbro.settings.Settings;

@Deprecated
public class PermissionsHandler {

	public AntiBotCore antibot = null;

	public PermissionsHandler(AntiBotCore instance) {
		antibot = instance;
		// TODO Auto-generated constructor stub
	}

	public void noPermission(CommandSender sender) {
		sender.sendMessage(Settings.prefix
				+ "\247cSorry, you don't have privileges.");
	}

	public boolean ownPermission(String perm, Player player, Integer level) {
		if (player != null) {

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
		} else {
			return true; // console.
		}

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

	public boolean hasPermission(String perm, Player pl) {
		if (useOp(pl) || ownPermission(perm, pl, 1)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasPerms(Player pl) {
		if (useOp(pl) || useWhitelist(pl)
				|| ownPermission("AntiBot.join", pl, 1)) {
			return true;
		} else {
			return false;
		}
	}

}
