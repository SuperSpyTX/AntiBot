package me.freebuild.superspytx.ab.handlers;

import me.freebuild.superspytx.ab.AntiBot;
import me.freebuild.superspytx.ab.datatrack.DataTrackCore;
import me.freebuild.superspytx.handlers.ab.chat.CaptchaHandler;
import me.freebuild.superspytx.handlers.ab.chat.ChatFlowHandler;
import me.freebuild.superspytx.handlers.ab.chat.ChatSpamHandler;
import me.freebuild.superspytx.handlers.ab.chat.CommandHandler;
import me.freebuild.superspytx.handlers.ab.login.BotHandler;
import me.freebuild.superspytx.handlers.ab.login.CountryBanHandler;
import me.freebuild.superspytx.handlers.ab.login.LoginDelayHandler;

public class HandlerCore
{

    protected AntiBot antibot = null;
    protected DataTrackCore datatrack = null;

    // handlers
    private CommandHandler commandhandler = null;
    private BotHandler bothandler = null;
    private ChatSpamHandler chatspamhandler = null;
    private CountryBanHandler countrybanhandler = null;
    private ChatFlowHandler chatflowhandler = null;
    private CaptchaHandler captchahandler = null;
    private LoginDelayHandler logindelayhandler = null;

    public HandlerCore(AntiBot instance, DataTrackCore instance2)
    {
        antibot = instance;
        commandhandler = new CommandHandler(instance);
        bothandler = new BotHandler(instance, instance2);
        chatspamhandler = new ChatSpamHandler(instance);
        countrybanhandler = new CountryBanHandler(instance);
        chatflowhandler = new ChatFlowHandler(instance);
        captchahandler = new CaptchaHandler(instance);
        logindelayhandler = new LoginDelayHandler(instance);
    }

    public CommandHandler getCommands()
    {
        return commandhandler;
    }

    public BotHandler getBotHandler()
    {
        return bothandler;
    }

    public ChatSpamHandler getChatSpamHandler()
    {
        return chatspamhandler;
    }

    public CountryBanHandler getCountryBanHandler()
    {
        return countrybanhandler;
    }

    public ChatFlowHandler getChatFlowHandler()
    {
        return chatflowhandler;
    }

    public CaptchaHandler getCaptchaHandler()
    {
        return captchahandler;
    }
    
    public LoginDelayHandler getLoginDelayHandler()
    {
        return logindelayhandler;
    }

}
