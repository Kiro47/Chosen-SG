package com.kiro.sg.listeners;

import com.kiro.sg.game.GameInstance;
import com.kiro.sg.game.GameManager;
import com.kiro.sg.game.GameState;
import com.kiro.sg.utils.DamageTracker;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage implements Listener
{

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event)
	{

		if (event.getEntityType() != EntityType.PLAYER)
		{
			return;
		}

		Player player = (Player) event.getEntity();
		GameInstance instance = GameManager.getInstance(player);
		if (instance == null || instance.getState() == GameState.STARTING || player.getGameMode() == GameMode.ADVENTURE)
		{
			event.setCancelled(true);
			return;
		}

		if (instance.getState() != GameState.STARTING)
		{
			event.setCancelled(false);
			if (player.getGameMode() == GameMode.SURVIVAL && player.getHealth() - event.getFinalDamage() <= 0)
			{
				event.setCancelled(true);

				instance.playerDeath(player);
			}
		}
	}


	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event)
	{
		Entity entity = event.getEntity();
		if (entity.getType() == EntityType.PLAYER)
		{
			Player player = (Player) entity;
			if (player.getGameMode() == GameMode.SURVIVAL)
			{
				Entity damager = event.getDamager();

				if (damager instanceof Projectile)
				{
					damager = (Entity) ((Projectile) damager).getShooter();
				}

				if (damager.getType() == EntityType.PLAYER)
				{
					Player dmger = (Player) damager;
					if (dmger.getGameMode() == GameMode.SURVIVAL)
					{
						DamageTracker.set(player, dmger);
					}
					else
					{
						event.setCancelled(true);
					}
				}
			}
		}
	}

}
