package me.freebuild.superspytx.ab.handlers;

import org.bukkit.entity.Player;

import me.freebuild.superspytx.ab.abs.Handler;
import me.freebuild.superspytx.ab.callunits.*;
import me.freebuild.superspytx.ab.handlers.chat.*;
import me.freebuild.superspytx.ab.handlers.login.*;
import me.freebuild.superspytx.ab.settings.Permissions;
import me.freebuild.superspytx.ab.workflow.GD;

public enum Handlers
{
    /* NOTE: Do not use formatter! */
    BOT(new BotHandler(), new LoginUnit(), Permissions.JOIN),
    COMMAND(new CommandHandler(), null, null),
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
    	if (this.name().equals(Handlers.COMMAND.name())) return true;
    	try {
	    	if (GD.getPI(pl).isExempt) return false;
	    	if (GD.getPI(pl).exemptedHandlers.contains(this)) return false;
	    	if (GD.deactivatedHandlers.contains(this)) return false;
	    	if (perm == null) return true;
    	} catch (Throwable t) {}
        return !(perm.getPermission(pl));
    }
}
