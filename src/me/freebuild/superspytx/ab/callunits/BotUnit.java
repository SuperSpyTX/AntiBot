package me.freebuild.superspytx.ab.callunits;

import me.freebuild.superspytx.ab.AntiBot;
import me.freebuild.superspytx.ab.handlers.Handlers;
import me.freebuild.superspytx.ab.workflow.WorkflowAgent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class BotUnit extends CallUnit
{
    
    @EventHandler
    public void join(PlayerJoinEvent e)
    {
        if(WorkflowAgent.dispatchUnit(e, Handlers.BOT))
        {
            e.setJoinMessage(null);
        }
    }
    
    @EventHandler
    public void chat(AsyncPlayerChatEvent e)
    {
        if(WorkflowAgent.dispatchUnit(e, Handlers.CHAT))
        {
            e.setCancelled(true);
        }
    }

}
