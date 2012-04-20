package somebody.is.madbro.listeners;

//import org.bukkit.event.player.PlayerChatEvent;
//import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import somebody.is.madbro.AntiBotCore;

public class BotListener implements Listener {

	public AntiBotCore antibot = null;
	
	public BotListener(AntiBotCore instance) {
		antibot = instance;
	}
	

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerKick(PlayerKickEvent event) {
		
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerPreLogin(PlayerPreLoginEvent event) {
		
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(PlayerChatEvent event) {
		
	}

	// falsified antibot trigger bug fix, or brolos bug fix.
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
	}
}
