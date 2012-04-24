package somebody.is.madbro.toolbox;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

import com.maxmind.geoip.LookupService;

import somebody.is.madbro.AntiBotCore;
import somebody.is.madbro.settings.Settings;

public class GeoIPUtility {

	public AntiBotCore antibot = null;
	private LookupService lkup = null;

	private File db = null;

	public GeoIPUtility(AntiBotCore instance) {
		antibot = instance;
		db = new File(instance.getDataFolder(), "GeoIP.dat");
	}

	public void initialize() {
		try {
			if (!checkIfDownloaded()) {
				System.out
						.println("Our memogram tells us to install GeoIP. Lets try dis.");
				preDownload();
				System.out
						.println("GeoIP, by Maximind!  Now installed.  Never to be seen again!");
			}
			System.out.println("Loading GeoIP..");
			lkup = new LookupService(db, LookupService.GEOIP_MEMORY_CACHE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Loading failed!");
		}
	}

	// lookup functions

	public boolean determineFateForIP(String ip) {
		String code = lkup.getCountry(ip).getCode();
		if (antibot.getDataTrack().getCountryTracker().countryBans
				.contains(code)) {
			// now determine the two behaviors.
			if (Settings.whiteListCountry) {
				return false; // dont ban.
			} else {
				return true; // yesban
			}
		} else {
			if (Settings.whiteListCountry) {
				return true; // yesban
			}
		}
		return false;
	}

	// loading shit.
	public boolean checkIfDownloaded() {
		if (db.exists()) {
			return true;
		} else {
			return false;
		}
	}

	public void loadCountryBanList(String list) {
		if (list.length() == 0) {
			return;
		}
		String[] liz = list.split(",");
		for (int i = 0; i < liz.length; i++) {
			antibot.getDataTrack().getCountryTracker().countryBans.add(liz[i]);
			System.out.println("Loaded Country " + liz[i]);
		}
		System.out.println("Loaded " + liz.length
				+ " entries to CountryBans list.");
	}

	public void preDownload() {
		try {
			String URL = "http://geolite.maxmind.com/download/geoip/database/GeoLiteCountry/GeoIP.dat.gz";
			URL downloadUrl = new URL(URL);
			URLConnection conn = downloadUrl.openConnection();
			conn.setConnectTimeout(10000);
			conn.connect();
			InputStream input = conn.getInputStream();
			if (URL.endsWith(".gz")) {
				input = new GZIPInputStream(input);
			}
			OutputStream output = new FileOutputStream(db);
			byte[] buffer = new byte[2048];
			int length = input.read(buffer);
			while (length >= 0) {
				output.write(buffer, 0, length);
				length = input.read(buffer);
			}
			output.close();
			input.close();

			System.out.println("Download Complete?");
		} catch (Exception e) {
			System.out
					.println("An error had occured while trying to download DB.");
		}

	}
}
