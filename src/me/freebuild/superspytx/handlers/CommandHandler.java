package me.freebuild.superspytx.handlers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import me.freebuild.superspytx.AntiBot;
import me.freebuild.superspytx.settings.Permissions;
import me.freebuild.superspytx.settings.Settings;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler
{

    public AntiBot antibot = null;

    public CommandHandler(AntiBot instance)
    {
        antibot = instance;
        // TODO Auto-generated constructor stub
    }

    public boolean handle(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
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
                sender.sendMessage(Settings.prefix + "/antibot info - Check current status of AntiBot.");
            }
            if (Permissions.ADMIN_ATTACK.getPermission(player))
            {
                sender.sendMessage(Settings.prefix + "/antibot run - Turn on invasion mode manually.");
            }
            if (Permissions.ADMIN_FLUSH.getPermission(player))
            {
                sender.sendMessage(Settings.prefix + "/antibot flush - Flush user data in AntiBot.");
            }
            if (Permissions.ADMIN_CHATMUTE.getPermission(player))
            {
                sender.sendMessage(Settings.prefix + "/antibot chatmute - Toggle chat flow's global chat mute.");
            }
            if (Permissions.ADMIN_TOGGLE.getPermission(player))
            {
                sender.sendMessage(Settings.prefix + "/antibot off - Turn off AntiBot.");
                sender.sendMessage(Settings.prefix + "/antibot on - Turn on AntiBot.");
            }
            sender.sendMessage(Settings.prefix + "/antibot version - Check this version of AntiBot.");
            return true;
        }
        if (args[0].compareToIgnoreCase("reload") == 0)
        {
            if (Permissions.ADMIN_RELOAD.getPermission(player, sender))
            {
                if (antibot.getSettings().loadSettings())
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
        if (args[0].compareToIgnoreCase("run") == 0)
        {
            if (Permissions.ADMIN_ATTACK.getPermission(player, sender))
            {
                if (!antibot.getDataTrack().getBotTracker().reanibo && Settings.whiteList)
                {
                    if (Settings.notify)
                    {
                        antibot.getServer().broadcastMessage(Settings.prefix + "\247cOh no! A minecraft bot invasion has began. Connection Throttling: \247aEnabled");
                    }
                    antibot.getDataTrack().getBotTracker().reanibo = true;
                    antibot.getUtility().getDebug().debug("Tripswitched!");
                    antibot.getDataTrack().getBotTracker().kickConnected();
                    antibot.getDataTrack().getBotTracker().botattempt = System.currentTimeMillis();
                    antibot.getDataTrack().getBotTracker().botcts += 1;
                }
                else if (!Settings.whiteList)
                {
                    sender.sendMessage(ChatColor.RED + "You do not have whitelist mode enabled!");
                }
            }
            return true;
        }

        if (args[0].compareToIgnoreCase("chatmute") == 0)
        {
            if (Permissions.ADMIN_CHATMUTE.getPermission(player, sender))
            {
                boolean b = !antibot.getDataTrack().getChatTracker().chatLockedDown;
                try
                {
                    b = (args[0].equalsIgnoreCase("on") ? true : (args[0].equalsIgnoreCase("off") ? false : !antibot.getDataTrack().getChatTracker().chatLockedDown));
                }
                catch (Exception shutup)
                {
                }

                antibot.getDataTrack().getChatTracker().chatLockedDown = b;
                if (Settings.notify)
                {
                    if (b)
                        antibot.getServer().broadcastMessage(Settings.prefix + ChatColor.DARK_AQUA + Settings.overflowedmessage.replace("%sec%", "infinity, and beyond"));
                    if (!b)
                        antibot.getServer().broadcastMessage(Settings.prefix + ChatColor.GREEN + "Chat has been unmuted by " + sender.getName() + "!");

                    antibot.getDataTrack().getChatTracker().chatflowscurrent = 0;
                }
            }

            return true;
        }

        if (args[0].compareToIgnoreCase("flush") == 0)
        {
            if (Permissions.ADMIN_FLUSH.getPermission(player, sender))
            {
                if (antibot.getUtility().getBot().flush2())
                {
                    sender.sendMessage(Settings.prefix + ChatColor.GREEN + "System flushed successfully!");
                }
                else
                {
                    sender.sendMessage(Settings.prefix + ChatColor.RED + "System failed to flush.");
                }
            }
            return true;
            // Reload here.
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
                    if (antibot.getUtility().getBot().toggle(true))
                    {
                        sender.sendMessage(Settings.prefix + ChatColor.GREEN + "System has been enabled!");
                    }
                    else
                    {
                        sender.sendMessage(Settings.prefix + ChatColor.RED + "Error while trying to enable AntiBot.");
                    }
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
                    if (antibot.getUtility().getBot().toggle(false))
                    {
                        sender.sendMessage(Settings.prefix + ChatColor.RED + "System has been disabled!");
                    }
                    else
                    {
                        sender.sendMessage(Settings.prefix + ChatColor.RED + "Error while trying to disable AntiBot.");
                    }
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
                long math = antibot.getDataTrack().getBotTracker().time - antibot.getDataTrack().getBotTracker().lasttime;
                antibot.debug("Secs between last login: " + math, sender);
                antibot.debug("Current Intervals: " + Settings.interval, sender);
                antibot.debug("Logged in: " + antibot.getDataTrack().getBotTracker().botcts, sender);
                antibot.debug("# of Accounts: " + Settings.accounts, sender);
                sender.sendMessage(Settings.prefix + "");
                if (antibot.getDataTrack().getBotTracker().reanibo)
                {
                    antibot.debug("Connection Throttling: " + ChatColor.GREEN + "Enabled", sender);
                }
                else
                {
                    antibot.debug("Connection Throttling: " + ChatColor.RED + "Disabled", sender);
                }
            }
            return true;
        }

        if (args[0].compareToIgnoreCase("version") == 0)
        {
            returnMotd(sender);
            return true;
        }
        sender.sendMessage(Settings.prefix + "Unknown system command.");
        return false;
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
        antibot.setInstall(new Date(antibot.getInstalldate()));
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        sender.sendMessage(Settings.prefix + "AntiBot " + antibot.getVersion() + " - Coded By .SuPaH sPii");
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
            sender.sendMessage(Settings.prefix + "System Installed on " + ChatColor.GREEN + sdf.format(antibot.getInstall()));
            break;
        case 1:
            sender.sendMessage(Settings.prefix + "Keeping Pwnage out of game since " + ChatColor.GREEN + sdf.format(antibot.getInstall()));
            break;
        case 2:
            sender.sendMessage(Settings.prefix + "Combatting spam since " + ChatColor.GREEN + sdf.format(antibot.getInstall()));
            break;
        case 3:
            sender.sendMessage(Settings.prefix + "Supporting PWN4G3 Bots since " + ChatColor.GREEN + sdf.format(antibot.getInstall()));
            break;
        case 4:
            sender.sendMessage(Settings.prefix + "Running PWN4G3 Bots to the void since " + ChatColor.GREEN + sdf.format(antibot.getInstall()));
            break;
        case 5:
            sender.sendMessage(Settings.prefix + "Making people mad since " + ChatColor.GREEN + sdf.format(antibot.getInstall()));
            break;
        case 6:
            sender.sendMessage(Settings.prefix + "Trolling spammers since " + ChatColor.GREEN + sdf.format(antibot.getInstall()));
            break;
        case 7:
            sender.sendMessage(Settings.prefix + "Supporting Wolflink289's idea since " + ChatColor.GREEN + sdf.format(antibot.getInstall()));
            break;
        case 8:
            sender.sendMessage(Settings.prefix + "Protecting this server since " + ChatColor.GREEN + sdf.format(antibot.getInstall()));
            break;
        case 9:
            sender.sendMessage(Settings.prefix + "All lights turned green since " + ChatColor.GREEN + sdf.format(antibot.getInstall()));
            break;
        case 10:
            sender.sendMessage(Settings.prefix + "Corrupting ability to spam since " + ChatColor.GREEN + sdf.format(antibot.getInstall()));
            break;
        case 11:
            sender.sendMessage(Settings.prefix + "Minecraft PWN4G3 Dun goof on " + ChatColor.GREEN + sdf.format(antibot.getInstall()));
            break;
        case 12:
            sender.sendMessage(Settings.prefix + "Making .SuPaH sPii proud since " + ChatColor.GREEN + sdf.format(antibot.getInstall()));
            break;
        case 13:
            sender.sendMessage(Settings.prefix + "Injected the Vaccine on " + ChatColor.GREEN + sdf.format(antibot.getInstall()));
            break;
        case 14:
            sender.sendMessage(Settings.prefix + "Giving AIDS to spammers since " + ChatColor.GREEN + sdf.format(antibot.getInstall()));
            break;
        case 15:
            sender.sendMessage(Settings.prefix + "Turning spammer users to WTF faces since " + ChatColor.GREEN + sdf.format(antibot.getInstall()));
            break;
        case 16:
            sender.sendMessage(Settings.prefix + "Chinese secret happened on " + ChatColor.GREEN + sdf.format(antibot.getInstall()));
            break;
        case 17:
            sender.sendMessage(Settings.prefix + "Been in Slim Shady's world since " + ChatColor.GREEN + sdf.format(antibot.getInstall()));
            break;
        case 18:
            sender.sendMessage(Settings.prefix + "Making other communities jelly since " + ChatColor.GREEN + sdf.format(antibot.getInstall()));
            break;
        case 19:
            sender.sendMessage(Settings.prefix + "Didn't have to buy anything since " + ChatColor.GREEN + sdf.format(antibot.getInstall()));
            break;
        case 20:
            sender.sendMessage(Settings.prefix + "Making Reanimation shit his pants since " + ChatColor.GREEN + sdf.format(antibot.getInstall()));
            break;
        default:
            sender.sendMessage(Settings.prefix + "System Installed on " + ChatColor.GREEN + sdf.format(antibot.getInstall()));
            break;
        }
    }

}
