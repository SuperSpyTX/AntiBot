package me.freebuild.superspytx.antibot.toolbox;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import me.freebuild.superspytx.antibot.Core;

public class CaptchaUtility {

	public Core antibot = null;

	public CaptchaUtility(Core instance) {
		antibot = instance;
		// TODO Auto-generated constructor stub
	}

	public String generatePuzzle() {
		// define variables.
		String[] alphabetlower = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z"
				.split(",");
		String[] alphabetupper = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z"
				.split(",");
		String[] numbers = "0,1,2,3,4,5,6,7,8,9".split(",");
		String[] minealpha = "0,1,2,3,4,5,6,7,8,9,a,b,c,d,e,f".split(",");
		int maxalphaL = alphabetlower.length;
		int maxalphaU = alphabetupper.length;
		int maxnum = numbers.length;
		int maxmineA = minealpha.length;

		// generate 10 character string.
		Random rdm = new Random();
		String puzzle = "";
		for (int i = 0; i < 11; i++) {
			switch (rdm.nextInt(2)) {
			case 1:
				puzzle += alphabetlower[rdm.nextInt(maxalphaL)];
				break;
			case 2:
				puzzle += alphabetupper[rdm.nextInt(maxalphaU)];
				break;
			case 3:
				puzzle += numbers[rdm.nextInt(maxnum)];
				break;
			default:
				puzzle += alphabetlower[rdm.nextInt(maxalphaL)];
			}
		}

		// generate answer from generated string.
		String answer = "";
		String[] points = new String[4];
		String restrictedcolorcode = minealpha[rdm.nextInt(maxmineA)];

		for (int a = 0; a < 4; a++) {
			points[a] = Integer.toString(rdm.nextInt(puzzle.length() - 1));
		}

		// check for repeats (Randomizer sucks)
		for (int z = 0; z < 4; z++) {
			String point = points[z];

			if (z != 0 && points[0].equalsIgnoreCase(point)) {
				points[z] = Integer.toString(rdm.nextInt(puzzle.length() - 1));
			}

			if (z != 1 && points[1].equalsIgnoreCase(point)) {
				points[z] = Integer.toString(rdm.nextInt(puzzle.length() - 1));
			}

			if (z != 2 && points[2].equalsIgnoreCase(point)) {
				points[z] = Integer.toString(rdm.nextInt(puzzle.length() - 1));
			}

			if (z != 3 && points[3].equalsIgnoreCase(point)) {
				points[z] = Integer.toString(rdm.nextInt(puzzle.length() - 1));
			}

		}

		for (int o = 0; o < 4; o++) {
			answer += puzzle.charAt(Integer.parseInt(points[o]));
		}

		// now format color codes.
		String newpuzzle = "";
		String colors = "";

		for (int g = 0; g < puzzle.length(); g++) {
			char spec = puzzle.charAt(g);
			String colorcode = "\247" + minealpha[rdm.nextInt(maxmineA)];

			if (colorcode.equalsIgnoreCase("\247" + restrictedcolorcode)) {
				colorcode = "\247e";
			}

			for (int z = 0; z < 4; z++) {
				if (Integer.parseInt(points[z]) == g
						&& answer.contains(("" + spec))) { // roflexploit
					colorcode = "\247" + restrictedcolorcode;
				}
			}

			newpuzzle += colorcode + spec;
			colors += colorcode;
		}

		// now check again, if it happens again, we'll just start over.

		char[] lawl = colors.toCharArray();
		char[] victor = restrictedcolorcode.toCharArray();
		Map<String, Integer> quickie = new HashMap<String, Integer>();
		for (int r = 0; r < victor.length; r++) {
			if (!quickie.containsKey("" + victor[r]))
				quickie.put("" + victor[r], 0);
			for (int q = 0; q < lawl.length; q++) {
				char h = lawl[q];
				if (victor[r] == h) {
					quickie.put("" + victor[r], quickie.get("" + victor[r]) + 1);
				}
			}

			if (quickie.get("" + victor[r]) > 4)
				return generatePuzzle(); // start over. just. it's only going to
											// bring you down tonight.
		}

		// attach answer in comma.
		newpuzzle += "," + answer + "," + restrictedcolorcode;

		// return generated string/puzzle.
		return newpuzzle;
	}

	public boolean compare(String input, String answer) {
		char[] onlychars = answer.toLowerCase().toCharArray();

		input = input.toLowerCase();

		if (input.length() > answer.length()
				|| input.length() < answer.length()) {
			return false;
		}

		for (int i = 0; i < input.length(); i++) {
			char cha = input.charAt(i);
			boolean safe = false;
			for (int o = 0; o < onlychars.length; o++) {
				char n = onlychars[o];
				if (n == cha)
					safe = true;
			}
			if (!safe)
				return false;
		}

		return true;
	}

	public String formatColorName(String e) {
		e = e.toLowerCase();
		e = e.replace("_", " ");
		return e;
	}

}
