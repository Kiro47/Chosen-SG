package com.kiro.sg.custom.items;

import com.kiro.sg.custom.CustomItem;
import com.kiro.sg.utils.Meta;
import com.kiro.sg.utils.chat.Msg;
import com.kiro.sg.utils.task.CompassTrackerTask;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class ItemCompass extends CustomItem
{

	public static void remove(Player player)
	{
		BukkitRunnable task = (BukkitRunnable) Meta.getMetadata(player, "tracker");
		if (task != null)
		{
			task.cancel();
			Meta.removeMetadata(player, "tracker");
		}
	}

	public static ItemStack reset(ItemStack stack)
	{
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "&4Activate!");
		stack.setItemMeta(meta);

		return stack;

	}

	@Override
	public Material material()
	{
		return Material.COMPASS;
	}

	@Override
	public String itemName()
	{
		return "Activated!";
	}

	@Override
	public boolean useItem(Player owner, Action action, Block block, BlockFace face)
	{
		if (!Meta.has(owner, "tracker"))
		{
			ItemStack stack = owner.getItemInHand();
			ItemMeta meta = stack.getItemMeta();

			if (meta.getDisplayName() == null || !meta.getDisplayName().contains("Active!"))
			{
				Meta.setMetadata(owner, "tracker", new CompassTrackerTask(owner));
				meta.setDisplayName(ChatColor.GREEN + "Active!");
				stack.setItemMeta(meta);
				owner.setItemInHand(stack);

				Msg.msgPlayer(owner, ChatColor.GREEN + "You have activated your compass!");
			}
		}
		else
		{
			Msg.msgPlayer(owner, ChatColor.RED + "You have already activated a tracker!");
		}
		return false;
	}
}
