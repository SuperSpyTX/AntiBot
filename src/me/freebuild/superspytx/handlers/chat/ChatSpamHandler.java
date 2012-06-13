package me.freebuild.superspytx.handlers.chat;

import me.freebuild.superspytx.AntiBot;
import me.freebuild.superspytx.datatrack.PlayerData;
import me.freebuild.superspytx.settings.Permissions;
import me.freebuild.superspytx.settings.Settings;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

public class ChatSpamHandler
{

    public AntiBot antibot = null;

    public ChatSpamHandler(AntiBot instance)
    {
        antibot = instance;
        // TODO Auto-generated constructor stub
    }

    public void handle(Player player, PlayerChatEvent event)
    {
        try
        {
            if (!Settings.enabled)
            {
                return;
            }

            String pN = player.getName();

            if (Permissions.CHATSPAM.getPermission(event.getPlayer()) || !Settings.enableAntiSpam)
            {
                return;
            }

            if (antibot.getDataTrack().getBotTracker().autokick.contains(player.getName()))
            {
                player.kickPlayer(Settings.kickMsg);
                event.setCancelled(true);
                return;
            }

            if (antibot.getDataTrack().getBotTracker().autoipkick.contains(player.getAddress().toString().split(":")[0]))
            {
                player.kickPlayer(Settings.kickMsg);
                event.setCancelled(true);
                return;
            }

            if (antibot.getDataTrack().getChatTracker().spammyPlayers.contains(pN))
            {
                event.setCancelled(true);
                return;
            }

            if (!antibot.getDataTrack().getChatTracker().trackplayers.containsKey(pN))
            {
                antibot.getDataTrack().getChatTracker().trackplayers.put(pN, antibot.getDataTrack().getPlayer(pN, this));
            }
            else
            {
                try
                {
                    PlayerData pc = antibot.getDataTrack().getChatTracker().trackplayers.get(pN);
                    long math = System.currentTimeMillis() - pc.lastChatMsg;
                    if (pc.amoumt > Settings.spamam && math < Settings.spamtime)
                    {
                        antibot.getDataTrack().getChatTracker().chatspamblocked += 1;
                        if (Settings.notify)
                        {
                            antibot.getServer().broadcastMessage(Settings.prefix + "\247chas detected chat spam!");
                        }

                        if (Settings.captchaEnabled && !Settings.chatMute)
                        {
                            antibot.getHandler().getCaptchaHandler().playerNeedsPuzzling(player);
                        }
                        else if (!Settings.chatMute)
                        {
                            antibot.getDataTrack().getChatTracker().trackplayers.remove(pN);
                            player.kickPlayer(Settings.kickMsg);
                            event.setCancelled(true);
                        }
                        else
                        {
                            antibot.getDataTrack().getChatTracker().trackplayers.remove(pN);
                            antibot.getDataTrack().getChatTracker().spammyPlayers.add(pN);
                            event.setCancelled(true);
                        }
                    }
                    else
                    {
                        pc.trig();
                    }
                }
                catch (Exception e)
                {

                }
            }

        }
        catch (Exception e)
        {
            // alright, it failed. Don't worry about it.
        }

    }

}
