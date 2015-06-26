package com.kiro.sg.scoreboard;

import com.kiro.sg.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class GameScoreboard
{

	private final Scoreboard scoreboard;
	private final Objective sidebar;

	private final Team ghosts;

	public GameScoreboard()
	{
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		sidebar = scoreboard.registerNewObjective("Sidebar", "dummy");
		sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);

		ghosts = scoreboard.registerNewTeam("ghosts");
		ghosts.setCanSeeFriendlyInvisibles(true);
		ghosts.setPrefix(ChatColor.DARK_GREEN.toString());
	}

	public Scoreboard getScoreboard()
	{
		return scoreboard;
	}

	public void setGhosts(Player player)
	{
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 1));
		ghosts.addPlayer(player);
	}

	public void removeGhost(Player player)
	{
		ghosts.removePlayer(player);
	}

	public void updateTimer(GameState state, int time)
	{
		ChatColor color = ChatColor.GREEN;
		if (time < 600)
		{
			color = ChatColor.YELLOW;
		}
		else if (time < 300)
		{
			color = ChatColor.RED;
		}

		String text = ChatColor.GOLD + state.text + "[" + color + time / 60 + ':' + time % 60 + ']';
		sidebar.setDisplayName(text);
	}

	public void updatePlayers(int count)
	{
		sidebar.getScore(ChatColor.RED + "Remaining").setScore(count);
	}

}
