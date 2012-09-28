package me.freebuild.superspytx.ab.workflow;

import java.util.List;
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
    private static Map<String, PI> opii = new ConcurrentHashMap<String, PI>();
    
    /* Bot Throttler */
    public static int b_cts = 0;
    public static long b_lc = 0L;
    public static List<PI> b_cp = new CopyOnWriteArrayList<PI>();
    
    /* Chat Flow */
    public static boolean cf_gm = false;
    public static int cf_cts = 0;
    public static long cf_ttmf = 5L;
    public static long cf_lmt = 0L;
    public static String cf_lm = "";
    public static String cf_lp = "";
    
    /* Country Bans */
    public static List<String> cb_cds = new CopyOnWriteArrayList<String>();
    
    
    public static PI getPI(Player player)
    {
        return getPI(player.getName());
    }
    
    public static PI getPI(String player)
    {
        if(opii.containsKey(player))
            updateOfflineData(player);
        if(pii.containsKey(player))
            return pii.get(player);
        else
        {
            PI bug = new PI(player);
            pii.put(player, bug);
            return bug;
        }
    }
    
    public static void unregisterPI(Player player)
    {
        unregisterPI(player.getName());
    }
    
    public static void unregisterPI(String player)
    {
        opii.put(player, getPI(player));
        pii.remove(player);
    }
    
    private static void updateOfflineData(String player)
    {
        if(opii.get(player) != null)
        {
            PI bug = opii.get(player);
            if(bug.updateOnlineStatus())
            {
                opii.remove(player);
                pii.put(player, bug);
            }
        }
    }

}
