package com.kiro.sg.listeners;

import com.kiro.sg.game.GameInstance;
import com.kiro.sg.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerLeaveArena implements Listener
{

	@EventHandler

	public void onPlayerRepsawn(PlayerRespawnEvent e)
	{
		GameInstance instance = GameManager.getInstance(e.getPlayer());
		if (instance != null)
		{
			instance.setSpectator(e.getPlayer());
		}
		//		handle(e.getPlayer());
		//		e.setRespawnLocation(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e)
	{
		handle(e.getPlayer());
	}


	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		e.getPlayer().teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
	}


	private static void handle(Player p)
	{
		GameInstance instance = GameManager.getInstance(p);

		if (instance != null)
		{
			instance.removePlayer(p);
		}

	}
}
