package me.freebuild.superspytx.ab.handlers.chat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.freebuild.superspytx.ab.AB;
import me.freebuild.superspytx.ab.abs.EventAction;
import me.freebuild.superspytx.ab.abs.Handler;
import me.freebuild.superspytx.ab.settings.Permissions;
import me.freebuild.superspytx.ab.settings.Settings;
import me.freebuild.superspytx.ab.workflow.GD;

public class CommandHandler implements Handler {
	
	@Override
	public boolean run(EventAction info) {
		CommandSender sender = info.sender;
		Command cmd = info.cmd;
		String label = info.label;
		String[] args = info.args;
		
		if(sender == null || cmd == null || label == null || args == null)
			return false;
		
		Player player = null;
        try
        {
            player = (Player) sender;
        }
        catch (Exception e)
        {
            // console!
        }
        if (args.length < 1)
        {
            returnMotd(sender);
            return true;
        }

        if (player != null)
        {
            if (!Permissions.ADMIN.getPermission(player, sender))
            {
                return true;
            }
        }

        if (args[0].compareToIgnoreCase("help") == 0)
        {
            sender.sendMessage(Settings.prefix + "AntiBot Help:");
            sender.sendMessage(Settings.prefix + "");
            sender.sendMessage(Settings.prefix + "/antibot help - Help Menu");
            if (Permissions.ADMIN_RELOAD.getPermission(player))
            {
                sender.sendMessage(Settings.prefix + "/antibot reload - Reload configuration");
            }
            if (Permissions.ADMIN_INFO.getPermission(player))
            {
                sender.sendMessage(Settings.prefix + "/antibot info - Check current status of AB.getInstance().");
            }
            if (Permissions.ADMIN_ATTACK.getPermission(player))
            {
                sender.sendMessage(Settings.prefix + "/antibot run - Turn on invasion mode manually.");
            }
            if (Permissions.ADMIN_FLUSH.getPermission(player))
            {
                sender.sendMessage(Settings.prefix + "/antibot flush - Flush user data in AB.getInstance().");
            }
            if (Permissions.ADMIN_CHATMUTE.getPermission(player))
            {
                sender.sendMessage(Settings.prefix + "/antibot chatmute - Toggle chat flow's global chat mute.");
            }
            if (Permissions.ADMIN_TOGGLE.getPermission(player))
            {
                sender.sendMessage(Settings.prefix + "/antibot off - Turn off AB.getInstance().");
                sender.sendMessage(Settings.prefix + "/antibot on - Turn on AB.getInstance().");
            }
            sender.sendMessage(Settings.prefix + "/antibot version - Check this version of AB.getInstance().");
            return true;
        }
        if (args[0].compareToIgnoreCase("reload") == 0)
        {
            if (Permissions.ADMIN_RELOAD.getPermission(player, sender))
            {
                if (AB.getSettingsCore().loadSettings())
                {
                    sender.sendMessage(Settings.prefix + ChatColor.GREEN + "Reloaded configuration successfully!");
                }
                else
                {
                    sender.sendMessage(Settings.prefix + ChatColor.RED + "Configuration failed to reload.");
                }
            }
            return true;
        }

        if (args[0].compareToIgnoreCase("chatmute") == 0)
        {
            if (Permissions.ADMIN_CHATMUTE.getPermission(player, sender))
            {
                boolean b = !GD.cf_gm;
                try
                {
                    b = (args[0].equalsIgnoreCase("on") ? true : (args[0].equalsIgnoreCase("off") ? false : !GD.cf_gm));
                }
                catch (Exception shutup)
                {
                }

                GD.cf_gm = b;
                if (Settings.notify)
                {
                    if (b)
                        AB.getInstance().getServer().broadcastMessage(Settings.prefix + ChatColor.DARK_AQUA + Settings.overflowedmessage.replace("%sec%", "infinity, and beyond"));
                    if (!b)
                        AB.getInstance().getServer().broadcastMessage(Settings.prefix + ChatColor.GREEN + "Chat has been unmuted by " + sender.getName() + "!");

                    GD.cf_cts = 0;
                }
            }

            return true;
        }
        
        if (args[0].compareToIgnoreCase("on") == 0)
        {
            if (Permissions.ADMIN_TOGGLE.getPermission(player, sender))
            {
                if (Settings.enabled)
                {
                    sender.sendMessage(Settings.prefix + "The system is already enabled!");
                }
                else
                {
                	Settings.enabled = true;
                    sender.sendMessage(Settings.prefix + ChatColor.GREEN + "System has been enabled!");
                }

            }
            return true;
            // Reload here.
        }
        if (args[0].compareToIgnoreCase("off") == 0)
        {
            if (Permissions.ADMIN_TOGGLE.getPermission(player, sender))
            {
                if (!Settings.enabled)
                {
                    sender.sendMessage(Settings.prefix + "The system is already disabled!");
                }
                else
                {
                	Settings.enabled = false;
                    sender.sendMessage(Settings.prefix + ChatColor.RED + "System has been disabled!");
                }
            }
            return true;
            // Reload here.
        }
        if (args[0].compareToIgnoreCase("info") == 0)
        {
            if (Permissions.ADMIN_INFO.getPermission(player, sender))
            {
                sender.sendMessage(Settings.prefix + "AntiBot System Info:");
                sender.sendMessage(Settings.prefix + "");
                if (Settings.interval > 100000)
                {
                    sender.sendMessage(Settings.prefix + "\247cSystem currently needs flushing.");
                    sender.sendMessage(Settings.prefix + "\247cTo remove this message, type /antibot flush");
                    sender.sendMessage(Settings.prefix + "");
                }
                long math = System.currentTimeMillis() - GD.b_lc;
                sender.sendMessage("Secs between last login: " + math);
                sender.sendMessage("Current Intervals: " + Settings.interval);
                sender.sendMessage("Logged in: " + GD.b_cts);
                sender.sendMessage("# of Accounts: " + Settings.accounts);
            }
            return true;
        }

        if (args[0].compareToIgnoreCase("version") == 0)
        {
            returnMotd(sender);
            return true;
        }
        sender.sendMessage(Settings.prefix + ChatColor.RED + "Unknown system command.");
        return false;
	}
	
