package me.freebuild.superspytx.antibot.toolbox;

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
			}
		}

		// generate answer from generated string.
		String answer = "";
		String[] points = new String[3];
		String restrictedcolorcode = minealpha[rdm.nextInt(maxmineA)];

		for (int o = 0; o < 4; o++) {
			points[o] = Integer.toString(rdm.nextInt(puzzle.length() - 1));
			answer += puzzle.charAt(Integer.parseInt(points[o]));
		}

		// now format color codes.
		String newpuzzle = "";

		for (int g = 0; g < puzzle.length() + 1; g++) {
			char spec = puzzle.charAt(g);
			String colorcode = "\247" + minealpha[rdm.nextInt(maxmineA)];
			for (int z = 0; z < 4; z++) {
				if (Integer.parseInt(points[z]) == g
						&& answer.contains(("" + spec))) { // roflexploit
					colorcode = "\247" + restrictedcolorcode;
				}
			}

			newpuzzle += colorcode + spec;
		}

		// attach answer in comma.
		newpuzzle += "," + answer + "," + restrictedcolorcode;

		// return generated string/puzzle.
		return newpuzzle;
	}

}
