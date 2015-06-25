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

public class ItemNetherstar extends CustomItem
{
	@Override
	public Material material()
	{
		return Material.NETHER_STAR;
	}

	@Override
	public String itemName()
	{
		return "Super heal";
	}

	@Override
	public void useItem(Player owner, Action action, Block block, BlockFace face)
	{
		ItemUtils.removeHeldItem(owner);
		double max = owner.getMaxHealth();

		owner.setHealth(Math.min(owner.getHealth() + max * 0.5, max));

		//owner.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10, 1));

		Msg.msgPlayer(owner, ChatColor.GREEN + "Some of your health has been restored!");
	}
}
