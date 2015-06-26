package com.kiro.sg.listeners;

import com.kiro.sg.game.GameInstance;
import com.kiro.sg.game.lobby.LobbyManager;
import com.kiro.sg.utils.Meta;
import com.kiro.sg.game.lobby.voting.Voting;
import com.kiro.sg.game.lobby.voting.VotingMap;
import com.kiro.sg.game.lobby.voting.VotingMapRenderer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LobbyListener implements Listener
{

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		VotingMapRenderer.sendToPlayer(event.getPlayer());
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		GameInstance game = (GameInstance) Meta.getMetadata(player, "game");
		if (game != null)
		{
			game.removePlayer(player);
			Meta.removeMetadata(player, "game");

			player.getInventory().clear();
			player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
		}
		else
		{
			LobbyManager.getInstance().removeFromQueue(player);
			Voting.removeVote(player);
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event)
	{
		if (event.getDamager().getType() == EntityType.PLAYER)
		{
			if (interact(event.getEntity(), (Player) event.getDamager()))
			{
				event.setCancelled(true);
			}
		}

	}

	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent event)
	{
		if (interact(event.getRightClicked(), event.getPlayer()))
		{
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityInteract(PlayerInteractEvent event)
	{
		if (!Meta.has(event.getPlayer(), "game"))
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
	}

	private static boolean interact(Entity entity, Player player)
	{
		if (entity.getType() == EntityType.ITEM_FRAME)
		{
			if (!Meta.has(player, "game"))
			{
				VotingMap map = VotingMap.Maps.get(getLoc(entity.getLocation()));
				if (map != null)
				{
					map.onClick(player);
					return true;
				}
			}
		}
		return false;
	}

	private static Location getLoc(Location loc)
	{
		return new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}

}
