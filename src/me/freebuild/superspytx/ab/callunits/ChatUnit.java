package me.freebuild.superspytx.ab.callunits;

import me.freebuild.superspytx.ab.handlers.Handlers;
import me.freebuild.superspytx.ab.workflow.GD;
import me.freebuild.superspytx.ab.workflow.WorkflowAgent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ChatUnit extends CallUnit
{
    @EventHandler(priority = EventPriority.HIGH)
    public void chat(AsyncPlayerChatEvent e)
    {
        if (WorkflowAgent.dispatchUnit(e, Handlers.CHATSPAM, true))
            e.setCancelled(true);
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void cmd(PlayerCommandPreprocessEvent e)
    {
        if (WorkflowAgent.dispatchUnit(e, Handlers.CHATSPAM, true))
            e.setCancelled(true);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void highest(AsyncPlayerChatEvent e)
    {
        if(WorkflowAgent.dispatchUnit(e, Handlers.CHATFLOW, true))
            e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void low(AsyncPlayerChatEvent e)
    {
        if (GD.cf_gm)
            e.setCancelled(true);
    }
}
