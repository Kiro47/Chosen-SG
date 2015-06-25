package com.kiro.sg.custom;

import com.kiro.sg.custom.items.*;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomItem
{

	private static final List<CustomItem> ITEMS;

	static
	{
		ITEMS = new ArrayList<>();
		ITEMS.add(new ItemFishingRod());
		ITEMS.add(new ItemHeart());
		ITEMS.add(new ItemCake());
		ITEMS.add(new ItemNetherstar());
		ITEMS.add(new ItemCarePackage());
		ITEMS.add(new ItemSlowball());
		ITEMS.add(new ItemCompass());
	}

	public static CustomItem checkForItem(ItemStack stack)
	{
		for (CustomItem item : ITEMS)
		{
			if (item.isValidItem(stack))
			{
				return item;
			}
		}
		return null;
	}

	public abstract Material material();

	public abstract String itemName();

	public abstract boolean useItem(Player owner, Action action, Block block, BlockFace face);

	public void useItemOnPlayer(Player owner, Player on)
	{
		useItem(owner, null, null, null);
	}

	protected boolean isStrictMatch()
	{
		return false;
	}

	public boolean isValidItem(ItemStack stack)
	{
		boolean isValid = false;

		isValid = stack.getType() == material();

		if (isStrictMatch() && isValid)
		{
			ItemMeta meta = stack.getItemMeta();
			isValid = meta.getDisplayName().equals(itemName());
		}

		return isValid;
	}

}
