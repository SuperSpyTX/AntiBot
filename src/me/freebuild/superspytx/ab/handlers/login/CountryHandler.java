package me.freebuild.superspytx.ab.handlers.login;

import me.freebuild.superspytx.ab.AB;
import me.freebuild.superspytx.ab.abs.EventAction;
import me.freebuild.superspytx.ab.abs.Handler;
import me.freebuild.superspytx.ab.tils.GeoTils;

public class CountryHandler implements Handler
{

    @Override
    public boolean run(EventAction info)
    {
        if(info.ip == null)
        {
            AB.log("CB: IP is null.");
            return false;
        }
        
        return GeoTils.skidtector(info.ip);
    }

    @Override
    public void performActions(EventAction info)
    {
        // do nothing.  the player is already denied access.
    }

}
