package com.kiro.sg.game;

import com.kiro.sg.utils.Meta;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameManager
{

	private static final List<GameInstance> gameInstances = new ArrayList<>();

	public static void registerGame(GameInstance instance)
	{
		gameInstances.add(instance);
	}

	public static void unregisterGame(GameInstance instance)
	{
		gameInstances.remove(instance);
	}

	public static GameInstance getInstance(Player player)
	{
		return (GameInstance) Meta.getMetadata(player, "game");
	}

}
