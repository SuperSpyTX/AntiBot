package me.freebuild.superspytx.ab.callunits;

import me.freebuild.superspytx.ab.AB;
import me.freebuild.superspytx.ab.handlers.Handlers;
import me.freebuild.superspytx.ab.settings.Language;
import me.freebuild.superspytx.ab.settings.Permissions;
import me.freebuild.superspytx.ab.settings.Settings;
import me.freebuild.superspytx.ab.workflow.GD;
import me.freebuild.superspytx.ab.workflow.WorkflowAgent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoginUnit extends CallUnit {
	
	@EventHandler
	public void join(PlayerJoinEvent e) {
		GD.getUpdatedPI(e.getPlayer()); // register their join time....accurately. and fix player object.
		if (WorkflowAgent.dispatchUnit(e, Handlers.BOT, false)) e.setJoinMessage(null);
		
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
		if (WorkflowAgent.dispatchUnit(e, Handlers.COUNTRYBANS, false)) e.disallow(PlayerLoginEvent.Result.KICK_OTHER, Language.countryBanMsg);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void logindelay(PlayerLoginEvent e) {
		if (WorkflowAgent.dispatchUnit(e, Handlers.LOGINDELAY, false)) e.disallow(PlayerLoginEvent.Result.KICK_OTHER, Language.loginDelayMsg);
	}
	
}
