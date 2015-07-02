package com.kiro.sg.mysql.query.queries;

import com.kiro.sg.game.GameInstance;
import com.kiro.sg.mysql.query.Query;

public class AddGameQuery implements Query
{

	private final GameInstance game;

	public AddGameQuery(GameInstance game)
	{
		this.game = game;
	}

	@Override
	public String getQuery()
	{
		return String.format("insert into sg_games values (NULL, '%s', 0, '%d');", game.getArena().getArenaName(), game.getRemaining().size());
	}
}
