package com.superspytx.ab.testing;

import com.superspytx.ab.tils.CaptchaTils;

public class CAPTCHATesting {
	
	public static void main(String[] args) {
		for (String l : CaptchaTils.generatePuzzleV2()) {
			System.out.println(l);
		}
	}
	
}
