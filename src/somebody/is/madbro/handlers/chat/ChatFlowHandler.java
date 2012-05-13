package somebody.is.madbro.handlers.chat;

import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerChatEvent;

import somebody.is.madbro.AntiBot;
import somebody.is.madbro.datatrack.ChatDataTrack;
import somebody.is.madbro.settings.Permissions;
import somebody.is.madbro.settings.Settings;

public class ChatFlowHandler {

	public AntiBot antibot = null;
	public ChatDataTrack chatdata = null;

	public ChatFlowHandler(AntiBot instance) {
		antibot = instance;
		chatdata = instance.getDataTrack().getChatTracker();
		// TODO Auto-generated constructor stub
	}

	public void handle(PlayerChatEvent event) {
		try {
			if (!Settings.enabled) {
				return;
			}

			if (Permissions.VOICE.getPermission(event.getPlayer())) {
				return;
			}

			if (chatdata.chatLockedDown) {
				event.setCancelled(true);
				return;
			}

			if (chatdata.lasttime < 1L) {
				chatdata.lasttime = System.currentTimeMillis();
				return;
			}

			Long math = System.currentTimeMillis() - chatdata.lasttime;
			if (math < Settings.overflows) {
				if (chatdata.chatflowscurrent > Settings.overflows) {
					// lockdown chatting.
					chatdata.chatLockedDown = true;
					if (Settings.notify) {
						antibot.getServer()
								.broadcastMessage(
										Settings.prefix
												+ ChatColor.DARK_AQUA
												+ Settings.overflowedmessage.replace(
														"%sec%",
														Long.toString(chatdata.chatmutedlength)));
					}

					Long timetomutefor = chatdata.chatmutedlength * 20L;
					chatdata.chatmutedlength += 5L; // increase by 5 each time.
					antibot.getServer().getScheduler()
							.scheduleSyncDelayedTask(antibot, new Runnable() {

								public void run() {
									if (chatdata.chatLockedDown) {
										chatdata.chatLockedDown = false;
										chatdata.chatflowscurrent = 0;
										antibot.getServer()
												.broadcastMessage(
														Settings.prefix
																+ ChatColor.GREEN
																+ "Chat has been unmuted!");
									}
								}
							}, timetomutefor);
					return;
				}
				chatdata.chatflowscurrent += 1;
				return;
			}

			chatdata.lasttime = System.currentTimeMillis();
			chatdata.chatflowscurrent = 0;
		} catch (Exception e) {

		}
	}

}
