package com.kiro.sg.mysql;

import com.kiro.sg.mysql.query.ExecuteQuery;
import com.kiro.sg.mysql.query.Query;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Consumer implements Runnable
{

	private static Consumer instance;
	private final Database db;
	private final Queue<Query> queries;
	private boolean active;

	public Consumer(Plugin pl, Database db)
	{
		this.db = db;
		instance = this;

		queries = new LinkedBlockingQueue<>();
		active = true;
		Thread dbThread = new Thread(this);
		dbThread.start();
	}

	private static Consumer getInstance()
	{
		return instance;
	}

	public static synchronized void queue(Query query)
	{
		instance.queries.add(query);
	}

	@Override
	public void run()
	{
		while (active)
		{
			try
			{
				Thread.sleep(1000);

				if (!this.queries.isEmpty())
				{
					if (this.db.isConnected())
					{
						Query query;
						while ((query = this.queries.poll()) != null)
						{
							try
							{
								if (query instanceof ExecuteQuery)
								{
									ExecuteQuery executeQuery = (ExecuteQuery) query;
									try (Statement statement = this.db.createStatement())
									{

										ResultSet set = statement.executeQuery(query.getQuery());

										executeQuery.execute(set);

										set.close();
										statement.close();
									}
								}
								else
								{
									try (Statement statement = this.db.createStatement())
									{
										statement.execute(query.getQuery());
									}
								}
							}
							catch (SQLException e)
							{
								try
								{
									Bukkit.getLogger().info(query.getQuery());
									Bukkit.getLogger().info(query.getClass().toString());
								}
								catch (Exception e2)
								{
									e2.printStackTrace();
								}
								e.printStackTrace();
							}
						}
					}
					else
					{
						this.db.connect();
						Thread.sleep(10000);
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public void stop()
	{
		active = false;
	}

	public Collection<Query> getQueries()
	{
		return Collections.unmodifiableCollection(this.queries);
	}
}
