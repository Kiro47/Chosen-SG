package com.kiro.sg.mysql.query.queries;

import com.kiro.sg.PlayerStats;
import com.kiro.sg.mysql.query.ExecuteQuery;

import java.sql.ResultSet;

public class GetPlayerQuery implements ExecuteQuery
{

	private final PlayerStats stats;

	public GetPlayerQuery(PlayerStats stats)
	{
		this.stats = stats;
	}

	@Override
	public void execute(ResultSet set)
	{
		stats.loadData(set);
	}

	@Override
	public String getQuery()
	{
		return String.format("select * from chosencraft.sg_users where `uuid` = '%s';", stats.getUuid().toString());
	}
}
