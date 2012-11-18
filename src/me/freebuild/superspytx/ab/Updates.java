package me.freebuild.superspytx.ab;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import me.freebuild.superspytx.ab.settings.Settings;

public class Updates implements Runnable
{

    @Override
    public void run()
    {
        try
        {
            if (Settings.checkupdates)
            {
                String check = check("https://raw.github.com/SuperSpyTX/AntiBot/master/dl/update.txt").split("SERVERREPORT6356574309780958632018")[1];
                if (!AB.getRealVersion().equals(check))
                {
                    Settings.newVersion = true;
                    Settings.version = check;
                    AB.log("YAY! A new update is currently available for AntiBot!");
                    AB.log("New version: " + Settings.version + " Your version: " + AB.getVersion());
                    AB.log("Check at http://dev.bukkit.org/server-mods/antibot/");
                }
            }
        }
        catch (Exception e)
        {
            AB.log("Failed to check for a update!");
        }
    }

    public static String check(String URL) throws MalformedURLException, IOException
    {
        URL url = new URL(URL);
        BufferedInputStream bin = new BufferedInputStream(url.openStream());
        String data = "";
        int tdata;
        while (true)
        {
            tdata = bin.read();
            if (tdata == -1)
            {
                return data;
            }
            else
            {
                data += (char) tdata;
            }
        }
    }

}
