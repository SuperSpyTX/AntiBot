package me.freebuild.superspytx.ab.handlers.chat;

import me.freebuild.superspytx.ab.abs.EventAction;
import me.freebuild.superspytx.ab.abs.Handler;
import me.freebuild.superspytx.ab.abs.PI;
import me.freebuild.superspytx.ab.workflow.GD;

public class CaptchaHandler implements Handler
{

    @Override
    public boolean run(EventAction info)
    {
        PI pli = GD.getPI(info.player);
        if(pli.cp_haspuzzle)
        {
            if(pli.cp_puzzle.overboard())
                return true;
            else
            {
                if(pli.cp_puzzle.isVersion2())
                {
                    
                } else {
                    
                }
            }
        }
        return false;
    }

    @Override
    public void performActions(EventAction info)
    {
        
    }

}
