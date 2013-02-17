package com.superspytx.ab.handlers.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import com.superspytx.ab.AB;
import com.superspytx.ab.AntiBot;
import com.superspytx.ab.abs.EventAction;
import com.superspytx.ab.abs.Handler;
import com.superspytx.ab.abs.PI;
import com.superspytx.ab.settings.Language;
import com.superspytx.ab.settings.Settings;
import com.superspytx.ab.tils.CaptchaTils;
import com.superspytx.ab.tils.Tils;
import com.superspytx.ab.workflow.GD;

public class ChatFlowHandler implements Handler {
	
	@Override
	public boolean run(EventAction info) {
		if (!Settings.flowEnabled) return false;
		if (GD.cf_gm || info.message == null || (GD.getPI(info.player).cs_trig) || (GD.getPI(info.player).cp_haspuzzle)) return false; // triggered spam? don't count towards chat flow.
		if (!GD.cf_pls.contains(info.pi)) GD.cf_pls.add(info.pi);
		AB.debug("Chat Flow debug");
		int ct = 0;
		if (GD.cf_lmt != 0L) {
			if (Tils.getLongDiff(GD.cf_lmt) < Settings.timetooverflow) {
				AB.debug("Time diff: " + Tils.getLongDiff(GD.cf_lmt));
				GD.cf_cts++;
				ct++;
				AB.debug("VL: " + GD.cf_cts);
				// TODO: Find if this causes false positives or not.
				if (Tils.strDiffCounter(info.message, GD.cf_lm) < Settings.spamdiffct && !GD.cf_lp.equalsIgnoreCase(info.player.getName())) {
					AB.debug("Str diff: " + Tils.strDiffCounter(info.message, GD.cf_lm));
					GD.cf_cts += 2;
					ct += 2;
					AB.debug("VL: " + GD.cf_cts);
				}
				if (GD.cf_cts >= Settings.overflows) {
					// TODO: Perform Action - Chat Overflow.
					AB.debug("Chat overflow! " + GD.cf_cts);
					AB.debug("VL: " + GD.cf_cts);
					GD.cf_ovfl++;
					return true;
				}
				if (ct < 1 && GD.cf_cts > 0) {
					GD.cf_cts -= 1;
					GD.cf_pls.remove(GD.cf_pls.size() - 1);
				}
				AB.debug("ChatFlow VL: " + GD.cf_cts);
				return false;
			}
		}
		
		AB.debug("Chat not overflowed.");
		GD.cf_cts = 0;
		GD.cf_pls.clear();
		GD.cf_lmt = System.currentTimeMillis();
		GD.cf_lm = info.message;
		AB.debug("Last message: " + info.message + " time: " + GD.cf_lmt);
		if (!GD.cf_lp.equalsIgnoreCase(info.player.getName()) || GD.cf_lp.length() == 0) {
			AB.debug("Last player: " + GD.cf_lp);
			GD.cf_lp = info.player.getName();
		}
		return false;
	}
	
	@Override
	public void performActions(EventAction info) {
		if (GD.cf_gm) return;
		GD.cf_gm = true;
		Bukkit.getScheduler().runTaskLaterAsynchronously(AntiBot.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				Bukkit.broadcastMessage(Language.prefix + ChatColor.RED + Language.overflowedMessage.replace("%sec%", Long.toString(GD.cf_ttmf)));
			}
			
		}, 20L);
		Bukkit.getScheduler().runTaskLaterAsynchronously(AntiBot.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				GD.cf_gm = false;
				GD.cf_cts = 0;
				GD.cf_lm = "";
				GD.cf_lp = "";
				GD.cf_lmt = 0L;
				GD.cf_ttmf += 5L;
				Bukkit.broadcastMessage(Language.prefix + ChatColor.GREEN + "Chat has been unmuted!");
			}
			
		}, 20L * GD.cf_ttmf);
		
		if (Settings.captchaEnabled && Settings.forceCaptchaOnChatFlow) {
			for (PI pl : GD.cf_pls) {
				if (pl.pl == null) continue;
				CaptchaTils.sendCaptchaToPlayer(pl.pl);
			}
		} else if (Settings.flowKicks) {
			for (PI pl : GD.cf_pls) {
				if (pl.pl == null) continue;
				Tils.kickPlayer(pl.pl);
			}
		}
		
		GD.cf_pls.clear();
	}
	
}
