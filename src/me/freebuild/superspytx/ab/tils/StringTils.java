package me.freebuild.superspytx.ab.tils;

public class StringTils
{

    public static int strDiffCounter(String now, String before)
    {
        int record = 0;
        char[] n = now.toCharArray();
        char[] b = before.toCharArray();
        boolean g = false;
        int ct = 0;
        while (!g)
        {
            if ((n.length) >= ct)
            {
                record += Math.abs(b.length - n.length) * 2;
                break;
            }
            else if ((b.length) >= ct)
            {
                record += Math.abs(n.length - b.length) * 2;
                break;
            }
            char nn = n[ct];
            char bb = b[ct];

            if (nn != bb)
                record++;

            ct++;
        }

        return record;
    }

}
