package com.kiro.sg.nms;

import net.minecraft.server.v1_8_R3.WorldBorder;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class BorderUtils
{

	private static Field worldBorder_field;
	private static Field wbHandle_field;
	private static Class<?> worldBorder_class;

	public static void init()
	{
		try
		{
			worldBorder_class = ReflectionUtils.getNMS("WorldBorder");
			worldBorder_field = ReflectionUtils.findField(ReflectionUtils.getNMS("World"), worldBorder_class);
			worldBorder_field.setAccessible(true);
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(worldBorder_field, worldBorder_field.getModifiers() & ~Modifier.FINAL);

			Class<?> clz = Bukkit.getWorlds().get(0).getWorldBorder().getClass();
			wbHandle_field = clz.getDeclaredField("handle");
			wbHandle_field.setAccessible(true);
			modifiersField.setInt(wbHandle_field, wbHandle_field.getModifiers() & ~Modifier.FINAL);


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
			final Object handle = ReflectionUtils.getNMS(world);

			Object wb = worldBorder_class.newInstance();
			worldBorder_field.set(handle, wb);
			wbHandle_field.set(world.getWorldBorder(), wb);

			((WorldBorder) wb).a(new WorldBorderListener(world, (WorldBorder) wb));

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}


}
