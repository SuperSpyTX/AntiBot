package me.freebuild.superspytx.antibot.datatrack;

import me.freebuild.superspytx.antibot.Core;
import me.freebuild.superspytx.antibot.chat.ChatSpamHandler;
import me.freebuild.superspytx.antibot.handlers.BotHandler;

public class DataTrackCore {
	
	public Core antibot = null;
	
	// trackers
	public BotDataTrack botdata = null;
	public ChatDataTrack chatdata = null;
	public CountryDataTrack countrydata = null;
	
	public DataTrackCore(Core instance) {
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
