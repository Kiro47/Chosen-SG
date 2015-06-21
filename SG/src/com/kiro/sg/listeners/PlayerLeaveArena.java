package com.kiro.sg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.kiro.sg.Arena;
import com.kiro.sg.ArenaManager;

public class PlayerLeaveArena implements Listener{
	
	@EventHandler
	
	public void onPlayerRepsanw(PlayerRespawnEvent e) {
		handle(e.getPlayer());
		e.setRespawnLocation(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
		return;
	}

	@EventHandler
	
	public void onPlayerQuit(PlayerQuitEvent e) {
		handle(e.getPlayer());
		return;
	}
	
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		e.getPlayer().teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
		return;
	}
	
	
	private void handle(Player p) {
		Arena a = ArenaManager.getInstance().getArena(p);
		
		if (a == null) {
			return;
		}
		
		a.removePlayer(p);
	}
}
