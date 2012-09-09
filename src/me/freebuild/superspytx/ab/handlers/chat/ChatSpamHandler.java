package me.freebuild.superspytx.ab.handlers.chat;

import me.freebuild.superspytx.ab.AB;
import me.freebuild.superspytx.ab.abs.EventAction;
import me.freebuild.superspytx.ab.abs.Handler;
import me.freebuild.superspytx.ab.abs.PI;
import me.freebuild.superspytx.ab.settings.Settings;
import me.freebuild.superspytx.ab.tils.MathTils;
import me.freebuild.superspytx.ab.tils.StringTils;
import me.freebuild.superspytx.ab.workflow.GD;

public class ChatSpamHandler implements Handler
{
    @Override
    public boolean run(EventAction info)
    {
        if (info.message == null)
            return false;

        PI pli = GD.getPI(info.player);
        pli.cs_trig = false;
        if (MathTils.getLongDiff(pli.b_connectfor) < 5000L)
            pli.cs_ct+= 2;
        
        AB.log("Chat spam check for " + info.player.getName());
        if (pli.cs_lmt != 0L)
        {
            AB.log("CS_LMT > 0");
            int ct = 0;
            if (StringTils.strDiffCounter(info.message, pli.cs_lm) < Settings.spamdiffct)
            {
                AB.log("Str Diff L: " + StringTils.strDiffCounter(info.message, pli.cs_lm));
                pli.cs_ct++;
                ct++;
                AB.log("VL: " + pli.cs_ct);
            }
            if (StringTils.strDiffCounter(info.message, GD.cf_lm) < 1)
            {
                AB.log("Str Diff G: " + StringTils.strDiffCounter(info.message, pli.cs_lm));
                pli.cs_ct+= 1337;
                ct+=1337;
                AB.log("VL: " + pli.cs_ct);
            }
            if (MathTils.getLongDiff(pli.cs_lmt) < Settings.spamtime)
            {
                AB.log("Time diff: " + MathTils.getLongDiff(pli.cs_lmt));
                pli.cs_ct++;
                ct++;
                AB.log("VL: " + pli.cs_ct);
            } else if (MathTils.consistency(pli, pli.cs_lmt))
            {
                pli.cs_ct++;
                ct++;
                AB.log("TC VL: " + pli.cs_ct);
            }

            if (pli.cs_ct >= Settings.spamam)
            {
                // TODO: Perform Action - Chat Message Spam.
                AB.log("Chat spam detected!");
                AB.log("VL: " + pli.cs_ct);
                pli.cs_trig = true;
                return true;
            }
            
            if (ct < 1 && pli.cs_ct > 0)
            {
                AB.log("Decrease for ChatSpam");
                pli.cs_ct -= 1;
            }
            AB.log("ChatSpam VL: " + pli.cs_ct);
        }
        AB.log("Chat Spam not detected");
        pli.cs_lm = info.message;
        pli.cs_lmt = System.currentTimeMillis();
        AB.log("Last Message: " + pli.cs_lm + " time: " + pli.cs_lmt);
        return false;
    }

    @Override
    public void performActions(EventAction info)
    {
        info.player.kickPlayer(Settings.kickMsg);
    }

}
