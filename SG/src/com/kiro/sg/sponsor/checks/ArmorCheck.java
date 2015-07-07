package com.kiro.sg.sponsor.checks;

import com.kiro.sg.sponsor.SponsorCheck;
import com.kiro.sg.utils.chat.Msg;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ArmorCheck implements SponsorCheck
{

	@Override
	public int weight()
	{
		return 2;
	}

	@Override
	public boolean checkAndExecute(Player player)
	{
		PlayerInventory inventory = player.getInventory();
		if (inventory.getHelmet().getType() != Material.IRON_HELMET)
		{
			inventory.addItem(new ItemStack(Material.IRON_HELMET));
			Msg.msgPlayer(player, ChatColor.DARK_GREEN + "you have been given an " + ChatColor.GREEN + "Iron Helmet");
			return true;
		}
		if (inventory.getBoots().getType() != Material.IRON_BOOTS)
		{
			inventory.addItem(new ItemStack(Material.IRON_BOOTS));
			Msg.msgPlayer(player, ChatColor.DARK_GREEN + "you have been given " + ChatColor.GREEN + "Iron Boots");
			return true;
		}
		if (inventory.getLeggings().getType() != Material.IRON_LEGGINGS)
		{
			inventory.addItem(new ItemStack(Material.IRON_LEGGINGS));
			Msg.msgPlayer(player, ChatColor.DARK_GREEN + "you have been given " + ChatColor.GREEN + "Iron Leggings");
			return true;
		}
		if (inventory.getChestplate().getType() != Material.IRON_CHESTPLATE)
		{
			inventory.addItem(new ItemStack(Material.IRON_CHESTPLATE));
			Msg.msgPlayer(player, ChatColor.DARK_GREEN + "you have been given an " + ChatColor.GREEN + "Iron Chestplate");
			return true;
		}
		return false;
	}
}
