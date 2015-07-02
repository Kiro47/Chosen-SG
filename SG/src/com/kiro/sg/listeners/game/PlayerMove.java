package com.kiro.sg.listeners.game;

import com.kiro.sg.game.GameInstance;
import com.kiro.sg.game.GameManager;
import com.kiro.sg.game.GameState;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener
{

	@EventHandler

	public void onPlayerMove(PlayerMoveEvent e)
	{
		//		Arena a = ArenaManager.getInstance().getArena(e.getPlayer());

		GameInstance instance = GameManager.getInstance(e.getPlayer());
		if (instance != null && instance.getState() == GameState.STARTING)
		{
			Location to = e.getTo();
			Location from = e.getFrom();
			if (to.getBlockX() != from.getBlockX() || to.getBlockZ() != from.getBlockZ())
			{
				e.getPlayer().teleport(from);
			}
		}

		//		if (a == null)
		//		{
		//			return;
		//		}
		//
		//
		//		if (a.getState() == ArenaState.STARTED)
		//		{
		//			return;
		//		}
		//
		//		if (e.getTo().getX() == e.getFrom().getX() && e.getTo().getZ() == e.getFrom().getZ())
		//		{
		//			return;
		//		}
		//
		//		e.setTo(e.getFrom());
	}

}
