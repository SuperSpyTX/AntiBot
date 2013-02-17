package com.superspytx.ab.callunits;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import com.superspytx.ab.settings.Language;
import com.superspytx.ab.workflow.GD;

public class GarbageUnit extends CallUnit {
	
	@EventHandler
	public void kick(PlayerKickEvent e) {
		if (e.getReason().equalsIgnoreCase(Language.kickMsg)) e.setLeaveMessage(null);
		
		GD.unregisterPI(e.getPlayer());
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent e) {
		GD.unregisterPI(e.getPlayer());
	}
}
