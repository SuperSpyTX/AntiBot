package somebody.is.madbro.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import somebody.is.madbro.AntiBotCore;
import somebody.is.madbro.settings.Settings;

public class ChatListener implements Listener {
	
	public AntiBotCore antibot = null;
	
	public ChatListener(AntiBotCore instance) {
		antibot = instance;
	}
	

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		if (!Settings.enabled) {
			return;
		}
		antibot.getHandler().getChatSpamHandler().handle(event.getPlayer(), (PlayerChatEvent) event);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(PlayerChatEvent event) {
		if (!Settings.enabled) {
			return;
		}
		antibot.getHandler().getChatSpamHandler().handle(event.getPlayer(), (PlayerChatEvent) event);
	}

}
