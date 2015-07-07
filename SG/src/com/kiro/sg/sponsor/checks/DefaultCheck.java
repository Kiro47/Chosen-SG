package com.kiro.sg.sponsor.checks;

import com.kiro.sg.sponsor.SponsorCheck;
import com.kiro.sg.utils.chat.Msg;
import com.kiro.sg.utils.misc.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class DefaultCheck implements SponsorCheck
{
	@Override
	public int weight()
	{
		return 1;
	}

	@Override
	public boolean checkAndExecute(Player player)
	{
		PlayerInventory inventory = player.getInventory();
		ItemStack stack;
		switch ((int) (Math.random() * 4))
		{
			case 0:
				stack = new ItemStack(Material.ENDER_CHEST, 1);
				ItemUtils.nameItem(stack, "Care Package");
				Msg.msgPlayer(player, ChatColor.DARK_GREEN + "you have been given a " + ChatColor.GREEN + "Care Package");
				break;
			case 2:
			case 1:
				stack = new ItemStack(Material.COMPASS, 1);
				ItemUtils.nameItem(stack, "Player Tracker" + ChatColor.RED + " (Activate!)");
				Msg.msgPlayer(player, ChatColor.DARK_GREEN + "you have been given a " + ChatColor.GREEN + "Player Tracker");
				break;
			case 3:
				stack = new ItemStack(Material.REDSTONE, 2);
				ItemUtils.nameItem(stack, "Heart");
				Msg.msgPlayer(player, ChatColor.DARK_GREEN + "you have been given " + ChatColor.GREEN + " 2 Redstone Hearts");
				break;
			default:
				stack = new ItemStack(Material.ENDER_CHEST, 1);
				ItemUtils.nameItem(stack, "Care Package");
				Msg.msgPlayer(player, ChatColor.DARK_GREEN + "you have been given a " + ChatColor.GREEN + "Care Package");
				break;
		}
		inventory.addItem(stack);
		return true;
	}
}
