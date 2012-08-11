package me.freebuild.superspytx.ab.handlers.chat;

import me.freebuild.superspytx.ab.AntiBot;
import me.freebuild.superspytx.ab.datatrack.PlayerData;
import me.freebuild.superspytx.ab.settings.Permissions;
import me.freebuild.superspytx.ab.settings.Settings;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ChatSpamHandler
{

    public AntiBot antibot = null;

    public ChatSpamHandler(AntiBot instance)
    {
        antibot = instance;
        // TODO Auto-generated constructor stub
    }

    public boolean handle(Player player)
    {
        try
        {
            if (!Settings.enabled || !Settings.enableAntiSpam)
            {
                return false;
            }

            String pN = player.getName();

            if (Permissions.CHATSPAM.getPermission(player))
            {
                return false;
            }

            if (antibot.getDataTrack().getLoginTracker().autokick.contains(player.getName()))
            {
                player.kickPlayer(Settings.kickMsg);
                return true;
            }

            if (antibot.getDataTrack().getLoginTracker().autoipkick.contains(player.getAddress().toString().split(":")[0]))
            {
                player.kickPlayer(Settings.kickMsg);
                return true;
            }

            if (antibot.getDataTrack().getChatTracker().spammyPlayers.contains(pN))
            {

                return true;
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

                        }
                        else
                        {
                            antibot.getDataTrack().getChatTracker().trackplayers.remove(pN);
                            antibot.getDataTrack().getChatTracker().spammyPlayers.add(pN);

                        }
                        return true;
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

        return false;

    }

}
