package com.kiro.sg.mysql.query.queries.top;

import com.kiro.sg.lobby.leaderboards.LeaderBoard;

public class GetTopKillsQuery extends TopQuery
{
	public GetTopKillsQuery(LeaderBoard.LeaderboardEntry entry)
	{
		super(entry);
	}

	@Override
	public String getQuery()
	{
		return "select `name`, `uuid`, `kills` from sg_users order by `kills` desc limit 3;";
	}
}
