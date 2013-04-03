package com.superspytx.ab.tils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;
import com.retardmind.geoipingthemuntiltheydie.AndSoStupidThat;
import com.superspytx.ab.AB;
import com.superspytx.ab.settings.Settings;
import com.superspytx.ab.workflow.GD;

public class GeoTils {
	private static File db = new File(AB.getInstance().getDataFolder(), "GeoIP.dat");
	private static AndSoStupidThat lkup = null;
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
				lkup = new AndSoStupidThat(db, AndSoStupidThat.GEOIP_MEMORY_CACHE);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AB.log("GeoIP Loading failed!");
		}
	}
	
	public static boolean detect(String ip) {
		if (lkup == null) return false;
		AB.debug("Lookup Service not null!");
		String cd = lkup.getCountry(ip).getCode();
		AB.debug("Checking country!");
		if (GD.cb_cds.contains(cd)) {
			AB.debug("Country is in list! Determining decision.");
			if (Settings.whiteListCountry)
				return false; // no ban
			else
				return true; // ban hammer
		} else {
			AB.debug("Country is not in list! Determining decision.");
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
			String URL = "https://github.com/SuperSpyTX/AntiBot/blob/master/dl/GeoIP.dat.gz?raw=true"; // Temporary until Geolite stops being gay.
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
			AB.log("Here are the logs you can use to report to the AntiBot Ticket Tracker.");
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static boolean isLoaded() {
		return dlSuccessful;
	}
	
}
