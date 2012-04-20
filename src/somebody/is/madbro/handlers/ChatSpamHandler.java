package somebody.is.madbro.handlers;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import somebody.is.madbro.AntiBotCore;
import somebody.is.madbro.settings.Settings;

public class ChatSpamHandler extends HandlerCore {

	public ChatSpamHandler(AntiBotCore instance) {
		super(instance);
		// TODO Auto-generated constructor stub
	}
	
	public void handle(Player player, PlayerChatEvent event) {
		try {
			if (!Settings.enabled) {
				return;
			}
			String pN = player.getName();
			if (getBotHandler().autokick.contains(player.getName())) {
				player.kickPlayer(Settings.kickMsg);
				event.setCancelled(true);
				return;
			}

			if (getBotHandler().autoipkick.contains(player.getAddress().toString()
					.split(":")[0])) {
				player.kickPlayer(Settings.kickMsg);
				event.setCancelled(true);
				return;
			}

			if (getBotHandler().spammyPlayers.contains(pN)) {
				event.setCancelled(true);
				return;
			}

			if (getPermissions().hasPerms(player) || !Settings.enableAntiSpam) {
				return;
			}

			if (!getBotHandler().trackplayers.containsKey(pN)) {
				getBotHandler().trackplayers.put(pN, antibot.getHandler().getPlayer(pN, this));
			} else {
				try {
					PlayerHandler pc = getBotHandler().trackplayers.get(pN);
					long math = System.currentTimeMillis() - pc.lastChatMsg;
					if (pc.amoumt > Settings.spamam && math < Settings.spamtime) {
						if (Settings.notify) {
							antibot.getServer().broadcastMessage(
									Settings.prefix
											+ "\247chas detected chat spam!");
						}
						if (!Settings.chatMute) {
							getBotHandler().trackplayers.remove(pN);
							player.kickPlayer(
									"C: " + Settings.kickMsg);
							event.setCancelled(true);
						} else {
							getBotHandler().trackplayers.remove(pN);
							getBotHandler().spammyPlayers.add(pN);
							event.setCancelled(true);
						}
					} else {
						pc.trig();
					}
				} catch (Exception e) {

				}
			}

		} catch (Exception e) {
			// alright, it failed. Don't worry about it.
		}
		
	}

}
