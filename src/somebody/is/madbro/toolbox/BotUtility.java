package somebody.is.madbro.toolbox;

import java.util.Random;

import org.bukkit.entity.Player;

import somebody.is.madbro.AntiBotCore;
import somebody.is.madbro.settings.Settings;

public class BotUtility {
	public AntiBotCore antibot = null;

	public BotUtility(AntiBotCore instance) {
		antibot = instance;
		// TODO Auto-generated constructor stub
	}

	public boolean toggle(Boolean e) {
		try {
			Settings.enabled = e;
			return true;
		} catch (Exception fe) {
			return false;
		}
	}

	public boolean flush() {
		try {
			if (antibot.getDataTrack().getBotTracker().reanibo) {
				antibot.getUtility().getDebug()
						.debug("Disabled Reanibios.");
				antibot.getDataTrack().getBotTracker().reanibo = false;
				Settings.interval = antibot.getDefaultinterval();
				antibot.getDataTrack().getBotTracker().connected.clear();
				Settings.accounts = antibot.getDefaultaccounts();
				antibot.getDataTrack().getBotTracker().lasttime = 0;
				antibot.getDataTrack().getBotTracker().botattempt = 0;
				if (Settings.notify && Settings.whiteList) {
					antibot.getServer()
							.broadcastMessage(
									Settings.prefix
											+ "\247aThe minecraft bot invasion has ended. Connection Throttling: \247cDisabled");
				}
			}
			antibot.getDataTrack().getBotTracker().botcts = 0;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean flush2() {
		try {
			antibot.getDataTrack().getBotTracker().reanibo = false;
			Settings.interval = antibot.getDefaultinterval();
			antibot.getDataTrack().getBotTracker().connected.clear();
			antibot.getDataTrack().getBotTracker().autokick.clear();
			antibot.getDataTrack().getBotTracker().autoipkick.clear();
			antibot.getDataTrack().getChatTracker().spammyPlayers.clear();
			antibot.getDataTrack().getBotTracker().ipList.clear();
			Settings.accounts = antibot.getDefaultaccounts();
			antibot.getDataTrack().getBotTracker().lasttime = 0;
			antibot.getDataTrack().getBotTracker().botattempt = 0;
			if (Settings.notify && Settings.whiteList) {
				antibot.getServer()
						.broadcastMessage(
								Settings.prefix
										+ "\247aThe minecraft bot invasion has ended. Connection Throttling: \247cDisabled");
			}
			antibot.getDataTrack().getBotTracker().botcts = 0;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public int getRandomInt() {
		Random rdm = new Random();
		return rdm.nextInt(5000);
	}

	public int getRandomIntInvasion() {
		if (!antibot.getDataTrack().getBotTracker().reanibo) {
			return 0;
		}
		Random rdm = new Random();
		if (Settings.interval > 35000) {
			return rdm.nextInt(7000);
		} else if (Settings.interval > 45000) {
			return rdm.nextInt(25000);
		}
		return rdm.nextInt(5000);
	}

	public void kickConnected() {
		// int kicked = 0;
		antibot.getUtility()
				.getDebug()
				.debug("Kicking players with method #1 Size: "
						+ antibot.getDataTrack().getBotTracker().connected
								.size());
		for (String pl : antibot.getDataTrack().getBotTracker().connected) {
			try {
				antibot.getUtility().getDebug()
						.debug("Checking if kick possible for player..." + pl);
				Player p2 = antibot.getServer().getPlayerExact(pl);
				if (!antibot.getDataTrack().getChatTracker().checkConnection(pl)) {
					antibot.getUtility().getDebug()
							.debug("Yes, Kicking player..." + pl);
					antibot.getServer().getPlayerExact(pl)
							.kickPlayer(Settings.kickMsg);
					antibot.getDataTrack().getBotTracker().spambotsblocked += 1;
					if (Settings.banUsers) {
						antibot.getDataTrack().getBotTracker().autoipkick
								.add(p2.getAddress().toString().split(":")[0]);
						antibot.getDataTrack().getBotTracker().autokick.add(pl);
					}
					// kicked += 1;
					antibot.getUtility().getDebug()
							.debug("Kicked player with method #1");
					antibot.getUtility()
							.getDebug()
							.debug("We now have autokick: "
									+ antibot.getDataTrack().getBotTracker().autokick
											.size()
									+ " ip: "
									+ antibot.getDataTrack().getBotTracker().autoipkick
											.size());
				} else {
					antibot.getUtility().getDebug()
							.debug("Not possible for player ...." + pl);
					antibot.getDataTrack().getBotTracker().connected.remove(pl);
				}
			} catch (Exception e) {
				// if it fails. go down here.
				e.printStackTrace();
				antibot.getUtility().getDebug()
						.debug("Failed to kick: " + pl);
			}
		}
		antibot.getDataTrack().getBotTracker().connected.clear();

		// kick players if the above method doesn't work :|
		/*
		 * antibot.getUtility().getDebug().debug("Checking if " + kicked
		 * + " is less than 1"); if (kicked < 1) {
		 * antibot.getUtility().getDebug()
		 * .debug("Kicking player with method #2"); Player[] players =
		 * antibot.getServer().getOnlinePlayers(); for (Player pl : players) {
		 * if (!hasPerms(pl)) { pl.kickPlayer(Settings.connectMsg);
		 * autokick.add(pl); antibot.getUtility().getDebug().debug(
		 * "Kicked player with method #2" ); } } }
		 */

	}

}
