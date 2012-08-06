package me.freebuild.superspytx.handlers.ab.login;

import me.freebuild.superspytx.ab.AntiBot;
import me.freebuild.superspytx.ab.datatrack.DataTrackCore;
import me.freebuild.superspytx.ab.settings.Permissions;
import me.freebuild.superspytx.ab.settings.Settings;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BotHandler
{

    private AntiBot botclass = null;
    private DataTrackCore data = null;

    public BotHandler(AntiBot instance, DataTrackCore instance2)
    {
        // transfer instance for compatibility
        botclass = instance;
        data = instance2;
        // TODO Auto-generated constructor stub
    }

    public void onPlayerKick(PlayerKickEvent event)
    {
        if (!Settings.enabled)
        {
            return;
        }

        if (data.getLoginTracker().autokick.contains(event.getPlayer().getName()))
        {
            event.setLeaveMessage(null);
            return;
        }

        if (data.getLoginTracker().autoipkick.contains(event.getPlayer().getAddress().toString().split(":")[0]))
        {
            event.setLeaveMessage(null);
            return;
        }

    }

    // falsified antibot trigger bug fix, or brolos bug fix.

    public void onPlayerQuit(PlayerQuitEvent event)
    {
        if (Permissions.JOIN.getPermission(event.getPlayer()))
        {
            return;
        }
        else
        {
            if (data.getLoginTracker().botcts < 1)
            {
                return;
            }
            else
            {
                data.getLoginTracker().botcts -= 1;
            }
        }
    }

    public void onPlayerMove(PlayerMoveEvent event)
    {
        if (!Settings.enabled)
        {
            return;
        }
        try
        {
            String pl = event.getPlayer().getName();
            if (data.getChatTracker().trackplayers.containsKey(pl))
            {
                if (!(data.getChatTracker().trackplayers.get(pl)).hasMoved)
                {
                    (data.getChatTracker().trackplayers.get(pl)).moved();
                }
            }
        }
        catch (Exception e)
        {
            botclass.getUtility().getDebug().debug("Player " + event.getPlayer().getName() + " did not get updated successfully for move.");
        }
    }

    public void onPlayerJoin(PlayerJoinEvent event)
    {
        try
        {
            botclass.getUtility().getDebug().debug("User is trying to connect..");
            data.getLoginTracker().time = System.currentTimeMillis();

            if (Permissions.JOIN.getPermission(event.getPlayer()))
            {
                botclass.getUtility().getDebug().debug("Whitelisted.");
                if (data.getLoginTracker().reanibo && Permissions.NOTIFY.getPermission(event.getPlayer()))
                {
                    event.getPlayer().sendMessage(Settings.prefix + "\247c" + Settings.connectInvasion);
                }
                if (data.getLoginTracker().reanibo && Permissions.NOTIFY.getPermission(event.getPlayer()) && Settings.interval > 100000)
                {
                    event.getPlayer().sendMessage(Settings.prefix + "\247cThe system needs a flush. Please type /antibot flush. Thanks.");
                }
                return;
            }

            if (!Settings.enabled)
            {
                return;
            }

            if (data.getLoginTracker().autokick.contains(event.getPlayer().getName()))
            {
                event.getPlayer().kickPlayer(Settings.kickMsg);
                event.setJoinMessage(null);
                return;
            }

            if (!data.getLoginTracker().reanibo)
            {
                // IP tracking usernames system.
                data.getLoginTracker().trackPlayer(event.getPlayer(), event.getPlayer().getAddress().toString().split(":")[0]);
                botclass.getUtility().getDebug().debug("Added user to tracking");
                data.getLoginTracker().addConnected(event.getPlayer().getName());
                botclass.getUtility().getDebug().debug("Added user to connected");
                data.getChatTracker().trackplayers.put(event.getPlayer().getName(), botclass.getDataTrack().getPlayer(event.getPlayer().getName(), this));
                botclass.getUtility().getDebug().debug("Added user to trackplayer");
            }

            if (data.getLoginTracker().botcts > Settings.accounts + 2 && data.getLoginTracker().reanibo)
            {
                Settings.accounts = Settings.accounts + 2;
                Settings.interval = Settings.interval + 5000;
            }

            // bug workaround
            if (Settings.interval < 1)
            {
                botclass.getUtility().getDebug().debug("Bug detected! Fixing bug.");
                // lets try setting this back to default Settings.intervals, if
                // not,
                // reload
                // the configuration.
                Settings.interval = botclass.getDefaultinterval();
                if (botclass.getDefaultinterval() < 1)
                {
                    // have to fix.
                    botclass.getSettings().loadSettings();
                }
            }

            long math = data.getLoginTracker().time - data.getLoginTracker().lasttime;
            int cb = Settings.interval + botclass.getUtility().getBot().getRandomIntInvasion();
            botclass.getUtility().getDebug().debug("Checking....0");
            botclass.getUtility().getDebug().debug("Math: " + math);
            botclass.getUtility().getDebug().debug("Time: " + data.getLoginTracker().time);
            botclass.getUtility().getDebug().debug("Current Interval: " + Settings.interval);
            botclass.getUtility().getDebug().debug("Random Interval: " + cb);
            botclass.getUtility().getDebug().debug("Lasttime: " + data.getLoginTracker().lasttime);
            botclass.getUtility().getDebug().debug("BotCts: " + data.getLoginTracker().botcts + " Accs: " + Settings.accounts);

            if (data.getLoginTracker().botcts > Settings.accounts && math < cb)
            {
                botclass.getUtility().getDebug().debug("Hit #1!");
                // Incoming invasion.
                if (!data.getLoginTracker().reanibo)
                {
                    if (Settings.whiteList)
                    {
                        if (Settings.notify && Settings.whiteList)
                        {
                            botclass.getServer().broadcastMessage(Settings.prefix + "\247cOh no! A minecraft bot invasion has began. Connection Throttling: \247aEnabled");
                        }
                        data.getLoginTracker().reanibo = true;
                    }
                    else
                    {
                        if (Settings.notify)
                        {
                            botclass.getServer().broadcastMessage(Settings.prefix + "\247chas detected minecraft spam!");
                        }
                        botclass.getUtility().getBot().flush();
                    }
                    botclass.getUtility().getDebug().debug("Tripswitched!");
                    botclass.getDataTrack().getLoginTracker().kickConnected();
                }
                data.getLoginTracker().botattempt = System.currentTimeMillis();
                data.getLoginTracker().botcts += 1;
                event.getPlayer().kickPlayer(Settings.kickMsg);
                event.setJoinMessage(null);
            }
            else if (data.getLoginTracker().botattempt < Settings.interval && data.getLoginTracker().reanibo)
            {
                botclass.getUtility().getDebug().debug("Hit #2");
                // Attempting to connect.
                data.getLoginTracker().botattempt = System.currentTimeMillis();
                data.getLoginTracker().botcts += 1;
                event.getPlayer().kickPlayer(Settings.connectMsg);
                event.setJoinMessage(null);
            }
            else
            {
                botclass.getUtility().getDebug().debug("Hit #3");

                if (data.getLoginTracker().reanibo)
                {
                    botclass.getUtility().getBot().flush();
                }
                // No invasion.
                data.getLoginTracker().lasttime = System.currentTimeMillis();
                data.getLoginTracker().botcts += 1;
            }

            if (!botclass.getServer().getOfflinePlayer(event.getPlayer().getName()).isOnline())
            {
                event.setJoinMessage(null);
            }

        }
        catch (Exception e)
        {
            botclass.getServer().broadcastMessage(Settings.prefix + "\247cAn error had occured! Please check console.");
            e.printStackTrace();
        }
    }

}
