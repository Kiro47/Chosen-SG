package com.kiro.sg.nms;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.entity.Player;

public class WorldBorderListener implements IWorldBorderListener
{
	private final World nmsWorld;
	private final WorldBorder worldBorder;
	private final org.bukkit.World world;

	public WorldBorderListener(org.bukkit.World world, WorldBorder worldBorder)
	{
		nmsWorld = (World) ReflectionUtils.getNMS(world);
		this.worldBorder = worldBorder;
		this.world = world;
	}

	// SET SIZE
	@Override
	public void a(WorldBorder worldBorder, double v)
	{
		final PacketPlayOutWorldBorder packet = new PacketPlayOutWorldBorder(worldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_SIZE);

		for (Player player : world.getPlayers())
		{
			((EntityPlayer) ReflectionUtils.getNMS(player)).playerConnection.sendPacket(packet);
		}

	}

	// LERP SIZE
	@Override
	public void a(WorldBorder worldBorder, double v, double v1, long l)
	{
		final PacketPlayOutWorldBorder packet = new PacketPlayOutWorldBorder(worldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.LERP_SIZE);

		for (Player player : world.getPlayers())
		{
			((EntityPlayer) ReflectionUtils.getNMS(player)).playerConnection.sendPacket(packet);
		}
	}

	// SET CENTER
	@Override
	public void a(WorldBorder worldBorder, double v, double v1)
	{
		final PacketPlayOutWorldBorder packet = new PacketPlayOutWorldBorder(worldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_CENTER);

		for (Player player : world.getPlayers())
		{
			((EntityPlayer) ReflectionUtils.getNMS(player)).playerConnection.sendPacket(packet);
		}
	}

	// SET WARNING TIME
	@Override
	public void a(WorldBorder worldBorder, int i)
	{
		final PacketPlayOutWorldBorder packet = new PacketPlayOutWorldBorder(worldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_WARNING_TIME);

		for (Player player : world.getPlayers())
		{
			((EntityPlayer) ReflectionUtils.getNMS(player)).playerConnection.sendPacket(packet);
		}
	}

	// SET WARNING BLOCKS
	@Override
	public void b(WorldBorder worldBorder, int i)
	{
		final PacketPlayOutWorldBorder packet = new PacketPlayOutWorldBorder(worldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_WARNING_BLOCKS);

		for (Player player : world.getPlayers())
		{
			((EntityPlayer) ReflectionUtils.getNMS(player)).playerConnection.sendPacket(packet);
		}
	}

	// SET DAMAGE BUFFER
	@Override
	public void b(WorldBorder worldBorder, double v)
	{

	}

	// SET DAMAGE AMOUNT
	@Override
	public void c(WorldBorder worldBorder, double v)
	{

	}
}
