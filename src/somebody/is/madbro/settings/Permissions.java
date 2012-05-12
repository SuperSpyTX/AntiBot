package somebody.is.madbro.settings;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public enum Permissions {
	
	//checks?
	NOTIFY("notify", 1),
	JOIN("join", 1),
	VOICE("voice", 1),
	SPAM("spam", 1),
	CHATSPAM("chatspam", 1),
	COUNTRYBAN("countryban", 1),
	
	//admin level permissions
	ADMIN_ROOT("admin.root" , 3),
	ADMIN_PLUS("admin.plus", 2),
	ADMIN_BASIC("admin.basic", 1),
	
	//individual permissions.
	ADMIN_RELOAD("admin.reload", 3),
	ADMIN_INFO("admin.info", 2),
	ADMIN_ATTACK("admin.attack", 3),
	ADMIN_NOTIFY("admin.notify", 2),
	ADMIN_FLUSH("admin.flush", 2),
	ADMIN_CHANGECONF("admin.changeconf", 3),
	ADMIN_TOGGLE("admin.toggle", 3),
	@Deprecated
	ADMIN_REMKICKPLAYER("admin.remkickplayer", -1),
	@Deprecated
	ADMIN("admin", -1);
	
	String perm = "";
	String baseperm = "";
	int level = 0;
	
	Permissions(String permission, int levelsofpenis) {
		perm = "antibot." + permission;
		baseperm = permission;
		level = levelsofpenis;
	}
	
	public boolean isLevelPermission() {
		return baseperm == ADMIN_ROOT.baseperm || baseperm == ADMIN_PLUS.baseperm || baseperm == ADMIN_BASIC.baseperm; 
	}
	
	public boolean getPermission(Player pl, CommandSender sender) {
		if(!this.getPermission(pl)) {
			sender.sendMessage(Settings.prefix
					+ "\247cSorry, you don't have privileges.");
			return false;
		} else {
			return true;
		}
	}
	
	public boolean getPermission(Player pl) {
		if (pl != null) {

			if (pl.hasPermission(perm)) {
				return true;
			}
			
			if (pl.isOp() && Settings.useOpPerms) {
				return true;
			}
			
			if (pl.isWhitelisted() && Settings.useWhiteListPerms && this.level < 2) {
				return true;
			}
			
			if(isLevelPermission()) {
				return false;
			}

			if(ADMIN_ROOT.getPermission(pl) && level >= 3) {
				return true;
			}
			
			if(ADMIN_PLUS.getPermission(pl) && level < 3) {
				return true;
			}
			
			if(ADMIN_BASIC.getPermission(pl) && level < 2) {
				return true;
			}
			
			return false;
		} else {
			return true; // console.
		}

	}

}
