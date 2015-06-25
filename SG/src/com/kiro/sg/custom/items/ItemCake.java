package com.kiro.sg.custom.items;

import com.kiro.sg.custom.CustomItem;
import com.kiro.sg.utils.misc.ItemUtils;
import com.kiro.sg.utils.chat.Msg;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class ItemCake extends CustomItem
{
	@Override
	public Material material()
	{
		return Material.CAKE;
	}

	@Override
	public String itemName()
	{
		return "Cake";
	}

	@Override
	public void useItem(Player owner, Action action, Block block, BlockFace face)
	{
		owner.setFoodLevel(20);
		ItemUtils.removeHeldItem(owner);

		Msg.msgPlayer(owner, ChatColor.GREEN + "Your hunger has been refilled!");
	}
}
