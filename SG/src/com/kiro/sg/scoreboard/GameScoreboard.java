package com.kiro.sg.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
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
		ghosts.addPlayer(player);
	}

	public void removeGhost(Player player)
	{
		ghosts.removePlayer(player);
	}

	public void updateTimer(int time)
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

		String text = ChatColor.GRAY + "[" + color + time / 60 + ':' + time % 60 + ChatColor.GRAY + ']';
		sidebar.setDisplayName(text);
	}

	public void updatePlayers(int count)
	{
		sidebar.getScore(ChatColor.RED + "Remaining").setScore(count);
	}

}
