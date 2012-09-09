package me.freebuild.superspytx.ab.abs;

import me.freebuild.superspytx.ab.AntiBot;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;

public class EventAction
{
    public Player player;
    public String message;
    public boolean async = false;
    public boolean cancelled = false;

    public EventAction(Event e, boolean isasync)
    {
        // this is a big one.
        if (e instanceof PlayerEvent)
        {
            player = ((PlayerEvent) e).getPlayer();
        }
        try
        {
            message = ((AsyncPlayerChatEvent) e).getMessage();
        }
        catch (Exception allspambotsarezedsdead)
        {
        }
        
        if(e instanceof Cancellable)
        {
            if(((Cancellable) e).isCancelled())
                cancelled = true;
        }
        async = isasync;
    }
}
