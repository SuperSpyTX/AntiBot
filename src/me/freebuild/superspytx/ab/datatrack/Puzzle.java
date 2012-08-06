package me.freebuild.superspytx.ab.datatrack;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.freebuild.superspytx.ab.AntiBot;

public class Puzzle
{

    private AntiBot core = null;
    private Player player = null;
    private String captcha = null;
    private String answer = null;
    private String[] captcha2 = null;
    private boolean newcaptcha = false;
    private ChatColor colorcode = null;
    private int attempts = 0;
    private long lastsolvetime = 0;

    public Puzzle(AntiBot instance, Player pl)
    {
        core = instance;
        player = pl;
        Random rdm = new Random();
        boolean swi = false;
        switch (rdm.nextInt(2))
        {
        case 1:
            newcaptcha = true;
            swi = true;
            captcha2 = instance.getUtility().getCaptcha().generatePuzzleV2();
            answer = captcha2[5];
            break;
        case 2:
            captcha = instance.getUtility().getCaptcha().generatePuzzle();
            answer = captcha.split(",")[1];
            colorcode = ChatColor.getByChar(captcha.split(",")[2]);
            captcha = captcha.split(",")[0];
            swi = true;
            break;
        }
        
        if(!swi) {
            newcaptcha = true;
            swi = true;
            captcha2 = instance.getUtility().getCaptcha().generatePuzzleV2();
            answer = captcha2[5];
        }
    }

    public boolean isNewCaptcha()
    {
        return newcaptcha;
    }

    public String[] getNewCaptcha() 
    {
        String[] filter = new String[5];
        for(int i = 0; i < captcha2.length - 1; i++) {
            filter[i] = captcha2[i].replace(" ", "_");
        }
        
        return filter;
    }

    public String getCaptcha()
    {
        if (newcaptcha)
            return null;
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
        return math < (newcaptcha ? 600L : 2000L);
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
