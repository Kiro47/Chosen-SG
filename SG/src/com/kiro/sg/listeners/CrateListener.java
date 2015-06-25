package com.kiro.sg.listeners;

import com.kiro.sg.game.GameInstance;
import com.kiro.sg.game.GameManager;
import com.kiro.sg.game.crates.SupplyCrate;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class CrateListener implements Listener
{

	@EventHandler
	public void onInteract(PlayerInteractEvent event)
	{
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			Block block = event.getClickedBlock();
			Material type = block.getType();
			if (type == Material.CHEST || type == Material.TRAPPED_CHEST || type == Material.ENDER_CHEST)
			{
				Player player = event.getPlayer();
				GameInstance game = GameManager.getInstance(player);
				if (game != null)
				{
					event.setCancelled(true);
					SupplyCrate crate = game.getCrates().getCrate(block.getLocation());
					if (type == Material.ENDER_CHEST)
					{
						crate.populate(3);
					}
					crate.open(player);

					World world = player.getWorld();
					world.playSound(block.getLocation(), Sound.CHEST_OPEN, 1.0f, 1.0f);
				}
			}
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event)
	{
		Inventory inventory = event.getInventory();
		if (inventory.getViewers().size() == 1 || inventory.getSize() == 9)
		{
			World world = event.getPlayer().getWorld();
			world.playSound(event.getPlayer().getLocation(), Sound.CHEST_CLOSE, 1.0f, 1.0f);
		}
	}


}
