package com.kiro.sg.listeners.lobby;

import com.kiro.sg.lobby.GameSign;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class StatusSignListener implements Listener
{

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		Block block = event.getClickedBlock();
		if (block != null && (block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN))
		{
			Location location = block.getLocation();
			GameSign sign = GameSign.getSign(location);
			if (sign != null)
			{
				sign.onClick(event.getPlayer());
			}
		}
	}

}
