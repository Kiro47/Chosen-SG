package com.kiro.sg.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class LobbyScoreboard
{


	private final Scoreboard scoreboard;
	private final Objective sidebar;
	private final Team voted;


	public LobbyScoreboard()
	{
		scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

		for (Objective objective : scoreboard.getObjectives())
		{
			objective.unregister();
		}
		for (Team objective : scoreboard.getTeams())
		{
			objective.unregister();
		}

		sidebar = scoreboard.registerNewObjective("Sidebar", "dummy");
		sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);

		voted = scoreboard.registerNewTeam("voted");
		voted.setPrefix(ChatColor.GOLD.toString());
	}

	public Scoreboard getScoreboard()
	{
		return scoreboard;
	}

	public void addVoted(Player player)
	{
		voted.addPlayer(player);
	}

	public void removeVoted(Player player)
	{
		voted.removePlayer(player);
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
		sidebar.getScore(ChatColor.RED + "Players Waiting").setScore(count);
	}

	public void updateOnlinePlayers()
	{
		sidebar.getScore(ChatColor.GREEN + "Online").setScore(Bukkit.getOnlinePlayers().size());
	}
}
