package me.freebuild.superspytx.ab.callunits;

import java.util.ArrayList;
import java.util.List;
import me.freebuild.superspytx.ab.AntiBot;
import me.freebuild.superspytx.ab.handlers.Handlers;
import me.freebuild.superspytx.ab.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

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
