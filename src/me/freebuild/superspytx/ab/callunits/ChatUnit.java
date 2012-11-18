package me.freebuild.superspytx.ab.callunits;

import me.freebuild.superspytx.ab.handlers.Handlers;
import me.freebuild.superspytx.ab.workflow.GD;
import me.freebuild.superspytx.ab.workflow.WorkflowAgent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ChatUnit extends CallUnit {
	@EventHandler(priority = EventPriority.HIGH)
	public void chat(AsyncPlayerChatEvent e) {
		if (WorkflowAgent.dispatchUnit(e, Handlers.CHATSPAM, true)) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void cmd(PlayerCommandPreprocessEvent e) {
		if (WorkflowAgent.dispatchUnit(e, Handlers.CHATSPAM, true)) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
	public void highest(AsyncPlayerChatEvent e) {
		if (WorkflowAgent.dispatchUnit(e, Handlers.CHATFLOW, false)) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void low(AsyncPlayerChatEvent e) {
		if (GD.cf_gm) e.setCancelled(true);
		
		if ((GD.getPI(e.getPlayer()).cp_haspuzzle)) {
			WorkflowAgent.asyncDispatchUnit(e, Handlers.CAPTCHA, true);
			e.setCancelled(true);
		}
	}
}
