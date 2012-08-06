package me.freebuild.superspytx.ab.datatrack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import me.freebuild.superspytx.ab.AntiBot;
import me.freebuild.superspytx.ab.settings.Permissions;
import me.freebuild.superspytx.ab.settings.Settings;

import org.bukkit.entity.Player;

public class LoginDataTrack
{

    public AntiBot botclass = null;

    public LoginDataTrack(AntiBot instance)
    {
        botclass = instance;
    }

    public ArrayList<String> autokick = new ArrayList<String>();
    public ArrayList<String> autoipkick = new ArrayList<String>();
    public HashMap<String, String> ipList = new HashMap<String, String>();
    public List<String> connected = new CopyOnWriteArrayList<String>();
    public long time;
    public long lasttime;
    public long botattempt;
    public int spamcts = 0;
    public int spambotsblocked = 0;
    public int botcts = 0;
    public boolean reanibo = false;

    // Countrybans
    public int countryusersblocked = 0;

    // Countrybans
    public List<String> countryBans = new CopyOnWriteArrayList<String>();
    
    // Login Delay
    public Map<String, Long> lastlogin = new HashMap<String, Long>();
    public Map<String, Long> loginholds = new HashMap<String, Long>();
    public Map<String, Integer> loginsets = new HashMap<String, Integer>();

    public void trackPlayer(Player e, String IP)
    {
        if (Permissions.JOIN.getPermission(e))
        {
            return;
        }
        if (!ipList.containsKey(IP))
        {
            ipList.put(IP, e.getName());
        }
        else
        {
            if (!Settings.enableMultiAccs)
            {
                return;
            }
            String pl = "";
            try
            {
                pl = ipList.get(IP);
            }
            catch (Exception e1)
            {

            }
            if (!pl.equals(e.getName()) || ipList.containsValue(e.getName()) && !ipList.containsKey(IP))
            {
                if (Settings.banUsers)
                {
                    autoipkick.add(IP);
                }
                if (Settings.notify)
                {
                    botclass.getServer().broadcastMessage(Settings.prefix + "\247chas detected multiple accounts!!");
                }
                if (!Settings.forceCaptchaOnMultiAcc || !Settings.captchaEnabled)
                {
                    try
                    {
                        e.kickPlayer(Settings.kickMsg);
                        botclass.getServer().getPlayerExact(ipList.get(IP)).kickPlayer(Settings.kickMsg);
                    }
                    catch (Exception e1)
                    {

                    }
                }
                else
                {
                    botclass.getHandler().getCaptchaHandler().playerNeedsPuzzling(e);
                }
            }
        }
    }

    public void addConnected(String playerName)
    {
        try
        {
            if (!connected.contains(playerName))
            {
                connected.add(playerName);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            botclass.getUtility().getDebug().debug("Adding new player failed: " + e.getMessage());
        }
    }

    public void removeConnected(String playerName)
    {
        try
        {
            if (connected.contains(playerName))
            {
                connected.remove(playerName);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            botclass.getUtility().getDebug().debug("Removing player failed: " + e.getMessage());
        }
    }

    public void kickConnected()
    {
        // int kicked = 0;
        botclass.getUtility().getDebug().debug("Kicking players with method #1 Size: " + botclass.getDataTrack().getLoginTracker().connected.size());
        for (String pl : botclass.getDataTrack().getLoginTracker().connected)
        {
            try
            {
                botclass.getUtility().getDebug().debug("Checking if kick possible for player..." + pl);
                Player p2 = botclass.getServer().getPlayerExact(pl);

                if (p2 == null)
                    continue;

                if (!botclass.getDataTrack().getChatTracker().checkConnection(pl))
                {
                    if (!Settings.forceCaptchaOnBotSpam)
                    {
                        botclass.getUtility().getDebug().debug("Yes, Kicking player..." + pl);
                        p2.kickPlayer(Settings.kickMsg);
                    }
                    else
                    {
                        botclass.getUtility().getDebug().debug("Yes, Sending CAPTCHA" + pl);
                        botclass.getHandler().getCaptchaHandler().playerNeedsPuzzling(p2);
                    }

                    botclass.getDataTrack().getLoginTracker().spambotsblocked += 1;
                    if (Settings.banUsers && (!Settings.forceCaptchaOnBotSpam || !Settings.captchaEnabled))
                    {
                        botclass.getDataTrack().getLoginTracker().autoipkick.add(p2.getAddress().toString().split(":")[0]);
                        botclass.getDataTrack().getLoginTracker().autokick.add(pl);
                    }
                    // kicked += 1;
                    botclass.getUtility().getDebug().debug("Kicked player with method #1");
                    botclass.getUtility().getDebug().debug("We now have autokick: " + botclass.getDataTrack().getLoginTracker().autokick.size() + " ip: " + botclass.getDataTrack().getLoginTracker().autoipkick.size());
                }
                else
                {
                    botclass.getUtility().getDebug().debug("Not possible for player ...." + pl);
                    botclass.getDataTrack().getLoginTracker().connected.remove(pl);
                }
            }
            catch (Exception e)
            {
                // if it fails. go down here.
                e.printStackTrace();
                botclass.getUtility().getDebug().debug("Failed to kick: " + pl);
            }
        }
        botclass.getDataTrack().getLoginTracker().connected.clear();

        // kick players if the above method doesn't work :|
        /*
         * botclass.getUtility().getDebug().debug("Checking if " + kicked +
         * " is less than 1"); if (kicked < 1) {
         * botclass.getUtility().getDebug()
         * .debug("Kicking player with method #2"); Player[] players =
         * botclass.getServer().getOnlinePlayers(); for (Player pl : players) {
         * if (!hasPerms(pl)) { pl.kickPlayer(Settings.connectMsg);
         * autokick.add(pl); botclass.getUtility().getDebug().debug(
         * "Kicked player with method #2" ); } } }
         */

    }

}
