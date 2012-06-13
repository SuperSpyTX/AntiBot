package me.freebuild.superspytx.datatrack;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import me.freebuild.superspytx.AntiBot;

public class CountryDataTrack
{

    public AntiBot botclass = null;

    public CountryDataTrack(AntiBot instance)
    {
        botclass = instance;
    }

    public int countryusersblocked = 0;

    public List<String> countryBans = new CopyOnWriteArrayList<String>();

}
