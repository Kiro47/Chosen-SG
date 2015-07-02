package com.kiro.sg.utils.misc;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public final class ItemUtils
{

	private static final List<Material> DOOR_LIST;

	static
	{
		DOOR_LIST = new ArrayList<>();

		for (Material material : Material.values())
		{
			if (material.name().endsWith("_DOOR"))
			{
				DOOR_LIST.add(material);
			}
		}
	}

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

	public static boolean isDoor(Material material)
	{
		switch (material)
		{
			case ACACIA_DOOR:
			case BIRCH_DOOR:
			case DARK_OAK_DOOR:
			case JUNGLE_DOOR:
			case SPRUCE_DOOR:
			case WOOD_DOOR:
			case IRON_DOOR:
			case WOODEN_DOOR:
				return true;
		}
		return false;
	}

	public static boolean isDoorClosed(byte data)
	{
		return data < 4;
	}

	public static boolean isDoorOpen(byte data)
	{
		return data >= 4;
	}

	public static boolean isTopHalf(byte data)
	{
		return data == 8;
	}

	public static BlockFace getDoorDirection(byte data)
	{
		switch (data)
		{
			case 0:
			case 7:
				return BlockFace.WEST;
			case 1:
			case 4:
				return BlockFace.NORTH;
			case 2:
			case 5:
				return BlockFace.EAST;
			case 3:
			case 6:
				return BlockFace.SOUTH;
		}
		return BlockFace.UP;
	}

	public static ItemStack nameItem(ItemStack stack, String name)
	{
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(name);

		stack.setItemMeta(meta);

		return stack;
	}


}
