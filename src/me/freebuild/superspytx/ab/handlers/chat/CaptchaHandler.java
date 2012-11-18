package me.freebuild.superspytx.ab.handlers.chat;

import me.freebuild.superspytx.ab.AB;
import me.freebuild.superspytx.ab.abs.EventAction;
import me.freebuild.superspytx.ab.abs.Handler;
import me.freebuild.superspytx.ab.abs.PI;
import me.freebuild.superspytx.ab.settings.Language;
import me.freebuild.superspytx.ab.settings.Settings;
import me.freebuild.superspytx.ab.workflow.GD;

public class CaptchaHandler implements Handler {
	
	@Override
	public boolean run(EventAction info) {
		if (!Settings.captchaEnabled) return false;
		PI pli = GD.getPI(info.player);
		if (pli.cp_haspuzzle) {
			if (pli.cp_puzzle.overboard())
				return true;
			else {
				if (!pli.cp_puzzle.checkAnswer(info.message)) {
					String wrong = pli.cp_puzzle.getAttempts() == 1 ? Language.captoneLeft : pli.cp_puzzle.getAttempts() + " " + Language.captattemptsLeft;
					info.player.sendMessage(Language.prefix + '\247' + "c" + "Incorrect CAPTCHA! You have " + wrong);
				} else {
					pli.cp_haspuzzle = false;
					pli.cp_solvedpuzzle = true;
					pli.resetSpamData();
					info.player.sendMessage(Language.prefix + '\247' + "a" + "Correct! Thanks for not being a bot. You can now speak again.");
				}
			}
		}
		return false;
	}
	
	@Override
	public void performActions(EventAction info) {
		AB.kickPlayer(info.player, Language.captchaKick);
	}
	
}
