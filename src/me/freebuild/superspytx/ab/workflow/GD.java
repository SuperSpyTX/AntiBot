package me.freebuild.superspytx.ab.workflow;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.freebuild.superspytx.ab.abs.PI;

public class GD
{
    /* Global Data for AntiBot */
    private static Map<String, PI> pii = new ConcurrentHashMap<String, PI>();
    
    /* Bot Throttler */
    public static int b_cts = 0;
    public static long b_lc = 0L;
    public static CopyOnWriteArrayList<PI> b_cp = new CopyOnWriteArrayList<PI>();
    
    /* Chat Flow */
    public static int cf_cts = 0;
    
    public static PI getPI(Player player)
    {
        if(pii.containsKey(player.getName()))
            return pii.get(player.getName());
        else
        {
            PI bug = new PI(player);
            pii.put(player.getName(), bug);
            return bug;
        }
    }
    
    public static PI getPI(String player)
    {
        return getPI(Bukkit.getPlayerExact(player));
    }
    
    public static void unregisterPI(Player player)
    {
        unregisterPI(player.getName());
    }
    
    public static void unregisterPI(String player)
    {
        pii.remove(player);
    }

}
