package somebody.is.madbro.toolbox;

import somebody.is.madbro.AntiBotCore;
import somebody.is.madbro.settings.Settings;

public class DebugUtility {
	public AntiBotCore antibot = null;
	public DebugUtility(AntiBotCore instance) {
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
