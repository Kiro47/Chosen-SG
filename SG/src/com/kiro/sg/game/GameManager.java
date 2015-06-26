package com.kiro.sg.game;

import com.kiro.sg.utils.Meta;
import org.bukkit.entity.Player;

public class GameManager
{


	public static GameInstance getInstance(Player player)
	{
		return (GameInstance) Meta.getMetadata(player, "game");
	}

}
