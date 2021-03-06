package com.superspytx.ab.abs;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import com.superspytx.ab.handlers.Handlers;

public class PI {
	/* Everything player info goes here! */
	public Player pl;
	public String p_name;
	public boolean isExempt = false;
	public List<Handlers> exemptedHandlers = new CopyOnWriteArrayList<Handlers>();
	public long ab_lastdc = 0L;
	public boolean ab_alreadyin = false; // true if already logged in.
	public boolean ab_online = true;
	
	/* Bot Handler Data */
	public long b_connectfor = 0L;
	
	/* Chat Spam Data */
	public double cs_ct = 0;
	public long cs_lmt = 0L;
	public String cs_lm = "";
	public String cs_lsm = "";
	public long cs_rd = 0L;
	public boolean cs_trig = false;
	
	/* Captcha Data */
	public boolean cp_haspuzzle = false;
	public long cp_idle = 0L;
	public boolean cp_solvedpuzzle = false;
	public Puzzle cp_puzzle;
	
	
	public PI(Player pl) {
		this.b_connectfor = System.currentTimeMillis();
		this.p_name = pl.getName();
		this.pl = pl;
	}
	
	public PI(String pl) {
		this.b_connectfor = System.currentTimeMillis();
		this.p_name = pl;
		this.pl = Bukkit.getPlayerExact(pl);
	}
	
	public boolean updateOnlineStatus() {
		pl = Bukkit.getPlayerExact(p_name);
		if (pl != null) this.clean();
		return (pl != null);
	}
	
	public void updateStatus() {
		if (pl == null) pl = Bukkit.getPlayerExact(p_name);
	}
	
	public void resetSpamData() {
		this.cs_ct = 0;
		this.cs_lm = "";
		this.cs_lmt = 0L;
		this.cs_rd = 0L;
		this.cs_trig = false;
	}
	
	private void clean() {
		this.b_connectfor = System.currentTimeMillis();
		// this.ab_lastdc = 0L; -- This would make login delay break.
		this.ab_alreadyin = false;
		this.ab_online = true;
		this.cp_haspuzzle = false;
		this.cp_solvedpuzzle = false;
		resetSpamData();
	}
	
}
