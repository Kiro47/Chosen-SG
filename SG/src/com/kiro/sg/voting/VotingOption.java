package com.kiro.sg.voting;

import com.kiro.sg.game.arena.ArenaManager;
import com.kiro.sg.game.arena.SGArena;

import java.util.regex.Pattern;

public class VotingOption
{

	private static final Pattern COMPILE = Pattern.compile(" ", Pattern.LITERAL);
	private final SGArena arena;
	private final boolean random;
	private int votes;
	private int option;
	private boolean isWinning;

	public VotingOption(SGArena arena)
	{
		this.arena = arena;
		random = false;
	}

	public VotingOption(boolean random)
	{
		this.arena = ArenaManager.getInstance().selectRandomArena();
		this.random = random;
	}

	public String getDisplayName()
	{
		return random ? "Random" : arena.getArenaName();
	}

	public SGArena getValue()
	{
		return arena;
	}

	public void reset()
	{
		votes = 0;
		isWinning = false;
	}

	public int getVotes()
	{
		return votes;
	}

	public int addVote()
	{
		return ++votes;
	}

	public int removeVote()
	{
		return --votes;
	}

	public int getOption()
	{
		return option;
	}

	public void setOption(int option)
	{
		this.option = option;
	}

	public boolean isWinning()
	{
		return isWinning;
	}

	public void setWinning(boolean winning)
	{
		isWinning = winning;
	}

	public String getMapImageName()
	{
		return COMPILE.matcher(getDisplayName()).replaceAll("");
	}

	@Override
	public boolean equals(Object obj)
	{
		return ((VotingOption) obj).getDisplayName().equals(getDisplayName());
	}
}
