package com.kiro.sg.game;

public enum GameState
{

	JOINING("Join"), STARTING("Start"), PLAYING("Play"), DEATHMATCH("DM"), ENDING("End"), INIT("Init");

	public final String text;

	GameState(String text)
	{
		this.text = text;
	}

}
