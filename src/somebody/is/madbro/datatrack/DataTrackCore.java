package somebody.is.madbro.datatrack;

import somebody.is.madbro.AntiBot;
import somebody.is.madbro.handlers.BotHandler;
import somebody.is.madbro.handlers.chat.ChatSpamHandler;

public class DataTrackCore {
	
	public AntiBot antibot = null;
	
	// trackers
	public BotDataTrack botdata = null;
	public ChatDataTrack chatdata = null;
	public CountryDataTrack countrydata = null;
	
	public DataTrackCore(AntiBot instance) {
		antibot = instance;
		botdata = new BotDataTrack(instance);
		chatdata = new ChatDataTrack(instance);
		countrydata = new CountryDataTrack(instance);
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
	
	public ChatDataTrack getChatTracker() {
		return chatdata;
	}
	
	public CountryDataTrack getCountryTracker() {
		return countrydata;
	}

}
