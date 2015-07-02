package com.kiro.sg.mysql.query.queries;

import com.kiro.sg.PlayerStats;
import com.kiro.sg.mysql.query.Query;

public class UpdatePlayerQuery implements Query
{

	private final PlayerStats stats;

	public UpdatePlayerQuery(PlayerStats stats)
	{
		this.stats = stats;
	}

	@Override
	public String getQuery()
	{
		return String.format("update sg_users set `name`='%s', `games`='%d', `kills`='%d', `deaths`='%d', `points`='%d', `score`='%d', `wins`='%d' where `id`='%d';",
				                    stats.getUsername(), stats.getGames(), stats.getKills(), stats.getDeaths(), stats.getPoints(), stats.getScore(), stats.getWins(), stats.getID());
	}
}
