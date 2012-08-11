package me.freebuild.superspytx.ab.listeners;

import me.freebuild.superspytx.ab.AntiBot;
import me.freebuild.superspytx.ab.settings.Settings;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ChatListener implements Listener
{

    public AntiBot antibot = null;

    public ChatListener(AntiBot instance)
    {
        antibot = instance;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerCommandLow(PlayerCommandPreprocessEvent event)
    {
        if (!Settings.enabled)
            return;

        if (antibot.getHandler().getCaptchaHandler().hasUnsolvedPuzzle(event.getPlayer()))
        {
            event.setCancelled(true);
            return;
        }
        
        event.setCancelled(antibot.getHandler().getChatSpamHandler().handle(event.getPlayer()));
        
        if(!event.isCancelled())
            event.setCancelled(antibot.getHandler().getChatFlowHandler().handle(event.getPlayer()));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChatLow(AsyncPlayerChatEvent event)
    {
        if (!Settings.enabled)
            return;

        if (antibot.getHandler().getCaptchaHandler().hasUnsolvedPuzzle(event.getPlayer()))
        {
            antibot.getHandler().getCaptchaHandler().handle(event);
            event.setCancelled(true);
            return;
        }
        
        event.setCancelled(antibot.getHandler().getChatSpamHandler().handle(event.getPlayer()));

        if(!event.isCancelled())
            event.setCancelled(antibot.getHandler().getChatFlowHandler().handle(event.getPlayer()));
    }

}
