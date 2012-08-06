package me.freebuild.superspytx.ab.listeners;

import me.freebuild.superspytx.ab.AntiBot;
import me.freebuild.superspytx.ab.settings.Settings;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginListener implements Listener
{

    public AntiBot antibot = null;

    public LoginListener(AntiBot instance)
    {
        antibot = instance;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerLogin(PlayerLoginEvent event)
    {
        antibot.getHandler().getLoginDelayHandler().handle(event);
        if (!Settings.enabled || antibot.getDataTrack().getLoginTracker().countryBans.size() < 1 || !Settings.geoIP)
        {
            return;
        }
        antibot.getHandler().getCountryBanHandler().handle(event);
    }

}
