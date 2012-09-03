package me.freebuild.superspytx.ab.handlers;

import me.freebuild.superspytx.ab.abs.EventAction;

public interface Handler
{
    public boolean run(EventAction info);
    
    public void performActions(EventAction info);
}
