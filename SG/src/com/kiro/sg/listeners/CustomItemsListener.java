package com.kiro.sg.listeners;

import com.kiro.sg.custom.CustomItem;
import com.kiro.sg.custom.events.PlayerDamageByPlayerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CustomItemsListener implements Listener
{

	@EventHandler
	public void onInteract(PlayerInteractEvent event)
	{
		if (event.hasItem())
		{
			Action action = event.getAction();
			if (action != Action.PHYSICAL)
			{
				ItemStack stack = event.getItem();
				CustomItem item = CustomItem.checkForItem(stack);
				if (item != null)
				{
					if (item.useItem(event.getPlayer(), event.getAction(), event.getClickedBlock(), event.getBlockFace()))
					{
						event.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	public void onEntityDamageByEntity(PlayerDamageByPlayerEvent event)
	{
		Player player = event.getDamager();
		ItemStack stack = player.getItemInHand();

		if (event.getItemUsed() != null)
		{
			stack = new ItemStack(event.getItemUsed());
		}

		CustomItem item = CustomItem.checkForItem(stack);
		if (item != null)
		{
			item.useItemOnPlayer(player, event.getPlayer());
		}
	}

}
