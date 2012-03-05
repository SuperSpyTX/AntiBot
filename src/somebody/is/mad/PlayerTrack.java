package somebody.is.mad;

public class PlayerTrack {

	public String username = "";
	public long lastChatMsg = 0;
	public long connectedFor = 0;
	public int amoumt = 0;
	public BotListener botlistener;
	
	public PlayerTrack(String usernam, BotListener instance) {
		username = usernam;
		botlistener = instance;
		lastChatMsg = System.currentTimeMillis();
		connectedFor = System.currentTimeMillis();
		amoumt = 0;
	}
	
	public boolean connectedForLonger() {
		if(connectedFor < botlistener.connectFor) {
			return false;
		}
		
		return true;
	}
	
	public void trig() {
		long math = System.currentTimeMillis() - lastChatMsg;
		if(math > botlistener.spamtime) {
			amoumt = 1;
			lastChatMsg = System.currentTimeMillis();
		} else {
			amoumt += 1;
			lastChatMsg = System.currentTimeMillis();
		}
	}
	
}
