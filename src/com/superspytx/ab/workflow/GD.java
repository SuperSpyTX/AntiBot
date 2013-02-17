package com.superspytx.ab.workflow;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import com.superspytx.ab.abs.PI;
import com.superspytx.ab.handlers.Handlers;
import com.superspytx.ab.settings.Language;
import com.superspytx.ab.settings.Settings;
import com.superspytx.ab.tils.Tils;

public class GD {
	/* Global Data for AntiBot */
	private static Map<String, PI> pii = new ConcurrentHashMap<String, PI>();
	private static Map<String, PI> opii = new ConcurrentHashMap<String, PI>();
	public static List<Handlers> deactivatedHandlers = new CopyOnWriteArrayList<Handlers>();
	
	/* Bot Throttler */
	public static int b_cts = 0;
	public static boolean b_kicking = false;
	public static long b_lc = 0L;
	public static List<PI> b_cp = new CopyOnWriteArrayList<PI>();
	
	/* Chat Flow */
	public static boolean cf_gm = false;
	public static int cf_cts = 0;
	public static long cf_ttmf = 5L;
	public static long cf_lmt = 0L;
	public static List<PI> cf_pls = new CopyOnWriteArrayList<PI>();
	public static String cf_lm = "";
	public static String cf_lp = "";
	
	/* Statistics */
	public static int b_blks = 0;
	public static int cf_ovfl = 0;
	public static int cp_caps = 0;
	public static int cs_spams = 0;
	public static int cb_invs = 0;
	
	/* Country Bans */
	public static List<String> cb_cds = new CopyOnWriteArrayList<String>();
	
	public static PI getPI(Player player) {
		if (player == null) return null;
		return getPI(player.getName());
	}
	
	public static PI getPI(String player) {
		if (opii.containsKey(player)) return updateOfflineData(player);
		if (pii.containsKey(player)) {
			return pii.get(player);
		} else {
			PI bug = new PI(player);
			pii.put(player, bug);
			return bug;
		}
	}
	
	public static PI getUpdatedPI(Player player) {
		if (player == null) return null;
		PI i = getPI(player);
		i.pl = player;
		return i;
	}
	
	public static PI getUpdatedPI(String player) {
		PI i = getPI(player);
		i.updateStatus();
		return i;
	}
	
	public static void unregisterPI(Player player) {
		if (player == null) return;
		unregisterPI(player.getName());
	}
	
	public static void unregisterPI(String player) {
		getPI(player).ab_lastdc = System.currentTimeMillis();
		getPI(player).ab_online = false;
		opii.put(player, getPI(player));
		pii.remove(player);
	}
	
	private static PI updateOfflineData(String player) {
		if (opii.get(player) != null) {
			PI bug = opii.get(player);
			if (bug.updateOnlineStatus()) {
				pii.put(player, bug);
				opii.remove(player);
			}
			return bug;
		}
		
		return null;
	}
	
	public static void updateTask() {
		for (Entry<String, PI> g : opii.entrySet()) {
			if (Tils.getLongDiff(g.getValue().ab_lastdc) >= 60000L) opii.remove(g.getKey());
		}
		
		if (!b_kicking && b_cp.size() > 0) {
			for (final PI pl : b_cp) {
				if (Tils.getLongDiff(pl.b_connectfor) <= Settings.connectFor) b_cp.remove(pl);
			}
		}
		
		if (Settings.captchaIdleKicks) {
			for (Entry<String, PI> g : pii.entrySet()) {
				if (g.getValue().cp_haspuzzle && Tils.getLongDiff(g.getValue().cp_idle) >= 60000L) {
					Tils.kickPlayer(g.getValue().pl, "You've idled on CAPTCHA for too long!");
				}
			}
		}
	}
	
	public static void reset() {
		/* Clear all data */
		pii.clear();
		opii.clear();
		b_cts = 0;
		b_kicking = false;
		b_lc = 0L;
		b_cp.clear();
		if (cf_gm) Bukkit.broadcastMessage(Language.prefix + ChatColor.GREEN + "Chat has been unmuted!");
		cf_gm = false;
		cf_cts = 0;
		cf_ttmf = 5L;
		cf_lmt = 0L;
		cf_lm = "";
		cf_lp = "";
	}
	
}
