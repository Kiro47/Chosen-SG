package com.kiro.sg.mysql;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Brandon
 *         <p/>
 *         creates a connection to the MySQL Database
 */
public class MySQL extends Database
{

	private static MySQL mysql;
	private String username;
	private String password;
	private String connectionURL;
	private boolean connecting;
	private Connection connection;

	/**
	 * MySQL Wrapper
	 */
	public MySQL(File directory)
	{
		if (mysql == null)
		{
			MySQLConfig.loadConfiguration(directory);
			this.username = MySQLConfig.SQL_USERNAME;
			this.password = MySQLConfig.SQL_PASSWORD;
			this.connectionURL = "jdbc:mysql://" + MySQLConfig.SQL_HOST + ':' + MySQLConfig.SQL_PORT + '/' + MySQLConfig.SQL_DATABASE;
			this.connecting = false;
		}

	}

	public static void init(File file, Plugin pl)
	{
		if (mysql == null)
		{
			mysql = new MySQL(file);
			mysql.connect();
			new Consumer(pl, mysql);
		}
	}

	/**
	 * Attempts to connect to the database
	 *
	 * @return true if it successfully connected
	 */
	@Override
	public boolean connect()
	{
		if (this.connecting)
		{
			return false;
		}
		try
		{
			Bukkit.getLogger().info("Mysql Connecting...");
			this.connecting = true;
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = DriverManager.getConnection(this.connectionURL, this.username, this.password);
			Bukkit.getLogger().info("Mysql Connected!");
			this.connecting = false;
			return this.isConnected();
		}
		catch (ClassNotFoundException e)
		{
			Bukkit.getLogger().info("JDBC Driver not found!");
		}
		catch (SQLException e)
		{
			Bukkit.getLogger().info("Could not connect to MySQL! " + e.getMessage());
		}
		this.connecting = false;
		return false;
	}

	/**
	 * @return the connection
	 */
	@Override
	public Connection getConnection()
	{
		return this.connection;
	}


}
