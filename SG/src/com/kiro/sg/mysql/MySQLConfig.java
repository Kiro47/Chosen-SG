package com.kiro.sg.mysql;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

final class MySQLConfig
{

	private static final int DEFAULT_MYSQL_PORT = 3306;

	public static String SQL_USERNAME;
	public static String SQL_PASSWORD;
	public static String SQL_DATABASE;

	public static String SQL_HOST;
	public static int SQL_PORT;

	public static boolean CENTRAL;
	public static String CENTRAL_SERVER;
	public static List<String> SERVERS;

	public static void loadConfiguration(File directory)
	{
		try
		{
			File file = new File(directory, "mysql.yml");
			if (!file.exists())
			{
				directory.mkdirs();
				file.createNewFile();
			}

			YamlConfiguration configuration = new YamlConfiguration();
			configuration.load(file);

			SQL_USERNAME = getString("sql.username", configuration, "user");
			SQL_PASSWORD = getString("sql.password", configuration, "pass");
			SQL_DATABASE = getString("sql.database", configuration, "minecraft");
			SQL_HOST = getString("sql.host", configuration, "host");
			SQL_PORT = getInt("sql.port", configuration, DEFAULT_MYSQL_PORT);

			configuration.save(file);

		}
		catch (Exception e)
		{
			Bukkit.getLogger().info("Couldn't load Configuration");
			e.printStackTrace();
		}
	}

	private static String getString(String path, YamlConfiguration config, String def)
	{
		while (true)
		{
			if (config.isSet(path))
			{
				return config.getString(path);
			}
			else
			{
				config.set(path, def);

			}
		}
	}

	private static int getInt(String path, YamlConfiguration config, int def)
	{
		while (true)
		{
			if (config.isSet(path))
			{
				return config.getInt(path);
			}
			else
			{
				config.set(path, def);

			}
		}
	}

}
