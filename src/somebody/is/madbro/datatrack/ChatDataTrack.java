package somebody.is.madbro.datatrack;

import java.util.ArrayList;
import java.util.HashMap;

import somebody.is.madbro.AntiBotCore;

public class ChatDataTrack {
	
	public AntiBotCore botclass = null;
	
	public ChatDataTrack(AntiBotCore instance) {
		botclass = instance;
	}
	
	public ArrayList<String> spammyPlayers = new ArrayList<String>();
	public HashMap<String, PlayerData> trackplayers = new HashMap<String, PlayerData>();
	
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
