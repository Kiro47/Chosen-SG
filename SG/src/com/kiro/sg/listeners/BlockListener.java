package com.kiro.sg.listeners;

import com.kiro.sg.Config;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockListener implements Listener
{

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event)
	{
		Player player = event.getPlayer();
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE)
		{
			event.setCancelled(false);
			return;
		}

		event.setCancelled(true);

		if (player.getGameMode() == GameMode.ADVENTURE)
		{
			return;
		}

		Block block = event.getBlock();
		Material material = block.getType();

		for (Material allowed : Config.PLACEABLE_BLOCKS)
		{
			if (material == allowed)
			{
				event.setCancelled(false);
				return;
			}
		}

	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		Player player = event.getPlayer();
		if (player.getGameMode() == GameMode.CREATIVE)
		{
			event.setCancelled(false);
			return;
		}

		event.setCancelled(true);

		if (player.getGameMode() == GameMode.ADVENTURE)
		{
			return;
		}

		Block block = event.getBlock();
		Material material = block.getType();
		boolean canBreak = false;

		for (Material allowed : Config.BREAKABLE_BLOCKS)
		{
			if (material == allowed)
			{
				canBreak = true;
				break;
			}
		}

		if (canBreak)
		{
			if (material == Material.BROWN_MUSHROOM || material == Material.RED_MUSHROOM)
			{
				block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(material, 1));
			}
			else
			{
				block.setType(Material.AIR);
			}
		}

	}
}
