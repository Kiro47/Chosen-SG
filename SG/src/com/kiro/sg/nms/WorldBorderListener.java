package com.kiro.sg.nms;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IWorldBorderListener;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_8_R3.WorldBorder;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class WorldBorderListener implements IWorldBorderListener
{
	private final World world;

	public WorldBorderListener(World world)
	{
		this.world = world;
	}

	// SET SIZE
	@Override
	public void a(WorldBorder worldBorder, double v)
	{
		final PacketPlayOutWorldBorder packet = new PacketPlayOutWorldBorder(worldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_SIZE);

		for (Player player : world.getPlayers())
		{
			((EntityPlayer) RefUtils.getNMS(player)).playerConnection.sendPacket(packet);
		}

	}

	// LERP SIZE
	@Override
	public void a(WorldBorder worldBorder, double v, double v1, long l)
	{
		final PacketPlayOutWorldBorder packet = new PacketPlayOutWorldBorder(worldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.LERP_SIZE);

		for (Player player : world.getPlayers())
		{
			((EntityPlayer) RefUtils.getNMS(player)).playerConnection.sendPacket(packet);
		}
	}

	// SET CENTER
	@Override
	public void a(WorldBorder worldBorder, double v, double v1)
	{
		final PacketPlayOutWorldBorder packet = new PacketPlayOutWorldBorder(worldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_CENTER);

		for (Player player : world.getPlayers())
		{
			((EntityPlayer) RefUtils.getNMS(player)).playerConnection.sendPacket(packet);
		}
	}

	// SET WARNING TIME
	@Override
	public void a(WorldBorder worldBorder, int i)
	{
		final PacketPlayOutWorldBorder packet = new PacketPlayOutWorldBorder(worldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_WARNING_TIME);

		for (Player player : world.getPlayers())
		{
			((EntityPlayer) RefUtils.getNMS(player)).playerConnection.sendPacket(packet);
		}
	}

	// SET WARNING BLOCKS
	@Override
	public void b(WorldBorder worldBorder, int i)
	{
		final PacketPlayOutWorldBorder packet = new PacketPlayOutWorldBorder(worldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_WARNING_BLOCKS);

		for (Player player : world.getPlayers())
		{
			((EntityPlayer) RefUtils.getNMS(player)).playerConnection.sendPacket(packet);
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
