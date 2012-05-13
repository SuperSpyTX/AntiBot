package somebody.is.madbro.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import somebody.is.madbro.AntiBot;
import somebody.is.madbro.settings.Permissions;
import somebody.is.madbro.settings.Settings;

public class UpdateListener implements Listener {
	
	public AntiBot antibot = null;
	
	public UpdateListener(AntiBot instance) {
		antibot = instance;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void handle(PlayerJoinEvent event) {
		// updates notify
		if (antibot.getUpdates().newVersion
				&& Permissions.ADMIN_NOTIFY.getPermission(event
						.getPlayer())) {
			event.getPlayer()
					.sendMessage(
							Settings.prefix
									+ "\247a"
									+ "There is currently a new update for AntiBot!");
			event.getPlayer().sendMessage(
					Settings.prefix + "\247a" + "New version: v"
							+ antibot.getUpdates().version
							+ " Your version: v"
							+ antibot.getVersion());
			event.getPlayer()
					.sendMessage(
							Settings.prefix
									+ "\247a"
									+ "Check at http://goo.gl/FuMrd");
		}
		// priority delayed start message
		if(Settings.delayedStart && !Settings.enabled) {
			if(Permissions.ADMIN_NOTIFY.getPermission(event.getPlayer())) {
				event.getPlayer().sendMessage(Settings.prefix + "\247c" + "System is currently having a " + "\247e" + "delayed start.");
			}
		}
	}

}
