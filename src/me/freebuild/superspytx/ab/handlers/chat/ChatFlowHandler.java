package me.freebuild.superspytx.ab.handlers.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import me.freebuild.superspytx.ab.AB;
import me.freebuild.superspytx.ab.AntiBot;
import me.freebuild.superspytx.ab.abs.EventAction;
import me.freebuild.superspytx.ab.abs.Handler;
import me.freebuild.superspytx.ab.settings.Settings;
import me.freebuild.superspytx.ab.tils.MathTils;
import me.freebuild.superspytx.ab.tils.StringTils;
import me.freebuild.superspytx.ab.workflow.GD;

public class ChatFlowHandler implements Handler
{

    @Override
    public boolean run(EventAction info)
    {
        if (GD.cf_gm)
            return false;
        if (info.message == null)
            return false;
        if ((GD.getPI(info.player).cs_trig))
            return false; // triggered spam? don't count towards chat flow.
        AB.log("Chat Flow debug");
        if (GD.cf_lmt != 0L)
        {
            AB.log("CF_LMT > 0");
            int ct = 0;
            if (StringTils.strDiffCounter(info.message, GD.cf_lm) < Settings.spamdiffct && !GD.cf_lp.equalsIgnoreCase(info.player.getName()))
            {
                AB.log("Str diff: " + StringTils.strDiffCounter(info.message, GD.cf_lm));
                GD.cf_cts++;
                ct++;
                AB.log("VL: " + GD.cf_cts);
            }
            if (MathTils.getLongDiff(GD.cf_lmt) < Settings.timetooverflow)
            {
                AB.log("Time diff: " + MathTils.getLongDiff(GD.cf_lmt));
                GD.cf_cts++;
                ct++;
                AB.log("VL: " + GD.cf_cts);
            }
            if (GD.cf_cts >= Settings.overflows)
            {
                AB.log("Chat overflow! " + GD.cf_cts);
                AB.log("VL: " + GD.cf_cts);
                // TODO: Perform Action - Chat Overflow.
                return true;
            }
            if (ct < 1 && GD.cf_cts > 0)
            {
                GD.cf_cts -= 1;
            }
            AB.log("ChatFlow VL: " + GD.cf_cts);
        }
        AB.log("Chat not overflowed.");
        GD.cf_lmt = System.currentTimeMillis();
        GD.cf_lm = info.message;
        AB.log("Last message: " + info.message + " time: " + GD.cf_lmt);
        if (!GD.cf_lp.equalsIgnoreCase(info.player.getName()) || GD.cf_lp.length() == 0)
        {
            AB.log("Last player: " + GD.cf_lp);
            GD.cf_lp = info.player.getName();
        }
        return false;
    }

    @Override
    public void performActions(EventAction info)
    {
        if (GD.cf_gm)
            return;
        GD.cf_gm = true;
        Bukkit.getScheduler().scheduleAsyncDelayedTask(AntiBot.getInstance(), new Runnable()
        {

            @Override
            public void run()
            {
                Bukkit.broadcastMessage(Settings.prefix + ChatColor.RED + Settings.overflowedmessage.replace("%sec%", Long.toString(GD.cf_ttmf)));
            }

        }, 40L);
        Bukkit.getScheduler().scheduleAsyncDelayedTask(AntiBot.getInstance(), new Runnable()
        {

            @Override
            public void run()
            {
                GD.cf_gm = false;
                GD.cf_cts = 0;
                GD.cf_lm = "";
                GD.cf_lp = "";
                GD.cf_lmt = 0L;
                GD.cf_ttmf += 5L;
                Bukkit.broadcastMessage(Settings.prefix + ChatColor.GREEN + "Chat has been unmuted!");
            }

        }, 20L * GD.cf_ttmf);
    }

}
