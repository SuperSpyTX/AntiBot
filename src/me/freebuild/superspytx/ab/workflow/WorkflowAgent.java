package me.freebuild.superspytx.ab.workflow;

import me.freebuild.superspytx.ab.AntiBot;
import me.freebuild.superspytx.ab.abs.EventAction;
import me.freebuild.superspytx.ab.handlers.Handlers;
import me.freebuild.superspytx.ab.settings.Settings;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class WorkflowAgent
{

    public static boolean dispatchUnit(Event event, Handlers handle, boolean handleAnyways)
    {
        if(!Settings.enabled) return false;
        if((new EventAction(event, false).cancelled) && !handleAnyways)
            return false;
        
        if(!handle.checkUser((new EventAction(event, false)).player)) return false;
        
        if(handle.getHandler().run(new EventAction(event, false))) {
           handle.getHandler().performActions(new EventAction(event, false));
           return true;
        }
        return false;
    }

    public static void asyncDispatchUnit(final Event event, final Handlers handle)
    {
        /* If you know this isn't "asynchronous", I apologize, but that's the title of the method anyways :3 */
        /* This is mainly for stuff that is going to count against the player, or stuff that won't cancel the event. */
        if(!Settings.enabled) return;
        Bukkit.getScheduler().scheduleAsyncDelayedTask(AntiBot.getInstance(), new Runnable() {
            public void run()
            {
                if(!Settings.enabled) return;
                if((new EventAction(event, true).cancelled))
                    return;
                
                if(!handle.checkUser((new EventAction(event, true)).player)) return;
                
                if(handle.getHandler().run(new EventAction(event, true))) {
                   handle.getHandler().performActions(new EventAction(event, true));
                }
            }
        }, 20L);
    }
}
