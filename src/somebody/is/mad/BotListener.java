package somebody.is.mad;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class BotListener implements Listener {

	private final AntiBot botclass;
	public long time;
	public long lasttime;
	public long botattempt;
	public int interval = 30000;
	public int accounts = 4;
	public boolean notify = true;
	public boolean useWhiteListPerms = true;
	public boolean useOpPerms = false;
	public boolean enabled = true;
	public boolean debugmode = false;
	public String kickMsg = "The Ban Hammer has spoken!";
	public String connectMsg = "You are not on the whitelist!";
	public String connectInvasion = "The server is currently under attack.";
	public int botcts;
	public boolean reanibo = false;
	private String connected = "";

	public BotListener(AntiBot instance) {
		this.botclass = instance;
	}

	public void debug(String msg) {
		if (debugmode) {
			botclass.getServer().broadcastMessage(
					"\247f[\247bAntiBot\247f] " + msg);
		}
	}

	public void addConnected(String playerName) {
		try {
			connected += playerName + ",";
		} catch (Exception e) {

		}
	}

	public void kickConnected() {
		try {
			String connected2[] = connected.split(",");
			for (String pl : connected2) {
				if (!hasPerms(botclass.getServer().getPlayerExact(pl))) {
					botclass.getServer().getPlayerExact(pl).kickPlayer(kickMsg);
				}
			}
		} catch (Exception e) {

		}
	}

	public boolean useWhitelist(Player pl) {
		if (useWhiteListPerms) {
			return pl.isWhitelisted();
		} else {
			return false;
		}
	}

	public boolean useOp(Player pl) {
		if (useOpPerms) {
			return pl.isOp();
		} else {
			return false;
		}
	}

	public boolean hasPerms(Player pl) {
		if (useOp(pl) || useWhitelist(pl)
				|| botclass.ownPermission("AntiBot.join", pl, 1)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean flush() {
		try {
			if (reanibo) {
				debug("Disabled Reanibios.");
				reanibo = false;
				interval = botclass.defaultinterval;
				connected = "";
				accounts = botclass.defaultaccounts;
				lasttime = 0;
				botattempt = 0;
				if (notify) {
					botclass.getServer()
							.broadcastMessage(
									"\247f[\247bAntiBot\247f] \247aThe minecraft bot invasion has ended. Connection Throttling: \247cDisabled");
				}
			}
			botcts = 0;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean toggle(Boolean e) {
		try {
			enabled = e;
			return true;
		} catch (Exception fe) {
			return false;
		}
	}

	public void onPlayerJoin(PlayerJoinEvent event) {
		if (!enabled) {
			return;
		}
		if (hasPerms(event.getPlayer())) { // It's a double check.
			debug("Whitelisted.");
			if (reanibo
					&& botclass.ownPermission("AntiBot.notify",
							event.getPlayer(), 1)) {
				event.getPlayer().sendMessage(
						"\247f[\247bAntiBot\247f] \247c" + connectInvasion);
			}
			if (reanibo
					&& botclass.ownPermission("AntiBot.admin.notify",
							event.getPlayer(), 2) && interval > 100000) {
				event.getPlayer()
						.sendMessage(
								"\247f[\247bAntiBot\247f] \247cThe system needs a flush. Please type /antibot flush. Thanks.");
			}
			return;
		}
	}

	public void onPlayerLogin(PlayerLoginEvent event) {
		if (!enabled) {
			return;
		}
		String playerName = event.getPlayer().getName();
		debug("User is trying to connect..");
		time = System.currentTimeMillis();
		if (hasPerms(event.getPlayer())) {
			debug("Whitelisted.");
			return;
		}
		if (botcts > accounts + 2 && reanibo) { // Increase violation levels.
			accounts = accounts + 2;
			interval = interval + 5000;
		}

		long math = time - lasttime;
		debug("Checking....0");
		debug("Math: " + math);
		debug("Time: " + time);
		debug("Current Interval: " + interval);
		debug("Lasttime: " + lasttime);
		debug("BotCts: " + botcts + " Accs: " + accounts);
		if (botcts > accounts && math < interval) {
			debug("Hit #1!");
			// Incoming invasion.
			if (!reanibo) {
				if (notify) {
					botclass.getServer()
							.broadcastMessage(
									"\247f[\247bAntiBot\247f] \247cOh no! A minecraft bot invasion has began. Connection Throttling: \247aEnabled");
				}
				reanibo = true;
				debug("Tripswitched!");
				kickConnected();
			}
			botattempt = System.currentTimeMillis();
			botcts += 1;
			event.disallow(PlayerLoginEvent.Result.KICK_OTHER, connectMsg);
		} else if (botattempt < interval && reanibo) {
			debug("Hit #2");
			// Attempting to connect.
			botattempt = System.currentTimeMillis();
			botcts += 1;
			event.disallow(PlayerLoginEvent.Result.KICK_OTHER, connectMsg);
		} else {
			debug("Hit #3");
			if (reanibo) {
				debug("Disabled Reanibios.");
				reanibo = false;
				interval = botclass.defaultinterval;
				connected = "";
				accounts = 4;
				botcts = 0;
				if (notify) {
					botclass.getServer()
							.broadcastMessage(
									"\247f[\247bAntiBot\247f] \247aThe minecraft bot invasion has ended. Connection Throttling: \247cDisabled");
				}
			}
			// No invasion.
			try {
				addConnected(playerName);
				lasttime = System.currentTimeMillis();
				botcts += 1;
			} catch (Exception e) {
				lasttime = System.currentTimeMillis();
				botcts += 1;
			}
		}

	}
}
