package com.kiro.sg;

import com.kiro.sg.mysql.Consumer;
import com.kiro.sg.mysql.query.queries.GetInsertIndex;
import com.kiro.sg.mysql.query.queries.GetPlayerQuery;
import com.kiro.sg.mysql.query.queries.InsertUserQuery;
import com.kiro.sg.mysql.query.queries.UpdatePlayerQuery;
import com.kiro.sg.utils.LastIDable;
import com.kiro.sg.utils.Meta;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerStats implements LastIDable
{

	private int id;
	private int wins;
	private int kills;
	private int score;
	private int points;
	private int games;
	private int deaths;
	private final UUID uuid;
	private final String username;

	private boolean edits;

	public PlayerStats(Player player)
	{
		uuid = player.getUniqueId();
		username = player.getName();
		Consumer.queue(new GetPlayerQuery(this));
		edits = false;
	}

	@Override
	public int getID()
	{
		return id;
	}

	@Override
	public void setID(int id)
	{
		System.out.println("Player ID: " + id);
		this.id = id;
	}

	public String getUsername()
	{
		return username;
	}

	public UUID getUuid()
	{
		return uuid;
	}

	public void addKill()
	{
		kills += 1;
		edits = true;
	}

	public void addDeath()
	{
		deaths += 1;
		edits = true;
	}

	public void addGame()
	{
		games += 1;
		edits = true;
	}

	public void addWin()
	{
		wins += 1;
		edits = true;
	}

	public void addPoints(int points)
	{
		this.points += points;
		edits = true;
	}

	public void setScore(int score)
	{
		this.score = score;
		edits = true;
	}

	public void loadData(ResultSet set)
	{
		try
		{
			if (set.first())
			{
				id = set.getInt(1);
				games = set.getInt(4);
				kills = set.getInt(5);
				deaths = set.getInt(6);
				points = set.getInt(7);
				score = set.getInt(8);
				wins = set.getInt(9);
				System.out.println("Player ID: " + id);
			}
			else
			{
				Consumer.queue(new InsertUserQuery(this));
				Consumer.queue(new GetInsertIndex(this));
				games = 0;
				kills = 0;
				deaths = 0;
				points = 0;
				score = 0;
				wins = 0;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void update()
	{
		if (edits)
		{
			edits = false;
			Consumer.queue(new UpdatePlayerQuery(this));
		}
	}

	public static void removeStats(Player player)
	{
		PlayerStats stats = getStats(player);
		if (stats != null)
		{
			stats.update();
		}
		Meta.removeMetadata(player, "stats");
	}

	public static PlayerStats getStats(Player player)
	{
		PlayerStats stats = (PlayerStats) Meta.getMetadata(player, "stats");
		if (stats == null)
		{
			Meta.setMetadata(player, "stats", stats = new PlayerStats(player));
		}

		return stats;
	}

	public int getKills()
	{
		return kills;
	}

	public int getGames()
	{
		return games;
	}

	public int getDeaths()
	{
		return deaths;
	}

	public int getPoints()
	{
		return points;
	}

	public int getScore()
	{
		return score;
	}

	public int getWins()
	{
		return wins;
	}
}
