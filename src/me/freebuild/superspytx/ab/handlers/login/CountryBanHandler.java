package me.freebuild.superspytx.ab.handlers.login;

import me.freebuild.superspytx.ab.AntiBot;
import me.freebuild.superspytx.ab.settings.Permissions;
import me.freebuild.superspytx.ab.settings.Settings;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class CountryBanHandler
{

    public AntiBot antibot = null;

    public CountryBanHandler(AntiBot instance)
    {
        antibot = instance;
    }

    public void handle(PlayerLoginEvent event)
    {
        // add countrybans bypass permissions.
        if (Permissions.COUNTRYBAN.getPermission(event.getPlayer()))
        {
            return;
        }
        String IP = event.getAddress().toString().split(":")[0].replace("/", "");
        // check against countrybans!
        antibot.getUtility().getDebug().debug("Countrybans size " + antibot.getDataTrack().getLoginTracker().countryBans.size());
        antibot.getUtility().getDebug().debug("Checking IP " + IP);
        if (antibot.getUtility().getGeoIP().determineFateForIP(IP))
        {
            antibot.getUtility().getDebug().debug("Banned IP " + IP);
            antibot.getDataTrack().getLoginTracker().countryusersblocked += 1;
            // oh noes! he's in a forbidden country!
            event.disallow(Result.KICK_OTHER, Settings.countryBanMsg);

            if (Settings.banUsers)
            {
                // well, we have permission to autoIP ban these country invaders.
                // so lets use our powers (evilface)
                antibot.getDataTrack().getLoginTracker().autoipkick.add(IP);
            }
        }

        // okie! we're ok!
    }

}
