package com.kiro.sg.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Database
{


	/**
	 * creates a connection to a MySQL Server
	 *
	 * @return true if connected or false if it was unable to establish a
	 * connection
	 */
	public abstract boolean connect();

	public Statement createStatement()
	{
		if (this.isConnected())
		{
			try
			{
				Connection connection = this.getConnection();
				return connection.createStatement();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	protected abstract Connection getConnection();

	/**
	 * @return true if there's a connection
	 */
	public boolean isConnected()
	{
		try
		{
			Connection connection = this.getConnection();
			return (connection != null) && connection.isValid(2000);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
