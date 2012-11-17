package me.freebuild.superspytx.ab.handlers.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import me.freebuild.superspytx.ab.AB;
import me.freebuild.superspytx.ab.AntiBot;
import me.freebuild.superspytx.ab.abs.EventAction;
import me.freebuild.superspytx.ab.abs.Handler;
import me.freebuild.superspytx.ab.settings.Permissions;
import me.freebuild.superspytx.ab.settings.Settings;
import me.freebuild.superspytx.ab.tils.CaptchaTils;
import me.freebuild.superspytx.ab.tils.MathTils;
import me.freebuild.superspytx.ab.tils.StringTils;
import me.freebuild.superspytx.ab.workflow.GD;

public class ChatFlowHandler implements Handler {
	
	@Override
	public boolean run(EventAction info) {
		if (GD.cf_gm || info.message == null || (GD.getPI(info.player).cs_trig) || (GD.getPI(info.player).cp_haspuzzle)) return false; // triggered spam? don't count towards chat flow.
		AB.debug("Chat Flow debug");
		int ct = 0;
		if (GD.cf_lmt != 0L) {
			if (MathTils.getLongDiff(GD.cf_lmt) < Settings.timetooverflow) {
				AB.debug("Time diff: " + MathTils.getLongDiff(GD.cf_lmt));
				GD.cf_cts++;
				ct++;
				AB.debug("VL: " + GD.cf_cts);
				// TODO: Find if this causes false positives or not.
				if (StringTils.strDiffCounter(info.message, GD.cf_lm) < Settings.spamdiffct && !GD.cf_lp.equalsIgnoreCase(info.player.getName())) {
					AB.debug("Str diff: " + StringTils.strDiffCounter(info.message, GD.cf_lm));
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
				}
				AB.debug("ChatFlow VL: " + GD.cf_cts);
				return false;
			}
		}
		
		AB.debug("Chat not overflowed.");
		GD.cf_cts = 0;
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
		Bukkit.getScheduler().scheduleAsyncDelayedTask(AntiBot.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				Bukkit.broadcastMessage(Settings.prefix + ChatColor.RED + Settings.overflowedmessage.replace("%sec%", Long.toString(GD.cf_ttmf)));
				
				if (Settings.captchaEnabled && Settings.forceCaptchaOnChatFlow) {
					for (Player pl : Bukkit.getOnlinePlayers()) {
						if (!Permissions.CAPTCHA.getPermission(pl)) {
							CaptchaTils.sendCaptchaToPlayer(pl);
						}
					}
				}
			}
			
		}, 20L);
		Bukkit.getScheduler().scheduleAsyncDelayedTask(AntiBot.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				GD.cf_gm = false;
				GD.cf_cts = 0;
				GD.cf_lm = "";
				GD.cf_lp = "";
				GD.cf_lmt = 0L;
				GD.cf_ttmf += 5L;
				Bukkit.broadcastMessage(Settings.prefix + ChatColor.GREEN + "Chat has been unmuted!");
			}
			
		}, 20L * GD.cf_ttmf);
	}
	
}
