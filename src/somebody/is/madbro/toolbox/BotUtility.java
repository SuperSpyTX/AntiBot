package somebody.is.madbro.toolbox;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import somebody.is.madbro.AntiBot;
import somebody.is.madbro.settings.Settings;

public class BotUtility {
	public AntiBot antibot = null;

	public BotUtility(AntiBot instance) {
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
			antibot.getDataTrack().getChatTracker().chatflowscurrent = 0;
			if(antibot.getDataTrack().getChatTracker().chatLockedDown) {
				antibot.getDataTrack().getChatTracker().chatLockedDown = false;
				antibot.getServer().broadcastMessage(
						Settings.prefix + ChatColor.GREEN
								+ "Chat has been unmuted!");
			}
			antibot.getDataTrack().getChatTracker().chatoverflows = 0;
			antibot.getDataTrack().getChatTracker().chatmutedlength = 5L;
			antibot.getDataTrack().getChatTracker().lasttime = 0L;
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

}
