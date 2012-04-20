package somebody.is.madbro.toolbox;

import somebody.is.madbro.AntiBotCore;
import somebody.is.madbro.listeners.BotListener;

public class UtilityCore {
	
	//utilities
	private BotUtility botutility = null;
	private DebugUtility debugutility = null;
	
	//listeners
	protected BotListener botlistener = null;
	
	protected AntiBotCore antibot = null;
	
	public UtilityCore(AntiBotCore instance) {
		antibot = instance;
		botlistener = instance.getBotListener();
		botutility = new BotUtility(instance);
		debugutility = new DebugUtility(instance);
		
	}
	
	public BotUtility getBotUtility() {
		return botutility;
	}
	
	public DebugUtility getDebugUtility() {
		return debugutility;
	}
	
	
}
