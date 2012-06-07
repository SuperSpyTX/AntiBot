package me.freebuild.superspytx.antibot.toolbox;

import me.freebuild.superspytx.antibot.Core;
import me.freebuild.superspytx.antibot.listeners.BotListener;

public class UtilityCore {
	
	//utilities
	private BotUtility botutility = null;
	private DebugUtility debugutility = null;
	private GeoIPUtility geoiputility = null;
	private DeregisterUtility deregisterutility = null;
	private CaptchaUtility captchautility = null;
	
	//listeners
	protected BotListener botlistener = null;
	
	protected Core antibot = null;
	
	public UtilityCore(Core instance) {
		antibot = instance;
		botlistener = instance.getBotListener();
		botutility = new BotUtility(instance);
		debugutility = new DebugUtility(instance);
		geoiputility = new GeoIPUtility(instance);
		deregisterutility = new DeregisterUtility(instance);
		captchautility = new CaptchaUtility(instance);
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
	
	public CaptchaUtility getCaptcha() {
		return captchautility;
	}
	
	
}
