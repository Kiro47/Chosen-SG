package com.kiro.sg.mysql.query.queries.top;

import com.kiro.sg.lobby.leaderboards.LeaderBoard;

public class GetTopWinsQuery extends TopQuery
{

	public GetTopWinsQuery(LeaderBoard.LeaderboardEntry entry)
	{
		super(entry);
	}

	@Override
	public String getQuery()
	{
		return "select `name`, `uuid`, `wins` from sg_users order by `wins` desc limit 3;";
	}
}
