package somebody.is.madbro.toolbox;

import somebody.is.madbro.AntiBot;
import somebody.is.madbro.settings.Settings;

public class DebugUtility {
	public AntiBot antibot = null;
	public DebugUtility(AntiBot instance) {
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
