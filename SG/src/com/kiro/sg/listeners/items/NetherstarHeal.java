package com.kiro.sg.listeners.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class NetherstarHeal implements Listener{
	
	@EventHandler
	public void onPlayerNetherHealUse(PlayerInteractEvent e) {
		
		if (!(e.getPlayer() instanceof Player)) return;
		
		Player p = (Player) e.getPlayer();
		
		if (	(!(p.getItemInHand().equals(Material.REDSTONE))) || 	 (!(p.getItemInHand().equals(Material.NETHER_STAR)))  || 
				(!(p.getItemInHand().equals(Material.CAKE))) 	|| (!(p.getItemInHand().equals(Material.ENDER_CHEST))) ) {
			return;
		}
		
		if (    (e.getAction().equals(Action.LEFT_CLICK_AIR)) || (e.getAction().equals(Action.LEFT_CLICK_BLOCK)  ) 	) {
				return;
		}
		
		
		
		if ( (e.getAction().equals(Action.RIGHT_CLICK_AIR)  || (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)))) {
			
		
			
			e.setCancelled(true);
			

			if (p.getInventory().getItemInHand().equals(Material.NETHER_STAR)) {
				
				int amt = p.getInventory().getItemInHand().getAmount();
				double mhp = p.getMaxHealth();
				double thp = mhp / 4;
				double hhp =  thp * 3;
				
				if (p.getHealth() + hhp > p.getMaxHealth()) {
					p.setHealth(p.getMaxHealth());
					p.getInventory().setItemInHand(null);
					p.getInventory().setItemInHand(new ItemStack(Material.NETHER_STAR, amt - 1));
					p.updateInventory();
					p.sendMessage(ChatColor.GREEN + "Some of your health has been restored!");
					
					return;
				}
				
				
				else {
					p.setHealth(p.getHealth() + hhp);
					p.getInventory().setItemInHand(null);
					p.getInventory().setItemInHand(new ItemStack(Material.NETHER_STAR, amt - 1));
					p.updateInventory();
					p.sendMessage(ChatColor.GREEN + "Some of your health has been restored!");
					return;
				}
			
				
				// Restores 3/4 of HP
			}
		}
		
		
		
	}
}
