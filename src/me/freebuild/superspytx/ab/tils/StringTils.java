package me.freebuild.superspytx.ab.tils;

public class StringTils {
	
	public static int strDiffCounter(String now, String before) {
		int record = 0;
		char[] n = now.toCharArray();
		char[] b = before.toCharArray();
		boolean g = false;
		int ct = 0;
		while (!g) {
			if (ct >= n.length) {
				record += Math.abs(b.length - n.length) * 2;
				break;
			} else if (ct >= b.length) {
				record += Math.abs(n.length - b.length) * 2;
				break;
			}
			char nn = n[ct];
			char bb = b[ct];
			
			if (nn != bb) record++;
			
			ct++;
		}
		
		// Don't give players a harsh punishment if both messages were only 2 chars or less...
		if (record == 0 && now.length() < 3 && before.length() < 3) record++;
		
		return record;
	}
	
}
