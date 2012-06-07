package me.freebuild.superspytx.antibot.datatrack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.entity.Player;

import me.freebuild.superspytx.antibot.Core;

public class CaptchaDataTrack {
	
	private Core core = null;

	public CaptchaDataTrack(Core instance) {
		core = instance;
	}
	
	public Map<Player, Puzzle> puzzles = new HashMap<Player, Puzzle>();
	public List<String> solvedplayers = new CopyOnWriteArrayList<String>();

}
