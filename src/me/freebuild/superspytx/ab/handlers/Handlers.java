package me.freebuild.superspytx.ab.handlers;

import org.bukkit.entity.Player;

import me.freebuild.superspytx.ab.abs.Handler;
import me.freebuild.superspytx.ab.callunits.*;
import me.freebuild.superspytx.ab.handlers.chat.*;
import me.freebuild.superspytx.ab.handlers.login.*;
import me.freebuild.superspytx.ab.settings.Permissions;

public enum Handlers
{
    BOT(new BotHandler(), new LoginUnit(), Permissions.JOIN),
    CHATSPAM(new ChatSpamHandler(), new ChatUnit(), Permissions.CHATSPAM),
    CHATFLOW(new ChatFlowHandler(), null, Permissions.VOICE),
    COUNTRYBANS(new CountryHandler(), null, Permissions.COUNTRYBAN),
    LOGINDELAY(new LoginDelayHandler(), null, Permissions.LOGINDELAY),
    CAPTCHA(new CaptchaHandler(), null, Permissions.CAPTCHA);

    private Handler handlerinstance;
    private CallUnit unit;
    private Permissions perm;

    Handlers(Handler handler, CallUnit unity, Permissions perm)
    {
        this.handlerinstance = handler;
        this.unit = unity;
        this.perm = perm;
    }

    public Handler getHandler()
    {
        return handlerinstance;
    }
    
    public CallUnit getUnit()
    {
        return unit;
    }
    
    public boolean checkUser(Player pl)
    {
        return !(perm.getPermission(pl));
    }
}
