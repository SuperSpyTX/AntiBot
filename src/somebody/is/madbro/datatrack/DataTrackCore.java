package somebody.is.madbro.datatrack;

import somebody.is.madbro.AntiBotCore;
import somebody.is.madbro.handlers.BotHandler;
import somebody.is.madbro.handlers.ChatSpamHandler;

public class DataTrackCore {
	
	public AntiBotCore antibot = null;
	
	public BotDataTrack botdata = null;
	
	public DataTrackCore(AntiBotCore instance) {
		antibot = instance;
		botdata = new BotDataTrack(instance);
	}
	
	public PlayerData getPlayer(String username, ChatSpamHandler thi) {
		return new PlayerData(antibot, username);
	}
	
	public PlayerData getPlayer(String username, BotHandler thi) {
		return new PlayerData(antibot, username);
	}
	
	public BotDataTrack getBotTracker() {
		return botdata;
	}

}
