package com.superspytx.ab.handlers.chat;

import com.superspytx.ab.AB;
import com.superspytx.ab.abs.EventAction;
import com.superspytx.ab.abs.Handler;
import com.superspytx.ab.abs.PI;
import com.superspytx.ab.settings.Settings;
import com.superspytx.ab.tils.CaptchaTils;
import com.superspytx.ab.tils.Tils;
import com.superspytx.ab.workflow.GD;

public class ChatSpamHandler implements Handler {
	@Override
	public boolean run(EventAction info) {
		if (!Settings.enableAntiSpam) return false;
		if (info.message == null) return false;
		
		PI pli = GD.getPI(info.player);
		
		pli.cs_trig = false;
		if (Tils.getLongDiff(pli.b_connectfor) < 2000L && !pli.ab_alreadyin) pli.cs_ct += 2;
		
		AB.debug("Chat spam check for " + info.player.getName());
		if (pli.cs_lmt != 0L) {
			AB.debug("CS_LMT > 0");
			int ct = 0;
			if (Tils.strDiffCounter(info.message, pli.cs_lm) < Settings.spamdiffct) {
				AB.debug("Str Diff L: " + Tils.strDiffCounter(info.message, pli.cs_lm));
				pli.cs_ct+= 0.5D;
				ct++;
				AB.debug("VL: " + pli.cs_ct);
			}
			if (Tils.strDiffCounter(info.message, GD.cf_lm) < Settings.spamdiffct) {
				AB.debug("Str Diff G: " + Tils.strDiffCounter(info.message, pli.cs_lm));
				pli.cs_ct++;
				ct++;
				AB.debug("VL: " + pli.cs_ct);
			}
			if (Tils.strDiffCounter(info.message, GD.cf_lm) < 1) {
				AB.debug("Str Diff HG: " + Tils.strDiffCounter(info.message, pli.cs_lm));
				pli.cs_ct += 2;
				ct += 2;
				AB.debug("VL: " + pli.cs_ct);
			}
			if (Tils.getLongDiff(pli.cs_lmt) < Settings.spamtime) {
				AB.debug("Time diff: " + Tils.getLongDiff(pli.cs_lmt));
				pli.cs_ct+= 0.5D;
				ct++;
				AB.debug("VL: " + pli.cs_ct);
			} else if (Tils.consistency(pli, pli.cs_lmt)) {
				pli.cs_ct += 2;
				ct += 2;
				AB.debug("TC VL: " + pli.cs_ct);
			}
			
			if (pli.cs_ct >= Settings.spamam) {
				// TODO: Perform Action - Chat Message Spam.
				AB.debug("Chat spam detected!");
				AB.debug("VL: " + pli.cs_ct);
				pli.cs_trig = true;
				pli.cs_lsm = info.message;
				pli.cs_lmt = System.currentTimeMillis();
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
			AB.debug("Sending CAPTCHA for " + info.pi.p_name);
			CaptchaTils.sendCaptchaToPlayer(info.player);
		} else {
			AB.debug("Kicking player " + info.pi.p_name);
			Tils.kickPlayer(info.player);
		}
	}
	
}
