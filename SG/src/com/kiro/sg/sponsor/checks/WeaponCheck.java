package com.kiro.sg.sponsor.checks;

import com.kiro.sg.sponsor.SponsorCheck;
import com.kiro.sg.utils.chat.Msg;
import com.kiro.sg.utils.misc.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class WeaponCheck implements SponsorCheck
{

	@Override
	public int weight()
	{
		return 3;
	}

	@Override
	public boolean checkAndExecute(Player player)
	{
		PlayerInventory inventory = player.getInventory();


		Material material = null;
		boolean hasType = false;
		boolean hasStick = false;

		if (inventory.contains(Material.DIAMOND, 2))
		{
			hasType = true;
			material = Material.DIAMOND;
		}
		else if (inventory.contains(Material.IRON_INGOT, 2))
		{
			hasType = true;
			material = Material.IRON_INGOT;
		}

		if (inventory.contains(Material.DIAMOND, 1))
		{
			material = Material.DIAMOND;
		}
		else if (inventory.contains(Material.IRON_INGOT, 1))
		{
			material = Material.IRON_INGOT;
		}

		if (inventory.contains(Material.STICK, 1))
		{
			hasStick = true;
		}

		if (inventory.contains(Material.IRON_SWORD) && material == Material.IRON_INGOT)
		{
			material = null;
		}

		if (hasType && hasStick)
		{
			ItemStack stack = new ItemStack(Material.WORKBENCH, 1);
			ItemUtils.nameItem(stack, "Portable Workbench");

			player.getInventory().addItem(stack);
			Msg.msgPlayer(player, ChatColor.DARK_GREEN + "you have been given a " + ChatColor.GREEN + "Portal Workbench");
		}
		else if (hasType && !hasStick)
		{
			player.getInventory().addItem(new ItemStack(Material.STICK, 1));
			Msg.msgPlayer(player, ChatColor.DARK_GREEN + "you have been given a " + ChatColor.GREEN + "Stick");
		}
		else if (material != null && hasStick)
		{
			player.getInventory().addItem(new ItemStack(material, 1));
			if (material == Material.DIAMOND)
			{
				Msg.msgPlayer(player, ChatColor.DARK_GREEN + "you have been given a " + ChatColor.GREEN + "Diamond");
			}
			else
			{
				Msg.msgPlayer(player, ChatColor.DARK_GREEN + "you have been given an " + ChatColor.GREEN + "Iron Ingot");
			}
		}

		return false;
	}

}
