package me.freebuild.superspytx.ab.callunits;

import me.freebuild.superspytx.ab.handlers.Handlers;
import me.freebuild.superspytx.ab.settings.Settings;
import me.freebuild.superspytx.ab.workflow.GD;
import me.freebuild.superspytx.ab.workflow.WorkflowAgent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoginUnit extends CallUnit
{
    
    @EventHandler
    public void join(PlayerJoinEvent e)
    {
        GD.getPI(e.getPlayer()); // register their join time....accurately.
        if(WorkflowAgent.dispatchUnit(e, Handlers.BOT, false))
            e.setJoinMessage(null);
    }
    
    @EventHandler
    public void country(PlayerLoginEvent e)
    {
        if(WorkflowAgent.dispatchUnit(e, Handlers.COUNTRYBANS, true))
            e.disallow(PlayerLoginEvent.Result.KICK_BANNED, Settings.countryBanMsg); 
    }

}
