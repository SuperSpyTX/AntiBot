package com.superspytx.ab.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import com.superspytx.ab.AB;
import com.superspytx.ab.abs.PI;
import com.superspytx.ab.handlers.Handlers;
import com.superspytx.ab.settings.Language;
import com.superspytx.ab.tils.CaptchaTils;
import com.superspytx.ab.workflow.GD;

/**
 * The only file in AntiBot ever documented. The official AntiBot API.
 * 
 * @author SuperSpyTX
 */
public class AntiBotAPI {
    
    /**
     * Check whether a player is exempted from AntiBot check(s).
     * 
     * @param player The player you want to check.
     * @return Whether the player is exempt from AntiBot check(s) or not.
     * @throws AntiBotAPIException if certain conditions are undesirable.
     */
    public static boolean isExempt(Player player) throws AntiBotAPIException {
        checkSafeForApiUsage(player);
        if (GD.getPI(player) == null) throw new AntiBotAPIException("Player does not exist in AntiBot.  Wtf?");
        return (GD.getPI(player).isExempt || !GD.getPI(player).exemptedHandlers.isEmpty());
    }
    
    /**
     * Check whether a player is exempted from a certain AntiBot check.
     * 
     * @param player The player you want to check.
     * @param handle The check you want to check against.
     * @return Whether the player is exempt from the given AntiBot check or not.
     * @throws AntiBotAPIException if certain conditions are undesirable.
     */
    public static boolean isExempt(Player player, Handlers handle) throws AntiBotAPIException {
        checkSafeForApiUsage(player, handle);
        if (GD.getPI(player) == null) throw new AntiBotAPIException("Player does not exist in AntiBot.  Wtf?");
        return (GD.getPI(player).exemptedHandlers.contains(handle) || !(handle.checkUser(player)));
    }
    
    /**
     * Exempt a player from all AntiBot checks.
     * 
     * @param player The player you want to exempt.
     * @throws AntiBotAPIException if certain conditions are undesirable.
     */
    public static void exemptPlayer(Player player) throws AntiBotAPIException {
        checkSafeForApiUsage(player);
        if (GD.getPI(player) == null) throw new AntiBotAPIException("Player does not exist in AntiBot.  Wtf?");
        AB.log("API: Exempted player(" + player.getName() + ") from all AntiBot checks.");
        GD.getPI(player).isExempt = true;
    }
    
    /**
     * Exempt a player from a certain AntiBot check.
     * 
     * @param player The player you want to exempt.
     * @throws AntiBotAPIException if certain conditions are undesirable.
     */
    public static void exemptPlayer(Player player, Handlers handle) throws AntiBotAPIException {
        checkSafeForApiUsage(player, handle);
        if (GD.getPI(player) == null) throw new AntiBotAPIException("Player does not exist in AntiBot.  Wtf?");
        if (GD.getPI(player).exemptedHandlers.contains(handle)) return;
        AB.log("API: Added handler(" + handle.name() + ") exemption for player(" + player.getName() + ")");
        GD.getPI(player).exemptedHandlers.add(handle);
    }
    
    /**
     * Unexempt a player from all AntiBot checks.
     * 
     * @param player The player you want to no longer be exempt.
     * @throws AntiBotAPIException if certain conditions are undesirable.
     */
    public static void unexemptPlayer(Player player) throws AntiBotAPIException {
        checkSafeForApiUsage(player);
        if (GD.getPI(player) == null) throw new AntiBotAPIException("Player does not exist in AntiBot.  Wtf?");
        AB.log("API: Unexempted player(" + player.getName() + ") from all AntiBot checks.");
        GD.getPI(player).isExempt = false;
        GD.getPI(player).exemptedHandlers.clear();
    }
    
