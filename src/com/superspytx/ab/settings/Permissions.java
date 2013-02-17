package com.superspytx.ab.settings;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public enum Permissions
{

    //checks?
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
    ADMIN_RELOAD("admin.reload", 2),
    ADMIN_INFO("admin.info", 2),
    ADMIN_NOTIFY("admin.notify", 2),
    ADMIN_CHATMUTE("admin.chatmute", 2),
    ADMIN_FLUSH("admin.flush", 3),
    ADMIN_TOGGLE("admin.toggle", 3),
    ADMIN_DEBUG("admin.debug", 3);

    String perm = "";
    String baseperm = "";
    int level = 0;

    Permissions(String permission, int leveld)
    {
        perm = "antibot." + permission;
        baseperm = permission;
        level = leveld;
    }

    public boolean isLevelPermission()
    {
        return this == ADMIN || this == ADMIN_BASIC;
    }

    public boolean getPermission(Player pl, CommandSender sender)
    {
        if (!this.getPermission(pl))
        {
            sender.sendMessage(Language.prefix + "\247cSorry, you don't have privileges.");
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

            if (isLevelPermission())
            {
                return false;
            }

            if (ADMIN.getPermission(pl) && level <= 3)
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
