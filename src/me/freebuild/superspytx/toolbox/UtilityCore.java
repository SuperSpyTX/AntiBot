package me.freebuild.superspytx.toolbox;

import me.freebuild.superspytx.AntiBot;
import me.freebuild.superspytx.listeners.BotListener;

public class UtilityCore
{

    //utilities
    private BotUtility botutility = null;
    private DebugUtility debugutility = null;
    private GeoIPUtility geoiputility = null;
    private DeregisterUtility deregisterutility = null;
    private CaptchaUtility captchautility = null;

    //listeners
    protected BotListener botlistener = null;

    protected AntiBot antibot = null;

    public UtilityCore(AntiBot instance)
    {
        antibot = instance;
        botlistener = instance.getBotListener();
        botutility = new BotUtility(instance);
        debugutility = new DebugUtility(instance);
        geoiputility = new GeoIPUtility(instance);
        deregisterutility = new DeregisterUtility(instance);
        captchautility = new CaptchaUtility(instance);
    }

    public BotUtility getBot()
    {
        return botutility;
    }

    public DebugUtility getDebug()
    {
        return debugutility;
    }

    public GeoIPUtility getGeoIP()
    {
        return geoiputility;
    }

    public DeregisterUtility getDeregister()
    {
        return deregisterutility;
    }

    public CaptchaUtility getCaptcha()
    {
        return captchautility;
    }

}
