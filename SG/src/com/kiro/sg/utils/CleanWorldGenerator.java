package com.kiro.sg.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CleanWorldGenerator extends ChunkGenerator
{
	private final int sx, sz;

	public CleanWorldGenerator(int sx, int sz)
	{
		this.sx = sx;
		this.sz = sz;
	}

	@Override
	public short[][] generateExtBlockSections(World world, Random random, int x, int z, BiomeGrid biomes)
	{
		int maxHeight = world.getMaxHeight();

		return new short[maxHeight / 16][];
	}

	@Override
	public List<BlockPopulator> getDefaultPopulators(World world)
	{
		return new ArrayList<>();
	}

	@Override
	public Location getFixedSpawnLocation(World world, Random random)
	{
		int cx = sx / 16;
		int cz = sz / 16;
		if (!world.isChunkLoaded(cx, cz))
		{
			world.loadChunk(cx, cz);
		}

		if (world.getHighestBlockYAt(sx, sz) < 0)
		{
			return new Location(world, sx, 64, sz);
		}

		return new Location(world, cx, world.getHighestBlockYAt(sx, sz), cz);
	}
}
