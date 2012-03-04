package somebody.is.mad;

public class PlayerChatter {

	public String username = "";
	public long lastChatMsg = 0;
	public int amoumt = 0;
	public BotListener botlistener;
	
	public PlayerChatter(String usernam, BotListener instance) {
		username = usernam;
		botlistener = instance;
		lastChatMsg = System.currentTimeMillis();
		amoumt += 1;
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
