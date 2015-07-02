package com.kiro.sg.mysql.query.queries.top;

import com.kiro.sg.lobby.leaderboards.LeaderBoard;

public class GetTopScoreQuery extends TopQuery
{

	public GetTopScoreQuery(LeaderBoard.LeaderboardEntry entry)
	{
		super(entry);
	}

	@Override
	public String getQuery()
	{
		return "select `name`, `uuid`, `score` from sg_users order by `score` desc limit 3;";
	}
}
