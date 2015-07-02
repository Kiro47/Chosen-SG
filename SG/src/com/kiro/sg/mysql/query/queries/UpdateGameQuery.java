package com.kiro.sg.mysql.query.queries;

import com.kiro.sg.PlayerStats;
import com.kiro.sg.game.GameInstance;
import com.kiro.sg.mysql.query.Query;

public class UpdateGameQuery implements Query
{

	private final GameInstance game;

	public UpdateGameQuery(GameInstance game)
	{
		this.game = game;
	}

	@Override
	public String getQuery()
	{
		if (game.getWinner() != null)
		{
			PlayerStats stats = PlayerStats.getStats(game.getWinner());
			return String.format("update sg_games set `winner` = '%d' where `id` = '%d'", stats.getID(), game.getID());
		}
		else
		{
			return String.format("update sg_games set `winner` = '%d' where `id` = '%d'", 0, game.getID());
		}
	}
}
