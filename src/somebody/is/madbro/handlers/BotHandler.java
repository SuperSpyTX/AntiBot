package somebody.is.madbro.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerPreLoginEvent.Result;

import somebody.is.madbro.AntiBotCore;
import somebody.is.madbro.settings.Settings;

public class BotHandler extends HandlerCore {

	private AntiBotCore botclass = null;
	public long time;
	public long lasttime;
	public long botattempt;
	public ArrayList<String> autokick = new ArrayList<String>();
	public ArrayList<String> autoipkick = new ArrayList<String>();
	public ArrayList<String> spammyPlayers = new ArrayList<String>();
	public ArrayList<String> connected = new ArrayList<String>();
	public HashMap<String, PlayerHandler> trackplayers = new HashMap<String, PlayerHandler>();
	public int spamcts = 0;
	public int botcts;
	public boolean reanibo = false;

	public BotHandler(AntiBotCore instance) {
		super(instance);
		//transfer instance for compatibility
		botclass = instance;
		// TODO Auto-generated constructor stub
	}

	public void addConnected(String playerName) {
		try {
			if (!connected.contains(playerName)) {
				connected.add(playerName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			botclass.getUtility().getDebugUtility()
					.debug("Adding new player failed: " + e.getMessage());
		}
	}

	public boolean checkConnection(String usr) {
		if (trackplayers.containsKey(usr)) {
			PlayerHandler mp = (PlayerHandler) trackplayers.get(usr);
			if (mp.connectedForLonger()) {
				return true;
			}
		}
		return false;
	}

	public void kickConnected() {
		// int kicked = 0;
		botclass.getUtility().getDebugUtility()
				.debug("Kicking players with method #1 Size: "
						+ connected.size());
		for (String pl : connected) {
			try {
				botclass.getUtility().getDebugUtility().debug("Kicking player..."
						+ pl);
				Player p2 = botclass.getServer().getPlayerExact(pl);
				if (!checkConnection(pl)) {
					botclass.getServer().getPlayerExact(pl)
							.kickPlayer(Settings.kickMsg);
					if (Settings.banUsers) {
						autoipkick
								.add(p2.getAddress().toString().split(":")[0]);
						autokick.add(pl);
					}
					// kicked += 1;
					botclass.getUtility().getDebugUtility()
							.debug("Kicked player with method #1");
					botclass.getUtility().getDebugUtility()
							.debug("We now have autokick: " + autokick.size()
									+ " ip: " + autoipkick.size());
				} else {
					connected.remove(pl);
				}
			} catch (Exception e) {
				// if it fails. go down here.
				botclass.getUtility().getDebugUtility().debug("Failed to kick: "
						+ pl);
			}
		}
		connected.clear();

		// kick players if the above method doesn't work :|
		/*
		 * botclass.getUtility().getDebugUtility().debug("Checking if " + kicked +
		 * " is less than 1"); if (kicked < 1) {
		 * botclass.getUtility().getDebugUtility()
		 * .debug("Kicking player with method #2"); Player[] players =
		 * botclass.getServer().getOnlinePlayers(); for (Player pl : players) {
		 * if (!hasPerms(pl)) { pl.kickPlayer(Settings.connectMsg);
		 * autokick.add(pl);
		 * botclass.getUtility().getDebugUtility().debug("Kicked player with method #2"
		 * ); } } }
		 */

	}

	public boolean flush() {
		try {
			if (reanibo) {
				botclass.getUtility().getDebugUtility().debug("Disabled Reanibios.");
				reanibo = false;
				Settings.interval = botclass.getDefaultinterval();
				connected.clear();
				Settings.accounts = botclass.getDefaultaccounts();
				lasttime = 0;
				botattempt = 0;
				if (Settings.notify && Settings.whiteList) {
					botclass.getServer()
							.broadcastMessage(
									Settings.prefix
											+ "\247aThe minecraft bot invasion has ended. Connection Throttling: \247cDisabled");
				}
			}
			botcts = 0;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean flush2() {
		try {
			reanibo = false;
			Settings.interval = botclass.getDefaultinterval();
			connected.clear();
			autokick.clear();
			autoipkick.clear();
			Settings.accounts = botclass.getDefaultaccounts();
			lasttime = 0;
			botattempt = 0;
			if (Settings.notify && Settings.whiteList) {
				botclass.getServer()
						.broadcastMessage(
								Settings.prefix
										+ "\247aThe minecraft bot invasion has ended. Connection Throttling: \247cDisabled");
			}
			botcts = 0;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean toggle(Boolean e) {
		try {
			Settings.enabled = e;
			return true;
		} catch (Exception fe) {
			return false;
		}
	}

	public int getRandomInt() {
		if (!reanibo) {
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

	public void trackPlayer(Player ev, String IP) {
		if (!botclass.iplist.isEmpty()) {
			// check ipmap list.
			boolean kicked = false;
			try {
				if (botclass.iplist.containsKey(IP)) {
					if (!botclass.iplist.get(IP).contains(ev.getName())) {
						ev.kickPlayer(Settings.kickMsg);
						kicked = true;
						if (Settings.banUsers) {
							autokick.add(ev.getName());
							autoipkick.add(IP);
						}
						botclass.iplist.remove(IP);
					}
				}
			} catch (Exception e) {
				// didn't find player.
			}

			// still here? add us.
			if (!kicked)
				botclass.iplist.put(IP, ev.getName());
		} else {
			botclass.iplist.put(IP, ev.getName());
		}
	}

	
	public void onPlayerKick(PlayerKickEvent event) {
		if (!Settings.enabled) {
			return;
		}

		// spammy chat kick.
		if (Settings.silentChatKick && !Settings.chatMute) {
			if (event.getReason().contains("C: ")) {
				event.setReason(event.getReason().replace("C: ", ""));
				event.setLeaveMessage(null);
				return;
			}
		}

		if (autokick.contains(event.getPlayer().getName())) {
			event.setLeaveMessage(null);
			return;
		}

		if (autoipkick.contains(event.getPlayer().getAddress().toString()
				.split(":")[0])) {
			event.setLeaveMessage(null);
			return;
		}

	}

	
	public void onPlayerPreLogin(PlayerPreLoginEvent event) {
		if (autoipkick.contains(event.getAddress().toString().split(":")[0])) {
			event.disallow(Result.KICK_BANNED, Settings.kickMsg);
		}
	}

	// falsified antibot trigger bug fix, or brolos bug fix.
	
	public void onPlayerQuit(PlayerQuitEvent event) {
		if (getPermissions().hasPerms(event.getPlayer())) {
			return;
		} else {
			botcts -= 1;
		}
	}

	
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (!Settings.enabled) {
			return;
		}
		try {
			botclass.getUtility().getDebugUtility()
					.debug("User is trying to connect..");
			time = System.currentTimeMillis();

			if (getPermissions().hasPerms(event.getPlayer())) {
				botclass.getUtility().getDebugUtility().debug("Whitelisted.");
				if (reanibo
						&& botclass
								.getHandler()
								.getPermissions()
								.ownPermission("AntiBot.Settings.notify",
										event.getPlayer(), 1)) {
					event.getPlayer().sendMessage(
							Settings.prefix + "\247c"
									+ Settings.connectInvasion);
				}
				if (reanibo
						&& botclass
								.getHandler()
								.getPermissions()
								.ownPermission("AntiBot.admin.Settings.notify",
										event.getPlayer(), 2)
						&& Settings.interval > 100000) {
					event.getPlayer()
							.sendMessage(
									Settings.prefix
											+ "\247cThe system needs a flush. Please type /antibot flush. Thanks.");
				}
				return;
			}

			if (autokick.contains(event.getPlayer().getName())) {
				event.getPlayer().kickPlayer(Settings.kickMsg);
				event.setJoinMessage("");
				return;
			}

			if (!reanibo) {
				// IP tracking usernames system.
				trackPlayer(event.getPlayer(), event.getPlayer().getAddress()
						.toString().split(":")[0]);
				botclass.getUtility().getDebugUtility()
						.debug("Added user to tracking");
				addConnected(event.getPlayer().getName());
				botclass.getUtility().getDebugUtility()
						.debug("Added user to connected");
				trackplayers.put(
						event.getPlayer().getName(),
						botclass.getHandler().getPlayer(
								event.getPlayer().getName(), this));
				botclass.getUtility().getDebugUtility()
						.debug("Added user to trackplayer");
			}

			if (botcts > Settings.accounts + 2 && reanibo) { // Increase
																// violation
				// levels.
				Settings.accounts = Settings.accounts + 2;
				Settings.interval = Settings.interval + 5000;
			}

			// bug workaround
			if (Settings.interval < 1) {
				botclass.getUtility().getDebugUtility()
						.debug("Bug detected! Fixing bug.");
				// lets try setting this back to default Settings.intervals, if
				// not,
				// reload
				// the configuration.
				Settings.interval = botclass.getDefaultinterval();
				if (botclass.getDefaultinterval() < 1) {
					// have to fix.
					botclass.getSettings().loadSettings(
							botclass.getDataFolder());
				}
			}

			long math = time - lasttime;
			int cb = Settings.interval + getRandomInt();
			botclass.getUtility().getDebugUtility().debug("Checking....0");
			botclass.getUtility().getDebugUtility().debug("Math: " + math);
			botclass.getUtility().getDebugUtility().debug("Time: " + time);
			botclass.getUtility().getDebugUtility().debug("Current Interval: "
					+ Settings.interval);
			botclass.getUtility().getDebugUtility().debug("Random Interval: " + cb);
			botclass.getUtility().getDebugUtility().debug("Lasttime: " + lasttime);
			botclass.getUtility().getDebugUtility().debug("BotCts: " + botcts
					+ " Accs: " + Settings.accounts);

			if (botcts > Settings.accounts && math < cb) {
				botclass.getUtility().getDebugUtility().debug("Hit #1!");
				// Incoming invasion.
				if (!reanibo) {
					if (Settings.whiteList) {
						if (Settings.notify && Settings.whiteList) {
							botclass.getServer()
									.broadcastMessage(
											Settings.prefix
													+ "\247cOh no! A minecraft bot invasion has began. Connection Throttling: \247aEnabled");
						}
						reanibo = true;
					} else {
						if (Settings.notify) {
							botclass.getServer()
									.broadcastMessage(
											Settings.prefix
													+ "\247chas detected minecraft spam!");
						}
					}
					botclass.getUtility().getDebugUtility().debug("Tripswitched!");
					kickConnected();
					flush();
				}
				botattempt = System.currentTimeMillis();
				botcts += 1;
				event.getPlayer().kickPlayer(Settings.connectMsg);
				event.setJoinMessage("");
			} else if (botattempt < Settings.interval && reanibo) {
				botclass.getUtility().getDebugUtility().debug("Hit #2");
				// Attempting to connect.
				botattempt = System.currentTimeMillis();
				botcts += 1;
				event.getPlayer().kickPlayer(Settings.connectMsg);
				event.setJoinMessage("");
			} else {
				botclass.getUtility().getDebugUtility().debug("Hit #3");

				if (reanibo) {
					flush();
				}
				// No invasion.
				lasttime = System.currentTimeMillis();
				botcts += 1;
			}

			if (!botclass.getServer()
					.getOfflinePlayer(event.getPlayer().getName()).isOnline()) {
				event.setJoinMessage("");
			}

		} catch (Exception e) {
			botclass.getServer()
					.broadcastMessage(
							Settings.prefix
									+ "\247cAn error had occured! Please check console.");
			e.printStackTrace();
		}
	}

}
