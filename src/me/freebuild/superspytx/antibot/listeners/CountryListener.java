package me.freebuild.superspytx.antibot.listeners;

import me.freebuild.superspytx.antibot.Core;
import me.freebuild.superspytx.antibot.settings.Settings;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class CountryListener implements Listener {
	
	public Core antibot = null;
	
	public CountryListener(Core instance) {
		antibot = instance;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (!Settings.enabled || antibot.getDataTrack().getCountryTracker().countryBans.size() < 1 || !Settings.geoIP) {
			return;
		}
		antibot.getHandler().getCountryBanHandler().handle(event);
	}

}
