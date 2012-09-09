package me.freebuild.superspytx.ab.handlers;

import me.freebuild.superspytx.ab.abs.Handler;
import me.freebuild.superspytx.ab.callunits.*;
import me.freebuild.superspytx.ab.handlers.chat.*;
import me.freebuild.superspytx.ab.handlers.login.*;

public enum Handlers
{
    BOT(new BotHandler(), new LoginUnit()),
    CHATSPAM(new ChatSpamHandler(), new ChatUnit()),
    CHATFLOW(new ChatFlowHandler(), null);

    private Handler handlerinstance;
    private CallUnit unit;

    Handlers(Handler handler, CallUnit unity)
    {
        this.handlerinstance = handler;
        this.unit = unity;
    }

    public Handler getHandler()
    {
        return handlerinstance;
    }
    
    public CallUnit getUnit()
    {
        return unit;
    }
}
