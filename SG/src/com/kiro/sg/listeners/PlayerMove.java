package com.kiro.sg.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.kiro.sg.Arena;
import com.kiro.sg.Arena.ArenaState;
import com.kiro.sg.ArenaManager;

public class PlayerMove implements Listener{
	
	@EventHandler
	
	public void onPlayerMove(PlayerMoveEvent e) {
		Arena a = ArenaManager.getInstance().getArena(e.getPlayer());
		
		if (a == null) {
			return;
		}
		
		
		if (a.getState() ==  ArenaState.STARTED) {
			return;
		}
		
		if (e.getTo().getX() == e.getFrom().getX() && e.getTo().getZ() == e.getFrom().getZ()) {
			return;
		}
		
		e.setTo(e.getFrom());
	}

}
