package com.kiro.sg.custom.items;

import com.kiro.sg.custom.CustomItem;
import com.kiro.sg.utils.Meta;
import com.kiro.sg.utils.task.CompassTrackerTask;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;

public class ItemCompass extends CustomItem
{

	public static void remove(Player player)
	{
		BukkitTask task = (BukkitTask) Meta.getMetadata(player, "tracker");
		if (task != null)
		{
			task.cancel();
			Meta.removeMetadata(player, "tracker");
		}
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
		ItemStack stack = owner.getItemInHand();
		ItemMeta meta = stack.getItemMeta();

		if (!meta.getDisplayName().contains("Active!"))
		{
			Meta.setMetadata(owner, "tracker", new CompassTrackerTask(owner));
			meta.setDisplayName(ChatColor.GREEN + "Active!");
			stack.setItemMeta(meta);
			owner.setItemInHand(stack);
		}
		return false;
	}
}
