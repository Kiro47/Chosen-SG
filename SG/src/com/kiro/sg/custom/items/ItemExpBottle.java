package com.kiro.sg.custom.items;

import com.kiro.sg.custom.CustomItem;
import com.kiro.sg.utils.misc.ItemUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class ItemExpBottle extends CustomItem
{
	@Override
	public Material material()
	{
		return Material.EXP_BOTTLE;
	}

	@Override
	public String itemName()
	{
		return "bottle O' Enchanting";
	}

	@Override
	public boolean useItem(Player owner, Action action, Block block, BlockFace face)
	{
		ItemUtils.removeHeldItem(owner);
		owner.setLevel(owner.getLevel() + 1);
		return true;
	}
}
