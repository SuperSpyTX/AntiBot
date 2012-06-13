package me.freebuild.superspytx.datatrack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.entity.Player;

import me.freebuild.superspytx.AntiBot;

public class CaptchaDataTrack
{

    private AntiBot core = null;

    public CaptchaDataTrack(AntiBot instance)
    {
        core = instance;
    }

    public Map<Player, Puzzle> puzzles = new HashMap<Player, Puzzle>();
    public List<String> solvedplayers = new CopyOnWriteArrayList<String>();

}
