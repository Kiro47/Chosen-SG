package com.kiro.sg.utils;

import com.kiro.sg.game.GameInstance;
import org.bukkit.entity.Player;

public final class Msg
{

	private Msg()
	{

	}

	public static void msgGame(String message, GameInstance game, boolean spectator)
	{
		for (Player player : game.getSpectators())
		{
			msgPlayer(player, message);
		}

		if (!spectator)
		{
			for (Player player : game.getRemaining())
			{
				msgPlayer(player, message);
			}
		}
	}

	public static void msgPlayer(Player player, String message)
	{
		player.sendMessage(message);
	}

}
