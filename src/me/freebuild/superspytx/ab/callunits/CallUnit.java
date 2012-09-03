package me.freebuild.superspytx.ab.callunits;

import java.util.ArrayList;

import me.freebuild.superspytx.ab.AntiBot;
import me.freebuild.superspytx.ab.settings.Settings;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class CallUnit implements Listener
{
    private ArrayList<CallUnit> units = new ArrayList<CallUnit>();
    
    public CallUnit()
    {
        if(!Settings.isSet()) {
            Settings.setup(); // there we go.
            units.add(new BotUnit());
            units.add(new GarbageUnit());
        }
    }
    
    public void registerUnits()
    {
        if(units.size() == 0) return;
        for(CallUnit unit : units)
        {
            Bukkit.getPluginManager().registerEvents(unit, AntiBot.getInstance());
        }
    }

}
