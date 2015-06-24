package com.kiro.sg.listeners.items;

import java.util.Comparator;

import org.bukkit.entity.Player;

public class CompassTracker implements Comparator<Player> {
	

	
	private Player player;
	
	public CompassTracker(Player player) {
		this.player = player;
	}
	
	@Override 
	public int compare (Player targ1, Player targ2) {
		return compare(
				targ1.getLocation().distance(player.getLocation()),
				targ2.getLocation().distance(player.getLocation()));
	}
	
	private int compare(double a, double b) {
		return a > b ? -1 : a > b ? 1 : 0 ;
	}
	
}
