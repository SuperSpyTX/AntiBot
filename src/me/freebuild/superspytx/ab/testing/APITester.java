package me.freebuild.superspytx.ab.testing;

import me.freebuild.superspytx.ab.AB;
import me.freebuild.superspytx.ab.settings.Settings;

/* This is just an example on how to utilize the API during startup. */
public class APITester {
	public void onEnable() {
		AB.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(AB.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				if (AB.getInstance().getServer().getPluginManager().getPlugin("AntiBot") != null) {
					try {
						if (Settings.enableAntiSpam) {
							me.freebuild.superspytx.ab.api.AntiBotAPI.deactivateHandler(me.freebuild.superspytx.ab.handlers.Handlers.CHATSPAM);
						} else {
							me.freebuild.superspytx.ab.api.AntiBotAPI.activateHandler(me.freebuild.superspytx.ab.handlers.Handlers.CHATSPAM);
						}
					} catch (me.freebuild.superspytx.ab.api.AntiBotAPIException e) {
						System.out.println(e.getMessage());
					}
				}
			}
			
		}, 600L); // start in 30 seconds.
	}
}
