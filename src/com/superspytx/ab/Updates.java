package com.superspytx.ab;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.superspytx.ab.settings.Settings;

public class Updates implements Runnable {
	
	@Override
	public void run() {
		try {
			if (Settings.checkupdates) {
				if (!AB.isDevelopment()) {
					String check = check("https://raw.github.com/SuperSpyTX/AntiBot/master/dl/update.txt").split("SERVERREPORT6356574309780958632018")[1];
					if (!AB.getVersion().equals(check)) {
						Settings.newVersion = true;
						Settings.version = check;
						AB.log("YAY! A new release is currently available for AntiBot!");
						AB.log("New version: " + Settings.version + " Your version: " + AB.getVersion());
						AB.log("Check at http://dev.bukkit.org/server-mods/antibot/");
					}
				} else {
					if (AB.getBuildNumber() == 0) return;
					
					String check = check("https://raw.github.com/SuperSpyTX/AntiBot/master/dl/update.txt").split("SERVERREPORT6356574309780958632018")[1];
					if (check.contains("-")) {
						check = check.split("-")[0];
					}
					
					if (AB.getRealVersion().equals(check)) {
						Settings.newVersion = true;
						Settings.version = check;
						AB.log("YAY! A new release is currently available for AntiBot!");
						AB.log("New version: " + Settings.version + " Your version: " + AB.getVersion());
						AB.log("Check at http://dev.bukkit.org/server-mods/antibot/");
						return;
					}
					
					String check2 = check("http://ci.h31ix.net/job/AntiBot/GitHubPollLog/?");
					check2 = findLatestBuild(check2);
					
					if (AB.getBuildNumber() < Integer.parseInt(check2)) {
						Settings.newVersion = true;
						Settings.version = check2;
						AB.log("YAY! A new development build is currently available for AntiBot!");
						AB.log("New build #" + Settings.version + " Your build #" + AB.getBuildNumber());
						AB.log("Check at http://ci.h31ix.net/job/AntiBot/lastBuild");
					}
				}
			}
		} catch (Exception e) {
			AB.log("Failed to check for a update!");
		}
	}
	
	public static String check(String URL) throws MalformedURLException, IOException {
		URL url = new URL(URL);
		URLConnection urlconn = url.openConnection();
		urlconn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
		urlconn.connect();
		BufferedInputStream bin = new BufferedInputStream(urlconn.getInputStream());
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
	
	public static String findLatestBuild(String e) {
		Pattern pattern = Pattern.compile("\\[poll\\] Last Build : #(.*)");
		Matcher matcher = pattern.matcher(e);
		while (matcher.find()) {
			String match = matcher.group();
			if (match.contains("Last Build")) {
				match = match.replace("[poll] Last Build : #", "");
				match = Integer.toString(Integer.parseInt(match) + 1);
				return match;
			}
		}
		return Integer.toString(AB.getBuildNumber());
	}
	
}
