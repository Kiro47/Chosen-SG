package com.kiro.sg.custom.items;

import com.kiro.sg.custom.CustomItem;
import com.kiro.sg.utils.misc.ItemUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class ItemFishingRod extends CustomItem
{
	@Override
	public Material material()
	{
		return Material.FISHING_ROD;
	}

	@Override
	public String itemName()
	{
		return "Fishing Rod";
	}

	@Override
	public void useItem(Player owner, Action action, Block block, BlockFace face)
	{
		ItemStack stack = ItemUtils.diminishUsages(owner.getItemInHand(), 0.1f);
		owner.setItemInHand(stack);
	}

	@Override
	public void useItemOnPlayer(Player owner, Player on)
	{
		Vector vec1 = owner.getLocation().toVector();
		Vector vec2 = on.getLocation().toVector();

		Vector vector = vec1.subtract(vec2);
		vector.setY(0.4);

		on.setVelocity(vector);
	}
}
