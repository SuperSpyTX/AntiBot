package somebody.is.madbro;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Updates implements Runnable {

	public String version = "";
	public boolean newVersion = false;

	public AntiBot antibot = null;

	public Updates(AntiBot instance) {
		antibot = instance;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			String check = check("http://pastebin.com/jdC0Tzzs").split(
					"SERVERREPORT6356574309780958632018")[1]; // roflroflroflrofl @pastebin link
			if (!antibot.getVersion().equals(check)) {
				newVersion = true;
				version = check;
				System.out
						.println("YAY! A new update is currently available for AntiBot!");
				System.out.println("New version: " + version
						+ " Your version: " + antibot.getVersion());
				System.out
						.println("Check at http://dev.bukkit.org/server-mods/antibot/");
			}
		} catch (Exception e) {
			System.out.println("Failed to check for a update!");
		}
	}

	public static String check(String URL) throws MalformedURLException,
			IOException {
		URL url = new URL(URL);
		BufferedInputStream bin = new BufferedInputStream(url.openStream());
		String data = "";
		int tdata;
		while (true) {
			tdata = bin.read();
			if (tdata == -1) {
				return data;
			} else {
				data += (char) tdata;
			}
		}
	}

}
