package somebody.is.mad;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.entity.Player;
//import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class BotListener implements Listener {

	private final AntiBot botclass;
	public long time;
	public long lasttime;
	public long botattempt;
	public int interval = 15000;
	public int accounts = 4;
	public boolean notify = true;
	public boolean useWhiteListPerms = true;
	public boolean useOpPerms = false;
	public boolean enabled = true;
	public boolean debugmode = false;
	public boolean whiteList = false;
	public ArrayList<String> autokick = new ArrayList<String>();
	public ArrayList<String> autoipkick = new ArrayList<String>();
	public String kickMsg = "The Ban Hammer has spoken!";
	public String connectMsg = "You are not on the whitelist!";
	public String connectInvasion = "The server is currently under attack.";
	public String lastMessage = "";
	public int spamcts = 0;
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
			//int kicked = 0;
			String connected2[] = connected.split(",");
			debug("Kicking players with method #1");
			for (String pl : connected2) {
				if (!hasPerms(botclass.getServer().getPlayerExact(pl))) {
					autoipkick.add(botclass.getServer().getPlayerExact(pl).getAddress().toString().split(":")[0]);
					botclass.getServer().getPlayerExact(pl).kickPlayer(kickMsg);
					autokick.add(pl);
					//kicked += 1;
					debug("Kicked player with method #1");
				}
			}

			// kick players if the above method doesn't work :|
			/*
			debug("Checking if " + kicked + " is less than 1");
			if (kicked < 1) {
				debug("Kicking player with method #2");
				Player[] players = botclass.getServer().getOnlinePlayers();
				for (Player pl : players) {
					if (!hasPerms(pl)) {
						pl.kickPlayer(connectMsg);
						autokick.add(pl);
						debug("Kicked player with method #2");
					}
				}
			}
			
			*/

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
				if (notify && whiteList) {
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

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (!enabled) {
			return;
		}
		if(autokick.contains(event.getPlayer().getName())) {
			event.getPlayer().kickPlayer(kickMsg);
			return;
		}
		
		if(autoipkick.contains(event.getPlayer().getAddress()
				.toString().split(":")[0])) {
			event.getPlayer().kickPlayer(kickMsg);
			return;
		}
		
		// IP tracking usernames system.
		trackPlayer(event.getPlayer(), event.getPlayer().getAddress()
				.toString().split(":")[0]);
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

	public int getRandomInt() {
		if (!reanibo) {
			return 0;
		}
		Random rdm = new Random();
		if (interval > 35000) {
			return rdm.nextInt(7000);
		} else if (interval > 45000) {
			return rdm.nextInt(25000);
		}
		return 0;
	}

	@SuppressWarnings("rawtypes")
	public void trackPlayer(Player ev, String IP) {
		if (!botclass.iplist.isEmpty()) {
			// check ipmap list.
			Iterator lulz = botclass.iplist.entrySet().iterator();
			while (lulz.hasNext()) {
				Entry l = (Entry) lulz.next();
				String ip = (String) l.getKey();
				if (ip.equalsIgnoreCase(IP)) { // uh oh. IPs matched!
					botclass.iplist.get(IP).addusername(ev.getName());
					if (botclass.iplist.get(IP).overmax()) {
						// lets kick its alts.
						Player[] players = botclass.getServer()
								.getOnlinePlayers();
						for (Player pl : players) {
							if (botclass.iplist.get(IP).usernames.contains(pl
									.getName())) {
								pl.kickPlayer(connectMsg);
								autokick.add(pl.getName());
							}
						}
						
						botclass.iplist.remove(IP);
					}
				}
			}
			//still here? add us.
			botclass.iplist.put(IP, new IPMap(IP, ev.getName()));
		} else {
			botclass.iplist.put(IP, new IPMap(IP, ev.getName()));
		}
	}

	@EventHandler
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

		// bug workaround
		if (interval < 1) {
			// lets try setting this back to default intervals, if not, reload
			// the configuration.
			interval = botclass.defaultinterval;
			if (botclass.defaultinterval < 1) {
				// have to fix.
				botclass.loadSekritTools();
			}
		}

		long math = time - lasttime;
		int cb = interval + getRandomInt();
		debug("Checking....0");
		debug("Math: " + math);
		debug("Time: " + time);
		debug("Current Interval: " + interval);
		debug("Random Interval: " + cb);
		debug("Lasttime: " + lasttime);
		debug("BotCts: " + botcts + " Accs: " + accounts);

		if (botcts > accounts && math < cb) {
			debug("Hit #1!");
			// Incoming invasion.
			if (!reanibo) {
				if(whiteList) {
				if (notify && whiteList) {
					botclass.getServer()
							.broadcastMessage(
									"\247f[\247bAntiBot\247f] \247cOh no! A minecraft bot invasion has began. Connection Throttling: \247aEnabled");
				}
				reanibo = true;
				} else {
					if(notify) {
						botclass.getServer()
						.broadcastMessage(
								"\247f[\247bAntiBot\247f] \247chas detected minecraft spam!");
					}
				}
				debug("Tripswitched!");
				kickConnected();
				flush();
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
				flush();
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
