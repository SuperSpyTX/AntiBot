package me.freebuild.superspytx.ab.handlers.chat;

import me.freebuild.superspytx.ab.AB;
import me.freebuild.superspytx.ab.abs.EventAction;
import me.freebuild.superspytx.ab.abs.Handler;
import me.freebuild.superspytx.ab.abs.PI;
import me.freebuild.superspytx.ab.settings.Settings;
import me.freebuild.superspytx.ab.tils.CaptchaTils;
import me.freebuild.superspytx.ab.tils.MathTils;
import me.freebuild.superspytx.ab.tils.StringTils;
import me.freebuild.superspytx.ab.workflow.GD;

public class ChatSpamHandler implements Handler {
	@Override
	public boolean run(EventAction info) {
		if (info.message == null) return false;
		
		PI pli = GD.getPI(info.player);
		pli.cs_trig = false;
		if (MathTils.getLongDiff(pli.b_connectfor) < 2000L && !pli.ab_alreadyin) pli.cs_ct += 2;
		
		AB.debug("Chat spam check for " + info.player.getName());
		if (pli.cs_lmt != 0L) {
			AB.debug("CS_LMT > 0");
			int ct = 0;
			if (StringTils.strDiffCounter(info.message, pli.cs_lm) < Settings.spamdiffct) {
				AB.debug("Str Diff L: " + StringTils.strDiffCounter(info.message, pli.cs_lm));
				pli.cs_ct++;
				ct++;
				AB.debug("VL: " + pli.cs_ct);
			}
			if (StringTils.strDiffCounter(info.message, GD.cf_lm) < Settings.spamdiffct) {
				AB.debug("Str Diff G: " + StringTils.strDiffCounter(info.message, pli.cs_lm));
				pli.cs_ct++;
				ct++;
				AB.debug("VL: " + pli.cs_ct);
			}
			if (StringTils.strDiffCounter(info.message, GD.cf_lm) < 0) {
				AB.debug("Str Diff HG: " + StringTils.strDiffCounter(info.message, pli.cs_lm));
				pli.cs_ct += 2;
				ct += 2;
				AB.debug("VL: " + pli.cs_ct);
			}
			if (MathTils.getLongDiff(pli.cs_lmt) < Settings.spamtime) {
				AB.debug("Time diff: " + MathTils.getLongDiff(pli.cs_lmt));
				pli.cs_ct++;
				ct++;
				AB.debug("VL: " + pli.cs_ct);
			} else if (MathTils.consistency(pli, pli.cs_lmt)) {
				pli.cs_ct += 2;
				ct += 2;
				AB.debug("TC VL: " + pli.cs_ct);
			}
			
			if (pli.cs_ct >= Settings.spamam) {
				// TODO: Perform Action - Chat Message Spam.
				AB.debug("Chat spam detected!");
				AB.debug("VL: " + pli.cs_ct);
				pli.cs_trig = true;
				GD.cs_spams++;
				return true;
			}
			
			if (ct < 1 && pli.cs_ct > 0) {
				AB.debug("Decrease for ChatSpam");
				pli.cs_ct -= 1;
			}
			AB.debug("ChatSpam VL: " + pli.cs_ct);
		}
		AB.debug("Chat Spam not detected");
		pli.cs_lm = info.message;
		pli.cs_lmt = System.currentTimeMillis();
		AB.debug("Last Message: " + pli.cs_lm + " time: " + pli.cs_lmt);
		return false;
	}
	
	@Override
	public void performActions(EventAction info) {
		if (Settings.captchaEnabled && Settings.forceCaptchaOnChatSpam) {
			CaptchaTils.sendCaptchaToPlayer(info.player);
		} else {
			info.player.kickPlayer(Settings.kickMsg);
		}
	}
	
}
