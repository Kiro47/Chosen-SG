package com.kiro.sg.listeners.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class HungerRefillCake implements Listener{

	
	@EventHandler
	public void onPlayerCakeUse(PlayerInteractEvent e) {
		
		if (!(e.getPlayer() instanceof Player)) return;
		
		Player p = (Player) e.getPlayer();
		
		if ((!(p.getItemInHand().equals(Material.CAKE))) ) {
			return;
		}
		
		if (    (e.getAction().equals(Action.LEFT_CLICK_AIR)) || (e.getAction().equals(Action.LEFT_CLICK_BLOCK)  ) 	) {
				return;
		}
		
		
		
		if ( (e.getAction().equals(Action.RIGHT_CLICK_AIR)  || (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)))) {
			
		
			
			e.setCancelled(true);
			
			
			if (p.getInventory().getItemInHand().equals(Material.CAKE)) {
				
				p.getInventory().setItemInHand(null);
				p.setFoodLevel(20);
				p.updateInventory();
				p.sendMessage(ChatColor.GREEN + "Your hunger has been refilled!");
				
				// Refills Hunger
			}
			
	
			
		}
		
		
		
	}
}
