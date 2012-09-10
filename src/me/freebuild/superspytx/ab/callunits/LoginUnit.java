package me.freebuild.superspytx.ab.callunits;

import me.freebuild.superspytx.ab.handlers.Handlers;
import me.freebuild.superspytx.ab.workflow.GD;
import me.freebuild.superspytx.ab.workflow.WorkflowAgent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoginUnit extends CallUnit
{
    
    @EventHandler
    public void join(PlayerJoinEvent e)
    {
        GD.getPI(e.getPlayer()); // register their join time....accurately.
        if(WorkflowAgent.dispatchUnit(e, Handlers.BOT, false))
        {
            e.setJoinMessage(null);
        } 
    }

}
