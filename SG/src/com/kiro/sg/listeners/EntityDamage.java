package com.kiro.sg.listeners;

import com.kiro.sg.custom.events.PlayerDamageByPlayerEvent;
import com.kiro.sg.game.GameInstance;
import com.kiro.sg.game.GameManager;
import com.kiro.sg.game.GameState;
import com.kiro.sg.utils.task.DamageTracker;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
	public void onEntityDamage(PlayerDamageByPlayerEvent event)
	{
		event.setCancelled(true);

		Player player = event.getPlayer();
		if (player.getGameMode() == GameMode.SURVIVAL)
		{
			Player damager = event.getDamager();
			if (damager.getGameMode() == GameMode.SURVIVAL)
			{
				DamageTracker.set(player, damager);
				event.setCancelled(false);
			}
		}
	}

}
