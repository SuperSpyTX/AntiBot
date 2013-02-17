package com.superspytx.ab.callunits;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import com.superspytx.ab.AB;
import com.superspytx.ab.handlers.Handlers;
import com.superspytx.ab.settings.Language;
import com.superspytx.ab.settings.Permissions;
import com.superspytx.ab.settings.Settings;
import com.superspytx.ab.workflow.Agent;
import com.superspytx.ab.workflow.GD;

public class LoginUnit extends CallUnit {
	
	@EventHandler
	public void join(PlayerJoinEvent e) {
		GD.getUpdatedPI(e.getPlayer()); // register their join time....accurately. and fix player object.
		if (Agent.dispatchUnit(e, Handlers.BOT, false)) e.setJoinMessage(null);
		
		/* Admin Notify */
		if (Permissions.ADMIN_NOTIFY.getPermission(e.getPlayer())) {
			if (Settings.delayingStart) {
				e.getPlayer().sendMessage(Language.prefix + Language.adminDSNotify);
			}
			
			if (Settings.newVersion) {
				if (!AB.isDevelopment()) {
					e.getPlayer().sendMessage(Language.prefix + Language.adminNVNotify.replace("&nv&", Settings.version).replace("&ov&", AB.getVersion()));
				} else {
					e.getPlayer().sendMessage(Language.prefix + Language.adminNBNotify.replace("&nv&", Settings.version).replace("&ov&", Integer.toString(AB.getBuildNumber())));
				}
			}
		}
	}
	
	@EventHandler
	public void country(PlayerLoginEvent e) {
		if (Agent.dispatchUnit(e, Handlers.COUNTRYBANS, false)) e.disallow(PlayerLoginEvent.Result.KICK_OTHER, Language.countryBanMsg);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void logindelay(PlayerLoginEvent e) {
		if (Agent.dispatchUnit(e, Handlers.LOGINDELAY, false)) e.disallow(PlayerLoginEvent.Result.KICK_OTHER, Language.loginDelayMsg);
	}
	
}
