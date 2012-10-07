package me.freebuild.superspytx.ab.handlers.login;

import org.bukkit.Bukkit;

import me.freebuild.superspytx.ab.AntiBot;
import me.freebuild.superspytx.ab.abs.EventAction;
import me.freebuild.superspytx.ab.abs.Handler;
import me.freebuild.superspytx.ab.abs.PI;
import me.freebuild.superspytx.ab.settings.Settings;
import me.freebuild.superspytx.ab.tils.CaptchaTils;
import me.freebuild.superspytx.ab.tils.MathTils;
import me.freebuild.superspytx.ab.workflow.GD;

public class BotHandler implements Handler
{
    
    @Override
    public boolean run(EventAction info)
    {
        if (!GD.b_cp.contains(info.player.getName()))
            GD.b_cp.add(GD.getPI(info.player));
        
        if ((GD.getPI(info.player)).b_connectfor == 0L)
            (GD.getPI(info.player)).b_connectfor = System.currentTimeMillis();
        
        if (GD.b_lc != 0L)
        {
            if (MathTils.getLongDiff(GD.b_lc) < Settings.interval)
            {
                GD.b_cts++;
                if (GD.b_cts >= Settings.accounts)
                {
                    // TODO: Perform Action - Connection Throttled.
                    return true;
                }
            }
            else
            {
                GD.b_cts = 1;
                GD.b_lc = System.currentTimeMillis();
            }
        }
        else
        {
            GD.b_lc = System.currentTimeMillis();
        }

        return false;
    }

    @Override
    public void performActions(EventAction info)
    {
        for (final PI pl : GD.b_cp)
        {
            if (MathTils.getLongDiff(pl.b_connectfor) < Settings.connectFor)
            {
                Bukkit.getScheduler().scheduleSyncDelayedTask(AntiBot.getInstance(), new Runnable() {
                    public void run()
                    {
                    	if(Settings.captchaEnabled && Settings.forceCaptchaOnBotSpam) {
                    		if(Bukkit.getPlayerExact(pl.pl.getName()) != null)
                    		{
                    			CaptchaTils.sendCaptchaToPlayer(pl.pl);
                    		}
                    	} else {
                        if(Bukkit.getPlayerExact(pl.pl.getName()) != null)
                            pl.pl.kickPlayer(Settings.kickMsg);
                    	}
                    }
                }, 20L);
            }
        }
        GD.b_cp.clear();
    }

}
