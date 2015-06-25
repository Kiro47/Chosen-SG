package com.kiro.sg.custom.items;

import com.kiro.sg.custom.CustomItem;
import com.kiro.sg.utils.misc.EventUtils;
import com.kiro.sg.utils.misc.ItemUtils;
import com.kiro.sg.utils.chat.Msg;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class ItemHeart extends CustomItem
{
	@Override
	public Material material()
	{
		return Material.REDSTONE;
	}

	@Override
	public String itemName()
	{
		return "Heart";
	}

	@Override
	public boolean useItem(Player owner, Action action, Block block, BlockFace face)
	{
		if (EventUtils.isRightClick(action))
		{
			ItemUtils.removeHeldItem(owner);
			owner.setMaxHealth(owner.getMaxHealth() + 2.0);

			Msg.msgPlayer(owner, ChatColor.GREEN + "Your health has been increased by a heart!");
		}
		return true;
	}
}
