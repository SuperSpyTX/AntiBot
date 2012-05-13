package somebody.is.madbro.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import somebody.is.madbro.AntiBot;
import somebody.is.madbro.settings.Settings;

public class CountryListener implements Listener {
	
	public AntiBot antibot = null;
	
	public CountryListener(AntiBot instance) {
		antibot = instance;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (!Settings.enabled || antibot.getDataTrack().getCountryTracker().countryBans.size() < 1 || !Settings.geoIP) {
			return;
		}
		antibot.getHandler().getCountryBanHandler().handle(event);
	}

}
