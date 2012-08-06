package me.freebuild.superspytx.ab.datatrack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.entity.Player;

import me.freebuild.superspytx.ab.AntiBot;

public class ChatDataTrack
{

    public AntiBot botclass = null;

    public ChatDataTrack(AntiBot instance)
    {
        botclass = instance;
    }

    // chat spamdata
    public CopyOnWriteArrayList<String> spammyPlayers = new CopyOnWriteArrayList<String>();
    public HashMap<String, PlayerData> trackplayers = new HashMap<String, PlayerData>();
    public int chatspamblocked = 0;

    // chat flowdata
    public int chatoverflows = 0;
    public Long chatmutedlength = 5L;
    public int chatflowscurrent = 0;
    public Long lasttime = 0L;
    public boolean chatLockedDown = false;
    
    public Map<Player, Puzzle> puzzles = new HashMap<Player, Puzzle>();
    public List<String> solvedplayers = new CopyOnWriteArrayList<String>();

    public boolean checkConnection(String usr)
    {
        if (trackplayers.containsKey(usr))
        {
            PlayerData mp = (PlayerData) trackplayers.get(usr);
            if (mp.connectedForLonger())
            {
                return true;
            }
        }
        return false;
    }

}
