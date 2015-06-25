package com.kiro.sg.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class SpectatorListner implements Listener
{

	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent event)
	{
		if (event.getPlayer().getGameMode() == GameMode.ADVENTURE)
		{
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event)
	{
		if (event.getPlayer().getGameMode() == GameMode.ADVENTURE)
		{
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.getAction() == Action.PHYSICAL)
		{
			if (event.getPlayer().getGameMode() == GameMode.ADVENTURE)
			{
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event)
	{
		if (event.getEntityType() == EntityType.PLAYER)
		{
			if (((HumanEntity) event.getEntity()).getGameMode() == GameMode.ADVENTURE)
			{
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onHungerDamage(FoodLevelChangeEvent event)
	{
		if (event.getEntityType() == EntityType.PLAYER)
		{
			if (event.getEntity().getGameMode() == GameMode.ADVENTURE)
			{
				event.setFoodLevel(20);
			}
		}
	}

}
