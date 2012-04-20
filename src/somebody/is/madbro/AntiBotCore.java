package somebody.is.madbro;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import somebody.is.madbro.handlers.HandlerCore;
import somebody.is.madbro.listeners.BotListener;
import somebody.is.madbro.settings.Settings;
import somebody.is.madbro.settings.SettingsCore;
import somebody.is.madbro.toolbox.UtilityCore;

public class AntiBotCore extends JavaPlugin {

	// listeners
	private BotListener botlistener = null;

	// utilities
	private UtilityCore utilitycore = null;

	// handlers
	private HandlerCore handlercore = null;

	// settings
	private SettingsCore settings = null;
	private File dataFolder;
	private long installdate;
	private Date install;
	private int defaultinterval;
	private int defaultaccounts;
	private String version = "v2.5";
	public HashMap<String, String> iplist = new HashMap<String, String>();

	public void onEnable() {

		// Configuration.
		dataFolder = getDataFolder();
		if (!dataFolder.exists()) {
			System.out.print("AntiBot: Missing Folder. Creating..");
			dataFolder.mkdir();
		}

		settings = new SettingsCore(this);
		settings.saveSettings(dataFolder);
		settings.loadSettings(dataFolder);

		botlistener = new BotListener(this);

		handlercore = new HandlerCore(this);

		utilitycore = new UtilityCore(this);

		// register listeners
		getServer().getPluginManager().registerEvents(botlistener, this);
		PluginDescriptionFile pdfFile = getDescription();
		System.out.println(pdfFile.getName() + " version " + getVersion()
				+ " is enabled!");

	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		return getHandler().getCommands().handle(sender, cmd, commandLabel, args);
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

	public SettingsCore getSettings() {
		return settings;
	}

	public File getFolder() {
		return dataFolder;
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