package com.superspytx.ab.testing;

import com.superspytx.ab.AB;
import com.superspytx.ab.settings.Settings;

/* This is just an example on how to utilize the API during startup. */
public class APITester {
	public void onEnable() {
		AB.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(AB.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				if (AB.getInstance().getServer().getPluginManager().getPlugin("AntiBot") != null) {
					try {
						if (Settings.enableAntiSpam) {
							com.superspytx.ab.api.AntiBotAPI.deactivateHandler(com.superspytx.ab.handlers.Handlers.CHATSPAM);
						} else {
							com.superspytx.ab.api.AntiBotAPI.activateHandler(com.superspytx.ab.handlers.Handlers.CHATSPAM);
						}
					} catch (com.superspytx.ab.api.AntiBotAPIException e) {
						System.out.println(e.getMessage());
					}
				}
			}
			
		}, 600L); // start in 30 seconds.
	}
}
