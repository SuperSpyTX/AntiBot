package com.superspytx.ab.abs;

import org.bukkit.ChatColor;
import com.superspytx.ab.settings.Settings;
import com.superspytx.ab.tils.CaptchaTils;

public class Puzzle {
	private boolean version2;
	private String answer;
	private String[] puzzle;
	private ChatColor cc;
	private int strikes;
	
	public Puzzle() {
		strikes = Settings.captchaAttempts;
		// old code.
		/*
		Random rdm = new Random();
		switch (rdm.nextInt(1))
		{
		case 0:
		    version2 = true;
		    puzzle = CaptchaTils.generatePuzzleV2();
		    answer = puzzle[5];
		    break;
		case 1:
		    puzzle = new String[1];
		    puzzle[0] = CaptchaTils.generatePuzzle();
		    answer = puzzle[0].split(",")[1];
		    cc = ChatColor.getByChar(puzzle[0].split(",")[2]);
		    puzzle[0] = puzzle[0].split(",")[0];
		    break;
		default:
		    version2 = true;
		    puzzle = CaptchaTils.generatePuzzleV2();
		    answer = puzzle[5];
		    break;
		}*/
		version2 = true;
		puzzle = CaptchaTils.generatePuzzleV2();
		answer = puzzle[5];
	}
	
	public boolean isVersion2() {
		return version2;
	}
	
	public boolean overboard() {
		return strikes == 0;
	}
	
	public int getAttempts() {
		return strikes;
	}
	
	public boolean checkAnswer(String ans) {
		if (!CaptchaTils.compare(ans, answer)) {
			strikes--;
			return false;
		}
		return true;
	}
	
	public String[] getPuzzle() {
		return puzzle;
	}
	
	public ChatColor getColorCode() {
		return cc;
	}
	
	
}
