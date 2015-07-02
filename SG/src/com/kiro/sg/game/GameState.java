package com.kiro.sg.game;

import com.kiro.sg.utils.chat.ChatUtils;

public enum GameState
{

	JOINING("Join", "&3Joining"), STARTING("Start", "&bStarting"), PLAYING("Play", "&aPlaying"), DEATHMATCH("DM", "&cDeathmatch"), ENDING("End", "&4Ending"), INIT("Init", "&eInitializing"), ENDED("Done", "&4Done.");

	public final String text;
	public final String disp;

	GameState(String text, String disp)
	{
		this.text = text; this.disp = ChatUtils.format(disp);
	}

}
