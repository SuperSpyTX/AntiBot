package me.freebuild.superspytx.handlers.chat;

import me.freebuild.superspytx.AntiBot;
import me.freebuild.superspytx.datatrack.ChatDataTrack;
import me.freebuild.superspytx.settings.Permissions;
import me.freebuild.superspytx.settings.Settings;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

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

    public void handle(PlayerChatEvent event)
    {
        try
        {
            if (!Settings.enabled || !Settings.flowEnabled)
            {
                return;
            }

            if (Permissions.VOICE.getPermission(event.getPlayer()))
            {
                return;
            }

            if (chatdata.chatLockedDown)
            {
                event.setCancelled(true);
                return;
            }

            if (chatdata.lasttime < 1L)
            {
                chatdata.lasttime = System.currentTimeMillis();
                return;
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
                    return;
                }
                chatdata.chatflowscurrent += 1;
                return;
            }

            chatdata.lasttime = System.currentTimeMillis();
            chatdata.chatflowscurrent = 0;
        }
        catch (Exception e)
        {

        }
    }

}
