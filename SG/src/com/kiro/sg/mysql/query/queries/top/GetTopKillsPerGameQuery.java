package com.kiro.sg.mysql.query.queries.top;

import com.kiro.sg.lobby.leaderboards.LeaderBoard;

public class GetTopKillsPerGameQuery extends TopQuery
{

	public GetTopKillsPerGameQuery(LeaderBoard.LeaderboardEntry entry)
	{
		super(entry);
	}

	@Override
	public String getQuery()
	{
		return "select `name`, `uuid`, `kills`/`games` from sg_users where `games` > '10' order by `kills`/`games` desc limit 3;";
	}
}
