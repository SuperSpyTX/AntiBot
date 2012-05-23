package somebody.is.madbro;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import net.h31ix.anticheat.manage.AnticheatManager;
import net.h31ix.anticheat.manage.CheckType;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import somebody.is.madbro.Metrics.Graph;
import somebody.is.madbro.datatrack.DataTrackCore;
import somebody.is.madbro.handlers.HandlerCore;
import somebody.is.madbro.listeners.BotListener;
import somebody.is.madbro.listeners.ChatListener;
import somebody.is.madbro.listeners.CountryListener;
import somebody.is.madbro.listeners.KickListener;
import somebody.is.madbro.listeners.UpdateListener;
import somebody.is.madbro.settings.Permissions;
import somebody.is.madbro.settings.Settings;
import somebody.is.madbro.settings.SettingsCore;
import somebody.is.madbro.toolbox.UtilityCore;

public class AntiBot extends JavaPlugin {

	// listeners
	private BotListener botlistener = null;
	private ChatListener chatlistener = null;
	private CountryListener countrylistener = null;
	private KickListener kicklistener = null;
	private UpdateListener updatelistener = null;

	// utilities
	private UtilityCore utilitycore = null;

	// handlers
	private HandlerCore handlercore = null;

	// data trackers
	private DataTrackCore datatrackcore = null;

	// updater
	private Updates updates = null;

	// settings
	private SettingsCore settings = null;

	// others
	private File dataFolder;
	private long installdate;
	private Date install;
	private int defaultinterval;
	private int defaultaccounts;
	private String version = null;

	public void onEnable() {

		// Configuration.
		dataFolder = getDataFolder();
		if (!dataFolder.exists()) {
			System.out.print("AntiBot: Missing Folder. Creating..");
			dataFolder.mkdir();
		}

		// listeners
		botlistener = new BotListener(this);
		chatlistener = new ChatListener(this);
		countrylistener = new CountryListener(this);
		kicklistener = new KickListener(this);
		updatelistener = new UpdateListener(this);

		// cores
		settings = new SettingsCore(this);
		datatrackcore = new DataTrackCore(this);
		handlercore = new HandlerCore(this, datatrackcore);
		utilitycore = new UtilityCore(this);

		settings.saveSettings(dataFolder);
		settings.loadSettings(dataFolder);
		try {
			Metrics metrics = new Metrics(this);

			Graph graph = metrics.createGraph("Bot Blocking Data");

			graph.addPlotter(new Metrics.Plotter("Bots Blocked") {

				@Override
				public int getValue() {
					return getDataTrack().getBotTracker().spambotsblocked;
				}

			});

			graph.addPlotter(new Metrics.Plotter("Chat Spam Blocked") {

				@Override
				public int getValue() {
					return getDataTrack().getChatTracker().chatspamblocked;
				}

			});

			graph.addPlotter(new Metrics.Plotter("Country Users Blocked") {

				@Override
				public int getValue() {
					return getDataTrack().getCountryTracker().countryusersblocked;
				}

			});
			
			graph.addPlotter(new Metrics.Plotter("CraftBukkit++ Noobs") {

				@Override
				public int getValue() {
					return Settings.craftBukkitMinusMinus ? 1 : 0;
				}

			});
			metrics.start();

			// report version to ingame.

		} catch (IOException e) {
			System.out.println("Metrics haz failed.");
		}

		// load geoip if our memogram tells us to.
		if (Settings.geoIP) {
			utilitycore.getGeoIP().initialize();
		}

		// delayed start
		if (Settings.delayedStart && Settings.enabled) {
			Settings.enabled = false;
			getServer().getScheduler().scheduleSyncDelayedTask(this,
					new Runnable() {

						public void run() {
							System.out.println("System has been enabled!");
							sendToAllAdminsWithNotify(ChatColor.GREEN
									+ "System has been enabled!");
							Settings.enabled = true;
							Settings.delayedStart = false;
						}
					}, Settings.startdelay * 20L);
			System.out.println("System is now having a delayed start!");
		}
		
		//troll craftbukkit++ noobs.
		try {
			if(getServer().getAnimalSpawnLimit() < 1) {  //detection might throw false positives.
				Settings.craftBukkitMinusMinus = true;
				System.out.println("WHAT? YOU USE CRAFTBUKKIT++? ROFLMFAO.");
			}
		} catch (Exception e) {
			//good boy.  You're not using crappit.
		}

		// and check for updates ^_^
		updates = new Updates(this); // call the class, so no NPEs if we need to
										// check if there is an update.
		if (Settings.checkupdates) {
			getServer().getScheduler().scheduleAsyncRepeatingTask(this,
					new Runnable() {

						public void run() {
							if(Settings.checkupdates) {
								updates.run();
							}
						}
					}, 60L, 12000L);
		}

		// register listeners
		getServer().getPluginManager().registerEvents(botlistener, this);
		getServer().getPluginManager().registerEvents(chatlistener, this);
		getServer().getPluginManager().registerEvents(countrylistener, this);
		getServer().getPluginManager().registerEvents(kicklistener, this);
		getServer().getPluginManager().registerEvents(updatelistener, this);

		// all finished.
		PluginDescriptionFile pdfFile = getDescription();
		version = pdfFile.getVersion();
		System.out.println(pdfFile.getName() + " version " + getVersion()
				+ " is enabled!");
		boolean development = (version.contains("-DEV"));
		if (development) {
			Settings.checkupdates = false;
			System.out
					.println("This is a development version of AntiBot and not a official release.  Please be careful.  Please report bugs as you find them.");
		} else {
			System.out
					.println("If you have any issues with AntiBot.  Or there is a false positive! Don't be afraid to make a ticket!");
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (sender instanceof Player) {
			return getHandler().getCommands().handle(sender, cmd, commandLabel,
					args);
		} else {
			return getHandler().getCommands().handle(sender, cmd, commandLabel,
					args);
		}
	}

	public void sendToAllAdminsWithNotify(String msg) {
		for (Player pl : getServer().getOnlinePlayers()) {
			if (Permissions.ADMIN_NOTIFY.getPermission(pl)) {
				pl.sendMessage(Settings.prefix + msg);
			}
		}
	}

	public void debug(String msg, CommandSender sender) {
		sender.sendMessage(Settings.prefix + msg);
	}

	public void onDisable() {
		System.out.println("Oh noes! Your server's condom slid off!");
	}

	// getters

	public BotListener getBotListener() {
		return botlistener;
	}

	public UtilityCore getUtility() {
		return utilitycore;
	}

	public HandlerCore getHandler() {
		return handlercore;
	}

	public DataTrackCore getDataTrack() {
		return datatrackcore;
	}

	public Updates getUpdates() {
		return updates;
	}

	public SettingsCore getSettings() {
		return settings;
	}

	public long getInstalldate() {
		return installdate;
	}

	public void setInstalldate(long installdate) {
		this.installdate = installdate;
	}

	public int getDefaultaccounts() {
		return defaultaccounts;
	}

	public void setDefaultaccounts(int defaultaccounts) {
		this.defaultaccounts = defaultaccounts;
	}

	public int getDefaultinterval() {
		return defaultinterval;
	}

	public void setDefaultinterval(int defaultinterval) {
		this.defaultinterval = defaultinterval;
	}

	public Date getInstall() {
		return install;
	}

	public void setInstall(Date install) {
		this.install = install;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}