package com.kiro.sg.listeners.spectator;

import com.kiro.sg.game.GameInstance;
import com.kiro.sg.game.GameManager;
import com.kiro.sg.game.spectators.CompassMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class SpectatorMenuListener implements Listener
{

	@EventHandler
	public void onPlayerClick(InventoryClickEvent event)
	{
		if (event.getSlotType() == InventoryType.SlotType.CONTAINER && "Player Menu".equals(event.getInventory().getName()))
		{
			event.setResult(Event.Result.DENY);

			Player player = (Player) event.getWhoClicked();

			GameInstance instance = GameManager.getInstance(player);
			if (instance != null)
			{
				CompassMenu menu = instance.getCompassMenu();
				menu.onClick(player, event.getSlot());
			}
		}
	}

}
