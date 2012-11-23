package me.freebuild.superspytx.ab.tils;

import me.freebuild.superspytx.ab.AB;
import me.freebuild.superspytx.ab.abs.PI;

public class Tils {
	
	public static Long getLongDiff(Long diff) {
		long currtime = System.currentTimeMillis();
		long dd = currtime - diff;
		if(dd == currtime)
			return 0L;
		return dd;
	}
	
	public static boolean consistency(PI pl, Long time) {
		// The consistency detector. The bot rapist.
		
		// round the difference.
		long diff = getLongDiff(time);
		long round = diff - (diff % 1000); // Thanks overflowed stacks!
		
		
		// begin identifying patterns.
		if (pl.cs_rd != 0L) {
			long max = pl.cs_rd - 800L; // drop .8 seconds from the stored rounded difference.
			AB.debug("Max: " + Long.toString(max) + " Round: " + Long.toString(round) + " Diff: " + Long.toString(diff));
			if (diff <= pl.cs_rd && diff >= max) return true;
		}
		
		pl.cs_rd = round;
		return false;
	}
	
	public static int strDiffCounter(String now, String before) {
		int record = 0;
		char[] n = now.toCharArray();
		char[] b = before.toCharArray();
		int ct = 0;
		while (true) {
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
