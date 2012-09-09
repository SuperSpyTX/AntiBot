package me.freebuild.superspytx.ab.abs;

import org.bukkit.entity.Player;

public class PI
{
    /* Everything player info goes here! */
    public Player pl;

    /* Bot Handler Data */
    public long b_connectfor = 0L;
    
    /* Chat Spam Data */
    public int cs_ct = 0;
    public long cs_lmt = 0L;
    public String cs_lm = "";
    public long cs_rd = 0L;
    public boolean cs_trig = false;
    
    public PI(Player pl)
    {
        this.b_connectfor = System.currentTimeMillis();
        this.pl = pl;
    }

}
