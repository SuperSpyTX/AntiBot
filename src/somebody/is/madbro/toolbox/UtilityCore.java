package somebody.is.madbro.toolbox;

import somebody.is.madbro.AntiBotCore;
import somebody.is.madbro.listeners.BotListener;

public class UtilityCore {
	
	//utilities
	private BotUtility botutility = null;
	private DebugUtility debugutility = null;
	private GeoIPUtility geoiputility = null;
	private DeregisterUtility deregisterutility = null;
	
	//listeners
	protected BotListener botlistener = null;
	
	protected AntiBotCore antibot = null;
	
	public UtilityCore(AntiBotCore instance) {
		antibot = instance;
		botlistener = instance.getBotListener();
		botutility = new BotUtility(instance);
		debugutility = new DebugUtility(instance);
		geoiputility = new GeoIPUtility(instance);
		deregisterutility = new DeregisterUtility(instance);
	}
	
	public BotUtility getBot() {
		return botutility;
	}
	
	public DebugUtility getDebug() {
		return debugutility;
	}
	
	public GeoIPUtility getGeoIP() {
		return geoiputility;
	}
	
	public DeregisterUtility getDeregister() {
		return deregisterutility;
	}
	
	
}
