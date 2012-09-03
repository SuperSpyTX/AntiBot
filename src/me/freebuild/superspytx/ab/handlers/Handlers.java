package me.freebuild.superspytx.ab.handlers;

import me.freebuild.superspytx.ab.handlers.chat.ChatSpamHandler;
import me.freebuild.superspytx.ab.handlers.login.BotHandler;

public enum Handlers
{
    BOT(new BotHandler()),
    CHAT(new ChatSpamHandler());
    
    private Handler handlerinstance;
    Handlers (Handler handler) {
        this.handlerinstance = handler;
    }
    
    public Handler getHandler()
    {
        return handlerinstance;
    }
}
