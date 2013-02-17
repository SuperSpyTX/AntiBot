package com.superspytx.ab.abs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import com.superspytx.ab.workflow.GD;

public class EventAction {
	public Player player;
	public PI pi;
	public String message;
	public String ip;
	public boolean async = false;
	public boolean cancelled = false;
	
	/* for command events */
	public CommandSender sender;
	public Command cmd;
	public String label;
	public String[] args;
	
	
	public EventAction(Event e, boolean isasync) {
		// this is a big one.
		
		if (e instanceof CommandEvent) {
			CommandEvent g = (CommandEvent) e;
			sender = g.sender;
			cmd = g.cmd;
			label = g.label;
			args = g.args;
			async = isasync;
			return;
		}
		
		if (e instanceof PlayerEvent) {
			player = ((PlayerEvent) e).getPlayer();
			pi = GD.getUpdatedPI(player);
		}
		try {
			message = ((AsyncPlayerChatEvent) e).getMessage();
		} catch (Exception allspambotsarezedsdead) {}
		
		try {
			message = ((PlayerCommandPreprocessEvent) e).getMessage();
		} catch (Exception allspambotsarezedsdead) {}
		
		try {
			ip = ((PlayerLoginEvent) e).getAddress().toString().split(":")[0].replace("/", "");
		} catch (Exception allspambotsarezedsdead) {}
		
		
		if (e instanceof Cancellable) {
			if (((Cancellable) e).isCancelled()) cancelled = true;
			
			if (message == null) cancelled = true;
		}
		
		async = isasync;
	}
}
