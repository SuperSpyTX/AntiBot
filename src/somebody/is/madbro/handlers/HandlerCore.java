package somebody.is.madbro.handlers;

import somebody.is.madbro.AntiBotCore;

public class HandlerCore {
	
	protected AntiBotCore antibot = null;
	
	//handlers
	private CommandHandler commandhandler = null;
	private PermissionsHandler permissionhandler = null;
	private BotHandler bothandler = null;
	private ChatSpamHandler chatspamhandler = null;
	
	public HandlerCore(AntiBotCore instance) {
		antibot = instance;
		commandhandler = new CommandHandler(instance);
		permissionhandler = new PermissionsHandler(instance);
		bothandler = new BotHandler(instance);
		chatspamhandler = new ChatSpamHandler(instance);
	}
	
	public PermissionsHandler getPermissions() {
		return permissionhandler;
	}
	
	public CommandHandler getCommands() {
		return commandhandler;
	}
	
	public PlayerHandler getPlayer(String username, ChatSpamHandler thi) {
		return new PlayerHandler(antibot, username);
	}
	
	public PlayerHandler getPlayer(String username, BotHandler thi) {
		return new PlayerHandler(antibot, username);
	}
	
	public BotHandler getBotHandler() {
		return bothandler;
	}
	
	public ChatSpamHandler getChatSpamHandler() {
		return chatspamhandler;
	}
	

}
