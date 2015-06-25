package com.kiro.sg.custom.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Only fired while players are in game.
 */
public class PlayerDamageByPlayerEvent extends Event implements Cancellable
{
	private static final HandlerList handlerList = new HandlerList();

	private final Player player;
	private final Player damager;
	private final Material itemUsed;
	private boolean isCancelled;

	public PlayerDamageByPlayerEvent(Player player, Player damager, Material itemUsed)
	{
		this.player = player;
		this.damager = damager;
		this.itemUsed = itemUsed;
	}

	public Material getItemUsed()
	{
		return itemUsed;
	}

	public Player getPlayer()
	{
		return player;
	}

	public Player getDamager()
	{
		return damager;
	}

	@Override
	public boolean isCancelled()
	{
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean cancel)
	{
		isCancelled = cancel;
	}

	public static HandlerList getHandlerList()
	{
		return handlerList;
	}

	@Override
	public HandlerList getHandlers()
	{
		return handlerList;
	}
}
