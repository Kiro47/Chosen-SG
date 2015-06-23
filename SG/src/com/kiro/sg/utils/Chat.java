
package com.kiro.sg.utils;

import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

public final class Chat
{

	private static final int[] FONT_SIZES =
			{1, 9, 9, 8, 8, 8, 8, 7, 9, 8, 9, 9, 8, 9, 9, 9, 8, 8, 8, 8, 9, 9, 8, 9, 8, 8, 8, 8, 8, 9, 9, 9, 4, 2, 5, 6, 6, 6, 6, 3, 5, 5, 5, 6, 2, 6, 2, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 2, 2, 5, 6, 5, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 4, 6, 6, 3, 6, 6, 6, 6, 6, 5, 6, 6, 2, 6, 5, 3, 6, 6, 6, 6, 6, 6, 6, 4, 6, 6, 6, 6, 6, 6, 5, 2, 5, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 3, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 6, 3, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 6, 2, 6, 6, 8, 9, 9, 6, 6, 6, 8, 8, 6, 8, 8, 8, 8, 8, 6, 6, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 6, 9, 9, 9, 5, 9, 9, 8, 7, 7, 8, 7, 8, 8, 8, 7, 8, 8, 7, 9, 9, 6, 7, 7, 7, 7, 7, 9, 6, 7, 8, 7, 6, 6, 9, 7, 6, 7, 1};

	private static final int CHAT_WINDOW_WIDTH = 320;
	private static final Pattern COMPILE = Pattern.compile("&([0-9a-f|k|l|m|n|o|r])");
	private static final Pattern PATTERN = Pattern.compile("(&|\247)([0-9a-f|k|l|m|n|o|r])");
	// private static final int CHAT_STRING_LENGTH = 119;

	private static String ALLOWED_CHARACTERS;

	public static void init()
	{
		String packageName = Bukkit.getServer().getClass().getPackage().getName();
		String[] packageSplit = packageName.split("\\.");
		String version = packageSplit[packageSplit.length - 1];
		try
		{
			Class<?> nmsClass = Class.forName("net.minecraft.server." + version + ".SharedConstants");
			Field field = nmsClass.getField("allowedCharacters");
			ALLOWED_CHARACTERS = field.get(null).toString();

		}
		catch (Exception e)
		{
			if (e instanceof ClassNotFoundException)
			{
				System.out.println("No such class!");
			}
		}
	}

	public static String center(String s)
	{
		int width = stringWidth(s);
		int size = CHAT_WINDOW_WIDTH / 2 - width / 2;
		width = stringWidth(" ");
		int count = size / width;
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < count; i++)
		{
			builder.append(' ');
		}
		return builder.append(s).toString();
	}

	public static int charWidth(char s)
	{
		int index = ALLOWED_CHARACTERS.indexOf(s);
		if (index == -1)
		{
			return 9;
		}
		else
		{
			return Chat.FONT_SIZES[index + 32];
		}
	}

	public static String fill(String filler)
	{
		int width = stringWidth(filler);
		int count = CHAT_WINDOW_WIDTH / width;
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < count; i++)
		{
			builder.append(filler);
		}

		return builder.toString();
	}

	public static String format(String in)
	{
		if (in == null)
		{
			return "";
		}
		else
		{
			return COMPILE.matcher(in).replaceAll("\247$1");
		}
	}

	public static int stringWidth(String s)
	{
		if (s == null)
		{
			return 0;
		}
		s = PATTERN.matcher(s).replaceAll("");
		int size = 0;
		for (int index = 0; index < s.length(); index++)
		{
			char ch = s.charAt(index);
			size += Chat.charWidth(ch);
		}

		return size;
	}

}
