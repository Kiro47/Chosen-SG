package com.kiro.sg.game.spectators;

import com.kiro.sg.game.GameInstance;
import com.kiro.sg.utils.chat.ChatUtils;
import com.kiro.sg.utils.misc.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class CompassMenu implements InventoryHolder
{

	private final GameInstance game;
	private final Inventory inv;

	public CompassMenu(GameInstance game)
	{
		this.game = game;
		int count = game.getRemaining().size();
		int rows = (int) Math.ceil(count / 9.0);

		inv = Bukkit.createInventory(this, rows * 9, "Player Menu");
	}

	public void update()
	{
		inv.clear();

		int count = game.getRemaining().size();
		for (int i = 0; i < count; i++)
		{
			Player player = game.getRemaining().get(i);

			ItemStack stack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			ItemMeta meta = stack.getItemMeta();
			if (meta instanceof SkullMeta)
			{
				SkullMeta skull = (SkullMeta) meta;
				skull.setOwner(player.getName());
				skull.setDisplayName(player.getDisplayName());
			}

			stack.setItemMeta(meta);

			inv.setItem(i, stack);
		}
	}

	public void onOpen(Player player)
	{
		player.openInventory(inv);
	}

	public boolean onClick(Player player, int slot)
	{
		if (slot >= 0 && slot <= inv.getSize())
		{
			int count = game.getRemaining().size();
			if (slot < count)
			{
				Player p = game.getRemaining().get(slot);
				player.teleport(p);
			}

			return true;
		}
		return false;
	}

	@Override
	public Inventory getInventory()
	{
		return inv;
	}

	public static ItemStack getCompassItem()
	{
		ItemStack stack = new ItemStack(Material.COMPASS);
		stack = ItemUtils.nameItem(stack, ChatUtils.format("&2Player Menu"));
		return stack;
	}

	public void dispose()
	{
		inv.clear();
	}
}
