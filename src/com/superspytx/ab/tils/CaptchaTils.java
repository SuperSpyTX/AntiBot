package com.superspytx.ab.tils;

import java.util.Random;
import org.bukkit.entity.Player;
import com.superspytx.ab.AB;
import com.superspytx.ab.abs.PI;
import com.superspytx.ab.abs.Puzzle;
import com.superspytx.ab.settings.Language;
import com.superspytx.ab.settings.Permissions;
import com.superspytx.ab.settings.Settings;
import com.superspytx.ab.workflow.GD;

public class CaptchaTils {
	
	public static String randomNumbers() {
		int[] g = new int[4];
		g[0] = (new Random()).nextInt(9);
		g[1] = (new Random()).nextInt(9);
		g[2] = (new Random()).nextInt(9);
		g[3] = (new Random()).nextInt(9);
		for (int i = 0; i < g.length; i++) {
			int o = g[i];
			for (int c = 0; c < g.length; c++) {
				if (c == i) continue;
				int a = g[c];
				if (o == a) return randomNumbers(); // looks like java's random number generator derped.
			}
		}
		
		String convert = "";
		for (int z : g)
			convert += Integer.toString(z);
		
		return convert;
	}
	
	public static String[] generatePuzzleV2() {
		String str = randomNumbers();
		String[] speceffects = "l,m,n,o,r".split(",");
		String[] minealpha = "0,1,2,3,4,5,6,7,8,9,a,b,c,d,e,f".split(",");
		String spec = '\247' + minealpha[(new Random()).nextInt(minealpha.length)] + '\247' + speceffects[(new Random()).nextInt(speceffects.length)];
		String[] puzzle = new String[6];
		String[] p0 = strFormat(str.toCharArray()[0]);
		String[] p1 = strFormat(str.toCharArray()[1]);
		String[] p2 = strFormat(str.toCharArray()[2]);
		String[] p3 = strFormat(str.toCharArray()[3]);
		for (int i = 0; i < 6; i++) {
			if (i > 4)
				puzzle[i] = str;
			else {
				puzzle[i] = (spec + p0[i] + "   " + p1[i] + "   " + p2[i] + "   " + p3[i]).replace(" ", "_");
			}
		}
		return puzzle;
	}
	
	private static String[] strFormat(char g) {
		if (g == "0".toCharArray()[0]) { return new String[] { "####", "#  #", "#  #", "#  #", "####" }; }
		if (g == "1".toCharArray()[0]) { return new String[] { " # ",  "## ", " # ",  " # ",  "###" }; }
		if (g == "2".toCharArray()[0]) { return new String[] { "####", "   #", "####", "#   ", "####" }; }
		if (g == "3".toCharArray()[0]) { return new String[] { "####", "   #", " ###", "   #", "####" }; }
		if (g == "4".toCharArray()[0]) { return new String[] { "#  #", "#  #", "####", "   #", "   #" }; }
		if (g == "5".toCharArray()[0]) { return new String[] { "####", "#   ", "####", "   #", "####" }; }
		if (g == "6".toCharArray()[0]) { return new String[] { "####", "#   ", "####", "#  #", "####" }; }
		if (g == "7".toCharArray()[0]) { return new String[] { "####", "   #", "   #", "   #", "   #" }; }
		if (g == "8".toCharArray()[0]) { return new String[] { "####", "#  #", "####", "#  #", "####" }; }
		if (g == "9".toCharArray()[0]) { return new String[] { "####", "#  #", "####", "   #", "####" }; }
		
		return null;
		
	}
	
	public static boolean compare(String input, String answer) {
		char[] onlychars = answer.toLowerCase().toCharArray();
		
		input = input.toLowerCase();
		
		if (input.length() > answer.length() || input.length() < answer.length()) { return false; }
		
		for (int i = 0; i < input.length(); i++) {
			char cha = input.charAt(i);
			boolean safe = false;
			for (int o = 0; o < onlychars.length; o++) {
				char n = onlychars[o];
				if (n == cha) safe = true;
			}
			if (!safe) return false;
		}
		
		return true;
	}
	
	public static String formatColorName(String e) {
		e = e.toLowerCase();
		e = e.replace("_", " ");
		return e;
	}
	
	public static void sendCaptchaToPlayer(Player pl) {
		PI p = GD.getPI(pl);
		
		if (!Settings.captchaEnabled) return;
		
		if (p.cp_haspuzzle) {
			// This usually means they spammed again.
			Tils.kickPlayer(p.pl);
			return;
		}
		
		if (Permissions.CAPTCHA.getPermission(pl)) {
			p.cp_solvedpuzzle = true;
			p.resetSpamData();
			return;
		}
		
		if (p.cp_solvedpuzzle) {
			Tils.kickPlayer(p.pl);
			return;
		}
		
		p.cp_haspuzzle = true;
		p.cp_puzzle = new Puzzle();
		GD.cp_caps++;
		
		if (p.cp_puzzle.isVersion2()) {
			pl.sendMessage(Language.prefix + '\247' + "c" + "Please enter the numbers printed below into chat:");
			for (int i = 0; i < 5; i++)
				pl.sendMessage(p.cp_puzzle.getPuzzle()[i]);
		}
	}
	
	public static void sendCaptchaToPlayerAPI(Player pl) {
		PI p = GD.getPI(pl);
		
		if (!Settings.captchaEnabled) return;
		
		if (p.cp_haspuzzle) return;
		
		if (Permissions.CAPTCHA.getPermission(pl)) {
			return;
		}
		
		p.cp_haspuzzle = true;
		p.cp_puzzle = new Puzzle();
		GD.cp_caps++;
		
		if (p.cp_puzzle.isVersion2()) {
			pl.sendMessage(Language.prefix + '\247' + "c" + "Please enter the numbers printed below into chat:");
			for (int i = 0; i < 5; i++)
				pl.sendMessage(p.cp_puzzle.getPuzzle()[i]);
		}
		
		AB.log("API: Sent player(" + pl.getName() + ") a puzzle.");
	}
}
