package me.freebuild.superspytx.listeners;

//import org.bukkit.event.player.PlayerChatEvent;
//import org.bukkit.event.player.PlayerJoinEvent;
import me.freebuild.superspytx.AntiBot;
import me.freebuild.superspytx.settings.Settings;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class BotListener implements Listener {

	public AntiBot antibot = null;
	
	public BotListener(AntiBot instance) {
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
