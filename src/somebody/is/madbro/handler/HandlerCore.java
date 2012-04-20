package somebody.is.madbro.handler;

import somebody.is.madbro.AntiBotCore;

public class HandlerCore {
	
	protected AntiBotCore antibot = null;
	
	//handlers
	private CommandHandler commandhandler = null;
	private PermissionsHandler permissionhandler = null;
	private BotHandler bothandler = null;
	
	public HandlerCore(AntiBotCore instance) {
		antibot = instance;
		commandhandler = new CommandHandler(instance);
		permissionhandler = new PermissionsHandler(instance);
		bothandler = new BotHandler(instance);
	}
	
	public PermissionsHandler getPermissions() {
		return permissionhandler;
	}
	
	public CommandHandler getCommands() {
		return commandhandler;
	}
	
	public PlayerHandler getPlayer(String username, BotHandler thi) {
		return new PlayerHandler(antibot, username);
	}
	
	public BotHandler getBotHandler() {
		return bothandler;
	}
	

}
