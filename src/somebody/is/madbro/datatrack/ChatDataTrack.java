package somebody.is.madbro.datatrack;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import somebody.is.madbro.AntiBot;

public class ChatDataTrack {
	
	public AntiBot botclass = null;
	
	public ChatDataTrack(AntiBot instance) {
		botclass = instance;
	}
	
	// chat spamdata
	public CopyOnWriteArrayList<String> spammyPlayers = new CopyOnWriteArrayList<String>();
	public HashMap<String, PlayerData> trackplayers = new HashMap<String, PlayerData>();
	public int chatspamblocked = 0;
	
	// chat flowdata
	public int chatoverflows = 0;
	public int settimetooverflow = 5000; //todo: set as a setting.
	public int setoverflows = 3; //todo: set as a setting.
	public String overflowedmessage = "SILENCE! Let's cool it down for %sec% seconds."; //todo: set as a setting.
	public Long chatmutedlength = 5L; //todo: possible setting.
	public int chatflowscurrent = 0;
	public Long lasttime = 0L;
	public boolean chatLockedDown = false;
	
	public boolean checkConnection(String usr) {
		if (trackplayers.containsKey(usr)) {
			PlayerData mp = (PlayerData) trackplayers.get(usr);
			if (mp.connectedForLonger()) {
				return true;
			}
		}
		return false;
	}

}
