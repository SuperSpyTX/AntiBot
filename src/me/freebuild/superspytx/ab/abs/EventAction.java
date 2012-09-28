package me.freebuild.superspytx.ab.abs;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class EventAction
{
    public Player player;
    public String message;
    public String ip;
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
        
        try
        {
            message = ((PlayerCommandPreprocessEvent) e).getMessage();
        }
        catch (Exception allspambotsarezedsdead)
        {
        }
        
        try
        {
            ip = ((PlayerLoginEvent) e).getAddress().toString().split(":")[0].replace("/", "");
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
