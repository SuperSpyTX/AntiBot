package somebody.is.madbro.toolbox;

import org.bukkit.entity.Player;

import somebody.is.madbro.AntiBotCore;

public class DeregisterUtility {

	public AntiBotCore antibot = null;

	public DeregisterUtility(AntiBotCore instance) {
		antibot = instance;
	}

	public String getUserFromIP(String IP) {
		try {
			return antibot.getDataTrack().getBotTracker().ipList.get(IP);
		} catch (Exception e) {
			return "";
		}
	}

	public void handle(String IP) {
		if (!IP.contains("/")) {
			IP = "/" + IP;
		}
		antibot.getDataTrack().getBotTracker()
				.removeConnected(getUserFromIP(IP));
		antibot.getDataTrack().getChatTracker().trackplayers
				.remove(getUserFromIP(IP));
	}

	public void handle(Player pl) {
		antibot.getDataTrack().getBotTracker()
				.removeConnected(pl.getName());
		antibot.getDataTrack().getChatTracker().trackplayers
				.remove(getUserFromIP(pl.getName()));
	}

}
