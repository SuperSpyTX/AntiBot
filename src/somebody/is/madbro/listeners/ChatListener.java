package somebody.is.madbro.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import somebody.is.madbro.AntiBot;
import somebody.is.madbro.settings.Settings;

public class ChatListener implements Listener {
	
	public AntiBot antibot = null;
	
	public ChatListener(AntiBot instance) {
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
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerCommandLow(PlayerCommandPreprocessEvent event) {
		if (!Settings.enabled) {
			return;
		}
		antibot.getHandler().getChatFlowHandler().handle((PlayerChatEvent) event);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerChatLow(PlayerChatEvent event) {
		if (!Settings.enabled) {
			return;
		}
		antibot.getHandler().getChatFlowHandler().handle((PlayerChatEvent) event);
	}

}
