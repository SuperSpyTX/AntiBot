package me.freebuild.superspytx.ab.utils;

public class StringCompare
{
    
    public static int strDiffCounter(String now, String before)
    {
        int record = 0;
        char[] n = now.toCharArray();
        char[] b = before.toCharArray();
        boolean g = false;
        int ct = 0;
        while(!g)
        {
            if((n.length) >= ct)
            {
                record += b.length - n.length;
                break;
            } else if ((b.length) >= ct) {
                record += n.length - b.length;
                break;
            }
            char nn = n[ct];
            char bb = b[ct];
            
            if(nn != bb)
                record++;
            
            ct++;
        }
        
        return record;
    }

}
