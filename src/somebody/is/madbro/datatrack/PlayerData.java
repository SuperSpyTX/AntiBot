package somebody.is.madbro.datatrack;

import somebody.is.madbro.AntiBot;
import somebody.is.madbro.listeners.BotListener;
import somebody.is.madbro.settings.Settings;

public class PlayerData {

	public String username = "";
	public long lastChatMsg = 0;
	public long connectedFor = 0;
	public int amoumt = 0;
	public boolean hasMoved = false;
	public BotListener botlistener;

	public AntiBot antibot = null;

	public PlayerData(AntiBot instance, String usernam) {
		antibot = instance;
		username = usernam;
		lastChatMsg = System.currentTimeMillis();
		connectedFor = System.currentTimeMillis();
		amoumt = 0;
	}

	public boolean connectedForLonger() {
		int math = (int) (System.currentTimeMillis() - connectedFor);
		if (math < Settings.connectFor) {
			return false;
		}

		if (!hasMoved) {
			return false; // bots can't move.
		}

		return true;
	}

	public void moved() {
		hasMoved = true;
		antibot.getUtility()
				.getDebug()
				.debug("Player " + username
						+ " has moved! Exempt from false antibot kicks!");
	}

	public void trig() {
		long math = System.currentTimeMillis() - lastChatMsg;
		if (math > Settings.spamtime) {
			amoumt = 1;
			lastChatMsg = System.currentTimeMillis();
		} else {
			amoumt += 1;
			lastChatMsg = System.currentTimeMillis();
		}
	}

}
