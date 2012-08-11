package me.freebuild.superspytx.ab.handlers.chat;

import me.freebuild.superspytx.ab.AntiBot;
import me.freebuild.superspytx.ab.datatrack.ChatDataTrack;
import me.freebuild.superspytx.ab.settings.Permissions;
import me.freebuild.superspytx.ab.settings.Settings;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatFlowHandler
{

    public AntiBot antibot = null;
    public ChatDataTrack chatdata = null;

    public ChatFlowHandler(AntiBot instance)
    {
        antibot = instance;
        chatdata = instance.getDataTrack().getChatTracker();
        // TODO Auto-generated constructor stub
    }

    public boolean handle(Player player)
    {
        try
        {
            if (!Settings.enabled || !Settings.flowEnabled)
            {
                return false;
            }

            if (Permissions.VOICE.getPermission(player))
            {
                return false;
            }

            if (antibot.getHandler().getCaptchaHandler().hasUnsolvedPuzzle(player))
            {
                return false;
            }

            if (chatdata.chatLockedDown)
            {
                return true;
            }

            if (chatdata.lasttime < 1L)
            {
                chatdata.lasttime = System.currentTimeMillis();
                return false;
            }

            Long math = System.currentTimeMillis() - chatdata.lasttime;
            if (math < Settings.timetooverflow)
            {
                if (chatdata.chatflowscurrent > Settings.overflows)
                {
                    // lockdown chatting.
                    chatdata.chatLockedDown = true;
                    if (Settings.notify)
                    {
                        antibot.getServer().broadcastMessage(Settings.prefix + ChatColor.DARK_AQUA + Settings.overflowedmessage.replace("%sec%", Long.toString(chatdata.chatmutedlength)));
                    }

                    // force captcha on chat overflow.

                    if (Settings.forceCaptchaOnChatFlow)
                    {
                        for (Player pl : antibot.getServer().getOnlinePlayers())
                        {
                            if (!antibot.getDataTrack().getChatTracker().solvedplayers.contains(pl.getName()))
                                antibot.getHandler().getCaptchaHandler().playerNeedsPuzzling(pl);
                        }
                    }

                    Long timetomutefor = chatdata.chatmutedlength * 20L;
                    chatdata.chatmutedlength += 5L; // increase by 5 each time.
                    chatdata.chatoverflows += 1;
                    antibot.getServer().getScheduler().scheduleSyncDelayedTask(antibot, new Runnable()
                    {

                        public void run()
                        {
                            if (chatdata.chatLockedDown)
                            {
                                chatdata.chatLockedDown = false;
                                chatdata.chatflowscurrent = 0;
                                antibot.getServer().broadcastMessage(Settings.prefix + ChatColor.GREEN + "Chat has been unmuted!");
                            }
                        }
                    }, timetomutefor);
                    return true;
                }
                chatdata.chatflowscurrent += 1;
                return false;
            }

            chatdata.lasttime = System.currentTimeMillis();
            chatdata.chatflowscurrent = 0;
        }
        catch (Exception e)
        {

        }
        
        return false;
    }

}
