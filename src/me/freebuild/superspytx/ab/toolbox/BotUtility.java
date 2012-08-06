package me.freebuild.superspytx.ab.toolbox;

import java.util.Random;

import me.freebuild.superspytx.ab.AntiBot;
import me.freebuild.superspytx.ab.settings.Settings;

import org.bukkit.ChatColor;

public class BotUtility
{
    public AntiBot antibot = null;

    public BotUtility(AntiBot instance)
    {
        antibot = instance;
        // TODO Auto-generated constructor stub
    }

    public boolean toggle(Boolean e)
    {
        try
        {
            Settings.enabled = e;
            return true;
        }
        catch (Exception fe)
        {
            return false;
        }
    }

    public boolean flush()
    {
        try
        {
            if (antibot.getDataTrack().getLoginTracker().reanibo)
            {
                antibot.getUtility().getDebug().debug("Disabled Reanibios.");
                antibot.getDataTrack().getLoginTracker().reanibo = false;
                Settings.interval = antibot.getDefaultinterval();
                antibot.getDataTrack().getLoginTracker().connected.clear();
                Settings.accounts = antibot.getDefaultaccounts();
                antibot.getDataTrack().getLoginTracker().lasttime = 0;
                antibot.getDataTrack().getLoginTracker().botattempt = 0;
                if (Settings.notify && Settings.whiteList)
                {
                    antibot.getServer().broadcastMessage(Settings.prefix + "\247aThe minecraft bot invasion has ended. Connection Throttling: \247cDisabled");
                }
            }
            antibot.getDataTrack().getLoginTracker().botcts = 0;
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public boolean flush2()
    {
        try
        {
            antibot.getDataTrack().getLoginTracker().reanibo = false;
            Settings.interval = antibot.getDefaultinterval();
            antibot.getDataTrack().getLoginTracker().connected.clear();
            antibot.getDataTrack().getLoginTracker().autokick.clear();
            antibot.getDataTrack().getLoginTracker().autoipkick.clear();
            antibot.getDataTrack().getChatTracker().spammyPlayers.clear();
            antibot.getDataTrack().getLoginTracker().ipList.clear();
            Settings.accounts = antibot.getDefaultaccounts();
            antibot.getDataTrack().getLoginTracker().lasttime = 0;
            antibot.getDataTrack().getLoginTracker().botattempt = 0;
            antibot.getDataTrack().getLoginTracker().lastlogin.clear();
            antibot.getDataTrack().getLoginTracker().loginholds.clear();
            antibot.getDataTrack().getLoginTracker().loginsets.clear();
            antibot.getDataTrack().getChatTracker().chatflowscurrent = 0;
            if (antibot.getDataTrack().getChatTracker().chatLockedDown)
            {
                antibot.getDataTrack().getChatTracker().chatLockedDown = false;
                antibot.getServer().broadcastMessage(Settings.prefix + ChatColor.GREEN + "Chat has been unmuted!");
            }
            antibot.getDataTrack().getChatTracker().chatmutedlength = 5L;
            antibot.getDataTrack().getChatTracker().lasttime = 0L;
            if (Settings.notify && Settings.whiteList)
            {
                antibot.getServer().broadcastMessage(Settings.prefix + "\247aThe minecraft bot invasion has ended. Connection Throttling: \247cDisabled");
            }
            antibot.getDataTrack().getLoginTracker().botcts = 0;
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public int getRandomInt()
    {
        Random rdm = new Random();
        return rdm.nextInt(5000);
    }

    public int getRandomIntInvasion()
    {
        if (!antibot.getDataTrack().getLoginTracker().reanibo)
        {
            return 0;
        }
        Random rdm = new Random();
        if (Settings.interval > 35000)
        {
            return rdm.nextInt(7000);
        }
        else if (Settings.interval > 45000)
        {
            return rdm.nextInt(25000);
        }
        return rdm.nextInt(5000);
    }
    
    public String getFriendlyTimeLeft(Long targettime)
    {
        long diff = (targettime - System.currentTimeMillis()) / 1000;
        antibot.getUtility().getDebug().debug("Targettime: " + targettime + "");
        antibot.getUtility().getDebug().debug("Time: " + System.currentTimeMillis() + "");
        antibot.getUtility().getDebug().debug("Before div: " + (targettime - System.currentTimeMillis()));
        antibot.getUtility().getDebug().debug("After div: " + diff);
        String build = "";
        
        if(diff >= 3600)
            build += Long.toString((diff / 3600)) + " hour(s)";
        else if(diff >= 60)
            build += Long.toString((diff / 60)) + " minute(s)";
        else
            build += Long.toString(diff) + " second(s)";
        
        return build;
    }

}
