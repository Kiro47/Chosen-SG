package com.kiro.sg.listeners;

import com.kiro.sg.voting.Voting;
import com.kiro.sg.voting.VotingMap;
import com.kiro.sg.voting.VotingMapRenderer;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class VotingListener
{

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		VotingMapRenderer.sendToPlayer(event.getPlayer());
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Voting.removeVote(event.getPlayer());
	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event)
	{
		if (event.getDamager().getType() == EntityType.PLAYER)
		{
			interact(event.getEntity(), (Player) event.getDamager());
		}
		event.setCancelled(true);

	}

	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent event)
	{
		interact(event.getRightClicked(), event.getPlayer());
		event.setCancelled(true);
	}

	@EventHandler
	public void onEntityInteract(PlayerInteractEvent event)
	{
		if (event.hasBlock())
		{
			Block block = event.getClickedBlock();
			if (block != null)
			{
				VotingMap map = VotingMap.Maps.get(block.getLocation());
				if (map != null)
				{
					map.onClick(event.getPlayer());
				}
			}
		}
	}

	private static void interact(Entity entity, Player player)
	{
		if (entity.getType() == EntityType.ITEM_FRAME)
		{
			VotingMap map = VotingMap.Maps.get(getLoc(entity.getLocation()));
			if (map != null)
			{
				map.onClick(player);
			}
		}
	}

	private static Location getLoc(Location loc)
	{
		return new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}

}
