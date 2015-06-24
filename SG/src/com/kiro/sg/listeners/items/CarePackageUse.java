package com.kiro.sg.listeners.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class CarePackageUse implements Listener{

	
	@EventHandler
	public void onCarePackageUse(PlayerInteractEvent e) {
		
		if (!(e.getPlayer() instanceof Player)) return;
		
		Player p = (Player) e.getPlayer();
		
		if ( (!(p.getItemInHand().equals(Material.ENDER_CHEST))) ) {
			return;
		}
		
		if (    (e.getAction().equals(Action.LEFT_CLICK_AIR)) || (e.getAction().equals(Action.LEFT_CLICK_BLOCK)  ) 	) {
				return;
		}
		
		
		
		if ( (e.getAction().equals(Action.RIGHT_CLICK_AIR)  || (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)))) {
			
		
			
			e.setCancelled(true);

			
			if (p.getInventory().getItemInHand().equals(Material.ENDER_CHEST)) {
				
			
				// Add Chest instance spawning
				
			}
			
			
		}
		
		
		
	}
}
