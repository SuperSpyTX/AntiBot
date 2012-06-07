package me.freebuild.superspytx.antibot.datatrack;

import org.bukkit.entity.Player;

import me.freebuild.superspytx.antibot.Core;
import me.freebuild.superspytx.antibot.handlers.BotHandler;
import me.freebuild.superspytx.antibot.handlers.chat.ChatSpamHandler;

public class DataTrackCore {
	
	private Core antibot = null;
	
	// trackers
	private BotDataTrack botdata = null;
	private ChatDataTrack chatdata = null;
	private CountryDataTrack countrydata = null;
	private CaptchaDataTrack captchadata = null;
	
	public DataTrackCore(Core instance) {
		antibot = instance;
		botdata = new BotDataTrack(instance);
		chatdata = new ChatDataTrack(instance);
		countrydata = new CountryDataTrack(instance);
		captchadata = new CaptchaDataTrack(instance);
	}
	
	public PlayerData getPlayer(String username, ChatSpamHandler thi) {
		return new PlayerData(antibot, username);
	}
	
	public PlayerData getPlayer(String username, BotHandler thi) {
		return new PlayerData(antibot, username);
	}
	
	public Puzzle getPuzzle(Player player) {
		return new Puzzle(antibot, player);
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
	
	public CaptchaDataTrack getCaptchaTracker() {
		return captchadata;
	}

}
