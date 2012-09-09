package me.freebuild.superspytx.ab.tils;

import me.freebuild.superspytx.ab.AB;
import me.freebuild.superspytx.ab.abs.PI;

public class MathTils
{
    public static Long getLongDiff(Long diff)
    {
        return System.currentTimeMillis() - diff;
    }
    
    public static boolean consistency(PI pl, Long time) {
        // The consistency detector.  The bot rapist.
        
        // round the difference.
        long diff = getLongDiff(time);
        long round = Math.round(diff);
        
        
        // begin identifying patterns.  By 5 seconds.
        if(pl.cs_rd != 0L)
        {
            long max = pl.cs_rd - 500L; // drop .5 seconds from the stored rounded difference.
            AB.log("Max: " + Long.toString(max) + " Round: " + Long.toString(round) + " Diff: " + Long.toString(diff));
            if(diff < pl.cs_rd && diff > max)
                return true;
        }
        
        pl.cs_rd = round;
        return false;
    }

}
