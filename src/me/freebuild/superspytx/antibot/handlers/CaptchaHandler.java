package me.freebuild.superspytx.antibot.handlers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import me.freebuild.superspytx.antibot.Core;
import me.freebuild.superspytx.antibot.datatrack.Puzzle;
import me.freebuild.superspytx.antibot.settings.Permissions;
import me.freebuild.superspytx.antibot.settings.Settings;

public class CaptchaHandler {

	private Core antibot = null;

	public CaptchaHandler(Core instance) {
		antibot = instance;
	}

	public void handle(PlayerChatEvent e) {
		if (hasUnsolvedPuzzle(e.getPlayer())) { // double checking never hurts.
			Puzzle puzzle = antibot.getDataTrack().getCaptchaTracker().puzzles
					.get(e.getPlayer());
			if (puzzle.tooTaylorSwift()) {
				puzzle.getPlayer().kickPlayer(Settings.kickMsg);
				return;
			}
			if (!puzzle.checkAnswer(e.getMessage())) {
				if (puzzle.naughty()) {
					puzzle.getPlayer().kickPlayer(Settings.captchafail);
					return; // end of that.
				}

				String wrong = "";
				int attemptsleft = puzzle.getAttempts();

				if (attemptsleft < 2) {
					wrong = "1 more try left";
				} else {
					wrong = attemptsleft + " tries left";
				}

				puzzle.getPlayer().sendMessage(
						Settings.prefix + ChatColor.RED
								+ "Wrong captcha text! You have " + wrong
								+ " before you get kicked!");
				puzzle.setSolveTime(System.currentTimeMillis());
			} else {
				// it's correct! yay!
				antibot.getDataTrack().getCaptchaTracker().puzzles.remove(e
						.getPlayer());
				antibot.getDataTrack().getCaptchaTracker().solvedplayers.add(e
						.getPlayer().getName());
				e.getPlayer()
						.sendMessage(
								Settings.prefix
										+ ChatColor.GREEN
										+ "Correct! You've been unmuted! Also thanks for not using script kiddie tools.");
			}
		}
	}

	public boolean hasUnsolvedPuzzle(Player player) {
		return antibot.getDataTrack().getCaptchaTracker().puzzles
				.containsKey(player);
	}

	public void playerNeedsPuzzling(Player player) {
		if (!Settings.captchaEnabled) {
			if (Permissions.CAPTCHA.getPermission(player))
				return;
			player.kickPlayer(Settings.kickMsg); //act as if they're getting kicked.
			
			return; // do nothing else.
		}

		if (Permissions.CAPTCHA.getPermission(player))
			return;

		if (!antibot.getDataTrack().getCaptchaTracker().solvedplayers
				.contains(player.getName())) {
			Puzzle puzzle = antibot.getDataTrack().getPuzzle(player);
			antibot.getDataTrack().getCaptchaTracker().puzzles.put(player,
					puzzle);
			player.sendMessage(Settings.prefix
					+ ChatColor.RED
					+ "Oh noes, CAPTCHA! Please enter "
					+ puzzle.getColor().toString()
					+ "4 "
					+ antibot.getUtility().getCaptcha()
							.formatColorName(puzzle.getColor().name())
					+ ChatColor.RED + " colored letters from these: "
					+ puzzle.getCaptcha());
			puzzle.setSolveTime(System.currentTimeMillis());
		} else {
			// player is still spamming? what is this wizardry! /kaikzpun
			player.kickPlayer(Settings.kickMsg);
		}
	}

}
