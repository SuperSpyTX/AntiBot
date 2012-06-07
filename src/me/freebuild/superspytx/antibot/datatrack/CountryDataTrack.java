package me.freebuild.superspytx.antibot.datatrack;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import me.freebuild.superspytx.antibot.Core;

public class CountryDataTrack {
	
	public Core botclass = null;
	
	public CountryDataTrack(Core instance) {
		botclass = instance;
	}
	
	public int countryusersblocked = 0;
	
	public List<String> countryBans = new CopyOnWriteArrayList< String >();

}
