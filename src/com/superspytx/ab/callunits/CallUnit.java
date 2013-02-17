package com.superspytx.ab.callunits;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import com.superspytx.ab.AntiBot;
import com.superspytx.ab.handlers.Handlers;
import com.superspytx.ab.settings.Settings;

public class CallUnit implements Listener {
	private List<CallUnit> units;
	
	public CallUnit() {
		if (!Settings.isSet()) {
			Settings.setup(); // there we go.
			units = new ArrayList<CallUnit>(); // only initialize if it's being setup.
			for (Handlers g : Handlers.values()) {
				CallUnit unit = g.getUnit();
				if (unit == null) continue;
				
				units.add(unit);
			}
			units.add(new GarbageUnit());
		}
	}
	
	public void registerUnits() {
		if (units == null || units.size() == 0) return;
		for (CallUnit unit : units) {
			Bukkit.getPluginManager().registerEvents(unit, AntiBot.getInstance());
		}
		units.clear(); // make sure it's cleared....
	}
	
}
