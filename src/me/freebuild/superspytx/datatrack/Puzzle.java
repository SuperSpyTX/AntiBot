package me.freebuild.superspytx.datatrack;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.freebuild.superspytx.AntiBot;

public class Puzzle
{

    private AntiBot core = null;
    private Player player = null;
    private String captcha = null;
    private String answer = null;
    private ChatColor colorcode = null;
    private int attempts = 0;
    private long lastsolvetime = 0;

    public Puzzle(AntiBot instance, Player pl)
    {
        core = instance;
        player = pl;
        captcha = instance.getUtility().getCaptcha().generatePuzzle();
        answer = captcha.split(",")[1];
        colorcode = ChatColor.getByChar(captcha.split(",")[2]);
        captcha = captcha.split(",")[0];
    }

    public String getCaptcha()
    {
        return captcha;
    }

    public int getAttempts()
    {
        return 3 - attempts;
    }

    public Player getPlayer()
    {
        return player;
    }

    public boolean naughty()
    {
        if (attempts > 2)
        {
            return true;
        }

        return false;
    }

    public void setSolveTime(Long m)
    {
        lastsolvetime = m;
    }

    public boolean tooTaylorSwift()
    {
        Long now = System.currentTimeMillis();
        Long math = now - lastsolvetime;
        return math < 4000L;
    }

    public boolean checkAnswer(String c)
    {
        if (naughty())
            return false;

        if (core.getUtility().getCaptcha().compare(c, answer))
        {
            return true;
        }

        attempts++;

        return false;
    }

    public ChatColor getColor()
    {
        return colorcode;
    }

}
