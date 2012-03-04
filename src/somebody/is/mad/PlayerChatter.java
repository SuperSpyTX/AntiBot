package somebody.is.mad;

public class PlayerChatter {

	public String username = "";
	public long lastChatMsg = 0;
	public int amoumt = 0;
	
	public PlayerChatter(String usernam) {
		username = usernam;
		lastChatMsg = System.currentTimeMillis();
		amoumt += 1;
	}
	
	public void trig() {
		lastChatMsg = System.currentTimeMillis();
		amoumt += 1;
	}
	
}
