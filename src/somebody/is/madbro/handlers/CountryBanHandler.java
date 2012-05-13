package somebody.is.madbro.handlers;

import org.bukkit.event.player.PlayerJoinEvent;

import somebody.is.madbro.AntiBot;
import somebody.is.madbro.settings.Settings;

public class CountryBanHandler {

	public AntiBot antibot = null;

	public CountryBanHandler(AntiBot instance) {
		antibot = instance;
	}

	public void handle(PlayerJoinEvent event) {
		String IP = event.getPlayer().getAddress().toString().split(":")[0].replace("/", "");
		// check against countrybans!
		antibot.getUtility().getDebug().debug("Countrybans size " + antibot.getDataTrack().getCountryTracker().countryBans.size());
		antibot.getUtility().getDebug().debug("Checking IP " + IP);
		if (antibot.getUtility().getGeoIP().determineFateForIP(IP)) {
			antibot.getUtility().getDebug().debug("Banned IP " + IP);
			antibot.getDataTrack().getCountryTracker().countryusersblocked += 1;
			// oh noes! he's in a forbidden country!
			event.getPlayer().kickPlayer(Settings.countryBanMsg);
			
			if(Settings.banUsers) {
				// well, we have permission to autoIP ban these country invaders.
				// so lets use our powers (evilface)
				antibot.getDataTrack().getBotTracker().autoipkick.add(IP);
			}
		}

		// okie! we're ok!
	}

}
