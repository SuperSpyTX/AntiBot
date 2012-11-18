package me.freebuild.superspytx.ab.handlers.login;

import me.freebuild.superspytx.ab.abs.EventAction;
import me.freebuild.superspytx.ab.abs.Handler;
import me.freebuild.superspytx.ab.settings.Settings;
import me.freebuild.superspytx.ab.tils.Tils;

public class LoginDelayHandler implements Handler {
	
	@Override
	public boolean run(EventAction info) {
		// Data Analysis in 3 lines.
		if (!Settings.logindelayEnabled) return false;
		if (info.pi.ab_lastdc < 2L) return false; // just to be safe.
		return Tils.getLongDiff(info.pi.ab_lastdc) < Settings.loginDelay;
	}
	
	@Override
	public void performActions(EventAction info) {
		// do nothing, handled already.
	}
	
}
