package me.freebuild.superspytx.ab;

import java.io.IOException;
import me.freebuild.superspytx.ab.Metrics.Graph;
import me.freebuild.superspytx.ab.abs.CommandEvent;
import me.freebuild.superspytx.ab.callunits.CallUnit;
import me.freebuild.superspytx.ab.handlers.Handlers;
import me.freebuild.superspytx.ab.settings.Permissions;
import me.freebuild.superspytx.ab.settings.Settings;
import me.freebuild.superspytx.ab.settings.SettingsCore;
import me.freebuild.superspytx.ab.tils.GeoTils;
import me.freebuild.superspytx.ab.workflow.GD;
import me.freebuild.superspytx.ab.workflow.WorkflowAgent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiBot extends JavaPlugin {
	private static AntiBot instance;
	private static SettingsCore settingscore;
	private static String version;
	private Updates updates;
	
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
	}
	
	public void onEnable() {
		instance = this;
		
		/* Initialize GeoIP utilities */
		GeoTils.initialize();
		
		/* Events */
		(new CallUnit()).registerUnits();
		
		/* Configuration */
		settingscore = new SettingsCore();
		settingscore.loadDefaults();
		settingscore.loadSettings();
		
		/* Register players on server */
		for (Player pl : this.getServer().getOnlinePlayers()) {
			GD.getPI(pl).ab_alreadyin = true;
		}
		
		/* Let's get our metric sticks and slap Metrics until it loads */
		Metrics metrics = null;
		try {
			metrics = new Metrics(this);
			
			Graph graph = metrics.createGraph("Bot Blocking Data");
			
			graph.addPlotter(new Metrics.Plotter("Bots Blocked") {
				
				@Override
				public int getValue() {
					return GD.b_blks;
				}
				
			});
			
			graph.addPlotter(new Metrics.Plotter("Chat Spam Blocked") {
				
				@Override
				public int getValue() {
					return GD.cs_spams;
				}
				
			});
			
			graph.addPlotter(new Metrics.Plotter("Chat Overflows") {
				
				@Override
				public int getValue() {
					return GD.cf_ovfl;
				}
				
			});
			
			graph.addPlotter(new Metrics.Plotter("Captcha Triggers") {
				
				@Override
				public int getValue() {
					return GD.cp_caps;
				}
				
			});
			
			graph.addPlotter(new Metrics.Plotter("Country Users Blocked") {
				
				@Override
				public int getValue() {
					return GD.cb_invs;
				}
				
			});
			
		} catch (IOException e) {
			AB.log("Metrics haz failed.");
		}
		
		/* Delayed Start */
		if (Settings.delayedStart && Settings.enabled) {
			Settings.enabled = false;
			Settings.delayingStart = true;
			
			getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
				
				public void run() {
					AB.log("System has been enabled!");
					for (Player pl : Bukkit.getOnlinePlayers()) {
						if (Permissions.ADMIN_NOTIFY.getPermission(pl)) pl.sendMessage(Settings.prefix + ChatColor.GREEN + "System has been enabled!");
					}
					Settings.enabled = true;
					Settings.delayedStart = false;
					Settings.delayingStart = false;
				}
			}, Settings.startdelay * 20L);
			AB.log("System is now having a delayed start!");
		}
		
		/* Setup one minute running task */
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
			public void run() {
				GD.updateTask();
			}
		}, 1200L, 1200L);
		
		/* Almost finished */
		PluginDescriptionFile pdfFile = getDescription();
		version = pdfFile.getVersion();
		boolean development = (version.contains("-SNAPSHOT"));
		
		if (development) {
			Settings.checkupdates = false;
			getLogger().info("This is a development version of AntiBot and not a official release.  Please be careful.  Please report bugs as you find them.");
			getLogger().info("Please check back the BukkitDev page for updates!");
		} else {
			if (metrics != null) metrics.start();
			getLogger().info("If you have any issues with AntiBot.  Or there is a false positive! Don't be afraid to make a ticket!");
		}
		
		/* Setup updates task */
		updates = new Updates();
		
		if (Settings.checkupdates) {
			getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
				
				public void run() {
					if (Settings.checkupdates) {
						updates.run();
					}
				}
			}, 140L, 12000L);
		}
		
		/* Now all finished */
		getLogger().info("AntiBot v" + version + " (Notorious UnLeet & deRobert Edition) enabled!");
	}
	
	public static AntiBot getInstance() {
		return instance;
	}
	
	public static String getVersion() {
		return version;
	}
	
	public static SettingsCore getSettingsCore() {
		return settingscore;
	}
	
	public static void debug(String e) {
		if (Settings.debugmode) {
			getInstance().getLogger().info("Debug: " + e);
			// TODO: Remove Debug Code!
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (Permissions.ADMIN_DEBUG.getPermission(pl)) pl.sendMessage("[AntiBot] Debug: " + e);
			}
			// TODO: Permission node based debug logs?
			Bukkit.broadcastMessage(Settings.prefix + "Debug: " + e);
		}
	}
	
	public static void log(String e) {
		getInstance().getLogger().info(e);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().startsWith("ab") || cmd.getName().startsWith("antibot")) { return WorkflowAgent.dispatchUnit(new CommandEvent(sender, cmd, label, args), Handlers.COMMAND, true); }
		
		return false;
	}
	
	
}
