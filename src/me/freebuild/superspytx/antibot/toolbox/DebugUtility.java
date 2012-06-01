package me.freebuild.superspytx.antibot.toolbox;

import me.freebuild.superspytx.antibot.Core;
import me.freebuild.superspytx.antibot.settings.Settings;

public class DebugUtility {
	public Core antibot = null;
	public DebugUtility(Core instance) {
		antibot = instance;
		// TODO Auto-generated constructor stub
	}

	public void debug(String msg) {
		if (Settings.debugmode) {
			antibot.getServer().broadcastMessage(
					Settings.prefix + "" + msg);
		}
	}

}
