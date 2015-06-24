package com.kiro.sg.listeners;

import com.kiro.sg.game.GameInstance;
import com.kiro.sg.game.GameManager;
import com.kiro.sg.game.GameState;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage implements Listener
{

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e)
	{

		if (e.getEntityType() != EntityType.PLAYER)
		{
			return;
		}

		Player p = (Player) e.getEntity();
		GameInstance instance = GameManager.getInstance(p);
		if (instance == null || instance.getState() == GameState.STARTING)
		{
			e.setCancelled(true);
			return;
		}

		if (instance.getState() != GameState.STARTING)
		{
			if (p.getGameMode() == GameMode.SURVIVAL && p.getHealth() - e.getFinalDamage() <= 0)
			{
				e.setCancelled(false);
				instance.playerDeath(p);
			}
		}
	}

}
