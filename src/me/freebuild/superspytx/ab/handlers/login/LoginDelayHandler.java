package me.freebuild.superspytx.ab.handlers.login;

import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import me.freebuild.superspytx.ab.AntiBot;
import me.freebuild.superspytx.ab.settings.Permissions;
import me.freebuild.superspytx.ab.settings.Settings;

public class LoginDelayHandler
{
    public AntiBot antibot = null;

    public LoginDelayHandler(AntiBot instance)
    {
        antibot = instance;
    }

    public void handle(PlayerLoginEvent event)
    {
        antibot.getUtility().getDebug().debug("Login Delay!");
        if (!Settings.enabled || !Settings.logindelayEnabled)
            return;

        if (Permissions.LOGINDELAY.getPermission(event.getPlayer()))
            return;

        antibot.getUtility().getDebug().debug("Checking login delay stuff");

        // already a hold?
        if (antibot.getDataTrack().getLoginTracker().loginholds.containsKey(event.getPlayer().getName()))
        {
            if ((antibot.getDataTrack().getLoginTracker().loginholds.get(event.getPlayer().getName()) - System.currentTimeMillis()) > 0)
                event.disallow(Result.KICK_OTHER, ("Too fast! You must wait %sec%").replace("%sec%", antibot.getUtility().getBot().getFriendlyTimeLeft(antibot.getDataTrack().getLoginTracker().loginholds.get(event.getPlayer().getName()))));
            else
                antibot.getDataTrack().getLoginTracker().loginholds.remove(event.getPlayer().getName());

            return;
        }

        if (!antibot.getDataTrack().getLoginTracker().lastlogin.containsKey(event.getPlayer().getName()))
        {
            antibot.getDataTrack().getLoginTracker().lastlogin.put(event.getPlayer().getName(), System.currentTimeMillis());
            return;
        }

        antibot.getDataTrack().getLoginTracker().lastlogin.put(event.getPlayer().getName(), System.currentTimeMillis());

        // let's calculate.
        long math = (System.currentTimeMillis() - antibot.getDataTrack().getLoginTracker().lastlogin.get(event.getPlayer().getName()));
        antibot.getUtility().getDebug().debug("Math: " + math);
        // has it been longer than 6 minutes since last login?
        if (math > 360000)
            antibot.getDataTrack().getLoginTracker().lastlogin.remove(event.getPlayer().getName());
        else
        {
            if (math < Settings.loginDelay)
            {
                // add a hold.
                antibot.getUtility().getDebug().debug("HOLDING! FLAG!");
                // but make sure there is tracked data first.
                if (antibot.getDataTrack().getLoginTracker().loginsets.containsKey(event.getPlayer().getName()))
                {
                    antibot.getDataTrack().getLoginTracker().loginsets.put(event.getPlayer().getName(), antibot.getDataTrack().getLoginTracker().loginsets.get(event.getPlayer().getName()) + 1);
                }
                else
                {
                    antibot.getDataTrack().getLoginTracker().loginsets.put(event.getPlayer().getName(), 1);
                }

                int calculatedhold = Settings.loginHold;

                if (antibot.getDataTrack().getLoginTracker().loginholds.containsKey(event.getPlayer().getName()))
                {
                    // determine the punishment for the player's instincts.
                    calculatedhold = calculatedhold + 5000 * antibot.getDataTrack().getLoginTracker().loginsets.get(event.getPlayer().getName());
                }

                antibot.getDataTrack().getLoginTracker().loginholds.put(event.getPlayer().getName(), (System.currentTimeMillis() + calculatedhold));
                event.disallow(Result.KICK_OTHER, ("Too fast! You must wait %sec%").replace("%sec%", antibot.getUtility().getBot().getFriendlyTimeLeft(antibot.getDataTrack().getLoginTracker().loginholds.get(event.getPlayer().getName()))));
            }
            else
                antibot.getUtility().getDebug().debug("No holding :(");
        }
    }

}
