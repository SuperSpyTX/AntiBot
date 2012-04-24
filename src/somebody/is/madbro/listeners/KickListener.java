package somebody.is.madbro.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import somebody.is.madbro.AntiBotCore;

public class KickListener implements Listener {
	
	public AntiBotCore antibot = null;
	
	public KickListener(AntiBotCore instance) {
		antibot = instance;
	}
	
	@EventHandler
	public void handle(PlayerKickEvent event) {
		antibot.getUtility().getDeregister().handle(event.getPlayer());
	}
	
	@EventHandler
	public void handle(PlayerQuitEvent event) {
		antibot.getUtility().getDeregister().handle(event.getPlayer());
	}

}
