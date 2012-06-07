package me.freebuild.superspytx.antibot.toolbox;

import me.freebuild.superspytx.antibot.Core;

import org.bukkit.entity.Player;

public class DeregisterUtility {

	public Core antibot = null;

	public DeregisterUtility(Core instance) {
		antibot = instance;
	}

	public String getUserFromIP(String IP) {
		try {
			return antibot.getDataTrack().getBotTracker().ipList.get(IP);
		} catch (Exception e) {
			return "";
		}
	}

	/*public void handle(String IP) {
		if (!IP.contains("/")) {
			IP = "/" + IP;
		}
		antibot.getDataTrack().getBotTracker()
				.removeConnected(getUserFromIP(IP));
		antibot.getDataTrack().getChatTracker().trackplayers
				.remove(getUserFromIP(IP));
	}*/

	public void handle(Player pl) {
		antibot.getDataTrack().getBotTracker().removeConnected(pl.getName());
		antibot.getDataTrack().getChatTracker().trackplayers
				.remove(getUserFromIP(pl.getName()));
		if (antibot.getDataTrack().getCaptchaTracker().puzzles.containsKey(pl))
			antibot.getDataTrack().getCaptchaTracker().puzzles.remove(pl);
		if (antibot.getDataTrack().getCaptchaTracker().solvedplayers
				.contains(pl.getName()))
			antibot.getDataTrack().getCaptchaTracker().solvedplayers.remove(pl
					.getName());
	}

}
