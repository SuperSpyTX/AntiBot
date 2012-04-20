package somebody.is.madbro.handlers;

import somebody.is.madbro.AntiBotCore;
import somebody.is.madbro.listeners.BotListener;
import somebody.is.madbro.settings.Settings;

public class PlayerHandler extends HandlerCore {
	
	public String username = "";
	public long lastChatMsg = 0;
	public long connectedFor = 0;
	public int amoumt = 0;
	public BotListener botlistener;
	
	public PlayerHandler(AntiBotCore instance, String usernam) {
		super(instance);
		username = usernam;
		lastChatMsg = System.currentTimeMillis();
		connectedFor = System.currentTimeMillis();
		amoumt = 0;
	}
	
	public boolean connectedForLonger() {
		if(connectedFor < Settings.connectFor) {
			return false;
		}
		
		return true;
	}
	
	public void trig() {
		long math = System.currentTimeMillis() - lastChatMsg;
		if(math > Settings.spamtime) {
			amoumt = 1;
			lastChatMsg = System.currentTimeMillis();
		} else {
			amoumt += 1;
			lastChatMsg = System.currentTimeMillis();
		}
	}
	
}
