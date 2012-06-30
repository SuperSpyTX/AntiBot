package me.freebuild.superspytx.listeners;

import me.freebuild.superspytx.AntiBot;
import me.freebuild.superspytx.settings.Settings;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class CountryListener implements Listener
{

    public AntiBot antibot = null;

    public CountryListener(AntiBot instance)
    {
        antibot = instance;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        if (!Settings.enabled || antibot.getDataTrack().getCountryTracker().countryBans.size() < 1 || !Settings.geoIP)
        {
            return;
        }
        antibot.getHandler().getCountryBanHandler().handle(event);
    }

}
