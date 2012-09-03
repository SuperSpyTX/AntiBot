package me.freebuild.superspytx.ab;

import me.freebuild.superspytx.ab.callunits.CallUnit;
import me.freebuild.superspytx.ab.settings.Settings;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiBot extends JavaPlugin
{
    private static AntiBot instance;
    private String version;
    
    public void onDisable()
    {
        Bukkit.getScheduler().cancelAllTasks();
    }
    
    public void onEnable()
    {
        instance = this;
        /* Events */
        (new CallUnit()).registerUnits();
        
        /* Configuration goes here */
        
        
        /* All finished */
        PluginDescriptionFile pdfFile = getDescription();
        version = pdfFile.getVersion();
        boolean development = (version.contains("-SNAPSHOT"));
        
        if (development)
        {
            Settings.checkupdates = false;
            getLogger().info("This is a development version of AntiBot and not a official release.  Please be careful.  Please report bugs as you find them.");
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

}
