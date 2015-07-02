package com.kiro.sg.lobby.leaderboards;

import java.util.UUID;

public class TopEntry
{

	public UUID uuid;
	public String name;
	public float value;

	public TopEntry()
	{

	}

	public TopEntry(UUID uuid, String name, float value)
	{
		this.uuid = uuid;
		this.name = name;
		this.value = value;
	}

	public float getValue()
	{
		return value;
	}

	public UUID getUuid()
	{
		return uuid;
	}

	public String getName()
	{
		return name;
	}

}
