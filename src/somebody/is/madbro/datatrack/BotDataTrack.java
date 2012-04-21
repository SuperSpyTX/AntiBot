package somebody.is.madbro.datatrack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.entity.Player;

import somebody.is.madbro.AntiBotCore;
import somebody.is.madbro.settings.Settings;

public class BotDataTrack {
	
	public AntiBotCore botclass = null;
	
	public BotDataTrack(AntiBotCore instance) {
		botclass = instance;
	}
	
	public ArrayList<String> autokick = new ArrayList<String>();
	public ArrayList<String> autoipkick = new ArrayList<String>();
	public ArrayList<String> spammyPlayers = new ArrayList<String>();
	public HashMap<String, String> ipList = new HashMap<String, String>();
	public List< String > connected = new CopyOnWriteArrayList< String >();
	public HashMap<String, PlayerData> trackplayers = new HashMap<String, PlayerData>();
	public long time;
	public long lasttime;
	public long botattempt;
	public int spamcts = 0;
	public int spambotsblocked = 0;
	public int botcts;
	public boolean reanibo = false;
	
	public void trackPlayer(Player e, String IP) {
		if (botclass.getHandler().getPermissions().hasPerms(e)) {
			return;
		}
		if (!ipList.containsKey(IP)) {
			ipList.put(IP, e.getName());
		} else {
			if (!Settings.enableMultiAccs) {
				return;
			}
			String pl = "";
			try {
				pl = ipList.get(IP);
			} catch (Exception e1) {

			}
			if (!pl.equals(e.getName()) || ipList.containsValue(e.getName())
					&& !ipList.containsKey(IP)) {
				if(Settings.banUsers) {
					autoipkick.add(IP);
				}
				if (Settings.notify) {
					botclass.getServer().broadcastMessage(
							Settings.prefix
									+ "\247chas detected multiple accounts!!");
				}
				try {
					e.kickPlayer(Settings.kickMsg);
					botclass.getServer().getPlayerExact(ipList.get(IP))
							.kickPlayer(Settings.kickMsg);
				} catch (Exception e1) {

				}
			}
		}
	}
	
	public void addConnected(String playerName) {
		try {
			if (!connected.contains(playerName)) {
				connected.add(playerName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			botclass.getUtility().getDebugUtility()
					.debug("Adding new player failed: " + e.getMessage());
		}
	}

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
