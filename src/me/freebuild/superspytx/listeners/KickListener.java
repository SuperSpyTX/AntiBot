package me.freebuild.superspytx.listeners;

import me.freebuild.superspytx.AntiBot;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class KickListener implements Listener
{

    public AntiBot antibot = null;

    public KickListener(AntiBot instance)
    {
        antibot = instance;
    }

    @EventHandler
    public void handle(PlayerKickEvent event)
    {
        antibot.getUtility().getDeregister().handle(event.getPlayer());
    }

    @EventHandler
    public void handle(PlayerQuitEvent event)
    {
        antibot.getUtility().getDeregister().handle(event.getPlayer());
    }

}
