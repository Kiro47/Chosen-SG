package com.kiro.sg.utils.chat;

import com.kiro.sg.game.GameInstance;
import org.bukkit.World;
import org.bukkit.entity.Player;

public final class Msg
{

	private Msg()
	{

	}

	public static void msgWorld(World world, String message)
	{
		for (Player player : world.getPlayers())
		{
			player.sendMessage(message);
		}
	}

	public static void msgGame(String message, GameInstance game, boolean spectator)
	{
		if (spectator)
		{
			for (Player player : game.getSpectators())
			{
				msgPlayer(player, message);
			}
		}
		else
		{
			msgWorld(game.getArena().getWorld(), message);
		}
	}

	public static void msgPlayer(Player player, String message)
	{
		player.sendMessage(message);
	}

}
