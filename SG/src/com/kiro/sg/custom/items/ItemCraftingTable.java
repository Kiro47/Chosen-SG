package com.kiro.sg.custom.items;

import com.kiro.sg.custom.CustomItem;
import com.kiro.sg.utils.chat.Msg;
import com.kiro.sg.utils.effects.ParticleEffects;
import com.kiro.sg.utils.misc.ItemUtils;
import com.kiro.sg.utils.task.ParticleSpiralTask;
import com.kiro.sg.utils.task.TemporaryBlockTask;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class ItemCraftingTable extends CustomItem
{
	@Override
	public Material material()
	{
		return Material.WORKBENCH;
	}

	@Override
	public String itemName()
	{
		return "Portable Workbench";
	}

	@Override
	public boolean useItem(Player owner, Action action, Block block, BlockFace face)
	{
		if (action == Action.RIGHT_CLICK_BLOCK)
		{
			if (face == BlockFace.UP || !block.getType().isSolid())
			{
				ItemUtils.removeHeldItem(owner);
				final TemporaryBlockTask task;
				if (!block.getType().isSolid())
				{
					task = new TemporaryBlockTask(block, 300, Material.WORKBENCH);
				}
				else
				{
					Location location = block.getLocation();
					location.add(0, 1, 0);
					task = new TemporaryBlockTask(location, 300, Material.WORKBENCH);
				}

				Location location = task.getLocation().clone().add(0.5, 0, 0.5);

				//Entity entity, int time, int split, double wrapps, int height, int points, double radius, Effect effect
				ParticleSpiralTask pst = new ParticleSpiralTask(location, 10, 5, 1, 1, 10, 0.75, ParticleEffects.ParticleType.HAPPY_VILLAGER);

				pst.executeAfter(new Runnable()
				{
					@Override
					public void run()
					{
						task.exe();
					}
				});

				Msg.msgPlayer(owner, ChatColor.GREEN + "You have placed a portable workbench");

				return true;
			}
			Msg.msgPlayer(owner, ChatColor.RED + "You can't place that on the wall");
		}
		return false;
	}
}
