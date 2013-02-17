package com.superspytx.ab.testing;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdatesTester {
	
	public static void main(String[] args) {
		try {
			System.out.println("Testing updates!");
			String uacache = System.getProperty("http.agent");
			System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:16.0) Gecko/20100101 Firefox/16.0");
			String check = check("http://ci.h31ix.net/job/AntiBot/GitHubPollLog/?");
			check = findLatestBuild(check);
			System.out.println("FINAL: " + check);
			System.setProperty("http.agent", uacache);
		} catch (Exception e) {
			
		}
	}
	
	public static String check(String URL) throws MalformedURLException, IOException {
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
	
	public static String findLatestBuild(String e) {
		Pattern pattern = Pattern.compile("\\[poll\\] Last Build : #(.*)");
		Matcher matcher = pattern.matcher(e);
		while (matcher.find()) {
			String match = matcher.group();
			if (match.contains("Last Build")) {
				match = match.replace("[poll] Last Build : #", "");
				match = Integer.toString(Integer.parseInt(match) + 1);
				System.out.println(match);
				System.out.println(Integer.toString(70));
				return match;
			}
		}
		return "70";
	}
}
