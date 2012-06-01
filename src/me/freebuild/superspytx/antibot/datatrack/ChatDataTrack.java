package me.freebuild.superspytx.antibot.datatrack;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import me.freebuild.superspytx.antibot.Core;


public class ChatDataTrack {
	
	public Core botclass = null;
	
	public ChatDataTrack(Core instance) {
		botclass = instance;
	}
	
	// chat spamdata
	public CopyOnWriteArrayList<String> spammyPlayers = new CopyOnWriteArrayList<String>();
	public HashMap<String, PlayerData> trackplayers = new HashMap<String, PlayerData>();
	public int chatspamblocked = 0;
	
	// chat flowdata
	public int chatoverflows = 0;
	public Long chatmutedlength = 5L;
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
