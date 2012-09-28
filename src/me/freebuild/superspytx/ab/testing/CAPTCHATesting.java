package me.freebuild.superspytx.ab.testing;

import me.freebuild.superspytx.ab.tils.CaptchaTils;

public class CAPTCHATesting
{
    
    public static void main(String[] args)
    {
        for(String l : CaptchaTils.generatePuzzleV2())
        {
            System.out.println(l);
        }
        
        System.out.println(CaptchaTils.generatePuzzle());
    }

}
