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
	private final Team living;

	public GameScoreboard()
	{
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		sidebar = scoreboard.registerNewObjective("Sidebar", "dummy");
		sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);

		ghosts = scoreboard.registerNewTeam("ghosts");
		ghosts.setCanSeeFriendlyInvisibles(true);
		ghosts.setPrefix(ChatColor.DARK_GREEN.toString());

		living = scoreboard.registerNewTeam("living");
		living.setCanSeeFriendlyInvisibles(true);
	}

	public Scoreboard getScoreboard()
	{
		return scoreboard;
	}

	public void dispose()
	{
		try
		{
			sidebar.unregister();
			ghosts.unregister();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void chestRefill(int time)
	{
		sidebar.getScore(ChatColor.GREEN + "Chest Refill").setScore(time);
	}

	public void addLiving(Player player)
	{
		living.addPlayer(player);
	}

	public void removeLiving(Player player)
	{
		living.removePlayer(player);
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
		text = String.format("%s%s%s[%s%02d:%02d%s]", ChatColor.GOLD.toString(), state.text, ChatColor.GRAY.toString(), color.toString(), time / 60, time % 60, ChatColor.GRAY.toString());
		sidebar.setDisplayName(text);
	}

	public void updatePlayers(int count)
	{
		sidebar.getScore(ChatColor.RED + "Remaining").setScore(count);
	}

}
