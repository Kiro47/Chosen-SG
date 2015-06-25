package com.kiro.sg.custom.items;

import com.kiro.sg.custom.CustomItem;
import com.kiro.sg.utils.effects.ParticleEffects;
import com.kiro.sg.utils.task.ParticleSpiralTask;
import com.kiro.sg.utils.task.TemporaryBlockTask;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class ItemCarePackage extends CustomItem
{


	@Override
	public Material material()
	{
		return Material.ENDER_CHEST;
	}

	@Override
	public String itemName()
	{
		return "Care Package";
	}

	@Override
	public void useItem(Player owner, Action action, Block block, BlockFace face)
	{
		if (action == Action.RIGHT_CLICK_BLOCK)
		{
			if (face == BlockFace.UP)
			{
				TemporaryBlockTask task;
				if (block.getType().isSolid())
				{
					task = new TemporaryBlockTask(block, 200, Material.ENDER_CHEST);
				}
				else
				{
					Location location = block.getLocation();
					location.add(0, 1, 0);
					task = new TemporaryBlockTask(block, 200, Material.ENDER_CHEST);
				}

				Location location = task.getLocation().clone().add(0.5, 0, 0.5);

				//Entity entity, int time, int split, double wrapps, int height, int points, double radius, Effect effect
				new ParticleSpiralTask(location, 20, 3, 0.5, 1, 5, 0.75, ParticleEffects.ParticleType.HAPPY_VILLAGER);
			}
		}
	}
}
