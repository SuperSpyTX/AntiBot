package me.freebuild.superspytx.ab.datatrack;

import org.bukkit.entity.Player;

import me.freebuild.superspytx.ab.AntiBot;
import me.freebuild.superspytx.ab.handlers.chat.ChatSpamHandler;
import me.freebuild.superspytx.ab.handlers.login.BotHandler;

public class DataTrackCore
{

    private AntiBot antibot = null;

    // trackers
    private LoginDataTrack botdata = null;
    private ChatDataTrack chatdata = null;

    public DataTrackCore(AntiBot instance)
    {
        antibot = instance;
        botdata = new LoginDataTrack(instance);
        chatdata = new ChatDataTrack(instance);
    }

    public PlayerData getPlayer(String username, ChatSpamHandler thi)
    {
        return new PlayerData(antibot, username);
    }

    public PlayerData getPlayer(String username, BotHandler thi)
    {
        return new PlayerData(antibot, username);
    }

    public Puzzle getPuzzle(Player player)
    {
        return new Puzzle(antibot, player);
    }

    public LoginDataTrack getLoginTracker()
    {
        return botdata;
    }

    public ChatDataTrack getChatTracker()
    {
        return chatdata;
    }

}
