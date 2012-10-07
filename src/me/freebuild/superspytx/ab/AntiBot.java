package me.freebuild.superspytx.ab;

import me.freebuild.superspytx.ab.callunits.CallUnit;
import me.freebuild.superspytx.ab.settings.Settings;
import me.freebuild.superspytx.ab.settings.SettingsCore;
import me.freebuild.superspytx.ab.tils.GeoTils;
import me.freebuild.superspytx.ab.workflow.GD;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiBot extends JavaPlugin
{
    private static AntiBot instance;
    private static SettingsCore settingscore;
    private String version;

    public void onDisable()
    {
        Bukkit.getScheduler().cancelTasks(this); // Wow? cancelAllTasks would fuck up plugins now wouldn't it? lul.
    }

    public void onEnable()
    {
        instance = this;
        
        /* Initialize stuff */
        GeoTils.initialize();
        
        /* Events */
        (new CallUnit()).registerUnits();

        /* Configuration */
        settingscore = new SettingsCore();
        settingscore.loadDefaults();
        settingscore.loadSettings();

        /* Register players on server */
        for(Player pl : this.getServer().getOnlinePlayers())
        {
            GD.getPI(pl).ab_alreadyin = true;
        }
        
        /* Setup minute running task */
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
        	public void run()
        	{
        		GD.updateTask();
        	}
        }, 1200L, 1200L);
        
        /* All finished */
        PluginDescriptionFile pdfFile = getDescription();
        version = pdfFile.getVersion();
        boolean development = (version.contains("-SNAPSHOT"));

        if (development)
        {
            Settings.checkupdates = false;
            getLogger().info("This is a development version of AntiBot and not a official release.  Please be careful.  Please report bugs as you find them.");
            getLogger().info("Please check back the BukkitDev page for updates!");
        }
        else
        {
            getLogger().info("If you have any issues with AntiBot.  Or there is a false positive! Don't be afraid to make a ticket!");
        }

        getLogger().info("AntiBot v" + version + " (Notorious UnLeet & deRobert Edition) enabled!");
    }

    public static AntiBot getInstance()
    {
        return instance;
    }
    
    public static void debug(String e)
    {
        if(Settings.debugmode) {
            getInstance().getLogger().info("Debug: " + e);
            //TODO: Remove Debug Code!
          /*  for(Player pl : Bukkit.getOnlinePlayers())
            {
                if(pl.hasPermission("ab.debug"))
                    pl.sendMessage("[AntiBot] Debug: " + e);
            } */
            // TODO: Permission node based debug logs?
            Bukkit.broadcastMessage(Settings.prefix + "Debug: " + e);
        }
    }
    
    public static void log(String e)
    {
        getInstance().getLogger().info(e);
    }

}
