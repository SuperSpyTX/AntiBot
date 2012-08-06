package me.freebuild.superspytx.ab.toolbox;

import java.util.logging.Level;

import me.freebuild.superspytx.ab.AntiBot;
import me.freebuild.superspytx.ab.settings.Settings;

public class DebugUtility
{
    public AntiBot antibot = null;

    public DebugUtility(AntiBot instance)
    {
        antibot = instance;
        // TODO Auto-generated constructor stub
    }

    public void debug(String msg)
    {
        if (Settings.debugmode)
        {
            antibot.getServer().broadcastMessage(Settings.prefix + "" + msg);
            antibot.getLogger().log(Level.INFO, Settings.prefix + "" + msg);
        }
    }

}
