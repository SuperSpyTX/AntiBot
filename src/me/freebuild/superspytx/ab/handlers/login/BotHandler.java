package me.freebuild.superspytx.ab.handlers.login;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.bukkit.Bukkit;
import me.freebuild.superspytx.ab.AB;
import me.freebuild.superspytx.ab.abs.EventAction;
import me.freebuild.superspytx.ab.abs.Handler;
import me.freebuild.superspytx.ab.abs.PI;
import me.freebuild.superspytx.ab.settings.Language;
import me.freebuild.superspytx.ab.settings.Settings;
import me.freebuild.superspytx.ab.tils.CaptchaTils;
import me.freebuild.superspytx.ab.tils.Tils;
import me.freebuild.superspytx.ab.workflow.GD;

public class BotHandler implements Handler {
	
	@Override
	public boolean run(EventAction info) {
		if (!GD.b_cp.contains(info.player.getName())) GD.b_cp.add(GD.getPI(info.player));
		
		if ((GD.getPI(info.player)).b_connectfor == 0L) (GD.getPI(info.player)).b_connectfor = System.currentTimeMillis();
		
		AB.debug("All initial stuff for BotHandler done");
		
		if (GD.b_lc != 0L) {
			AB.debug("Lastlogin > 0");
			if (Tils.getLongDiff(GD.b_lc) < Settings.interval) {
				GD.b_cts++;
				AB.debug("Counting!");
				if (GD.b_cts >= Settings.accounts) {
					// TODO: Perform Action - Connection Throttled.
					AB.debug("Bots tripwired!");
					GD.b_lc = System.currentTimeMillis();
					return true;
				}
			} else {
				AB.debug("Count reset to 1");
				GD.b_cts = 1;
			}
		} else {
			AB.debug("Initial join");
		}
		
		AB.debug("Current Time: " + Tils.getLongDiff(GD.b_lc));
		GD.b_lc = System.currentTimeMillis();
		
		return false;
	}
	
	@Override
	public void performActions(EventAction info) {
		if (GD.b_kicking) {
			if (Settings.captchaEnabled && Settings.forceCaptchaOnBotSpam)
				CaptchaTils.sendCaptchaToPlayer(info.player);
			else
				info.player.kickPlayer(Language.kickMsg);
			return;
		}
		GD.b_kicking = true;
		final List<PI> list = new CopyOnWriteArrayList<PI>(GD.b_cp);
		AB.debug("KICK SIZE: " + list.size());
		for (final PI pl : list) {
			try {
				AB.debug("Testing player: " + pl.p_name);
				if (pl != null) pl.updateStatus();
				if (pl == null || pl.pl == null) continue;
				if (Bukkit.getPlayerExact(pl.pl.getName()) == null) continue;
				AB.debug("Kicking " + pl.p_name);
				if (Tils.getLongDiff(pl.b_connectfor) < Settings.connectFor) { // TODO: Remove check after things are working.
					if (Settings.captchaEnabled && Settings.forceCaptchaOnBotSpam)
						CaptchaTils.sendCaptchaToPlayer(pl.pl);
					else
						pl.pl.kickPlayer(Language.kickMsg);
					GD.b_blks++;
				}
			} catch (NullPointerException e) {
				continue;
			}
		}
		GD.b_cp.clear();
		GD.b_kicking = false;
	}
	
}
