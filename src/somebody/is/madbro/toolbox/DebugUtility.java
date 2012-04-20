package somebody.is.madbro.toolbox;

import somebody.is.madbro.AntiBotCore;
import somebody.is.madbro.settings.Settings;

public class DebugUtility extends UtilityCore {
	
	public DebugUtility(AntiBotCore instance) {
		super(instance);
		// TODO Auto-generated constructor stub
	}

	public void debug(String msg) {
		if (Settings.debugmode) {
			antibot.getServer().broadcastMessage(
					Settings.prefix + "" + msg);
		}
	}

}
