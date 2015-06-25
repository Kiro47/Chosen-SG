package com.kiro.sg.custom.items;

import com.kiro.sg.custom.CustomItem;
import com.kiro.sg.utils.chat.Msg;
import com.kiro.sg.utils.misc.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.block.Action;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemSlowball extends CustomItem
{
	@Override
	public Material material()
	{
		return Material.SNOW_BALL;
	}

	@Override
	public String itemName()
	{
		return "SlowBall";
	}

	@Override
	public boolean useItem(Player owner, Action action, Block block, BlockFace face)
	{
		owner.launchProjectile(Snowball.class);

		ItemUtils.removeHeldItem(owner);
		return false;
	}

	@Override
	public void useItemOnPlayer(Player owner, Player player)
	{
		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 3, 60));
		Msg.msgPlayer(player, ChatColor.RED + "You have been tagged by a slowball! You will be slow for 3 seconds.");
		Msg.msgPlayer(owner, ChatColor.GREEN + "You tagged " + player.getDisplayName() + " with a slowball!");
	}
}
