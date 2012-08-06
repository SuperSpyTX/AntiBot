package me.freebuild.superspytx.ab.toolbox;

import me.freebuild.superspytx.ab.AntiBot;

import org.bukkit.entity.Player;

public class DeregisterUtility
{

    public AntiBot antibot = null;

    public DeregisterUtility(AntiBot instance)
    {
        antibot = instance;
    }

    public String getUserFromIP(String IP)
    {
        try
        {
            return antibot.getDataTrack().getLoginTracker().ipList.get(IP);
        }
        catch (Exception e)
        {
            return "";
        }
    }

    /*public void handle(String IP) {
    	if (!IP.contains("/")) {
    		IP = "/" + IP;
    	}
    	antibot.getDataTrack().getLoginTracker()
    			.removeConnected(getUserFromIP(IP));
    	antibot.getDataTrack().getChatTracker().trackplayers
    			.remove(getUserFromIP(IP));
    }*/

    public void handle(Player pl)
    {
        antibot.getDataTrack().getLoginTracker().removeConnected(pl.getName());
        antibot.getDataTrack().getChatTracker().trackplayers.remove(getUserFromIP(pl.getName()));
        if (antibot.getDataTrack().getChatTracker().puzzles.containsKey(pl))
            antibot.getDataTrack().getChatTracker().puzzles.remove(pl);
        if (antibot.getDataTrack().getChatTracker().solvedplayers.contains(pl.getName()))
            antibot.getDataTrack().getChatTracker().solvedplayers.remove(pl.getName());
    }

}
