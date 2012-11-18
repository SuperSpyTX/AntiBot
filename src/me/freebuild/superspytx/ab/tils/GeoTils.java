package me.freebuild.superspytx.ab.tils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;
import me.freebuild.superspytx.ab.AB;
import me.freebuild.superspytx.ab.settings.Settings;
import me.freebuild.superspytx.ab.workflow.GD;
import com.maxmind.geoip.LookupService;

public class GeoTils {
	private static File db = new File(AB.getInstance().getDataFolder(), "GeoIP.dat");
	private static LookupService lkup = null;
	private static boolean dlSuccessful = false;
	
	public static void initialize() {
		if (Settings.isSet() && dlSuccessful) return;
		try {
			if (!checkIfDownloaded()) {
				AB.log("Downloading GeoIP country data.");
				dlSuccessful = preDownload();
				if (dlSuccessful) {
					AB.log("GeoIP, by Maximind installed.  Never to be seen again!");
				} else {
					AB.log("GeoIP servers derped and failed to load GeoIP.  Country Bans functionality is broken until this is done.");
				}
			} else {
				dlSuccessful = true;
			}
			
			if (dlSuccessful) {
				AB.log("Loading GeoIP..");
				lkup = new LookupService(db, LookupService.GEOIP_MEMORY_CACHE);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AB.log("GeoIP Loading failed!");
		}
	}
	
	public static boolean detect(String ip) {
		if (lkup == null) return false;
		String cd = lkup.getCountry(ip).getCode();
		if (GD.cb_cds.contains(cd)) {
			if (Settings.whiteListCountry)
				return false; // no ban
			else
				return true; // ban hammer
		} else {
			if (Settings.whiteListCountry) return true; // ban hammer
		}
		
		return false; // no ban.
	}
	
	private static boolean checkIfDownloaded() {
		if (db.exists()) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean preDownload() {
		try {
			String URL = "https://raw.github.com/SuperSpyTX/AntiBot/blob/master/dl/GeoIP.dat.gz"; // Temporary until Geolite stops being gay.
			URL downloadUrl = new URL(URL);
			URLConnection conn = downloadUrl.openConnection();
			conn.setConnectTimeout(10000);
			conn.connect();
			InputStream input = conn.getInputStream();
			input = new GZIPInputStream(input);
			OutputStream output = new FileOutputStream(db);
			byte[] buffer = new byte[2048];
			int length = input.read(buffer);
			while (length >= 0) {
				output.write(buffer, 0, length);
				length = input.read(buffer);
			}
			output.close();
			input.close();
			
			AB.debug("Download Complete?");
			return true;
		} catch (Exception e) {
			AB.log("An error had occured while trying to download DB.");
			return false;
		}
		
	}
	
	public static boolean isLoaded() {
		return dlSuccessful;
	}
	
}
