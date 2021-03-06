package com.kiro.sg.game.crates;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class SupplyCrate implements InventoryHolder
{

	private final Inventory inventory;

	public SupplyCrate()
	{
		inventory = Bukkit.createInventory(this, 9, "Supply Crate");
	}

	public void populate(int mod)
	{
		inventory.setContents(CrateUtils.createContents(9, mod));
	}

	public void open(Player player)
	{
		player.openInventory(inventory);
	}

	@Override
	public Inventory getInventory()
	{
		return inventory;
	}
}
