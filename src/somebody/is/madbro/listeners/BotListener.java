package somebody.is.madbro.listeners;

//import org.bukkit.event.player.PlayerChatEvent;
//import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import somebody.is.madbro.AntiBotCore;
import somebody.is.madbro.settings.Settings;

public class BotListener implements Listener {

	public AntiBotCore antibot = null;
	
	public BotListener(AntiBotCore instance) {
		antibot = instance;
	}
	

	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		if (!Settings.enabled) {
			return;
		}
		antibot.getHandler().getBotHandler().onPlayerKick(event);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerMove(PlayerMoveEvent event) {
		if (!Settings.enabled) {
			return;
		}
		antibot.getHandler().getBotHandler().onPlayerMove(event);
	}

	// falsified antibot trigger bug fix, or brolos bug fix.
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuit(PlayerQuitEvent event) {
		if (!Settings.enabled) {
			return;
		}
		antibot.getHandler().getBotHandler().onPlayerQuit(event);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (!Settings.enabled) {
			return;
		}
		antibot.getHandler().getBotHandler().onPlayerJoin(event);
	}
}
