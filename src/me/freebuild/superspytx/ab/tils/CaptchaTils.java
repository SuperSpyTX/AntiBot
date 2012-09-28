package me.freebuild.superspytx.ab.tils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CaptchaTils
{
    public static String generatePuzzle()
    {
        // define variables.
        String[] alphabetlower = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z".split(",");
        String[] alphabetupper = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z".split(",");
        String[] numbers = "0,1,2,3,4,5,6,7,8,9".split(",");
        String[] minealpha = "0,1,2,3,4,5,6,7,8,9,a,b,c,d,e,f".split(",");
        int maxalphaL = alphabetlower.length;
        int maxalphaU = alphabetupper.length;
        int maxnum = numbers.length;
        int maxmineA = minealpha.length;

        // generate 10 character string.
        Random rdm = new Random();
        String puzzle = "";
        for (int i = 0; i < 11; i++)
        {
            switch (rdm.nextInt(2))
            {
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

        for (int a = 0; a < 4; a++)
        {
            points[a] = Integer.toString(rdm.nextInt(puzzle.length() - 1));
        }

        // check for repeats (Randomizer sucks)
        for (int z = 0; z < 4; z++)
        {
            String point = points[z];

            if (z != 0 && points[0].equalsIgnoreCase(point))
            {
                points[z] = Integer.toString(rdm.nextInt(puzzle.length() - 1));
            }

            if (z != 1 && points[1].equalsIgnoreCase(point))
            {
                points[z] = Integer.toString(rdm.nextInt(puzzle.length() - 1));
            }

            if (z != 2 && points[2].equalsIgnoreCase(point))
            {
                points[z] = Integer.toString(rdm.nextInt(puzzle.length() - 1));
            }

            if (z != 3 && points[3].equalsIgnoreCase(point))
            {
                points[z] = Integer.toString(rdm.nextInt(puzzle.length() - 1));
            }

        }

        for (int o = 0; o < 4; o++)
        {
            answer += puzzle.charAt(Integer.parseInt(points[o]));
        }

        // now format color codes.
        String newpuzzle = "";
        String colors = "";

        for (int g = 0; g < puzzle.length(); g++)
        {
            char spec = puzzle.charAt(g);
            String colorcode = "\247" + minealpha[rdm.nextInt(maxmineA)];

            if (colorcode.equalsIgnoreCase("\247" + restrictedcolorcode))
            {
                colorcode = "\247e";
            }

            for (int z = 0; z < 4; z++)
            {
                if (Integer.parseInt(points[z]) == g && answer.contains(("" + spec)))
                { // roflexploit
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
        for (int r = 0; r < victor.length; r++)
        {
            if (!quickie.containsKey("" + victor[r]))
                quickie.put("" + victor[r], 0);
            for (int q = 0; q < lawl.length; q++)
            {
                char h = lawl[q];
                if (victor[r] == h)
                {
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

    public static String randomNumbers()
    {
        int[] g = new int[4];
        g[0] = (new Random()).nextInt(9);
        g[1] = (new Random()).nextInt(9);
        g[2] = (new Random()).nextInt(9);
        g[3] = (new Random()).nextInt(9);
        for (int i = 0; i < g.length; i++)
        {
            int o = g[i];
            for (int c = 0; c < g.length; c++)
            {
                if (c == i)
                    continue;
                int a = g[c];
                if (o == a)
                    return randomNumbers(); // looks like java's random number generator derped.
            }
        }

        String convert = "";
        for (int z : g)
            convert += Integer.toString(z);

        return convert;
    }

    public static String[] generatePuzzleV2()
    {
        String str = randomNumbers();
        String[] speceffects = "l,m,n,o,r".split(",");
        String[] minealpha = "0,1,2,3,4,5,6,7,8,9,a,b,c,d,e,f".split(",");
        String spec = '\247' + minealpha[(new Random()).nextInt(minealpha.length)] + '\247' + speceffects[(new Random()).nextInt(speceffects.length)];
        String[] puzzle = new String[6];
        String[] p0 = strFormat(str.toCharArray()[0]);
        String[] p1 = strFormat(str.toCharArray()[1]);
        String[] p2 = strFormat(str.toCharArray()[2]);
        String[] p3 = strFormat(str.toCharArray()[3]);
        for(int i = 0; i < 6; i++)
        {
            if(i > 4)
                puzzle[i] = str;
            else
            {
                puzzle[i] = spec + p0[i] + "  " + p1[i] + "  " + p2[i] + "  " + p3[i];
            }
        }
        return puzzle;
    }

    private static String[] strFormat(char g)
    {
        if (g == "0".toCharArray()[0])
        {
            return new String[] { "####", "#  #", "#  #", "#  #", "####" };
        }
        if (g == "1".toCharArray()[0])
        {
            return new String[] { " # ", "## ", " # ", " # ", "###" };
        }
        if (g == "2".toCharArray()[0])
        {
            return new String[] { "####", "   #", "####", "#   ", "####" };
        }
        if (g == "3".toCharArray()[0])
        {
            return new String[] { "####", "   #", " ###", "   #", "####" };
        }
        if (g == "4".toCharArray()[0])
        {
            return new String[] { "#  #", "#  #", "####", "   #", "   #" };
        }
        if (g == "5".toCharArray()[0])
        {
            return new String[] { "####", "#   ", "####", "   #", "####" };
        }
        if (g == "6".toCharArray()[0])
        {
            return new String[] { "####", "#   ", "####", "#  #", "####" };
        }
        if (g == "7".toCharArray()[0])
        {
            return new String[] { "####", "   #", "   #", "   #", "   #" };
        }
        if (g == "8".toCharArray()[0])
        {
            return new String[] { "####", "#  #", "####", "#  #", "####" };
        }
        if (g == "9".toCharArray()[0])
        {
            return new String[] { "####", "#  #", "####", "   #", "####" };
        }

        return null;

    }

    public static boolean compare(String input, String answer)
    {
        char[] onlychars = answer.toLowerCase().toCharArray();

        input = input.toLowerCase();

        if (input.length() > answer.length() || input.length() < answer.length())
        {
            return false;
        }

        for (int i = 0; i < input.length(); i++)
        {
            char cha = input.charAt(i);
            boolean safe = false;
            for (int o = 0; o < onlychars.length; o++)
            {
                char n = onlychars[o];
                if (n == cha)
                    safe = true;
            }
            if (!safe)
                return false;
        }

        return true;
    }

    public static String formatColorName(String e)
    {
        e = e.toLowerCase();
        e = e.replace("_", " ");
        return e;
    }
}
