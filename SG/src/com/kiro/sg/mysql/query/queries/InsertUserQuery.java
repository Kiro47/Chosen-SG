package com.kiro.sg.mysql.query.queries;

import com.kiro.sg.PlayerStats;
import com.kiro.sg.mysql.query.Query;

import java.util.UUID;

public class InsertUserQuery implements Query
{
	private final UUID uuid;
	private final String name;

	public InsertUserQuery(PlayerStats stats)
	{
		uuid = stats.getUuid();
		name = stats.getUsername();
	}

	@Override
	public String getQuery()
	{
		return String.format("insert into sg_users (`uuid`, `name`) values ('%s', '%s');", uuid.toString(), name);
	}
}
