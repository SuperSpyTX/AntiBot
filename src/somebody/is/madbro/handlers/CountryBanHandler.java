package somebody.is.madbro.handlers;

import org.bukkit.event.player.PlayerJoinEvent;

import somebody.is.madbro.AntiBotCore;
import somebody.is.madbro.settings.Settings;

public class CountryBanHandler {

	public AntiBotCore antibot = null;

	public CountryBanHandler(AntiBotCore instance) {
		antibot = instance;
	}

	public void handle(PlayerJoinEvent event) {
		String IP = event.getPlayer().getAddress().toString().split(":")[0].replace("/", "");
		// check against countrybans!
		antibot.getUtility().getDebug().debug("Countrybans size " + antibot.getDataTrack().getCountryTracker().countryBans.size());
		antibot.getUtility().getDebug().debug("Checking IP " + IP);
		String country = antibot.getUtility().getGeoIP().lookupIp(IP);
		antibot.getUtility().getDebug().debug("Country " + country);
		if (antibot.getDataTrack().getCountryTracker().countryBans
				.contains(country)) {
			antibot.getUtility().getDebug().debug("Banned IP " + IP);
			// oh noes! he's in a forbidden country!
			event.getPlayer().kickPlayer(Settings.countryBanMsg);
			// TODO: organize tracker removals/unregisterers.
			antibot.getDataTrack().getBotTracker()
					.removeConnected(event.getPlayer().getName());
			antibot.getDataTrack().getChatTracker().trackplayers.remove(event
					.getPlayer().getName());
			
			if(Settings.banUsers) {
				// well, we have permission to autoIP ban these country invaders.
				// so lets use our powers (evilface)
				antibot.getDataTrack().getBotTracker().autoipkick.add(IP);
			}
		}

		// okie! we're ok!
	}

}
