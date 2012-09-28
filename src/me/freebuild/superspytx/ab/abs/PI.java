package me.freebuild.superspytx.ab.abs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PI
{
    /* Everything player info goes here! */
    public Player pl;
    public String p_name;
    public boolean online = true;
    public boolean ab_loggedin = false; // true if already logged in.

    /* Bot Handler Data */
    public long b_connectfor = 0L;
    
    /* Chat Spam Data */
    public int cs_ct = 0;
    public long cs_lmt = 0L;
    public String cs_lm = "";
    public long cs_rd = 0L;
    public boolean cs_trig = false;
    
    /* Captcha Data */
    public boolean cp_haspuzzle = false;
    
    
    public PI(Player pl)
    {
        this.b_connectfor = System.currentTimeMillis();
        this.p_name = pl.getName();
        this.pl = pl;
    }
    
    public PI(String pl)
    {
        this.b_connectfor = System.currentTimeMillis();
        this.p_name = pl;
        this.pl = Bukkit.getPlayerExact(pl);
    }
    
    public boolean updateOnlineStatus()
    {
        if(pl != null)
            return true;
        
        pl = Bukkit.getPlayerExact(p_name);
        return (pl != null);
    }

}
