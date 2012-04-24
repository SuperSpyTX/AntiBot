package somebody.is.madbro.datatrack;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import somebody.is.madbro.AntiBotCore;

public class CountryDataTrack {
	
	public AntiBotCore botclass = null;
	
	public CountryDataTrack(AntiBotCore instance) {
		botclass = instance;
	}
	
	public List<String> countryBans = new CopyOnWriteArrayList< String >();

}