    /**
     * Unexempt a player from a certain AntiBot check.
     * 
     * @param player The player you want to no longer be exempt.
     * @throws AntiBotAPIException if certain conditions are undesirable.
     */
    public static void unexemptPlayer(Player player, Handlers handle) throws AntiBotAPIException {
        checkSafeForApiUsage(player, handle);
        if (GD.getPI(player) == null) throw new AntiBotAPIException("Player does not exist in AntiBot.  Wtf?");
        if (!GD.getPI(player).exemptedHandlers.contains(handle)) return;
        AB.log("API: Removed handler(" + handle.name() + ") exemption for player(" + player.getName() + ")");
        GD.getPI(player).exemptedHandlers.remove(handle);
    }
    
    /**
     * Deactivate a handler/check in AntiBot globally.
     * 
     * @param handle The handler to deactivate.
     * @throws AntiBotAPIException if certain conditions are undesirable.
     */
    public static void deactivateHandler(Handlers handle) throws AntiBotAPIException {
        checkSafeForApiUsage(handle);
        if (GD.deactivatedHandlers.contains(handle)) return;
        AB.log("API: Deactivated handler(" + handle.name() + ") globally.");
        GD.deactivatedHandlers.add(handle);
    }
    
    /**
     * Activate a handler/check in AntiBot globally.
     * 
     * @param handle The handler to activate.
     * @throws AntiBotAPIException if certain conditions are undesirable.
     */
    public static void activateHandler(Handlers handle) throws AntiBotAPIException {
        checkSafeForApiUsage(handle);
        if (!GD.deactivatedHandlers.contains(handle)) return;
        AB.log("API: Activated handler(" + handle.name() + ") globally.");
        GD.deactivatedHandlers.remove(handle);
    }
    
    /**
     * Get a player's analyzed AntiBot data.
     * 
     * @param player The player you want to get data from.
     * @return the player's PlayerInfo object containing the player's data.
     * @throws AntiBotAPIException if certain conditions are undesirable.
     */
    public static PI getPlayerData(Player player) throws AntiBotAPIException {
        checkSafeForApiUsage(player);
        AB.log("API: Returned player(" + player.getName() + ") data.");
        return GD.getPI(player);
    }
    
    /**
     * Sends a player a puzzle for them to solve. <b>NOTICE:</b> If you send a puzzle to a exempt player, it will not do anything.
     * 
     * @param player The recipient of the puzzle.
     * @throws AntiBotAPIException if certain conditions are undesirable.
     */
    public static void sendPuzzle(Player player) throws AntiBotAPIException {
        checkSafeForApiUsage(player);
        CaptchaTils.sendCaptchaToPlayerAPI(player);
    }
    
    /**
     * Automatically solves a player's puzzle. This is the way to remove a player's puzzle. <b>NOTICE:</b> This does nothing if the player doesn't have a puzzle in the first place.
     * 
     * @param player The recipient with a puzzle.
     * @throws AntiBotAPIException if certain conditions are undesirable.
     */
    public static void autoSolvePuzzle(Player player) throws AntiBotAPIException {
        checkSafeForApiUsage(player);
        PI pli = GD.getPI(player);
        if (pli == null) throw new AntiBotAPIException("Player does not exist in AntiBot.  Wtf?");
        if (!pli.cp_haspuzzle) return;
        if (pli.cp_puzzle == null) throw new AntiBotAPIException("The player's puzzle is null!");
        AB.log("API: Autosolved a player(" + player.getName() + ")'s puzzle");
        pli.cp_haspuzzle = false;
        Bukkit.broadcastMessage("<" + pli.pl.getDisplayName() + "> " + pli.cs_lsm);
        pli.resetSpamData();
        pli.pl.sendMessage(Language.prefix + '\247' + "a" + "Correct! Thanks for not being a bot. You can now speak again.");
    }
    
    /**
     * Check if the AntiBot API is available for usage.
     * 
     * @param player The player object
     * @throws AntiBotAPIException when it is not safe for use.
     */
    private static void checkSafeForApiUsage(Object... player) throws AntiBotAPIException {
        if (AB.getInstance() != null) {
            for (Object pl : player) {
                if (pl != null) {
                    continue;
                }
                throw new AntiBotAPIException("An object passed to the API is null.");
            }
            return;
        }
        
        throw new AntiBotAPIException("AntiBot has not fully loaded yet.");
    }
}
