package com.superspytx.ab.handlers.chat;

import com.superspytx.ab.abs.EventAction;
import com.superspytx.ab.abs.Handler;
import com.superspytx.ab.abs.PI;
import com.superspytx.ab.settings.Language;
import com.superspytx.ab.settings.Settings;
import com.superspytx.ab.tils.Tils;
import com.superspytx.ab.workflow.GD;

public class CaptchaHandler implements Handler {
	
	@Override
	public boolean run(EventAction info) {
		if (!Settings.captchaEnabled) return false;
		PI pli = GD.getPI(info.player);
		if (pli.cp_haspuzzle) {
			if (pli.cp_puzzle.overboard())
				return true;
			else {
				pli.cp_idle = System.currentTimeMillis();
				if (!pli.cp_puzzle.checkAnswer(info.message)) {
					String wrong = pli.cp_puzzle.getAttempts() == 1 ? Language.captoneLeft : pli.cp_puzzle.getAttempts() + " " + Language.captattemptsLeft;
					info.player.sendMessage(Language.prefix + '\247' + "c" + "Incorrect CAPTCHA! You have " + wrong);
				} else {
					pli.cp_haspuzzle = false;
					pli.cp_solvedpuzzle = true;
					pli.resetSpamData();
					info.player.chat(pli.cs_lsm);
					info.player.sendMessage(Language.prefix + '\247' + "a" + "Correct! Thanks for not being a bot. You can now speak again.");
				}
			}
		}
		return false;
	}
	
	@Override
	public void performActions(EventAction info) {
		Tils.kickPlayer(info.player, Language.captchaKick);
	}
	
}
