package somebody.is.madbro.handlers;

import somebody.is.madbro.AntiBotCore;
import somebody.is.madbro.datatrack.DataTrackCore;

public class HandlerCore {

	protected AntiBotCore antibot = null;
	protected DataTrackCore datatrack = null;

	// handlers
	private CommandHandler commandhandler = null;
	private PermissionsHandler permissionhandler = null;
	private BotHandler bothandler = null;
	private ChatSpamHandler chatspamhandler = null;

	public HandlerCore(AntiBotCore instance, DataTrackCore instance2) {
		antibot = instance;
		commandhandler = new CommandHandler(instance);
		permissionhandler = new PermissionsHandler(instance);
		bothandler = new BotHandler(instance, instance2);
		chatspamhandler = new ChatSpamHandler(instance);
	}

	public PermissionsHandler getPermissions() {
		return permissionhandler;
	}

	public CommandHandler getCommands() {
		return commandhandler;
	}

	public BotHandler getBotHandler() {
		return bothandler;
	}

	public ChatSpamHandler getChatSpamHandler() {
		return chatspamhandler;
	}

}
