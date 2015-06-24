package com.kiro.sg.listeners.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RedstoneHealthBonus implements Listener{

	
	@EventHandler
	public void onPlayerRedstoneUse(PlayerInteractEvent e) {
		
		if (!(e.getPlayer() instanceof Player)) return;
		
		Player p = (Player) e.getPlayer();
		
		if (	(!(p.getItemInHand().equals(Material.REDSTONE)))) {
			return;
		}
		
		if (    (e.getAction().equals(Action.LEFT_CLICK_AIR)) || (e.getAction().equals(Action.LEFT_CLICK_BLOCK)  ) 	) {
				return;
		}
		
		
		
		if ( (e.getAction().equals(Action.RIGHT_CLICK_AIR)  || (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)))) {
			
		
			
			e.setCancelled(true);
			
			if (p.getInventory().getItemInHand().equals(Material.REDSTONE)) {
				int amt = p.getInventory().getItemInHand().getAmount();
				
			p.setMaxHealth(p.getMaxHealth()  + 2.0);
			p.setHealth(p.getHealth() + 2.0);
			p.getInventory().getItemInHand().setType(null);
			p.getInventory().setItemInHand( new ItemStack(Material.REDSTONE, amt - 1 ));
			p.updateInventory();
			p.sendMessage(ChatColor.GREEN + "Health has been increased by one heart!");
			return;
			
			// Heart of health per redstone
			}

			
		}
		
		
		
	}
}
