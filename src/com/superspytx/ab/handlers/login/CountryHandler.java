package com.superspytx.ab.handlers.login;

import com.superspytx.ab.AB;
import com.superspytx.ab.abs.EventAction;
import com.superspytx.ab.abs.Handler;
import com.superspytx.ab.settings.Settings;
import com.superspytx.ab.tils.GeoTils;
import com.superspytx.ab.workflow.GD;

public class CountryHandler implements Handler {
	
	@Override
	public boolean run(EventAction info) {
		if (!Settings.geoIP) return false;
		AB.debug("Country Bans check!");
		if (!GeoTils.isLoaded()) return false;
		AB.debug("GeoTools loaded!");
		if (info.ip == null) {
			AB.debug("CB: IP is null.");
			return false;
		}
		
		return GeoTils.detect(info.ip);
	}
	
	@Override
	public void performActions(EventAction info) {
		// do nothing. the player is already denied access.
		GD.cb_invs++;
	}
	
}
