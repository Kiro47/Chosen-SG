package com.kiro.sg.mysql.query.queries.top;

import com.kiro.sg.lobby.leaderboards.LeaderBoard;

/**
 * Created by Brandon on 6/30/2015.
 */
public class GetTopGamesQuery extends TopQuery
{

	public GetTopGamesQuery(LeaderBoard.LeaderboardEntry entry)
	{
		super(entry);
	}

	@Override
	public String getQuery()
	{
		return "select `name`, `uuid`, `games` from sg_users order by `games` desc limit 3;";
	}
}
