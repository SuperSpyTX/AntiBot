package com.superspytx.ab.workflow;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import com.superspytx.ab.AntiBot;
import com.superspytx.ab.abs.EventAction;
import com.superspytx.ab.handlers.Handlers;
import com.superspytx.ab.settings.Settings;

public class Agent {
	
	public static boolean dispatchUnit(final Event event, final Handlers handle, boolean handleAnyways) {
		if (!Settings.enabled && !handle.equals(Handlers.COMMAND)) return false;
		if ((new EventAction(event, false).cancelled) && !handleAnyways) return false;
		
		try {
			if ((new EventAction(event, false).pi).cp_haspuzzle && !handle.equals(Handlers.CAPTCHA)) return false;
		} catch (NullPointerException e) {}
		
		if (!handle.checkUser((new EventAction(event, false)).player)) return false;
		
		if (handle.getHandler().run(new EventAction(event, false))) {
			Bukkit.getScheduler().runTaskLaterAsynchronously(AntiBot.getInstance(), new Runnable() {
				public void run() {
					handle.getHandler().performActions(new EventAction(event, false));
				}
			}, 0L); // Run on the same tick.
			return true;
		}
		return false;
	}
	
	public static void asyncDispatchUnit(final Event event, final Handlers handle, final boolean handleAnyways) {
		/* If you know this isn't "asynchronous", I apologize, but that's the title of the method anyways :3 */
		/* This is mainly for stuff that is going to count against the player, or stuff that won't cancel the event. */
		if (!Settings.enabled && !handle.equals(Handlers.COMMAND)) return;
		Bukkit.getScheduler().runTaskLaterAsynchronously(AntiBot.getInstance(), new Runnable() {
			public void run() {
				if (!Settings.enabled) return;
				if ((new EventAction(event, true).cancelled) && !handleAnyways) return;
				
				if (!handle.checkUser((new EventAction(event, true)).player)) return;
				
				if (handle.getHandler().run(new EventAction(event, true))) {
					handle.getHandler().performActions(new EventAction(event, true));
				}
			}
		}, 0L); // Run on the same tick.
	}
}
