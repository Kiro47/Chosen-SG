package com.kiro.sg.listeners;

import com.kiro.sg.custom.events.PlayerDamageByPlayerEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

public class CustomEventsListener implements Listener
{

	private final PluginManager pluginManager;

	public CustomEventsListener()
	{
		pluginManager = Bukkit.getPluginManager();
	}

	/**
	 * Checks for PlayerDamageByPlayerEvent <- only fires if players are in a match.
	 */
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
	{
		Entity entity = event.getEntity();
		if (entity.getType() == EntityType.PLAYER)
		{
			Player player = (Player) entity;
			if (player.getGameMode() == GameMode.SURVIVAL) // only time to be in survival mode is if you're in a game.
			{
				Material itemUsed = null;

				Entity damager = event.getDamager();

				if (damager instanceof Projectile)
				{
					if (damager.getType() == EntityType.ARROW)
					{
						itemUsed = Material.ARROW;
					}
					else if (damager.getType() == EntityType.SNOWBALL)
					{
						itemUsed = Material.SNOW_BALL;
					}
					damager = (Entity) ((Projectile) damager).getShooter();
				}

				if (damager.getType() == EntityType.PLAYER)
				{
					Player dmger = (Player) damager;
					if (itemUsed == null)
					{
						ItemStack hand = dmger.getItemInHand();
						if (hand != null)
						{
							itemUsed = hand.getType();
						}
					}

					PlayerDamageByPlayerEvent evt = new PlayerDamageByPlayerEvent(player, dmger, itemUsed);

					pluginManager.callEvent(evt);
					
					event.setCancelled(evt.isCancelled());
				}
			}
		}
	}

}
