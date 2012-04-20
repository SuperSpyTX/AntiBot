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
		antibot.getHandler().getBotHandler().onPlayerKick(event);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerPreLogin(PlayerPreLoginEvent event) {
		antibot.getHandler().getBotHandler().onPlayerPreLogin(event);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		antibot.getHandler().getChatSpamHandler().handle(event.getPlayer(), (PlayerChatEvent) event);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(PlayerChatEvent event) {
		antibot.getHandler().getChatSpamHandler().handle(event.getPlayer(), (PlayerChatEvent) event);
	}

	// falsified antibot trigger bug fix, or brolos bug fix.
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		antibot.getHandler().getBotHandler().onPlayerQuit(event);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		antibot.getHandler().getBotHandler().onPlayerJoin(event);
	}
}
