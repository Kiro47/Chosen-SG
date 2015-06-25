package com.kiro.sg.utils.misc;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public final class ItemUtils
{
	private ItemUtils()
	{
	}

	/**
	 * removes a single item from a players hand
	 */
	public static void removeHeldItem(Player player)
	{
		PlayerInventory inventory = (PlayerInventory) player.getInventory();
		int slot = inventory.getHeldItemSlot();
		ItemStack[] contents = inventory.getContents();

		ItemStack stack = contents[slot];
		if (stack != null && stack.getAmount() > 1)
		{
			stack.setAmount(stack.getAmount() - 1);
		}
		else
		{
			contents[slot] = null;
		}

		inventory.setContents(contents);
	}

	/**
	 * @param stack  the ItemStack to diminish the usages of
	 * @param amount the percentage to diminish [0-1]
	 * @return the edited Item Stack
	 */
	public static ItemStack diminishUsages(ItemStack stack, float amount)
	{
		Material material = stack.getType();
		short max = material.getMaxDurability();
		short durability = stack.getDurability();

		durability += max * amount;

		if (durability >= max)
		{
			return null;
		}

		stack.setDurability(durability);
		return stack;
	}

}
