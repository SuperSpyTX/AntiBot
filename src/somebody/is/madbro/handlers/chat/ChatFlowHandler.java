package somebody.is.madbro.handlers.chat;

import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerChatEvent;

import somebody.is.madbro.AntiBotCore;
import somebody.is.madbro.datatrack.ChatDataTrack;
import somebody.is.madbro.settings.Permissions;
import somebody.is.madbro.settings.Settings;

public class ChatFlowHandler {

	public AntiBotCore antibot = null;
	public ChatDataTrack chatdata = null;

	public ChatFlowHandler(AntiBotCore instance) {
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
			if (math < chatdata.settimetooverflow) {
				if (chatdata.chatflowscurrent > chatdata.setoverflows) {
					// lockdown chatting.
					chatdata.chatLockedDown = true;
					if (Settings.notify) {
						antibot.getServer()
								.broadcastMessage(
										Settings.prefix
												+ ChatColor.DARK_AQUA
												+ chatdata.overflowedmessage.replace(
														"%sec%",
														Long.toString(chatdata.chatmutedlength)));
					}

					Long timetomutefor = chatdata.chatmutedlength * 20L;
					chatdata.chatmutedlength += 5L; // increase by 5 each time.
					antibot.getServer().getScheduler()
							.scheduleSyncDelayedTask(antibot, new Runnable() {

								public void run() {
									chatdata.chatLockedDown = false;
									chatdata.chatflowscurrent = 0;
									antibot.getServer().broadcastMessage(
											Settings.prefix + ChatColor.GREEN
													+ "Chat has been unmuted!");

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
