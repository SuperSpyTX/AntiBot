package me.freebuild.superspytx.ab.handlers.chat;

import me.freebuild.superspytx.ab.abs.EventAction;
import me.freebuild.superspytx.ab.abs.PI;
import me.freebuild.superspytx.ab.handlers.Handler;
import me.freebuild.superspytx.ab.settings.Settings;
import me.freebuild.superspytx.ab.utils.MathUtils;
import me.freebuild.superspytx.ab.utils.StringCompare;
import me.freebuild.superspytx.ab.workflow.GD;

public class ChatSpamHandler implements Handler
{
    @Override
    public boolean run(EventAction info)
    {
        if(info.message == null) return false;
        PI pli = GD.getPI(info.player);
        if(pli.cs_lmt != 0L)
        {
            if(StringCompare.strDiffCounter(info.message, pli.cs_lm) < Settings.spamdiffct)
            {
                // TODO: Perform Action - Chat Message Spam.
                return true;
            }
            if(MathUtils.getLongDiff(pli.cs_lmt) < Settings.spamtime)
            {
                pli.cs_ct++;
                if(pli.cs_ct >= Settings.spamam)
                {
                    // TODO: Perform Action - Chat Message Spam.
                    return true;
                }
            } else {
                pli.cs_ct = 0;
            }
        }
        pli.cs_lm = info.message;
        pli.cs_lmt = System.currentTimeMillis();
        return false;
    }

    @Override
    public void performActions(EventAction info)
    {
        info.player.kickPlayer("Y U SO RETARDED KTHXBAI!");
    }

}
