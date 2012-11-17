package me.freebuild.superspytx.ab.settings;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public enum Permissions
{

    //checks?
    NOTIFY("notify", 1),
    JOIN("join", 1),
    VOICE("voice", 1),
    CHATSPAM("chatspam", 1),
    CAPTCHA("captcha", 1),
    LOGINDELAY("logindelay", 1),
    COUNTRYBAN("countryban", 1),

    //admin level permissions
    ADMIN_BASIC("admin.basic", 2),
    ADMIN("admin", 3),

    //individual permissions.
    ADMIN_RELOAD("admin.reload", 3),
    ADMIN_INFO("admin.info", 2),
    ADMIN_NOTIFY("admin.notify", 2),
    ADMIN_CHATMUTE("admin.chatmute", 2),
    ADMIN_TOGGLE("admin.toggle", 3),
    ADMIN_DEBUG("admin.debug", 3);

    String perm = "";
    String baseperm = "";
    int level = 0;

    Permissions(String permission, int levelsofpenis)
    {
        perm = "antibot." + permission;
        baseperm = permission;
        level = levelsofpenis;
    }

    public boolean isLevelPermission()
    {
        return baseperm == ADMIN.baseperm || baseperm == ADMIN_BASIC.baseperm;
    }

    public boolean getPermission(Player pl, CommandSender sender)
    {
        if (!this.getPermission(pl))
        {
            sender.sendMessage(Settings.prefix + "\247cSorry, you don't have privileges.");
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean getPermission(Player pl)
    {
        if (pl != null)
        {

            if (pl.hasPermission(perm))
            {
                return true;
            }

            if (pl.isOp() && Settings.useOpPerms)
            {
                return true;
            }
            
            //BUG: May not work with equals().
            if (pl.isWhitelisted() && Settings.useWhiteListPerms && this.equals(JOIN))
            {
                return true;
            }

            if (isLevelPermission())
            {
                return false;
            }

            if (ADMIN.getPermission(pl) && level >= 3)
            {
                return true;
            }

            if (ADMIN_BASIC.getPermission(pl) && level < 3)
            {
                return true;
            }

            return false;
        }
        else
        {
            return true; // console?
        }

    }

}
