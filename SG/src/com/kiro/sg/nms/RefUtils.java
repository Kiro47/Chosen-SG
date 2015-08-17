package com.kiro.sg.nms;

import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class RefUtils
{

	public static String NMS_VERSION;

	public static void getVersion()
	{
		try
		{
			String pack = Bukkit.getServer().getClass().getPackage().getName();

			NMS_VERSION = pack.substring(pack.lastIndexOf('.') + 1, pack.length());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static Object getNMS(Object obj)
	{
		try
		{
			Class<?> clz = obj.getClass();

			Method method = clz.getMethod("getHandle", null);

			return method.invoke(obj);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static Class<?> getNMS(String className)
	{
		try
		{
			return Class.forName("net.minecraft.server." + NMS_VERSION + '.' + className);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static Field findField(Class<?> clz, Class<?> type)
	{
		try
		{
			Field[] fields = clz.getDeclaredFields();
			for (Field field : fields)
			{
				if (field.getType().equals(type))
				{
					return field;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}


}
