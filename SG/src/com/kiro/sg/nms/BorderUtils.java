package com.kiro.sg.nms;

import net.minecraft.server.v1_8_R3.WorldBorder;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.lang.reflect.Field;

public class BorderUtils
{

	private static Field worldBorder_field;
	private static Field wbHandle_field;
	private static Class<?> worldBorder_class;

	public static void init()
	{
		try
		{
			worldBorder_class = RefUtils.getNMS("WorldBorder");
			worldBorder_field = RefUtils.findField(RefUtils.getNMS("World"), worldBorder_class);
			worldBorder_field.setAccessible(true);
			RefUtils.removeFinal(worldBorder_field);

			Class<?> clz = Bukkit.getWorlds().get(0).getWorldBorder().getClass();
			wbHandle_field = clz.getDeclaredField("handle");
			wbHandle_field.setAccessible(true);
			RefUtils.removeFinal(wbHandle_field);


		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void assignNew(World world)
	{
		try
		{
			final Object handle = RefUtils.getNMS(world);

			Object wb = worldBorder_class.newInstance();
			worldBorder_field.set(handle, wb);
			wbHandle_field.set(world.getWorldBorder(), wb);

			((WorldBorder) wb).a(new WorldBorderListener(world));

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}


}
