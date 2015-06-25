package com.kiro.sg.utils.task;

import com.kiro.sg.SGMain;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;

public class TemporaryBlockTask extends BukkitRunnable
{

	private final Location location;
	private final Material material;
	private final MaterialData data;

	public TemporaryBlockTask(Block block, int ticks, Material material)
	{
		this.location = block.getLocation();

		BlockState state = block.getState();

		this.material = block.getType();
		data = state.getData();

		block.setType(material);

		runTaskLater(SGMain.getPlugin(), ticks);
	}

	public Location getLocation()
	{
		return location;
	}

	public TemporaryBlockTask(Location location, int ticks, Material material)
	{
		this.location = location;

		Block block = location.getBlock();
		BlockState state = block.getState();

		this.material = block.getType();
		data = state.getData();

		block.setType(material);

		runTaskLater(SGMain.getPlugin(), ticks);
	}

	@Override
	public void run()
	{
		Block block = location.getBlock();
		block.setType(material);
		block.getState().setData(data);

	}
}