	@Override
	public void performActions(EventAction info) {
		
	}

    public String returnStatus()
    {
        if (!Settings.enabled)
        {
            if (Settings.delayedStart)
            {
                return ChatColor.RED + "Disabled" + " (" + ChatColor.YELLOW + "Delayed Start" + ChatColor.WHITE + ")";
            }
            else
            {
                return ChatColor.RED + "Disabled";
            }
        }
        else
        {
            return ChatColor.GREEN + "Enabled";
        }
    }

    public void returnMotd(CommandSender sender)
    {
        Date localdate = new Date(Settings.installdate);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        sender.sendMessage(Settings.prefix + "AntiBot " + AB.getVersion() + " - Coded By .SuPaH sPii");
        sender.sendMessage(Settings.prefix + "Inspired by Wolflink289 <3");
        sender.sendMessage(Settings.prefix + "Continued inspiration by Evenprime & Fafaffy <3");
        //return status if player has admin permissions.
        Player player = null;
        try
        {
            player = (Player) sender;
        }
        catch (Exception e)
        {
            // console!
        }
        if (Permissions.ADMIN_NOTIFY.getPermission(player))
        {
            sender.sendMessage(Settings.prefix + "System Status: " + returnStatus());
        }
        Random random = new Random();
        switch (random.nextInt(20))
        {
        case 0:
            sender.sendMessage(Settings.prefix + "System Installed on " + ChatColor.GREEN + sdf.format(localdate));
            break;
        case 1:
            sender.sendMessage(Settings.prefix + "Keeping Pwnage out of game since " + ChatColor.GREEN + sdf.format(localdate));
            break;
        case 2:
            sender.sendMessage(Settings.prefix + "Combatting spam since " + ChatColor.GREEN + sdf.format(localdate));
            break;
        case 3:
            sender.sendMessage(Settings.prefix + "Supporting PWN4G3 Bots since " + ChatColor.GREEN + sdf.format(localdate));
            break;
        case 4:
            sender.sendMessage(Settings.prefix + "Running PWN4G3 Bots to the void since " + ChatColor.GREEN + sdf.format(localdate));
            break;
        case 5:
            sender.sendMessage(Settings.prefix + "Making people mad since " + ChatColor.GREEN + sdf.format(localdate));
            break;
        case 6:
            sender.sendMessage(Settings.prefix + "Trolling spammers since " + ChatColor.GREEN + sdf.format(localdate));
            break;
        case 7:
            sender.sendMessage(Settings.prefix + "Supporting Wolflink289's idea since " + ChatColor.GREEN + sdf.format(localdate));
            break;
        case 8:
            sender.sendMessage(Settings.prefix + "Protecting this server since " + ChatColor.GREEN + sdf.format(localdate));
            break;
        case 9:
            sender.sendMessage(Settings.prefix + "All lights turned green since " + ChatColor.GREEN + sdf.format(localdate));
            break;
        case 10:
            sender.sendMessage(Settings.prefix + "Corrupting ability to spam since " + ChatColor.GREEN + sdf.format(localdate));
            break;
        case 11:
            sender.sendMessage(Settings.prefix + "Minecraft PWN4G3 Dun goof on " + ChatColor.GREEN + sdf.format(localdate));
            break;
        case 12:
            sender.sendMessage(Settings.prefix + "Making .SuPaH sPii proud since " + ChatColor.GREEN + sdf.format(localdate));
            break;
        case 13:
            sender.sendMessage(Settings.prefix + "Injected the Vaccine on " + ChatColor.GREEN + sdf.format(localdate));
            break;
        case 14:
            sender.sendMessage(Settings.prefix + "Giving AIDS to spammers since " + ChatColor.GREEN + sdf.format(localdate));
            break;
        case 15:
            sender.sendMessage(Settings.prefix + "Turning spammer users to WTF faces since " + ChatColor.GREEN + sdf.format(localdate));
            break;
        case 16:
            sender.sendMessage(Settings.prefix + "Chinese secret happened on " + ChatColor.GREEN + sdf.format(localdate));
            break;
        case 17:
            sender.sendMessage(Settings.prefix + "Been in Slim Shady's world since " + ChatColor.GREEN + sdf.format(localdate));
            break;
        case 18:
            sender.sendMessage(Settings.prefix + "Making other communities jelly since " + ChatColor.GREEN + sdf.format(localdate));
            break;
        case 19:
            sender.sendMessage(Settings.prefix + "Didn't have to buy anything since " + ChatColor.GREEN + sdf.format(localdate));
            break;
        case 20:
            sender.sendMessage(Settings.prefix + "Making Reanimation shit his pants since " + ChatColor.GREEN + sdf.format(localdate));
            break;
        default:
            sender.sendMessage(Settings.prefix + "System Installed on " + ChatColor.GREEN + sdf.format(localdate));
            break;
        }
    }
	
}
